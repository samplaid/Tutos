/**
 * 
 */
package lu.wealins.webia.services.core.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.services.core.service.CommissionPaymentSlipService;

/**
 * @author ORO
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
public class CommissionPaymentSlipServiceImplTest {

	@Autowired
	private CommissionPaymentSlipService commissionPaymentSlipService;

	/**
	 * Test method for {@link lu.wealins.webia.services.core.service.impl.CommissionPaymentSlipServiceImpl#retrievePaymentSlip(java.lang.Long)}.
	 */
	@Test
	public void testRetrievePaymentSlip() {
		List<TransferDTO> result = commissionPaymentSlipService.retrievePaymentSlip(4L);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
	}

}
