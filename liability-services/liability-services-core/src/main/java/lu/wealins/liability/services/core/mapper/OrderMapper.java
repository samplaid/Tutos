package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.webia.services.OrderFIDDTO;
import lu.wealins.liability.services.core.persistence.entity.EstimatedOrderNoEntity;
import lu.wealins.liability.services.core.persistence.entity.ValorizedOrderForFIDEntity;
import lu.wealins.liability.services.core.persistence.entity.ValorizedOrderNoEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface OrderMapper {

	/**
	 * Constant CANCELLED_ORDER_SUFFIX
	 */
	public static final String CANCELLED_ORDER_SUFFIX = "A";

	/**
	 * Constant ESTIMATED_ORDER
	 */
	public static final String ESTIMATED_ORDER = "_E";

	/**
	 * Constant VALORIZED_ORDER
	 */
	public static final String VALORIZED_ORDER = "_V";

	/**
	 * Constant Amount as quantity
	 */
	public static final String IS_AMOUNT_AS_QUANTITY = "M";

	/**
	 * Constant Both as quantity
	 */
	public static final String IS_BOTH_AS_QUANTITY = "B";

	/**
	 * Constant ACHAT
	 */
	public static final String ACHAT = "ACHAT";

	/**
	 * Constant VENTE
	 */
	public static final String VENTE = "VENTE";

	/**
	 * Constant quantity
	 */
	public static final String IS_QUANTITY = "Q";
	
	/**
	 * Constant Both as quantity
	 */
	public static final String AGGREGATION_FLAG = "N";

	@Mappings({
			@Mapping(target = "origin", constant = "LISSIA"),
			@Mapping(source = "id", target = "groupeId"),
			@Mapping(target = "cancelId", constant = ""),
			@Mapping(target = "natureId", constant = "CLE"),
			@Mapping(source = "policyId", target = "contractId"),
			@Mapping(source = "policyId", target = "policyId"),
			@Mapping(source = "depositAccount", target = "portfolio"),
			@Mapping(source = "activityDate", target = "entryDate"),
			@Mapping(source = "fundCurrency", target = "amountCurrency"),
			@Mapping(source = "price", target = "nav"),
			@Mapping(source = "valorizationDate", target = "navDate"),
			@Mapping(target = "isInvestment", constant = "Y"),
			@Mapping(target = "isConversion", constant = "N"),
			@Mapping(target = "isTechnicalCancel", constant = "N"),
			@Mapping(target = "isCancel", constant = "N"),
			@Mapping(target = "isEstimated", constant = "E"),
			@Mapping(target = "isQantity", constant = "Q"),
			@Mapping(target = "aggregationFlag", constant = "N")
	})
	OrderDTO asEstimatedOrderDto(EstimatedOrderNoEntity in);

	@Mappings({
			@Mapping(target = "origin", constant = "LISSIA"),
			@Mapping(source = "id", target = "groupeId"),
			@Mapping(target = "cancelId", constant = ""),
			@Mapping(target = "natureId", constant = "CLE"),
			@Mapping(source = "policyId", target = "contractId"),
			@Mapping(source = "policyId", target = "policyId"),
			@Mapping(source = "depositAccount", target = "portfolio"),
			@Mapping(source = "activityDate", target = "entryDate"),
			@Mapping(source = "fundCurrency", target = "amountCurrency"),
			@Mapping(source = "price", target = "nav"),
			@Mapping(source = "valorizationDate", target = "navDate"),
			@Mapping(target = "isInvestment", constant = "Y"),
			@Mapping(target = "isConversion", constant = "N"),
			@Mapping(target = "isTechnicalCancel", constant = "N"),
			@Mapping(target = "isCancel", constant = "N"),
			@Mapping(target = "isEstimated", constant = "E"),
			@Mapping(target = "isQantity", constant = "Q"),
			@Mapping(target = "aggregationFlag", constant = "N")
	})
	OrderDTO asAdditionalEstimatedOrderDto(ValorizedOrderNoEntity in);

	@Mappings({
			@Mapping(target = "origin", constant = "LISSIA"),
			@Mapping(source = "id", target = "groupeId"),
			@Mapping(target = "cancelId", constant = ""),
			@Mapping(target = "natureId", constant = "CLE"),
			@Mapping(source = "policyId", target = "contractId"),
			@Mapping(source = "policyId", target = "policyId"),
			@Mapping(source = "depositAccount", target = "portfolio"),
			@Mapping(source = "activityDate", target = "entryDate"),
			@Mapping(source = "fundCurrency", target = "amountCurrency"),
			@Mapping(source = "price", target = "nav"),
			@Mapping(source = "valorizationDate", target = "navDate"),
			@Mapping(target = "isInvestment", constant = "N"),
			@Mapping(target = "isConversion", constant = "Y"),
			@Mapping(target = "isTechnicalCancel", constant = "N"),
			@Mapping(target = "isCancel", constant = "N"),
			@Mapping(target = "isEstimated", constant = "C"),
			@Mapping(target = "isQantity", constant = "B"),
			@Mapping(target = "aggregationFlag", constant = "N")
	})
	OrderDTO asValorizedOrderDto(ValorizedOrderNoEntity in);

	@Mappings({
			@Mapping(source = "injectionReference", target = "transactionId"),
			@Mapping(source = "operationDate", target = "transactionDate"),
			@Mapping(source = "accountRoot", target = "portfolioCode"),
			@Mapping(source = "eventLabel", target = "eventType"),
			@Mapping(source = "transactionStatus", target = "status"),
			@Mapping(source = "orderType", target = "transactionType"),
			@Mapping(source = "fund", target = "funds"),
	})
	OrderFIDDTO asValorizedOrderForFIDDto(ValorizedOrderForFIDEntity in);
}
