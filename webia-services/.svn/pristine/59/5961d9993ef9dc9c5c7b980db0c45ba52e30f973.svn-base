package lu.wealins.webia.services.core.service.validations.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import lu.wealins.webia.services.core.service.validations.SendingRulesValidationService;
import lu.wealins.webia.services.core.utils.Constants;

@Service
public class SendingRulesValidationServiceImpl implements SendingRulesValidationService {

	private static final String MANDATORY_EMAIL_AGENT = "Mail to agent is mandatory.";

	@Override
	public void validateMailToAgentRules(String sendingRules, String mailToAgent, List<String> errors) {
		if (StringUtils.isNotEmpty(sendingRules)) {
			if (Arrays.stream(Constants.MAIL_AGENTS_CODE).anyMatch(mac -> sendingRules.indexOf(mac) > -1)) {
				if (StringUtils.isEmpty(mailToAgent)) {
					errors.add(MANDATORY_EMAIL_AGENT);
				}
			}
		}
	}

}
