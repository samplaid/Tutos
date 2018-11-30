package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.EncashmentFundFormDTO;
import lu.wealins.common.dto.webia.services.FindSapEncashmentsResponse;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingResponse;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsRequest;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsResponse;
import lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus;
import lu.wealins.webia.services.core.mapper.EncashmentFundFormMapper;
import lu.wealins.webia.services.core.mapper.SapAccountingMapper;
import lu.wealins.webia.services.core.persistence.entity.EncashmentFundFormEntity;
import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.webia.services.core.persistence.repository.EncashmentFundFormRepository;
import lu.wealins.webia.services.core.persistence.repository.SapAccountingRepository;
import lu.wealins.webia.services.core.service.EncashmentFundFormService;

@Service
public class EncashmentFundFormServiceImpl implements EncashmentFundFormService {

	private static final String APP_FORM_ID_CANNOT_BE_NULL = "Application form id cannot be null";
	private static final String FUND_CANNOT_BE_NULL = "Fund cannot be null.";
	private static final String NEW_STATUS = "NEW";
	private static final String CANCEL_STATUS = "CANCEL";
	private static final String CANCEL_POSTED_STATUS = "CCL_POSTED";
	private static final String NEW_POSTED_STATUS = "NEW_POSTED";

	@Autowired
	private EncashmentFundFormRepository encashmentFundFormRepository;

	@Autowired
	private SapAccountingRepository sapAccountingRepository;

	@Autowired
	private EncashmentFundFormMapper encashmentFundFormMapper;

	@Autowired
	private SapAccountingMapper sapAccountingMapper;

	@Override
	public Collection<EncashmentFundFormDTO> getEncashmentFundForms(Integer formId) {
		Assert.notNull(formId, APP_FORM_ID_CANNOT_BE_NULL);

		List<EncashmentFundFormEntity> encashmentFundForms = encashmentFundFormRepository.findByFormId(formId);
		return encashmentFundFormMapper.asEncashmentFundFormDTOs(encashmentFundForms);
	}

	@Override
	public Collection<EncashmentFundFormDTO> getEncashmentFundForms(Integer formId, String fundId) {
		Assert.notNull(formId, APP_FORM_ID_CANNOT_BE_NULL);

		List<EncashmentFundFormEntity> encashmentFundForms = encashmentFundFormRepository.findByFormIdAndFundId(formId, fundId);
		return encashmentFundFormMapper.asEncashmentFundFormDTOs(encashmentFundForms);
	}

	@Override
	public void updateEncashmentFundForms(Collection<EncashmentFundFormDTO> encashmentFundForms, Integer formId, String fundId, BigDecimal splitAmt, String contractCurrency, Date paymentDt) {
		// if (CollectionUtils.isEmpty(encashmentFundForms)) {
		// return;
		// }

		if (splitAmt == null || StringUtils.isBlank(contractCurrency) || paymentDt == null) {
			return;
		}

		encashmentFundForms.forEach(x -> x.setFormId(formId));

		Collection<EncashmentFundFormDTO> updatableEncashmentFundForm = getActiveEncashmentFundForm(encashmentFundForms);
		if (CollectionUtils.isEmpty(updatableEncashmentFundForm)) {

			EncashmentFundFormDTO encashmentFundFormDTO = new EncashmentFundFormDTO();
			encashmentFundFormDTO.setCashStatus(EncashmentFormFundStatus.NEW.name());
			encashmentFundFormDTO.setFormId(formId);
			encashmentFundFormDTO.setFundId(fundId);
			encashmentFundFormDTO.setCashCurrency(contractCurrency);
			encashmentFundFormDTO.setCashDt(paymentDt);
			encashmentFundFormDTO.setCashAmt(splitAmt);

			encashmentFundForms.add(encashmentFundFormDTO);
		}

		Collection<EncashmentFundFormDTO> newEncashmentFundForms = new ArrayList<>();
		for (EncashmentFundFormDTO encashmentFundForm : encashmentFundForms) {
			// skip status.
			if (EncashmentFormFundStatus.CCL_POSTED.name().equalsIgnoreCase(encashmentFundForm.getCashStatus())
					|| EncashmentFormFundStatus.CANCEL.name().equalsIgnoreCase(encashmentFundForm.getCashStatus())) {
				continue;
			}
			if (encashmentFundForm.getCashFundFormId() != null) {
				// if an ID exists, then check if it has been changed and if it can be updated
				EncashmentFundFormEntity encashmentFundFormEntity = encashmentFundFormRepository.findOne(encashmentFundForm.getCashFundFormId());

				if (encashmentFundFormEntity != null && hasChange(encashmentFundFormEntity, splitAmt, contractCurrency, paymentDt)) {
					// Close old NEW_POSTEDs and recreate new ones.
					if (EncashmentFormFundStatus.NEW_POSTED == encashmentFundFormEntity.getCashStatus()) {

						encashmentFundForm.setCashStatus(EncashmentFormFundStatus.CANCEL.name());

						EncashmentFundFormDTO encashmentFundFormDTO = encashmentFundFormMapper.asEncashmentFundFormDTO(encashmentFundFormEntity);
						encashmentFundFormDTO.setCashFundFormId(null);
						encashmentFundFormDTO.setCashStatus(EncashmentFormFundStatus.NEW.toString());
						updateEncashmentFundFormValue(splitAmt, contractCurrency, paymentDt, encashmentFundFormDTO);

						newEncashmentFundForms.add(encashmentFundFormDTO);
					} else {
						updateEncashmentFundFormValue(splitAmt, contractCurrency, paymentDt, encashmentFundForm);
					}
				}
			}
		}
		encashmentFundForms.addAll(newEncashmentFundForms);
	}

