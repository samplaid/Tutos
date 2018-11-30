package lu.wealins.liability.services.core.utils;

import org.junit.Assert;
import org.junit.Before;
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
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mock/dev/beansContextWithNoWebService.xml", "classpath:/mock/dev/coreContextWithNoWebService.xml", "classpath:mock/dev/mockProperties.xml"})
@ActiveProfiles(value = "unit-test")
public class HistoricManagerTest {

	@Autowired
	private PolicyRepository policyRepository;
	
	@Autowired
	private HistoricManager historicManager;

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
	public void testHistorize() {
		
		PolicyEntity policyEntity = policyRepository.findOne("ZXXXXXXXX");
				
		// Assert that unmodified entity is not dirty
		Assert.assertFalse(historicManager.historize(policyEntity));
		
		// Assert that altered entity is dirty
		policyEntity.setBrokerRefContract("new-broker-contract-ref");
		Assert.assertTrue(historicManager.historize(policyEntity));
	}

}
