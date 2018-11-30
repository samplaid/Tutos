package lu.wealins.webia.services.core.service.validations;

import java.util.List;

public interface CpsValidationService {

	/**
	 * Validate the first cps user.
	 * 
	 * @param firstCpsUser The first cps user.
	 * @param errors The errors messages.
	 */
	void validateFirstCps(String firstCpsUser, List<String> errors);

	/**
	 * Validate the second cps user.
	 * 
	 * @param secondCpsUser The second cps user.
	 * @param errors The errors messages.
	 */
	void validateSecondCps(String secondCpsUser, List<String> errors);

	/**
	 * Check if the first cps user is not null and different of the second cps uer.
	 * 
	 * @param firstCpsUser The first cps user.
	 * @param secondCpsUser The second cps user.
	 * @param errors The error messages.
	 */
	void validateFourEyes(String firstCpsUser, String secondCpsUser, List<String> errors);

	/**
	 * Validate the first cps user and the second user. Check if the first cps user is not null and different of the second cps uer.
	 * 
	 * @param formData The formData
	 * @param errors
	 */
	void validateCpsUsers(String firstCpsUser, String secondCpsUser, List<String> errors);
}
