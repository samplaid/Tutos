package lu.wealins.webia.core.service.document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyBeneficiaryClauseStatus;
import lu.wealins.editing.common.webia.BeneficiaryClause;
import lu.wealins.editing.common.webia.ChangeBeneficiaryDetails;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.EndOnInsuredDeathType;
import lu.wealins.editing.common.webia.Endorsement;
import lu.wealins.editing.common.webia.Policy;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.webia.core.service.LiabilitPolicyChangeService;
import lu.wealins.webia.core.service.impl.LiabilityBeneficiaryChangeRequestHelper;
import lu.wealins.webia.core.service.impl.PolicyDocumentService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class PolicyBenefChangeService extends PolicyDocumentService {

	@Autowired
	private LiabilityBeneficiaryChangeRequestHelper beneficiaryRequestHelper;

	@Autowired
	private LiabilitPolicyChangeService policyChangeService;

	@Autowired
	private RestClientUtils restClientUtils;

	private static final String LIABILITY_BENEFICIARY_CHANGE = "liability/beneficiaryChange/clientRoleView";

	@Override
	protected Document createSpecificDocument(EditingRequest editingRequest, PolicyDTO policyDTO, String productCountry, String language, String userId) {
		Endorsement endorsement = generateEndorsement(policyDTO, productCountry, editingRequest.getWorkflowItemId());
		Policy policy = endorsement.getPolicy();

		Document document = createPolicyDocument(editingRequest.getWorkflowItemId(), policyDTO, productCountry, language, policy, userId);
		document.setEndorsement(endorsement);
		return document;
	}

	@Override
	protected DocumentType getDocumentType() {
		return DocumentType.CHANGE_BENEF;
	}

	private Endorsement generateEndorsement(PolicyDTO policyDTO, String productCountry, Long workflowItemId) {

		BeneficiaryChangeRequest request = beneficiaryRequestHelper.createBeneficiaryChangeRequest(policyDTO.getPolId(), workflowItemId.intValue());
		BeneficiaryChangeDTO beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE, request, BeneficiaryChangeDTO.class);
		ChangeBeneficiaryDetails changeBeneficiaryDetails = getChangeDetails(beneficiaryChange, policyDTO);
		PolicyChangeDTO policyChange = policyChangeService.getPolicyChange(workflowItemId.intValue());

		List<PolicyHolder> holders = generateHolders(beneficiaryChange.getPolicyHolders());
		Policy policy = getXmlPolicy(policyDTO, holders);

		Endorsement endorsement = new Endorsement();
		endorsement.setChangeBeneficiaryDetails(changeBeneficiaryDetails);
		endorsement.setEffectDate(policyChange.getDateOfChange());
		endorsement.setPolicy(policy);

		return endorsement;
	}

	private ChangeBeneficiaryDetails getChangeDetails(BeneficiaryChangeDTO beneficiaryChange, PolicyDTO policyDTO) {

		Collection<PolicyBeneficiaryClauseDTO> policyDeathClauses = unionAndFilterCollections(beneficiaryChange.getDeathBenefClauses(), beneficiaryChange.getDeathNominativeClauses());
		Collection<PolicyBeneficiaryClauseDTO> policylifeClauses = unionAndFilterCollections(beneficiaryChange.getLifeBenefClauses(), beneficiaryChange.getLifeNominativeClauses());

		BeneficiaryClause xmlDeathClauses = generateClauses(DEATH, policyDeathClauses, beneficiaryChange.getDeathBeneficiaries(),
				beneficiaryChange.getLifeBeneficiaries());
		BeneficiaryClause xmlLifeClauses = generateClauses(LIFE, policylifeClauses, beneficiaryChange.getDeathBeneficiaries(),
				beneficiaryChange.getLifeBeneficiaries());

		ChangeBeneficiaryDetails changeBeneficiaryDetails = new ChangeBeneficiaryDetails();
		changeBeneficiaryDetails.setBeneficiaryClauseDeath(xmlDeathClauses);
		changeBeneficiaryDetails.setBeneficiaryClauseLife(xmlLifeClauses);

		EndOnInsuredDeathType endOnInsuredDeathEnum = getEndOnInsuredDeath(policyDTO.getFirstPolicyCoverages());

		if (endOnInsuredDeathEnum != null) {
			changeBeneficiaryDetails.setEndOnInsuredDeath(endOnInsuredDeathEnum);
		}
		return changeBeneficiaryDetails;
	}

	private Collection<PolicyBeneficiaryClauseDTO> unionAndFilterCollections(Collection<PolicyBeneficiaryClauseDTO> collectionA, Collection<PolicyBeneficiaryClauseDTO> collectionB) {

		Collection<PolicyBeneficiaryClauseDTO> clauses = Stream.concat(collectionA.stream(), collectionB.stream()).collect(Collectors.toList());
		boolean containsActiveClause = clauses.stream().anyMatch(clause -> clause.getStatus() != null && clause.getStatus().intValue() == PolicyBeneficiaryClauseStatus.ACTIVE.getValue());
		return containsActiveClause
				? clauses.stream().filter(clause -> clause.getStatus() == null || PolicyBeneficiaryClauseStatus.ACTIVE.getValue() == clause.getStatus().intValue())
						.collect(Collectors.toList())
				: clauses.stream().filter(clause -> clause.getStatus() == null || PolicyBeneficiaryClauseStatus.INACTIVE.getValue() == clause.getStatus().intValue())
						.collect(Collectors.toList());
	}

	@Override
	protected String getXmlPath(EditingRequest editingRequest, String policyId) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formatedDate = date.format(formatter);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(filePath);
		stringBuilder.append("/");
		stringBuilder.append("CBF");
		stringBuilder.append("_");
		stringBuilder.append(editingRequest.getWorkflowItemId());
		stringBuilder.append("_");
		if (Boolean.TRUE.equals(editingRequest.getSimulation())) {
			stringBuilder.append("SIM");
			stringBuilder.append("_");
		}
		stringBuilder.append(escapePolicyId(policyId));
		stringBuilder.append("_");
		stringBuilder.append(formatedDate);
		stringBuilder.append(".xml");

		return stringBuilder.toString();
	}
}
