package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.ApplyBeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.UpdateBeneficiaryChangeRequest;
import lu.wealins.liability.services.core.business.BeneficiaryChangeService;
import lu.wealins.liability.services.ws.rest.BeneficiaryChangeRESTService;

@Component
public class BeneficiaryChangeRESTServiceImpl implements BeneficiaryChangeRESTService {

	@Autowired
	private BeneficiaryChangeService beneficiaryChangeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.BeneficiaryChangeRESTService#getBeneficiaryChange(javax.ws.rs.core.SecurityContext,
	 * lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest)
	 */
	@Override
	public BeneficiaryChangeDTO getBeneficiaryChange(SecurityContext context, BeneficiaryChangeRequest beneficiaryChangeRequest) {
		return beneficiaryChangeService.getBeneficiaryChange(beneficiaryChangeRequest);
	}

	@Override
	public BeneficiaryChangeDTO updateBeneficiaryChange(SecurityContext context, UpdateBeneficiaryChangeRequest updateBeneficiaryChangeRequest) {
		return beneficiaryChangeService.updateBeneficiaryChange(updateBeneficiaryChangeRequest);
	}

	@Override
	public BeneficiaryChangeDTO applyBeneficiaryChange(SecurityContext context, ApplyBeneficiaryChangeRequest applyBeneficiaryChangeRequest) {
		return beneficiaryChangeService.applyBeneficiaryChange(applyBeneficiaryChangeRequest);
	}

	@Override
	public BeneficiaryChangeDTO initBeneficiaryChange(SecurityContext context, BeneficiaryChangeRequest beneficiaryChangeRequest) {
		return beneficiaryChangeService.initBeneficiaryChange(beneficiaryChangeRequest);
	}
	
}
