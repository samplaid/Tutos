/**
 * 
 */
package lu.wealins.webia.core.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.webia.core.constants.CommissionConstant;
import lu.wealins.webia.core.mock.commission.CommissionMock;

/**
 * Test class for {@link CommissionUtils}
 * 
 * @author oro
 */
@RunWith(PowerMockRunner.class)
public class CommissionUtilsTest {

	private List<CommissionReconciliationDTO> commissionReconciliations = new ArrayList<>();

	@Before
	public void initTest() {
		commissionReconciliations = CommissionMock.getCommissionReconciliations();
	}

	/**
	 * Test method for {@link lu.wealins.webia.core.utils.CommissionUtils#determineStatus(java.math.BigDecimal)}.
	 */
	@Test
	public void testDetermineStatus() {
		String actual = CommissionUtils.determineStatus(null);
		Assert.assertEquals(CommissionConstant.NOT_RECONCILED, actual);

		actual = CommissionUtils.determineStatus(new BigDecimal(452));
		Assert.assertEquals(CommissionConstant.NOT_RECONCILED, actual);

		// if gap == 0 assert reconciled
		actual = CommissionUtils.determineStatus(BigDecimal.ZERO);
		Assert.assertEquals(CommissionConstant.RECONCILED, actual);
	}

	/**
	 * Test method for {@link lu.wealins.webia.core.utils.CommissionUtils#searchByCodeName(java.util.List, java.lang.String)}.
	 */
	@Test
	public void testSearchByCodeNameListOfCommissionReconciliationDTOString() {

		// if the text is null, returns the full list
		List<CommissionReconciliationDTO> results = CommissionUtils.searchByCodeName(commissionReconciliations, null);
		Assert.assertTrue(!results.isEmpty());
		Assert.assertTrue(results.size() == commissionReconciliations.size());

		// if the text is filled in, then return a result or empty
		String text = "Toto";
		results = CommissionUtils.searchByCodeName(commissionReconciliations, text);
		Assert.assertTrue("The name = [" + text + "] exists in the mock list however the result search is empty.", !results.isEmpty());

		text = "A02";
		results = CommissionUtils.searchByCodeName(commissionReconciliations, text);
		Assert.assertTrue("The code = [" + text + "] exists in the mock list however the result search is empty.", !results.isEmpty());

		text = "not in list";
		results = CommissionUtils.searchByCodeName(commissionReconciliations, text);
		Assert.assertTrue("The search text = [" + text + "] does not exists in the mock list however the result search is not empty.", results.isEmpty());
	}

	/**
	 * Test method for {@link lu.wealins.webia.core.utils.CommissionUtils#groupCommissionReconciliations(List)}.
	 */
	@Test
	public void testSortCommissionReconciliationGroup() {
		List<CommissionReconciliationGroupDTO> results = CommissionUtils.groupCommissionReconciliations(commissionReconciliations);
		
		int prev = -1;
		for (CommissionReconciliationGroupDTO commissionReconciliationGroupDTO : results) {
			int current = getStatusOrdinal(commissionReconciliationGroupDTO.getAggregate().getStatus());
			Assert.assertTrue("The commission reconciliation group is not sorted by status. This is mandatory", current >= prev);
			prev = current;
		}

	}

	private int getStatusOrdinal(String status) {

		if ("Reconciled".equals(status)) {
			return 0;
		}

		if ("NOT RECONCILED".equals(status)) {
			return 1;
		}

		if ("UNKNOWN".equals(status)) {
			return 2;
		}

		return Integer.MAX_VALUE;
	}

}
