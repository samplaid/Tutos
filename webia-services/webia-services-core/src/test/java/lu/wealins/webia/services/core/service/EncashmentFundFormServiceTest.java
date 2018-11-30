package lu.wealins.webia.services.core.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus;
import lu.wealins.webia.services.core.persistence.entity.EncashmentFundFormEntity;
import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;
import lu.wealins.webia.services.core.persistence.repository.EncashmentFundFormRepository;
import lu.wealins.webia.services.core.persistence.repository.FundFormRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml", "classpath:/mock/dev/mockProperties.xml" })
@ActiveProfiles("unit-test")
@Ignore
public class EncashmentFundFormServiceTest {

	@Autowired
	private EncashmentFundFormService encashmentFundFormService;
	
	@Autowired
	private FundFormRepository fundFormRepository;
	
	@Autowired
	private EncashmentFundFormRepository encashmentFundFormRepository;
	
	private static final Integer FUND_FORM_ID = Integer.valueOf(1);
	private static final Integer FORM_ID = Integer.valueOf(100);
	private static final String FUND_ID = "dummy";
	
	@Mock private KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal;
	@Mock private KeycloakSecurityContext keycloakSecurityContext; 
	@Mock private AccessToken accessToken;

	@Before public void initMocks() {
		MockitoAnnotations.initMocks(this);
		
		// mock the behaviour of the Keycloak Security Context
		SecurityContextThreadLocal.set(keycloakPrincipal);
		Mockito.when(keycloakPrincipal.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
		Mockito.when(keycloakSecurityContext.getToken()).thenReturn(accessToken);
		Mockito.when(accessToken.getPreferredUsername()).thenReturn("junit-user");
	}
	
	@Test
	public void deleteOrCancelTest() {
		
		FundFormEntity fundFormentity = fundFormRepository.findOne(FUND_FORM_ID);

		// perform deleteOrCancel
		encashmentFundFormService.deleteOrCancel(fundFormentity);
		
		// and assert that complying records have been deleted or changed to the correct status
		fundFormentity = fundFormRepository.findOne(FUND_FORM_ID);
		assertFalse(fundFormentity.getEncashmentFundForms().stream().anyMatch(x -> EncashmentFormFundStatus.NEW.equals(x.getCashStatus())));
		assertTrue(fundFormentity.getEncashmentFundForms().stream().filter(x -> EncashmentFormFundStatus.CANCEL.equals(x.getCashStatus())).count() == 2);
		assertTrue(fundFormentity.getEncashmentFundForms().stream().filter(x -> EncashmentFormFundStatus.NEW_POSTED.equals(x.getCashStatus())).count() == 0);
	}
	
	private EncashmentFundFormEntity createEncashmentFundForm(EncashmentFormFundStatus status) {
		EncashmentFundFormEntity encashmentFundFormEntity = new EncashmentFundFormEntity();
		encashmentFundFormEntity.setCashStatus(status);
		encashmentFundFormEntity.setFormId(FORM_ID);
		encashmentFundFormEntity.setFundId(FUND_ID);
		
		return encashmentFundFormEntity;
	}
	
	@Before
	public void setUp() {
		
		// create a fund form
		FundFormEntity fundFormEntity = new FundFormEntity();
		fundFormEntity.setFundFormId(FUND_FORM_ID);
		fundFormEntity.setFormId(FORM_ID);
		fundFormEntity.setFundId(FUND_ID);
		fundFormEntity.setFundTp("FID");
		
		// and also create a encashment of each type
		List<EncashmentFundFormEntity> encashmentFundFormEntities = new ArrayList<>();
		encashmentFundFormEntities.add(createEncashmentFundForm(EncashmentFormFundStatus.NEW));
		encashmentFundFormEntities.add(createEncashmentFundForm(EncashmentFormFundStatus.NEW_POSTED));
		encashmentFundFormEntities.add(createEncashmentFundForm(EncashmentFormFundStatus.CCL_POSTED));
		encashmentFundFormEntities.add(createEncashmentFundForm(EncashmentFormFundStatus.CANCEL));
		encashmentFundFormEntities.stream().forEach(eff -> {encashmentFundFormRepository.save(eff);});
		
		fundFormEntity.setEncashmentFundForms(encashmentFundFormEntities);
		fundFormRepository.save(fundFormEntity);
	}
	
	@After
	public void teadDown() {
		encashmentFundFormRepository.deleteAll();
		fundFormRepository.deleteAll();
	}
}
