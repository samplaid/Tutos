package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.FundValuationRequest;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.AbstractFundFormDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.webia.core.mapper.FundMapper;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.FundCreationException;

@Service
public class LiabilityFundServiceImpl implements LiabilityFundService {
	private static final String AGENT_ID = "/agent/";
	private static final String EMPTY_STRING = "";
	private static final String FUND_CANNOT_BE_NULL = "Fund cannot be null.";
	private static final String FUND = "FUND";
	private static final String FUND_ID_NOT_GENERATED_BY_SERVER = "Fund id must be generated by the server when creating a new fund.";
	private static final String LIABILITY_FUND = "liability/fund";
	private static final String LIABILITY_UPDATE_FUND = "liability/fund/update";
	private static final String LIABILITY_CREATE_FUND = "liability/fund/create";
	private static final String LIABILITY_VALIDATE_ACTIVE_FUNDS = "liability/fund/validateActiveFunds";
	private static final String WEBIA_SERVICES_NEXT_ID = "webia/sequence/";
	private static final String LIABILITY_INIT_VALORITATION_FUND = "liability/fund/initValorization/";
	private static final String LIABILITY_PERFORM_FUND_VALUATION = "liability/fund/performFundValuation/";
	private static final String WEBIA_FUND_FORM_SAVE = "webia/fundForm/save";
	private static final String LIABILITY_VALIDATE_FUND = "liability/fund/validateFund";


	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private FundMapper fundMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#getFund(java.lang.String)
	 */
	@Override
	public FundDTO getFund(String idFund) {
		if (StringUtils.isEmpty(idFund)) {
			return null;
		}

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.put("id", Arrays.asList(idFund));
		return restClientUtils.get(LIABILITY_FUND, "/one", queryParams, FundDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#getFunds(java.util.Collection)
	 */
	@Override
	public Collection<FundLiteDTO> getFunds(Collection<String> fundIds) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();

		fundIds.forEach(x -> queryParams.add("ids", x));

		return restClientUtils.get(LIABILITY_FUND, EMPTY_STRING, queryParams, new GenericType<Collection<FundLiteDTO>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#getFunds(java.lang.String)
	 */
	@Override
	public Collection<String> getFunds(String agentId) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();

		queryParams.add("agentId", agentId);

		return restClientUtils.get(LIABILITY_FUND, AGENT_ID, queryParams, new GenericType<Collection<String>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#update(lu.wealins.webia.ws.rest.dto.FundDTO)
	 */
	@Override
	public FundDTO update(FundDTO fund) {
		updateFEFundPriceBasis(fund);
		return restClientUtils.post(LIABILITY_UPDATE_FUND, fund, FundDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#create(lu.wealins.webia.ws.rest.dto.FundDTO)
	 */
	@Override
	public FundDTO create(FundDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);

		if (fund.getFdsId() != null) {
			throw new FundCreationException(FUND_ID_NOT_GENERATED_BY_SERVER);
		}

		setupFundId(fund);
		updateFEFundPriceBasis(fund);

		return restClientUtils.post(LIABILITY_CREATE_FUND, fund, FundDTO.class);
	}

	private void setupFundId(FundDTO fund) {
		if (fund.getFdsId() != null) {
			return;
		}

		String fdsId = restClientUtils.get(WEBIA_SERVICES_NEXT_ID, FUND, String.class);
		fund.setFdsId(fdsId);
	}

	@Override
	public void valoriseNewFid(AppFormDTO appForm) {
		Collection<String> fundIds = appForm.getFundForms().stream().map(x -> x.getFundId()).collect(Collectors.toList());
		Collection<FundLiteDTO> funds = getFunds(fundIds);
		Map<String, FundLiteDTO> fundsMap = funds.stream().collect(Collectors.toMap(x -> x.getFdsId(), x -> x));

		// search for all the new FID/FAS and launch the initial valorisation service
		appForm.getFundForms().forEach(ff -> {
			if ("FID".equals(ff.getFundTp()) || "FAS".equals(ff.getFundTp())) {
				FundLiteDTO fund = fundsMap.get(ff.getFundId());

				Boolean addOnValuableAmount = canAddFIDorFASFundValuationAmount(fund.getFdsId(), appForm.getPaymentDt(), (short) 1);

				if (BooleanUtils.isNotTrue(fund.getIsValorized())) {
					FundValuationRequest request = new FundValuationRequest();
					request.setFdsId(ff.getFundId());
					request.setDate(appForm.getPaymentDt());
					restClientUtils.post(LIABILITY_INIT_VALORITATION_FUND, request, Boolean.class);
				} else {
					updateFundValuationAmount(appForm.getPaymentDt(), ff, addOnValuableAmount);
				}

				ff.setAddOnValuableAmount(addOnValuableAmount);
			}
		});

	}

	/**
	 * @param appForm
	 * @param ff
	 * @param addOnValuableAmount
	 * @return
	 */
	private void updateFundValuationAmount(Date valuationDate, FundFormDTO ff, Boolean addOnValuableAmount) {
		if (Objects.isNull(addOnValuableAmount)) {
			throw new IllegalStateException("Fund is valorized therefore the payment date is before the first fund price.");
		}

		if (addOnValuableAmount.booleanValue()) {
			boolean amountAdded = updateValuation(valuationDate, ff);

			if (!amountAdded) {
				ff.setValuationAmt(null);
			}

		} else {
			ff.setValuationAmt(null);
		}

		ff = restClientUtils.post(WEBIA_FUND_FORM_SAVE, ff, FundFormDTO.class);
	}

	private boolean updateValuation(Date valuationDate, AbstractFundFormDTO fundForm) {
		FundValuationRequest request = new FundValuationRequest();
		request.setFdsId(fundForm.getFundId());
		request.setDate(valuationDate);
		request.setPrice(fundForm.getValuationAmt());
		request.setPriceType((short) 4);
		return restClientUtils.post(LIABILITY_PERFORM_FUND_VALUATION, request, Boolean.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#validateActiveFunds(java.util.List)
	 */
	@Override
	public Collection<String> validateActiveFunds(List<String> fundIds) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		fundIds.forEach(x -> params.add("fundIds", x));

		return restClientUtils.get(LIABILITY_VALIDATE_ACTIVE_FUNDS, EMPTY_STRING, params, new GenericType<Collection<String>>() {
			// nothing to do.
		});
	}

	@Override
	public Collection<String> validateFund(FundLiteDTO fund) {
		Collection<String> errors = new ArrayList<>();
		if (Boolean.FALSE.equals(restClientUtils.post(LIABILITY_VALIDATE_FUND, fund, Boolean.class))) {
			errors.add("The fund " + fund.getFdsId() + " is not valid");
		}
		return errors;
	}

	@Override
	public <T extends FundLiteDTO> Collection<T> getFEorFICs(Collection<T> funds) {
		Assert.notNull(funds, "Funds cannot be empty.");
		return funds.stream().filter(x -> isFEorFIC(x)).collect(Collectors.toList());
	}

	@Override
	public boolean isFIDorFAS(FundLiteDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);
		return (FundSubType.FID.name().equals(fund.getFundSubType())
				|| FundSubType.FAS.name().equals(fund.getFundSubType()));
	}

	@Override
	public boolean isFEorFIC(FundLiteDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);
		return (FundSubType.FE.name().equals(fund.getFundSubType())
				|| FundSubType.FIC.name().equals(fund.getFundSubType()));
	}

	@Override
	public boolean isFas(FundLiteDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);
		return FundSubType.FAS.name().equals(fund.getFundSubType());
	}

	@Override
	public boolean isFE(FundLiteDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);
		return fund.getFundSubType() != null && FundSubType.FE.name().equals(fund.getFundSubType().trim());
	}

	private boolean isFIC(FundLiteDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);
		return fund.getFundSubType() != null && FundSubType.FIC.name().equals(fund.getFundSubType().trim());
	}
	
	
	@Override
	public Boolean canAddFIDorFASFundValuationAmount(String fdsId, Date date, short priceType) {
		if (org.apache.commons.lang3.StringUtils.isBlank(fdsId) || Objects.isNull(date)) {
			return false;
		}

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("fdsId", fdsId);
		queryParams.add("valuationDate", new SimpleDateFormat("yyyy-MM-dd").format(date));
		queryParams.add("priceType", priceType);
		return restClientUtils.get(LIABILITY_FUND, "/canAddFIDorFASFundValuationAmount", queryParams, Boolean.class);
	}

	private void updateFEFundPriceBasis(FundDTO fund) {
		if (isFE(fund)) {
			BigDecimal entryFee = fund.getNavEntryFee();
			BigDecimal exitFee = fund.getNavExitFee();
			if ((Objects.nonNull(exitFee) && (exitFee.compareTo(new BigDecimal(0)) > 0))
					|| (Objects.nonNull(entryFee) && (entryFee.compareTo(new BigDecimal(0)) > 0))) {
				fund.setPriceBasis(4);
			}
			else {
				fund.setPriceBasis(1);
			}
		}
	}

	public void removeEmptyFunds(Collection<FundFormDTO> fundForms) {
		Collection<FundFormDTO> filtered = fundForms.stream().filter(fundForm -> !this.isNewEmptyFund(fundForm)).collect(Collectors.toList());
		fundForms.clear();
		fundForms.addAll(filtered);
	}

	/**
	 * Check if at least one field has been filled
	 * 
	 * @param regFund the fund object
	 */
	private boolean isNewEmptyFund(FundFormDTO fundForm) {

		boolean isFid = FundSubType.FID.name().equals(fundForm.getFundTp());
		boolean isFas = FundSubType.FAS.name().equals(fundForm.getFundTp());
		FundLiteDTO fund = fundForm.getFund();

		return (fund == null || (isFid && isNewEmptyFid(fund) || (isFas && isNewEmptyFas(fund))));
	}

	/**
	 * Check if at least one field has been filled
	 * 
	 * @param fund the fund object
	 */
	private boolean isNewEmptyFid(FundLiteDTO fund) {

		return (fund.getAssetManager() == null &&
				fund.getAssetManagerFee() == null &&
				fund.getFinFeesFlatAmount() == null &&
				fund.getDepositBank() == null &&
				fund.getBankDepositFee() == null &&
				fund.getDepositBankFlatFee() == null);
	}

	/**
	 * Check if at least one field has been filled in case of a new fid was created.
	 * 
	 * @param fund the fund object
	 */
	private boolean isNewEmptyFas(FundLiteDTO fund) {
		return (fund.getAssetManagerFee() == null &&
				fund.getFinFeesFlatAmount() == null &&
				fund.getFinancialAdvisor() == null &&
				fund.getFinAdvisorFee() == null &&
				fund.getDepositBank() == null &&
				fund.getBankDepositFee() == null &&
				fund.getDepositBankFlatFee() == null);
	}

	@Override
	public void initFundsCurrency(Collection<FundFormDTO> fundForms, String contractCurrency) {
		if (contractCurrency != null) {
			fundForms.stream().forEach(fund -> {
				if (fund.getFund().getCurrency() == null) {
					fund.getFund().setCurrency(contractCurrency);
				}
			});
		}
	}

	@Override
	public boolean isExternal(FundLiteDTO fund) {
		return isFIC(fund) || isFE(fund);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityFundService#addFundOnFundForm(lu.wealins.common.dto.webia.services.AppFormDTO)
	 */
	@Override
	public void addFundOnFundForm(Collection<FundFormDTO> fundForms, String contractCurrency) {
		for (FundFormDTO fundForm : fundForms) {
			FundLiteDTO fund = fundForm.getFund();
			String fundId = fundForm.getFundId();

			if (fund != null) {
				String fdsId = fund.getFdsId();
				if (fundId != null && fdsId != null && !fundId.equals(fdsId)) {
					throw new IllegalStateException("Fund ids are not the same!");
				}
				if (StringUtils.isEmpty(fund.getCurrency())) {
					fund.setCurrency(contractCurrency);
				}
				if (StringUtils.isEmpty(fdsId)) {
					fund = create(fundMapper.asFundDTO(fund));
				}
				fundForm.setFund(fund);
				if (fund != null) {
					fundForm.setFundId(fund.getFdsId());
				}
			} else if (fundId != null) {
				fund = getFund(fundId);
				fundForm.setFund(fund);
			}
		}
	}

	@Override
	public Collection<FundLiteDTO> getFunds(PolicyValuationDTO policyValuation) {
		return getFunds(getFundIds(policyValuation));
	}

	@Override
	public Collection<String> getFundIds(PolicyValuationDTO policyValuation) {
		if (policyValuation == null) {
			return new ArrayList<>();
		}

		List<PolicyValuationHoldingDTO> holdings = policyValuation.getHoldings();
		if (CollectionUtils.isEmpty(holdings)) {
			return new ArrayList<>();
		}

		return holdings.stream().map(x -> x.getFundId()).collect(Collectors.toList());
	}

	@Override
	public Collection<FundLiteDTO> getInvestedFunds(String polId) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("policyId", polId);

		return restClientUtils.get(LIABILITY_FUND, "/investedFunds", queryParams, new GenericType<Collection<FundLiteDTO>>() {
		});
	}

	@Override
	public void updateFidFasValuations(Collection<FundTransactionFormDTO> fundForms, Date valuationDate) {
		if (CollectionUtils.isNotEmpty(fundForms)) {
			fundForms.stream().filter(fundForm -> isFIDorFAS(fundForm.getFund())).forEach(fundForm -> updateValuation(valuationDate, fundForm));
		}
	}
}
