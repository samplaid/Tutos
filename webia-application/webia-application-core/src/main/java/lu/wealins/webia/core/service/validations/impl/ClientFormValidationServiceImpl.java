package lu.wealins.webia.core.service.validations.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.enums.ClientType;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;
import lu.wealins.webia.core.service.LiabilityClientService;
import lu.wealins.webia.core.service.validations.ClientFormValidationService;

@Service
public class ClientFormValidationServiceImpl implements ClientFormValidationService {

	@Autowired
	private LiabilityClientService clientService;

	@Override
	public void validatePolicyHolders(Collection<PolicyHolderFormDTO> policyHolders, List<String> errors) {
		if (!CollectionUtils.isEmpty(policyHolders)) {
			policyHolders.forEach(policyHolder -> {

				if (policyHolder != null) {
					ClientDTO policyHolderClient = clientService.getClient(policyHolder.getClientId());

					if (BooleanUtils.isTrue(policyHolderClient.isDead())) {
						errors.add("The policy holder named " + policyHolderClient.getFirstName() + " - " + policyHolderClient.getName() + " is died. The process cannot be continued.");
					}

				}

			});
		}
	}

	@Override
	public void validateGdpr(Collection<InsuredFormDTO> insureds, List<String> errors) {

		insureds.forEach(insured -> {

			boolean gdprAccepted = clientService.isClientGdprConsentAccepted(insured.getClientId());

			if (!gdprAccepted) {
				ClientDTO insuredClient = clientService.getClient(insured.getClientId());
				StringBuilder messageBuilder = new StringBuilder(
						"Cannot accept the client consient: GDPR - Date of consent should be filled in and GDPR - End of consent should be emptied: (");
				messageBuilder.append(insuredClient.getFirstName());
				messageBuilder.append(" - ");
				messageBuilder.append(insuredClient.getName());
				messageBuilder.append(").");
				errors.add(messageBuilder.toString());
			}
		});
	}

	@Override
	public void validateInsureds(Collection<InsuredFormDTO> insureds, List<String> errors) {
		if (!CollectionUtils.isEmpty(insureds)) {
			insureds.forEach(insured -> {

				if (insured != null) {
					ClientDTO insuredClient = clientService.getClient(insured.getClientId());

					if (insuredClient != null && ClientType.PHYSICAL.getType() != insuredClient.getType()) {
						errors.add("Insured client named " + insuredClient.getFirstName() + " - " + insuredClient.getName() + " must not be a client entity. ( id = " + insuredClient.getCliId() + ")");
					}

					if (BooleanUtils.isTrue(insuredClient.isDead())) {
						errors.add("Insured client named " + insuredClient.getFirstName() + " - " + insuredClient.getName() + " is died. The process cannot be continued.");
					}

				}

			});
		}
	}

}
