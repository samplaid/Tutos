/**
 * 
 */
package lu.wealins.webia.core.service.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.FollowUpPolicyDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.ws.rest.request.TranscoType;

@Service
public class FollowUpDocumentContentHelper {

	private static String WEALINS_BROKER_ID = "WEALINS_BROKER_ID";
	private static String WEALINS_ASSET_MANAGER_ID = "WEALINS_ASSET_MANAGER_ID";
	private static final String DEFAULT_FOLLOW_UP_LANG = "";

	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private WebiaApplicationParameterService webiaApplicationParameterService;

	public String formatIban(String iban) {
		String value = StringUtils.deleteWhitespace(iban);
		final int length = value.length();
		final int lastPossibleBlock = length - 4;
		final StringBuilder sb = new StringBuilder(length + (length - 1) / 4);
		int i;
		for (i = 0; i < lastPossibleBlock; i += 4) {
			sb.append(value, i, i + 4);
			sb.append(' ');
		}
		sb.append(value, i, length);
		return sb.toString();
	}


	public String getStep(AppFormDTO appFormDTO) {
		if (appFormDTO != null && appFormDTO.getWorkflowItemId() != null) {
			WorkflowGeneralInformationDTO workflowGeneralInformationDTO = getWorkflowInfo(
					appFormDTO.getWorkflowItemId().longValue());
			return workflowGeneralInformationDTO.getCurrentStepName();
		}
		return StringUtils.EMPTY;
	}

	public WorkflowGeneralInformationDTO getWorkflowInfo(Long workFlowItemId) {
		String usrId = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
		return workflowService.getWorkflowGeneralInformation(workFlowItemId + "", usrId);
	}

	public boolean isBrokerWealins(AppFormDTO enrichedAppForm) {

		boolean isBrokerWealins = false;
		String brokerId = null;
		if (enrichedAppForm != null && enrichedAppForm.getBroker() != null
				&& enrichedAppForm.getBroker().getPartnerId() != null
				&& !enrichedAppForm.getBroker().getPartnerId().isEmpty()) {

			brokerId = enrichedAppForm.getBroker().getPartnerId();
			List<String> configuredWealinsBrokerIds = (List<String>) webiaApplicationParameterService
					.getApplicationParameters(WEALINS_BROKER_ID);

			if (configuredWealinsBrokerIds != null && !configuredWealinsBrokerIds.isEmpty()) {

				String configuredWealinsBroker = configuredWealinsBrokerIds.stream()
						.filter(id -> id != null && !id.isEmpty()).findFirst().orElse(null);

				isBrokerWealins = configuredWealinsBroker != null
						&& brokerId.equalsIgnoreCase(configuredWealinsBroker.trim());
			}
		}
		return isBrokerWealins;

	}

	public boolean isAgentWealinsAssetManager(String agentId) {
		if (agentId == null || agentId.isEmpty()) {
			return false;
		}
		boolean isWealinsAssetManager = false;
		List<String> configuredWealinsAssetManagerIds = (List<String>) webiaApplicationParameterService
				.getApplicationParameters(WEALINS_ASSET_MANAGER_ID);

		if (configuredWealinsAssetManagerIds != null && !configuredWealinsAssetManagerIds.isEmpty()) {

			String configuredWealinsAssetManager = configuredWealinsAssetManagerIds.stream()
					.filter(id -> id != null && !id.isEmpty()).findFirst().orElse(null);
			isWealinsAssetManager = configuredWealinsAssetManager != null
					&& agentId.equalsIgnoreCase(configuredWealinsAssetManager.trim());
		}

		return isWealinsAssetManager;
	}

