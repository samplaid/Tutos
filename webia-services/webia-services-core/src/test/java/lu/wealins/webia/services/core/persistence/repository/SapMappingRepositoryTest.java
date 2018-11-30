package lu.wealins.webia.services.core.persistence.repository;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
public class SapMappingRepositoryTest {

	private static final String _5678 = "5678";
	private static final String _01234 = "01234";
	private static final String CURRENCY_TEST = "CURRENCY_TEST";
	private static final String ACCOUNT_TEST = "ACCOUNT_TEST";
	private static final String CURRENCY = "CURRENCY";
	private static final String ACCOUNT = "ACCOUNT";
	@Autowired
	private SapMappingRepository sapMappingRepository;

	@Before
	public void load() {
		List<SapMappingEntity> sapMappingEntities = new ArrayList<>();
		SapMappingEntity sapMappingEntity = new SapMappingEntity();
		// type Account
		sapMappingEntity.setType(ACCOUNT);
		sapMappingEntity.setDataIn(ACCOUNT_TEST);
		sapMappingEntity.setDataOut(_01234);
		sapMappingEntities.add(sapMappingEntity);
		// type CURRENCY
		sapMappingEntity = new SapMappingEntity();
		sapMappingEntity.setType(CURRENCY);
		sapMappingEntity.setDataIn(CURRENCY_TEST);
		sapMappingEntity.setDataOut(_5678);
		sapMappingEntities.add(sapMappingEntity);

		sapMappingRepository.save(sapMappingEntities);
	}

	@Test
	public void findAccountNumberByTypeAccountAndCurrencyOK() {
		String account = sapMappingRepository.findAccountNumberByTypeAccountAndCurrency(ACCOUNT_TEST, CURRENCY_TEST);
		assertTrue(_01234.concat(_5678).equalsIgnoreCase(account));
	}

	@Test
	public void findAccountNumberByTypeAccountAndCurrencyFAIL() {
		String account = sapMappingRepository.findAccountNumberByTypeAccountAndCurrency(ACCOUNT_TEST, "FAKE");
		assertNull(account);
	}
}
