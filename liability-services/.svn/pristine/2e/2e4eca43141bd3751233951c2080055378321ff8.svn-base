package lu.wealins.liability.services.core.business.impl;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.FundNameService;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;

@Component
@Transactional
public class FundNameServiceImpl implements FundNameService {

	private static final String POOL = "_#Pool#_";
	private static final String POA = "_#POA#_";
	private static final String PE = "_#PE#_";
	
	@Autowired
	private AgentService agentService;
	
	@Override
	public String generate(FundLiteDTO fund) {
		
		Assert.notNull(fund);
		Assert.notNull(fund.getFdsId());
		
		StringBuilder sb = new StringBuilder();
		
		if (fund.getFundType() == 2 || "FE".equalsIgnoreCase(fund.getFundSubType())) {
			// FE
			sb.append(fund.getName());
			
			if ( StringUtils.hasText(fund.getIsinCode()) ) {
				sb.append(" - ");
				sb.append(fund.getIsinCode());
			}
			
			if ( StringUtils.hasText(fund.getDepositBank()) ) {
				
				AgentDTO bank = agentService.getAgent(fund.getDepositBank());
				
				if (bank != null && StringUtils.hasText(bank.getName())) {
					sb.append(" - ");
					sb.append(bank.getName());
				}
			}
			
		} else if ("FIC".equalsIgnoreCase(fund.getFundSubType())) {
			// FIC : Code + Name + Custodian Bank
			
			sb.append(fund.getFdsId());
			sb.append(" - ");
			sb.append(fund.getName());
			
			if ( StringUtils.hasText(fund.getDepositBank()) ) {
				
				AgentDTO bank = agentService.getAgent(fund.getDepositBank());
				
				if (bank != null && StringUtils.hasText(bank.getName())) {
					sb.append(" - ");
					sb.append(bank.getName());
				}
			}
			
		} else if ("FID".equalsIgnoreCase(fund.getFundSubType())) {
			// FID :  (IBAN + Root)/Fund ID + Asset Manager + Custodian Bank + PE + POA + Pool
			
			if (StringUtils.hasText(fund.getIban())) {
				sb.append(fund.getIban());
				
				if (StringUtils.hasText(fund.getAccountRoot())) {
					sb.append(" - ");
					sb.append(fund.getAccountRoot());
				}
				
			} else {
				sb.append(fund.getFdsId());
			}
			
			if(StringUtils.hasText(fund.getName())) {
				sb.append(" - ");
				sb.append(fund.getName());
			}
			
			if (StringUtils.hasText(fund.getAssetManager())) {
				
				AgentDTO am = agentService.getAgent(fund.getAssetManager());
				
				if (am != null && StringUtils.hasText(am.getName())) {
					sb.append(" - ");
					sb.append(am.getName());
				}
			}

			if ( StringUtils.hasText(fund.getDepositBank()) ) {
				
				AgentDTO bank = agentService.getAgent(fund.getDepositBank());
				
				if (bank != null && StringUtils.hasText(bank.getName())) {
					sb.append(" - ");
					sb.append(bank.getName());
				}
			}
			
			if (BooleanUtils.isTrue(fund.getPrivateEquity())) {
				sb.append(" - ");
				sb.append(PE);
			}
			
			if (BooleanUtils.isTrue(fund.getPoa())) {
				sb.append(" - ");
				sb.append(POA);				
			}
			
			if (BooleanUtils.isTrue(fund.getPool())) {
				sb.append(" - ");
				sb.append(POOL);				
			}
			
		} else if ("FAS".equalsIgnoreCase(fund.getFundSubType())) {
			// FAS :  (IBAN + Root)/Fund ID + Advisor + Custodian Bank + PE + POA + Pool
			
			if (StringUtils.hasText(fund.getIban())) {
				sb.append(fund.getIban());
				
				if (StringUtils.hasText(fund.getAccountRoot())) {
					sb.append(" - ");
					sb.append(fund.getAccountRoot());
				}
				
			} else {
				sb.append(fund.getFdsId());
			}
			
			if(StringUtils.hasText(fund.getName())) {
				sb.append(" - ");
				sb.append(fund.getName());
			}
			
			if (StringUtils.hasText(fund.getFinancialAdvisor())) {
				
				AgentDTO am = agentService.getAgent(fund.getFinancialAdvisor());
				
				if (am != null && StringUtils.hasText(am.getName())) {
					sb.append(" - ");
					sb.append(am.getName());
				}
			}

			if ( StringUtils.hasText(fund.getDepositBank()) ) {
				
				AgentDTO bank = agentService.getAgent(fund.getDepositBank());
				
				if (bank != null && StringUtils.hasText(bank.getName())) {
					sb.append(" - ");
					sb.append(bank.getName());
				}
			}
			
			if (BooleanUtils.isTrue(fund.getPrivateEquity())) {
				sb.append(" - ");
				sb.append(PE);
			}
			
			if (BooleanUtils.isTrue(fund.getPoa())) {
				sb.append(" - ");
				sb.append(POA);				
			}
			
			if (BooleanUtils.isTrue(fund.getPool())) {
				sb.append(" - ");
				sb.append(POOL);				
			}
			
		}
		
		return sb.toString();
	}

}
