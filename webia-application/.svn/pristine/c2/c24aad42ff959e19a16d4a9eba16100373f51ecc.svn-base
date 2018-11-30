package lu.wealins.webia.core.service.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO.ImpGrpPfi;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.AmountType;
import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;
import lu.wealins.webia.core.mapper.WithdrawalMapper;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.utils.Constantes;

@Service
public class WithdrawalServiceHelper {

	@Autowired
	private WithdrawalMapper withdrawalMapper;

	@Autowired
	private LiabilityPolicyCoverageService policyCoverageService;

	private Map<Integer, BigDecimal> getCalculatedProportions(TransactionFormDTO transactionFormDTO) {
		Map<Integer, BigDecimal> proportionMap = new HashMap<Integer, BigDecimal>();

		Collection<UnitByCoverageDTO> coveragesByUnits = policyCoverageService.getUnitByCoverages(transactionFormDTO.getPolicyId());

		if (coveragesByUnits.size() > 0) {
			// get the coverage

			BigDecimal unitsTotal = coveragesByUnits
					.stream()
					.map(UnitByCoverageDTO::getUnits)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			proportionMap = coveragesByUnits.stream()
					.collect(Collectors.toMap(x -> x.getCoverage(), x -> x.getUnits().multiply(transactionFormDTO.getAmount()).divide(unitsTotal, 2, RoundingMode.HALF_UP)));
			roundValues(proportionMap, transactionFormDTO.getAmount());
		}

		return proportionMap;

	}

	private void roundValues(Map<Integer, BigDecimal> proportionMap, BigDecimal amount) {
		// TODO rounding
		BigDecimal totalUnits = proportionMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal amountVariation = amount.subtract(totalUnits);
		Integer key = (Integer) proportionMap.keySet().toArray()[0];
		proportionMap.put(key, proportionMap.get(key).add(amountVariation));

	}

	public List<WithdrawalInputDTO> prepareWithdrawalRequest(TransactionFormDTO transactionFormDTO) {

		List<WithdrawalInputDTO> withdrawalRequests = new ArrayList<WithdrawalInputDTO>();
		
		//Net to gross
		if (transactionFormDTO.getAmountType() == AmountType.GROSS) {
			transactionFormDTO.setAmount(calculateMovementValue(transactionFormDTO));
		}
		Map<Integer, BigDecimal> proportionMap = getCalculatedProportions(transactionFormDTO);
		
		for (Integer coverage : proportionMap.keySet()) {
			WithdrawalInputDTO withdrawal = new WithdrawalInputDTO();
			withdrawal = withdrawalMapper.asWithdrawalInputDTO(transactionFormDTO);
			withdrawal.getImpPocPolicyCoverages().setCoverage(coverage.shortValue());
			 withdrawal.getImpGrpPfi().getRows()
					.forEach(x -> x.getImpItmPfiPolicyFundInstructions().setHolding(coverage.shortValue()));
			 withdrawal.getImpGrpPfi().getRows()
					.forEach(x -> x.getImpItmPfiPolicyFundInstructions().setMovementValue(proportionMap.get(coverage).toString()));
			
			withdrawalRequests.add(withdrawal);
		}

		return withdrawalRequests;
	}

	private BigDecimal calculateMovementValue(TransactionFormDTO transactionFormDTO) {
		BigDecimal result = transactionFormDTO.getAmount();
		if (transactionFormDTO.getTransactionFees() != null && transactionFormDTO.getTransactionFees().compareTo(BigDecimal.ZERO) > 0) {
			result = result.multiply(BigDecimal.ONE.subtract(transactionFormDTO.getTransactionFees()
					.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)));
		}
		
