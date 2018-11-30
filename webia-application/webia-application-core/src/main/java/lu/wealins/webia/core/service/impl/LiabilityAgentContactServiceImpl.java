package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.webia.core.service.LiabilityAgentContactService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityAgentContactServiceImpl implements LiabilityAgentContactService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LiabilityAgentContactServiceImpl.class);
	
	
	private static final String LIABILITY_AGENT_CONTACT = "liability/agentContact/";
	private static final String LIABILITY_SAVE_AGENT_CONTACT = "liability/agentContact/save";

	private static final String CONTACT_OF_CPS = "CPS";
	
	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityFundService liabilityFundService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityAgentContactService#save(lu.wealins.webia.ws.rest.dto.AgentContactDTO, java.lang.String)
	 */
	@Override
	public AgentContactDTO save(AgentContactDTO agentContact) {
		AgentContactDTO ret = restClientUtils.post(LIABILITY_SAVE_AGENT_CONTACT, agentContact, AgentContactDTO.class);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityAgentContactService#find(java.lang.Long)
	 */
	@Override
	public AgentContactDTO find(Integer agentContactId) {
		if (agentContactId == null) {
			return null;
		}

		MultivaluedMap<String, Object> queryParam = new MultivaluedHashMap<String, Object>();
		queryParam.putSingle("id", agentContactId);
		return restClientUtils.get(LIABILITY_AGENT_CONTACT, "find", queryParam, AgentContactDTO.class);
	}
	
	@Override
	public AgentContactLiteDTO getFundDepositBankContactAgent(String fdsId) {
		FundDTO fund = liabilityFundService.getFund(fdsId);
		if (fund != null){
			AgentDTO depositBankAgent = fund.getDepositBankAgent();
			if (depositBankAgent != null && CollectionUtils.isNotEmpty(depositBankAgent.getAgentContacts())){
				return depositBankAgent.getAgentContacts().stream().filter(ac -> CONTACT_OF_CPS.equals(ac.getContactFunction()) && "1".equals(ac.getStatus())).findFirst().orElse(null);
			}
		}
		return null;
	}

	@Override
	public AgentContactLiteDTO getFundPaymentContactAgent(String fdsId) {
		
		FundDTO fund = liabilityFundService.getFund(fdsId);
		if (fund != null){
			if (!StringUtils.isEmpty(fund.getExDepositBankContact())) {
				MultivaluedMap<String, Object> queryParam = new MultivaluedHashMap<String, Object>();
				queryParam.putSingle("agentId", fund.getDepositBankAgent().getAgtId());
				queryParam.putSingle("contactId", fund.getExDepositBankContact());
				return restClientUtils.get(LIABILITY_AGENT_CONTACT, "findByAgentIdAndContactId", queryParam, AgentContactLiteDTO.class);
			}
		}
		return null;
	}
}
