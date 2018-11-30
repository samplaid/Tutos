package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.InsuredLiteDTO;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface InsuredService {

	/**
	 * Get insured clients linked to a policy.
	 * 
	 * @param policy The policy.
	 * @return The insured clients.
	 */
	Collection<InsuredDTO> getInsureds(PolicyEntity policy);


	/**
	 * Retrieve the dead insureds among the relationships provided
	 * 
	 * @param cliPolRelationships the relationships to filter
	 * @return a {@link Collection} of {@link InsuredDTO}
	 */
	Collection<InsuredDTO> getDeadInsureds(Collection<CliPolRelationshipEntity> cliPolRelationships);

	Collection<InsuredLiteDTO> getInsuredLites(PolicyEntity policy);

}
