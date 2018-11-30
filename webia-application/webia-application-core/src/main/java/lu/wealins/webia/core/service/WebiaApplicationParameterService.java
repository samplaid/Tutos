package lu.wealins.webia.core.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;

public interface WebiaApplicationParameterService {

	/**
	 * Get the application parameter according its code.
	 * 
	 * @param code The application parameter code.
	 * @return The application parameter.
	 */
	ApplicationParameterDTO getApplicationParameter(String code);
	
	/**
	 * Get the application parameters according its code.
	 * 
	 * @param code The application parameter code.
	 * @return The application parameter.
	 */
	Collection<String> getApplicationParameters(String code);

	/**
	 * Get logins who are allowed to by pass step access control.
	 * 
	 * @return The logins
	 */
	Set<String> getLoginsByPassStepAccess();
	
	/**
	 * Get list of integers from the application parameter code.
	 * 
	 * @param code The application parameter code.
	 * @return The list of integers.
	 */
	List<Integer> getIntegerValues(String code);

	/**
	 * Get A big decimal value from the application parameter code.
	 * 
	 * @param code The application parameter code.
	 * @return the big decimal value.
	 */
	BigDecimal getBigDecimalValue(String code);

	/**
	 * Get An integer value from the application parameter code.
	 * 
	 * @param code The application parameter code.
	 * @return the integer value.
	 */
	Integer getIntegerValue(String code);

}
