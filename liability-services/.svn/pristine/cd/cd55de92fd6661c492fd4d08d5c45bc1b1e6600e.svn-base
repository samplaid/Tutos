/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;

/**
 * @author NGA
 *
 */
@Component
public class PolicyValuationUtils {

	private static final StringBuilder SQL_POLICY_VALUATION = new StringBuilder();

	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	ExchangeRateUtils exchangeRateUtils;

	static {
		SQL_POLICY_VALUATION.append(
				" WITH POLICY_AND_DATE AS ( SELECT POLICY, TRN_ID, EFFECTIVE_DATE, VALUE0 FROM TRANSACTIONS WHERE TRN_ID = ? ");
		SQL_POLICY_VALUATION.append(" )  ");
		SQL_POLICY_VALUATION.append(" , POL_HOLDINGS AS (  ");
		SQL_POLICY_VALUATION
				.append(" select ft.FK_POLICIESPOL_ID POL_ID, ft.fund FUND, sum(units) UNITS, max(ft.date0) DATE0 ");
		SQL_POLICY_VALUATION.append(" from POLICY_AND_DATE p   ");
		SQL_POLICY_VALUATION.append(" join TRANSACTIONS t on (p.POLICY = t.FK_POLICIESPOL_ID) ");
		SQL_POLICY_VALUATION.append(
				" join FUND_TRANSACTIONS ft on (p.POLICY = ft.FK_POLICIESPOL_ID and t.TRN_ID = ft.FK_TRANSACTIONSTRN_ID) ");
		SQL_POLICY_VALUATION.append(" where t.STATUS not in (5,6) and ft.STATUS = 1 and ( ");

		SQL_POLICY_VALUATION.append(" t.EFFECTIVE_DATE <= p.EFFECTIVE_DATE or ft.FK_TRANSACTIONSTRN_ID = p.TRN_ID ");
		SQL_POLICY_VALUATION.append(" or (t.FK_TRANSACTION_TRL_ID = p.TRN_ID) ");
		SQL_POLICY_VALUATION.append(" ) ");
		SQL_POLICY_VALUATION.append(" group by ft.FK_POLICIESPOL_ID, ft.fund having sum(units) > 0 ");
		SQL_POLICY_VALUATION.append(" )  ");
		SQL_POLICY_VALUATION.append(" , HOLDING_WITH_PRICES AS ( ");
		SQL_POLICY_VALUATION
				.append(" select ROW_NUMBER() over (partition by h.FUND order by fp.date0 desc) AS ROWNUM, ");
		SQL_POLICY_VALUATION.append(" h.POL_ID, rtrim(h.FUND) FUND, h.UNITS,  ");
		SQL_POLICY_VALUATION.append(
				" fp.DATE0 PRICE_DATE, PRICE, rtrim(fp.CURRENCY) CURRENCY, rtrim(p.CURRENCY) POLICY_CURRENCY, f.FUND_SUB_TYPE ");
		SQL_POLICY_VALUATION.append(" from POL_HOLDINGS h  ");
		SQL_POLICY_VALUATION.append(" join POLICY_AND_DATE c on (c.POLICY = h.POL_ID) ");
		SQL_POLICY_VALUATION.append(" join FUND_PRICES fp on (fp.FK_FUNDSFDS_ID = h.FUND) ");
		SQL_POLICY_VALUATION.append(" join FUNDS f on h.FUND = f.FDS_ID  ");
		SQL_POLICY_VALUATION.append(" join POLICIES p on (p.POL_ID = c.POLICY)  ");
		SQL_POLICY_VALUATION.append(" where fp.STATUS = 1 and fp.RECORD_TYPE = 1 and fp.PRICE_TYPE = 1 ");
		SQL_POLICY_VALUATION.append(" and (fp.DATE0 <= h.DATE0 or fp.DATE0 <= c.EFFECTIVE_DATE) ");
		SQL_POLICY_VALUATION.append(" ) ");
		SQL_POLICY_VALUATION.append(" SELECT * FROM HOLDING_WITH_PRICES WHERE ROWNUM = 1 order by 2,3; ");

	}

	public static String getPolicyValuationRequest() {
		return SQL_POLICY_VALUATION.toString();
	}

	public List<PolicyTransactionValuationDTO> getConvertedValuations(
			List<PolicyTransactionValuationDTO> policyValuations) {

		if (policyValuations == null || policyValuations.isEmpty()) {
			return policyValuations;
		}
		
		List<PolicyTransactionValuationDTO> convertedValuations = policyValuations.stream().map(valuation -> {
			
			if (valuation.getFundCurrency() != null && valuation.getPolicyCurrency() != null
					&& !valuation.getFundCurrency().trim().equalsIgnoreCase(valuation.getPolicyCurrency().trim())
					&& valuation.getPrice() != null && BigDecimal.ZERO.compareTo(valuation.getPrice()) != 0) {

				BigDecimal amount = exchangeRateUtils.convert(valuation.getPrice(), valuation.getFundCurrency(),
						valuation.getPolicyCurrency(), valuation.getPriceDate());

				PolicyTransactionValuationDTO policyValuation = new PolicyTransactionValuationDTO();
				policyValuation.setFund(valuation.getFund());
				policyValuation.setFundCurrency(valuation.getFundCurrency());
				policyValuation.setFundType(valuation.getFundType());
				policyValuation.setPolicyCurrency(valuation.getPolicyCurrency());
				policyValuation.setPolId(valuation.getPolId());
				policyValuation.setPrice(amount);
				policyValuation.setPriceDate(valuation.getPriceDate());
				policyValuation.setUnits(valuation.getUnits());

				return policyValuation;
			}

			return valuation;
			
		}).collect(Collectors.toList());
		
		return convertedValuations;
	}
}
