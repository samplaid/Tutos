package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDetailsInputDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.TransactionFundDTO;
import lu.wealins.common.dto.liability.services.enums.FundTransactionStatus;
import lu.wealins.liability.services.core.business.TransactionService;
import lu.wealins.liability.services.core.mapper.TransactionMapper;
import lu.wealins.liability.services.core.persistence.entity.TransactionEntity;
import lu.wealins.liability.services.core.persistence.repository.TransactionRepository;
import lu.wealins.liability.services.core.persistence.specification.TransactionSpecifications;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.TransactionDetailsUtils;
import lu.wealins.liability.services.core.utils.TransactionSqlRequestUtils;


@Service
public class TransactionServiceImpl implements TransactionService {
	private static final StringBuilder SQL_POLICY_TRANSACTION_HISTORY = new StringBuilder();
	private static final StringBuilder SQL_POLICY_TRANSACTION_ELEMENTS = new StringBuilder();

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	TransactionDetailsUtils transactionDetailsUtils;

	@Autowired
	private CalendarUtils calendarUtils;


	static {
		SQL_POLICY_TRANSACTION_HISTORY.append(" WITH MY_POLICY AS ( SELECT ? POLICY ), ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" signe as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select s.ATR_ID, -1 as signe from ACCOUNT_TRANSACTIONS s where ((s.DBCR='D' and s.STATUS=1) or (s.DBCR='C' and s.STATUS in (2,5,7))) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select s.ATR_ID, 1 as signe from ACCOUNT_TRANSACTIONS s where ((s.DBCR='C' and s.STATUS=1) or (s.DBCR='D' and s.STATUS in (2,5,7))) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ),");

		// gross
		SQL_POLICY_TRANSACTION_HISTORY.append(" gross as (");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, sum(at.amount*s.signe) as 'gross' ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where ((t.EVENT_TYPE=15 and at.account='WDNPEN') or (t.EVENT_TYPE=4 and at.account='SURR')) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select t.TRN_ID, t.value0 as 'gross' from TRANSACTIONS t  ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.EVENT_TYPE in (2,3,6,8,12,13,17,19,21,23,30,35,62,63,71,78,82,86)");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select distinct  t.TRN_ID, (case when (ft.UNITS>=0) then t.value0  else -t.value0 end) as 'gross' from TRANSACTIONS t  ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join FUND_TRANSACTIONS ft on (t.TRN_ID = ft.FK_TRANSACTIONSTRN_ID) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.EVENT_TYPE in (44) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// arbitrage_amount
		SQL_POLICY_TRANSACTION_HISTORY.append(" arbitrage_amount as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID,sum(isnull(at.amount,0 )) as 'net' from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join transactions t on t.TRN_ID=at.FK_TRANSACTIONSTRN_ID and at.account='ULISWF' ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.EVENT_TYPE in (19,23) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where (t.EVENT_TYPE in (19,23) )");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group By TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// net_arbitrage
		SQL_POLICY_TRANSACTION_HISTORY.append(" net_arbitrage as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select asf.TRN_ID, abs(t.value0)- (asf.net) as  'net' from transactions t ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join arbitrage_amount asf on asf.TRN_ID = t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join ACCOUNT_TRANSACTIONS at on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID and at.account='ULISWF' ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.EVENT_TYPE in (19,23) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where (t.EVENT_TYPE in (19,23) ) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select t.TRN_ID, abs(t.value0) as  'net' from transactions t ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join ACCOUNT_TRANSACTIONS at on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where (t.EVENT_TYPE in (19,23) ) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.TRN_ID not in (select TRN_ID from arbitrage_amount) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// net
		SQL_POLICY_TRANSACTION_HISTORY.append(" net as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, sum (at.amount*s.signe) as 'net' ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where (t.EVENT_TYPE=2 and at.EVENT_TYPE in (1,2) and at.account in ('PREMFY','PREMRN')) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select t.TRN_ID, t.value0 as 'net' from TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.EVENT_TYPE in (3,4,6,8,12,13,15,17,21,30,35,62,63,71,78,82,86) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select distinct  t.TRN_ID, (case when (ft.UNITS>=0) then t.value0  else -t.value0 end) as 'net' from TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join FUND_TRANSACTIONS ft on (t.TRN_ID = ft.FK_TRANSACTIONSTRN_ID) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.EVENT_TYPE in (44) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, net from net_arbitrage ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// fee
		SQL_POLICY_TRANSACTION_HISTORY.append(" fee as ( ");

		// added to manage withdrawal that handle fee like management fees
		SQL_POLICY_TRANSACTION_HISTORY
				.append("  select t.TRN_ID, sum (at.amount*s.signe) as 'fee' from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY
				.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID and t.EVENT_TYPE=15 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join net on net.TRN_ID = at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join gross on gross.TRN_ID = at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(
				" where (t.EVENT_TYPE=15 and at.account='ADM') and ((t.status = 5 and at.DBCR='C') or t.status <>5) and abs(gross.gross) - abs(net.net) > 0  and t.TRN_ID NOT IN( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID from ACCOUNT_TRANSACTIONS at  ");
		SQL_POLICY_TRANSACTION_HISTORY
				.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID and t.EVENT_TYPE=15 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(
				" where (t.EVENT_TYPE=15 and at.account='PENALTY') and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by TRN_ID )");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ");
		// end

		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, sum (at.amount*s.signe) as 'fee' from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID and t.EVENT_TYPE=15 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(
				" where (t.EVENT_TYPE=15 and at.account='PENALTY') and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append("  group by TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, sum(at.amount) as 'fee' from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join TRANSACTIONS t on t.TRN_ID=at.FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where (t.EVENT_TYPE in (19,23) and at.account='ULISWF') ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, sum(at.amount*s.signe) as 'fee' from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where ((t.EVENT_TYPE=4 and at.account='PENALTY') or (t.EVENT_TYPE=2 and at.EVENT_TYPE in (1,2) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and at.account in ('POLFEEI','POLFEEIR'))) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by TRN_ID	");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// tax
		SQL_POLICY_TRANSACTION_HISTORY.append(" tax as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select TRN_ID, at.amount*s.signe as 'tax' from ACCOUNT_TRANSACTIONS at ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join TRANSACTIONS t on at.FK_TRANSACTIONSTRN_ID=t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join signe s on s.ATR_ID=at.ATR_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where at.EVENT_TYPE in (2,1) and at.account='PREMTAX' and ((t.status = 5 and at.DBCR='C') or t.status <>5) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.EVENT_TYPE=2 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// fund_status
		SQL_POLICY_TRANSACTION_HISTORY.append(" fund_status as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select FK_TRANSACTIONSTRN_ID  as 'TRN_ID', max(fst.status) as 'status'  from FUND_TRANSACTIONS fst where  fst.STATUS in (3,4,6) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by FK_TRANSACTIONSTRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// fund_trans
		SQL_POLICY_TRANSACTION_HISTORY.append(" fund_trans as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select t.TRN_ID, t.EFFECTIVE_DATE, t.EVENT_TYPE, t.STATUS, fst.status as 'fund_status' , t.currency,  (sum(gross)) as 'gross', (sum(net)) as 'net', sum((fee)) as 'fee', sum((tax)) as 'tax',t.REFERENCE, max(coverage) as last_coverage ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from TRANSACTIONS t  ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join fund_status fst on t.TRN_ID=fst.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join gross on t.TRN_ID=gross.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join net on t.TRN_ID=net.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join fee on t.TRN_ID=fee.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join tax on t.TRN_ID=tax.TRN_ID, MY_POLICY ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.FK_POLICIESPOL_ID=MY_POLICY.POLICY ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.EVENT_TYPE <> 1 and t.EVENT_TYPE<>44");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and exists (select top 1 1 from FUND_TRANSACTIONS ft2 where ft2.FK_TRANSACTIONSTRN_ID=t.TRN_ID) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by t.TRN_ID,t.EFFECTIVE_DATE, t.EVENT_TYPE, t.STATUS, t.currency, fst.status, t.REFERENCE ");
		// add Death notif without fundTransaction
		SQL_POLICY_TRANSACTION_HISTORY.append("   union ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select t.TRN_ID, t.EFFECTIVE_DATE, t.EVENT_TYPE, t.STATUS, fst.status as 'fund_status' , t.currency,  (sum(gross)) as 'gross', (sum(net)) as 'net', sum((fee)) as 'fee', sum((tax)) as 'tax',t.REFERENCE, max(coverage) as last_coverage ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join fund_status fst on t.TRN_ID=fst.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join gross on t.TRN_ID=gross.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join net on t.TRN_ID=net.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join fee on t.TRN_ID=fee.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join tax on t.TRN_ID=tax.TRN_ID, MY_POLICY ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.FK_POLICIESPOL_ID=MY_POLICY.POLICY ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.EVENT_TYPE = 6 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and not exists (select top 1 1 from FUND_TRANSACTIONS ft2 where ft2.FK_TRANSACTIONSTRN_ID=t.TRN_ID) ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by t.TRN_ID,t.EFFECTIVE_DATE, t.EVENT_TYPE, t.STATUS, t.currency, fst.status, t.REFERENCE ");
		// add Death notif without fundTransaction end
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");
		
		

		// fund_trans_adjustement
		SQL_POLICY_TRANSACTION_HISTORY.append(" fund_trans_adjustement as (");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select t.TRN_ID, t.EFFECTIVE_DATE, t.EVENT_TYPE, t.STATUS, fst.status as 'fund_status' , t.currency,  gross,  net, fee , tax , t.REFERENCE, coverage ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from TRANSACTIONS t ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join fund_status fst on fst.TRN_ID=t.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join gross on t.TRN_ID=gross.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join net on t.TRN_ID=net.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join fee on t.TRN_ID=fee.TRN_ID ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join tax on t.TRN_ID=tax.TRN_ID, MY_POLICY ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where t.FK_POLICIESPOL_ID= MY_POLICY.POLICY ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and t.EVENT_TYPE <> 1 and t.EVENT_TYPE = 44 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" and exists (select top 1 1 from FUND_TRANSACTIONS ft2 where ft2.FK_TRANSACTIONSTRN_ID=t.TRN_ID)	");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ), ");

		// transactions_policy
		SQL_POLICY_TRANSACTION_HISTORY.append(" transactions_policy as ( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select fts.TRN_ID,  fts.EFFECTIVE_DATE, fts.EVENT_TYPE, (case when (fts.REFERENCE='TRN_INC') then e.NAME+'- Incomplete transaction'  else e.NAME end) as NAME, fts.STATUS, opt1.DESCRIPTION, fts.fund_status, opt2.DESCRIPTION as 'DESCRIPTIONS',fts.currency,  abs(gross) as 'gross', abs(net) as 'net', abs(fee) as 'fee', abs(tax) as 'tax', coverage as 'last_coverage' ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from fund_trans_adjustement fts ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join Events e on e.EVT_ID=fts.EVENT_TYPE");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join OPTION_DETAILS opt1 on opt1.FK_OPTIONSOPT_ID = 'STATUS_TRN' and opt1.NUMBER=fts.STATUS ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join OPTION_DETAILS opt2 on opt2.FK_OPTIONSOPT_ID = 'STATUS_FTR' and opt2.NUMBER=fts.fund_status ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" union all ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select ft.TRN_ID, ft.EFFECTIVE_DATE, ft.EVENT_TYPE, (case when (ft.REFERENCE='TRN_INC') then e.NAME+'- Incomplete transaction'  else e.NAME end) as NAME, ft.STATUS, opt1.DESCRIPTION, ft.fund_status, opt2.DESCRIPTION as 'DESCRIPTIONS',ft.currency,  abs(sum(gross)) as 'gross', abs(sum(net)) as 'net', sum(abs(fee)) as 'fee', sum(abs(tax)) as 'tax', max(last_coverage) as 'last_coverage' ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" from fund_trans ft ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" join Events e on e.EVT_ID=ft.EVENT_TYPE ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join OPTION_DETAILS opt1 on opt1.FK_OPTIONSOPT_ID = 'STATUS_TRN' and opt1.NUMBER=ft.STATUS ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" left join OPTION_DETAILS opt2 on opt2.FK_OPTIONSOPT_ID = 'STATUS_FTR' and opt2.NUMBER=ft.fund_status ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by ft.TRN_ID, ft.EFFECTIVE_DATE, ft.EVENT_TYPE, e.NAME, ft.STATUS,opt1.DESCRIPTION, ft.fund_status, opt2.DESCRIPTION, ft.currency,ft.REFERENCE ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ) ");

		// get Transaction before grouping them.
		SQL_POLICY_TRANSACTION_ELEMENTS.append(SQL_POLICY_TRANSACTION_HISTORY);
		SQL_POLICY_TRANSACTION_ELEMENTS.append(" select TRN_ID, gross  From transactions_policy ");
		SQL_POLICY_TRANSACTION_ELEMENTS.append(" where EFFECTIVE_DATE = ? and EVENT_TYPE = ? AND STATUS = ?");

		// transactions_policy_group
		SQL_POLICY_TRANSACTION_HISTORY.append(", transactions_policy_group as( ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select EFFECTIVE_DATE, EVENT_TYPE, NAME, STATUS, DESCRIPTION, fund_status,  DESCRIPTIONS, currency,  abs(sum(gross)) as 'gross', abs(sum(net)) as 'net', sum(abs(fee)) as 'fee', sum(abs(tax)) as 'tax', max(last_coverage) as last_coverage, MAX(TRN_ID) as lastTrnId ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" From transactions_policy ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where EVENT_TYPE <> 44 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" group by EFFECTIVE_DATE,EVENT_TYPE,NAME, STATUS, DESCRIPTION, fund_status, DESCRIPTIONS, currency ");

		SQL_POLICY_TRANSACTION_HISTORY.append(" union all ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" select EFFECTIVE_DATE, EVENT_TYPE, NAME, STATUS, DESCRIPTION, fund_status,  DESCRIPTIONS, currency,  abs(gross) as 'gross', abs(net) as 'net', abs(fee) as 'fee', abs(tax) as 'tax', last_coverage, TRN_ID as lastTrnId ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" From transactions_policy  ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" where EVENT_TYPE = 44 ");
		SQL_POLICY_TRANSACTION_HISTORY.append(" ) ");

		SQL_POLICY_TRANSACTION_HISTORY.append("  select *  from transactions_policy_group order by EFFECTIVE_DATE DESC ");

	}

	@Override
	public TransactionDTO getTransactionById(long transactionId) {
		return transactionMapper.asTransactionDTO(transactionRepository.getOne(transactionId));
	}

	@Override
	public TransactionDTO getTransaction(String polId, Integer coverage, List<Integer> eventTypes) {

		List<TransactionEntity> transactions = getTransactions(polId, coverage, eventTypes, null, null, null);

		return transactionMapper.asTransactionDTO(lu.wealins.common.collection.CollectionUtils.extractSingletonOrNull(transactions));
	}

	@Override
	public Collection<TransactionDTO> getTransaction(String polId, BigDecimal value0, String currency, Date effectiveDate, List<Integer> eventTypes) {

		List<TransactionEntity> transactions = getTransactions(polId, null, eventTypes, value0, currency, effectiveDate);

		return transactionMapper.asTransactionDTOs(transactions);
	}

	private List<TransactionEntity> getTransactions(String polId, Integer coverage, List<Integer> eventTypes, BigDecimal value0, String currency, Date effectiveDate) {
		Specifications<TransactionEntity> spec = Specifications.where(TransactionSpecifications.initial());

		if (polId != null) {
			spec = spec.and(TransactionSpecifications.withPolicy(polId));
		}

		if (coverage != null) {
			spec = spec.and(TransactionSpecifications.withCoverage(coverage));
		}

		if (value0 != null) {
			spec = spec.and(TransactionSpecifications.withValue0(value0));
		}

		if (currency != null) {
			spec = spec.and(TransactionSpecifications.withCurrency(currency));
		}

		if (effectiveDate != null) {
			spec = spec.and(TransactionSpecifications.withEffectiveDateGreaterThanOrEqualTo(calendarUtils.resetTime(effectiveDate)));
			spec = spec.and(TransactionSpecifications.withEffectiveDateLessThanOrEqualTo(calendarUtils.createDate(effectiveDate, 23, 59, 59)));
		}

		if (CollectionUtils.isNotEmpty(eventTypes)) {
			spec = spec.and(TransactionSpecifications.withEventType(eventTypes.toArray(new Integer[eventTypes.size()])));
		}

		return transactionRepository.findAll(spec);
	}

	@Override
	public List<PolicyTransactionsHistoryDTO> getPolicyTransactionsHistory(String polId) {

		List<PolicyTransactionsHistoryDTO> history = jdbcTemplate.query(SQL_POLICY_TRANSACTION_HISTORY.toString(),
				new Object[] { polId }, new RowMapper<PolicyTransactionsHistoryDTO>() {

					@Override
					public PolicyTransactionsHistoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						PolicyTransactionsHistoryDTO pth = new PolicyTransactionsHistoryDTO();

						pth.setEffectiveDate(rs.getDate(1));
						pth.setEventType(rs.getInt(2));
						pth.setEventName(rs.getString(3));
						pth.setStatusCode(rs.getInt(4));
						pth.setStatus(StringUtils.isNotBlank(rs.getString(7)) ? rs.getString(7) : rs.getString(5));
						pth.setCurrency(rs.getString(8));
						pth.setGrossAmount(rs.getBigDecimal(9));
						pth.setNetAmount(rs.getBigDecimal(10));
						pth.setFeeAmount(rs.getBigDecimal(11));
						pth.setTaxAmount(rs.getBigDecimal(12));
						pth.setCoverage(rs.getInt(13));
						pth.setLastTrnId(rs.getBigDecimal(14));
						pth.setPolicyClientCountry(StringUtils.EMPTY);
						return pth;
					}

				});
		
		
		return history;

	}



	@Override
	public Collection<BigDecimal> getPolicyMortalityCharge(String policyId, Date paymentDate,
			Integer eventTypes) {
		return transactionRepository.getMortalityCharge(policyId, paymentDate, Arrays.asList(eventTypes));
	}

	@Override
	public List<FrenchTaxPolicyTransactionDTO> getFrenchTaxTransactionForPolicy(String policyId) {
		List<FrenchTaxPolicyTransactionDTO> transactions = jdbcTemplate.query(
				TransactionSqlRequestUtils.getPolicyTransactionsRequest(),
				new Object[] { policyId }, new RowMapper<FrenchTaxPolicyTransactionDTO>() {

					@Override
					public FrenchTaxPolicyTransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						FrenchTaxPolicyTransactionDTO pth = new FrenchTaxPolicyTransactionDTO();
						pth.setPolicy(policyId);
						pth.setTransactionType(rs.getString(1));
						pth.setEffectiveDate(rs.getDate(2));
						pth.setTransactionsAggregated(rs.getInt(3));
						pth.setNetTransactionAmount(rs.getBigDecimal(4));
						pth.setTransactionCurrency(rs.getString(5));
						pth.setAccountCurrency(rs.getString(6));
						pth.setLastTransactionId(rs.getInt(7));
						return pth;
					}
				});
		return transactions;
	}

	private Map<BigDecimal, BigDecimal> getPolicyTransactionsElements(String polId, Date effectiveDate,
			Integer eventType, Integer status) {
		List<TransactionFundDTO> elements = jdbcTemplate.query(SQL_POLICY_TRANSACTION_ELEMENTS.toString(),
				new Object[] { polId, effectiveDate, eventType, status }, new RowMapper<TransactionFundDTO>() {
					@Override
					public TransactionFundDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						TransactionFundDTO transactionDetail = new TransactionFundDTO();
						transactionDetail.setTranactionId(rs.getBigDecimal(1));
						transactionDetail
								.setNetAmount(rs.getBigDecimal(2) == null ? BigDecimal.ZERO : rs.getBigDecimal(2));
						return transactionDetail;
					}
				});
		if (elements == null || elements.isEmpty()) {
			new HashMap<BigDecimal, BigDecimal>();
		}
		return elements.stream().collect(Collectors.groupingBy(TransactionFundDTO::getTranactionId,
				Collectors.reducing(BigDecimal.ZERO, TransactionFundDTO::getNetAmount, BigDecimal::add)));
	}

	@Override
	public Collection<TransactionDTO> getActiveTransactionByPolicyAndEventType(String policyId, Integer eventType) {
		return transactionMapper.asTransactionDTOs(transactionRepository.findActiveByPolicyIdAndEventType(policyId, eventType));
	}

	@Override
	public Collection<TransactionDTO> getAwaitingAdministrationFees(String policyId, Date effectiveDate) {
		return transactionMapper.asTransactionDTOs(transactionRepository.findAdministrationFees(policyId, effectiveDate, Arrays.asList(FundTransactionStatus.POSTED.getStatus())));
	}

	@Override
	public Collection<TransactionDTO> getActiveTransactionByPolicyAndEventTypeAndDate(String policyId, Integer eventType, Date date) {
		return transactionMapper.asTransactionDTOs(transactionRepository.findActiveByPolicyIdAndEventTypeAndDate(policyId, eventType, date));
	}

	@Override
	public Collection<TransactionDTO> getActiveLinkedTransactions(Long transactionId) {
		return transactionMapper.asTransactionDTOs(transactionRepository.findActiveLinkedTransactions(transactionId));
	}

	@Override
	public Collection<TransactionDTO> getActiveLinkedTransactions(Collection<Long> transactionIds) {
		return transactionMapper.asTransactionDTOs(transactionRepository.findActiveLinkedTransactionsList(transactionIds));
	}

	@Override
	public PolicyTransactionsHistoryDetailsDTO getPolicyTransactionsDetails(PolicyTransactionsHistoryDetailsInputDTO input) {
		Assert.notNull(input);

		String polId = input.getPolicyId();
		Assert.hasText(polId);

		PolicyTransactionsHistoryDTO transaction = input.getTransaction();
		Assert.notNull(transaction);
		Date effectiveDate = transaction.getEffectiveDate();
		Assert.notNull(effectiveDate);
		Integer eventType = transaction.getEventType();
		Integer status = transaction.getStatusCode();

		Map<BigDecimal, BigDecimal> transactionsComposite = getPolicyTransactionsElements(polId, effectiveDate,
				eventType, status);

		BigDecimal grossAmount = transaction.getGrossAmount();
		BigDecimal netAmount = transaction.getNetAmount();

		return transactionDetailsUtils.retrieveTransactionsDetails(transactionsComposite, eventType, polId,
				grossAmount, netAmount, status);
	}

}
