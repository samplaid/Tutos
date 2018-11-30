package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface PolicyAgentShareService {

	/**
	 * Get the broker linked to the policy.
	 * 
	 * @param policyEntity The policy.
	 * @return The Broker.
	 */
	PolicyAgentShareDTO getBroker(PolicyEntity policyEntity);

	PolicyAgentShareDTO getPreviousBroker(PolicyEntity policyEntity, Date endDate);

	Collection<PolicyAgentShareEntity> getPolicyAgentShareEntitiesForBroker(PolicyEntity policyEntity);

	void disable(PolicyEntity policyEntity, int type, String category, Date endDate);

	/**
	 * Get the business introducers linked to the policy.
	 * 
	 * @param policyEntity The policy.
	 * @return The business introducers.
	 */
	PolicyAgentShareDTO getBusinessIntroducer(PolicyEntity policyEntity);

	PolicyAgentShareDTO getBusinessIntroducer(String modifyProcess);

	PolicyAgentShareDTO getBroker(String modifyProcess);

	void applyChange(Integer workflowItemId, Date activeDate);

	Collection<PolicyAgentShareEntity> getPolicyAgentShareEntities(PolicyAgentShareType type, AgentCategory category, String modifyProcess);

	PolicyAgentShareDTO getBrokerContact(String modifyProcess);

	PolicyAgentShareDTO getSubBroker(String modifyProcess);

	/**
	 * Get the country managers linked to the policy.
	 * 
	 * @param policyEntity The policy.
	 * @return The country managers.
	 */
	Collection<PolicyAgentShareDTO> getCountryManagers(PolicyEntity policyEntity);
	
	/**
	 * Get the sub broker linked to the policy.
	 * 
	 * @param policyEntity The policy.
	 * @return The Broker.
	 */
	PolicyAgentShareDTO getSubBroker(PolicyEntity policyEntity);
	
	PolicyAgentShareEntity getSubBrokerEntity(PolicyEntity policyEntity);

	/**
	 * Retrieves the contacts of the brokers
	 * @param policyEntity The policy.
	 * @return the contacts of the brokers
	 */
	PolicyAgentShareDTO getBrokerContact(PolicyEntity policyEntity);

	PolicyAgentShareEntity getBrokerContactEntity(PolicyEntity policyEntity);

	Collection<PolicyAgentShareDTO> getPolicyAgentShares(String polId, Integer type, String agentCategory, Integer coverage, Integer primary);

	PolicyAgentShareDTO getBrokerEntryFees(String polId);

	PolicyAgentShareDTO getLastOperationBrokerEntryFees(String polId);

	PolicyAgentShareDTO getLastOperationEntryFees(String polId, Integer coverage);

	PolicyAgentShareDTO getLastOperationBrokerAdminFees(String polId);

	void deleteWithModifyProcess(String modifyProcess);

	void updateBroker(PolicyAgentShareDTO policyAgentShare, String modifyProcess);

	void updateBrokerContact(PolicyAgentShareDTO policyAgentShare, String modifyProcess);

	void updateSubBroker(PolicyAgentShareDTO policyAgentShare, String modifyProcess);

	void updateBusinessIntroducer(PolicyAgentShareDTO policyAgentShare, String modifyProcess);

	PolicyAgentShareDTO getPreviousBroker(String polId, Date effectiveDate);

	PolicyAgentShareDTO getPreviousBrokerContact(PolicyEntity policyEntity, Date endDate);

	PolicyAgentShareDTO getPreviousSubBroker(PolicyEntity policyEntity, Date endDate);

	PolicyAgentShareDTO getLastAdvisorFees(String polId);

	PolicyAgentShareDTO createOrUpdateAgentShare(String agentId, PolicyAgentShareType type, String polId, Integer coverage, BigDecimal numericValue);
}
