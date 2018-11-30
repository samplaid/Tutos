package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;
import lu.wealins.common.dto.webia.services.UpdateAppFormPolicyDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.services.core.mapper.AppFormMapper;
import lu.wealins.webia.services.core.persistence.entity.AppFormEntity;
import lu.wealins.webia.services.core.persistence.entity.BenefClauseFormEntity;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;
import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;
import lu.wealins.webia.services.core.persistence.entity.PolicyTransferFormEntity;
import lu.wealins.webia.services.core.persistence.repository.AppFormRepository;
import lu.wealins.webia.services.core.service.AppFormService;
import lu.wealins.webia.services.core.service.BenefClauseFormService;
import lu.wealins.webia.services.core.service.BeneficiaryService;
import lu.wealins.webia.services.core.service.ClientFormService;
import lu.wealins.webia.services.core.service.EncashmentFundFormService;
import lu.wealins.webia.services.core.service.FundFormService;
import lu.wealins.webia.services.core.service.InsuredService;
import lu.wealins.webia.services.core.service.PartnerFormService;
import lu.wealins.webia.services.core.service.PolicyHolderService;
import lu.wealins.webia.services.core.service.PolicyTransferFormService;
import lu.wealins.webia.services.core.service.UpdatableStatusService;

@Service(value = "appFormService")
public class AppFormServiceImpl extends WorkflowFormServiceImpl<AppFormDTO> implements AppFormService, UpdatableStatusService {

	private static final String APP_FORM_CANNOT_BE_NULL = "AppForm cannot be null.";
	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String FORM_ITEM_ID_CANNOT_BE_NULL = "Form item id cannot be null.";
	private static final String DEFAULT_CURRENCY = "EUR";
	private static final String POLICY_ID_NOT_NULL = "The policy id can't be null";
	private static final String UPDATE_REQUEST_NOT_NULL = "The dto can't be null";

	@Autowired
	private AppFormRepository appFormRepository;

	@Autowired
	private AppFormMapper appFormMapper;

	@Autowired
	private PartnerFormService partnerFormService;

	@Autowired
	private BenefClauseFormService benefClauseFormService;

	@Autowired
	private FundFormService fundFormService;
	
	@Autowired
	private EncashmentFundFormService encashmentFundFormService;

	@Autowired
	private PolicyHolderService policyHolderService;

	@Autowired
	private InsuredService insuredService;

	@Autowired
	private BeneficiaryService beneficiaryService;

	@Autowired
	private ClientFormService clientFormService;
	
	@Autowired
	private PolicyTransferFormService policyTransferFormService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.AppFormService#getAppForm(java.lang.Integer)
	 */
	@Override
	public AppFormDTO getFormData(Integer workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		return appFormMapper.asAppFormDTO(appFormRepository.findByWorkflowItemId(workflowItemId));
	}

	@Override
	public AppFormDTO initFormData(Integer workflowItemId) {
		AppFormDTO formData = super.initFormData(workflowItemId);

		formData.setContractCurrency(DEFAULT_CURRENCY);

		return formData;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.AppFormService#update(lu.wealins.common.dto.webia.services.AppFormDTO, java.lang.String)
	 */
	@Override
	public AppFormDTO updateFormData(AppFormDTO appForm, String stepWorkflow) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);

		boolean isAfterOrEqualsUpdateInput = StepTypeDTO.getStepType(stepWorkflow).isAfterOrEquals(StepTypeDTO.UPDATE_INPUT);
		if (isAfterOrEqualsUpdateInput) {
			return appForm;
		}

		AppFormEntity appFormEntity = appFormMapper.asAppFormEntity(appForm);
		appFormEntity = appFormRepository.save(appFormEntity);

		updateClientForms(appForm, appFormEntity);
		updatePartnerForms(appForm, appFormEntity);
		updateFundForms(appForm, appFormEntity);
		updateBenefClauseForms(appForm, appFormEntity);
		updatePolicyTransferForms(appForm, appFormEntity);

		return appFormMapper.asAppFormDTO(appFormEntity);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.AppFormService#updateStatus(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Boolean updateStatus(Integer workflowItemId, OperationStatus status) {
		Assert.notNull(workflowItemId);
		Assert.notNull(status);

