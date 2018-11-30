/**
 * 
 */
package lu.wealins.webia.services.ws.rest.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lu.wealins.webia.services.ws.rest.CommissionPaymentSlipRESTService;

/**
 * @author ORO
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
public class CommissionPaymentSlipRESTServiceImplTest {

	@Autowired
	private CommissionPaymentSlipRESTService commissionPaymentSlipRESTService;
	/**
	 * Test method for {@link lu.wealins.webia.services.ws.rest.impl.CommissionPaymentSlipRESTServiceImpl#retrievePaymentSlip(javax.ws.rs.core.SecurityContext, java.lang.Long)}.
	 */
	@Test
	public void testRetrievePaymentSlip() {
		Response response = commissionPaymentSlipRESTService.retrievePaymentSlip(null, 4L);
		Object obj = response.getEntity();
		Assert.assertNotNull(obj);
		Assert.assertTrue(obj instanceof List<?>);
		Assert.assertFalse(((List<?>) obj).isEmpty());
	}

	@Test
	public void testDate() {
		String value = DateFormatUtils.format(new Date(), "yyyyddMMHHmmss");
		System.out.println(value);
	}

}
