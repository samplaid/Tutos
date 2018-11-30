/**
 * 
 */
package lu.wealins.liability.services.core.validation.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.persistence.repository.AgentContactRepository;
import lu.wealins.liability.services.core.validation.AgentValidationService;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.enums.AgentContactFunction;

/**
 * An implementation class for agent validation
 * 
 * @author oro
 *
 */
@Service
public class AgentValidationServiceImpl implements AgentValidationService {

	@Autowired
	private AgentContactRepository agentContactRepository;

	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.core.validation.AgentValidationService#validateCentralizedCommunication(lu.wealins.common.dto.liability.services.AgentDTO)
	 */
	@Override
	public List<String> validateOnlyOneActiveCpsContact(AgentDTO agent) {
		Assert.notNull(agent);

		List<String> errors = new ArrayList<>();

		if (StringUtils.isNotBlank(agent.getAgtId()) && BooleanUtils.isTrue(agent.getCentralizedCommunication())) {
			long count = agentContactRepository.countHaveActiveFunction(agent.getAgtId(), AgentContactFunction.CPS.getCode());

			if (count != 1) {
				errors.add(
						"The centralized communication is checked. The agent with name " + agent.getName() + " (" + agent.getAgtId()
								+ ") must have one and only one active contact with a CPS function.");
			}
		}

		return errors;
	}

}
