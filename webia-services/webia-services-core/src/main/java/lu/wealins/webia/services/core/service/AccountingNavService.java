package lu.wealins.webia.services.core.service;

import java.text.ParseException;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;
import lu.wealins.common.dto.webia.services.GetAccountingNavRequest;
import lu.wealins.common.dto.webia.services.GetAccountingNavResponse;

public interface AccountingNavService {

	/**
	 * Get an accounting nav function of a fund and a date
	 * 
	 * @return the accounting nav
	 */
	GetAccountingNavResponse getAccountingNavByFundIdAndNavDate(GetAccountingNavRequest request);
	

	/**
	 * Get an accounting nav function of a fund and a date
	 * 
	 * @return the accounting nav
	 */
	GetAccountingNavResponse getAccountingNavByIsinAndNavDate(GetAccountingNavRequest request);

	/**
	 * Check CloturedVni .
	 * 
	 * @param request
	 *            CloturedVniInjectionControlRequest to check
	 * @return response CloturedVniInjectionControlResponse
	 */
	CloturedVniInjectionControlResponse checkAccountingNav(CloturedVniInjectionControlRequest request)
			throws ParseException;

	/**
	 * Inject CloturedVni .
	 * 
	 * @param request
	 *            CloturedVniInjectionControlRequest to check
	 * @return response CloturedVniInjectionControlResponse
	 */
	CloturedVniInjectionControlResponse injectAccountingNav(SecurityContext context, AccountingNavToInject request)
			throws ParseException;

}
