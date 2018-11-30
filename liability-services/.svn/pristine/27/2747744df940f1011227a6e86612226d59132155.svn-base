package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.liability.services.ExchangeRateDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.liability.services.core.business.ExchangeRateService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyValuationHoldingService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.utils.PolicyValuationUtils;

@Service
@Transactional
public class PolicyValuationServiceImpl implements PolicyValuationService {

	private static final Logger logger = LoggerFactory.getLogger(PolicyValuationServiceImpl.class);

	private static final String sqlBrokerPolicies = "select distinct p.pol_id from POLICY_AGENT_SHARES pas, policies p where p.pol_id=pas.FK_POLICIESPOL_ID and pas.STATUS=1 and p.STATUS=1 and pas.TYPE=5 and pas.AGENT=?";
	private static final String sqlHolderPolicies = "SELECT distinct p.pol_id from CLI_POL_RELATIONSHIPS cpr, policies p where p.pol_id=cpr.FK_POLICIESPOL_ID and cpr.STATUS=1 and cpr.TYPE in (1,2,3) and cpr.CLIENT=?";

	@Autowired
	private PolicyValuationUtils policyValuationUtils;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private PolicyValuationHoldingService policyValuationHoldingService;

	@Override
	public PolicyValuationDTO getBrokerPolicyValuation(String agtId, String otherCurrency) {

		PolicyValuationDTO valuation = new PolicyValuationDTO();
		valuation.setOtherCurrency(otherCurrency);

		getLastValuation(valuation, sqlBrokerPolicies, new Object[] { agtId });

		return valuation;
	}

	@Override
	public PolicyValuationDTO getHolderPolicyValuation(String cliId, String otherCurrency) {

		PolicyValuationDTO valuation = new PolicyValuationDTO();
		valuation.setOtherCurrency(otherCurrency);

		getLastValuation(valuation, sqlHolderPolicies, new Object[] { cliId });

		return valuation;
	}

