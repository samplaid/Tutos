/**
 * 
 */
package lu.wealins.liability.services.core.validation.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.mapper.ClientContactDetailsMapper;
import lu.wealins.liability.services.core.persistence.entity.ClientContactDetailEntity;
import lu.wealins.liability.services.core.persistence.repository.ClientContactDetailRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.validation.ClientContactDetailValidationService;
import lu.wealins.common.dto.liability.services.ClientContactDetailDTO;

/**
 * @author oro
 *
 */
@Service
public class ClientContactDetailValidationServiceImpl implements ClientContactDetailValidationService {
	private static final String MANDATORY_COUNTRY_DATE_OF_CHANGE = "The country address has been changed. The date of change should be provided.";

	@Autowired
	private ClientContactDetailRepository clientContactDetailRepository;

	@Autowired
	private ClientContactDetailsMapper contactDetailsMapper;

	/**
	 * The client contact must not be null. <br>
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> validateCountryDateOfChange(ClientContactDetailDTO clientContact) {
		Assert.notNull(clientContact);
		
		Set<String> errors = new HashSet<>();
		
		if (StringUtils.isNotBlank(clientContact.getClcId())) {
			ClientContactDetailEntity contactFromDb = clientContactDetailRepository.findOne(clientContact.getClcId());
			ClientContactDetailDTO contactDbDto = contactDetailsMapper.asClientContactDetailDTO(contactFromDb);
			
			if (clientContact != null && contactDbDto != null) {
				boolean hasCountryDb = StringUtils.isNotBlank(contactDbDto.getCountry());
				boolean blankedCountry = StringUtils.isBlank(clientContact.getCountry());
				boolean countryEqual = StringUtils.equalsAnyIgnoreCase(contactDbDto.getCountry(), clientContact.getCountry());
				boolean dateOfChangeNull = CalendarUtils.isNull(clientContact.getDateOfCountryChange());

				if (hasCountryDb && dateOfChangeNull && (blankedCountry || !countryEqual)) {
					errors.add(MANDATORY_COUNTRY_DATE_OF_CHANGE);
				}
			}
		}


		return errors;

	}

}
