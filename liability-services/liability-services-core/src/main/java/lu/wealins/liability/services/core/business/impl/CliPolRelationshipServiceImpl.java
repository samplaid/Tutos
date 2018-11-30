package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipStatus;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipMapper;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipWithClientRoleActivationMapper;
import lu.wealins.liability.services.core.mapper.ClientMapper;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.ClientClaimsDetailEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.repository.CliPolRelationshipRepository;
import lu.wealins.liability.services.core.persistence.repository.ClientClaimsDetailRepository;
import lu.wealins.liability.services.core.persistence.repository.ClientRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.HistoricManager;
import lu.wealins.liability.services.core.utils.PolicyUtils;
import lu.wealins.liability.services.core.utils.constantes.Constantes;

@Service
public class CliPolRelationshipServiceImpl implements CliPolRelationshipService {
	private final Logger logger = LoggerFactory.getLogger(CliPolRelationshipServiceImpl.class);

	@Autowired
	private CliPolRelationshipMapper cliPolRelationshipMapper;
	@Autowired
	private CliPolRelationshipWithClientRoleActivationMapper cliPolRelationshipWithClientRoleActivationMapper;
	@Autowired
	private CliPolRelationshipRepository cliPolRelationshipRepository;
	@Autowired
	private HistoricManager historicManager;
	@Autowired
	private CalendarUtils calendarUtils;
	@Autowired
	private ClientMapper clientMapper;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientClaimsDetailRepository clientClaimsDetailRepository;
	@Autowired
	private PolicyUtils policyUtils;

	@Override
	public void disableCliPolRelationship(String policyId, List<Integer> excludedClientIds, List<CliPolRelationshipType> types) {
		disableCliPolRelationship(policyId, excludedClientIds, types, null);
	}

	@Override
	public void disableCliPolRelationship(String policyId, List<CliPolRelationshipType> types, Date disabledDate) {
		disableCliPolRelationship(policyId, new ArrayList<>(), types, disabledDate);
	}

	@Override
	public void enableCliPolRelationships(Integer workflowItemId, List<CliPolRelationshipType> excludedSubRoles) {
		Assert.notNull(workflowItemId);

		Collection<CliPolRelationshipEntity> cliPolRelationships = cliPolRelationshipRepository.findAllByModifyProcess(workflowItemId + "");
		Set<Integer> excludedSubRoleValues = excludedSubRoles.stream().map(x -> x.getValue()).collect(Collectors.toSet());
		cliPolRelationships.removeIf(x -> excludedSubRoleValues.contains(Integer.valueOf(x.getType())));

		cliPolRelationships.forEach(x -> {
			x.setEndDate(calendarUtils.createDefaultDate());
			x.setStatus(CliPolRelationshipStatus.ACTIVE.getValue());
		});

		cliPolRelationships.forEach(x -> cliPolRelationshipRepository.save(x));
	}

	@Override
	public void disableCliPolRelationship(String policyId, List<Integer> excludedClientIds, List<CliPolRelationshipType> types, Date disabledDate) {
		Assert.notNull(policyId);
		Assert.notNull(excludedClientIds);
		Assert.notNull(types);

		List<Integer> cliPolRelationshipTypeValues = types.stream().map(x -> Integer.valueOf(x.getValue())).collect(Collectors.toList());

		Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = null;
		Date endDate = disabledDate != null ? disabledDate : new Date();
		if (CollectionUtils.isEmpty(excludedClientIds)) {
			cliPolRelationshipEntities = cliPolRelationshipRepository.findEnabledByPolicyIdAndTypes(policyId, cliPolRelationshipTypeValues, endDate);
		} else {
			cliPolRelationshipEntities = cliPolRelationshipRepository.findEnabledByPolicyIdAndNotClientIdAndTypes(policyId, excludedClientIds, cliPolRelationshipTypeValues, endDate);
		}
		logger.debug("Disable old CliPolRelationshipEntities having old clients {0}.", excludedClientIds);
		cliPolRelationshipEntities.forEach(x -> x.setEndDate(disabledDate != null ? disabledDate : x.getActiveDate()));
		cliPolRelationshipEntities.forEach(x -> save(x));
	}

