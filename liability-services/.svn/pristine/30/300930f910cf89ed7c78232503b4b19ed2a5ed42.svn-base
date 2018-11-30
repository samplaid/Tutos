package lu.wealins.liability.services.core.listener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lu.wealins.common.dto.liability.services.AgentBankAccountDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.drm.DRMAgentDTO;
import lu.wealins.common.dto.liability.services.drm.DRMBankAccountDTO;
import lu.wealins.common.dto.liability.services.drm.DRMEmailDTO;
import lu.wealins.common.dto.liability.services.drm.DRMMessageDTO;
import lu.wealins.liability.services.core.business.AgentBankAccountService;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.mapper.AgentBankAccountMapper;
import lu.wealins.liability.services.core.mapper.DRMMapper;
import lu.wealins.liability.services.core.persistence.entity.AgentBankAccountEntity;
import lu.wealins.liability.services.core.persistence.repository.DRMLissiaMappingRepository;

public class DRMPartnerListener implements MessageListener {
	private final static String TYPE_CATEGORY = "CATEGORY";
	private final static String TYPE_COUNTRY = "COUNTRY";
	private final static String TYPE_PAYMENT_COMMISSION = "PAYMENT_COMMISSION";
	private final static String TYPE_PAYMENT_METHOD = "PAYMENT_METHOD";
	private final static String USER = "DRMag";
	private static final String DOCUMENTATION_LANGUAGE_TYPE = "DOCUMENTATION LANGUAGE";
	private static final String DELETE_AGENT_MESSAGE = "before_delete";

	private static final String BROKER = "BK";
	private static final String ASSET_MANAGER = "AM";
	private static final String DEPOSIT_BANK = "DB";

	@Autowired
	ConnectionFactory connectionFactory;

	@Autowired
	AgentService agentService;

	@Autowired
	AgentBankAccountService agentBankAccountService;

	@Autowired
	DRMMapper drmMapper;

	@Autowired
	AgentBankAccountMapper agentBankAccountMapper;

	@Autowired
	private DRMLissiaMappingRepository drmLissiaMappingRepository;