		AppFormEntity appFormEntiy = appFormRepository.findByWorkflowItemId(workflowItemId);
		appFormEntiy.setStatusCd(status.name());
		appFormRepository.save(appFormEntiy);

		return Boolean.TRUE;
	}

	@Override
	public Boolean updateCoverage(Integer formId, Integer coverage) {
		Assert.notNull(formId);

		AppFormEntity appFormEntiy = appFormRepository.findOne(formId);

		appFormEntiy.setCoverage(coverage);
		appFormRepository.save(appFormEntiy);

		return Boolean.TRUE;
	}

	private void updateBenefClauseForms(AppFormDTO appForm, AppFormEntity target) {
		updateLifeBenefClauseForms(appForm, target);
		updateDeathBenefClauseForms(appForm, target);
	}
	
	@Transactional(rollbackFor = { Exception.class })
	private void updatePolicyTransferForms(AppFormDTO appForm, AppFormEntity target) {

		Integer appFormId = target.getFormId();
		Collection<PolicyTransferFormEntity> updatedPolicyTransferForms = policyTransferFormService.update(appForm.getPolicyTransferForms(), appFormId);
		target.setPolicyTransferForms(updatedPolicyTransferForms);

		Collection<PolicyTransferFormEntity> policyTransferFormsFromDB = getPolicyTransfers(appForm.getFormId());
		Collection<PolicyTransferFormEntity> policyTransferFormToRemove = policyTransferFormsFromDB.stream()
				.filter(pt -> pt != null && pt.getPolicyTransferFormId() != null && !updatedPolicyTransferForms.stream().anyMatch(upt -> upt.getPolicyTransferFormId().equals(pt.getPolicyTransferFormId())))
				.collect(Collectors.toList());

		policyTransferFormService.delete(policyTransferFormToRemove);
		
		
	}

	@Transactional(rollbackFor = { Exception.class })
	private void updateLifeBenefClauseForms(AppFormDTO appForm, AppFormEntity target) {
		Integer appFormId = target.getFormId();

		Collection<BenefClauseFormEntity> lifeBenefClauseFormsFromDB = getLifeBenefClauseForms(appForm);
		Collection<BenefClauseFormEntity> updatedLifeBenefClauseForms = benefClauseFormService.update(appForm.getLifeBenefClauseForms(), appFormId);
		target.setLifeBenefClauseForms(updatedLifeBenefClauseForms);

		Collection<Integer> lifeBenefClauseFormIdsFromDB = getBenefClauseFormIds(lifeBenefClauseFormsFromDB);
		Collection<Integer> updatedLifeBenefClauseFormIds = getBenefClauseFormIds(updatedLifeBenefClauseForms);
		Collection<Integer> lifeBenefClauseFormIdsToRemove = CollectionUtils.subtract(lifeBenefClauseFormIdsFromDB, updatedLifeBenefClauseFormIds);

		benefClauseFormService.delete(lifeBenefClauseFormIdsToRemove);
	}

	@Transactional(rollbackFor = { Exception.class })
	private void updateDeathBenefClauseForms(AppFormDTO appForm, AppFormEntity target) {
		Integer appFormId = target.getFormId();

		Collection<BenefClauseFormEntity> deathBenefClauseFormsFromDB = getDeathBenefClauseForms(appForm);
		Collection<BenefClauseFormEntity> updatedDeathBenefClauseForms = benefClauseFormService.update(appForm.getDeathBenefClauseForms(), appFormId);
		target.setDeathBenefClauseForms(updatedDeathBenefClauseForms);

		Collection<Integer> deathBenefClauseFormIdsFromDB = getBenefClauseFormIds(deathBenefClauseFormsFromDB);
		Collection<Integer> updatedDeathBenefClauseFormIds = getBenefClauseFormIds(updatedDeathBenefClauseForms);
		Collection<Integer> deathBenefClauseFormIdsToRemove = CollectionUtils.subtract(deathBenefClauseFormIdsFromDB, updatedDeathBenefClauseFormIds);

		benefClauseFormService.delete(deathBenefClauseFormIdsToRemove);
	}

	@Transactional(rollbackFor = { Exception.class })
	private void updateClientForms(AppFormDTO appForm, AppFormEntity target) {
		Integer formId = target.getFormId();
		// Remove all
		clientFormService.deleteWithFormId(formId);

		Collection<ClientFormEntity> clientForms = policyHolderService.update(appForm.getPolicyHolders(), formId);

		clientForms.addAll(insuredService.update(appForm.getInsureds(), formId));
		clientForms.addAll(beneficiaryService.updateBeneficiaries(appForm.getLifeBeneficiaries(), formId));
		clientForms.addAll(beneficiaryService.updateBeneficiaries(appForm.getDeathBeneficiaries(), formId));
		
		//filter the other relationship to leave only one client-role
		Collection<ClientFormDTO> otherClients = appForm.getOtherClients();
		Collection<ClientFormDTO> filterOtherClients = otherClients.stream().filter(o-> !clientForms.stream().anyMatch(c -> c.getClientId().equals(o.getClientId()) && c.getClientRelationTp().equals(o.getClientRelationTp())) ).collect(Collectors.toList());
	
		clientForms.addAll(clientFormService.updateOtherClients(filterOtherClients, formId));

		target.setClientForms(clientForms);

	}

	@Transactional(rollbackFor = { Exception.class })
	private void updatePartnerForms(AppFormDTO appForm, AppFormEntity target) {
		Integer appFormId = target.getFormId();
		Collection<PartnerFormEntity> partnerFormsFromDB = getPartnerForms(appForm);
		Collection<PartnerFormEntity> partnerForms = new ArrayList<>();

		if (appForm.getBroker() != null) {
			if (BooleanUtils.isFalse(appForm.getBroker().getIsOverridedFees()) && StringUtils.isNotEmpty(appForm.getBroker().getExplainOverFees())) {
				appForm.getBroker().setExplainOverFees(null);
			}

			// make sure no broker contact are selected if Wealins is chosen as a Broker
			if (partnerFormService.isWealinsBroker(appForm.getBroker())) {
				appForm.setBrokerContact(null);
			}

			partnerForms.add(partnerFormService.update(appForm.getBroker(), appFormId));
		}
		if (appForm.getSubBroker() != null) {
			partnerForms.add(partnerFormService.update(appForm.getSubBroker(), appFormId));
		}
		if (appForm.getBrokerContact() != null) {
			partnerForms.add(partnerFormService.update(appForm.getBrokerContact(), appFormId));
		}
		if (appForm.getBusinessIntroducer() != null) {
			partnerForms.add(partnerFormService.update(appForm.getBusinessIntroducer(), appFormId));
		}
		if (CollectionUtils.isNotEmpty(appForm.getCountryManagers())) {
			partnerForms.addAll(partnerFormService.update(appForm.getCountryManagers(), appFormId));
		}

		target.setPartnerForms(partnerForms);

		Collection<Integer> partnerFormIds = getPartnerFormIds(partnerFormsFromDB);
		Collection<Integer> updatedPartnerFormIds = getPartnerFormIds(partnerForms);
		Collection<Integer> partnerFormIdsToRemove = CollectionUtils.subtract(partnerFormIds, updatedPartnerFormIds);

		partnerFormService.delete(partnerFormIdsToRemove);
	}

	@Transactional(rollbackFor = { Exception.class })
	private void updateFundForms(AppFormDTO appForm, AppFormEntity target) {
		Integer appFormId = target.getFormId();
		BigDecimal paymentAmt = appForm.getPaymentAmt();
		String contractCurrency = appForm.getContractCurrency();
		Date paymentDt = appForm.getPaymentDt();
		Collection<FundFormEntity> updatedFundForms = fundFormService.update(appForm.getFundForms(), appFormId, paymentAmt, contractCurrency, paymentDt);
		target.setFundForms(updatedFundForms);

		Collection<FundFormEntity> fundFormsFromDB = getFundForms(appForm.getFormId());
		Collection<FundFormEntity> fundFormToRemove = fundFormsFromDB.stream()
				.filter(ff -> ff != null && ff.getFundFormId() != null && !updatedFundForms.stream().anyMatch(uff -> uff.getFundFormId().equals(ff.getFundFormId())))
				.collect(Collectors.toList());

		fundFormService.delete(fundFormToRemove);
	}

	private Collection<Integer> getBenefClauseFormIds(Collection<BenefClauseFormEntity> benefClauseForms) {
		return benefClauseForms.stream().map(partnerForm -> {
			return partnerForm.getBenefClauseFormId();
		}).collect(Collectors.toList());
	}

	private Collection<Integer> getPartnerFormIds(Collection<PartnerFormEntity> partnerForms) {
		return partnerForms.stream().map(partnerForm -> {
			return partnerForm.getPartnerFormId();
		}).collect(Collectors.toList());
	}

	private Collection<BenefClauseFormEntity> getLifeBenefClauseForms(AppFormDTO appForm) {
		Integer appFormId = appForm.getFormId();
		if (appFormId != null) {
			return benefClauseFormService.getLifeBenefClauseForms(appFormId);
		}

		return new ArrayList<>();
	}

	private Collection<BenefClauseFormEntity> getDeathBenefClauseForms(AppFormDTO appForm) {
		Integer appFormId = appForm.getFormId();
		if (appFormId != null) {
			return benefClauseFormService.getDeathBenefClauseForms(appFormId);
		}

		return new ArrayList<>();
	}

	private Collection<PartnerFormEntity> getPartnerForms(AppFormDTO appForm) {
		Integer appFormId = appForm.getFormId();
		if (appFormId != null) {
			return partnerFormService.getPartnerForms(appFormId);
		}

		return new ArrayList<>();
	}

	private Collection<FundFormEntity> getFundForms(Integer appFormId) {
		if (appFormId != null) {
			return fundFormService.getFundForms(appFormId);
		}

		return new ArrayList<>();
	}
	
	private Collection<PolicyTransferFormEntity> getPolicyTransfers(Integer appFormId) {
		if (appFormId != null) {
			return policyTransferFormService.getPolicyTransfers(appFormId);
		}

		return new ArrayList<>();
	}

	@Override
	public AppFormDTO abort(AppFormDTO appForm) {

		appForm.setStatusCd(OperationStatus.ABORTED.name());
		AppFormEntity appFormEntity = appFormRepository.findOne(appForm.getFormId());

		// handle encashmment funds
		appFormEntity.getFundForms().forEach(fundForm -> {
			encashmentFundFormService.deleteOrCancel(fundForm);
			fundFormService.resetCashAccount(fundForm);
		});
		
		appFormRepository.save(appFormEntity);

		return appForm;
	}

	@Override
	public AppFormDTO getAppForm(Integer formId) {
		Assert.notNull(formId, FORM_ITEM_ID_CANNOT_BE_NULL);

		AppFormEntity appForm = appFormRepository.findByFormId(formId);

		return appFormMapper.asAppFormDTO(appForm);
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.APP_FORM;
	}

	@Override
	@Transactional
	public AppFormDTO updatePolicy(UpdateAppFormPolicyDTO request) {
		Assert.notNull(request, UPDATE_REQUEST_NOT_NULL);
		Assert.notNull(request.getFormId(), FORM_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(request.getPolicyId(), POLICY_ID_NOT_NULL);

		AppFormEntity appFormEntity = appFormRepository.findByFormId(request.getFormId());
		appFormEntity.setPolicyId(request.getPolicyId());

		return appFormMapper.asAppFormDTO(appFormEntity);
	}


	@Override
	public Collection<AppFormDTO> getAppFormsByPolicy(String policyId) {
		List<AppFormEntity> appFormsEntity = appFormRepository.findByPolicyId(policyId);
		if (appFormsEntity == null || appFormsEntity.isEmpty()) {
			return new ArrayList<AppFormDTO>();
		}
		return appFormsEntity.stream().map(appForm -> appFormMapper.asAppFormDTO(appForm)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AppFormDTO recreate(Integer workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		AppFormEntity appFormEntity = appFormRepository.findByWorkflowItemId(workflowItemId);
		appFormEntity.setStatusCd(OperationStatus.DRAFT.name());
		appFormEntity.setCoverage(null);
		return appFormMapper.asAppFormDTO(appFormEntity);
	}

}
