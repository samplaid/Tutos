/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.TransactionHistoryDetailsDTO;

@Component
public class TransactionDetailsUtils {


	private static final StringBuilder SQL_TRANSACTIONS_DETAILS = new StringBuilder();

	private static final String SQL_TRANSACTIONS_FUND_SATUS = "SELECT e.DESCRIPTION AS FUND_STATUS FROM OPTION_DETAILS e WHERE e.NUMBER = :statusCode and e.FK_OPTIONSOPT_ID='STATUS_FTR'  ";

	private static final StringBuilder SQL_TRANSACTIONS_DETAILS_COMMISSION = new StringBuilder();
	private static final Integer SCALE = new Integer(6);
	static {

		SQL_TRANSACTIONS_DETAILS.append(" SELECT  a.EVENT_TYPE   AS EVENT_TYPE,  ");
		SQL_TRANSACTIONS_DETAILS.append(" b.FUND_SUB_TYPE     AS FUND_SUB_TYPE, ");
		SQL_TRANSACTIONS_DETAILS.append(" e.DESCRIPTION     AS DESCRIPTION,  ");
		SQL_TRANSACTIONS_DETAILS.append(" a.STATUS     AS FUND_STATUS_CODE,  ");
		SQL_TRANSACTIONS_DETAILS.append(" a.DATE0     AS EFFECTIVE_DATE,  ");
		SQL_TRANSACTIONS_DETAILS
				.append(" c.AGT_ID + ' - ' + b.NAME + ' - '+ c.NAME  collate SQL_Latin1_General_CP1_CI_AS AS FUND_NAME,  ");
		SQL_TRANSACTIONS_DETAILS.append(" a.PRICE                     AS PRICE,  ");
		SQL_TRANSACTIONS_DETAILS.append(" sum(a.UNITS)                AS UNITS,  ");
		SQL_TRANSACTIONS_DETAILS.append(" sum(a.VALUE_FUND_CCY)       AS VALUE_FUND, ");
		SQL_TRANSACTIONS_DETAILS.append(" a.FUND_CURRENCY     AS FUND_CURRENCY, ");
		SQL_TRANSACTIONS_DETAILS.append(" a.EXCHANGE_RATE     AS EXCHANGE_RATE, ");
		SQL_TRANSACTIONS_DETAILS.append(" sum(a.VALUE_POL_CCY)     AS VALUE_POLICY, ");
		SQL_TRANSACTIONS_DETAILS.append(" a.POLICY_CURRENCY AS POLICY_CURRENCY, ");
		SQL_TRANSACTIONS_DETAILS.append(" b.FDS_ID          AS         FUND_ID, ");

		SQL_TRANSACTIONS_DETAILS.append(" a.FK_FUND_TRANSACFTD_ID         AS          FUND_TRANSACFTD_ID  ");

		SQL_TRANSACTIONS_DETAILS.append(" FROM FUND_TRANSACTIONS a  ");
		SQL_TRANSACTIONS_DETAILS.append(" left join FUNDS b on (b.FDS_ID = a.FUND) ");
		SQL_TRANSACTIONS_DETAILS.append(" join TRANSACTIONS tr on (tr.TRN_ID = a.TRANSACTION0)  ");
		SQL_TRANSACTIONS_DETAILS.append(" left join AGENTS c on (c.AGT_ID = b.DEPOSIT_BANK and c.CATEGORY = 'DB')  ");
		SQL_TRANSACTIONS_DETAILS
				.append(" join OPTION_DETAILS e on (e.NUMBER= tr.STATUS and e.FK_OPTIONSOPT_ID='STATUS_FTR')  ");
		SQL_TRANSACTIONS_DETAILS.append(
				" WHERE a.TRANSACTION0 in (:transactionsId) and tr.EVENT_TYPE = :eventType ");
		SQL_TRANSACTIONS_DETAILS.append(
				" and a.FK_POLICIESPOL_ID = :policyId  ");
		SQL_TRANSACTIONS_DETAILS
				.append("  and ((a.FK_POLICY_FUND_PFI_ID is not null AND a.FK_FUND_TRANSACFTD_ID is null and a.STATUS = :status ) ");
		SQL_TRANSACTIONS_DETAILS
				.append("  OR (a.FK_POLICY_FUND_PFI_ID  is  null AND a.FK_FUND_TRANSACFTD_ID is not null and a.STATUS not in (5) ) ");
		SQL_TRANSACTIONS_DETAILS
				.append("  OR (a.FK_POLICY_FUND_PFI_ID  is  null AND a.FK_FUND_TRANSACFTD_ID is null AND tr.EVENT_TYPE not in (21,23,19) and a.EVENT_TYPE not in (12) and a.STATUS = :status)  ");

		SQL_TRANSACTIONS_DETAILS
				.append(" OR (a.FK_POLICY_FUND_PFI_ID  is  null AND a.FK_FUND_TRANSACFTD_ID is null AND a.EVENT_TYPE in (37,38))");
		SQL_TRANSACTIONS_DETAILS.append(
				" OR (a.FK_POLICY_FUND_PFI_ID  is  not null AND a.FK_FUND_TRANSACFTD_ID is not null and a.STATUS not in (5) )) ");

		SQL_TRANSACTIONS_DETAILS.append(
				" GROUP BY a.EVENT_TYPE,b.FDS_ID ,b.FUND_SUB_TYPE,e.DESCRIPTION,a.STATUS,a.DATE0,a.PRICE, a.FUND_CURRENCY,a.POLICY_CURRENCY,a.EXCHANGE_RATE,a.FK_FUND_TRANSACFTD_ID ");
		SQL_TRANSACTIONS_DETAILS.append(" ,c.AGT_ID,b.NAME,c.NAME ");

		/// Calculate commission
		SQL_TRANSACTIONS_DETAILS_COMMISSION.append(
				" select AMOUNT AS COMMISSION, CURRENCY, name + ' - ' + CENTRE collate SQL_Latin1_General_CP1_CI_AS AS AGENT_LABEL ");
		SQL_TRANSACTIONS_DETAILS_COMMISSION.append(" From   ACCOUNT_TRANSACTIONS d, AGENTS a   ");
		SQL_TRANSACTIONS_DETAILS_COMMISSION
				.append(" WHERE  d.TRANSACTION0 in (:transactionsId) and d.ACCOUNT='AGENTBAL' and  d.centre = a.AGT_ID ");

	}

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public static String getTransactionsDetailsRequest() {
		return SQL_TRANSACTIONS_DETAILS.toString();
	}