	@Override
	public void onMessage(Message message) {
		BytesMessage bytesMessage = (BytesMessage) message;
		try {
			byte[] byteData = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(byteData);
			ObjectMapper objectMapper = new ObjectMapper();
			// convert json string to object
			DRMMessageDTO messageConverted = objectMapper.readValue(byteData, DRMMessageDTO.class);
			DRMAgentDTO agent = messageConverted.getPartner();
			if (messageConverted.getEvent().equals(DELETE_AGENT_MESSAGE)) {
				agent.setDeleted(true);
			}
			AgentDTO agentToInject = mapAgent(agent);
			agentToInject.setAgtId(getLissiaAgentId(agentToInject));

			if (checkInjectionCriterias(agent)) {
				injectAgentInLissia(agentToInject);
			} else if (agentToInject.getAgtId() != null) {
				agentToInject.setStatus("2");
				injectAgentInLissia(agentToInject);
			}

		} catch (JMSException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * filter agent with good category
	 * 
	 * @param agent
	 * @return
	 */
	private boolean checkInjectionCriterias(DRMAgentDTO agent) {
		return drmLissiaMappingRepository.findMapping(TYPE_CATEGORY, agent.getPartner_role_c()) != null;
	}

	/**
	 * inject agent in LISSIA
	 * 
	 * @param agent
	 * @return
	 */
	public AgentDTO injectAgentInLissia(AgentDTO agent) {
		AgentDTO newAgent = null;

		if (agent.getAgtId() != null && !StringUtils.isBlank(agent.getAgtId())) {
			addUpdateDefaultValues(agent);

			ArrayList<AgentBankAccountDTO> bankAccountsToDisable = new ArrayList<>();
			ArrayList<AgentBankAccountDTO> bankAccountsToEnable = new ArrayList<>();
			ArrayList<AgentBankAccountDTO> bankAccountsToAdd = new ArrayList<>();

			checkAgentBankAccounts(agent, bankAccountsToDisable, bankAccountsToEnable, bankAccountsToAdd);

			for (AgentBankAccountDTO bankAccount : bankAccountsToEnable) {
				bankAccount.setStatus(new Integer(1));
			}

			for (AgentBankAccountDTO bankAccount : bankAccountsToAdd) {
				bankAccount.setStatus(new Integer(1));
			}

			for (AgentBankAccountDTO bankAccount : bankAccountsToDisable) {
				bankAccount.setStatus(new Integer(2));
			}

			agent.setBankAccounts(bankAccountsToEnable);
			agent.getBankAccounts().addAll(bankAccountsToDisable);

			addAgentIdToBankAccounts(agent, agent.getAgtId());

			newAgent = agentService.update(agent);

			if (!bankAccountsToAdd.isEmpty()) {
				agent.setBankAccounts(bankAccountsToAdd);
				agentService.addBankAccount(agent);
			}
		} else {
			addCreateDefaultValues(agent);

			newAgent = agentService.create(agent);

			addAgentIdToBankAccounts(agent, newAgent.getAgtId());
			agentService.addBankAccount(agent);
		}

		return newAgent;
	}

	private void addAgentIdToBankAccounts(AgentDTO agent, String id) {
		for (AgentBankAccountDTO bankAccount : agent.getBankAccounts()) {
			bankAccount.setAgent0(id);
		}

	}

	public AgentDTO mapAgent(DRMAgentDTO drmAgent) {

		AgentDTO agentDTO = drmMapper.asAgentDTO(drmAgent);
		List<AgentBankAccountDTO> bankAccounts = new ArrayList<AgentBankAccountDTO>();
		for (DRMBankAccountDTO bankAccount : drmAgent.getBka_bankaccounts_accounts_2()) {
			AgentBankAccountDTO bankAccountDTO = drmMapper.asAgentBankAccountDTO(bankAccount);
			if (bankAccount.getDeleted().equals("1")) {
				bankAccountDTO.setStatus(new Integer(2));
			}
			bankAccounts.add(bankAccountDTO);
		}

		agentDTO.setBankAccounts(bankAccounts);

		// category mapping
		agentDTO.setCategory(drmLissiaMappingRepository.findMapping(TYPE_CATEGORY, drmAgent.getPartner_role_c()));

		agentDTO.setDocumentationLanguage(new Integer(0));

		// documentation language mapping
		String languageMapping = drmLissiaMappingRepository.findMapping(DOCUMENTATION_LANGUAGE_TYPE, drmAgent.getCommunication_language_c());
		if (languageMapping != null && !languageMapping.isEmpty()) {
			agentDTO.setDocumentationLanguage(Integer.valueOf(languageMapping));
		}

		// email mapping
		for (DRMEmailDTO email : drmAgent.getEmail()) {
			if (email.isPrimary_address()) {
				agentDTO.setEmail(email.getEmail_address());
				break;
			}
		}
		// drm status mapping
		agentDTO.setCrmStatus(StringUtils.truncate(drmAgent.getOnboarding_status_c(), 20));

		// map country
		agentDTO.setCountry(drmLissiaMappingRepository.findMapping(TYPE_COUNTRY, drmAgent.getShipping_address_country_c()));

		// map payment commissions
		if (drmAgent.getComm_to_be_paid_c() != null && !drmAgent.getComm_to_be_paid_c().isEmpty())
			agentDTO.setPaymentCommission(new Integer(drmLissiaMappingRepository.findMapping(TYPE_PAYMENT_COMMISSION, drmAgent.getComm_to_be_paid_c())));

		// map payment method
		if (drmAgent.getPayment_mode_c() != null && !drmAgent.getPayment_mode_c().isEmpty())
			agentDTO.setPaymentMethod(new Integer(drmLissiaMappingRepository.findMapping(TYPE_PAYMENT_METHOD, drmAgent.getPayment_mode_c())));

		// map desired report
		agentDTO.setDesiredReport(new Integer(drmAgent.isComm_report_c() ? 1 : 0));

		if (agentDTO.getCategory().trim().equals(DEPOSIT_BANK.trim())) {
			// set custody bank BIC
			agentDTO.setPaymentAccountBic(drmAgent.getCustody_bank_bic_code_c());
		} else {
			agentDTO.setPaymentAccountBic(drmAgent.getPayment_account_c());
		}

		// map status
		if (drmAgent.isDeleted() || (drmAgent.getInactive_reason_c() != null && !drmAgent.getInactive_reason_c().isEmpty())) {
			agentDTO.setStatus("2");
		} else {
			agentDTO.setStatus("1");

		}

		return agentDTO;

	}

	public void checkAgentBankAccounts(AgentDTO agent, Collection<AgentBankAccountDTO> toDisable, Collection<AgentBankAccountDTO> toEnable, Collection<AgentBankAccountDTO> toAdd) {
		if (agent != null && agent.getBankAccounts() != null && agent.getAgtId() != null && !StringUtils.isBlank(agent.getAgtId())) {
			List<AgentBankAccountEntity> existingBankAccounts = agentBankAccountService.getAgentBankAccountsByAgent(agent.getAgtId());

			for (AgentBankAccountDTO newBankAccount : agent.getBankAccounts()) {
				boolean alreadyExists = false;

				for (AgentBankAccountEntity existingBankAccount : existingBankAccounts) {
					if (sameBankAccount(existingBankAccount, newBankAccount)) {
						alreadyExists = true;
						if (newBankAccount.getStatus() != null && newBankAccount.getStatus().intValue() == 2) {
							toDisable.add(agentBankAccountMapper.asAgentBankAccountDTO(existingBankAccount));
							break;
						}
						toEnable.add(agentBankAccountMapper.asAgentBankAccountDTO(existingBankAccount));
						existingBankAccounts.remove(existingBankAccount);
						break;
					}
				}

				if (!alreadyExists) {
					toAdd.add(newBankAccount);
				}
			}

			toDisable.addAll(agentBankAccountMapper.asAgentBankAccountDTOs(existingBankAccounts));
		}
	}

	private boolean sameBankAccount(AgentBankAccountEntity existingBankAccount, AgentBankAccountDTO newBankAccount) {

		if (existingBankAccount.getIban().trim().equals(newBankAccount.getIban().trim())
				&& existingBankAccount.getBic().trim().equals(newBankAccount.getBic().trim())
				&& existingBankAccount.getAccountName().trim().equals(newBankAccount.getAccountName().trim())
				&& existingBankAccount.getAccountCurrency().trim().equals(newBankAccount.getAccountCurrency().trim())
				&& existingBankAccount.getBankName().trim().equals(newBankAccount.getBankName().trim())) {
			return true;
		} else {
			return false;
		}
	}

	public void addCreateDefaultValues(AgentDTO agent) {
		agent.setBranch("1");
		agent.setType(0);
		agent.setLimitCode(1);
		agent.setLimitAmount(new BigDecimal(0));
		agent.setPaymentCode(1);
		agent.setCreatedProcess(USER);
		agent.setCreatedBy(USER);
		agent.setCentralizedCommunication(false);
		agent.setPaymentFrequency(3);
	}

	public void addUpdateDefaultValues(AgentDTO agent) {
		agent.setModifyProcess(USER);
		agent.setModifyBy(USER);
		agent.setTerminationBasis(agent.getDateOfTermination() == null ? 1 : 0);
	}

	/**
	 * Get the Lissia agent id if it exists
	 * 
	 * @param agent
	 * @return agent id
	 */
	public String getLissiaAgentId(AgentDTO agent) {
		if (agent.getAgtId() != null && !agent.getAgtId().isEmpty()) {
			return agent.getAgtId();
		}

		if (agent.getCrmRefererence() != null && !agent.getCrmRefererence().isEmpty()) {
			Collection<AgentLightDTO> collection = agentService.getAgentWithPartnerID(agent.getCrmRefererence());

			if (!collection.isEmpty()) {
				ArrayList<AgentLightDTO> list = new ArrayList<>(collection);
				return list.get(0).getAgtId();
			}
		}

		return null;
	}
}
