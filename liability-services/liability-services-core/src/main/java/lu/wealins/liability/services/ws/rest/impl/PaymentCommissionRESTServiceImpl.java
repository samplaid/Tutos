package lu.wealins.liability.services.ws.rest.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.AgentBankAccountService;
import lu.wealins.liability.services.core.persistence.entity.AgentBankAccountEntity;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.persistence.repository.AgentRepository;
import lu.wealins.liability.services.ws.rest.PaymentCommissionRESTService;
import lu.wealins.common.dto.liability.services.AgentDataForTransferDTO;
import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;

@Component
public class PaymentCommissionRESTServiceImpl implements PaymentCommissionRESTService {
	private static final Integer DESIRE_REPORT = 1;
	private static final Integer ACTIVE_AGENT_BANK = 1;

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(PaymentCommissionRESTServiceImpl.class);

	@Autowired
	private AgentBankAccountService agentBankAccountService;
	
	@Autowired
	private AgentRepository agentRepository;

	@Override
	public Response extractAgentDataForTransfers(SecurityContext context, String codeWealins, List<BrokerProcessDTO> brokerProcessDTO) {
		AgentDataForTransfersResponse response = new AgentDataForTransfersResponse();
		List<AgentDataForTransferDTO> listOfDataResponse = new ArrayList<AgentDataForTransferDTO>();
		try {
			for (BrokerProcessDTO broker : brokerProcessDTO) {
				AgentEntity agent = agentRepository.findByAgtId(broker.getBrokerId());
				
				if (agent != null && DESIRE_REPORT.equals(agent.getDesiredReport())) { 
					
					Set<AgentBankAccountEntity> listBankAccounts =  agent.getBankAccounts();
					if (!CollectionUtils.isEmpty(listBankAccounts)) {
						Boolean mapped = false;
						AgentBankAccountEntity agentBankAccountEntityForMapping = null;
						AgentBankAccountEntity agentBankAccountEntityEurForMapping = null;
						for (AgentBankAccountEntity agentBankAccountEntity : listBankAccounts) {
							if (ACTIVE_AGENT_BANK.equals(agentBankAccountEntity.getStatus()) && broker.getCurrency().equals(agentBankAccountEntity.getCommPaymentCurrency() == null ? null : agentBankAccountEntity.getCommPaymentCurrency().trim())) {
								agentBankAccountEntityForMapping = agentBankAccountEntity;
							}
							if (ACTIVE_AGENT_BANK.equals(agentBankAccountEntity.getStatus()) && broker.getCurrency().equals("EUR")) {
								agentBankAccountEntityEurForMapping = agentBankAccountEntity;
							}
						}
						if (agentBankAccountEntityForMapping == null && agentBankAccountEntityEurForMapping != null) {
							agentBankAccountEntityForMapping = agentBankAccountEntityEurForMapping;
						} else if (agentBankAccountEntityForMapping == null) {
							for (AgentBankAccountEntity agentBankAccountEntity : listBankAccounts) {
								agentBankAccountEntityForMapping = agentBankAccountEntity;
								break;
							}
						}
						if (agentBankAccountEntityForMapping != null) {
							List<AgentBankAccountEntity> brokerWealinsBank = new ArrayList<AgentBankAccountEntity>();
							try {
								validateData(agentBankAccountEntityForMapping.getIban(), broker);
								validateData(agentBankAccountEntityForMapping.getBic(), broker);
								validateData(agentBankAccountEntityForMapping.getAccountName(), broker);
								validateData(agent.getPaymentAccountBic(), broker);
								if (agentBankAccountEntityForMapping.getAgent() != null) {
									validateData(String.valueOf(agentBankAccountEntityForMapping.getAgent().getPaymentMethod()), broker);
								} else {
									throw new Exception("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]");
								}
								
								brokerWealinsBank = agentBankAccountService.getAgentBankAccountByAgentAndCurrencyAndBic(codeWealins, broker.getCurrency(), agent.getPaymentAccountBic());
								if (CollectionUtils.isEmpty(brokerWealinsBank)) {
									if (!"EUR".equals(broker.getCurrency())) {
										brokerWealinsBank = agentBankAccountService.getAgentBankAccountByAgentAndCurrencyAndBic(codeWealins, "EUR", agent.getPaymentAccountBic());
									} else {
										//response.setError("Incompleted Data for BROKER WEALINS currency = " + broker.getCurrency() + " and bic = " + agent.getPaymentAccountBic());
										//return Response.ok(response).build();
										throw new Exception("Incompleted Data for BROKER WEALINS currency = " + broker.getCurrency() + " and bic = " + agent.getPaymentAccountBic());
									}
								}
								validateData(brokerWealinsBank.get(0).getAccountName(), broker);
								validateData(brokerWealinsBank.get(0).getAccountName(), broker);
								validateData(brokerWealinsBank.get(0).getAccountName(), broker);
							} catch (Exception e) {
								logger.error("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]" + e);
								continue;
							}
							
							
							AgentDataForTransferDTO agentData = new AgentDataForTransferDTO();
							agentData.setBrokerId(broker.getBrokerId());
							agentData.setCurrency(broker.getCurrency());
							agentData.setIbanDonord(brokerWealinsBank.get(0).getIban());
							agentData.setSwiftDonord(brokerWealinsBank.get(0).getBic());
							agentData.setLibDonord(brokerWealinsBank.get(0).getAccountName());
							agentData.setIbanBenef(agentBankAccountEntityForMapping.getIban());
							agentData.setSwiftBenef(agentBankAccountEntityForMapping.getBic());
							agentData.setLibBenef(agentBankAccountEntityForMapping.getAccountName());
							agentData.setTransferType(String.valueOf(agentBankAccountEntityForMapping.getAgent().getPaymentMethod()));
							listOfDataResponse.add(agentData);
							mapped = true;
						}
						if (!mapped)  {
							// response.setError("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]");
							// return Response.ok(response).build();
							logger.error("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]");
						}
					} else {
						// response.setError("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]");
						// return Response.ok(response).build();
						logger.error("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]");
					}
				}
			}
			response.setAgents(listOfDataResponse);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.error("Error during mapping broker info " + e);
			response.setError(e.getMessage() == null ? "java.lang.NullPointerException" : e.getMessage());
			return Response.ok(response).build();
		}
	}
	
	private void validateData(String data, BrokerProcessDTO broker) throws Exception {
		if (StringUtils.isEmpty(data == null ? null : data.trim())) {
			throw new Exception("Incompleted Data, check broker=[" + broker.getBrokerId() + "]" +  " currency=[" + broker.getCurrency() + "]");
		}
	}

}