	@Override
	public void saveBeneficiary(BeneficiaryDTO beneficiary, CliPolRelationshipType beneficiaryType, String policyId, Date activeDate) {

		Integer cliId = beneficiary.getCliId();
		if (cliId == null) {
			throw new IllegalStateException("Beneficiary must be linked to a client.");
		}

		// 1. Convert CliPolRelationship entities from BeneficiaryDTO.
		final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = cliPolRelationshipMapper.asCliPolRelationshipEntities(beneficiary, beneficiaryType, policyId, activeDate,
				new ArrayList<CliPolRelationshipEntity>(), false);

		// 2. Get disabled cliPolRelationship entities
		List<CliPolRelationshipEntity> disabledCliPolRelationshipEntities = getDisabledCliPolRelationshipEntities(policyId, cliId, CliPolRelationshipType.BENEFICIARY_RELATIONSHIP_TYPE_GROUP,
				cliPolRelationshipEntities);

		// 3. Save
		logger.debug("Create new CliPolRelationshipEntities.");
		cliPolRelationshipEntities.forEach(x -> save(x));
		logger.debug("Update existing CliPolRelationshipEntities.");
		disabledCliPolRelationshipEntities.forEach(x -> save(x));

	}

	@Override
	public void saveBeneficiary(BeneficiaryDTO beneficiary, CliPolRelationshipType beneficiaryType, String policyId, Date activeDate, String workflowItemId) {
		Integer cliId = beneficiary.getCliId();
		if (cliId == null) {
			throw new IllegalStateException("Beneficiary must be linked to a client.");
		}

		// 1. Convert CliPolRelationship entities from BeneficiaryDTO.
		final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = cliPolRelationshipMapper.asCliPolRelationshipEntities(beneficiary, beneficiaryType, policyId, activeDate,
				new ArrayList<CliPolRelationshipEntity>(), true);

		// 2. Disable relations
		disableCliPolRelations(activeDate, workflowItemId, cliPolRelationshipEntities);

		// 3. Save new relations
		logger.debug("Create new CliPolRelationshipEntities.");
		cliPolRelationshipEntities.forEach(x -> save(x, workflowItemId));
	}

	@Override
	public void savePolicyHolder(PolicyHolderDTO policyHolder, String policyId, Date activeDate, String workflowItemId) {
		Integer cliId = policyHolder.getCliId();
		if (cliId == null) {
			throw new IllegalStateException("Beneficiary must be linked to a client.");
		}

		// 1. Convert CliPolRelationship entities from BeneficiaryDTO.
		final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = cliPolRelationshipMapper.asCliPolRelationshipEntities(policyHolder, policyId, activeDate,
				new ArrayList<CliPolRelationshipEntity>(), true);

		// 2. Disable relations
		disableCliPolRelations(activeDate, workflowItemId, cliPolRelationshipEntities);

		// 3. Save new relations
		logger.debug("Create new CliPolRelationshipEntities.");
		cliPolRelationshipEntities.forEach(x -> save(x, workflowItemId));
	}

	private void disableCliPolRelations(Date activeDate, String workflowItemId, final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities) {
		cliPolRelationshipEntities.forEach(x -> {
			x.setStatus(CliPolRelationshipStatus.INACTIVE.getValue());
			x.setEndDate(activeDate);
			x.setModifyProcess(workflowItemId + "");
		});
	}

	@Override
	public void saveOtherClient(OtherClientDTO otherClient, String policyId, Date activeDate, String workflowItemId) {
		Integer cliId = otherClient.getCliId();
		if (cliId == null) {
			throw new IllegalStateException("Other client must be linked to a client.");
		}

		// Same behavior as the LISSIA new bussiness webservice
		if (otherClient.getTypeNumber() == null) {
			otherClient.setTypeNumber(Integer.valueOf(1));
		}

		// 1. Convert CliPolRelationship entities from otherClient.
		final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = Arrays.asList(cliPolRelationshipMapper.asCliPolRelationshipEntity(otherClient, policyId, activeDate, true));

		disableCliPolRelations(activeDate, workflowItemId, cliPolRelationshipEntities);

		// 3. Save new relations
		logger.debug("Create new CliPolRelationshipEntities.");
		cliPolRelationshipEntities.forEach(x -> save(x, workflowItemId));
	}

