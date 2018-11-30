#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.rest;

import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ${package}.ws.rest.SomethingRESTService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@WebAppConfiguration
public class SomethingRESTServiceTest {

	protected Dispatcher dispatcher;

	@Mock
	protected SecurityContext securityContext;

	@Autowired
	private SomethingRESTService somethingRESTService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		KeycloakPrincipal<KeycloakSecurityContext> principal = Mockito.mock(KeycloakPrincipal.class);
		Mockito.when(this.securityContext.getUserPrincipal()).thenReturn(principal);
		Mockito.when(principal.getName()).thenReturn("unit-test-user");
	}

	@Test
	public void test() throws Exception {
		this.dispatcher = MockDispatcherFactory.createDispatcher();
		this.dispatcher.getRegistry().addSingletonResource(somethingRESTService);

		// TODO How to test Roles locally ?? without JEE domain)
		MockHttpRequest request = MockHttpRequest.get("/something/action");
		MockHttpResponse response = new MockHttpResponse();

		ResteasyProviderFactory.getContextDataMap().put(SecurityContext.class, this.securityContext);

		this.dispatcher.invoke(request, response);

		System.out.println(response.getStatus());
		System.out.println(response.getContentAsString()); // TODO ?? Load
															// spring
	}
}
