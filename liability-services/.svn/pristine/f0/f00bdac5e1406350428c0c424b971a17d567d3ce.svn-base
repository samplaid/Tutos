package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.AgentBankAccountService;
import lu.wealins.liability.services.core.persistence.entity.AgentBankAccountEntity;
import lu.wealins.liability.services.core.persistence.repository.AgentBankAccountRepository;

@Service
public class AgentBankAccountServiceImpl implements AgentBankAccountService {
	@Autowired
	private AgentBankAccountRepository agentBankAccountRepository;

	@Override
	public AgentBankAccountEntity getAgentBankAccountByAgent(String agent) {

		return agentBankAccountRepository.findByAgent0(agent);
	}

	@Override
	public List<AgentBankAccountEntity> getAgentBankAccountByAgentAndCurrency(String agent, String currency) {
		List<Integer> excludeStatus = new ArrayList<Integer>();
		excludeStatus.add(0);
		return agentBankAccountRepository.findByAgent0AndCommPaymentCurrencyAndStatusNotIn(agent, currency, excludeStatus);
	}

	@Override
	public List<AgentBankAccountEntity> getAgentBankAccountByAgentAndCurrencyAndBic(String agent, String currency, String bic) {
		List<Integer> excludeStatus = new ArrayList<Integer>();
		excludeStatus.add(0);
		return agentBankAccountRepository.findByAgent0AndCommPaymentCurrencyAndBicAndStatusNotIn(agent, currency, bic, excludeStatus);
	}

	@Override
	public List<AgentBankAccountEntity> getAgentBankAccountsByAgent(String agent) {
		return agentBankAccountRepository.findByAgentId(agent);
	}
}
