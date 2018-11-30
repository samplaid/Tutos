package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.UpdateInputService;
import lu.wealins.liability.services.ws.rest.UpdateInputRESTService;
import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.UpdateInputResponse;

@Component
public class UpdateInputRESTServiceImpl implements UpdateInputRESTService {

	@Autowired
	private UpdateInputService updateInputService;

	@Override
	public UpdateInputResponse updateInput(SecurityContext context, UpdateInputRequest request) {
		Assert.notNull(request);
		return updateInputService.updateInput(request);
	}

}
