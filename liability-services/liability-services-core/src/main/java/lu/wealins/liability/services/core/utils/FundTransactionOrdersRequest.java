/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import org.springframework.stereotype.Component;

/**
 * @author NGA
 *
 */
@Component
public class FundTransactionOrdersRequest {

	private static final StringBuilder SQL_FUND_TRANSACTION = new StringBuilder();

	static {

		SQL_FUND_TRANSACTION.append(" WITH POLICY_TRANSACTIONS AS ( ");

		// Rachat partiel avec des transactions effectives
		SQL_FUND_TRANSACTION.append(" SELECT 'WITH' TRN_TYPE, t.* FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_FUND_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_FUND_TRANSACTION.append(" and t.EVENT_TYPE = 15 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");

		SQL_FUND_TRANSACTION.append(" union ");

		// Rachat total mais sans un rachat total au meme jour sur la coverage 1, ce
		// rachat est considéré comme un rachat partiel.
		SQL_FUND_TRANSACTION.append(" SELECT 'WITH' TRN_TYPE, t.* FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_FUND_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_FUND_TRANSACTION.append(" and t.EVENT_TYPE = 4 ");
		SQL_FUND_TRANSACTION.append(" and t.COVERAGE <> 1 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_FUND_TRANSACTION.append(
				" and not exists (SELECT * FROM TRANSACTIONS t1 WHERE t1.POLICY = t.POLICY and t1.EFFECTIVE_DATE = t.EFFECTIVE_DATE ");
		SQL_FUND_TRANSACTION.append(" and t1.STATUS not in (6,5) and t1.EVENT_TYPE = 4 and t1.COVERAGE = 1 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1)) ");

		SQL_FUND_TRANSACTION.append(" union ");

		// Rachat total sur coverage 1
		SQL_FUND_TRANSACTION.append(" SELECT 'SURR' TRN_TYPE, t.* FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_FUND_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_FUND_TRANSACTION.append(" and t.EVENT_TYPE = 4  ");
		SQL_FUND_TRANSACTION.append(" and t.COVERAGE = 1 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");

		SQL_FUND_TRANSACTION.append(" union ");

		// Rachat total sur la coverage <> 1, et il existe un rachat total au meme jour
		// sur coverage 1
		SQL_FUND_TRANSACTION.append(" SELECT 'SURR' TRN_TYPE, t.* FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_FUND_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_FUND_TRANSACTION.append(" and t.EVENT_TYPE = 4  ");
		SQL_FUND_TRANSACTION.append(" and t.COVERAGE <> 1 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM TRANSACTIONS t1 WHERE t1.POLICY = t.POLICY and t1.EFFECTIVE_DATE = t.EFFECTIVE_DATE");
		SQL_FUND_TRANSACTION.append(" and t1.STATUS not in (6,5) and t1.EVENT_TYPE = 4 and t1.COVERAGE = 1 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1)) ");

		SQL_FUND_TRANSACTION.append(" union ");

		// Maturity
		SQL_FUND_TRANSACTION.append(" SELECT 'MATU' TRN_TYPE, t.* FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_FUND_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_FUND_TRANSACTION.append(" and t.EVENT_TYPE = 21 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");

		SQL_FUND_TRANSACTION.append(" union ");

		// Premium
		SQL_FUND_TRANSACTION.append(" SELECT 'PREM' TRN_TYPE, t.* FROM TRANSACTIONS t WHERE 1=1 ");
		SQL_FUND_TRANSACTION.append(" and t.STATUS not in (6,5) ");
		SQL_FUND_TRANSACTION.append(" and t.EVENT_TYPE = 2 ");
		SQL_FUND_TRANSACTION.append(
				" and exists (SELECT * FROM FUND_TRANSACTIONS ft where ft.FK_TRANSACTIONSTRN_ID = t.TRN_ID and ft.STATUS = 1) ");
		SQL_FUND_TRANSACTION.append(" )");

		// Result
		SQL_FUND_TRANSACTION.append(
				" SELECT pt.POLICY, pt.TRN_TYPE, pt.EFFECTIVE_DATE, pt.TRN_ID, abs(ft.VALUE_POL_CCY) as AMOUNT ");
		SQL_FUND_TRANSACTION.append(
				" FROM POLICY_TRANSACTIONS pt join FUND_TRANSACTIONS ft on (ft.FK_TRANSACTIONSTRN_ID = pt.TRN_ID) ");
		SQL_FUND_TRANSACTION.append(
				" where pt.POLICY = ? and pt.TRN_TYPE= ? and pt.EFFECTIVE_DATE = ? and ft.UNIT_TYPE = 1 and ft.STATUS = 1 ");
		SQL_FUND_TRANSACTION.append(" ORDER BY EFFECTIVE_DATE; ");

	}

	public static String getPolicyFundTransactionOrdersRequest() {
		return SQL_FUND_TRANSACTION.toString();
	}

}
