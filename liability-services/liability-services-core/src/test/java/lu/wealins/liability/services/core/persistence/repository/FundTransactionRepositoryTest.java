package lu.wealins.liability.services.core.persistence.repository;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.liability.services.core.persistence.entity.EstimatedOrderNoEntity;
import lu.wealins.liability.services.core.persistence.entity.ValorizedOrderNoEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/mock/dev/coreContextWithNoWebService.xml" })
@ActiveProfiles("dev-test")
@Ignore
public class FundTransactionRepositoryTest {

	@Autowired
	private FundTransactionRepository fundTransactionRepository;

	@Test
	public void findValorizedOrder() {
		Pageable pageable = new PageRequest(1, 5);

		Page<ValorizedOrderNoEntity> elements = fundTransactionRepository.findValorizedOrder(0L, pageable);
		Assert.assertNotNull(elements);
	}

	@Test
	public void findEstimatedOrder() {
		Pageable pageable = new PageRequest(1, 5);

		Page<EstimatedOrderNoEntity> elements = fundTransactionRepository.findEstimatedOrder(0L, pageable);
		Assert.assertNotNull(elements);
	}
}
