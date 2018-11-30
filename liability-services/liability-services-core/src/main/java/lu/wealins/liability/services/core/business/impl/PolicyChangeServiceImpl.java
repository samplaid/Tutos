package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;
import lu.wealins.common.dto.liability.services.enums.PolicyChangeStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyChangeType;
import lu.wealins.liability.services.core.business.PolicyChangeService;
import lu.wealins.liability.services.core.mapper.PolicyChangeMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyChangeEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyChangeRepository;

@Service
public class PolicyChangeServiceImpl implements PolicyChangeService {

	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "The workflow item id can't be null";
	private static final String POL_ID_CANNOT_BE_NULL = "The policy id can't be null";

	@Autowired
	private PolicyChangeRepository repository;

	@Autowired
	private PolicyChangeMapper mapper;

	@Override
	public Boolean saveChanges(PolicyChangeRequest policyChangeRequest) {
		Assert.notNull(policyChangeRequest);
		return saveChanges(policyChangeRequest.getType(), policyChangeRequest.getPolicyId(), policyChangeRequest.getWorkflowItemId(), policyChangeRequest.getDateOfChange(), policyChangeRequest.getStatus());
	}

	@Transactional
	private Boolean saveChanges(PolicyChangeType type, String policyId, Integer workflowItemId, Date dateOfChange, PolicyChangeStatus status) {
		Optional<PolicyChangeEntity> optionalChanges = repository.findByWorkflowItemId(workflowItemId);

		PolicyChangeEntity entity = null;

		if (optionalChanges.isPresent()) {
			entity = optionalChanges.get();
			entity.setDateOfChange(dateOfChange);
			entity.setStatus(status);
		} else {
			entity = new PolicyChangeEntity();
			entity.setDateOfChange(dateOfChange);
			entity.setStatus(status);
			entity.setType(type);
			entity.setPolicyId(policyId);
			entity.setFkPoliciespolId(policyId);
			entity.setWorkflowItemId(workflowItemId);
		}

		repository.save(entity);
		return Boolean.TRUE;
	}
	
	@Override
	@Transactional
	public void cancelByworkflowItemId(Integer workflowItemId) {		
		Optional<PolicyChangeEntity> optionalChanges = repository.findByWorkflowItemId(workflowItemId);
		if (optionalChanges.isPresent()) {
			PolicyChangeEntity entity = optionalChanges.get();
			entity.setStatus(PolicyChangeStatus.TERMINATED);
			entity.setCancelDate(new Date());
			repository.save(entity);
		}		
	}

	@Override
	public PolicyChangeDTO getPolicyChange(Integer workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		PolicyChangeEntity entity = repository.findByWorkflowItemId(workflowItemId).orElse(null);

		return mapper.asPolicyChangeDTO(entity);
	}
	
	@Override
	public Collection<PolicyChangeDTO> getPolicyChanges(String polId){
		Assert.notNull(polId, POL_ID_CANNOT_BE_NULL);
		
		Collection<PolicyChangeEntity> entities = repository.findByPolicyIdOrderByDateOfChangeDesc(polId);
		return mapper.asPolicyChangeDTOs(entities);
	}
}
