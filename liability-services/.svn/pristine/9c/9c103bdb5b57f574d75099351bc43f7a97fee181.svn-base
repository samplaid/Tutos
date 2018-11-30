package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.FundTransactionEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.FundTransactionDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface FundTransactionMapper {

	@Mappings({
			@Mapping(source = "policy.polId", target = "polId"),
			@Mapping(source = "policyFundInstruction.pfiId", target = "pfiId"),
			@Mapping(source = "transaction.trnId", target = "trnId"),
			@Mapping(ignore = true, target = "accountTransactions"),
			@Mapping(ignore = true, target = "fundTransactionDate")
	})
	FundTransactionDTO asFundTransactionDTO(FundTransactionEntity in);

	List<FundTransactionDTO> asFundTransactionDTOs(List<FundTransactionEntity> in);

}
