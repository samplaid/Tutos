package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AdminFeesDTO;
import lu.wealins.liability.services.core.business.AccountTransactionService;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.AccountTransactionRESTService;

@Component
public class AccountTransactionRESTServiceImpl implements AccountTransactionRESTService {

	@Autowired
	private AccountTransactionService accountTransactionRESTService;
	@Autowired
	private CalendarUtils calendarUtils;

	@Override
	public Collection<AdminFeesDTO> getAdminFees(SecurityContext context, String policyId, String effectiveDate) {
		Date dt = calendarUtils.createDate(effectiveDate);

		return accountTransactionRESTService.getAdminFees(policyId, dt);
	}
}
