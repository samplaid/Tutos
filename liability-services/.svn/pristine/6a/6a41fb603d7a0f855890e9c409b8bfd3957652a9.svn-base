package lu.wealins.liability.services.core.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import lu.wealins.common.security.token.SecurityContextThreadLocal;

@Component(value = "customAuditorAwareBean")
public class AuditorAwareImpl implements AuditorAware<String> {
  
    @Override
    public String getCurrentAuditor() {
    	return SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
    }
 
}