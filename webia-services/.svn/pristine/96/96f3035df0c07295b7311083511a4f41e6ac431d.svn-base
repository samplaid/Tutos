package lu.wealins.webia.services.core.persistence.repository;

import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.services.core.mapper.SapOpenBalanceMapper;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.webia.services.core.persistence.entity.SapOpenBalanceEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
public class CommissionToPayRepositoryTest {
	@Autowired
	private CommissionToPayRepository commissionToPayRepository;
	@Autowired
	private SapOpenBalanceRepository sapOpenBalanceRepository;
	@Autowired
	private SapOpenBalanceMapper sapOpenBalanceMapper;

	@Test
	public void testFindAll() {
		List<CommissionToPayEntity> entities = commissionToPayRepository.findAll();
		assertNotNull(entities);
	}

	@Test
	public void testSapOpenBalance() {
		SapOpenBalanceEntity sapOpenBalanceEntity = sapOpenBalanceRepository.findOne(20184000041758001L);
		CommissionToPayDTO sapOpenBalance = sapOpenBalanceMapper.asCommissionToPayDTO(sapOpenBalanceEntity);
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 11, 31);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sapOpenBalance.getComDate());

		Assert.assertEquals(cal.getTime(), cal2.getTime());

		String period = new SimpleDateFormat("yyyyMM").format(sapOpenBalance.getComDate());
		Assert.assertEquals(period, "201712");
	}
}