	@Override
	public void saveOtherClient(OtherClientDTO otherClient, String policyId, Date paymentDt) {
		Integer cliId = otherClient.getCliId();
		if (cliId == null) {
			throw new IllegalStateException("Other client must be linked to a client.");
		}

		// Same behavior as the LISSIA new bussiness webservice
		if (otherClient.getTypeNumber() == null) {
			otherClient.setTypeNumber(Integer.valueOf(1));
		}

		// 1. Convert CliPolRelationship entities from policyHolderDTO.
		final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = Arrays.asList(cliPolRelationshipMapper.asCliPolRelationshipEntity(otherClient, policyId, paymentDt, false));

		// 2. Get disabled cliPolRelationship entities
		List<CliPolRelationshipEntity> disabledCliPolRelationshipEntities = getDisabledCliPolRelationshipEntities(policyId, cliId, CliPolRelationshipType.OTHER_CLIENT_RELATIONSHIP_TYPE_GROUP,
				cliPolRelationshipEntities);

		// 3. Save
		logger.debug("Create new CliPolRelationshipEntities.");
		cliPolRelationshipEntities.forEach(x -> save(x));
		logger.debug("Update existing CliPolRelationshipEntities.");
		disabledCliPolRelationshipEntities.forEach(x -> save(x));

	}

	@Override
	public void savePolicyHolder(PolicyHolderDTO policyHolder, String policyId, Date paymentDt) {

		Integer cliId = policyHolder.getCliId();
		if (cliId == null) {
			throw new IllegalStateException("Policy holder must be linked to a client.");
		}

		// 1. Convert CliPolRelationship entities from policyHolderDTO.
		final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = cliPolRelationshipMapper.asCliPolRelationshipEntities(policyHolder, policyId, paymentDt,
				new ArrayList<CliPolRelationshipEntity>(), false);

		// 2. Get disabled cliPolRelationship entities
		List<CliPolRelationshipEntity> disabledCliPolRelationshipEntities = getDisabledCliPolRelationshipEntities(policyId, cliId, CliPolRelationshipType.POLICY_HOLDER_RELATIONSHIP_TYPE_GROUP,
				cliPolRelationshipEntities);

		// 3. Save
		logger.debug("Create new CliPolRelationshipEntities.");
		cliPolRelationshipEntities.forEach(x -> save(x));
		logger.debug("Update existing CliPolRelationshipEntities.");
		disabledCliPolRelationshipEntities.forEach(x -> save(x));

	}

	private List<CliPolRelationshipEntity> getDisabledCliPolRelationshipEntities(String policyId, Integer cliId, List<CliPolRelationshipType> types,
			Collection<CliPolRelationshipEntity> cliPolRelationshipEntities) {

		Collection<CliPolRelationshipEntity> cliPolRelationshipEntitiesFromDB = getCliPolRelationshipEntitiesFromDB(policyId, cliId, types);

		List<CliPolRelationshipEntity> disabledCliPolRelationshipEntities = cliPolRelationshipEntitiesFromDB.stream()
				.filter(x -> !cliPolRelationshipEntities.stream().anyMatch(y -> x.getCprId().equals(y.getCprId()))).collect(Collectors.toList());

		disabledCliPolRelationshipEntities.forEach(x -> x.setEndDate(x.getActiveDate()));

		return disabledCliPolRelationshipEntities;
	}

	private Collection<CliPolRelationshipEntity> getCliPolRelationshipEntitiesFromDB(String policyId, Integer cliId, List<CliPolRelationshipType> types) {
		List<Integer> beneficiaryRelationTypes = types.stream().map(x -> Integer.valueOf(x.getValue())).collect(Collectors.toList());

		return cliPolRelationshipRepository.findAllByPolicyIdAnClientIdAndTypes(policyId, cliId, beneficiaryRelationTypes);
	}

