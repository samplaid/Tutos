/**
 * 
 */
package lu.wealins.liability.services.core.validation.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.liability.services.core.persistence.repository.ClientRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.TinUtils;
import lu.wealins.liability.services.core.utils.constantes.Constantes;
import lu.wealins.liability.services.core.validation.ClientContactDetailValidationService;
import lu.wealins.liability.services.core.validation.ClientValidationService;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;
import lu.wealins.common.dto.liability.services.account.ClientAccountDTO;
import lu.wealins.common.dto.liability.services.enums.ClientLinkedPersonStatus;
import lu.wealins.common.dto.liability.services.enums.ClientType;

/**
 * This class provides the methods that validates the client informations.
 * 
 * @author oro
 *
 */
@Service
public class ClientValidationServiceImpl implements ClientValidationService {

	private static final String CONSENT_DATE_ORDER_INCORRECT = "The start date of consent should not be after the end date of consent.";
	private static final String CONSENT_START_DATE_NOT_FILLED_BUT_END_DATE = "The start date of consent is not defined however the end date of consent is filled.";
	private static final String EMAIL_MANDATORY = "The email is mandatory.";
	private static final String NAME_MANDATORY = "The name is mandatory.";
	private static final String ACCOUNT_STATUS_MANDATORY = "The accounts status are mandatory.";

	@Autowired
	private ClientContactDetailValidationService clientContactDetailValidationService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientService clientService;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Set<String> validateName(String name) {
		Set<String> errors = new HashSet<>();

		if (StringUtils.trimToEmpty(name).isEmpty()) {
			errors.add(NAME_MANDATORY);
		}

		return errors;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Set<String> validateTin(ClientDTO client) {
		Assert.notNull(client);
		Assert.notNull(client.getType());

		Set<String> errors = new HashSet<>();

		if (client.getType().intValue() != ClientType.MORAL.getType()) {

			if (!StringUtils.isEmpty(client.getNationalIdNo()) && !StringUtils.isEmpty(client.getNatIdCountry())) {
				if (!TinUtils.isTinValid(client.getNationalIdNo(), client.getNatIdCountry())) {
					errors.add("TIN '" + client.getNationalIdNo() + "' is not valid for the country (1): " + client.getNatIdCountry());
				}
			}

			if (!StringUtils.isEmpty(client.getNationalId2()) && !StringUtils.isEmpty(client.getNatId2Country())) {
				if (!TinUtils.isTinValid(client.getNationalId2(), client.getNatId2Country())) {
					errors.add("TIN '" + client.getNationalId2() + "' is not valid for the country (2): " + client.getNatId2Country());
				}
			}
		}

		return errors;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Set<String> validateEmail(ClientDTO client) {
		Assert.notNull(client);

		Set<String> errors = new HashSet<>();

		if (BooleanUtils.toBoolean(client.getStatementByEmail())) {

			if (StringUtils.isEmpty(client.getEmail())) {
				errors.add(EMAIL_MANDATORY);
			} else {
				validateEmail(client.getEmail().split(";"));
			}
		}

		return errors;
	}

	/**
	 * Validate a set of email
	 * 
	 * @param emails the set of email
	 */
	private Set<String> validateEmail(String[] emails) {
		Set<String> errors = new HashSet<>();

		for (int i = 0; i < emails.length; i++) {
			if (!StringUtils.isEmpty(emails[i]) && !emails[i].matches(Constantes.EMAIL_REGEX)) {
				errors.add("The email " + emails[i] + " is in wrong format.");
			}
		}

		return errors;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Set<String> validateDateOfConsent(ClientDTO client) {
		Assert.notNull(client);

		Set<String> errors = new HashSet<>();

		if (CalendarUtils.isNull(client.getGdprStartdate()) && CalendarUtils.nonNull(client.getGdprEnddate())) {
			errors.add(CONSENT_START_DATE_NOT_FILLED_BUT_END_DATE);
		}

		if (CalendarUtils.nonNull(client.getGdprStartdate()) && CalendarUtils.nonNull(client.getGdprEnddate())) {

			if (client.getGdprStartdate().compareTo(client.getGdprEnddate()) > 0) {
				errors.add(CONSENT_DATE_ORDER_INCORRECT);
			}
		}

		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateCountryDateOfChange(ClientDTO client) {
		Assert.notNull(client);
		return clientContactDetailValidationService.validateCountryDateOfChange(client.getHomeAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateAccounts(Collection<ClientAccountDTO> accounts) {
		Set<String> errors = new HashSet<>();

		if(!CollectionUtils.isEmpty(accounts) && accounts.stream().anyMatch(account -> account.getStatus() == null)) {
			errors.add(ACCOUNT_STATUS_MANDATORY);
		}
		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateNoDeceasedRepresentingCompany(Collection<ClientLinkedPersonDTO> representingCompanies) {
		Set<String> errors = new HashSet<>();

		if (CollectionUtils.isNotEmpty(representingCompanies)) {
			representingCompanies.forEach(representingCompany -> {
				ClientEntity controllingPerson = clientRepository.findOne(representingCompany.getControllingPerson());

				if (clientService.isDead(controllingPerson) && ClientLinkedPersonStatus.ACTIVE.getStatus().equals(representingCompany.getStatus())) {
					StringBuilder errorsMessageBuilder = new StringBuilder();
					errorsMessageBuilder.append("The representing company ");
					errorsMessageBuilder.append(controllingPerson.getName());
					errorsMessageBuilder.append(" - ");
					errorsMessageBuilder.append(controllingPerson.getFirstName());
					errorsMessageBuilder.append(" (");
					errorsMessageBuilder.append(controllingPerson.getCliId());
					errorsMessageBuilder.append(" ) is already died.");
					errors.add(errorsMessageBuilder.toString());
				}
			});
		}

		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateKycDateNotInFuture(Date kycDate) {
		return validateDateNotInFuture(kycDate, "The KYC date must not be in the future.");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateGdprStartDateNotInFuture(Date gdprStartDate) {
		return validateDateNotInFuture(gdprStartDate, "The start date of consent must not be in the future.");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateGdprEndDateNotInFuture(Date gdprEndDate) {
		return validateDateNotInFuture(gdprEndDate, "The end date of consent must not be in the future.");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateLastDateOfReviewNotInFuture(Date lastDateOfReview) {
		return validateDateNotInFuture(lastDateOfReview, "The last date of the review must not be in the future.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateMedicalQuestionnaire(Date healthDecDate) {
		return validateDateNotInFuture(healthDecDate, "The Medical questionaire must not be in the future.");
	}

}
