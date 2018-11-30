package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AbstractFundFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.webia.services.core.mapper.FundFormMapper;
import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;
import lu.wealins.webia.services.core.persistence.repository.EncashmentFundFormRepository;
import lu.wealins.webia.services.core.persistence.repository.FundFormRepository;
import lu.wealins.webia.services.core.service.EncashmentFundFormService;
import lu.wealins.webia.services.core.service.FundFormService;

@Service
public class FundFormServiceImpl implements FundFormService {

	private static final String FAS = "FAS";
	private static final String FID = "FID";
	private static final String FE = "FE";
	private static final String FIC = "FIC";
	private static final String APP_FORM_ID_CANNOT_BE_NULL = "Application form id cannot be null";
	private static final String FUND_CANNOT_BE_NULL = "Fund cannot be null.";
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	@Autowired
	private FundFormRepository fundFormRepository;

	@Autowired
	private EncashmentFundFormRepository encashmentFundFormRepository;

	@Autowired
	private FundFormMapper fundFormMapper;

	@Autowired
	private EncashmentFundFormService encashmentFundFormService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.FundFormService#getFundForms(java.lang.Integer)
	 */
	@Override
	public Collection<FundFormEntity> getFundForms(Integer formId) {
		Assert.notNull(formId, APP_FORM_ID_CANNOT_BE_NULL);

		return fundFormRepository.findByFormId(formId);
	}

	@Override
	public Collection<FundFormEntity> update(Collection<FundFormDTO> fundForms, Integer formId, BigDecimal paymentAmt, String contractCurrency, Date paymentDt) {
		Collection<FundFormEntity> updatedFundForms = new ArrayList<>();
		for (FundFormDTO fundForm : fundForms) {
			fundForm.setFormId(formId);
			updateEncashmentFundForms(fundForm, paymentAmt, contractCurrency, paymentDt);

			// encashmentFundFormService.deleteOrCancel(fundForm);
			FundFormEntity fundFormEntity = save(fundForm);

			if (BooleanUtils.isFalse(fundForm.getIsCashFundAccount())) {
				encashmentFundFormService.deleteOrCancel(fundFormEntity);
			}

			updatedFundForms.add(fundFormEntity);
		}
		return updatedFundForms;
	}

	private void updateEncashmentFundForms(FundFormDTO fundForm, BigDecimal paymentAmt, String contractCurrency, Date paymentDt) {
		BigDecimal splitAmount = computeSplitAmount(fundForm.getSplit(), paymentAmt);
		if (BooleanUtils.isTrue(fundForm.getIsCashFundAccount())){
			encashmentFundFormService.updateEncashmentFundForms(fundForm.getEncashmentFundForms(), fundForm.getFormId(), fundForm.getFundId(), splitAmount, contractCurrency, paymentDt);
		}
			
	}

	private BigDecimal computeSplitAmount(BigDecimal split, BigDecimal paymentAmt) {
		if (split != null && paymentAmt != null) {
			return paymentAmt.multiply(split).divide(HUNDRED, BigDecimal.ROUND_CEILING);
		}
		return null;
	}

	private FundFormEntity save(FundFormDTO fundForm) {
		Assert.notNull(fundForm, FUND_CANNOT_BE_NULL);
		
		FundFormEntity fundFormEntity = fundFormMapper.asFundFormEntity(fundForm);
		encashmentFundFormRepository.save(fundFormEntity.getEncashmentFundForms());

		return fundFormRepository.save(fundFormEntity);
	}

	@Override
	public FundFormDTO update(FundFormDTO fundForm) {
		return fundFormMapper.asFundFormDTO(save(fundForm));
	}

	private <T extends AbstractFundFormDTO> boolean hasType(T fundForm, String fundTp) {
		return fundForm != null && fundTp != null && fundTp.equalsIgnoreCase(fundForm.getFundTp());
	}

	@Override
	public <T extends AbstractFundFormDTO> boolean isFidOrFas(T fundForm) {
		return hasType(fundForm, FID) || hasType(fundForm, FAS);
	}

	@Override
	public <T extends AbstractFundFormDTO> boolean isFeOrFic(T fundForm) {
		return hasType(fundForm, FE) || hasType(fundForm, FIC);
	}

	@Override
	public <T extends AbstractFundFormDTO> boolean containsFeOrFic(Collection<T> fundForms) {
		return fundForms.stream().anyMatch(x -> isFeOrFic(x));
	}

	@Override
	public <T extends AbstractFundFormDTO> Collection<T> getFidOrFas(Collection<T> fundForms) {
		return fundForms.stream().filter(x -> isFidOrFas(x)).collect(Collectors.toList());
	}

	@Override
	public void delete(Collection<FundFormEntity> fundForms) {
		for (Iterator<FundFormEntity> iterator = fundForms.iterator(); iterator.hasNext();) {
			FundFormEntity fundForm = iterator.next();
			encashmentFundFormService.deleteOrCancel(fundForm);
			fundForm.setEncashmentFundForms(new ArrayList<>());
			fundFormRepository.delete(fundForm);

			iterator.remove();
		}
	}

	@Override
	public void resetCashAccount(FundFormEntity fundForm) {
		fundForm.setIsCashFundAccount(false);
		fundFormRepository.save(fundForm);
	}

}
