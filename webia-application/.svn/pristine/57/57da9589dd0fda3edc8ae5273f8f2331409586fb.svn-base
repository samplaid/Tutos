package lu.wealins.webia.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.FollowUpPolicyDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.CreateEditingRequest;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.editing.common.webia.SubscriptionFollowUp;
import lu.wealins.webia.core.service.AppFormPolicyDataService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;

public abstract class PolicyTransactionsHistoryMapper {

	private static final String NOMINATIVE = "N";

	private static final String EN = "EN";

	@Autowired
	private BenefClauseFormMapper benefClauseFormMapper;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private LiabilityProductService productService;

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private AppFormPolicyDataService appFormService;

	@Autowired
	private BeneficiaryMapper beneficiaryMapper;

	@Autowired
	private PartnerFormMapper partnerFormMapper;
	
	@Autowired
	private FollowUpDocumentContentHelper followUpDocumentContentHelper;

	@Mappings({
			@Mapping(target = "productCd", source = "product.prdId"),
			@Mapping(target = "productCountryCd", source = "product.nlCountry"),
			@Mapping(target = "applicationForm", source = "additionalId"),
			@Mapping(target = "policyId", source = "polId"),
			@Mapping(target = "countryCd", source = "issueCountryOfResidence"),
			@Mapping(target = "contractCurrency", source = "currency"),
			@Mapping(target = "sendingRules", source = "category.uddId"),
			@Mapping(target = "mailToAgent", source = "mailToAgent.agtId"),
			@Mapping(target = "paymentDt", source = "dateOfCommencement"),
			@Mapping(target = "nonSurrenderClauseDt", source = "nonSurrenderClauseDate"),
			@Mapping(target = "score", source = "scoreNewBusiness"),			
			@Mapping(target = "noCoolOff", source = "noCooloff"),
			@Mapping(target = "policyTransferForms", source = "policyTransfers"),
			

			@Mapping(ignore = true, target = "expectedPremium"),
			@Mapping(ignore = true, target = "broker"),
			@Mapping(ignore = true, target = "brokerContact"),
			@Mapping(ignore = true, target = "subBroker"),
			@Mapping(ignore = true, target = "businessIntroducer"),
			@Mapping(ignore = true, target = "countryManagers"),
			@Mapping(ignore = true, target = "deathBeneficiaries"),
			@Mapping(ignore = true, target = "lifeBeneficiaries"),
			@Mapping(ignore = true, target = "insureds"),

	})
	public abstract void updateAppFormDTO(PolicyDTO in, @MappingTarget AppFormDTO out);

	protected void afterMapping(PolicyDTO policy, AppFormDTO appForm) {
		mapClauses(appForm, policy);
		mapBroker(appForm, policy);
		mapBeneficiaries(appForm, policy);
	}

	@Mappings({ @Mapping(source = "productCd", target = "product"),
			@Mapping(source = "workflowItemId", target = "workflowItemId"),
			@Mapping(source = "policyId", target = "policy"), })
	public abstract void asAppFormDTOToCreateEditingRequest(AppFormDTO in, @MappingTarget CreateEditingRequest out);

	private void mapClauses(AppFormDTO appForm, PolicyDTO policy) {

		PolicyClausesDTO policyClauses = policyService.getClauses(policy.getPolId(), appForm.getProductCd(), EN);
		List<PolicyBeneficiaryClauseDTO> policyBeneficiaryClauses = policyClauses.getMaturity();

		// remove clause nominative, same behavior as the policy screen
		policyBeneficiaryClauses.removeIf(x -> NOMINATIVE.equalsIgnoreCase(x.getTypeOfClause()));
		if (CollectionUtils.isNotEmpty(policyBeneficiaryClauses)) {
			appForm.setLifeBenefClauseForms(benefClauseFormMapper.asBenefClauseFormDTOs(policyBeneficiaryClauses));
		}
		
		policyBeneficiaryClauses = policyClauses.getDeath();
		policyBeneficiaryClauses.removeIf(x -> NOMINATIVE.equalsIgnoreCase(x.getTypeOfClause()));

		// remove clause nominative, same behavior as the policy screen
		if (CollectionUtils.isNotEmpty(policyBeneficiaryClauses)) {
			appForm.setDeathBenefClauseForms(benefClauseFormMapper.asBenefClauseFormDTOs(policyBeneficiaryClauses));
		}

	}

	private void mapBeneficiaries(AppFormDTO appForm, PolicyDTO policy) {
		appForm.setLifeBeneficiaries(mapBeneficiaries(policy.getLifeBeneficiaries(), ClientRelationType.BENEFICIARY_AT_MATURITY));
		appForm.setDeathBeneficiaries(mapBeneficiaries(policy.getDeathBeneficiaries(), ClientRelationType.BENEFICIARY_AT_DEATH));
	}

