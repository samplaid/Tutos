package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO.ImpGrpPfi;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO.ImpPasPolicyAgentShares;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO.ImpPocPolicyCoverages;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.helper.MoneyOutFeesHelper;
import lu.wealins.webia.core.utils.Constantes;

@Mapper(componentModel = "spring")
public abstract class WithdrawalMapper {
	@Autowired
	LiabilityPolicyService liabilityPolicyService;

	@Autowired
	private MoneyOutFeesHelper moneyOutFeesHelper;


	public abstract WithdrawalInputDTO asWithdrawalInputDTO(TransactionFormDTO in);

	@AfterMapping
	public WithdrawalInputDTO afterEntityMapping(TransactionFormDTO in, @MappingTarget WithdrawalInputDTO target) {
		PolicyDTO policyDto = liabilityPolicyService.getPolicy(in.getPolicyId());

		setupImpGrpPfi(in, target, policyDto);
		setupImpPocPolicyCoverages(in, target);
		setupImpPasPolicyAgentShares(in, target, policyDto);

		return target;
	}

	private void setupImpGrpPfi(TransactionFormDTO in, WithdrawalInputDTO target, PolicyDTO policyDto) {
		ImpGrpPfi impGrpPfi = new ImpGrpPfi();
		List<ImpGrpPfi.Row> rows = impGrpPfi.getRows();

		addRows(rows, in, policyDto);

		target.setImpGrpPfi(impGrpPfi);
	}

	private void addRows(List<ImpGrpPfi.Row> rows, TransactionFormDTO in, PolicyDTO policyDto) {

		if (in.getSpecificAmountByFund() == false) {
		ImpGrpPfi.Row row = new ImpGrpPfi.Row();

		ImpItmPfiPolicyFundInstructions impItmPfiPolicyFundInstructions = new ImpItmPfiPolicyFundInstructions();
		impItmPfiPolicyFundInstructions.setInstructionDate(new SimpleDateFormat(Constantes.YYYYMMDD).format(in.getEffectiveDate()));
		impItmPfiPolicyFundInstructions.setPolicy(in.getPolicyId());

		short type = in.getSpecificAmountByFund() == true ? (short) 6/* Defined withdrawal */ : (short) 2/* Withdrawal */;

		impItmPfiPolicyFundInstructions.setType(type);
		impItmPfiPolicyFundInstructions.setMovementCcy(policyDto.getCurrency());
			BigDecimal formTransactionFees = in.getTransactionFees();
			impItmPfiPolicyFundInstructions.setSwitchFee(formTransactionFees.toString());
		impItmPfiPolicyFundInstructions.setSwitchFeeCcy(policyDto.getCurrency());



			/*
			 * for (FundTransactionFormDTO fundTran : in.getFundTransactionForms()) { impItmPfiPolicyFundInstructions.setFund(fundTran.getFundId());
			 * impItmPfiPolicyFundInstructions.setMovementType(getMovementType(fundTran.getInputType())); impItmPfiPolicyFundInstructions.setMovementValue(getMovementValue(fundTran));
			 * 
			 * row.setImpItmPfiPolicyFundInstructions(impItmPfiPolicyFundInstructions); rows.add(row); row = new ImpGrpPfi.Row(); }
			 */

			row.setImpItmPfiPolicyFundInstructions(impItmPfiPolicyFundInstructions);
			rows.add(row);
		}
	}

	@Mappings({
			@Mapping(source = "fundId", target = "fund")
	})
	public abstract ImpItmPfiPolicyFundInstructions asImpItmPfiPolicyFundInstructions(FundTransactionFormDTO in);

	@AfterMapping
	public ImpItmPfiPolicyFundInstructions afterEntityMapping(FundTransactionFormDTO in, @MappingTarget ImpItmPfiPolicyFundInstructions target) {
		// target.setFund(in.getFundId());
		target.setMovementType(getMovementType(in.getInputType()));

		return target;

	}

	private void setupImpPocPolicyCoverages(TransactionFormDTO in, WithdrawalInputDTO target) {
		ImpPocPolicyCoverages impPocPolicyCoverages = new ImpPocPolicyCoverages();
		impPocPolicyCoverages.setPolicy(in.getPolicyId());

		target.setImpPocPolicyCoverages(impPocPolicyCoverages);

	}

	private void setupImpPasPolicyAgentShares(TransactionFormDTO in, WithdrawalInputDTO target, PolicyDTO policyDto) {
		ImpPasPolicyAgentShares impPasPolicyAgentShares = new ImpPasPolicyAgentShares();
		PolicyAgentShareDTO broker = policyDto.getBroker();
		if (broker != null) {
			String agent = broker.getAgent().getAgtId();
			impPasPolicyAgentShares.setAgent(agent);
			BigDecimal formTransactionFees = in.getTransactionFees();
			BigDecimal formBrokerTransactionFees = in.getBrokerTransactionFees();
			BigDecimal brokerFees = moneyOutFeesHelper.getBrokerFees(formTransactionFees, formBrokerTransactionFees);
			impPasPolicyAgentShares.setPercentage(brokerFees.toString());
			target.setImpPasPolicyAgentShares(impPasPolicyAgentShares);
		}

	}

	private short getMovementType(FundTransactionInputType type) {
		short result = 0;
		switch (type) {
		case ALL_FUND:
			result = 1;
			break;
		case GROSS_AMOUNT:
			result = 2;
			break;
		case UNITS:
			result = 3;
			break;
		case PERCENTAGE:
			result = 4;
			break;

		}
		return result;
	}

	private String getMovementValue(FundTransactionFormDTO fund) {
		String result = "";
		switch (fund.getInputType()) {
		case GROSS_AMOUNT:
			result = fund.getAmount().toString();
			break;
		case UNITS:
			result = fund.getUnits().toString();
			break;
		case PERCENTAGE:
			result = fund.getPercentage().toString();
			break;
		default:
			break;

		}
		return result;
	}

	/*
	 * private Map<Short,String> getMap (FundTransactionInputType type, FundTransactionFormDTO fund) { Map<Short,String> map; String result = ""; switch (type) { case GROSS_AMOUNT: result =
	 * fund.getAmount().toString(); map.put(1, "") break; case UNITS: result = fund.getUnits().toString(); break; case PERCENTAGE: result = fund.getPercentage().toString(); break; default: break;
	 * 
	 * } return result; }
	 */
}