	private void updateEncashmentFundFormValue(BigDecimal splitAmt, String contractCurrency, Date paymentDt, EncashmentFundFormDTO encashmentFundFormDTO) {
		encashmentFundFormDTO.setCashCurrency(contractCurrency);
		encashmentFundFormDTO.setCashDt(paymentDt);
		encashmentFundFormDTO.setCashAmt(splitAmt);
	}

	/**
	 * Simple update
	 * 
	 * @param encashmentFundForm
	 * @param userName
	 * @return
	 */
	private EncashmentFundFormDTO update(EncashmentFundFormDTO encashmentFundForm, String userName) {
		Assert.notNull(encashmentFundForm, FUND_CANNOT_BE_NULL);

		EncashmentFundFormEntity encashmentFundFormEntity = encashmentFundFormMapper.asEncashmentFundFormEntity(encashmentFundForm);

		if (encashmentFundFormEntity == null) {
			return encashmentFundForm;
		}

		encashmentFundFormEntity = encashmentFundFormRepository.save(encashmentFundFormEntity);
		return encashmentFundFormMapper.asEncashmentFundFormDTO(encashmentFundFormEntity);

	}

	@Override
	public EncashmentFundFormEntity save(EncashmentFundFormDTO encashmentFundForm) {
		EncashmentFundFormEntity encashmentFundFormEntity = encashmentFundFormMapper.asEncashmentFundFormEntity(encashmentFundForm);

		return encashmentFundFormRepository.save(encashmentFundFormEntity);
	}
	
	private boolean hasChange(EncashmentFundFormEntity entity, BigDecimal splitAmt, String contractCurrency, Date paymentDt) {
		return (entity == null
				|| (entity.getCashAmt() == null && splitAmt != null) || (entity.getCashAmt() != null && splitAmt == null) || entity.getCashAmt().compareTo(splitAmt) != 0
				|| StringUtils.compare(entity.getCashCurrency(), contractCurrency) != 0
				|| (entity.getCashDt() == null && paymentDt != null) || (entity.getCashDt() != null && paymentDt == null) || entity.getCashDt().compareTo(paymentDt) != 0);
	}

	@Override
	public Collection<EncashmentFundFormDTO> getActiveEncashmentFundForm(Collection<EncashmentFundFormDTO> encashmentFundForm) {
		if (encashmentFundForm == null) {
			return new ArrayList<EncashmentFundFormDTO>();
		}
		return encashmentFundForm.stream()
				.filter(eff -> eff != null && !EncashmentFormFundStatus.CCL_POSTED.toString().equals(eff.getCashStatus()) && !EncashmentFormFundStatus.CANCEL.toString().equals(eff.getCashStatus()))
				.collect(Collectors.toList());
	}

	private void delete(Collection<Integer> ids) {
		for (Integer id : ids) {
			encashmentFundFormRepository.delete(id);
		}
	}

	/**
	 * return encashments for sap accounting
	 * 
	 * @return
	 */
	@Override

