package lu.wealins.webia.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.editing.common.webia.Beneficiary;
import lu.wealins.webia.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class BeneficiaryMapper {

	@Mappings({
			@Mapping(source = "typeNumber", target = "rank"),
			@Mapping(source = "dateOfBirth", target = "birthDate", dateFormat = "yyyy-MM-dd"),
			@Mapping(target = "revocable", expression = "java(in.getIrrevocable() == null ? null : in.getIrrevocable().equals(Boolean.FALSE))"),
			@Mapping(source = "percentageSplit", target = "split"),
			@Mapping(source = "firstName", target = "firstname"),
			@Mapping(source = "exEqualParts", target = "equalParts"),
	})
	public abstract Beneficiary asBeneficiary(BeneficiaryDTO in);

	public abstract Collection<Beneficiary> asBeneficiaries(Collection<BeneficiaryDTO> in);

	@Mappings({
			@Mapping(source = "cliId", target = "clientId"),
			@Mapping(source = "typeNumber", target = "rankNumber"),
			@Mapping(source = "percentageSplit", target = "split"),
			@Mapping(source = "exEqualParts", target = "isEqualParts")
	})
	public abstract BeneficiaryFormDTO asBeneficiaryFormDTO(BeneficiaryDTO in);

	@Mappings({
			@Mapping(source = "clientId", target = "cliId"),
			@Mapping(source = "rankNumber", target = "typeNumber"),
			@Mapping(source = "split", target = "percentageSplit"),
			@Mapping(source = "isEqualParts", target = "exEqualParts")
	})
	public abstract BeneficiaryDTO asBeneficiaryDTO(BeneficiaryFormDTO in);


}
