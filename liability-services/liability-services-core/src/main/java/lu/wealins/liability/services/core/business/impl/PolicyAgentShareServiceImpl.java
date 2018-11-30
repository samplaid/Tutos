package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.liability.services.core.business.PolicyAgentShareService;
import lu.wealins.liability.services.core.business.PolicyChangeService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.mapper.PolicyAgentShareMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyAgentShareRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyCoverageRepository;
import lu.wealins.liability.services.core.persistence.specification.PolicyAgentShareSpecification;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.HistoricManager;

@Service
public class PolicyAgentShareServiceImpl implements PolicyAgentShareService {

	@Autowired
	private PolicyAgentShareMapper policyAgentShareMapper;

	@Autowired
	private PolicyAgentShareRepository policyAgentShareRepository;

	@Autowired
	private PolicyCoverageRepository policyCoverageRepository;

	@Autowired
	private HistoricManager historicManager;

	@Autowired
	private PolicyChangeService policyChangeService;

	@Autowired
	private CalendarUtils calendarUtils;

	@Autowired
	private PolicyService policyservice;

	private final static int ACTIVE_STATUS = 1;

	private static final int PRIMARY_AGENT = 1;

	@Override
	public Collection<PolicyAgentShareDTO> getCountryManagers(PolicyEntity policyEntity) {
		return getPolicyAgentShares(policyEntity, PolicyAgentShareType.FISA_SALES_PERSON.getType(), AgentCategory.WEALINS_SALES_PERSON.getCategory(), PolicyAgentShareStatus.ACTIVE.getStatus());
	}

