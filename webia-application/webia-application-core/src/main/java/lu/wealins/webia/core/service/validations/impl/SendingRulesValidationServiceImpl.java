package lu.wealins.webia.core.service.validations.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.ClientContactDetailDTO;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.webia.core.service.LiabilityClientService;
import lu.wealins.webia.core.service.validations.SendingRulesValidationService;

@Service
public class SendingRulesValidationServiceImpl implements SendingRulesValidationService {

	@Autowired
	private LiabilityClientService clientService;

	@Override
	public void validate(String sendingRules, Integer firstPolicyHolderClientId, Collection<String> errors) {
		if (sendingRules != null && sendingRules.startsWith("MCC")) {
			ClientDTO client = clientService.getClient(firstPolicyHolderClientId);
			ClientContactDetailDTO correspondenceAddress = client.getCorrespondenceAddress();

			if (correspondenceAddress == null) {
				errors.add("Sending rules - No correspondence address exists yet for the first policyholder.");
			}
		}
	}
}
