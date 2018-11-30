/**
 * 
 */
package lu.wealins.webia.core.mock.commission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.webia.core.constants.CommissionConstant;

/**
 * @author oro
 *
 */
public final class CommissionMock {

	public static List<CommissionReconciliationDTO> getCommissionReconciliations() {
		List<CommissionReconciliationDTO> commissions = new ArrayList<>();
		CommissionReconciliationDTO commission = new CommissionReconciliationDTO();
		commission.setAgentId("A01");
		commission.setName("Toto");
		commission.setPeriod("201710");
		commission.setCurrency("EUR");
		commission.setSapBalance(new BigDecimal(10));
		commission.setStatementBalance(new BigDecimal(10));
		commission.setGap(new BigDecimal(0));
		commission.setStatus(CommissionConstant.RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A01");
		commission.setName("Toto");
		commission.setPeriod("201705");
		commission.setCurrency("USD");
		commission.setSapBalance(new BigDecimal(12));
		commission.setStatementBalance(new BigDecimal(12));
		commission.setGap(new BigDecimal(0));
		commission.setStatus(CommissionConstant.RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A02");
		commission.setName("Bebe");
		commission.setPeriod("201712");
		commission.setCurrency("EUR");
		commission.setSapBalance(new BigDecimal(10));
		commission.setStatementBalance(new BigDecimal(10));
		commission.setGap(new BigDecimal(0));
		commission.setStatus(CommissionConstant.RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A02");
		commission.setName("Bebe");
		commission.setPeriod("201803");
		commission.setCurrency("USD");
		commission.setSapBalance(new BigDecimal(12));
		commission.setStatementBalance(new BigDecimal(20));
		commission.setGap(new BigDecimal(8));
		commission.setStatus(CommissionConstant.NOT_RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A03");
		commission.setName("Rene");
		commission.setPeriod("201701");
		commission.setCurrency("CHK");
		commission.setSapBalance(new BigDecimal(5));
		commission.setStatementBalance(new BigDecimal(5));
		commission.setGap(new BigDecimal(0));
		commission.setStatus(CommissionConstant.RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A04");
		commission.setName("Pepe");
		commission.setPeriod("201803");
		commission.setCurrency("EUR");
		commission.setSapBalance(new BigDecimal(5));
		commission.setStatementBalance(new BigDecimal(6));
		commission.setGap(new BigDecimal(1));
		commission.setStatus(CommissionConstant.NOT_RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A05");
		commission.setName("Dadoune");
		commission.setPeriod("201804");
		commission.setCurrency("EUR");
		commission.setSapBalance(new BigDecimal(0));
		commission.setStatementBalance(new BigDecimal(6));
		commission.setGap(new BigDecimal(6));
		commission.setStatus(CommissionConstant.NOT_RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A06");
		commission.setName("Peter");
		commission.setPeriod("201802");
		commission.setCurrency("EUR");
		commission.setSapBalance(new BigDecimal(4));
		commission.setStatementBalance(new BigDecimal(0));
		commission.setGap(new BigDecimal(4));
		commission.setStatus(CommissionConstant.NOT_RECONCILED);
		commissions.add(commission);

		commission = new CommissionReconciliationDTO();
		commission.setAgentId("A07");
		commission.setName("Sanda");
		commission.setPeriod("201808");
		commission.setCurrency("EUR");
		commission.setSapBalance(null);
		commission.setStatementBalance(null);
		commission.setGap(null);
		commission.setStatus(CommissionConstant.NOT_RECONCILED);
		commissions.add(commission);

		return commissions;

	}
}