	void updateBeneficiaryCliPolRelationship(CliPolRelationshipEntity beneficiaryCliPolRelationshipEntityFromDB, CliPolRelationshipEntity beneficiaryCliPolRelationshipEntity) {
		if (beneficiaryCliPolRelationshipEntityFromDB == null) {
			return;
		}
		beneficiaryCliPolRelationshipEntityFromDB.setTypeNumber(beneficiaryCliPolRelationshipEntity.getTypeNumber());
		beneficiaryCliPolRelationshipEntityFromDB.setPercentageSplit(beneficiaryCliPolRelationshipEntity.getPercentageSplit());
	}

	private void save(CliPolRelationshipEntity entity) {
		save(entity, null);
	}

	private void save(CliPolRelationshipEntity entity, String modifyProcess) {
		if (historicManager.historize(entity)) {
			if (StringUtils.isNotBlank(modifyProcess)) {
				entity.setModifyProcess(modifyProcess);
			}
			logger.debug("Save CliPolRelationshipEntity (id={0}, policy={1}, type={2}, typeNumber={3}, coverage={4}, client={5}, activeDate={6}, endDate={7}, split={8})", entity.getCprId(),
					entity.getPolicy(), entity.getType(), entity.getTypeNumber(), entity.getCoverage(), entity.getClient(), entity.getActiveDate(), entity.getEndDate(), entity.getPercentageSplit());
			cliPolRelationshipRepository.save(entity);
		}
	}

