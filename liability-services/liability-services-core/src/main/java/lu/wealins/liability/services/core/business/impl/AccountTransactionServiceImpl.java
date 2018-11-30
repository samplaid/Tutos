package lu.wealins.liability.services.core.business.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AdminFeesDTO;
import lu.wealins.common.dto.liability.services.enums.EventType;
import lu.wealins.liability.services.core.business.AccountTransactionService;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

	private static final String ADMIN_FEES_QUERY = "select sum(CASE DBCR WHEN 'C' then -amount else AMOUNT end) as TRF_MT, a.currency as TRF_CURRENCY, a.centre from ACCOUNT_TRANSACTIONS a, funds b " +
			"where TRANSACTION0 in (select distinct trn_id from TRANSACTIONS where FK_POLICIESPOL_ID = ? and EFFECTIVE_DATE = ? and EVENT_TYPE = " + EventType.ADMINISTRATION_FEE.getEvtId() + ") " +
			"and account = 'ULDTF' " +
			"and a.centre = b.FDS_ID " +
			"and b.FUND_SUB_TYPE in ('FID','FAS') " +
			"group by a.CENTRE, a.CURRENCY";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Collection<AdminFeesDTO> getAdminFees(String policyId, Date effectiveDate) {

		Object[] params = { policyId, effectiveDate };

		List<AdminFeesDTO> adminFees = jdbcTemplate.query(ADMIN_FEES_QUERY, params, new RowMapper<AdminFeesDTO>() {

			@Override
			public AdminFeesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminFeesDTO h = new AdminFeesDTO();

				h.setAmount(rs.getBigDecimal(1));
				h.setCurrency(rs.getString(2));
				h.setCentre(rs.getString(3));

				return h;
			}

		});

		return adminFees;
	}

}
