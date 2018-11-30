/**
 * 
 */
package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.FundFormService;
import lu.wealins.webia.services.ws.rest.FundFormRESTService;
import lu.wealins.common.dto.webia.services.FundFormDTO;

@Component
public class FundFormRESTServiceImpl implements FundFormRESTService {

	@Autowired
	private FundFormService fundFormService;

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.FundFormRESTService#save(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.FundFormDTO)
	 */
	@Override
	public FundFormDTO save(SecurityContext context, FundFormDTO fundForm) {
		return fundFormService.update(fundForm);
	}

}
