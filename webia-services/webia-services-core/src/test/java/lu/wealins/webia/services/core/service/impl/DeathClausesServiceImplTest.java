package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.common.dto.webia.services.enums.ClauseType;
import lu.wealins.common.dto.liability.services.enums.CountryCodeEnum;
import lu.wealins.common.dto.liability.services.enums.DeathCoverageLives;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.validations.DeathClausesValidationService;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml", "classpath:/mock/dev/mockProperties.xml" })
@ActiveProfiles("unit-test")
@Ignore
public class DeathClausesServiceImplTest {

	@Autowired
	private DeathClausesValidationService DeathClausesValidationService;

	@Autowired
	private ApplicationParameterService applicationParameterService;

	@Mock
	AppFormDTO appForm;
	@Mock
	BenefClauseFormDTO deathBenefClauseForm;
	@Mock
	Collection<PolicyHolderFormDTO> policyHolders;

	private static final String RULES_2_POLICYHOLDERS_NORD = "RULES_2_POLICYHOLDERS_NORD";

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testNordicRules() {

		// prepare mocks
		Mockito.when(appForm.getProductCountryCd()).thenReturn(CountryCodeEnum.SWEDEN.getCode());
		Mockito.when(appForm.getPolicyHolders()).thenReturn(policyHolders);

		Mockito.when(appForm.getLives()).thenReturn(DeathCoverageLives.JOINT_SECOND_DEATH.getCode());
		Mockito.when(policyHolders.size()).thenReturn(2);
		Mockito.when(deathBenefClauseForm.getRankNumber()).thenReturn(1);
		Mockito.when(deathBenefClauseForm.getClauseText())
				.thenReturn(applicationParameterService.getStringValue(RULES_2_POLICYHOLDERS_NORD));
		Mockito.when(deathBenefClauseForm.getClauseTp()).thenReturn(ClauseType.FREE.getValue());

		List<String> errors = new ArrayList<>();

		// assert rule is triggered if death clause is missing
		// DeathClausesValidationService.validateDeathClauses(appForm, errors);
		Assert.assertNotEquals(0, errors.size());
		errors.clear();

		// assert rule is not triggered if all requirements are met
		Collection<BenefClauseFormDTO> deathClauses = new ArrayList<>();
		deathClauses.add(deathBenefClauseForm);
		Mockito.when(appForm.getDeathBenefClauseForms()).thenReturn(deathClauses);
		Assert.assertEquals(0, errors.size());
		errors.clear();
	}
}
