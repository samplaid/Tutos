/**
 * 
 */
package lu.wealins.webia.services.core.utils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDetailsDTO;
import lu.wealins.common.dto.webia.services.SurrenderTotalReportDTO;

/**
 * @author NGA
 *
 */
@Component
public class SurrenderResultUtils {
	
	private static final StringBuilder SQL_SURRENDER_RESULT_DETAILS = new StringBuilder();
	private static final StringBuilder SQL_SURRENDER_RESULT_TOTAL = new StringBuilder();
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	static {
		SQL_SURRENDER_RESULT_DETAILS.append(" with n as ( ");
		SQL_SURRENDER_RESULT_DETAILS.append(
				" select TRN_TAX_ID, PREV_TRN_TAX_ID from TRANSACTION_TAX t where TRN_TAX_ID = :transactionId ");
		SQL_SURRENDER_RESULT_DETAILS.append(" 	union all  ");
		SQL_SURRENDER_RESULT_DETAILS.append(
				" 	select t1.TRN_TAX_ID, t1.PREV_TRN_TAX_ID from TRANSACTION_TAX t1, n where n.PREV_TRN_TAX_ID = t1.trn_tax_id ");
		SQL_SURRENDER_RESULT_DETAILS.append(" ) select ");

		SQL_SURRENDER_RESULT_DETAILS.append(" t.policy as POLICY, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" t.TRN_DATE, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" t.TRN_TYPE, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" e.PREMIUM_DATE, e.PREMIUM_VALUE_BEFORE, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" t.POLICY_VALUE, ");

		SQL_SURRENDER_RESULT_DETAILS.append(" t.TRN_TAX_ID, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" t.PREV_TRN_TAX_ID, ");
		SQL_SURRENDER_RESULT_DETAILS
				.append(" CASE WHEN t.TRN_TYPE = 'PREM' THEN 0 ELSE t.TRN_NET_AMT END WITH_NET_AMT, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" e.PREMIUM_VALUE_AFTER, ");
		SQL_SURRENDER_RESULT_DETAILS.append(" e.SPLIT_PERCENT, e.CAPITAL_GAIN_AMT ");

		SQL_SURRENDER_RESULT_DETAILS.append(" from TRANSACTION_TAX t ");
		SQL_SURRENDER_RESULT_DETAILS.append(" join TRANSACTION_TAX_DETAILS e on (t.TRN_TAX_ID = e.TRN_TAX_ID) ");
		SQL_SURRENDER_RESULT_DETAILS.append(" join n on (n.TRN_TAX_ID = t.TRN_TAX_ID) ");
		SQL_SURRENDER_RESULT_DETAILS.append(" order by t.policy, t.ORIGIN_ID, t.TRN_DATE, e.PREMIUM_DATE option (maxrecursion 0) ; ");
		SQL_SURRENDER_RESULT_DETAILS.append(" ");

		// REQUEST TO GET TOTAL PREMIUM AND WITHDRAWAL TO DISPLAY IN REPORT DETAILS
		SQL_SURRENDER_RESULT_TOTAL.append(" with n as ( ");
		SQL_SURRENDER_RESULT_TOTAL
				.append(" select TRN_TAX_ID, TRN_TYPE, TRN_NET_AMT, PREV_TRN_TAX_ID from TRANSACTION_TAX t  ");
		SQL_SURRENDER_RESULT_TOTAL.append(" where t.TRN_TAX_ID = :transactionId  ");
		SQL_SURRENDER_RESULT_TOTAL.append(" union all  ");
		SQL_SURRENDER_RESULT_TOTAL.append(" select t1.TRN_TAX_ID, t1.TRN_TYPE, t1.TRN_NET_AMT, t1.PREV_TRN_TAX_ID ");
		SQL_SURRENDER_RESULT_TOTAL.append(" from TRANSACTION_TAX t1, n  ");
		SQL_SURRENDER_RESULT_TOTAL.append(" where n.PREV_TRN_TAX_ID = t1.TRN_TAX_ID ");
		SQL_SURRENDER_RESULT_TOTAL
				.append(" ) select TRN_TYPE, sum(TRN_NET_AMT) TOTAL from n where TRN_TYPE in ('PREM', 'WITH','SURR','MATU') ");
		SQL_SURRENDER_RESULT_TOTAL.append(" group by TRN_TYPE  option (maxrecursion 0) ;");

	}


	public static String getSurrenderResultDetailRequest() {
		return SQL_SURRENDER_RESULT_DETAILS.toString();
	}

	public static String getSurrenderResultTotalRequest() {
		return SQL_SURRENDER_RESULT_TOTAL.toString();
	}

	public List<SurrenderReportResultDetailsDTO> retrieveSurrenderReportDetails(Long transactionId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("transactionId", transactionId);
		return jdbcTemplate.query(SQL_SURRENDER_RESULT_DETAILS.toString(), paramMap,
				new SurrenderReportResultDetailsRowMapper());
	}

	public Map<String, BigDecimal> retrieveSurrenderReportTotal(Long transactionId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("transactionId", transactionId);
		List<SurrenderTotalReportDTO> results = jdbcTemplate.query(SQL_SURRENDER_RESULT_TOTAL.toString(), paramMap,
				new SurrenderReportResultTotalsRowMapper());
		if (results == null || results.isEmpty()) {
			return new HashMap<String, BigDecimal>();
		}
		
		return results.stream().collect(Collectors.groupingBy(SurrenderTotalReportDTO::getEventType,
				Collectors.reducing(BigDecimal.ZERO, SurrenderTotalReportDTO::getTotalAmount, BigDecimal::add)));
		
	}

	private final class SurrenderReportResultDetailsRowMapper implements RowMapper<SurrenderReportResultDetailsDTO> {
		@Override
		public SurrenderReportResultDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			SurrenderReportResultDetailsDTO resultDeatail = new SurrenderReportResultDetailsDTO();
			resultDeatail.setPolicy(rs.getString("POLICY"));
			resultDeatail.setTransactiondate(rs.getDate("TRN_DATE"));
			resultDeatail.setTransactionTaxEventType(rs.getString("TRN_TYPE"));
			resultDeatail.setPremiumDate(rs.getDate("PREMIUM_DATE"));
			resultDeatail.setPremiumValueBefore(rs.getBigDecimal("PREMIUM_VALUE_BEFORE"));
			resultDeatail.setPolicyValue(rs.getBigDecimal("POLICY_VALUE"));
			resultDeatail.setWithNetAmount(rs.getBigDecimal("WITH_NET_AMT"));
			resultDeatail.setPremiumValueAfter(rs.getBigDecimal("PREMIUM_VALUE_AFTER"));
			resultDeatail.setSplitPercent(rs.getBigDecimal("SPLIT_PERCENT"));
			resultDeatail.setCapitalGainAmount(rs.getBigDecimal("CAPITAL_GAIN_AMT"));
			return resultDeatail;
		}

	}

	private final class SurrenderReportResultTotalsRowMapper implements RowMapper<SurrenderTotalReportDTO> {
		@Override
		public SurrenderTotalReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			SurrenderTotalReportDTO detailsTotalReport = new SurrenderTotalReportDTO();
			detailsTotalReport.setEventType(StringUtils.strip(rs.getString("TRN_TYPE")));
			detailsTotalReport.setTotalAmount(rs.getBigDecimal("TOTAL"));
			return detailsTotalReport;
		}

	}
}
