package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyEndorsementDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.common.dto.liability.services.enums.PolicyEventType;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.BrokerChangeService;
import lu.wealins.liability.services.core.business.PolicyAgentShareService;
import lu.wealins.liability.services.core.business.PolicyChangeService;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.PolicyEndorsementService;
import lu.wealins.liability.services.core.business.PolicyEventService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.business.ProductValueService;
import lu.wealins.liability.services.core.business.UoptDetailService;
import lu.wealins.liability.services.core.mapper.BrokerChangeMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity;
import lu.wealins.liability.services.core.utils.CalendarUtils;

@Service
public class BrokerChangeServiceImpl implements BrokerChangeService {

	private static final BigDecimal ANTICIPATED = new BigDecimal(3);
	private static final BigDecimal EXPIRED = new BigDecimal(2);
	private static final BigDecimal CURRENT = new BigDecimal(4);
	private static final BigDecimal FIRST_DAY_OF_QUARTER = new BigDecimal(2);
	private static final BigDecimal FIRST_DAY_OF_MONTH = new BigDecimal(1);

	@Autowired
	private BrokerChangeMapper brokerChangeMapper;

	@Autowired
	private PolicyAgentShareService policyAgentShareService;

	@Autowired
	private PolicyChangeService policyChangeService;

	@Autowired
	private PolicyEventService policyEventService;

	@Autowired
	private PolicyEndorsementService policyEndorsementService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private PolicyCoverageService coverageService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private PolicyValuationService policyValuationService;

	@Autowired
	private ProductValueService productValueService;

	@Autowired
	private UoptDetailService uoptDetailService;

	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public BrokerChangeDTO initBrokerChange(AmendmentRequest brokerChangeRequest) {
		Assert.notNull(brokerChangeRequest);
		String policyId = brokerChangeRequest.getPolicyId();
		Assert.notNull(policyId);
		Assert.notNull(brokerChangeRequest.getWorkflowItemId());

		BrokerChangeDTO brokerChange = brokerChangeMapper.asBrokerChangeDTO(brokerChangeRequest);

		return brokerChange;
	}

	@Override
	public BrokerChangeDTO getBrokerChange(Integer workflowItemId) {

		BrokerChangeDTO brokerChange = new BrokerChangeDTO();

		String modifyProcess = workflowItemId + "";
		brokerChange.setBroker(policyAgentShareService.getBroker(modifyProcess)); // + Commission
		brokerChange.setBrokerContact(policyAgentShareService.getBrokerContact(modifyProcess));
		brokerChange.setSubBroker(policyAgentShareService.getSubBroker(modifyProcess));
		brokerChange.setWorkflowItemId(workflowItemId);

		PolicyChangeDTO policyChange = policyChangeService.getPolicyChange(workflowItemId);
		if (policyChange != null) {
			brokerChange.setChangeDate(policyChange.getDateOfChange());
			brokerChange.setPolicyId(policyChange.getPolicyId());
		}

		setupMailToAgent(brokerChange, false);
		setupBrokerContractRef(brokerChange, false);
		setupSendingRules(brokerChange, false);
		setupPolicyValuation(brokerChange, new Date());

		return brokerChange;
	}
	
	@Override
	public BrokerChangeDTO getBrokerChangeBefore(Integer workflowItemId) {

		BrokerChangeDTO brokerChange = new BrokerChangeDTO();

		PolicyChangeDTO policyChange = policyChangeService.getPolicyChange(workflowItemId);
		if (policyChange != null) {
			brokerChange.setChangeDate(policyChange.getDateOfChange());
			brokerChange.setPolicyId(policyChange.getPolicyId());
		}
		
		PolicyEntity policyEntity = policyService.getPolicyEntity(policyChange.getPolicyId());
		Date dateOfChange = policyChange.getDateOfChange();
		
		brokerChange.setBroker(policyAgentShareService.getPreviousBroker(policyEntity, dateOfChange)); 
		brokerChange.setBrokerContact(policyAgentShareService.getPreviousBrokerContact(policyEntity, dateOfChange));
		brokerChange.setSubBroker(policyAgentShareService.getPreviousSubBroker(policyEntity, dateOfChange));
		brokerChange.setWorkflowItemId(workflowItemId);



		setupMailToAgent(brokerChange, true);
		setupBrokerContractRef(brokerChange, true);
		setupSendingRules(brokerChange, true);
		setupPolicyValuation(brokerChange, DateUtils.addDays(dateOfChange, -1));
				
		return brokerChange;
	}

