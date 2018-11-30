package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.GetAccountingPeriodResponse;
import lu.wealins.webia.services.core.service.AccountingPeriodService;
import lu.wealins.webia.services.ws.rest.AccountingPeriodRESTService;

@Component
public class AccountingPeriodRESTServiceImpl implements AccountingPeriodRESTService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountingPeriodRESTServiceImpl.class);

	@Autowired
	private AccountingPeriodService accountingPeriodService;

	@Override
	public GetAccountingPeriodResponse getAccountingNavByFundAndDate(SecurityContext context) {
		return accountingPeriodService.getActiveAccountingPeriod();
	}

}
