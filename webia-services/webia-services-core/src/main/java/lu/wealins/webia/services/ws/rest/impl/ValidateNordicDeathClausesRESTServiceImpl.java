package lu.wealins.webia.services.ws.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.validations.DeathClausesValidationService;
import lu.wealins.webia.services.ws.rest.ValidateNordicDeathClausesRESTService;
import lu.wealins.common.dto.webia.services.NordicValidationRequest;

@Component
public class ValidateNordicDeathClausesRESTServiceImpl implements ValidateNordicDeathClausesRESTService {

	@Autowired
	DeathClausesValidationService deathClausesValidationService;

	@Override
	public Collection<String> validateDeathClauses(SecurityContext context, NordicValidationRequest request) {
		List<String> errors = new ArrayList<String>();
		deathClausesValidationService.validateDeathClauses(request.getPolicyHolderSize(), request.getProductCountry(),
				request.getLives(), request.getClauses(), errors);
		return errors;
	}

}
