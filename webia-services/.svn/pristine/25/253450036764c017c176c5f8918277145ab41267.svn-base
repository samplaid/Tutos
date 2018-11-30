package lu.wealins.webia.services.core.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import lu.wealins.common.security.token.SecurityContextThreadLocal;

@Component(value = "customAuditorAwareBean")
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		String preferredUsername = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();

		if (preferredUsername != null && preferredUsername.length() > 5) {
			return preferredUsername.substring(0, 4);
		}

		return preferredUsername;
	}

}