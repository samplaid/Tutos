package lu.wealins.liability.services.core.business.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyCommissionRateDTO;
import lu.wealins.common.dto.liability.services.PolicyFeeRateDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.PolicyCommissionRateService;
import lu.wealins.liability.services.core.business.PolicyFeeRateService;
import lu.wealins.liability.services.core.business.PolicyValuationHoldingService;

@Service
public class PolicyValuationHoldingServiceImpl implements PolicyValuationHoldingService {
	
	/**
	 * Query to calculate a policy's position until a given date. The calculation is done in two steps, the first step does the sums of all the fund transactions since the policy commencement.
	 * 
	 * If the sum of a fund is zero, it means the fund is surrendered and it should not be taken into account.
	 * 
	 * The second step is to find the last available price of each holding in the position. The prices are given in the currencies of the funds, to calculate the value of the policy the exchange
	 * conversion will be needed.
	 * 
	 * 
	 */
	public static final String sqlValuation = "WITH POLICY_AND_DATE AS ( SELECT ? POLICY, ? DTVAL )    "
			+ ", POL_HOLDINGS AS (    " + "	select ft.FK_POLICIESPOL_ID POL_ID, ft.fund FUND, sum(units) UNITS    "
			+ "		from POLICY_AND_DATE p    " + "			join TRANSACTIONS t on (p.POLICY = t.FK_POLICIESPOL_ID)    "
			+ "			join FUND_TRANSACTIONS ft on (p.POLICY = ft.FK_POLICIESPOL_ID and t.TRN_ID = ft.FK_TRANSACTIONSTRN_ID)    "
			+ "		where t.STATUS = 1 and ft.STATUS = 1 and ft.DATE0 <= p.DTVAL    "
			+ "		group by ft.FK_POLICIESPOL_ID, ft.fund having sum(units) > 0    " + ")  "
			+ ", HOLDING_WITH_PRICES AS (    "
			+ "	select ROW_NUMBER() over (partition by h.FUND order by fp.date0 desc) AS ROWNUM,    "
			+ "		h.POL_ID, rtrim(h.FUND) FUND, h.UNITS, "
			+ "		fp.DATE0 PRICE_DATE, PRICE, rtrim(fp.CURRENCY) CURRENCY, rtrim(p.CURRENCY) POLICY_CURRENCY, f.FUND_SUB_TYPE    "
			+ "		from POL_HOLDINGS h    " + "			join FUND_PRICES fp on (fp.FK_FUNDSFDS_ID = h.FUND)    "
			+ "			join FUNDS f on h.FUND = f.FDS_ID    "
			+ "			join POLICY_AND_DATE c on (c.POLICY = h.POL_ID and fp.DATE0 <= c.DTVAL)    "
			+ "			join POLICIES p on (p.POL_ID = c.POLICY)    "
			+ "		where fp.STATUS = 1 and fp.RECORD_TYPE = 1 and fp.PRICE_TYPE = 1    " + ")    "
			+ "SELECT * FROM HOLDING_WITH_PRICES WHERE ROWNUM = 1 order by 2,3";

