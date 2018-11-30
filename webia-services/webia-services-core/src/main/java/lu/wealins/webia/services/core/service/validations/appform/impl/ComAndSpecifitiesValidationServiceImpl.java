/**
 * 
 */
package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.service.validations.appform.ComAndSpecifitiesValidationService;
import lu.wealins.common.dto.webia.services.AppFormDTO;

@Service
public class ComAndSpecifitiesValidationServiceImpl implements ComAndSpecifitiesValidationService {
	private static final String SENDING_RULES_REQUIRED = "The sending rules is mandatory.";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateSendingRulesNotNull(AppFormDTO appForm, List<String> errors) {
		if (StringUtils.isBlank(appForm.getSendingRules())) {
			errors.add(SENDING_RULES_REQUIRED);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateMailToAgentNullOverSendingRulesCodes(AppFormDTO appForm, List<String> agentMailSendingRulesCodes, List<String> errors) {
		Assert.notNull(errors, "Error object list must not be null.");
		Assert.notNull(agentMailSendingRulesCodes, "The list of excluded mail sending rule must not be null.");

		if (!agentMailSendingRulesCodes.contains(appForm.getSendingRules()) && StringUtils.isNotBlank(appForm.getMailToAgent())) {
			errors.add("Mail to agent must be emptied for specified sending rule with code " + appForm.getSendingRules());
		}
	}

}