	public String getAgentLanguage(AgentLightDTO in) {
		if (in == null) {
			return DEFAULT_FOLLOW_UP_LANG;
		}
		String language = null;
		if (in.getDocumentationLanguage() != null) {
			language = documentGenerationService.getTransco(TranscoType.DOCUMENT_LANGUAGE,
					in.getDocumentationLanguage().toString());
			if (language == null || language.trim().isEmpty()) {
				language = documentGenerationService.getTransco(TranscoType.LANGUAGE,
						in.getDocumentationLanguage().toString());
			}
		}

		if (language == null || language.trim().isEmpty()) {
			language = DEFAULT_FOLLOW_UP_LANG;
		}

		return language;
	}

	public Collection<String> getPoliciesOfFunds(FundLiteDTO fund) {
		if (fund != null && fund.getFdsId() != null && !fund.getFdsId().isEmpty()) {
			return policyService.getPoliciesByFund(fund.getFdsId().trim());
		}
		return null;
	}

	public Collection<String> getPoliciesOfFunds(String fundId) {
		if (fundId != null && !fundId.isEmpty()) {
			return policyService.getPoliciesByFund(fundId.trim());
		}
		return null;
	}

	public int getPoliciesNumber(AppFormDTO enrichedAppForm) {

		if (enrichedAppForm == null || enrichedAppForm.getFundForms() == null
				|| enrichedAppForm.getFundForms().isEmpty()) {
			return 0;
		}

		
		SortedSet<FollowUpPolicyDTO> policiesFund = getFundPolicies(enrichedAppForm);

		return (new ArrayList<>(policiesFund)).size()+1;
	}
	
	
	public SortedSet<FollowUpPolicyDTO> getFundPolicies(AppFormDTO enrichedAppForm ) {

		if (enrichedAppForm == null || enrichedAppForm.getFundForms() == null
				|| enrichedAppForm.getFundForms().isEmpty()) {
			return null;
		}
		
		String defaultPolicy = StringUtils.stripToEmpty(enrichedAppForm.getPolicyId());

		Set<FollowUpPolicyDTO> policiesFund = enrichedAppForm.getFundForms().stream().map(fundForm -> fundForm.getFund())
				.filter(fundLite -> isFIDOrFAS(fundLite)).map(fundLiteDTO -> fundLiteDTO.getFdsId())
				.filter(fund -> fund != null && !fund.isEmpty()).map(fundId -> policyService.getPoliciesByFund(fundId))
				.flatMap(Collection::stream)
				.filter(policy -> !StringUtils.isBlank(policy))
				.map(fundPolicy -> StringUtils.trim(fundPolicy))
				.filter(police -> !police.equals(defaultPolicy))
				.map(policyId -> policyService.getPolicy(policyId))
				.filter(policy -> policy!=null )
				.map(policyDto -> {
					FollowUpPolicyDTO followUpPolicy = new FollowUpPolicyDTO();
					followUpPolicy.setPolicy(policyDto.getPolId());
					followUpPolicy.setAppForm(StringUtils.strip(policyDto.getAdditionalId()));
					followUpPolicy.setEffectDate(policyDto.getDateOfCommencement());
					return followUpPolicy;
				})
				.collect(Collectors.toSet());	
	

		if (policiesFund == null || policiesFund.isEmpty()) {
			SortedSet<FollowUpPolicyDTO> policiesFundForms = new TreeSet<FollowUpPolicyDTO>();
			return policiesFundForms;
		}
		
		return new TreeSet<FollowUpPolicyDTO>(policiesFund);
	}

	public boolean isFAS(FundLiteDTO fundDTO) {
		return fundDTO != null && fundDTO.getFundSubType() != null
				&& FundSubType.FAS.name().equals(fundDTO.getFundSubType().trim());
	}

	public boolean isFID(FundLiteDTO fundDTO) {
		return fundDTO != null && fundDTO.getFundSubType() != null
				&& FundSubType.FID.name().equals(fundDTO.getFundSubType().trim());
	}

	public boolean isFIDOrFAS(FundLiteDTO fundDTO) {
		boolean isFid = isFID(fundDTO);
		boolean isFas = isFAS(fundDTO);
		return isFid || isFas;
	}

}

