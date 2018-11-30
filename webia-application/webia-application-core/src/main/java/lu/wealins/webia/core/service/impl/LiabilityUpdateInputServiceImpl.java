package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.UpdateInputResponse;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.mapper.UpdateInputRequestMapper;
import lu.wealins.webia.core.service.ClientRoleActivationFlagService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.LiabilityUpdateInputService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityUpdateInputServiceImpl implements LiabilityUpdateInputService {

	private static final String LIABILITY_UPDATE_INPUT = "liability/updateInput";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private UpdateInputRequestMapper updateInputRequestMapper;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	@Autowired
	private ClientRoleActivationFlagService clientRoleActivationFlagService;

	@Autowired
	private LiabilityProductService productService;

	@Override
	public UpdateInputResponse updateInput(AppFormDTO appForm, String usrId) {
		UpdateInputRequest asUpdateInputRequest = updateInputRequestMapper.asUpdateInputRequest(appForm);

		updateClientRelations(asUpdateInputRequest, appForm.getProductCd());
		asUpdateInputRequest.setUserName(workflowUserService.getLogin(usrId));

		return restClientUtils.post(LIABILITY_UPDATE_INPUT, asUpdateInputRequest, UpdateInputResponse.class);
	}

	private void updateClientRelations(UpdateInputRequest updateInputRequest, String productCd) {
		String countryCode = productService.getCountryCode(productCd);
		ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation = clientRoleActivationFlagService.solveBeneficiaryRoleActivation(countryCode);

		updateBeneficiaryRelations(updateInputRequest.getDeathBeneficiaries(), solveBeneficiaryRoleActivation);
		updateBeneficiaryRelations(updateInputRequest.getLifeBeneficiaries(), solveBeneficiaryRoleActivation);
	}

	private void updateBeneficiaryRelations(Collection<BeneficiaryDTO> beneficiaries, ClientRoleActivationFlagDTO solveBeneficiaryRoleActivation) {
		for (BeneficiaryDTO beneficiary : beneficiaries) {
			if (!solveBeneficiaryRoleActivation.isActivatedIrrevocable()) {
				beneficiary.setIrrevocable(Boolean.FALSE);
			}
			if (!solveBeneficiaryRoleActivation.isActivatedSeparatePropertyRights()) {
				beneficiary.setSeparatePropertyRights(Boolean.FALSE);
			}
			if (!solveBeneficiaryRoleActivation.isActivatedSeparatePropertyNoRights()) {
				beneficiary.setSeparatePropertyNoRights(Boolean.FALSE);
			}
			if (!solveBeneficiaryRoleActivation.isActivatedAcceptant()) {
				beneficiary.setAcceptant(Boolean.FALSE);
			}
			if (!solveBeneficiaryRoleActivation.isActivatedBenefUsuFructary()) {
				beneficiary.setUsufructuary(Boolean.FALSE);
			}
			if (!solveBeneficiaryRoleActivation.isActivatedBareOwner()) {
				beneficiary.setBareOwner(Boolean.FALSE);
			}
		}
	}
}
