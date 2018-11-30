package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;
import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.core.service.ClientRoleActivationFlagService;
import lu.wealins.webia.core.service.ClientRoleActivationResolverService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductService;

@Service
public class ClientRoleActivationFlagServiceImpl implements ClientRoleActivationFlagService {

	@Autowired
	private ClientRoleActivationResolverService clientRoleActivationResolverService;
	@Autowired
	private LiabilityPolicyService policyService;
	@Autowired
	private LiabilityProductService productService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientRoleActivationFlagService#solveBeneficiaryRoleActivation(java.lang.String)
	 */
	@Override
	public ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation(String countryCode) {
		ClientRoleActivationFlagDTO activableRolesFlag = new ClientRoleActivationFlagDTO();
		List<? extends ActivableRoleBasedCountry> activableRoles = clientRoleActivationResolverService.solveClientRoleActivation(countryCode);
		activableRoles.forEach(activableRole -> dispatch(activableRole, activableRolesFlag));
		return activableRolesFlag;
	}

	/**
	 * @param activableBean
	 * @param activationFlag
	 * @return
	 */
	private void dispatch(ActivableRoleBasedCountry activableBean, ClientRoleActivationFlagDTO activationFlag) {
		setupActivationFlag(activationFlag, activableBean.getRoleNumber(), activableBean.isEnable());
	}

	@Override
	public void setupActivationFlag(ClientRoleActivationFlagDTO activationFlag, Integer roleNumber, boolean isEnable) {
		ClientRelationType roleRef = ClientRelationType.toEnum(roleNumber);
		if (roleRef != null) {
			switch (roleRef) {
			case ACCEPTANT:
				activationFlag.setActivatedAcceptant(isEnable);
				break;
			case BARE_OWNER:
				activationFlag.setActivatedBareOwner(isEnable);
				break;
			case BENEFICIARY_AT_DEATH:
				activationFlag.setActivatedDeathBeneficiary(isEnable);
				break;
			case IRREVOCABLE_BEN:
				activationFlag.setActivatedIrrevocable(isEnable);
				break;
			case BENEFICIARY_AT_MATURITY:
				activationFlag.setActivatedLifeBeneficiary(isEnable);
				break;
			case SEPARATE_PROPERTY_NO_RIGHTS:
				activationFlag.setActivatedSeparatePropertyNoRights(isEnable);
				break;
			case SEPARATE_PROPERTY_RIGHTS:
				activationFlag.setActivatedSeparatePropertyRights(isEnable);
				break;
			case USUFRUCTUARY:
				activationFlag.setActivatedUsufructuary(isEnable);
				break;
			case BENEFICIARY_BARE_OWNER:
				activationFlag.setActivatedBenefBareOwner(isEnable);
				break;
			case BENEFICIARY_USUFRUCTUARY:
				activationFlag.setActivatedBenefUsuFructary(isEnable);
				break;
			case SUCCESSION_DEATH:
				activationFlag.setActivatedSuccessionDeath(isEnable);
				break;
			case SUCCESSION_LIFE:
				activationFlag.setActivatedSuccessionLife(isEnable);
				break;
			default:
				break;
			}
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientRoleActivationFlagService#solvePolicyHolderRoleActivation(java.lang.String, boolean, boolean)
	 */
	@Override
	public ClientRoleActivationFlagDTO solvePolicyHolderRoleActivation(String countryCode, boolean productCapi, boolean yearTerm) {
		ClientRoleActivationFlagDTO activableRolesFlag = new ClientRoleActivationFlagDTO();
		List<? extends ActivableRoleBasedCountry> activableRoles = clientRoleActivationResolverService.solvePolicyHolderRoleActivation(countryCode, productCapi, yearTerm);
		activableRoles.forEach(activableRole -> dispatch(activableRole, activableRolesFlag));
		return activableRolesFlag;
	}

	public ClientRoleActivationFlagDTO solveAllRoleActivations(String policyId) {
		PolicyLightDTO policyLight = policyService.getPolicyLight(policyId);
		if (policyLight == null) {
			throw new IllegalStateException("Policy " + policyId + " cannot be found.");
		}

		String countryCode = productService.getCountryCode(policyLight.getPrdId());

		if (countryCode == null) {
			throw new IllegalStateException("No country code liked to the " + policyLight.getPrdId() + ".");
		}

		ClientRoleActivationFlagDTO beneficiaryRoleActivation = solveBeneficiaryRoleActivation(countryCode);

		boolean isCapi = productService.isCapiProduct(policyLight.getPrdId());
		boolean yearTerm = isTerm(policyLight);

		ClientRoleActivationFlagDTO policyHolderRoleActivation = solvePolicyHolderRoleActivation(countryCode, isCapi, yearTerm);

		beneficiaryRoleActivation.setActivatedUsufructuary(policyHolderRoleActivation.isActivatedUsufructuary());
		beneficiaryRoleActivation.setActivatedBareOwner(policyHolderRoleActivation.isActivatedBareOwner());
		beneficiaryRoleActivation.setActivatedSuccessionDeath(policyHolderRoleActivation.isActivatedSuccessionDeath());
		beneficiaryRoleActivation.setActivatedSuccessionLife(policyHolderRoleActivation.isActivatedSuccessionLife());

		return beneficiaryRoleActivation;
	}

	private boolean isTerm(PolicyLightDTO policyLight) {
		PolicyCoverageDTO firstCoverage = policyLight.getFirstCoverage();
		if (firstCoverage == null) {
			return false;
		}

		BigDecimal term = firstCoverage.getTerm();

		return term != null && term.intValue() > 0;
	}

}
