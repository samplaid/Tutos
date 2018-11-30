package lu.wealins.liability.services.core.validation.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.exceptions.AgentCreationException;
import lu.wealins.liability.services.core.business.exceptions.ReportExceptionHelper;
import lu.wealins.liability.services.core.validation.AgentContactValidationService;
import lu.wealins.common.dto.liability.services.AgentContactBasicDTO;

/**
 * The {@link AgentContactValidationService} implementation class.
 * 
 * @author oro
 *
 */
@Service
public class AgentContactValidationServiceImpl implements AgentContactValidationService {

	/**
	 * {@inheritDoc} <br>
	 * The agent should not has more than one <i>Contact of Commission payment</i> and <i>Contact of CPS</i>. <br>
	 * This method throws an error if one of these conditions is not met.
	 * 
	 * @throws AgentCreationException if one of these conditions is not met.
	 */
	@Override
	public void validateContactFunction(String agentOwnerId, Collection<? extends AgentContactBasicDTO> contacts) {
		Supplier<Stream<? extends AgentContactBasicDTO>> supplier = () -> contacts.stream();

		if (supplier.get().anyMatch((ac) -> StringUtils.isEmpty(ac.getContactFunction()))) {
			ReportExceptionHelper.throwIfErrorsIsNotEmpty(Arrays.asList("The agent with id = " + agentOwnerId + " contains a contact with an empty function."),
					AgentCreationException.class);
		}

		Stream<? extends AgentContactBasicDTO> filtered = supplier.get().filter((ac) -> ac != null && "COMMISSI".equals(ac.getContactFunction()) && "1".equals(ac.getStatus()));

		if (filtered.count() > 1) {
			ReportExceptionHelper.throwIfErrorsIsNotEmpty(
					Arrays.asList("The agent with id = " + agentOwnerId + " must not contain more than one active contact with 'Commission' function."), AgentCreationException.class);
		}

		filtered = supplier.get().filter((ac) -> ac != null && "CPS".equals(ac.getContactFunction()) && "1".equals(ac.getStatus()));

		if (filtered.count() > 1) {
			ReportExceptionHelper.throwIfErrorsIsNotEmpty(
					Arrays.asList("The agent with id = " + agentOwnerId + " must not contain more than one active contact with 'Contact of CPS' function."), AgentCreationException.class);
		}

	}

}
