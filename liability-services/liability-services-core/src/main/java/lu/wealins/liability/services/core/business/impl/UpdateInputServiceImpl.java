package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.BeneficiaryService;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.PolicyClausesService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyTransferService;
import lu.wealins.liability.services.core.business.UpdateInputService;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.UpdateInputResponse;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;

@Service
public class UpdateInputServiceImpl implements UpdateInputService {

	@Autowired
	private PolicyClausesService policyClausesService;
	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;
	@Autowired
	private BeneficiaryService beneficiaryService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PolicyTransferService policyTransferService;

	@Override
	public UpdateInputResponse updateInput(UpdateInputRequest request) {
		Assert.notNull(request);

		updatePolicyBeneficiaryClauses(request);
		updateBeneficiaries(request);
		updatePolicyHolders(request);
		updateOtherClients(request);
		updatePolicy(request);
		updatePolicyTransfers(request);

		return new UpdateInputResponse();
	}


	private void updatePolicyTransfers(UpdateInputRequest request) {
		policyTransferService.deleteByPolicyAndCoverage(request.getPolicyId(), 1);
		policyTransferService.update(request.getPolicyTransfers());
	}

	private void updatePolicy(UpdateInputRequest request) {
		policyService.updateForUpdateInputStep(request, request.getUserName());
	}

	private void updateBeneficiaries(UpdateInputRequest request) {
		beneficiaryService.updateDeathBeneficiaries(request.getDeathBeneficiaries(), request.getPolicyId(), request.getPaymentDt());
		beneficiaryService.updateLifeBeneficiaries(request.getLifeBeneficiaries(), request.getPolicyId(), request.getPaymentDt());
		disableBeneficiaryRelationships(request);
	}

	private void disableBeneficiaryRelationships(UpdateInputRequest request) {
		List<Integer> excludedClientIds = request.getDeathBeneficiaries().stream().map(x -> x.getCliId()).collect(Collectors.toList());
		excludedClientIds.addAll(request.getLifeBeneficiaries().stream().map(x -> x.getCliId()).collect(Collectors.toList()));
		cliPolRelationshipService.disableCliPolRelationship(request.getPolicyId(), excludedClientIds, CliPolRelationshipType.BENEFICIARY_RELATIONSHIP_TYPE_GROUP);
	}

	private void updateOtherClients(UpdateInputRequest request) {
		updateExistingOtherClients(request.getOtherClients(), request.getPolicyId(), request.getPaymentDt());
		disableOtherClientsRelationships(request);
	}


	private void updatePolicyHolders(UpdateInputRequest request) {
		updateExistingPolicyHolders(request.getPolicyHolders(), request.getPolicyId(), request.getPaymentDt());
		disablePolicyHoldersRelationships(request);
	}

	private void disablePolicyHoldersRelationships(UpdateInputRequest request) {
		List<Integer> excludedClientIds = request.getPolicyHolders().stream().map(x -> x.getCliId()).collect(Collectors.toList());
		cliPolRelationshipService.disableCliPolRelationship(request.getPolicyId(), excludedClientIds, CliPolRelationshipType.POLICY_HOLDER_RELATIONSHIP_TYPE_GROUP);
	}

	private void disableOtherClientsRelationships(UpdateInputRequest request) {
		List<Integer> excludedClientIds = request.getOtherClients().stream().map(x -> x.getCliId()).collect(Collectors.toList());
		cliPolRelationshipService.disableCliPolRelationship(request.getPolicyId(), excludedClientIds, CliPolRelationshipType.OTHER_CLIENT_RELATIONSHIP_TYPE_GROUP);
	}

	private void updateExistingOtherClients(Collection<OtherClientDTO> otherClients, String policyId, Date paymentDt) {
		otherClients.forEach(otherClient -> cliPolRelationshipService.saveOtherClient(otherClient, policyId, paymentDt));
	}

	private void updateExistingPolicyHolders(Collection<PolicyHolderDTO> policyHolders, String policyId, Date paymentDt) {
		policyHolders.forEach(policyHolder -> cliPolRelationshipService.savePolicyHolder(policyHolder, policyId, paymentDt));
	}

	private void updatePolicyBeneficiaryClauses(UpdateInputRequest request) {
		policyClausesService.updatePolicyBeneficiaryClauses(request.getDeathPolicyBeneficiaryClauses(), request.getLifePolicyBeneficiaryClauses(), request.getPolicyId());
	}
}
