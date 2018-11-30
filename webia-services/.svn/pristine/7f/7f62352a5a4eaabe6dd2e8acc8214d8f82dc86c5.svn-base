package lu.wealins.webia.services.core.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.core.components.CommissionToPayWrapper;
import lu.wealins.webia.services.core.service.PaymentCommissionService;

/**
 * 
 * @author xqv99
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
@Ignore
public class PaymentCommissionServiceImplTest {
	@Autowired
	PaymentCommissionService paymentCommissionService;

	@Test
	public void testGetCommissionsToPayByTypeOK() {
		String type = "12";

		List<CommissionToPayWrapper> result = paymentCommissionService.getCommissionsToPayByType(type);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	public void testGetCommissionsToPayByTypeFAIL() {
		String type = "FAKE_TYPE";

		List<CommissionToPayWrapper> result = paymentCommissionService.getCommissionsToPayByType(type);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
