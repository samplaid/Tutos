package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyEndorsementDTO;
import lu.wealins.common.dto.liability.services.PolicyEventDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyEndorsementStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyEndorsementType;
import lu.wealins.common.dto.liability.services.enums.PolicyEventType;
import lu.wealins.liability.services.core.business.PolicyEndorsementService;
import lu.wealins.liability.services.core.business.PolicyEventService;
import lu.wealins.liability.services.core.persistence.entity.PolicyEndorsementEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyEndorsementRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.HistoricManager;

@Service
public class PolicyEndorsementServiceImpl implements PolicyEndorsementService {

	@Autowired
	private PolicyEndorsementRepository policyEndorsementRepository;
	@Autowired
	private PolicyEventService policyEventService;
	@Autowired
	private HistoricManager historicManager;
	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public Collection<PolicyEndorsementEntity> createOrUpdatePolicyEndorsement(long pevId, String polId, PolicyEndorsementType type, String valueBefore, String valueAfter, String workflowItemId,
			Integer... coverages) {
		Assert.notNull(polId);
		Assert.notNull(type);
		Assert.notNull(workflowItemId);
		Assert.notNull(coverages);

		Collection<PolicyEndorsementEntity> policyEndorsements = new HashSet<>();
		for (Integer coverage : coverages) {
			PolicyEndorsementEntity policyEndorsement = policyEndorsementRepository.findBycreatedProcessAndTypeAndCoverage(workflowItemId, type.getType(), coverage);

			if (policyEndorsement == null) {
				policyEndorsement = createPolicyEndorsement(pevId, polId, type, coverage);
			}

			policyEndorsement.setValueBefore(valueBefore);
			policyEndorsement.setValueAfter(valueAfter);

			if (historicManager.historize(policyEndorsement)) {
				if (StringUtils.isNotBlank(workflowItemId)) {
					policyEndorsement.setCreatedProcess(workflowItemId);
				}
				if (policyEndorsement.getPenId() == 0) {
					policyEndorsement.setPenId(policyEndorsementRepository.findMaxPenId() + 1);
				}

				policyEndorsement = policyEndorsementRepository.save(policyEndorsement);
			}

			policyEndorsements.add(policyEndorsement);
		}


		return policyEndorsements;
	}

	private PolicyEndorsementEntity createPolicyEndorsement(long pevId, String polId, PolicyEndorsementType type, Integer coverage) {
		PolicyEndorsementEntity policyEndorsement = new PolicyEndorsementEntity();
		Integer maxEndorsementNo = policyEndorsementRepository.findMaxEndorsementNo(polId);
		if (maxEndorsementNo == null) {
			maxEndorsementNo = Integer.valueOf(0);
		}
		policyEndorsement.setPevId(pevId);
		policyEndorsement.setEndorsementNo(maxEndorsementNo + 1);
		policyEndorsement.setType(type.getType());
		policyEndorsement.setEffectiveDate(calendarUtils.createDefaultDate());
		policyEndorsement.setStatus(PolicyEndorsementStatus.ACTIVE.getStatus());
		policyEndorsement.setPolId(polId);
		policyEndorsement.setCoverage(coverage);

		return policyEndorsement;
	}

	@Override
	public String getValueAfter(PolicyEventType eventType, String workflowItemId, Integer... coverages) {
		return getValues(eventType, workflowItemId, x -> x.getValueAfter(), coverages);
	}

	@Override
	public String getValueBefore(PolicyEventType eventType, String workflowItemId, Integer... coverages) {
		return getValues(eventType, workflowItemId, x -> x.getValueBefore(), coverages);
	}

	private String getValues(PolicyEventType eventType, String workflowItemId, Function<? super PolicyEndorsementDTO, ? extends String> mapper, Integer... coverages) {
		Collection<PolicyEndorsementDTO> policyEndorsements = getPolicyEndorsements(eventType, workflowItemId, coverages);

		if (CollectionUtils.isEmpty(policyEndorsements)) {
			return null;
		}

		Collection<String> values = policyEndorsements.stream().map(mapper).distinct().collect(Collectors.toSet());

		if (values.size() > 1) {
			throw new IllegalStateException("There is more than one policy endorsements linked to the event type " + eventType.getType() + " with the created process " + workflowItemId
					+ " having different 'valueAfter' values.");
		}

		return values.iterator().next();
	}

	@Override
	public Collection<PolicyEndorsementDTO> getPolicyEndorsements(PolicyEventType eventType, String workflowItemId, Integer... coverages) {
		PolicyEventDTO policyEvent = policyEventService.getPolicyEvent(eventType, workflowItemId);
		if (policyEvent == null) {
			return new ArrayList<>();
		}

		return policyEvent.getPolicyEndorsements().stream().filter(x -> Arrays.asList(coverages).contains(x.getCoverage())).collect(Collectors.toList());

	}

	@Override
	public Collection<PolicyEndorsementDTO> getChangedPolicyEndorsements(PolicyEventType eventType, String workflowItemId, Integer... coverages) {
		return getPolicyEndorsements(eventType, workflowItemId, coverages)
			.stream()
			.filter(this::hasChanged)
				.collect(Collectors.toList());
	}

	private boolean hasChanged(PolicyEndorsementDTO endorsement) {
		return !StringUtils.equals(endorsement.getValueAfter(), endorsement.getValueBefore());
	}
}