	@Override
	public PolicyAgentShareDTO getBusinessIntroducer(PolicyEntity policyEntity) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(getSinglePolicyAgentShare(policyEntity, PolicyAgentShareType.ADDL_ADVISOR_FEE.getType(), null, PolicyAgentShareStatus.ACTIVE.getStatus()));
	}

	@Override
	public PolicyAgentShareDTO getBusinessIntroducer(String modifyProcess) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(lu.wealins.common.collection.CollectionUtils.extractSingletonOrNull(
				policyAgentShareRepository.findByModifyProcessAndTypeAndStatus(modifyProcess, PolicyAgentShareType.ADDL_ADVISOR_FEE.getType(),
						PolicyAgentShareStatus.AMENDMENT.getStatus())));
	}

	@Override
	public PolicyAgentShareDTO getBroker(String modifyProcess) {

		Collection<PolicyAgentShareEntity> policyAgentShareEntities = policyAgentShareRepository.findByModifyProcessAndTypeAndCategory(modifyProcess,
				PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.BROKER.getCategory());

		if (CollectionUtils.isEmpty(policyAgentShareEntities)) {
			return null;
		}

		return policyAgentShareMapper.asPolicyAgentShareDTO(policyAgentShareEntities.stream().findFirst().orElse(null));
	}

	@Override
	public void applyChange(Integer workflowItemId, Date activeDate) {
		Assert.notNull(workflowItemId);
		Assert.notNull(activeDate);
		String modifyProcess = workflowItemId + "";
		PolicyChangeDTO policyChange = policyChangeService.getPolicyChange(workflowItemId);
		Assert.notNull(policyChange, "There is no policy change linked to the workflow item id " + workflowItemId + ".");

		Collection<PolicyAgentShareEntity> policyAgentShares = policyAgentShareRepository.findByModifyProcessAndStatus(modifyProcess, PolicyAgentShareStatus.AMENDMENT.getStatus());
		policyAgentShares.forEach(x -> enable(x, activeDate));
		policyAgentShareRepository.save(policyAgentShares);
	}

	public void enable(PolicyAgentShareEntity policyAgentShare, Date activeDate) {
		Assert.notNull(policyAgentShare);
		Assert.notNull(activeDate);

		policyAgentShare.setStatus(PolicyAgentShareStatus.ACTIVE.getStatus());
		policyAgentShare.setActiveDate(activeDate);
		policyAgentShare.setEndDate(calendarUtils.createDefaultDate());
	}


	@Override
	public Collection<PolicyAgentShareEntity> getPolicyAgentShareEntities(PolicyAgentShareType type, AgentCategory category, String modifyProcess) {

		return policyAgentShareRepository.findByModifyProcessAndTypeAndCategory(modifyProcess, type.getType(), category.getCategory());
	}

	@Override
	public PolicyAgentShareDTO getBrokerContact(String modifyProcess) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(lu.wealins.common.collection.CollectionUtils.extractSingletonOrNull(
				policyAgentShareRepository.findByModifyProcessAndTypeAndCategory(modifyProcess, PolicyAgentShareType.SALES_REPRESENTATIVE.getType(),
						AgentCategory.PERSON_CONTACT.getCategory())));
	}

	@Override
	public PolicyAgentShareDTO getSubBroker(String modifyProcess) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(lu.wealins.common.collection.CollectionUtils.extractSingletonOrNull(
				policyAgentShareRepository.findByModifyProcessAndTypeAndCategory(modifyProcess, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.SUB_BROKER.getCategory())));
	}

	@Override
	public PolicyAgentShareDTO getBroker(PolicyEntity policyEntity) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(getSinglePolicyAgentShare(getPolicyAgentShareEntitiesForBroker(policyEntity)));
	}

	@Override
	public PolicyAgentShareDTO getPreviousBroker(PolicyEntity policyEntity, Date endDate) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(getSinglePolicyAgentShare(getPolicyAgentShareEntitiesForBroker(policyEntity, endDate)));
	}

	@Override
	public Collection<PolicyAgentShareEntity> getPolicyAgentShareEntitiesForBroker(PolicyEntity policyEntity) {
		return getPolicyAgentShareEntities(policyEntity, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.BROKER.getCategory(), PolicyAgentShareStatus.ACTIVE.getStatus(), null);
	}

	public Collection<PolicyAgentShareEntity> getPolicyAgentShareEntitiesForBroker(PolicyEntity policyEntity, Date endDate) {
		return getPolicyAgentShareEntities(policyEntity, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.BROKER.getCategory(), PolicyAgentShareStatus.INACTIVE.getStatus(), endDate);
	}

	@Override
	public PolicyAgentShareDTO getSubBroker(PolicyEntity policyEntity) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(getSubBrokerEntity(policyEntity));
	}

	@Override
	public PolicyAgentShareEntity getSubBrokerEntity(PolicyEntity policyEntity) {
		return getSinglePolicyAgentShare(policyEntity, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.SUB_BROKER.getCategory(), PolicyAgentShareStatus.ACTIVE.getStatus());
	}
	
	@Override
	public PolicyAgentShareDTO getPreviousSubBroker(PolicyEntity policyEntity, Date endDate) {
		PolicyAgentShareEntity singleSubBroker = getSinglePolicyAgentShare(getPolicyAgentShareEntities(policyEntity, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.SUB_BROKER.getCategory(), PolicyAgentShareStatus.INACTIVE.getStatus(), endDate));
		return policyAgentShareMapper.asPolicyAgentShareDTO(singleSubBroker);
	}

	@Override
	public PolicyAgentShareDTO getBrokerContact(PolicyEntity policyEntity) {
		return policyAgentShareMapper.asPolicyAgentShareDTO(getBrokerContactEntity(policyEntity));
	}

	public PolicyAgentShareEntity getBrokerContactEntity(PolicyEntity policyEntity) {
		return getSinglePolicyAgentShare(policyEntity, PolicyAgentShareType.SALES_REPRESENTATIVE.getType(), AgentCategory.PERSON_CONTACT.getCategory(), PolicyAgentShareStatus.ACTIVE.getStatus());
	}
	
	@Override
	public PolicyAgentShareDTO getPreviousBrokerContact(PolicyEntity policyEntity, Date endDate) {
		PolicyAgentShareEntity singleSubBroker = getSinglePolicyAgentShare(getPolicyAgentShareEntities(policyEntity, PolicyAgentShareType.SALES_REPRESENTATIVE.getType(), AgentCategory.PERSON_CONTACT.getCategory(), PolicyAgentShareStatus.INACTIVE.getStatus(), endDate));
		return policyAgentShareMapper.asPolicyAgentShareDTO(singleSubBroker);
	}

	@Override
	public PolicyAgentShareDTO getBrokerEntryFees(String polId) {
		Collection<PolicyAgentShareDTO> policyAgentShares = getPolicyAgentShares(polId, PolicyAgentShareType.INITIAL_COMM_FEE.getType(), AgentCategory.BROKER.getCategory(), 1, null);

		if (CollectionUtils.isEmpty(policyAgentShares)) {
			return null;
		}

		return policyAgentShares.iterator().next();
	}

	@Override
	public PolicyAgentShareDTO getLastOperationBrokerEntryFees(String polId) {
		Collection<PolicyAgentShareDTO> policyAgentShares = getPolicyAgentShares(polId, PolicyAgentShareType.INITIAL_COMM_FEE.getType(), AgentCategory.BROKER.getCategory(), null, null);
		
		return policyAgentShares
				.stream()
				.sorted(Comparator.comparing(PolicyAgentShareDTO::getCoverage).reversed())
				.findFirst()
				.orElseThrow(() -> new IllegalStateException(String.format("No broker entry fees found for policy : %s", polId)));
	}

	@Override
	public PolicyAgentShareDTO getLastOperationEntryFees(String polId, Integer coverage) {
		return getLastOperationFees(polId, PolicyAgentShareType.INITIAL_COMM_FEE, coverage);
	}

	private PolicyAgentShareDTO getLastOperationFees(String polId, PolicyAgentShareType agentType, Integer coverage) {
		Collection<PolicyAgentShareDTO> policyAgentShares = getPolicyAgentShares(polId, agentType.getType(), null, coverage, PRIMARY_AGENT);

		return policyAgentShares
				.stream()
				.sorted(Comparator.comparing(PolicyAgentShareDTO::getCoverage).reversed())
				.findFirst()
				.orElse(null);
	}

	@Override
	public Collection<PolicyAgentShareDTO> getPolicyAgentShares(String polId, Integer type, String agentCategory, Integer coverage, Integer primary) {
		Specifications<PolicyAgentShareEntity> policyAgentShareSpecifications = Specifications.where(PolicyAgentShareSpecification.initial());

		if (polId != null) {
			policyAgentShareSpecifications = policyAgentShareSpecifications.and(PolicyAgentShareSpecification.withPolId(polId));
		}
		if (type != null) {
			policyAgentShareSpecifications = policyAgentShareSpecifications.and(PolicyAgentShareSpecification.withType(type));
		}
		if (agentCategory != null) {
			policyAgentShareSpecifications = policyAgentShareSpecifications.and(PolicyAgentShareSpecification.withAgentCategory(agentCategory));
		}
		if (coverage != null) {
			policyAgentShareSpecifications = policyAgentShareSpecifications.and(PolicyAgentShareSpecification.withCoverage(coverage));
		}
		if (primary != null) {
			policyAgentShareSpecifications = policyAgentShareSpecifications.and(PolicyAgentShareSpecification.withPrimary(primary));
		}

		policyAgentShareSpecifications = policyAgentShareSpecifications.and(PolicyAgentShareSpecification.withStatus(ACTIVE_STATUS));

		return policyAgentShareMapper.asPolicyAgentShareDTOs(policyAgentShareRepository.findAll(policyAgentShareSpecifications));
	}

	private PolicyAgentShareEntity getSinglePolicyAgentShare(PolicyEntity policyEntity, int type, String category, int status) {
		return getSinglePolicyAgentShare(getPolicyAgentShareEntities(policyEntity, type, category, status, null));
	}

	private PolicyAgentShareEntity getSinglePolicyAgentShare(Collection<PolicyAgentShareEntity> policyAgentShares) {

		// Retrieve the primary agent
		if (policyAgentShares.size() > 1) {
			policyAgentShares = policyAgentShares.stream().filter(x -> BooleanUtils.isTrue(x.getPrimaryAgent())).collect(Collectors.toList());
		}
		// retrieve the max coverage.
		return policyAgentShares.stream().max((pa1, pa2) -> Integer.compare(pa1.getCoverage(), pa2.getCoverage())).orElse(null);
	}

	private Collection<PolicyAgentShareDTO> getPolicyAgentShares(PolicyEntity policyEntity, int type, String category, int status) {
		return policyAgentShareMapper.asPolicyAgentShareDTOs(getPolicyAgentShareEntities(policyEntity, type, category, status, null));
	}

	@Override
	public void disable(PolicyEntity policyEntity, int type, String category, Date endDate) {

		Set<PolicyAgentShareEntity> policyAgentShares = policyEntity.getPolicyAgentShares();

		List<PolicyAgentShareEntity> filteredPolicyAgentShares = policyAgentShares.stream()
				.filter(getPolicyAgentShareByTypeAndCategoryAndStatusPredicate(type, category, PolicyAgentShareStatus.ACTIVE.getStatus(), null))
				.collect(Collectors.toList());

		filteredPolicyAgentShares.forEach(x -> {
			x.setEndDate(endDate);
			x.setStatus(PolicyAgentShareStatus.INACTIVE.getStatus());
		});

		policyAgentShareRepository.save(policyAgentShares);
	}

	private Predicate<PolicyAgentShareEntity> getPolicyAgentShareByTypeAndCategoryAndStatusPredicate(int type, String category, int status, Date endDate) {
		if (endDate != null) {
			return pas -> pas.getStatus() == status
					&& pas.getType() == type
					&& endDate != null && pas.getEndDate() != null && calendarUtils.isSameDay(endDate, pas.getEndDate())
					&& pas.getAgent() != null
					&& pas.getAgent().getAgentCategory() != null
					&& (category != null ? category.equals(pas.getAgent().getAgentCategory().getAcaId().trim()) : true);
		} else {
			return pas -> pas.getStatus() == status
					&& pas.getType() == type
					&& pas.getAgent() != null
					&& pas.getAgent().getAgentCategory() != null
					&& (category != null ? category.equals(pas.getAgent().getAgentCategory().getAcaId().trim()) : true);
		}
	}

	private Collection<PolicyAgentShareEntity> getPolicyAgentShareEntities(PolicyEntity policyEntity, int type, String category, int status, Date endDate) {
		Set<PolicyAgentShareEntity> policyAgentShares = policyEntity.getPolicyAgentShares();
		
		List<PolicyAgentShareEntity> filteredPolicyAgentShares = policyAgentShares.stream()
				.filter(getPolicyAgentShareByTypeAndCategoryAndStatusPredicate(type, category, status, endDate).and(pas -> checkPolicyCoverage(policyEntity, pas)))
				.distinct()
				.sorted((fpas1, fpas2) -> Integer.compare(fpas1.getCoverage(), fpas2.getCoverage()))
				.collect(Collectors.toList());
		
		Set<String> agentIds = new HashSet<>();
		List<PolicyAgentShareEntity> filteredUniquePolicyAgentShares = new ArrayList<PolicyAgentShareEntity>();
		for (PolicyAgentShareEntity policyAgentShare : filteredPolicyAgentShares) {
			String agentId = policyAgentShare.getAgent().getAgtId();
			if (!agentIds.contains(agentId)) {
				agentIds.add(agentId);
				filteredUniquePolicyAgentShares.add(policyAgentShare);
			}
		}
		
		return filteredUniquePolicyAgentShares;
	}

	private boolean checkPolicyCoverage(PolicyEntity policyEntity, PolicyAgentShareEntity pas) {
		PolicyCoverageEntity pce = policyCoverageRepository.findByPolicyAndCoverage(policyEntity, pas.getCoverage());
		return pce != null
				? pce.getStatus() != 5
				: false;
	}

	@Override
	public PolicyAgentShareDTO getLastOperationBrokerAdminFees(String polId) {
		return getLastOperationFees(polId, PolicyAgentShareType.ADVISOR_FEES, null);
	}

	@Override
	public void deleteWithModifyProcess(String modifyProcess) {
		policyAgentShareRepository.deleteWithModifyProcess(modifyProcess);
	}

	@Override
	public void updateBroker(PolicyAgentShareDTO policyAgentShare, String modifyProcess) {
		Assert.isTrue(policyAgentShare.getType() != null && PolicyAgentShareType.ADVISOR_FEES.getType() == policyAgentShare.getType().intValue(),
				"The broker type must be " + PolicyAgentShareType.ADVISOR_FEES.getType() + ".");
		Assert.isTrue(policyAgentShare.getAgent() != null && AgentCategory.BROKER.getCategory().equals(policyAgentShare.getAgent().getCategory()),
				"The broker category must be '" + AgentCategory.BROKER.getCategory() + "'.");

		update(policyAgentShare, modifyProcess);
	}

	@Override
	public void updateBrokerContact(PolicyAgentShareDTO policyAgentShare, String modifyProcess) {
		checkDataIntegrity(policyAgentShare, PolicyAgentShareType.SALES_REPRESENTATIVE, AgentCategory.PERSON_CONTACT, "broker contact");

		update(policyAgentShare, modifyProcess);
	}

	private void checkDataIntegrity(PolicyAgentShareDTO policyAgentShare, PolicyAgentShareType type, AgentCategory category, String agentType) {
		Assert.isTrue(policyAgentShare.getType() != null && type.getType() == policyAgentShare.getType().intValue(),
				"The " + agentType + " type must be " + type.getType() + ".");
		if (category != null) {
			Assert.isTrue(policyAgentShare.getAgent() != null && category.getCategory().equals(policyAgentShare.getAgent().getCategory()),
					"The " + agentType + " category must be '" + category.getCategory() + "'.");
		}
		// Assert.isTrue(policyAgentShare.getStatus() == PolicyAgentShareStatus.AMENDMENT.getStatus(),
		// "The " + agentType + " status must be equals to " + PolicyAgentShareStatus.AMENDMENT.getStatus() + ".");
	}

	@Override
	public void updateSubBroker(PolicyAgentShareDTO policyAgentShare, String modifyProcess) {
		checkDataIntegrity(policyAgentShare, PolicyAgentShareType.ADVISOR_FEES, AgentCategory.SUB_BROKER, "sub-broker");

		update(policyAgentShare, modifyProcess);
	}

	@Override
	public void updateBusinessIntroducer(PolicyAgentShareDTO policyAgentShare, String modifyProcess) {
		Assert.isTrue(policyAgentShare.getType() != null && PolicyAgentShareType.ADDL_ADVISOR_FEE.getType() == policyAgentShare.getType().intValue(),
				"The business introducer type must be " + PolicyAgentShareType.ADDL_ADVISOR_FEE.getType() + ".");

		checkDataIntegrity(policyAgentShare, PolicyAgentShareType.ADDL_ADVISOR_FEE, null, "business introducer");

		update(policyAgentShare, modifyProcess);
	}

	private void update(PolicyAgentShareDTO policyAgentShare, String modifyProcess) {
		PolicyAgentShareEntity policyAgentShareEntity = policyAgentShareMapper.asPolicyAgentShareEntity(policyAgentShare);

		if (historicManager.historize(policyAgentShareEntity)) {
			if (StringUtils.isNotBlank(modifyProcess)) {
				policyAgentShareEntity.setModifyProcess(modifyProcess);
				policyAgentShareEntity.setSacrificePercent(BigDecimal.ZERO);
				policyAgentShareEntity.setSacrificeAmount(BigDecimal.ZERO);
			}
			if (policyAgentShareEntity.getPasId() == null) {
				policyAgentShareEntity.setPasId(getNextId());
			}

			policyAgentShareEntity = policyAgentShareRepository.save(policyAgentShareEntity);
		}

		policyAgentShare.setPasId(policyAgentShareEntity.getPasId());
	}

	private Long getNextId() {
		return new Long(policyAgentShareRepository.findMaxPasId() + 1);
	}

	@Override
	public PolicyAgentShareDTO getPreviousBroker(String polId, Date endDate) {
		
		PolicyEntity policy = policyservice.getPolicyEntity(polId);

		return getPreviousBroker(policy, endDate);
	}

	@Override
	public PolicyAgentShareDTO getLastAdvisorFees(String polId) {
		Assert.notNull(polId, "The policy id can't be null");

		List<PolicyAgentShareEntity> agents = policyAgentShareRepository.findAdvisorFeesAgent(polId);
		
		if(CollectionUtils.isEmpty(agents)) {
			new IllegalStateException(String.format("No broker agent was found for policy %s, found %s", polId));
		}
		
		PolicyAgentShareEntity agentShareEntity = policyAgentShareRepository.findAdvisorFeesAgent(polId).iterator().next();

		return policyAgentShareMapper.asPolicyAgentShareDTO(agentShareEntity);
	}

	@Override
	public PolicyAgentShareDTO createOrUpdateAgentShare(String agentId, PolicyAgentShareType type, String polId, Integer coverage, BigDecimal numericValue) {
		Optional<PolicyAgentShareEntity> optionalPolicyAgentShare = policyAgentShareRepository.findByPolicyAndAgentAndTypeAndCoverage(polId, agentId, type.getType(), coverage);

		PolicyAgentShareEntity updatedEntity = optionalPolicyAgentShare.map(entity -> updateNumericValue(entity, numericValue))
				.orElseGet(() -> createNewPolicyAgentShare(agentId, type, polId, coverage, numericValue));

		boolean needsSave = historicManager.historize(updatedEntity);

		if (needsSave) {
			updatedEntity = policyAgentShareRepository.save(updatedEntity);
		}

		return policyAgentShareMapper.asPolicyAgentShareDTO(updatedEntity);
	}

	private PolicyAgentShareEntity updateNumericValue(PolicyAgentShareEntity entity, BigDecimal numericValue) {
		entity.setPercentage(numericValue);
		return entity;
	}

	private PolicyAgentShareEntity createNewPolicyAgentShare(String agentId, PolicyAgentShareType type, String polId, Integer coverage, BigDecimal numericValue) {
		PolicyAgentShareEntity entity = new PolicyAgentShareEntity();
		entity.setPrimaryAgent(Boolean.FALSE);
		entity.setAgentId(agentId);
		entity.setType(type.getType());
		entity.setPolId(polId);
		entity.setCoverage(coverage);
		entity.setPercentage(numericValue);
		entity.setStatus(ACTIVE_STATUS);
		entity.setPasId(getNextId());
		return entity;
	}

}