	private Collection<BeneficiaryFormDTO> mapBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, ClientRelationType clientRelationType) {
		Collection<BeneficiaryFormDTO> beneficiaryForms = new ArrayList<>();
		for (BeneficiaryDTO beneficiary : beneficiaries) {
			BeneficiaryFormDTO beneficiaryForm = beneficiaryMapper.asBeneficiaryFormDTO(beneficiary);
			beneficiaryForm.setClientRelationTp(clientRelationType.getValue());

			beneficiaryForms.add(beneficiaryForm);
		}
		return beneficiaryForms;
	}

	private void mapBroker(AppFormDTO appForm, PolicyDTO policy) {
		PolicyAgentShareDTO broker = policy.getBroker();
		if (broker != null) {
			PartnerFormDTO partner = createPartner(appForm, broker, AgentCategory.BROKER);

			updateBrokerFees(policy, partner);

			appForm.setBroker(partner);
		}

	}

	protected void updateBrokerFees(PolicyDTO policy, PartnerFormDTO partner) {
		partnerFormMapper.updateBrokerFees(policy, partner);
	}

	protected PartnerFormDTO asPartnerFormDTO(PolicyAgentShareDTO lissiaPartner) {
		return partnerFormMapper.asPartnerFormDTO(lissiaPartner);
	}

	private PartnerFormDTO createPartner(AppFormDTO appForm, PolicyAgentShareDTO lissiaPartner, AgentCategory category) {
		PartnerFormDTO partner = asPartnerFormDTO(lissiaPartner);
		partner.setPartnerCategory(category.getCategory());
		partner.setFormId(appForm.getFormId());
		return partner;
	}

	@Mappings({ @Mapping(target = "product", source = "productCd"),
			@Mapping(target = "contractCurrency", source = "contractCurrency"),
	})
	public abstract SubscriptionFollowUp asSouscriptionFollow(AppFormDTO appForm);

	@AfterMapping
	protected SubscriptionFollowUp SouscriptionFollowAfterMapping(AppFormDTO appForm,
			@MappingTarget SubscriptionFollowUp target) {
		String step = getStep(appForm);
		target.setStep(step);
		target.setProduct(getProductName(target.getProduct()));
		
		SortedSet<FollowUpPolicyDTO> policies = followUpDocumentContentHelper.getFundPolicies(appForm);

		List<String> policiesList = new ArrayList<>();
		List<String> applicationForms = new ArrayList<>();
		if (policies == null || policies.isEmpty()) {
			policiesList.add(StringUtils.stripToEmpty(appForm.getPolicyId()));
			applicationForms.add(StringUtils.stripToEmpty(appForm.getApplicationForm()));
			target.setPolicyIds(policiesList);
			target.setApplicationForms(applicationForms);
			return target;
		}

		
		policiesList.add(StringUtils.stripToEmpty(appForm.getPolicyId()));
		applicationForms.add(StringUtils.stripToEmpty(appForm.getApplicationForm()));
		
		for(FollowUpPolicyDTO followUpPolicyDTO : policies) {	
			policiesList.add(StringUtils.stripToEmpty(followUpPolicyDTO.getPolicy()));
			applicationForms.add(StringUtils.stripToEmpty(followUpPolicyDTO.getAppForm()));		
		}

		target.setPolicyIds(policiesList);
		target.setApplicationForms(applicationForms);

		return target;
	}

	@AfterMapping
	protected CreateEditingRequest AppFormDTOToCreateEditingRequest(AppFormDTO in,
			@MappingTarget CreateEditingRequest out) {
		if (out != null && (out.getPolicy() == null || out.getPolicy().trim().isEmpty())) {
			out.setPolicy(in.getApplicationForm());
		}
		return out;
	}

	private String getStep(AppFormDTO appFormDTO) {
		if (appFormDTO != null && appFormDTO.getWorkflowItemId() != null) {
			WorkflowGeneralInformationDTO workflowGeneralInformationDTO = getWorkflowInfo(
					appFormDTO.getWorkflowItemId().longValue());
			return workflowGeneralInformationDTO.getCurrentStepName();
		}
		return StringUtils.EMPTY;
	}

	private WorkflowGeneralInformationDTO getWorkflowInfo(Long workFlowItemId) {
		String usrId = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
		return workflowService.getWorkflowGeneralInformation(workFlowItemId + "", usrId);
	}

	private String getProductName(String productId) {
		String productName = productId;

		if (productId != null && !productId.isEmpty()) {
			ProductDTO productDto = productService.getProduct(productId.trim());
			if (productDto != null && productDto.getName() != null && !productDto.getName().isEmpty()) {
				productName = productDto.getName();
			}
		}

		return productName;
	}

	private boolean isFIDOrFAS(FundLiteDTO fundDTO) {
		return isFID(fundDTO) || isFAS(fundDTO);
	}
	private boolean isFID(FundLiteDTO fundDTO) {
		return FundSubType.FID.name().equals(fundDTO.getFundSubType());
	}

	private boolean isFAS(FundLiteDTO fundDTO) {
		return FundSubType.FAS.name().equals(fundDTO.getFundSubType());
	}

}
