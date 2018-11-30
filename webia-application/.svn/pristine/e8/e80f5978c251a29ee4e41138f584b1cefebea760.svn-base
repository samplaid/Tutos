package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.editing.common.webia.Fund;

/**
 * 
 * @author bqv55
 *
 */
@Mapper(componentModel = "spring")
public interface FundMapper {

	@Mappings({
			@Mapping(source = "fdsId", target = "fundId"),
			@Mapping(source = "documentationName", target = "label"),
			@Mapping(source = "isinCode", target = "ISIN"),
			@Mapping(source = "riskProfile", target = "riskProfile.riskProfileCode"),
			@Mapping(source = "fundClassification", target = "fundClassification.value"),
			@Mapping(source = "groupingCode", target = "fundClassification.category"),
	})
	public Fund asFund(FundDTO in);

	FundDTO asFundDTO(FundLiteDTO in);
}
