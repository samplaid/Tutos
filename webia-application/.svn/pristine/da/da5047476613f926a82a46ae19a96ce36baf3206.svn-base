/**
 * 
 */
package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.FrenchTaxPolicyTransactionDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.SurrenderReportResultDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance.CalculationDetails;
import lu.wealins.editing.common.webia.SurrenderDetails.FiscalDataFrance.CalculationDetails.Entry;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.helper.FrenchTaxHelper;
import lu.wealins.webia.core.service.impl.LiabilityPolicyServiceImpl;
import lu.wealins.webia.core.utils.TransactionTaxType;


/**
 * @author NGA
 *
 */
@Mapper(componentModel = "spring", uses = BigDecimalToAmountTypeMapper.class)
public abstract class TransactionTaxMapper {

	private static final Logger logger = LoggerFactory.getLogger(TransactionTaxMapper.class);
	private static final String TRANSACTION_ORIGIN = "LISSIA";

	@Autowired
	private LiabilityFundTransactionService liabilityTransactionService;

	@Autowired
	FrenchTaxHelper frenchTaxHelper;

	@Autowired
	LiabilityPolicyServiceImpl policyService;

	@Autowired
	private LiabilityPolicyValuationService liabilityPolicyValuationService;

	@Mappings({ @Mapping(source = "lastTransactionId", target = "originId"),
			@Mapping(source = "transactionType", target = "transactionType"),
			@Mapping(source = "policy", target = "policy"),
			@Mapping(source = "effectiveDate", target = "transactionDate", dateFormat = "yyyy-MM-dd"),
			@Mapping(source = "netTransactionAmount", target = "transactionNetAmount"),
			@Mapping(source = "transactionCurrency", target = "currency") })
	public abstract TransactionTaxDTO asTransactionTaxDTO(FrenchTaxPolicyTransactionDTO in);

	@AfterMapping
	protected TransactionTaxDTO transactionTaxDTOAfterMapping(FrenchTaxPolicyTransactionDTO in, @MappingTarget TransactionTaxDTO target) {
		
		if (target == null) {
			return target;
		}
		Optional<String> isFrenchTaxable = frenchTaxHelper.isPolicyFrenchTaxable(target.getPolicy());
		target.setTaxCountry(isFrenchTaxable.orElse(StringUtils.EMPTY));
		if (TransactionTaxType.MATU.name().equals(target.getTransactionType())
				|| TransactionTaxType.WITH.name().equals(target.getTransactionType())
				|| TransactionTaxType.SURR.name().equals(target.getTransactionType()) ) {
			List<PolicyTransactionValuationDTO> policyValuationAfterTransactions = (List<PolicyTransactionValuationDTO>) liabilityPolicyValuationService
					.getPolicyValuationAfterTransaction(target.getOriginId().longValue());
			String EffectiveDateParsed = null;

			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				EffectiveDateParsed = format.format(target.getTransactionDate());
			} catch (Exception parseException) {
				logger.error("Error parsing of transaction Date when mapping  {} ", parseException);
			}

			BigDecimal fundAmount = liabilityTransactionService.getFundTransactionAmount(target.getPolicy(),
					target.getTransactionType(), EffectiveDateParsed);
			BigDecimal policyValue = fundAmount;
			if (policyValuationAfterTransactions == null || policyValuationAfterTransactions.isEmpty()) {
				target.setPolicyValue(policyValue);
			}

			policyValue = policyValuationAfterTransactions.stream()
					.filter(policytransactionValue -> policytransactionValue.getPrice() != null)
					.map(value -> value.getPrice().multiply(value.getUnits()).setScale(6, RoundingMode.HALF_UP))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			if (fundAmount == null) {
				target.setPolicyValue(policyValue);
			} else {
				target.setPolicyValue(policyValue.add(fundAmount));
			}
		}

		if (target.getPolicy() != null && !target.getPolicy().trim().isEmpty()) {
			PolicyDTO policyDTO = policyService.getPolicy(target.getPolicy());
			target.setPolicyEffectDate(policyDTO.getDateOfCommencement());
		}
		target.setOrigin(TRANSACTION_ORIGIN);
		target.setStatus(1);
		return target;
	}


	public abstract CalculationDetails asCalculationDetails(SurrenderReportResultDTO in);

	@AfterMapping
	protected CalculationDetails calculationDetailsAfterMapping(SurrenderReportResultDTO in,
			@MappingTarget CalculationDetails target) {
		if(target == null) {
			target = new CalculationDetails();
		}

		List<SurrenderReportResultDetailsDTO> surrenderdetails = in.getSurrenderReportDetails();
		if (surrenderdetails != null && !surrenderdetails.isEmpty()) {
			List<Entry> entries = surrenderdetails.stream().map(surrenderdetail -> asEntry(surrenderdetail))
					.collect(Collectors.toList());
			target.setEntries(entries);
		}

		return target;
	}

	@Mappings({ @Mapping(source = "policy", target = "policyId"),
			@Mapping(source = "transactiondate", target = "transactionDate"),
			@Mapping(source = "transactionTaxEventType", target = "transationType"),
			@Mapping(source = "premiumDate", target = "premiumDate"),
			@Mapping(source = "splitPercent", target = "splitPercent") })
	public abstract Entry asEntry(SurrenderReportResultDetailsDTO in);
}
