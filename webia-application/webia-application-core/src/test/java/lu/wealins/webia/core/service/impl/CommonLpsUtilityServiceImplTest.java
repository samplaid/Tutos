package lu.wealins.webia.core.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;

@ActiveProfiles(value = "unit-test")
@RunWith(MockitoJUnitRunner.class)
public class CommonLpsUtilityServiceImplTest {

	@Spy
	private CommonLpsUtilityServiceImpl<TransactionFormDTO> commonLpsUtilityService;

	@Test
	public void test() {

		Date policyDate = Date.from(LocalDate.of(2000, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date withdrawalWithin2YearsDate = Date.from(LocalDate.of(2001, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date withdrawalAfter2YearsDate = Date.from(LocalDate.of(2003, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		Assert.assertNull(commonLpsUtilityService.checkMov1(null, null));
		Assert.assertNull(commonLpsUtilityService.checkMov1(policyDate, null));
		Assert.assertNull(commonLpsUtilityService.checkMov1(null, withdrawalWithin2YearsDate));
		Assert.assertTrue(commonLpsUtilityService.checkMov1(policyDate, withdrawalWithin2YearsDate));
		Assert.assertFalse(commonLpsUtilityService.checkMov1(policyDate, withdrawalAfter2YearsDate));
	}
}