	public FindSapEncashmentsResponse findSapEncashments() {
		FindSapEncashmentsResponse response = new FindSapEncashmentsResponse();
		// List<EncashmentFundFormEntity> encashments = encashmentFundFormRepository.findSapEncashments();
		List<EncashmentFundFormEntity> encashments = encashmentFundFormRepository.findByCashStatusIn(Arrays.asList(EncashmentFormFundStatus.CANCEL, EncashmentFormFundStatus.NEW));
		List<EncashmentFundFormDTO> encashmentsDTO = new ArrayList<EncashmentFundFormDTO>();
		if (encashments != null) {
			encashmentsDTO.addAll(encashmentFundFormMapper.asEncashmentFundFormDTOs(encashments));
		}
		response.setEncashments(encashmentsDTO);
		return response;
	}

	@Override
	public SaveEncashmentsInSapAccountingResponse saveInSapAccounting(SaveEncashmentsInSapAccountingRequest request) {
		Collection<SapAccountingEntity> sapToSave = sapAccountingMapper.asSapAccountingEntities(request.getEncashments());
		List<SapAccountingEntity> entitiesSaved = sapAccountingRepository.save(sapToSave);

		SaveEncashmentsInSapAccountingResponse response = new SaveEncashmentsInSapAccountingResponse();
		response.setEncashments((List<SapAccountingDTO>) sapAccountingMapper.asSapAccountingDTOs(entitiesSaved));
		return response;
	}

	@Override
	public UpdateEncashmentsResponse updateEncashments(UpdateEncashmentsRequest request) {
		List<EncashmentFundFormDTO> updatedEncashmentFundForms = new ArrayList<>();
		if (request != null && request.getEncashments() != null && request.getEncashments().size() > 0) {
			for (EncashmentFundFormDTO encashmentFundForm : request.getEncashments()) {

				if (encashmentFundForm.getCashStatus().equals(NEW_STATUS)) {
					encashmentFundForm.setCashStatus(NEW_POSTED_STATUS);
				}

				if (encashmentFundForm.getCashStatus().equals(CANCEL_STATUS)) {
					encashmentFundForm.setCashStatus(CANCEL_POSTED_STATUS);
				}
				updatedEncashmentFundForms.add(update(encashmentFundForm, "SYSTEM"));
			}
		}
		UpdateEncashmentsResponse response = new UpdateEncashmentsResponse();
		response.setEncashments(updatedEncashmentFundForms);
		return response;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteOrCancel(FundFormDTO updatedFundForm){
		Collection<EncashmentFundFormDTO> removableEncashmentFundForm = getActiveEncashmentFundForm(getEncashmentFundForms(updatedFundForm.getFormId(), updatedFundForm.getFundId()));
		Collection<EncashmentFundFormDTO> updatedEncashmentFundForm = getActiveEncashmentFundForm(updatedFundForm.getEncashmentFundForms());
		
		// get the list of entries not posted to SAP that can be physically deleted
		List<Integer> idsToDelete = removableEncashmentFundForm.stream()
				.filter(eff -> !updatedEncashmentFundForm.stream().anyMatch(e -> e.getCashFundFormId() != null && e.getCashFundFormId().equals(eff.getCashFundFormId()))
						&& EncashmentFormFundStatus.NEW.toString().equals(eff.getCashStatus()))
				.map(eff -> eff.getCashFundFormId()).collect(Collectors.toList());
		delete(idsToDelete);
	}

	@Override
	public void deleteOrCancel(FundFormEntity fundForm) {
		ArrayList<EncashmentFundFormEntity> encashmentFundForms = new ArrayList<>();

		for (Iterator<EncashmentFundFormEntity> iterator = fundForm.getEncashmentFundForms().iterator(); iterator.hasNext();) {
			EncashmentFundFormEntity encashmentFundFormEntity = iterator.next();
			if (EncashmentFormFundStatus.NEW.equals(encashmentFundFormEntity.getCashStatus())) {
				encashmentFundFormRepository.delete(encashmentFundFormEntity);
				iterator.remove();
			} else if (EncashmentFormFundStatus.NEW_POSTED.equals(encashmentFundFormEntity.getCashStatus())) {
				encashmentFundFormEntity.setCashStatus(EncashmentFormFundStatus.CANCEL);
				encashmentFundForms.add(encashmentFundFormRepository.save(encashmentFundFormEntity));
			} else {
				encashmentFundForms.add(encashmentFundFormEntity);
			}
		}

		fundForm.setEncashmentFundForms(encashmentFundForms);
	}
}
