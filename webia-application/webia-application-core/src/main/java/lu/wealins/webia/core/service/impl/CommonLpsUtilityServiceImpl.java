package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityClientService;
import lu.wealins.webia.core.service.LiabilityExchangeRateService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaCheckDataService;
import lu.wealins.webia.core.service.WebiaCheckWorkflowService;
import lu.wealins.webia.core.service.lps.CommonLpsUtilityService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public abstract class CommonLpsUtilityServiceImpl<T extends FormDataDTO> implements CommonLpsUtilityService<T> {

	private static final String LUX = "LUX";
	private static final String AMOUNT_SUBSCRIPTION_LPS06 = "AMOUNT_SUBSCRIPTION_LPS06";
	private static final String COUNTRIES_EEE = "COUNTRIES_EEE";
	private static final String YES = "YES";
	private static final String NO = "NO";

	@Autowired
	protected WebiaApplicationParameterService applicationParameterService;

	@Autowired
	protected WebiaCheckWorkflowService checkWorkflowService;

	@Autowired
	protected LiabilityClientService clientService;

	@Autowired
	private LiabilityExchangeRateService liabilityExchangeRateService;

	@Autowired
	private LiabilityAgentService agentService;

	private Set<String> countriesEee;

	private BigDecimal amountSubscriptionLps06;

	@Autowired
	protected WebiaCheckDataService webiaCheckDataService;

	@Autowired
	protected RestClientUtils restClientUtils;

	public void setupCheckData(Integer workflowItemId, String checkCode, Boolean condition, Map<String, CheckDataDTO> checkDataByCheckWorkflow) {
		CheckDataDTO checkData = checkDataByCheckWorkflow.get(checkCode);

		if (checkData == null) {
			checkData = new CheckDataDTO();
			checkData.setWorkflowItemId(workflowItemId);
			CheckWorkflowDTO checkWorkflow = checkWorkflowService.getCheckWorkflow(checkCode);

			if (checkWorkflow == null) {
				throw new IllegalStateException("No question " + checkCode + " has been found.");
			}

			checkData.setCheckId(checkWorkflow.getCheckId());
			checkData.setDataValueYesNoNa(convert(condition));

			checkDataByCheckWorkflow.put(checkCode, checkData);
		}

		checkData.setDataValueYesNoNa(convert(condition));
	}

	protected abstract Map<String, CheckDataDTO> setupCheckDataForLps(T formData, Collection<CheckStepDTO> checkSteps);

	@Override
	public void updateCheckSteps(Collection<CheckStepDTO> checkSteps, T formData) {
		Map<String, CheckDataDTO> checkDataByCheckCode = setupCheckDataForLps(formData, checkSteps);

		CheckDataContainerDTO checkDataContainer = new CheckDataContainerDTO();
		checkDataContainer.setCheckData(checkDataByCheckCode);

		checkDataContainer = webiaCheckDataService.update(checkDataContainer);

		for (Entry<String, CheckDataDTO> entrySet : checkDataContainer.getCheckData().entrySet()) {
			CheckWorkflowDTO checkWorkflow = checkSteps.stream().map(x -> x.getCheck()).filter(x -> entrySet.getKey().equals(x.getCheckCode())).findFirst()
					.orElse(null);
			if (checkWorkflow != null) {
				checkWorkflow.setCheckData(entrySet.getValue());
			}
		}

	}

	protected Map<Integer, ClientDTO> getclientsMap(T formData) {
		Collection<Integer> clientIds = getClientIds(formData);
		Collection<ClientDTO> clients = clientService.getClients(clientIds);

		return clients.stream().collect(Collectors.toMap(x -> x.getCliId(), x -> x));
	}

	public Collection<Integer> getClientIds(T formData) {
		Set<Integer> clients = new HashSet<>();

		clients.addAll(getClientIdsForDeathBeneficiaries(formData));
		clients.addAll(getClientIdsForLifeBeneficiaries(formData));
		clients.addAll(getClientIdsForPolicyHolders(formData));
		clients.addAll(getClientIdsForInsureds(formData));

		return clients;
	}

	protected Boolean checkLps1(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> clients = getInsuredsAndPolicyHolders(formData, clientsMap);

		Boolean lpsCondition = null;
		boolean allHomeCountriesAreDefined = CollectionUtils.isNotEmpty(clients) && clients.stream().allMatch(c -> c != null && c.getHomeAddress() != null && c.getHomeAddress().getCountry() != null);

		if (allHomeCountriesAreDefined) {
			lpsCondition = Boolean.valueOf(clients.stream().allMatch(c -> getCountriesEee().contains(c.getHomeAddress().getCountry())));
		}

		return lpsCondition;
	}

	protected Boolean checkLps2(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> clients = getInsuredsAndPolicyHolders(formData, clientsMap);
		Boolean lpsCondition = null;
		boolean allNationalitiesAreDefined = CollectionUtils.isNotEmpty(clients) && clients.stream().allMatch(c -> getNationality(c) != null);

		if (allNationalitiesAreDefined) {
			lpsCondition = Boolean.valueOf(clients.stream().allMatch(c -> getCountriesEee().contains(getNationality(c))));
		}

		return lpsCondition;
	}

	private String getNationality(ClientDTO client) {
		if (clientService.isPhysical(client)) {
			return client.getNationality();
		} else if (clientService.isMoral(client)) {
			return client.getCountryOfBirth();
		}

		return null;
	}

	protected Boolean checkLps3(T formData) {
		Boolean lpsCondition = null;
		String premiumCountryCd = getPremiumCountryCd(formData);

		if (premiumCountryCd != null) {
			lpsCondition = Boolean.valueOf(!getCountriesEee().contains(premiumCountryCd));
		}

		return lpsCondition;
	}

	public abstract String getPremiumCountryCd(T formData);

	public abstract PartnerFormDTO getBroker(T formData);

	protected Boolean checkLps4(T formData, Map<Integer, ClientDTO> clientsMap) {
		PartnerFormDTO broker = getBroker(formData);
		Boolean lpsCondition = null;

		if (broker != null && broker.getPartnerId() != null) {
			AgentDTO agent = agentService.getAgent(broker.getPartnerId());
			if (agent != null) {
				String countryBroker = agent.getCountry();
				if (countryBroker != null) {
					if (LUX.equals(countryBroker)) {
						lpsCondition = Boolean.FALSE;
					} else {
						Collection<ClientDTO> clients = getInsuredsAndPolicyHolders(formData, clientsMap);
						boolean allHomeCountriesAreDefined = CollectionUtils.isNotEmpty(clients)
								&& clients.stream().allMatch(c -> c.getHomeAddress() != null && c.getHomeAddress().getCountry() != null);
						if (allHomeCountriesAreDefined) {
							Set<String> countries = clients.stream().filter(x -> x.getHomeAddress() != null && x.getHomeAddress().getCountry() != null).map(x -> x.getHomeAddress().getCountry())
									.collect(Collectors.toSet());
							lpsCondition = Boolean.valueOf(!countries.contains(countryBroker));
						}
					}
				}
			}
		}

		return lpsCondition;
	}

	public abstract BigDecimal getPaymentAmt(T formData);

	public abstract BigDecimal getExpectedPremium(T formData);

	public abstract String getContractCurrency(T formData);

	public abstract Date getPaymentDt(T formData);

	protected Boolean checkLps5(T formData) {
		Boolean lpsCondition = null;
		BigDecimal paymentAmt = getPaymentAmt(formData);
		BigDecimal expectedPremium = getExpectedPremium(formData);

		if (paymentAmt != null || expectedPremium != null) {
			BigDecimal amount = paymentAmt;
			if (amount == null) {
				amount = expectedPremium;
			}

			if (amount != null) {
				BigDecimal amountSubscriptionLps05 = getAmountSubscriptionLps05();
				String contractCurrency = getContractCurrency(formData);

				if (!"EUR".equals(contractCurrency)) {
					Date date = getPaymentDt(formData);
					if (date == null) {
						date = new Date();
					}
					amountSubscriptionLps05 = liabilityExchangeRateService.convert(amountSubscriptionLps05, "EUR", contractCurrency, date);
				}

				lpsCondition = Boolean.valueOf(amount.compareTo(amountSubscriptionLps05) > 0);
			}
		}

		return lpsCondition;
	}

	protected Boolean checkLps10(T formData, Map<Integer, ClientDTO> clientsMap) {
		Boolean lpsCondition = null;
		Collection<ClientDTO> policyHolders = getClientsForPolicyHolders(formData, clientsMap);
		boolean allPolicyHolderTypeAreDefined = CollectionUtils.isNotEmpty(policyHolders) && policyHolders.stream().allMatch(x -> x.getType() != null);
		if (allPolicyHolderTypeAreDefined) {
			lpsCondition = Boolean.valueOf(policyHolders.stream().allMatch(x -> clientService.isPhysical(x)));
		}

		return lpsCondition;
	}

	protected Boolean checkLps14(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> clients = getInsuredsAndPolicyHolders(formData, clientsMap);
		clients.addAll(getClientsForLifeBeneficiaries(formData, clientsMap));
		clients.addAll(getClientsForDeathBeneficiaries(formData, clientsMap));

		Boolean lpsCondition = null;

		boolean allPEPClientsAreDefined = CollectionUtils.isNotEmpty(clients) && clients.stream().allMatch(x -> StringUtils.isNotBlank(x.getPoliticallyExposedPerson()) || clientService.isMoral(x));
		if (allPEPClientsAreDefined) {
			lpsCondition = Boolean.valueOf(clients.stream().anyMatch(x -> clientService.isPEP(x)));
		}

		return lpsCondition;
	}

	protected Boolean checkLps15(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> clients = getClientsForPolicyHolders(formData, clientsMap);

		boolean allHolderAreMoral = clients.stream().allMatch(x -> clientService.isMoral(x));
		if (allHolderAreMoral) {
			return Boolean.FALSE;
		}

		Boolean lpsCondition = null;

		boolean allPEPandRCAClientsAreDefined = CollectionUtils.isNotEmpty(clients)
				&& clients.stream().allMatch(x -> StringUtils.isNotBlank(x.getPoliticallyExposedPerson()) || clientService.isMoral(x))
				&& clients.stream().allMatch(x -> StringUtils.isNotBlank(x.getRelativeCloseAssoc()) || clientService.isMoral(x));
		if (allPEPandRCAClientsAreDefined) {
			lpsCondition = Boolean.valueOf(clients.stream().anyMatch(x -> clientService.isPEP(x) || clientService.isRCA(x)));
		}

		return lpsCondition;
	}

	protected Boolean checkLps16(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> clients = getClientsForPolicyHolders(formData, clientsMap);
		Boolean lpsCondition = null;

		if (CollectionUtils.isNotEmpty(clients)) {

			boolean allinformationsAreDefined = CollectionUtils.isNotEmpty(clients) && clients.stream().allMatch((c) -> {
				if (clientService.isPhysical(c)) {
					return StringUtils.isNotBlank(c.getProfession()) && c.getActivityRiskCat() != null;
				}
				return c.getActivityRiskCat() != null;
			});

			if (allinformationsAreDefined) {
				lpsCondition = new Boolean(clients.stream().anyMatch((c) -> {
					if (clientService.isPhysical(c)) {
						return clientService.hasCriticalProfession(c) && clientService.hasCriticalActivity(c);
					}
					return clientService.hasCriticalActivity(c);
				}));
			}

		}

		return lpsCondition;
	}

	protected Boolean checkLps13(T formData, Map<Integer, ClientDTO> clientsMap) {

		Collection<ClientDTO> lifeBeneficiaries = getClientsForLifeBeneficiaries(formData, clientsMap);
		Collection<ClientDTO> deathBeneficiaries = getClientsForDeathBeneficiaries(formData, clientsMap);
		Boolean lpsCondition = Boolean.FALSE;

		if (CollectionUtils.isNotEmpty(deathBeneficiaries) || CollectionUtils.isNotEmpty(lifeBeneficiaries)) {
			lpsCondition = Boolean.valueOf(CollectionUtils.isNotEmpty(deathBeneficiaries)
					&& deathBeneficiaries.stream().anyMatch(x -> clientService.isMoral(x)));
			if (BooleanUtils.isFalse(lpsCondition)) {
				lpsCondition = Boolean.valueOf(CollectionUtils.isNotEmpty(lifeBeneficiaries)
						&& lifeBeneficiaries.stream().anyMatch(x -> clientService.isMoral(x)));
			}
		}

		return lpsCondition;
	}

	private Collection<ClientDTO> getInsuredsAndPolicyHolders(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> clients = getClientsForInsureds(formData, clientsMap);

		clients.addAll(getClientsForPolicyHolders(formData, clientsMap));

		return clients;
	}

	protected Collection<ClientDTO> getClientsForLifeBeneficiaries(T formData, Map<Integer, ClientDTO> clientsMap) {
		return getClientIdsForLifeBeneficiaries(formData).stream().map(e -> clientsMap.get(e)).collect(Collectors.toSet());
	}

	protected Collection<ClientDTO> getClientsForDeathBeneficiaries(T formData, Map<Integer, ClientDTO> clientsMap) {
		return getClientIdsForDeathBeneficiaries(formData).stream().map(e -> clientsMap.get(e)).collect(Collectors.toSet());
	}

	protected Collection<ClientDTO> getClientsForInsureds(T formData, Map<Integer, ClientDTO> clientsMap) {
		return getClientIdsForInsureds(formData).stream().map(e -> clientsMap.get(e)).collect(Collectors.toSet());
	}

	protected Collection<ClientDTO> getClientsForPolicyHolders(T formData, Map<Integer, ClientDTO> clientsMap) {
		return getClientIdsForPolicyHolders(formData).stream().map(e -> clientsMap.get(e)).collect(Collectors.toSet());
	}

	public Collection<Integer> getClientIdsForLifeBeneficiaries(T formData) {
		return new ArrayList<>();
	}

	public Collection<Integer> getClientIdsForDeathBeneficiaries(T formData) {
		return new ArrayList<>();
	}

	public Collection<Integer> getClientIdsForPolicyHolders(T formData) {
		return new ArrayList<>();
	}

	public Collection<Integer> getClientIdsForInsureds(T formData) {
		return new ArrayList<>();
	}

	private BigDecimal getAmountSubscriptionLps05() {
		if (amountSubscriptionLps06 == null) {
			ApplicationParameterDTO applicationParameter = applicationParameterService.getApplicationParameter(AMOUNT_SUBSCRIPTION_LPS06);
			if (applicationParameter == null) {
				throw new IllegalStateException("No application parameter " + AMOUNT_SUBSCRIPTION_LPS06 + " defined.");
			}
			amountSubscriptionLps06 = new BigDecimal(applicationParameter.getValue());
		}
		return amountSubscriptionLps06;
	}

	private Set<String> getCountriesEee() {
		if (countriesEee == null) {
			countriesEee = applicationParameterService.getApplicationParameters(COUNTRIES_EEE).stream().collect(Collectors.toSet());
		}

		return countriesEee;
	}

	private String convert(Boolean value) {
		if (value == null) {
			return null;
		}
		return BooleanUtils.isTrue(value) ? YES : NO;
	}

	protected Boolean convert(String value) {
		if (value == null) {
			return null;
		}
		return YES.equals(value);
	}

	protected Boolean checkMov13(T formData, Map<Integer, ClientDTO> clientsMap) {
		Collection<ClientDTO> policyHolders = getClientsForPolicyHolders(formData, clientsMap);
		return Boolean.valueOf(policyHolders.stream().anyMatch(clientService::isDap));
	}

	protected Boolean checkMov1(Date policyEffectiveDate, Date workflowStartDate) {
		if (policyEffectiveDate == null || workflowStartDate == null) {
			return null;
		}

		// Assert that the workflow date occurs within the two first year of the subscription
		LocalDate policyDate = policyEffectiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate workflowDate = workflowStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return Boolean.valueOf(workflowDate.isBefore(policyDate.plusYears(2)));
	}

}