	@Override
	public BrokerChangeDTO updateBrokerChange(BrokerChangeDTO brokerChange) {

		String modifyProcess = brokerChange.getWorkflowItemId() + "";
		policyAgentShareService.deleteWithModifyProcess(modifyProcess);

		// We create a broker only if there is no commission --> no fund --> strange but safe
		if (CollectionUtils.isEmpty(getpolicyValuationHoldings(brokerChange))) {
			PolicyAgentShareDTO broker = brokerChange.getBroker();
			if (broker != null) {
				policyAgentShareService.updateBroker(broker, modifyProcess);
			}
		}

		PolicyAgentShareDTO brokerContact = brokerChange.getBrokerContact();
		if (brokerContact != null) {
			policyAgentShareService.updateBrokerContact(brokerContact, modifyProcess);
		}

		PolicyAgentShareDTO subBroker = brokerChange.getSubBroker();
		if (subBroker != null) {
			policyAgentShareService.updateSubBroker(subBroker, modifyProcess);
		}

		String policyId = brokerChange.getPolicyId();
		PolicyEntity policyEntity = policyService.getPolicyEntity(policyId);

		updateSendingRules(brokerChange, modifyProcess, policyEntity);
		updateMailToAgent(brokerChange, modifyProcess, policyEntity);
		updateBrokerConctractRef(brokerChange, modifyProcess, policyEntity);
		updatePolicyValuation(brokerChange, modifyProcess);

		return brokerChange;
	}

	private void setupMailToAgent(BrokerChangeDTO brokerChange, Boolean before) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";
		String value;
		if(BooleanUtils.isNotTrue(before)){
			value = policyEndorsementService.getValueAfter(PolicyEventType.MAIL_TO_AGENT, modifyProcess, 0);
		} else {
			value = policyEndorsementService.getValueBefore(PolicyEventType.MAIL_TO_AGENT, modifyProcess, 0);
		} 

		if (StringUtils.isBlank(value)) {
			return;
		}

