/**
 * 
 */
package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.common.dto.liability.services.enums.CountryCodeEnum;
import lu.wealins.webia.services.core.service.ClientRoleActivationResolverService;
import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;

@Service
public class ClientRoleActivationResolverServiceImpl implements ClientRoleActivationResolverService {

	/* (non-Javadoc)
	 * @see lu.wealins.webia.core.service.ClientRoleActivationResolverService#solveClientRoleActivation(java.lang.String)
	 */
	@Override
	public List<? extends ActivableRoleBasedCountry> solveClientRoleActivation(String countryCode) {
		List<ActivableRoleBasedCountry> activables = new ArrayList<>();

		activables.add(ClientRelationType.BENEFICIARY_USUFRUCTUARY.setEnable(CountryCodeEnum.FRENCH.getCode().equals(countryCode)));
		activables.add(ClientRelationType.BENEFICIARY_BARE_OWNER.setEnable(CountryCodeEnum.FRENCH.getCode().equals(countryCode)));
		activables.add(ClientRelationType.IRREVOCABLE_BEN.setEnable(!CountryCodeEnum.FRENCH.getCode().equals(countryCode) && !CountryCodeEnum.BELGIUM.getCode().equals(countryCode)));
		activables.add(ClientRelationType.ACCEPTANT.setEnable(CountryCodeEnum.BELGIUM.getCode().equals(countryCode)));
		activables.add(ClientRelationType.SEPARATE_PROPERTY_NO_RIGHTS.setEnable(CountryCodeEnum.SWEDEN.getCode().equals(countryCode) || CountryCodeEnum.NORWAY.getCode().equals(countryCode)
				|| CountryCodeEnum.FINLAND.getCode().equals(countryCode) || CountryCodeEnum.PORTUGAL.getCode().equals(countryCode)));

		activables.add(ClientRelationType.SEPARATE_PROPERTY_RIGHTS.setEnable(CountryCodeEnum.SWEDEN.getCode().equals(countryCode) || CountryCodeEnum.NORWAY.getCode().equals(countryCode)
				|| CountryCodeEnum.FINLAND.getCode().equals(countryCode) || CountryCodeEnum.PORTUGAL.getCode().equals(countryCode)));
		return activables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ClientRoleActivationResolverService#solvePolicyHolderRoleActivation(java.lang.String)
	 */
	@Override
	public List<? extends ActivableRoleBasedCountry> solvePolicyHolderRoleActivation(String countryCode, boolean productCapi, boolean yearTerm) {
		List<ActivableRoleBasedCountry> activables = new ArrayList<>();

		if (!productCapi && (CountryCodeEnum.FRENCH.getCode().equals(countryCode) ||
				CountryCodeEnum.BELGIUM.getCode().equals(countryCode) ||
				CountryCodeEnum.LUXEMBOURG.getCode().equals(countryCode))) {

			activables.add(ClientRelationType.USUFRUCTUARY.setEnable(CountryCodeEnum.FRENCH.getCode().equals(countryCode)));
			activables.add(ClientRelationType.BARE_OWNER.setEnable(CountryCodeEnum.FRENCH.getCode().equals(countryCode)));
			activables.add(ClientRelationType.SUCCESSION_DEATH.setEnable(!productCapi));
			activables.add(ClientRelationType.SUCCESSION_LIFE.setEnable(!productCapi && yearTerm));
		}


		return activables;
	}

}
