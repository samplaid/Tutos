package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

import lu.wealins.liability.services.core.persistence.entity.AccountTransactionEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AccountTransactionDTO;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = { StringToTrimString.class })
public interface AccountTransactionMapper {
	@Mappings({
			@Mapping(source = "accountBalance.abnId", target = "abnId"),
			@Mapping(source = "fundTransaction.ftrId", target = "ftrId"),
			@Mapping(source = "fundTransaction.fundTransactionDate.fund.fundSubType", target = "fundSubType"),
			@Mapping(source = "policyCoverage.pocId", target = "pocId"),
			@Mapping(source = "postingSet.pstId", target = "pstId"),
			@Mapping(source = "transaction.trnId", target = "trnId"),
			@Mapping(source = "transaction.policy.product.nlCountry", target = "nlCountry"),
			@Mapping(source = "transaction.policy.polId", target = "polId"),
			@Mapping(source = "transaction.policy.issueCountryOfResidence", target = "country"),
			@Mapping(source = "transaction.policy.product.nlProduct", target = "product"),
			@Mapping(source = "transaction.policy.brokerRefContract", target = "brokerRefContract"),
			@Mapping(source = "transaction.policy.product.prdId", target = "productCd"),
			@Mapping(source = "transaction.effectiveDate", target = "effectiveDate"),
			@Mapping(source = "policyCoverage.coverage", target = "coverage"),
	})

	AccountTransactionDTO asAccountTransactionDTO(AccountTransactionEntity in);

	List<AccountTransactionDTO> asAccountTransactionDTOs(List<AccountTransactionEntity> in);

}
