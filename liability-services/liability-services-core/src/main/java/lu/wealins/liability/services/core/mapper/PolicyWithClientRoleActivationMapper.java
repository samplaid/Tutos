package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.liability.services.core.business.BeneficiaryService;
import lu.wealins.liability.services.core.business.OtherClientService;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, ProductMapper.class, PolicyAgentShareMapper.class,
		PolicyCoverageMapper.class, PolicyPremiumMapper.class, UoptDetailMapper.class })
public abstract class PolicyWithClientRoleActivationMapper {

	@Autowired
	private PolicyMapper policyMapper;
	@Autowired
	protected BeneficiaryService beneficiaryService;
	@Autowired
	protected OtherClientService otherClientService;

	public PolicyDTO asPolicyDTOWithClientRoleActivation(PolicyEntity in, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		PolicyDTO policyDTO = policyMapper.asPolicyDTO(in);

		mapClients(in, policyDTO, clientRoleActivationFlag);

		return policyDTO;
	}

	protected void mapClients(PolicyEntity policyEntity, PolicyDTO policyDTO, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		policyDTO.setLifeBeneficiaries(beneficiaryService.getLifeBeneficiaries(policyEntity, clientRoleActivationFlag));
		policyDTO.setDeathBeneficiaries(beneficiaryService.getDeathBeneficiaries(policyEntity, clientRoleActivationFlag));
		policyDTO.setOtherClients(otherClientService.getOtherClients(policyEntity, clientRoleActivationFlag));
	}

}
