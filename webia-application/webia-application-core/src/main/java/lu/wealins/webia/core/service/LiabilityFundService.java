package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;

public interface LiabilityFundService {

	/**
	 * Get the fund according its id.
	 * 
	 * @param fundId The fund id.
	 * @exception Return a <code>ObjectNotFoundException</code> if the fund does not exist.
	 * @return The fund.
	 */
	FundDTO getFund(String idFund);

	Collection<FundLiteDTO> getFunds(Collection<String> fundIds);

	Collection<String> getFunds(String agentId);

	/**
	 * Update the given fund.
	 * 
	 * @param fund The fund.
	 * @return The updated fund.
	 */
	FundDTO update(FundDTO fund);

	/**
	 * Create a new fund.
	 * 
	 * @param fund The fund.
	 * @return The new fund.
	 */
	FundDTO create(FundDTO fund);

	/**
	 * Create the initial unity value of the new Fid
	 * 
	 * @param appForm The appForm from analysis step
	 */
	void valoriseNewFid(AppFormDTO appForm);

	/**
	 * Validate if the funds are active otherwise it returns the error messages with the non-active funds.
	 * 
	 * @param fundIds The fund ids.
	 * @return list of errors.
	 */
	Collection<String> validateActiveFunds(List<String> fundIds);

	/**
	 * Validate the fund provided
	 * 
	 * @param fundForm the fund to validate
	 * @return list of errors
	 */
	Collection<String> validateFund(FundLiteDTO fundForm);

	<T extends FundLiteDTO> Collection<T> getFEorFICs(Collection<T> funds);

	/**
	 * Check if the fund is a FID or a FAS.
	 * 
	 * @param fund The fund.
	 * @return True, if successful.
	 */
	boolean isFIDorFAS(FundLiteDTO fund);

	boolean isFEorFIC(FundLiteDTO fund);

	/**
	 * check if fund is type of FAS
	 * 
	 * @param fund the fund
	 * @return true or false
	 */
	boolean isFas(FundLiteDTO fund);

	/**
	 * check if fund is FE
	 * 
	 * @param fund
	 *            the fund
	 * @return true or false
	 */
	boolean isFE(FundLiteDTO fund);

	boolean isExternal(FundLiteDTO fund);

	/**
	 * Add fund on fund forms
	 * 
	 * @param appForm The application form.
	 */
	void addFundOnFundForm(Collection<FundFormDTO> fundForms, String contractCurrency);
	
	/**
	 * Returns : <br>
	 * <b>Null</b>: if the one of the parameter is null or empty or a fund price is not found. <br>
	 * <b>false</b>: if a fund price is found but its valuation date is equal to the date passed in parameter. <br>
	 * <b>true</b>: otherwise.
	 * 
	 * @param fdsId the fund id
	 * @param date the payment date
	 * @param priceType the type of price to be added.
	 * @return a boolean value or null.
	 */
	Boolean canAddFIDorFASFundValuationAmount(String fdsId, Date date, short priceType);

	void removeEmptyFunds(Collection<FundFormDTO> fundForms);

	void initFundsCurrency(Collection<FundFormDTO> fundForms, String contractCurrency);

	Collection<FundLiteDTO> getFunds(PolicyValuationDTO policyValuation);

	Collection<String> getFundIds(PolicyValuationDTO policyValuation);

	Collection<FundLiteDTO> getInvestedFunds(String polId);

	void updateFidFasValuations(Collection<FundTransactionFormDTO> fundForms, Date valuationDate);
}
