package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.helper.MoneyOutFeesHelper;

@Mapper(componentModel = "spring")
public abstract class SurrenderMapper {

	@Autowired
	private MoneyOutFeesHelper moneyOutFeesHelper;

	@Mappings({
			@Mapping(ignore = true, target = "transactionFees"),
			@Mapping(ignore = true, target = "brokerTransactionFees")
	})
	public abstract PolicySurrenderDTO asPolicySurrenderDTO(TransactionFormDTO in);

	@AfterMapping
	protected PolicySurrenderDTO afterMapping(TransactionFormDTO in, @MappingTarget PolicySurrenderDTO target) {
		BigDecimal transactionFees = in.getTransactionFees();
		BigDecimal brokerTransactionFees = in.getBrokerTransactionFees();

		target.setBrokerTransactionFees(moneyOutFeesHelper.getBrokerFees(transactionFees, brokerTransactionFees));
		target.setTransactionFees(transactionFees);

		return target;
	}
}