	private static final String sqlAfterValuation = "WITH POLICY_AND_DATE AS ( SELECT POLICY, TRN_ID, EFFECTIVE_DATE, VALUE0 FROM TRANSACTIONS WHERE TRN_ID = ?)  "
			+ ", POL_HOLDINGS AS (    "
			+ "	select ft.FK_POLICIESPOL_ID POL_ID, ft.fund FUND, sum(units) UNITS, max(ft.date0) DATE0 "
			+ "	from POLICY_AND_DATE p    " + "	join TRANSACTIONS t on (p.POLICY = t.FK_POLICIESPOL_ID)   "
			+ "	join FUND_TRANSACTIONS ft on (p.POLICY = ft.FK_POLICIESPOL_ID and t.TRN_ID = ft.FK_TRANSACTIONSTRN_ID)  "
			+ "   where t.STATUS = 1 and ft.STATUS = 1 and "
			+ " (t.EFFECTIVE_DATE < p.EFFECTIVE_DATE or ft.FK_TRANSACTIONSTRN_ID = p.TRN_ID "
			+ " or (t.EFFECTIVE_DATE = p.EFFECTIVE_DATE and ft.FK_TRANSACTIONSTRN_ID <= p.TRN_ID) "
			+ " or (t.EFFECTIVE_DATE = p.EFFECTIVE_DATE and t.EVENT_TYPE = 12 and ft.HOLDING_VALUATION = p.VALUE0)) "
			+ " 	group by ft.FK_POLICIESPOL_ID, ft.fund having sum(units) > 0    " + " ) "
			+ ", HOLDING_WITH_PRICES AS (    "
			+ "	select ROW_NUMBER() over (partition by h.FUND order by fp.date0 desc) AS ROWNUM,    "
			+ "	h.POL_ID, rtrim(h.FUND) FUND, h.UNITS, "
			+ "	fp.DATE0 PRICE_DATE, PRICE, rtrim(fp.CURRENCY) CURRENCY, rtrim(p.CURRENCY) POLICY_CURRENCY, f.FUND_SUB_TYPE    "
			+ "	from POL_HOLDINGS h   "
			+ "	join FUND_PRICES fp on (fp.FK_FUNDSFDS_ID = h.FUND and fp.DATE0 <= h.DATE0)    "
			+ "	join FUNDS f on h.FUND = f.FDS_ID    " + "	join POLICY_AND_DATE c on (c.POLICY = h.POL_ID) "
			+ "	join POLICIES p on (p.POL_ID = c.POLICY)    "
			+ "	where fp.STATUS = 1 and fp.RECORD_TYPE = 1 and fp.PRICE_TYPE = 1	" + " )    "
			+ " SELECT * FROM HOLDING_WITH_PRICES WHERE ROWNUM = 1 order by 2,3 ";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PolicyFeeRateService policyFeeRateService;
	@Autowired
	private PolicyCommissionRateService policyCommissionRateService;
	@Autowired
	private FundService fundService;

	@Override
	public List<PolicyValuationHoldingDTO> getPolicyValuationHoldings(String policy, Date date) {
		Object[] params = new Object[] { policy, date };
		return executeQuery(sqlValuation, params, policy);
	}

	@Override
	public List<PolicyValuationHoldingDTO> getPolicyAfterValuation(String policy, Long tran) {

		Object[] params = new Object[] { tran };

		return executeQuery(sqlAfterValuation, params, policy);
	}

	private List<PolicyValuationHoldingDTO> executeQuery(String query, Object[] params, String policy) {
		// Get the policy fee rate
		PolicyFeeRateDTO feeRate = policyFeeRateService.getPolicyFeeRate(policy);

		// Get the policy commission rate and the primary agent
		PolicyCommissionRateDTO crate = policyCommissionRateService.getPolicyCommissionRate(policy);

		return jdbcTemplate.query(query, params,
				new RowMapper<PolicyValuationHoldingDTO>() {

					@Override
					public PolicyValuationHoldingDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						PolicyValuationHoldingDTO h = new PolicyValuationHoldingDTO();

						h.setFundId(rs.getString(3));
						h.setUnits(rs.getBigDecimal(4));

						// Populate the fee & commission rates to each holding.
						if (feeRate != null) {
							h.setFeeRate(feeRate.getFeeRate());
						}

						if (crate != null) {
							h.setCommissionRate(crate.getCommissionRate());
							h.setAgent(crate.getAgentId());
						}

						h.setPriceDate(rs.getDate(5));
						h.setPrice(rs.getBigDecimal(6));
						h.setFundCurrency(rs.getString(7));
						h.setFundSubType(rs.getString(9));

						// Generate the fund display name
						FundLiteDTO fund = fundService.getFundLite(h.getFundId());
						h.setFundDisplayName(fund.getDisplayName());

						return h;
					}

				});
	}
}