		brokerChange.setMailToAgent(agentService.getAgentLite(value));
	}

	private void setupBrokerContractRef(BrokerChangeDTO brokerChange, Boolean before) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";
		String value;
		if(BooleanUtils.isNotTrue(before)){
			value = policyEndorsementService.getValueAfter(PolicyEventType.BROKER_CONTRACT_REF, modifyProcess, 0);
		} else {
			value = policyEndorsementService.getValueBefore(PolicyEventType.BROKER_CONTRACT_REF, modifyProcess, 0);
		} 

		if (StringUtils.isBlank(value)) {
			return;
		}

		brokerChange.setBrokerRefContract(value);
	}

	private void setupSendingRules(BrokerChangeDTO brokerChange, Boolean before) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";
		String value;
		if(BooleanUtils.isNotTrue(before)){
			value = policyEndorsementService.getValueAfter(PolicyEventType.SENDING_RULE, modifyProcess, 0);
		} else {
			value = policyEndorsementService.getValueBefore(PolicyEventType.SENDING_RULE, modifyProcess, 0);
		} 

		if (StringUtils.isBlank(value)) {
			return;
		}

		brokerChange.setCategory(uoptDetailService.getUoptDetail(value));
	}


	private void updateMailToAgent(BrokerChangeDTO brokerChange, String modifyProcess, PolicyEntity policyEntity) {

		String valueBefore = StringUtils.isBlank(policyEntity.getMailToAgent()) ? null : policyEntity.getMailToAgent();
		String valueAfter = brokerChange.getMailToAgent() == null ? null : brokerChange.getMailToAgent().getAgtId();

		// if value before and value after are the same then we skip the save
		if (isSameValue(valueBefore, valueAfter)) {
			return;
		}

		policyEventService.createOrUpdatePolicyEvent(policyEntity.getPolId(), PolicyEventType.MAIL_TO_AGENT, brokerChange.getChangeDate(), valueBefore, valueAfter, modifyProcess, 0);
	}

	private boolean isSameValue(String valueBefore, String valueAfter) {
		return (valueBefore == null && valueAfter == null) || (valueBefore != null && valueBefore.equals(valueAfter));
	}

	private boolean isSameValue(BigDecimal valueBefore, BigDecimal valueAfter) {
		return (valueBefore == null && valueAfter == null) || (valueBefore != null && valueBefore.compareTo(valueAfter) == 0);
	}

	private void updateBrokerConctractRef(BrokerChangeDTO brokerChange, String modifyProcess, PolicyEntity policyEntity) {

		String valueBefore = StringUtils.isBlank(policyEntity.getMailToAgent()) ? null : policyEntity.getBrokerRefContract();
		String valueAfter = brokerChange.getBrokerRefContract() == null ? null : brokerChange.getBrokerRefContract();

		// if value before and value after are the same then we skip the save
		if (isSameValue(valueBefore, valueAfter)) {
			return;
		}

		policyEventService.createOrUpdatePolicyEvent(policyEntity.getPolId(), PolicyEventType.BROKER_CONTRACT_REF, brokerChange.getChangeDate(), valueBefore, valueAfter, modifyProcess, 0);
	}

	private void updateSendingRules(BrokerChangeDTO brokerChange, String modifyProcess, PolicyEntity policyEntity) {

		String valueBefore = policyEntity.getCategory() == null ? null : policyEntity.getCategory().getUddId();
		String valueAfter = brokerChange.getCategory() == null ? null : brokerChange.getCategory().getUddId();

		// if value before and value after are the same then we skip the save
		if (isSameValue(valueBefore, valueAfter)) {
			return;
		}

		policyEventService.createOrUpdatePolicyEvent(policyEntity.getPolId(), PolicyEventType.SENDING_RULE, brokerChange.getChangeDate(), valueBefore, valueAfter, modifyProcess, 0);
	}

	private void setupPolicyValuation(BrokerChangeDTO brokerChange, Date valuationDate) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";

		PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(brokerChange.getPolicyId(), valuationDate);
		brokerChange.setPolicyValuation(policyValuation);

		List<PolicyValuationHoldingDTO> holdings = policyValuation.getHoldings();
		if (CollectionUtils.isEmpty(holdings)) {
			return;
		}
		Collection<PolicyAgentShareEntity> brokerPolicyAgentShareEntities = policyAgentShareService.getPolicyAgentShareEntities(PolicyAgentShareType.ADVISOR_FEES, AgentCategory.BROKER, modifyProcess);

		Map<String, Collection<Integer>> coveragesByFund = getCoveragesByFund(brokerChange);

		for (PolicyValuationHoldingDTO policyValuationHolding : policyValuation.getHoldings()) {
			String fundId = policyValuationHolding.getFundId();
			Collection<Integer> coverages = coveragesByFund.get(fundId);

			setupAdminFees(policyValuationHolding, modifyProcess, coverages);
			setupCommissionRate(policyValuationHolding, brokerPolicyAgentShareEntities, coverages);
		}

	}

	private Map<String, Collection<Integer>> getCoveragesByFund(BrokerChangeDTO brokerChange) {
		Collection<PolicyValuationHoldingDTO> holdings = getpolicyValuationHoldings(brokerChange);
		if (CollectionUtils.isEmpty(holdings)) {
			return new HashMap<>();
		}

		List<String> fundIds = holdings.stream().map(x -> x.getFundId()).collect(Collectors.toList());

		return coverageService.getCoverages(brokerChange.getPolicyId(), fundIds);
	}

	private void setupAdminFees(PolicyValuationHoldingDTO policyValuationHolding, String modifyProcess, Collection<Integer> coverages) {
		String valueAfter = policyEndorsementService.getValueAfter(PolicyEventType.ADMIN_FEE, modifyProcess, coverages.toArray(new Integer[coverages.size()]));

		if (StringUtils.isNotBlank(valueAfter)) {
			policyValuationHolding.setFeeRate(new BigDecimal(valueAfter));
		}
	}

	private void setupCommissionRate(PolicyValuationHoldingDTO policyValuationHolding, Collection<PolicyAgentShareEntity> brokerPolicyAgentShareEntities, Collection<Integer> coverages) {
		List<PolicyAgentShareEntity> filterBokerPolicyAgentShareEntities = brokerPolicyAgentShareEntities.stream().filter(x -> coverages.contains(x.getCoverage())).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(filterBokerPolicyAgentShareEntities)) {
			return;
		}

		PolicyAgentShareEntity firstElement = filterBokerPolicyAgentShareEntities.iterator().next();
		BigDecimal firstCommissionRate = firstElement.getPercentage();

		boolean allCommissionRateAreEquals = filterBokerPolicyAgentShareEntities.stream().allMatch(x -> firstElement.getPercentage() != null && firstCommissionRate.compareTo(x.getPercentage()) == 0);

		if (!allCommissionRateAreEquals) {
			throw new IllegalStateException("Commission rates are different for the broker " + firstElement.getAgent().getAgtId() + " with the coverages " + coverages + ".");
		}

		policyValuationHolding.setCommissionRate(firstCommissionRate);
	}

	private void updatePolicyValuation(BrokerChangeDTO brokerChange, String modifyProcess) {
		String policyId = brokerChange.getPolicyId();
		if (StringUtils.isBlank(policyId)) {
			return;
		}

		Map<String, Collection<Integer>> coveragesByFund = getCoveragesByFund(brokerChange);
		PolicyAgentShareDTO broker = brokerChange.getBroker();
		Map<String, BigDecimal> currentFeeRates = getCurrentFeeRates(policyId);

		for (PolicyValuationHoldingDTO policyValuationHolding : getpolicyValuationHoldings(brokerChange)) {
			String fundId = policyValuationHolding.getFundId();
			Collection<Integer> coverages = coveragesByFund.get(fundId);

			updateCommissionRates(broker, coverages, policyValuationHolding.getCommissionRate(), modifyProcess);
			updateAdminFees(policyId, coverages, brokerChange.getChangeDate(), currentFeeRates.get(fundId), policyValuationHolding.getFeeRate(), modifyProcess);
		}

	}

	@Override
	public BrokerChangeDTO applyChangeToPolicy(BrokerChangeDTO brokerChange) {
		String policyId = brokerChange.getPolicyId();
		Assert.notNull(policyId);
		PolicyEntity policyEntity = policyService.getPolicyEntity(policyId);

		Assert.notNull(policyEntity, "Policy " + policyId + " does not exist.");
		
		applyChangeToPolicyAgentShares(brokerChange, policyEntity);
		applyChangeToBrokerRefContract(brokerChange, policyEntity);
		applyChangeToMailToAgent(brokerChange, policyEntity);
		applyChangeToSendingRules(brokerChange, policyEntity);
		// admin fees will be apply during the batch of activation fees.
		// applyChangeToAdminFees(brokerChange);

		policyService.save(policyEntity);

		return brokerChange;
	}

	private void applyChangeToBrokerRefContract(BrokerChangeDTO brokerChange, PolicyEntity policyEntity) {
		policyEntity.setBrokerRefContract(brokerChange.getBrokerRefContract());
	}

	private void applyChangeToSendingRules(BrokerChangeDTO brokerChange, PolicyEntity policyEntity) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";
		String valueAfter = policyEndorsementService.getValueAfter(PolicyEventType.SENDING_RULE, modifyProcess, 0);
		String valueBefore = policyEndorsementService.getValueBefore(PolicyEventType.SENDING_RULE, modifyProcess, 0);

		if (valueBefore != valueAfter) {
			UoptDetailEntity category = valueAfter == null ? null : uoptDetailService.getUoptDetailEntity(valueAfter);
			policyEntity.setCategory(category);
		}

	}

	private void applyChangeToMailToAgent(BrokerChangeDTO brokerChange, PolicyEntity policyEntity) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";

		String valueAfter = policyEndorsementService.getValueAfter(PolicyEventType.MAIL_TO_AGENT, modifyProcess, 0);
		String valueBefore = policyEndorsementService.getValueBefore(PolicyEventType.MAIL_TO_AGENT, modifyProcess, 0);

		if (valueBefore != valueAfter) {
			policyEntity.setMailToAgent(valueAfter);
		}

	}

	private void applyChangeToPolicyAgentShares(BrokerChangeDTO brokerChange, PolicyEntity policyEntity) {
		Date changeDate = brokerChange.getChangeDate();

		disableBroker(policyEntity, changeDate);
		disableSubBroker(policyEntity, changeDate);
		disableBrokerContact(policyEntity, changeDate);

		policyAgentShareService.applyChange(brokerChange.getWorkflowItemId(), changeDate);

	}

	@Override
	public BrokerChangeDTO applyChangeToAdminFees(BrokerChangeDTO brokerChange) {
		String policyId = brokerChange.getPolicyId();
		Date currentDate = new Date();
		Collection<AdminFeeChangeDTO> effectiveDatesForAdminFees = getAdminFeeChanges(brokerChange);
		
		effectiveDatesForAdminFees.forEach(x -> {
			Date effectiveDateForAdminFees = x.getEffectiveDate();
			if (currentDate.after(effectiveDateForAdminFees)) {
				productValueService.createOrUpdateProductValue(ControlDefinitionType.CONTRACT_MANAGEMENT_FEE.getValue(), policyId, x.getCoverage(), x.getValue());
			}
		});

		return brokerChange;
	}

	@Override
	public Collection<AdminFeeChangeDTO> getAdminFeeChanges(BrokerChangeDTO brokerChange) {

		Collection<AdminFeeChangeDTO> adminFeeChanges = new ArrayList<>();

		Collection<PolicyEndorsementDTO> policyEndorsements = getChangedEndorsements(brokerChange);
		String policyId = brokerChange.getPolicyId();
		Date effectiveDate = brokerChange.getChangeDate();

		for (PolicyEndorsementDTO policyEndorsement : policyEndorsements) {
			Integer coverage = policyEndorsement.getCoverage();

			ProductValueDTO adminFeeMethodproductValue = productValueService.getProductValue(ControlDefinitionType.ADMIN_FEE_PAYMENT_METHOD.getValue(), policyId, coverage);
			Assert.notNull(adminFeeMethodproductValue,
					"No product value C12PRA " + ControlDefinitionType.ADMIN_FEE_PAYMENT_METHOD.getValue() + " linked to the policyId " + policyId + " and the coverage " + coverage + ".");
			ProductValueDTO adminFeePeriodicityproductValue = productValueService.getProductValue(ControlDefinitionType.ADMIN_FEE_PERIODICITY.getValue(), policyId, coverage);
			Assert.notNull(adminFeePeriodicityproductValue,
					"No product value C12PRA " + ControlDefinitionType.ADMIN_FEE_PERIODICITY.getValue() + " linked to the policyId " + policyId + " and the coverage " + coverage + ".");

			Date effectiveDateForAdminFees = getEffectiveDateForAdminFees(adminFeeMethodproductValue, adminFeePeriodicityproductValue, effectiveDate);

			AdminFeeChangeDTO effectiveDateForAdminFeesDTO = new AdminFeeChangeDTO();

			effectiveDateForAdminFeesDTO.setCoverage(coverage);
			effectiveDateForAdminFeesDTO.setEffectiveDate(effectiveDateForAdminFees);
			effectiveDateForAdminFeesDTO.setValue(new BigDecimal(policyEndorsement.getValueAfter()));

			adminFeeChanges.add(effectiveDateForAdminFeesDTO);

		}

		return adminFeeChanges;
	}

	private Date getEffectiveDateForAdminFees(ProductValueDTO adminFeeMethodproductValue, ProductValueDTO adminFeePeriodicityproductValue, Date effectiveDate) {

		if (ANTICIPATED.compareTo(adminFeeMethodproductValue.getNumericValue()) == 0) {
			if (BigDecimal.ZERO.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0 || CURRENT.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0) {
				return effectiveDate;
			}
			if (FIRST_DAY_OF_QUARTER.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0) {
				return calendarUtils.getFirstDayOfQuarter(effectiveDate);
			}
			if (FIRST_DAY_OF_MONTH.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0) {
				return calendarUtils.getFirstDayOfMonth(effectiveDate);
			}

			throw new IllegalStateException(
					"Admin fee periodicity (product value = 'C12VAL', nuemric value = " + adminFeePeriodicityproductValue.getNumericValue() + ")  is not managed with anticipated payment.");
		}

		if (EXPIRED.compareTo(adminFeeMethodproductValue.getNumericValue()) == 0) {
			if (BigDecimal.ZERO.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0 || CURRENT.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0) {
				return effectiveDate;
			}
			if (FIRST_DAY_OF_QUARTER.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0) {
				return calendarUtils.getFirstDayOfNextQuarter(effectiveDate);
			}
			if (FIRST_DAY_OF_MONTH.compareTo(adminFeePeriodicityproductValue.getNumericValue()) == 0) {
				return calendarUtils.getFirstDayOfNextMonth(effectiveDate);
			}
			throw new IllegalStateException(
					"Admin fee periodicity (product value = 'C12VAL', nuemric value = " + adminFeePeriodicityproductValue.getNumericValue() + ")  is not managed with anticipated payment.");
		}

		throw new IllegalStateException("Admin fee payment method (product value = 'C12PRA', nuemric value = " + adminFeeMethodproductValue.getNumericValue() + ")  is not managed.");
	}

	private Collection<PolicyEndorsementDTO> getChangedEndorsements(BrokerChangeDTO brokerChange) {
		String modifyProcess = brokerChange.getWorkflowItemId() + "";

		Integer[] coveragesArray = getCoverageArray(brokerChange);

		return policyEndorsementService.getChangedPolicyEndorsements(PolicyEventType.ADMIN_FEE, modifyProcess, coveragesArray);
	}

	private Integer[] getCoverageArray(BrokerChangeDTO brokerChange) {
		Map<String, Collection<Integer>> coveragesByFund = getCoveragesByFund(brokerChange);
		
		Set<Integer> coverages = new HashSet<>();
		coveragesByFund.values().forEach(x -> coverages.addAll(x));
		
		Integer[] coveragesArray = coverages.toArray(new Integer[coverages.size()]);
		return coveragesArray;
	}

	private void disableSubBroker(PolicyEntity policyEntity, Date endDate) {
		policyAgentShareService.disable(policyEntity, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.SUB_BROKER.getCategory(), endDate);
	}

	private void disableBrokerContact(PolicyEntity policyEntity, Date endDate) {
		policyAgentShareService.disable(policyEntity, PolicyAgentShareType.SALES_REPRESENTATIVE.getType(), AgentCategory.PERSON_CONTACT.getCategory(), endDate);
	}

	private void disableBroker(PolicyEntity policyEntity, Date endDate) {
		policyAgentShareService.disable(policyEntity, PolicyAgentShareType.ADVISOR_FEES.getType(), AgentCategory.BROKER.getCategory(), endDate);
	}


	private void updateCommissionRates(PolicyAgentShareDTO broker, Collection<Integer> coverages, BigDecimal commissionRate, String modifyProcess) {
		if (broker == null) {
			return;
		}

		for (Integer coverage : coverages) {
			// Force creation
			broker.setPasId(0);
			broker.setCoverage(coverage);

			broker.setPercentage(commissionRate);
			policyAgentShareService.updateBroker(broker, modifyProcess);
		}
	}

	private void updateAdminFees(String polId, Collection<Integer> coverages, Date effectiveDate, BigDecimal currentFeeRate, BigDecimal newFeeRate, String modifyProcess) {

		// if value before and value after are the same then we skip the save
		if (isSameValue(currentFeeRate, newFeeRate)) {
			return;
		}

		String valueBefore = currentFeeRate == null ? null : currentFeeRate + "";
		String valueAfter = newFeeRate == null ? null : newFeeRate + "";

		policyEventService.createOrUpdatePolicyEvent(polId, PolicyEventType.ADMIN_FEE, effectiveDate, valueBefore, valueAfter, modifyProcess,
				coverages.toArray(new Integer[coverages.size()]));
	}

	private Map<String, BigDecimal> getCurrentFeeRates(String polId) {

		PolicyValuationDTO cuurentPolicyValuation = policyValuationService.getPolicyValuation(polId, new Date());
		return cuurentPolicyValuation.getHoldings().stream().collect(Collectors.toMap(x -> x.getFundId(), x -> x.getFeeRate()));
	}

	private Collection<PolicyValuationHoldingDTO> getpolicyValuationHoldings(BrokerChangeDTO brokerChange) {
		PolicyValuationDTO policyValuation = brokerChange.getPolicyValuation();
		if (policyValuation == null) {
			return new ArrayList<>();
		}

		return policyValuation.getHoldings();
	}

	@Override
	public Boolean hasAdminFeesChanged(BrokerChangeDTO brokerChange) {
		return Boolean.valueOf(CollectionUtils.isNotEmpty(getChangedEndorsements(brokerChange)));
	}

}
