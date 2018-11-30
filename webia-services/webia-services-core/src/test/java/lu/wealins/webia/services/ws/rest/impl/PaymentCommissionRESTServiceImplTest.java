package lu.wealins.webia.services.ws.rest.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.ws.rest.PaymentCommissionRESTService;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;
import lu.wealins.common.dto.webia.services.PageResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
public class PaymentCommissionRESTServiceImplTest {
	@Autowired
	PaymentCommissionRESTService paymentCommissionRESTService;
	private static final String SERVICE_URI = "payment-commission/not-transfered-commission";

	@Test
	public void testExtractNotTransferedCommissionsToPayByType() {
		
		PageResult<CommissionToPayWrapperDTO> pageResult = paymentCommissionRESTService.extractNotTransferedCommissionsToPayByType(null, "ENTRY", 0, 100);
		assertNotNull(pageResult);
		assertNotNull(pageResult.getContent());
	}

	//@Test
	public void testInsertTransfers() {
		fail("Not yet implemented");
	}

	//@Test
	public void testUpdateCommissionToPay() {
		fail("Not yet implemented");
	}

}
