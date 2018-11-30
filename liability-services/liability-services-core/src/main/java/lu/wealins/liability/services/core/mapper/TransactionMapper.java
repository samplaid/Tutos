package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.liability.services.core.persistence.entity.TransactionEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, TransactionDetailMapper.class, PolicyFundInstructionMapper.class, AccountTransactionMapper.class, DocumentRequestMapper.class,
		FundTransactionMapper.class })
public interface TransactionMapper {

	static final String LISSIA = "LISSIA";

	@Mappings({
			@Mapping(source = "order.ordId", target = "ordId"),
			@Mapping(source = "policy.polId", target = "polId")
	})
	TransactionDTO asTransactionDTO(TransactionEntity in);

	List<TransactionDTO> asTransactionDTOs(List<TransactionEntity> in);

	@Mappings({
			@Mapping(source = "currency", target = "currency"),
			@Mapping(source = "trnId", target = "originId"),
			@Mapping(source = "policy.dateOfCommencement", target = "policyEffectDate"),
			@Mapping(source = "effectiveDate", target = "transactionDate"),
			@Mapping(source = "policyId", target = "policy"),
			@Mapping(source = "createdBy", target = "user")
	})
	TransactionTaxDTO asTransactionTaxDTO(TransactionEntity in);

	List<TransactionTaxDTO> asTransactionTaxDTOs(List<TransactionEntity> in);

	@AfterMapping
	default TransactionTaxDTO transactionTaxDTO(TransactionEntity in, @MappingTarget TransactionTaxDTO target) {
		target.setOrigin(LISSIA);
		return target;
	}

}
