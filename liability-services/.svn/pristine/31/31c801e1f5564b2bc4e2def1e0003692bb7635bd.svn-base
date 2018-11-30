package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.BeneficiaryService;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipMapper;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipWithClientRoleActivationMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryLiteDTO;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	private CliPolRelationshipMapper cliPolRelationshipMapper;
	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;

	@Autowired
	private CliPolRelationshipWithClientRoleActivationMapper cliPolRelationshipWithClientRoleActivationMapper;

	@Override
	public Collection<BeneficiaryDTO> getLifeBeneficiaries(PolicyEntity policy) {
		return cliPolRelationshipMapper.asLifeBeneficiaryDTOs(policy);
	}

	@Override
	public Collection<BeneficiaryLiteDTO> getDeathBeneficiaryLites(PolicyEntity policy) {
		return cliPolRelationshipMapper.asDeathBeneficiaryLiteDTOs(policy);
	}

	@Override
	public Collection<BeneficiaryLiteDTO> getLifeBeneficiaryLites(PolicyEntity policy) {
		return cliPolRelationshipMapper.asLifeBeneficiaryLiteDTOs(policy);
	}

	@Override
	public Collection<BeneficiaryDTO> getDeathBeneficiaries(PolicyEntity policy) {
		return cliPolRelationshipMapper.asDeathBeneficiaryDTOs(policy);
	}

	@Override
	public Collection<BeneficiaryDTO> getLifeBeneficiaries(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return cliPolRelationshipWithClientRoleActivationMapper.asLifeBeneficiaryDTOs(policy, clientRoleActivationFlag);
	}

	@Override
	public Collection<BeneficiaryDTO> getDeathBeneficiaries(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return cliPolRelationshipWithClientRoleActivationMapper.asDeathBeneficiaryDTOs(policy, clientRoleActivationFlag);
	}

	@Override
	public Collection<BeneficiaryDTO> getLifeBeneficiaries(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return cliPolRelationshipWithClientRoleActivationMapper.asLifeBeneficiaryDTOs(workflowItemId, clientRoleActivationFlag);
	}

	@Override
	public Collection<BeneficiaryDTO> getDeathBeneficiaries(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return cliPolRelationshipWithClientRoleActivationMapper.asDeathBeneficiaryDTOs(workflowItemId, clientRoleActivationFlag);
	}

	@Override
	public void updateDeathBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate) {
		beneficiaries.forEach(beneficiary -> cliPolRelationshipService.saveBeneficiary(beneficiary, CliPolRelationshipType.BENEFICIARY_AT_DEATH, policyId, activeDate));
	}

	@Override
	public void updateLifeBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate) {
		beneficiaries.forEach(beneficiary -> cliPolRelationshipService.saveBeneficiary(beneficiary, CliPolRelationshipType.BENEFICIARY_AT_MATURITY, policyId, activeDate));
	}

	@Override
	public void updateDeathBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate, String workflowItemId) {
		beneficiaries.forEach(beneficiary -> cliPolRelationshipService.saveBeneficiary(beneficiary, CliPolRelationshipType.BENEFICIARY_AT_DEATH, policyId, activeDate, workflowItemId));
	}

	@Override
	public void updateLifeBeneficiaries(Collection<BeneficiaryDTO> beneficiaries, String policyId, Date activeDate, String workflowItemId) {
		beneficiaries.forEach(beneficiary -> cliPolRelationshipService.saveBeneficiary(beneficiary, CliPolRelationshipType.BENEFICIARY_AT_MATURITY, policyId, activeDate, workflowItemId));
	}
}
