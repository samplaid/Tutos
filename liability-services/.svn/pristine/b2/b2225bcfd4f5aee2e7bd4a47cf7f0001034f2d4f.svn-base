package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.ControlService;
import lu.wealins.liability.services.ws.rest.ControlRESTService;
import lu.wealins.common.dto.liability.services.ControlDTO;

@Component
public class ControlRESTServiceImpl implements ControlRESTService {

	@Autowired
	private ControlService controlService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ControlRESTService#get(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public ControlDTO get(SecurityContext context, String ctlId) {
		return controlService.getControl(ctlId);
	}

}