	private void getLastValuation(PolicyValuationDTO valuation, String policySQL, Object[] params) {
		if (StringUtils.isBlank(valuation.getOtherCurrency())) {
			valuation.setOtherCurrency("EUR");
		}
		String sql = "select sum(total) from ( " + " select sum(prices) a, currency b, ISNULL(E.MID_RATE,1) c, "
				+ "(CASE WHEN RECIPROCAL=0 THEN (sum(prices) * ISNULL(E.MID_RATE,1)) "
				+ " WHEN RECIPROCAL=1 THEN (sum(prices) / ISNULL(E.MID_RATE,1)) END) total, "
				+ "ISNULL(E.TO_CURRENCY, currency) CURRENCY from (" + " select sum(units)*p1.price prices, p1.currency "
				+ " from POLICY_FUND_HOLDINGS h, funds f,FUND_PRICES p1  "
				+ " where p1.RECORD_TYPE=1 and p1.price_type=1 and p1.FK_FUNDSFDS_ID=h.fund and f.fds_id=h.fund "
				+ " and units > 0 "
				+ " and p1.date0 = (select max(date0) from FUND_PRICES p2 where p2.RECORD_TYPE=1 and p2.price_type=1 and p2.FK_FUNDSFDS_ID=p1.FK_FUNDSFDS_ID group by p2.FK_FUNDSFDS_ID) "
				+ " and h.FK_POLICIESPOL_ID in ( " + policySQL + " ) "
				+ " group by f.fds_id,p1.price, p1.currency) T left join "
				+ " (select from_currency,to_currency,MID_RATE,date0, RECIPROCAL from (select from_currency, to_currency, MID_RATE,date0,row_number() over(partition by from_currency, to_currency order by date0 desc) as rn, RECIPROCAL from EXCHANGE_RATES where status = 1 and date0 <= getdate()) as T "
				+ " where rn = 1 " + " union all "
				+ " select 'EUR' as FROM_CURRENCY, 'EUR' as TO_CURRENCY, 1 AS MID_RATE, getdate() as date0, 1 as RECIPROCAL) "
				+ " E on E.FROM_CURRENCY=T.currency and E.TO_CURRENCY='" + valuation.getOtherCurrency() + "' "
				+ " group by T.currency, E.TO_CURRENCY, MID_RATE, E.RECIPROCAL) M group by M.CURRENCY";

		BigDecimal total = new BigDecimal(0);
		try {
			total = jdbcTemplate.queryForObject(sql, params, BigDecimal.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		valuation.setTotalOtherCurrency(total);
	}

	@Override
	public PolicyValuationDTO getPolicyValuation(String policy, Date date) {
		return this.getPolicyValuation(policy, date, null);
	}

	/**
	 * The purpose of this function is to calculate the policy valuation. The
	 * valuations are calculated in fund currency, then in policy currency, if
	 * required in a third currency.
	 * 
	 * Since the fee rate and the commission rate are the same for all the
	 * holdings, we get them once and then populate them to each holding.
	 * 
	 * 
	 * @author DUO
	 * @return Policy valuation data structure
	 * @see PolicyValuationDTO
	 */
	@Override
	public PolicyValuationDTO getPolicyValuation(String policy, Date date, String otherCurrency) {

		PolicyValuationDTO dto = new PolicyValuationDTO();
		dto.setPolicyId(policy);
		dto.setDate(date);
		dto.setOtherCurrency(otherCurrency);

		List<PolicyValuationHoldingDTO> holdings = policyValuationHoldingService.getPolicyValuationHoldings(policy, date);

		// Get the policy currency
		dto.setPolicyCurrency(policyService.getPolicyCurrency(policy));

		dto.setHoldings(holdings);

		// Calculate the holding value in fund currency
		_value_fund_curreny(dto);

		// Calculate each holding value and the total holdings' value in policy
		// currency
		_value_policy_currency(dto, false);

		// Calculate each holding value and the total value in a specified
		// currency.
		_value_other_currency(dto, false);

		return dto;
	}

	@Override
	public PolicyValuationDTO getPolicyAfterValuation(String policy, Date date, Long tran) {
		PolicyValuationDTO dto = new PolicyValuationDTO();
		dto.setPolicyId(policy);
		dto.setDate(date);
		dto.setOtherCurrency(null);

		List<PolicyValuationHoldingDTO> holdings = policyValuationHoldingService.getPolicyAfterValuation(policy, tran);
		dto.setHoldings(holdings);

		// Calculate the holding value in fund currency
		_value_fund_curreny(dto);

		// Calculate each holding value and the total holdings' value in policy
		// currency
		_value_policy_currency(dto, true);

		// Calculate each holding value and the total value in a specified
		// currency.
		_value_other_currency(dto, true);

		return dto;
	}


	/**
	 * Calculate the policy value in a currency other than that of the policy.
	 * 
	 * @param valuation
	 */
	private void _value_other_currency(PolicyValuationDTO valuation, boolean usedHoldingDate) {

		if (StringUtils.isEmpty(valuation.getOtherCurrency())) {
			return;
		}

		BigDecimal total = BigDecimal.ZERO;

		for (PolicyValuationHoldingDTO h : valuation.getHoldings()) {

			// The target currency is same as the fund currency.
			if (h.getFundCurrency().trim().equals(valuation.getOtherCurrency().trim())) {

				h.setOtherCurrencyDate(valuation.getDate());
				h.setOtherCurrencyRate(BigDecimal.ONE);
				h.setHoldingValueOtherCurrency(h.getHoldingValueFundCurreny());

			} else if (valuation.getPolicyCurrency().trim().equals(valuation.getOtherCurrency().trim())) {

				h.setOtherCurrencyDate(h.getPolicyCurrencyDate());
				h.setOtherCurrencyRate(h.getPolicyCurrencyRate());
				h.setHoldingValueOtherCurrency(h.getHoldingValuePolicyCurrency());

			} else {
				Date dt = usedHoldingDate ? h.getPriceDate() : valuation.getDate();
				ExchangeRateDTO r = exchangeRateService.getExchangeRate(h.getFundCurrency().trim(),
						valuation.getOtherCurrency().trim(), dt);

				if (r != null) {
					h.setOtherCurrencyDate(r.getDate0());

					if (BooleanUtils.toBooleanObject(r.getReciprocal())) {

						h.setOtherCurrencyRate(BigDecimal.ONE.divide(r.getMidRate(), 7, RoundingMode.HALF_UP));
						BigDecimal t = h.getHoldingValueFundCurreny().divide(r.getMidRate(), 2, RoundingMode.HALF_UP);
						h.setHoldingValueOtherCurrency(t);

					} else {

						h.setOtherCurrencyRate(r.getMidRate());
						BigDecimal t = h.getHoldingValueFundCurreny().multiply(r.getMidRate()).setScale(2,
								RoundingMode.HALF_UP);
						h.setHoldingValueOtherCurrency(t);
					}
				}
			}

			BigDecimal s = h.getHoldingValueOtherCurrency() != null ? h.getHoldingValueOtherCurrency()
					: BigDecimal.ZERO;
			total = total.add(s);
		}

		valuation.setTotalOtherCurrency(total);
	}

	/**
	 * Calculate the policy value in policy currency. If the position is in
	 * another currency than that of the policy, the conversion must be done.
	 * 
	 * @param valuation
	 */
	private void _value_policy_currency(PolicyValuationDTO valuation, boolean usedHoldingDate) {
		BigDecimal total = BigDecimal.ZERO;

		if (StringUtils.isEmpty(valuation.getPolicyCurrency())) {
			valuation.setTotalPolicyCurrency(total);
			return;
		}

		for (PolicyValuationHoldingDTO h : valuation.getHoldings()) {

			// The target currency is same as the fund currency.
			if (h.getFundCurrency().trim().equals(valuation.getPolicyCurrency().trim())) {

				h.setPolicyCurrencyDate(valuation.getDate());
				h.setPolicyCurrencyRate(BigDecimal.ONE);
				h.setHoldingValuePolicyCurrency(h.getHoldingValueFundCurreny());

			} else {
				Date dt = usedHoldingDate ? h.getPriceDate() : valuation.getDate();

				ExchangeRateDTO r = exchangeRateService.getExchangeRate(h.getFundCurrency().trim(),
						valuation.getPolicyCurrency().trim(), dt);

				if (r != null) {
					h.setPolicyCurrencyDate(r.getDate0());

					if (BooleanUtils.toBooleanObject(r.getReciprocal())) {

						h.setPolicyCurrencyRate(BigDecimal.ONE.divide(r.getMidRate(), 7, RoundingMode.HALF_UP));
						BigDecimal t = h.getHoldingValueFundCurreny().divide(r.getMidRate(), 2, RoundingMode.HALF_UP);
						h.setHoldingValuePolicyCurrency(t);

					} else {

						h.setPolicyCurrencyRate(r.getMidRate());
						BigDecimal t = h.getHoldingValueFundCurreny().multiply(r.getMidRate()).setScale(2,
								RoundingMode.HALF_UP);
						h.setHoldingValuePolicyCurrency(t);
					}

				}
			}

			BigDecimal s = h.getHoldingValuePolicyCurrency() != null ? h.getHoldingValuePolicyCurrency()
					: BigDecimal.ZERO;
			total = total.add(s);

			logger.info("exDate : " + h.getPolicyCurrencyDate());
			logger.info("exRate : " + h.getPolicyCurrencyRate());
			logger.info("HoldingValuePolicyCurrency : " + h.getHoldingValuePolicyCurrency());

		}
		valuation.setTotalPolicyCurrency(total);
	}

	/**
	 * Calculate the value of each holding in the currency of the fund.
	 * 
	 * @param valuation
	 */
	private void _value_fund_curreny(PolicyValuationDTO valuation) {

		// Fund value = units x fund price
		for (PolicyValuationHoldingDTO h : valuation.getHoldings()) {

			BigDecimal t = h.getUnits().multiply(h.getPrice());
			h.setHoldingValueFundCurreny(t.setScale(2, RoundingMode.HALF_UP));
		}

	}

	@Override
	public List<PolicyTransactionValuationDTO> getPolicyValuationAfterTransaction(Long transactionId) {
		String request = PolicyValuationUtils.getPolicyValuationRequest();
		List<PolicyTransactionValuationDTO> policyTransactionValuation = jdbcTemplate.query(request,
				new Object[] { BigDecimal.valueOf(transactionId) }, new RowMapper<PolicyTransactionValuationDTO>() {
					@Override
					public PolicyTransactionValuationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						PolicyTransactionValuationDTO policyValuation = new PolicyTransactionValuationDTO();
						policyValuation.setPolId(rs.getString(2));
						policyValuation.setFund(rs.getString(3));
						policyValuation.setUnits(rs.getBigDecimal(4));
						policyValuation.setPriceDate(rs.getDate(5));
						policyValuation.setPrice(rs.getBigDecimal(6));
						policyValuation.setFundCurrency(rs.getString(7));
						policyValuation.setPolicyCurrency(rs.getString(8));
						policyValuation.setFundType(rs.getString(9));

						return policyValuation;
					}

				});

		return policyValuationUtils.getConvertedValuations(policyTransactionValuation);
	}

}
