package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyEventDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyEventStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyEventType;
import lu.wealins.liability.services.core.business.PolicyEndorsementService;
import lu.wealins.liability.services.core.business.PolicyEventService;
import lu.wealins.liability.services.core.mapper.PolicyEventMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyEndorsementEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEventEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyEndorsementRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyEventRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.HistoricManager;

@Service
public class PolicyEventServiceImpl implements PolicyEventService {

	@Autowired
	private PolicyEventRepository policyEventRepository;
	@Autowired
	private PolicyEndorsementRepository policyEndorsementRepository;
	@Autowired
	private HistoricManager historicManager;
	@Autowired
	private CalendarUtils calendarUtils;
	@Autowired
	private PolicyEventMapper policyEventMapper;

	@Autowired
	private PolicyEndorsementService policyEndorsementService;


	@Override
	public PolicyEventDTO getPolicyEvent(PolicyEventType eventType, String workflowItemId) {
		return policyEventMapper.asPolicyEventDTO(policyEventRepository.findBycreatedProcessAndEvent(workflowItemId, eventType.getType()));
	}

	@Override
	public PolicyEventDTO createOrUpdatePolicyEvent(String polId, PolicyEventType eventType, Date effectiveDate, String valueBefore, String valueAfter, String workflowItemId, Integer... coverage) {
		Assert.notNull(polId);
		Assert.notNull(eventType);
		Assert.notNull(effectiveDate);
		Assert.notNull(workflowItemId);

		PolicyEventEntity policyEvent = policyEventRepository.findBycreatedProcessAndEvent(workflowItemId, eventType.getType());

		if (policyEvent == null) {
			policyEvent = createPolicyEvent(polId, eventType);
		}

		policyEvent.setEffectiveDate(effectiveDate);

		if (historicManager.historize(policyEvent)) {
			if (StringUtils.isNotBlank(workflowItemId)) {
				policyEvent.setCreatedProcess(workflowItemId);
			}
			policyEvent.setSystemDate(new Date());
			if (policyEvent.getPevId() == 0) {
				policyEvent.setPevId(policyEventRepository.findMaxPevId() + 1);
			}

			policyEvent = policyEventRepository.save(policyEvent);
		}

		Collection<PolicyEndorsementEntity> policyEndorsements = policyEndorsementService.createOrUpdatePolicyEndorsement(policyEvent.getPevId(), polId, eventType.getEndorsementType(), valueBefore,
				valueAfter,
				workflowItemId, coverage);

		policyEndorsementRepository.save(policyEndorsements);

		policyEvent.setPolicyEndorsements(policyEndorsements);

		return policyEventMapper.asPolicyEventDTO(policyEvent);
	}

	private PolicyEventEntity createPolicyEvent(String polId, PolicyEventType eventType) {
		PolicyEventEntity policyEvent = new PolicyEventEntity();

		policyEvent.setEvent(eventType.getType());
		policyEvent.setCoverage(0);
		policyEvent.setPolId(polId);
		policyEvent.setPolicy(polId);
		policyEvent.setTransaction0(Long.valueOf(0));
		policyEvent.setActualEventDate(calendarUtils.createDefaultDate());
		policyEvent.setOccurrence(Integer.valueOf(0));
		policyEvent.setStatus(PolicyEventStatus.ACTIVE.getStatus());

		return policyEvent;
	}

}
