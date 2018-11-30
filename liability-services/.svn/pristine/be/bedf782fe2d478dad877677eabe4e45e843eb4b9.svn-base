package lu.wealins.liability.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.ws.rest.OptionDetailRESTService;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OptionDetails;

/**
 * @author XQV89
 *
 */
@Component
public class OptionDetailRESTServiceImpl implements OptionDetailRESTService {

	@Autowired
	private OptionDetailService optionDetailService;

	@Override
	public List<OptionDetailDTO> getLives(SecurityContext context) {
		return optionDetailService.getLives();
	}

	@Override
	public List<OptionDetailDTO> getLives(SecurityContext context, String productId) {
		return optionDetailService.getLives(productId);
	}

	@Override
	public OptionDetails getCPRRoles(@Context SecurityContext context) {
		return optionDetailService.getCPRRoles();
	}

	@Override
	public List<OptionDetailDTO> getLanguages(SecurityContext ctx) {
		return optionDetailService.getLanguages();
	}

	@Override
	public List<OptionDetailDTO> getMaritalStatus(SecurityContext ctx) {
		return optionDetailService.getMaritalStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.OptionDetailRESTService#getPricingFrequency(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<OptionDetailDTO> getPricingFrequencies(SecurityContext ctx) {
		return optionDetailService.getPricingFrequency();
	};

	@Override
	public List<OptionDetailDTO> getPaymentModes(SecurityContext ctx) {
		return optionDetailService.getPaymentModes();
	}

	@Override
	public List<OptionDetailDTO> getAccountStatus(SecurityContext ctx) {
		return optionDetailService.getAccountStatus();
	}

}