	@Override
	public boolean canClientDeathBeNotified(Integer clientId) {
		if (clientId == null) {
			return true;
		}
		boolean canBeNotifiedDeath = true;
		List<PolicyEntity> policiesConcerned = cliPolRelationshipRepository.getPoliciesSettlementInCaseOfDeath(clientId);
		// check if there is at least one active policies
		for (PolicyEntity p : policiesConcerned) {
			if (policyUtils.isPolicyActive(p)) {
				canBeNotifiedDeath = false;
				break;
			}
		}
		logger.debug("{} Policy need a settlement if client {} die", policiesConcerned != null ? policiesConcerned.size() : 0, clientId);
		return canBeNotifiedDeath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeClientPolicyRelationShip(Integer clientId, Date dateDeathNotified) {
		Assert.notNull(clientId, "Cannot close a policy relationship of a null client id.");
		Assert.notNull(dateDeathNotified, "The notification date of death must be provided.");

		List<CliPolRelationshipEntity> relationShip = cliPolRelationshipRepository.findActiveByClientIdAndTypesAndDate(clientId, CliPolRelationshipType.DEFAULT_POLICY_HOLDER_ROLES_TYPES,
				CalendarUtils.resetTime(Calendar.getInstance().getTime()));

		relationShip.forEach(relation -> {

			if (CliPolRelationshipStatus.ACTIVE.getValue() == (short) relation.getStatus()) {
				// In case of empty end date, fill it with the date of death. do not change others.
				if (CalendarUtils.isNull(relation.getEndDate())) {
					relation.setEndDate(dateDeathNotified);
				}

				relation.setStatus(CliPolRelationshipStatus.INACTIVE.getValue());

				if (historicManager.historize(relation)) {
					cliPolRelationshipRepository.save(relation);

					// Management for owner on death
					String policyId = relation.getPolicyId().trim();
					List<CliPolRelationshipEntity> ownerOnDeathRelationShip = new ArrayList<CliPolRelationshipEntity>();
					if (CliPolRelationshipType.OWNER.getValue() == relation.getType()) {
						// The death notification is for the owner, so we look for an owner on death relation for the policy
						ownerOnDeathRelationShip = cliPolRelationshipRepository.findActiveByPolicyIdAndTypes(policyId, Arrays.asList(CliPolRelationshipType.OWNER_ON_DEATH.getValue()));
					} else if (CliPolRelationshipType.JOINT_OWNER.getValue() == relation.getType()) {
						// The death notification is for the joint owner, so we look for an joint owner on death relation for the policy
						ownerOnDeathRelationShip = cliPolRelationshipRepository.findActiveByPolicyIdAndTypes(policyId, Arrays.asList(CliPolRelationshipType.JOINT_OWNER_ON_DEATH.getValue()));
					}

					// The relations found are also closed
					ownerOnDeathRelationShip.forEach(ownerOnDeathRelation -> {

						if (CliPolRelationshipStatus.ACTIVE.getValue() == (short) ownerOnDeathRelation.getStatus()) {

							// In case of empty end date, fill it with the date of death. do not change others.
							if (CalendarUtils.isNull(ownerOnDeathRelation.getEndDate())) {
								ownerOnDeathRelation.setEndDate(dateDeathNotified);
							}

							ownerOnDeathRelation.setStatus(CliPolRelationshipStatus.INACTIVE.getValue());

							if (historicManager.historize(ownerOnDeathRelation)) {
								cliPolRelationshipRepository.save(ownerOnDeathRelation);

								Integer ownerOnDeathClientId = ownerOnDeathRelation.getClientId();
								List<ClientClaimsDetailEntity> clientClaimsDetails = clientClaimsDetailRepository.findByClientId(ownerOnDeathClientId);
								boolean isDeceased = clientClaimsDetails.stream().anyMatch(
										ccd -> ccd != null && ccd.getDateOfDeath() != null && LocalDateTime.ofInstant(ccd.getDateOfDeath().toInstant(), ZoneId.systemDefault()).getYear() > 1900);

								// If the owner on death is not deceased, a new relation is created, the owner on death becomes the owner or the joint owner on death becomes the joint owner
								// Only if such a relation doesn't already exists
								if (!isDeceased) {
									List<CliPolRelationshipEntity> alreadyExistRelationShip = cliPolRelationshipRepository.findActiveByClientIdAndPolicyIdAndTypes(ownerOnDeathClientId, policyId,
											CliPolRelationshipType.DEFAULT_POLICY_HOLDER_ROLES_TYPES);
									if (alreadyExistRelationShip.isEmpty()) {
										// There is no active policy holder role type for the owner on death (or joint) on the policy, we can create one
										// We manage the rank to create no more than one policy holder or joint policy holder
										Integer typeNumber = relation.getType();
										while (!cliPolRelationshipRepository.findActiveByPolicyIdAndType(policyId, typeNumber).isEmpty() && typeNumber < 3) {
											typeNumber++;
										}
										PolicyHolderDTO newPolicyHolder = clientMapper.asPolicyHolderDTO(clientRepository.findById(ownerOnDeathClientId));
										// Initialization of default values for new policy holder
										newPolicyHolder.setTypeNumber(typeNumber);

										final Collection<CliPolRelationshipEntity> cliPolRelationshipEntities = cliPolRelationshipMapper.asCliPolRelationshipEntities(newPolicyHolder, policyId,
												dateDeathNotified,
												new ArrayList<CliPolRelationshipEntity>(), true);
										cliPolRelationshipEntities.forEach(x -> {
											x.setTypeNumber(1);
											x.setPercentageSplit(BigDecimal.ZERO);
											save(x);
										});
									}
								}
							}
						}
					});
				}
			}

		});

	}

	@Override
	public boolean isActive(CliPolRelationshipEntity cliPolRelationship) {
		Assert.notNull(cliPolRelationship);
		Date endDate = cliPolRelationship.getEndDate();

		if (calendarUtils.isBeforeOrEqualsNow(endDate)) {
			return false;
		}

		Date activateDate = cliPolRelationship.getActiveDate();

		if (calendarUtils.isNull(activateDate)) {
			return false;
		}

		Date today = new Date();

		return calendarUtils.isSameDayOrAfter(today, activateDate);
	}

	@Override
	public boolean hasType(CliPolRelationshipType type, CliPolRelationshipEntity cliPolRelationship) {
		return type.getValue() == cliPolRelationship.getType()
				&& cliPolRelationship.getClientId() != Constantes.FAKE_CLIENT_ID;
	}

	@Override
	public boolean isActiveWithType(CliPolRelationshipEntity cliPolRelationship, CliPolRelationshipType type) {
		return hasType(type, cliPolRelationship) && isActive(cliPolRelationship);
	}

	@Override
	public Collection<CliPolRelationshipEntity> getActiveCliPolRelationshipEntitiesWithType(Collection<CliPolRelationshipEntity> CliPolRelationshipEntities, CliPolRelationshipType type) {
		Assert.notNull(CliPolRelationshipEntities);
		Assert.notNull(type);

		return CliPolRelationshipEntities.stream().filter(x -> isActiveWithType(x, type)).collect(Collectors.toList());
	}

	@Override
	public boolean hasPolicyHolderRole(CliPolRelationshipEntity cliPol) {
		return CliPolRelationshipType.toCliPolRelationshipType(cliPol.getType()).isInGroup(CliPolRelationshipType.POLICY_HOLDER_PRINCIPAL_RELATIONSHIP_TYPE_GROUP);
	}

	@Override
	public boolean hasBeneficiaryRole(CliPolRelationshipEntity cliPol) {
		return CliPolRelationshipType.toCliPolRelationshipType(cliPol.getType()).isInGroup(CliPolRelationshipType.BENEFICIARY_PRINCIPAL_RELATIONSHIP_TYPE_GROUP);
	}

	@Override
	public boolean hasInsuredRole(CliPolRelationshipEntity cliPol) {
		return CliPolRelationshipType.toCliPolRelationshipType(cliPol.getType()).isInGroup(CliPolRelationshipType.INSURED_PRINCIPAL_RELATIONSHIP_TYPE_GROUP);
	}

	@Override
	public void deleteDisabledWithModifyProcess(String workflowItemId) {
		String id = StringUtils.rightPad(workflowItemId, CliPolRelationshipEntity.CPR_ID_LENGTH, ' ');
		cliPolRelationshipRepository.deleteDisabledWithModifyProcess(id);
	}

	/**
	 * Relation which are not in the policy holder/beneficiary/insured realations are added in the other client relations.
	 * 
	 * @param cliPolRelationshipEntities The cli pol relation entities
	 * @param excludedPolicyHolderRelations The policy holder relations
	 * @param excludedBeneficiaryRelations The beneficiary relations
	 * @param excludedInsuredRelations The insured relations.
	 * @return
	 */
	@Override
	public List<CliPolRelationshipEntity> getOtherCliPolRelationships(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities, List<CliPolRelationshipType> excludedPolicyHolderRelations,
			List<CliPolRelationshipType> excludedBeneficiaryRelations, List<CliPolRelationshipType> excludedInsuredRelations) {
		List<CliPolRelationshipEntity> cliPolRelations = cliPolRelationshipEntities.stream().filter(filteredCliPol -> {
			CliPolRelationshipType cliPolRelationshipType = CliPolRelationshipType.toCliPolRelationshipType(filteredCliPol.getType());

			boolean hasPolicyHolderRole = hasPolicyHolderRole(filteredCliPol);
			boolean isPolicyHolderRelation = hasPolicyHolderRole || cliPolRelationshipType.isInGroup(excludedPolicyHolderRelations);

			if (isPolicyHolderRelation) {
				return false;
			}

			boolean hasBeneficiaryRole = hasBeneficiaryRole(filteredCliPol);
			boolean isBeneficiaryRelation = hasBeneficiaryRole || cliPolRelationshipType.isInGroup(excludedBeneficiaryRelations);

			if (isBeneficiaryRelation) {
				return false;
			}

			boolean hasInsuredRole = hasInsuredRole(filteredCliPol);
			boolean isInsuredRelation = hasInsuredRole || cliPolRelationshipType.isInGroup(excludedInsuredRelations);

			if (isInsuredRelation) {
				return false;
			}

			return true;
		}).collect(Collectors.toList());
		return cliPolRelations;
	}

	public List<CliPolRelationshipEntity> getOtherCliPolRelationships(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return getOtherCliPolRelationships(cliPolRelationshipRepository.findAllByModifyProcess(workflowItemId),
				cliPolRelationshipWithClientRoleActivationMapper.getPolicyHolderCliPolRelationshipTypes(clientRoleActivationFlag),
				cliPolRelationshipWithClientRoleActivationMapper.getBeneficiaryCliPolRelationshipTypes(clientRoleActivationFlag),
				CliPolRelationshipType.INSURED_RELATIONSHIP_TYPE_GROUP);
	}

	@Override
	public List<CliPolRelationshipEntity> findByPolicyId(String policyId) {
		return cliPolRelationshipRepository.findByPolicyId(policyId);
	}

}
