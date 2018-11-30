/**
 * 
 */
package lu.wealins.liability.services.core.validation.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.business.exceptions.ClientCreationException;
import lu.wealins.liability.services.core.business.exceptions.ReportExceptionHelper;
import lu.wealins.liability.services.core.validation.ClientValidationService;
import lu.wealins.liability.services.core.validation.ClientValidator;
import lu.wealins.common.dto.liability.services.ClientDTO;

/**
 * This class executes the validation of {@link ClientDTO}.
 * 
 * @author oro
 *
 */
@Service
public class ClientValidatorImpl implements ClientValidator<ClientDTO> {

	@Autowired
	private ClientValidationService validationService;
	@Autowired
	private ClientService clientService;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void validate(ClientDTO client) {
		Assert.notNull(client);
		Set<String> errors = new HashSet<>();

		errors.addAll(validationService.validateName(client.getName()));

		if (clientService.isMoral(client)) {
			errors.addAll(validationService.validateNoDeceasedRepresentingCompany(client.getPersonsRepresentingCompany()));
		}
		errors.addAll(validationService.validateMedicalQuestionnaire(client.getHealthDecDate()));
		errors.addAll(validationService.validateTin(client));
		errors.addAll(validationService.validateCountryDateOfChange(client));
		errors.addAll(validationService.validateKycDateNotInFuture(client.getKycDate()));

		// date of consent
		errors.addAll(validationService.validateGdprStartDateNotInFuture(client.getGdprStartdate()));
		errors.addAll(validationService.validateGdprEndDateNotInFuture(client.getGdprEnddate()));
		errors.addAll(validationService.validateDateOfConsent(client));

		errors.addAll(validationService.validateLastDateOfReviewNotInFuture(client.getDateOfRevision()));
		errors.addAll(validationService.validateEmail(client));
		errors.addAll(validationService.validateAccounts(client.getClientAccounts()));

		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, ClientCreationException.class);

	}


}
