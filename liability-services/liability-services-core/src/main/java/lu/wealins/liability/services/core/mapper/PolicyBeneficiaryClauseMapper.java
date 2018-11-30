package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class PolicyBeneficiaryClauseMapper {

	public static final int CODE_SIZE = 10;
	public static final int TYPE_SIZE = 10;
	public static final int POLICY_SIZE = 14;
	public static final int CREATED_PROCESS_SIZE = 15;
	public static final int MODIFY_PROCESS_SIZE = 15;
	public static final int CREATED_BY_SIZE = 5;
	public static final int MODIFY_BY_SIZE = 5;

	public abstract PolicyBeneficiaryClauseDTO asPolicyBeneficiaryClauseDTO(PolicyBeneficiaryClauseEntity in);

	public abstract PolicyBeneficiaryClauseEntity asPolicyBeneficiaryClauseEntity(PolicyBeneficiaryClauseDTO in);

	public abstract void asPolicyBeneficiaryClauseEntity(PolicyBeneficiaryClauseDTO in, @MappingTarget PolicyBeneficiaryClauseEntity out);

	public abstract Collection<PolicyBeneficiaryClauseDTO> asPolicyBeneficiaryClauseDTOs(Collection<PolicyBeneficiaryClauseEntity> in);

	@AfterMapping
	public PolicyBeneficiaryClauseEntity afterEntityMapping(PolicyBeneficiaryClauseDTO in, @MappingTarget PolicyBeneficiaryClauseEntity out) {
		
		if (out.getPolicy() != null) {
			out.setPolicy(String.format("%-" + POLICY_SIZE + "s", out.getPolicy()));
		}

		if (out.getTextOfClause() == null) {
			out.setTextOfClause(" ");
		}

		if (out.getType() != null) {
			out.setType(String.format("%-" + TYPE_SIZE + "s", out.getType()));
		}

		if (out.getCode() != null) {
			out.setCode(String.format("%-" + CODE_SIZE + "s", out.getCode()));
		}

		if (out.getFkPoliciespolId() != null) {
			out.setFkPoliciespolId(String.format("%-" + POLICY_SIZE + "s", out.getFkPoliciespolId()));
		}

		if (out.getCreatedProcess() != null) {
			out.setCreatedProcess(String.format("%-" + CREATED_PROCESS_SIZE + "s", out.getCreatedProcess()));
		}

		if (out.getModifyProcess() != null) {
			out.setModifyProcess(String.format("%-" + MODIFY_PROCESS_SIZE + "s", out.getModifyProcess()));
		}

		if (out.getCreatedBy() != null) {
			out.setCreatedBy(String.format("%-" + CREATED_BY_SIZE + "s", out.getCreatedBy()));
		}

		if (out.getModifyBy() != null) {
			out.setModifyBy(String.format("%-" + MODIFY_BY_SIZE + "s", out.getModifyBy()));
		}

		return out;
	}

}