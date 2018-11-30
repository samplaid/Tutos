/**
 * 
 */
package lu.wealins.webia.services.core.service.validations.appform;

import java.util.List;

import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface ComAndSpecifitiesValidationService {

	/**
	 * Validate the sending rule to be not null.
	 * 
	 * @param appForm the form that contains the communication and the specificities.
	 * @param errors a list of errors
	 */
	void validateSendingRulesNotNull(AppFormDTO appForm, List<String> errors);

	/**
	 * Register an error when the value of the field {@link AppFormDTO#getSendingRules() sending rule} does not contained in the {@code mailSendingRulesCodes} list and the field
	 * {@link AppFormDTO#getMailToAgent() mailToAgent} is filled in.
	 * 
	 * @param appForm the form that contains the communication and the specificities.
	 * @param agentMailSendingRulesCodes a list of sending rules the mail will be deliver to the agent.
	 * @param errors a list of errors
	 */
	void validateMailToAgentNullOverSendingRulesCodes(AppFormDTO appForm, List<String> agentMailSendingRulesCodes, List<String> errors);
}
