package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.FundSearcherRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.business.exceptions.FundCreationException;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;

/**
 * The Fund service is responsible to manipulate the fund objects
 * 
 */
public interface FundService {

	/**
	 * Get the fund according its id.
	 * 
	 * @param fundId The fund id.
	 * @exception Return a <code>ObjectNotFoundException</code> if the fund does not exist.
	 * @return The fund.
	 */
	FundDTO getFund(String fundId);

	FundEntity getFundEntity(String fundId);

	FundLiteDTO getFundLite(String fundId);

	Collection<FundLiteDTO> getFunds(Collection<String> fundIds);

	Collection<String> getFundIds(String agentId);

	/**
	 * Update an existing fund with the WSSUPDFDS soap service and the user name.
	 * 
	 * Fund to be updated must exist in the database, otherwise the method will throw an exception. All the values of the fund will be replaced with the data in the DTO parameter.
	 * 
	 * @param fund The fund.
	 * @return The updated fund.
	 */
	FundDTO update(FundDTO fund);

	/**
	 * Create a new fund with the WSSUPDFDS soap service and the user name.
	 * 
	 * @param fund The fund.
	 * @return The created fund.
	 */
	FundDTO create(FundDTO fund);

	/**
	 * Search the funds in the given type and which have the search string in the name or ISIN code.
	 * 
	 * The page parameter is zero indexed, the first page has the number 0.
	 * 
	 * @param s
	 * @param type
	 * @param brokerId optional filter on brokerId and null for type=FE
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	SearchResult<FundLiteDTO> search(String s, String type, String brokerId, boolean onlyBroker, int pageNum, int pageSize);

	/**
	 * Search fund.
	 * 
	 * @param fundSearcherRequest The search request.
	 * @return The search result.
	 */
	SearchResult<FundLiteDTO> search(FundSearcherRequest fundSearcherRequest);

	/**
	 * extract fund for FID
	 * 
	 * @return List of FID
	 */
	List<FundEntity> extractFidFund();

	/**
	 * Init the fund price unity to 100
	 * 
	 * @param fundId The fund Id.
	 * @param dateVNI the premium's date of payment
	 * @return true if ok.
	 */
	boolean initValorization(String fundId, Date dateVNI);

	/**
	 * Update Fid exported_fund flag
	 * 
	 * @param fund ids
	 * @param userName The user name.
	 * @return number of records updated
	 * @throws Exception
	 */
	Long updatetFidFundFlag(List<String> fids) throws Exception;

	/**
	 * check if fund is type of FID or FAS
	 * 
	 * @param fund the fund
	 * @return true or false
	 */
	boolean isFIDorFAS(FundDTO fund);

	/**
	 * check if fund is type of FE or FIC
	 * 
	 * @param fund the fund
	 * @return true or false
	 */
	boolean isFeOrFic(FundDTO fund);

	/**
	 * check if fund is type of FE
	 * 
	 * @param fund the fund
	 * @return true or false
	 */
	boolean isFe(FundDTO fund);
	
	/**
	 * heck if fund is type of FE
	 * 
	 * @param fdsId
	 *            fund type as string
	 * @return
	 */
	boolean isFe(String fdsId);

	/**
	 * check if fund is type of FID
	 * 
	 * @param fund
	 *            the fund
	 * @return true or false
	 */
	boolean isFid(FundDTO fund);

	/**
	 * check if fund is type of FAS
	 * 
	 * @param fund the fund
	 * @return true or false
	 */
	boolean isFas(FundDTO fund);

	/**
	 * check if fund is type of FIC
	 * 
	 * @param fund the fund
	 * @return true or false
	 */
	boolean isFic(FundDTO fund);

	/**
	 * check if fund is valid
	 * 
	 * @param fund the fund
	 * @return true or throw exception
	 * @throws FundCreationException
	 */
	boolean validateFund(FundDTO fund);

	boolean validateFunds(List<String> fdsIds);

	/**
	 * Validate if the funds are active otherwise it returns the error messages with the non-active funds.
	 * 
	 * @param fundIds The fund ids.
	 * @return The list of errors.
	 */
	List<String> validateActiveFunds(List<String> fundIds);

	boolean performFundValuation(String fundId, Date dateVNI, BigDecimal price, short priceType);
	
	
	/**
	 * Specified if valuationAmount can be added.
	 * @param fdsId the fund id
	 * @param date the payment date
	 * @param priceType the type of price to be added.
	 * @return a boolean value .
	 */
	Boolean canAddFIDorFASValuationAmount(String fdsId, Date date, int priceType);
	
	Collection<FundLiteDTO> getInvestedFunds(String polId);

}