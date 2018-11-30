/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.liability.services.core.persistence.repository.ExchangeRateRepository;

@Component
public class TransactionSqlRequestUtils {

	private static final StringBuilder SQL_POLICY_TRANSACTION = new StringBuilder();
	@Autowired
	ExchangeRateRepository exchangeRateRepository;

	@Autowired
	ExchangeRateUtils exchangeRateUtils;

	static {

		SQL_POLICY_TRANSACTION.append(" WITH POLICY_TRANSACTIONS AS ( ");
		SQL_POLICY_TRANSACTION.append(
				" SELECT 'WITH' TRN_TYPE, t.POLICY, t.TRN_ID, t.EVENT_TYPE,t.EFFECTIVE_DATE, t.CURRENCY FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_POLICY_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_POLICY_TRANSACTION.append(" and t.EVENT_TYPE = 15  ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" SELECT 'WITH' TRN_TYPE, t.POLICY, t.TRN_ID, t.EVENT_TYPE,t.EFFECTIVE_DATE, t.CURRENCY FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_POLICY_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_POLICY_TRANSACTION.append(" and t.EVENT_TYPE = 4  ");
		SQL_POLICY_TRANSACTION.append(" and t.COVERAGE <> 1 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_POLICY_TRANSACTION.append(
				" and not exists (SELECT * FROM TRANSACTIONS t1 WHERE t1.POLICY = t.POLICY and t1.EFFECTIVE_DATE = t.EFFECTIVE_DATE ");
		SQL_POLICY_TRANSACTION.append(" and t1.STATUS not in (6,5) and t1.EVENT_TYPE = 4 and t1.COVERAGE = 1 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1)) ");

		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" SELECT 'SURR' TRN_TYPE,t.POLICY, t.TRN_ID, t.EVENT_TYPE, t.EFFECTIVE_DATE, t.CURRENCY  FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_POLICY_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_POLICY_TRANSACTION.append(" and t.EVENT_TYPE = 4  ");
		SQL_POLICY_TRANSACTION.append(" and t.COVERAGE = 1 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");

		SQL_POLICY_TRANSACTION.append("  union ");

		SQL_POLICY_TRANSACTION.append(
				" SELECT 'SURR' TRN_TYPE,t.POLICY, t.TRN_ID, t.EVENT_TYPE, t.EFFECTIVE_DATE, t.CURRENCY  FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_POLICY_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_POLICY_TRANSACTION.append(" and t.EVENT_TYPE = 4  ");
		SQL_POLICY_TRANSACTION.append(" and t.COVERAGE <> 1 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM TRANSACTIONS t1 WHERE t1.POLICY = t.POLICY and t1.EFFECTIVE_DATE = t.EFFECTIVE_DATE ");
		SQL_POLICY_TRANSACTION.append(" and t1.STATUS not in (6,5) and t1.EVENT_TYPE = 4 and t1.COVERAGE = 1 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1)) ");

		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" SELECT 'MATU' TRN_TYPE,t.POLICY, t.TRN_ID, t.EVENT_TYPE, t.EFFECTIVE_DATE, t.CURRENCY  FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_POLICY_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_POLICY_TRANSACTION.append(" and t.EVENT_TYPE = 21 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");

		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" SELECT 'PREM' TRN_TYPE,t.POLICY, t.TRN_ID, t.EVENT_TYPE, t.EFFECTIVE_DATE, t.CURRENCY  FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_POLICY_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_POLICY_TRANSACTION.append(" and t.EVENT_TYPE = 2 ");
		SQL_POLICY_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_POLICY_TRANSACTION.append(" ), ");

		SQL_POLICY_TRANSACTION.append(" SIGNE as (  ");
		SQL_POLICY_TRANSACTION.append(
				" select s.ATR_ID, -1 as signe from ACCOUNT_TRANSACTIONS s where ((s.DBCR='D' and s.STATUS=1) or (s.DBCR='C' and s.STATUS in (2,5,7))) ");
		SQL_POLICY_TRANSACTION.append(" union ");
		SQL_POLICY_TRANSACTION.append(
				" select s.ATR_ID, 1 as signe from ACCOUNT_TRANSACTIONS s where ((s.DBCR='C' and s.STATUS=1) or (s.DBCR='D' and s.STATUS in (2,5,7))) ");
		SQL_POLICY_TRANSACTION.append(" ), ");

		SQL_POLICY_TRANSACTION.append(" NET_TRANSACTION AS ( ");

		SQL_POLICY_TRANSACTION.append(
				" select TRN_ID, t.CURRENCY, sum ((at.amount*s.signe)) as 'net', at.CURRENCY as 'ACC_CURRENCY' ");
		SQL_POLICY_TRANSACTION.append(" from POLICY_TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION.append(" join ACCOUNT_TRANSACTIONS at  on t.TRN_ID = at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION.append(" join SIGNE s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION.append(" where ( t.EVENT_TYPE = 15 and at.account in ('PTBM') ) ");
		SQL_POLICY_TRANSACTION.append(" group by TRN_ID,t.CURRENCY , at.CURRENCY ");

		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" select TRN_ID, t.CURRENCY, sum ((at.amount*s.signe)) as 'net', at.CURRENCY as 'ACC_CURRENCY' ");
		SQL_POLICY_TRANSACTION.append(" from POLICY_TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION.append(" join ACCOUNT_TRANSACTIONS at  on t.TRN_ID = at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION.append(" join SIGNE s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION.append(" where ( t.EVENT_TYPE = 4 and at.account in ('PTBM') ) ");
		SQL_POLICY_TRANSACTION.append(" group by TRN_ID,t.CURRENCY , at.CURRENCY  ");

		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" select TRN_ID, t.CURRENCY, sum ((at.amount*s.signe)) as 'net', at.CURRENCY as 'ACC_CURRENCY' ");
		SQL_POLICY_TRANSACTION.append(" from POLICY_TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION.append(" join ACCOUNT_TRANSACTIONS at  on t.TRN_ID = at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION.append(" join SIGNE s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION.append(" where ( t.EVENT_TYPE = 21 and at.account in ('PTBM') ) ");
		SQL_POLICY_TRANSACTION.append(" group by TRN_ID,t.CURRENCY , at.CURRENCY ");

		SQL_POLICY_TRANSACTION.append(" union ");

		SQL_POLICY_TRANSACTION.append(
				" select TRN_ID, t.CURRENCY, sum ((at.amount*s.signe)) as 'net', at.CURRENCY as 'ACC_CURRENCY' ");
		SQL_POLICY_TRANSACTION.append(" from POLICY_TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION.append(" join ACCOUNT_TRANSACTIONS at  on t.TRN_ID = at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION.append(" join SIGNE s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION
				.append(" where (t.EVENT_TYPE=2  and at.account in ('PREMRN','PREMFY', 'POLFEEI', 'POLFEEIR', 'PREMTAX')) ");
		SQL_POLICY_TRANSACTION.append(" group by TRN_ID,t.CURRENCY , at.CURRENCY ");
		SQL_POLICY_TRANSACTION.append(" ) ");

		SQL_POLICY_TRANSACTION.append(
				" SELECT TRN_TYPE, EFFECTIVE_DATE, COUNT(pt.TRN_ID) TRN_COUNT, abs(sum(net)) NET_TRN_AMOUNT, pc.CURRENCY as 'TRN_CURRENCY',nt.ACC_CURRENCY,  MAX(pt.TRN_ID) LAST_TRN_ID ");
		SQL_POLICY_TRANSACTION.append(" FROM POLICY_TRANSACTIONS pt  ");
		SQL_POLICY_TRANSACTION.append(" JOIN NET_TRANSACTION nt ON pt.TRN_ID = nt.TRN_ID ");
		SQL_POLICY_TRANSACTION.append(" Left join POLICIES pc on pc.POL_ID = pt.POLICY");
		SQL_POLICY_TRANSACTION.append(" where pt.POLICY =  ? ");
		SQL_POLICY_TRANSACTION.append(" GROUP BY  TRN_TYPE, pt.EFFECTIVE_DATE, pc.CURRENCY, nt.ACC_CURRENCY ");
		SQL_POLICY_TRANSACTION.append(" ORDER BY  pt.EFFECTIVE_DATE DESC; ");

	}

	public static String getPolicyTransactionsRequest() {
		return SQL_POLICY_TRANSACTION.toString();
	}

	public List<FrenchTaxPolicyTransactionDTO> getConvertedFrenchTaxtransactions(
			List<FrenchTaxPolicyTransactionDTO> policyTransactionsTaxs) {

		if (policyTransactionsTaxs == null || policyTransactionsTaxs.isEmpty()) {
			return policyTransactionsTaxs;
		}
		
		List<FrenchTaxPolicyTransactionDTO> convertedTransactions = policyTransactionsTaxs.stream().map(transactionTax -> {
			
					if (transactionTax != null && transactionTax.getTransactionCurrency() != null
							&& transactionTax.getAccountCurrency() != null
							&& !transactionTax.getTransactionCurrency().trim()
									.equalsIgnoreCase(transactionTax.getAccountCurrency().trim())
					&& transactionTax.getNetTransactionAmount() != null && BigDecimal.ZERO.compareTo(transactionTax.getNetTransactionAmount()) != 0) {
				
						BigDecimal convertedNetAmount = exchangeRateUtils.convert(
								transactionTax.getNetTransactionAmount(), transactionTax.getAccountCurrency().trim(),
								transactionTax.getTransactionCurrency().trim(), transactionTax.getEffectiveDate());
				transactionTax.setNetTransactionAmount(convertedNetAmount);
						FrenchTaxPolicyTransactionDTO frenchTaxPolicyTransaction = new FrenchTaxPolicyTransactionDTO();
						frenchTaxPolicyTransaction.setAccountCurrency(transactionTax.getAccountCurrency());
						frenchTaxPolicyTransaction.setEffectiveDate(transactionTax.getEffectiveDate());
						frenchTaxPolicyTransaction.setLastTransactionId(transactionTax.getLastTransactionId());
						frenchTaxPolicyTransaction.setNetTransactionAmount(convertedNetAmount);
						frenchTaxPolicyTransaction.setPolicy(transactionTax.getPolicy());
						frenchTaxPolicyTransaction.setTransactionCurrency(transactionTax.getTransactionCurrency());
						frenchTaxPolicyTransaction
								.setTransactionsAggregated(transactionTax.getTransactionsAggregated());
						frenchTaxPolicyTransaction.setTransactionType(transactionTax.getTransactionType());

						return frenchTaxPolicyTransaction;
			}

			return transactionTax;
			
		}).collect(Collectors.toList());
		return convertedTransactions;
	}

}
