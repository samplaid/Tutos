package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OptionDetails;

public interface OptionDetailService {

	/**
	 * Get the lives constant (option = 'NBVLVS')
	 * 
	 * @return A list of Live constants or an empty list.
	 */
	List<OptionDetailDTO> getLives();

	/**
	 * Get the lives constant (option = 'NBVLVS') for a given product id
	 * 
	 * @return A list of Live constants or an empty list.
	 */
	List<OptionDetailDTO> getLives(String prdId);

	/**
	 * Get the cliPolRelationship roles (option = 'CPR_TYPE')
	 * 
	 * @return The cliPolRelationship roles response.
	 */
	OptionDetails getCPRRoles();

	/**
	 * FK_OPTIONSOPT_ID = 'LANGUAGES'
	 * 
	 * @return
	 */
	List<OptionDetailDTO> getLanguages();

	/**
	 * FK_OPTIONSOPT_ID = 'CLI_MSTAT'
	 * 
	 * @return
	 */
	List<OptionDetailDTO> getMaritalStatus();

	/**
	 * Get the policy statuses. FK_OPTIONSOPT_ID = 'STATUS_POL'+statusPolNumber
	 * 
	 * @return The policy statuses.
	 */
	List<OptionDetailDTO> getPolicyStatuses(Integer statusPolNumber);

	/**
	 * Get the client policy relation types. FK_OPTIONSOPT_ID = 'CPR_TYPE'
	 * 
	 * @return The client policy relation types.
	 */
	List<OptionDetailDTO> getClientPolicyRelationTypes();

	/**
	 * Find option detail having the specified number in the collection of opetion details.
	 * 
	 * @param optionDetails The option details.
	 * @param number The number.
	 * @return The option detail.
	 */
	OptionDetailDTO getOptionDetail(Collection<OptionDetailDTO> optionDetails, Integer number);

	/**
	 * Find option details having the specified numbers in the collection of opetion details.
	 * 
	 * @param optionDetails The option details.
	 * @param number The numbers.
	 * @return The option details.
	 */
	Collection<OptionDetailDTO> getOptionDetails(Collection<OptionDetailDTO> optionDetails, Collection<Integer> numbers);

	/**
	 * Get the pricing frequency where FK_OPTIONSOPT_ID = 'PRICING_FREQUENCY'
	 * 
	 * @return set of pricing frequency
	 */
	List<OptionDetailDTO> getPricingFrequency();

	/**
	 * Get policy description note.
	 * 
	 * @param ponType the value representing the policy note in option details table
	 * @return a list of policy description note.
	 */
	List<OptionDetailDTO> getPolicyNote(String ponType);

	/**
	 * Get an instance of policy description note related to the provided number
	 * 
	 * @param ponType the value representing the policy note in option details table
	 * @param number the number of the policy option details.
	 * @return an option detail.
	 */
	OptionDetailDTO getPolicyNote(String ponType, int number);

	/**
	 * Get the fund price type constant (option = 'PRICE_TYPE')
	 * 
	 * @return A list of fund price type constants or an empty list.
	 */
	List<OptionDetailDTO> getPriceTypes();

	/**
	 * Get the payment modes type constant (option = 'AGSPME')
	 * 
	 * @return A list of payment modes type constants or an empty list.
	 */
	List<OptionDetailDTO> getPaymentModes();
	
	/**
	 * Get all the account status (option = 'STATUS_BKA')
	 * 
	 * @return A list of payment modes type constants or an empty list.
	 */
	List<OptionDetailDTO> getAccountStatus();
}
