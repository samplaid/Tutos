/**
 * 
 */
package lu.wealins.liability.services.core.validation;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;
import lu.wealins.common.dto.liability.services.account.ClientAccountDTO;

/**
 * This interface is used to validate client informations
 * 
 * @author oro
 *
 */
public interface ClientValidationService {

	/**
	 * Validates the client name.
	 * 
	 * @param name the client name
	 * @param errors a list that contains errors message
	 * @return list of errors
	 */
	Set<String> validateName(String name);
	
	
	/**
	 * Validates the client name.
	 * 
	 * @param name the client name
	 * @param errors a list that contains errors message
	 * @return list of errors
	 */
	
	/**
	 * Validate medical Questionnaire
	 * <p> Medical questionnaire must not be in the future.
	 * @param healthDecDate
	 * @return list of error
	 */
	Set<String> validateMedicalQuestionnaire(Date healthDecDate);

	/**
	 * Validate client TIN.
	 * 
	 * @param client the client
	 */
	Set<String> validateTin(ClientDTO client);

	/**
	 * Validate the client email if the statement by email is filled in.
	 * 
	 * @param email the client email
	 * @return list of errors
	 */
	Set<String> validateEmail(ClientDTO client);

	/**
	 * Validate the client date of consent.
	 * 
	 * @param client the client
	 * @return list of errors
	 */
	Set<String> validateDateOfConsent(ClientDTO client);

	/**
	 * If the client country has been changed, assert the date of change to be filled in.
	 * 
	 * @param clientContact the client contact.
	 * @return list of errors
	 */
	Set<String> validateCountryDateOfChange(ClientDTO client);
	
	/**
	 * If the client has linked accounts, we verify that the status are not null.
	 * 
	 * @param accounts the accounts linked to the client.
	 * @return list of errors
	 */
	Set<String> validateAccounts(Collection<ClientAccountDTO> accounts);

	/**
	 * The representing company should not be a client that is already died.
	 * 
	 * @param representingCompany a set of clients
	 * @return list of errors
	 */
	Set<String> validateNoDeceasedRepresentingCompany(Collection<ClientLinkedPersonDTO> representingCompany);

	/**
	 * Validates if the KYC date is not in the future.
	 * 
	 * @param kycDate a date
	 * @return list of errors
	 */
	Set<String> validateKycDateNotInFuture(Date kycDate);

	/**
	 * Validates if the GDPR start date is not in the future.
	 * 
	 * @param gdprStartDate a date
	 * @return list of errors
	 */
	Set<String> validateGdprStartDateNotInFuture(Date gdprStartDate);

	/**
	 * Validates if the GDPR end date is not in the future.
	 * 
	 * @param gdprEndDate a date
	 * @return list of errors
	 */
	Set<String> validateGdprEndDateNotInFuture(Date gdprEndDate);

	/**
	 * Validates if the review last date is not in the future.
	 * 
	 * @param lastDateOfReview a date
	 * @return list of errors
	 */
	Set<String> validateLastDateOfReviewNotInFuture(Date lastDateOfReview);

	/**
	 * Validate a date to be not in the future.
	 * 
	 * @param date the date to validate
	 * @param message an error message to returned when the condition is met.
	 * @return list of errors.
	 */
	default Set<String> validateDateNotInFuture(Date date, String message) {
		Set<String> errors = new HashSet<>();

		if (CalendarUtils.nonNull(date)) {
			Date now = CalendarUtils.resetTime(Calendar.getInstance().getTime());
			Date inernalDate = CalendarUtils.resetTime(date);

			if (now.before(inernalDate)) {
				errors.add(message);
			}

		}

		return errors;
	}

}