	public PolicyTransactionsHistoryDetailsDTO retrieveTransactionsDetails(
			Map<BigDecimal, BigDecimal> transactionsDetails, Integer eventType, String policyId,
			BigDecimal grossAmount, BigDecimal netAmount, Integer status) {
		Map<String, Object> paramMap = new HashMap<>();
		PolicyTransactionsHistoryDetailsDTO transactionHistoryDetails = new PolicyTransactionsHistoryDetailsDTO();
		Set<BigDecimal> transactions = transactionsDetails.keySet();
		paramMap.put("transactionsId", transactions);
		paramMap.put("eventType", eventType);
		paramMap.put("policyId", policyId);
		paramMap.put("status", status);

		List<TransactionHistoryDetailsDTO> transactionFundDetails = namedJdbcTemplate
				.query(
				SQL_TRANSACTIONS_DETAILS.toString(), paramMap,
				new TransactionsDetailsRowMapper());

		if (transactionFundDetails == null || transactionFundDetails.isEmpty()) {
			return transactionHistoryDetails;
		}


		Map<String, Object> paramComissionMap = new HashMap<>();
		paramComissionMap.put("transactionsId", transactions);

		List<TransactionHistoryDetailsDTO> transactionFundDetailsFiltered = new ArrayList<TransactionHistoryDetailsDTO>();

		transactionHistoryDetails.setTransactionHistoryDetails(transactionFundDetailsFiltered);
		List<Pair<String, Pair<BigDecimal, String>>> commissionsInfos = namedJdbcTemplate
				.query(SQL_TRANSACTIONS_DETAILS_COMMISSION.toString(), paramComissionMap,
						new CommisionDetailsRowMapper());

		Map<Integer, BigDecimal> transactionFundDetailsByType = transactionFundDetails.stream()
				.filter(detail -> (new Integer(37).equals(detail.getEventType())
						|| new Integer(38).equals(detail.getEventType())))
				.collect(Collectors.groupingBy(TransactionHistoryDetailsDTO::getEventType, Collectors
						.reducing(BigDecimal.ZERO, TransactionHistoryDetailsDTO::getValueFund, BigDecimal::add)));
		
		for (TransactionHistoryDetailsDTO details : transactionFundDetails) {
			if (new Integer(19).equals(eventType) && (new Integer(37).equals(details.getEventType())
					|| new Integer(38).equals(details.getEventType()))) {
					details.setSplit(calculateSplit(details.getUnits(), details.getPrice(),
							transactionFundDetailsByType.get(details.getEventType())));
				details.setStatus(getFundStatus(details.getFundStatusCode()));
			} else {
				details.setSplit(calculateSplit(details.getValuePol(), netAmount));
			}
			transactionFundDetailsFiltered.add(details);
		}
		
		if (commissionsInfos != null && !commissionsInfos.isEmpty()) {
			BigDecimal amount = commissionsInfos.stream().map(commission -> commission.getSecond().getFirst())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			transactionHistoryDetails.setCommission(amount!=null?amount.setScale(SCALE):amount);
			transactionHistoryDetails
					.setCommissionCurrency(StringUtils.stripToEmpty(commissionsInfos.get(0).getSecond().getSecond()));
			transactionHistoryDetails.setAgentLabel(commissionsInfos.get(0).getFirst());
		}

		return transactionHistoryDetails;
	}