		return result;
	}

	public List<WithdrawalInputDTO> prepareDefinedWithdrawalRequest(TransactionFormDTO transactionFormDTO) {

		List<WithdrawalInputDTO> withdrawalRequests = new ArrayList<WithdrawalInputDTO>();

		Collection<UnitByFundAndCoverageDTO> unitByFundsAndCoverages = policyCoverageService.getUnitByFundsAndCoverages(transactionFormDTO.getPolicyId());

		if (unitByFundsAndCoverages.size() > 0) {
			// get the coverage list
			Set<Integer> coverageList = unitByFundsAndCoverages
					.stream()
					.map(x -> x.getCoverage())
					.collect(Collectors.toSet());
	
			// for each coverage calculate

			for (Integer coverage : coverageList) {
				WithdrawalInputDTO withdrawal = new WithdrawalInputDTO();
				withdrawal = withdrawalMapper.asWithdrawalInputDTO(transactionFormDTO);

				for (FundTransactionFormDTO fund : transactionFormDTO.getFundTransactionForms()) {
					if (fund.getInputType() != null) {
						Map<Integer, BigDecimal> proportionMap = unitByFundsAndCoverages.stream().filter(x -> x.getFdsId().equals(fund.getFundId()))
								.collect(Collectors.toMap(x -> x.getCoverage(), x -> x.getUnits()));

						if (proportionMap.get(coverage) != null) {
							BigDecimal amount = getAmount(fund);
							BigDecimal fundWithDrawal = getProportionByFund(proportionMap, fund, coverage, amount);
							withdrawal.getImpGrpPfi().getRows().add(createPolicyFund(transactionFormDTO, fund, coverage, fundWithDrawal));
						}
					}
				}

				// set the coverage
				withdrawal.getImpPocPolicyCoverages().setCoverage(coverage.shortValue());
				withdrawalRequests.add(withdrawal);
			}
		}

		return withdrawalRequests;
	}
	

	private BigDecimal getProportionByFund(Map<Integer, BigDecimal> proportionMap, FundTransactionFormDTO fund, Integer coverage, BigDecimal withdrawalAmount) {
		BigDecimal result = BigDecimal.ZERO;
		switch (fund.getInputType()) {
		case ALL_FUND:
			break;
		case GROSS_AMOUNT:
		case UNITS:
			result = getfundAmountByCoverage(proportionMap, coverage, withdrawalAmount);
			break;
		case PERCENTAGE:
			result = withdrawalAmount;// getfundParcentagetByCoverage(proportionMap, coverage, withdrawalAmount);
			break;
		}
		return result;


	}

	private ImpGrpPfi.Row createPolicyFund(TransactionFormDTO transactionFormDTO, FundTransactionFormDTO fund, Integer coverage, BigDecimal fundWithDrawal) {
		ImpGrpPfi.Row row = new ImpGrpPfi.Row();
		ImpItmPfiPolicyFundInstructions impItmPfiPolicyFundInstructions = withdrawalMapper.asImpItmPfiPolicyFundInstructions(fund);
		impItmPfiPolicyFundInstructions.setPolicy(transactionFormDTO.getPolicyId());
		impItmPfiPolicyFundInstructions.setHolding(coverage.shortValue());
		impItmPfiPolicyFundInstructions.setSwitchFee((transactionFormDTO.getTransactionFees() == null) ? "0" : transactionFormDTO.getTransactionFees().toString());
		impItmPfiPolicyFundInstructions.setType((short) 6);// Defined Withdrawal
		impItmPfiPolicyFundInstructions.setInstructionDate(new SimpleDateFormat(Constantes.YYYYMMDD).format(transactionFormDTO.getEffectiveDate()));
		impItmPfiPolicyFundInstructions.setMovementValue((fund.getInputType() == FundTransactionInputType.ALL_FUND) ? "" : fundWithDrawal.toString());
		impItmPfiPolicyFundInstructions.setMovementCcy(transactionFormDTO.getCurrency());
		impItmPfiPolicyFundInstructions.setSwitchFeeCcy(transactionFormDTO.getCurrency());
		row.setImpItmPfiPolicyFundInstructions(impItmPfiPolicyFundInstructions);
		return row;

	}

	private BigDecimal getAmount( FundTransactionFormDTO fund){
		BigDecimal result = BigDecimal.ZERO;
		switch (fund.getInputType()) {
		case ALL_FUND:
			break;
		case GROSS_AMOUNT:
			result = fund.getAmount();
			break;
		case UNITS:
			result = fund.getUnits();
			break;
		case PERCENTAGE:
			result = fund.getPercentage();
			break;
		}
		return result;
	}

	private BigDecimal getfundAmountByCoverage(Map<Integer, BigDecimal> proportionMap, Integer coverage, BigDecimal withdrawalAmount) {
	
		BigDecimal totalUnits = proportionMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal result = proportionMap.get(coverage).multiply(withdrawalAmount).divide(totalUnits, 2, RoundingMode.HALF_UP);
		return result;
	}

}
