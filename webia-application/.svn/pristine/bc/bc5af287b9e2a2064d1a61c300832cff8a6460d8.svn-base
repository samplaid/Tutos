package lu.wealins.webia.core.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.enums.TransferType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.webia.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, BeneficiaryMapper.class, PolicyHolderMapper.class, ClientFormMapper.class, PolicyTransferMapper.class })
public abstract class UpdateInputRequestMapper {

	private static final int SUBSCRIPTION_COVERAGE = 1;
	@Autowired
	private BenefClauseFormMapper benefClauseFormMapper;

	@Mappings({
			@Mapping(source = "broker.partnerAuthorized", target = "brokerPartnerAuthorized"),
			@Mapping(source = "policyTransferForms", target = "policyTransfers")
	})
	public abstract UpdateInputRequest asUpdateInputRequest(AppFormDTO in);

	@AfterMapping
	public UpdateInputRequest afterEntityMapping(AppFormDTO in, @MappingTarget UpdateInputRequest out) {

		out.setDeathPolicyBeneficiaryClauses(mapPolicyBeneficiaryClauseDTOs(in.getDeathBenefClauseForms(), in.getPolicyId()));
		out.setLifePolicyBeneficiaryClauses(mapPolicyBeneficiaryClauseDTOs(in.getLifeBenefClauseForms(), in.getPolicyId()));

		// apply the coverage to the policy transfer (always 1 for a subscription)
		out.getPolicyTransfers().stream().forEach(pt -> {
			pt.setPolicy(in.getPolicyId());
			pt.setFkPoliciespolId(in.getPolicyId());
			pt.setTransferType(in.getPaymentTransfer() ? TransferType.RE_INVEST : TransferType.TRANSFER);
			pt.setCoverage(new Integer(SUBSCRIPTION_COVERAGE));
		});

		return out;
	}

	private Collection<PolicyBeneficiaryClauseDTO> mapPolicyBeneficiaryClauseDTOs(Collection<BenefClauseFormDTO> benefClauseForms, String policyId) {
		Collection<PolicyBeneficiaryClauseDTO> policyBeneficiaryClauses = new ArrayList<>();
		for (BenefClauseFormDTO benefClauseForm : benefClauseForms) {
			PolicyBeneficiaryClauseDTO policyBeneficiaryClause = benefClauseFormMapper.asPolicyBeneficiaryClauseDTO(benefClauseForm);
			policyBeneficiaryClause.setPolicy(policyId);
			policyBeneficiaryClause.setFkPoliciespolId(policyId);

			policyBeneficiaryClauses.add(policyBeneficiaryClause);
		}

		return policyBeneficiaryClauses;
	}

}
