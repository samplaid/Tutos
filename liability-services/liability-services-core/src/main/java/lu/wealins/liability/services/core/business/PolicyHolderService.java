package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface PolicyHolderService {

	/**
	 * Get the policy holders linked to a policy.
	 * 
	 * @param policy The policy.
	 * @return The policy holders.
	 */
	Collection<PolicyHolderDTO> getPolicyHolders(PolicyEntity policy);

	/**
	 * Retrieve the dead holders among the relationships provided
	 * 
	 * @param cliPolRelationships the relationships to filter
	 * @return a {@link Collection} of {@link PolicyHolderDTO}
	 */
	Collection<PolicyHolderDTO> getDeadHolders(Collection<CliPolRelationshipEntity> cliPolRelationships);

	Collection<PolicyHolderDTO> getPolicyHolders(String workflowItemId);

	Collection<PolicyHolderLiteDTO> getPolicyHolderLites(PolicyEntity policy);

	/**
	 * Get portfolio amount expressed in the given currency.
	 * 
	 * @param polycHolder policy holder id.
	 * @param date The portfolio date.
	 * @param currency The currency.
	 * @return The portfolio amount.
	 */
	BigDecimal getPortfolioAmount(Integer polycHolder, Date date, String currency);

	void updatePolicyHolders(Collection<PolicyHolderDTO> policyHolders, String policyId, Date activeDate, String workflowItemId);
}
