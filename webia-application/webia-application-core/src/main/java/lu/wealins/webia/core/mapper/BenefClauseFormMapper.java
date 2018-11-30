package lu.wealins.webia.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.webia.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class BenefClauseFormMapper {

	@Mappings({
			@Mapping(source = "code", target = "clauseCd"),
			@Mapping(source = "rank", target = "rankNumber"),
			@Mapping(source = "textOfClause", target = "clauseText"),
			@Mapping(target = "clauseFormTp", expression = "java(\"Death\".equals(in.getType()) ? \"D\" : \"L\")"),
			@Mapping(target = "clauseTp", expression = "java(\"F\".equals(in.getTypeOfClause()) ? \"Free\" : \"Standard\")"),
	})
	public abstract BenefClauseFormDTO asBenefClauseFormDTO(PolicyBeneficiaryClauseDTO in);

	public abstract Collection<BenefClauseFormDTO> asBenefClauseFormDTOs(Collection<PolicyBeneficiaryClauseDTO> in);

	@Mappings({
			@Mapping(source = "rankNumber", target = "rank"),
			@Mapping(target = "typeOfClause", expression = "java(\"Free\".equals(in.getClauseTp()) ? \"F\" : \"S\")"),
			@Mapping(target = "status", constant = "1"),
			@Mapping(target = "type", expression = "java(\"D\".equals(in.getClauseFormTp()) ? \"Death\" : \"Life\")"),
			@Mapping(source = "clauseText", target = "textOfClause"),
			@Mapping(source = "clauseCd", target = "code"),
	})
	public abstract ImpItmPbcPolicyBeneficiaryClauses asImpItmPbcPolicyBeneficiaryClauses(BenefClauseFormDTO in);

	@Mappings({
			@Mapping(source = "rankNumber", target = "rank"),
			@Mapping(target = "typeOfClause", expression = "java(\"Free\".equals(in.getClauseTp()) ? \"F\" : \"S\")"),
			@Mapping(target = "status", constant = "1"),
			@Mapping(target = "type", expression = "java(\"D\".equals(in.getClauseFormTp()) ? \"Death\" : \"Life\")"),
			@Mapping(source = "clauseText", target = "textOfClause"),
			@Mapping(source = "clauseCd", target = "code"),
	})
	public abstract PolicyBeneficiaryClauseDTO asPolicyBeneficiaryClauseDTO(BenefClauseFormDTO in);

	public abstract Collection<PolicyBeneficiaryClauseDTO> asPolicyBeneficiaryClauseDTOs(Collection<BenefClauseFormDTO> in);

}
