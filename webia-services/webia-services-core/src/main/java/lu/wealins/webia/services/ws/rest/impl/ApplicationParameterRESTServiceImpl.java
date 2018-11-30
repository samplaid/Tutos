package lu.wealins.webia.services.ws.rest.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.ws.rest.ApplicationParameterRESTService;

@Component
public class ApplicationParameterRESTServiceImpl implements ApplicationParameterRESTService {

	@Autowired
	private ApplicationParameterService applicationParameterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ApplicationParameterRESTService#getApplicationParameter(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public ApplicationParameterDTO getApplicationParameter(SecurityContext context, String code) {
		return applicationParameterService.getApplicationParameter(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ApplicationParameterRESTService#getApplicationParameters(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public List<String> getApplicationParameters(SecurityContext context, String code) {
		return applicationParameterService.getStringValues(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ApplicationParameterRESTService#getIntegerValue(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Integer getIntegerValue(SecurityContext context, String code) {
		return applicationParameterService.getIntegerValue(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.ApplicationParameterRESTService#getIntegerValues(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public List<Integer> getIntegerValues(SecurityContext context, String code) {
		return applicationParameterService.getIntegerValues(code);
	}
	
	@Override
	public BigDecimal getBigDecimalValue(SecurityContext context, String code) {
		return applicationParameterService.getBigDecimalValue(code);
	}
}
