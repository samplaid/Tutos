package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.SequenceService;
import lu.wealins.webia.services.ws.rest.SequenceRESTService;

@Component
public class SequenceRESTServiceImpl implements SequenceRESTService {

	@Autowired
	private SequenceService sequenceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.SequenceRESTService#generateNextId(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public String generateNextId(SecurityContext context, String target) {
		return sequenceService.generateNextId(target);
	}
}