	private final class TransactionsDetailsRowMapper implements RowMapper<TransactionHistoryDetailsDTO> {
		@Override
		public TransactionHistoryDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransactionHistoryDetailsDTO transactionsDetail = TransactionHistoryDetailsDTO.builder()
					.eventAs(rs.getInt("EVENT_TYPE")).withFundSubType(rs.getString("FUND_SUB_TYPE"))
					.withFundName(rs.getString("FUND_NAME"))
					.withPrice(rs.getBigDecimal("PRICE") != null ? rs.getBigDecimal("PRICE").abs().setScale(SCALE)
							: rs.getBigDecimal("PRICE"))
					.withUnits(rs.getBigDecimal("UNITS") != null ? rs.getBigDecimal("UNITS").abs().setScale(SCALE)
							: rs.getBigDecimal("UNITS"))
					.withValueFund(rs.getBigDecimal("VALUE_FUND") != null ? rs.getBigDecimal("VALUE_FUND").abs().setScale(SCALE)
							: rs.getBigDecimal("VALUE_FUND"))
					.withFundCurrency(rs.getString("FUND_CURRENCY")).withExchangeRate(rs.getBigDecimal("EXCHANGE_RATE"))
					.withValuePol(rs.getBigDecimal("VALUE_POLICY") != null ? rs.getBigDecimal("VALUE_POLICY").abs().setScale(SCALE)
							: rs.getBigDecimal("VALUE_POLICY"))
					.withPolicyCurrency(rs.getString("POLICY_CURRENCY"))
					.withStatus(rs.getString("DESCRIPTION"))
					.withFundId(rs.getString("FUND_ID"))
					.withEffectiveDate(rs.getDate("EFFECTIVE_DATE"))
					.withFundTransaction(rs.getString("FUND_TRANSACFTD_ID"))
					.withFundStatusCode(rs.getInt("FUND_STATUS_CODE"))
					.build();
			return transactionsDetail;
		}

	}

	private final class fundDetailsRowMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("FUND_STATUS");
		}
	}

	private final class CommisionDetailsRowMapper implements RowMapper<Pair<String, Pair<BigDecimal, String>>> {
		@Override
		public Pair<String, Pair<BigDecimal, String>> mapRow(ResultSet rs, int rowNum) throws SQLException {

			BigDecimal amount = rs.getBigDecimal("COMMISSION");
			Pair<BigDecimal, String> commissionInfo = Pair.of(amount== null ? BigDecimal.ZERO.setScale(SCALE):amount.setScale(SCALE),
					StringUtils.stripToEmpty(rs.getString("CURRENCY")));
			return Pair.of(StringUtils.stripToEmpty(rs.getString("AGENT_LABEL")), commissionInfo);
		}
	}

	private BigDecimal calculateSplit(BigDecimal units, BigDecimal price, BigDecimal netAmount) {
		
		if (units == null || BigDecimal.ZERO.compareTo(units) == 0 || price == null) {
			return units == null? null:units.setScale(SCALE);
		}

		if (netAmount == null || BigDecimal.ZERO.compareTo(netAmount) == 0) {
			return null;
		}

		return (units.multiply(price).multiply(BigDecimal.valueOf(100)).divide(netAmount, SCALE,
				BigDecimal.ROUND_HALF_UP)).abs();
	}

	private BigDecimal calculateSplit(BigDecimal currencyValue, BigDecimal netAmount) {

		if (netAmount == null || BigDecimal.ZERO.compareTo(netAmount) == 0 || currencyValue == null) {
			return null;
		}
		return (currencyValue.multiply(BigDecimal.valueOf(100)).divide(netAmount, SCALE,
				BigDecimal.ROUND_HALF_UP))
				.abs();
	}

	private String getFundStatus(Integer fundStatusCode) {
		if (fundStatusCode == null) {
			return StringUtils.EMPTY;
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("statusCode", fundStatusCode);

		List<String> status = namedJdbcTemplate.query(SQL_TRANSACTIONS_FUND_SATUS, paramMap,
				new fundDetailsRowMapper());
		if (status == null || status.isEmpty()) {
			return StringUtils.EMPTY;
		}
		return StringUtils.stripToEmpty(status.get(0));
	}

}
