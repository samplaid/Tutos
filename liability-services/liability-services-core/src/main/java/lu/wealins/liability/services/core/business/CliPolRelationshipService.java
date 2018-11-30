package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;

public interface CliPolRelationshipService {

	/**
	 * Save beneficiary.
	 * 
	 * @param beneficiary The beneficiary.
	 * @param beneficiaryType The beneficiary type (death or life)
	 * @param policyId The policy Id.
	 * @param activeDate The active date.
	 * 
	 * 1. Convert CliPolRelationship entities from BeneficiaryDTO.
	 * 2. Get disabled cliPolRelationship entities.
	 * 3. Save new relations and disable the old ones.
	 */
	void saveBeneficiary(BeneficiaryDTO beneficiary, CliPolRelationshipType beneficiaryType, String policyId, Date activeDate);

	void saveBeneficiary(BeneficiaryDTO beneficiary, CliPolRelationshipType beneficiaryType, String policyId, Date activeDate, String workflowItemId);

	void savePolicyHolder(PolicyHolderDTO policyHolder, String policyId, Date paymentDt);

	void savePolicyHolder(PolicyHolderDTO policyHolder, String policyId, Date paymentDt, String workflowItemId);

	void saveOtherClient(OtherClientDTO otherClient, String policyId, Date paymentDt);

	void disableCliPolRelationship(String policyId, List<Integer> excludedClientIds, List<CliPolRelationshipType> types);

	void disableCliPolRelationship(String policyId, List<CliPolRelationshipType> types, Date disabledDate);

	void enableCliPolRelationships(Integer workflowItemId, List<CliPolRelationshipType> excludedSubRoles);

	void disableCliPolRelationship(String policyId, List<Integer> excludedClientIds, List<CliPolRelationshipType> types, Date disabledDate);

	/**
	 * Return if the death can be notify for a given client
	 * 	-check if there is no police settlement to perform before 
	 * 
	 * @param clientId - the client Id
	 * @return a boolean
	 */
	boolean canClientDeathBeNotified(Integer clientId);

	boolean isActive(CliPolRelationshipEntity cliPolRelationship);
	
	boolean hasType(CliPolRelationshipType type, CliPolRelationshipEntity cliPolRelationship);

	boolean isActiveWithType(CliPolRelationshipEntity cliPolRelationship, CliPolRelationshipType type);

	Collection<CliPolRelationshipEntity> getActiveCliPolRelationshipEntitiesWithType(Collection<CliPolRelationshipEntity> CliPolRelationshipEntities, CliPolRelationshipType type);

	/**
	 * Close the client policy relationship with the notified date of death. The closure will take into account for the relation types {@code OWNER(1), JOINT_OWNER(2), ADDN_OWNER(3)}.
	 * 
	 * @param clientId the client id
	 * @param dateDeathNotified the notified date of death.
	 */
	void closeClientPolicyRelationShip(Integer clientId, Date dateDeathNotified);
	
	boolean hasPolicyHolderRole(CliPolRelationshipEntity cliPol);

	boolean hasBeneficiaryRole(CliPolRelationshipEntity cliPol);

	boolean hasInsuredRole(CliPolRelationshipEntity cliPol);

	void saveOtherClient(OtherClientDTO otherClient, String policyId, Date activeDate, String workflowItemId);

	void deleteDisabledWithModifyProcess(String workflowItemId);

	List<CliPolRelationshipEntity> getOtherCliPolRelationships(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities, List<CliPolRelationshipType> excludedPolicyHolderRelations,
			List<CliPolRelationshipType> excludedBeneficiaryRelations, List<CliPolRelationshipType> excludedInsuredRelations);

	List<CliPolRelationshipEntity> getOtherCliPolRelationships(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	/**
	 * Find all the {@link CliPolRelationshipEntity} bound to a policy
	 * 
	 * @param policyId the policy id
	 * @return a list of {@link CliPolRelationshipEntity}
	 */
	List<CliPolRelationshipEntity> findByPolicyId(String policyId);
}
