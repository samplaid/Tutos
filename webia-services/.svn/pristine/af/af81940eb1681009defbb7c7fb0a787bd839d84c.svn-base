package lu.wealins.webia.services.ws.rest.impl;

import java.text.ParseException;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;
import lu.wealins.webia.services.core.service.AccountingNavService;
import lu.wealins.webia.services.ws.rest.AccountingNavRESTService;
import lu.wealins.common.dto.webia.services.GetAccountingNavRequest;
import lu.wealins.common.dto.webia.services.GetAccountingNavResponse;



@Component
public class AccountingNavRESTServiceImpl implements AccountingNavRESTService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountingNavRESTServiceImpl.class);

	/**
	 * The accounting nav service
	 */
	@Autowired
	private AccountingNavService accountingNavService;

	@Override
	public GetAccountingNavResponse getAccountingNavByFundAndDate(SecurityContext context, GetAccountingNavRequest request) {
		return accountingNavService.getAccountingNavByFundIdAndNavDate(request);
	}


	@Override
	public CloturedVniInjectionControlResponse checkAccountingNav(SecurityContext context,
			CloturedVniInjectionControlRequest request) throws ParseException {
		return accountingNavService.checkAccountingNav(request);
	}

	@Override
	public CloturedVniInjectionControlResponse injectAccountingNav(SecurityContext context,
			AccountingNavToInject request) throws ParseException {
		return accountingNavService.injectAccountingNav(context, request);
	}
	

	@Override
	public GetAccountingNavResponse getAccountingNavByIsinAndDate(SecurityContext context,
			GetAccountingNavRequest request) {
		return accountingNavService.getAccountingNavByIsinAndNavDate(request);
	}
	

}
