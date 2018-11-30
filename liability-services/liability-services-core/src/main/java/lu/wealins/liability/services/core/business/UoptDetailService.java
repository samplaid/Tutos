package lu.wealins.liability.services.core.business;

import java.util.List;

import lu.wealins.common.dto.liability.services.UoptDetailDTO;
import lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity;

public interface UoptDetailService {

	UoptDetailDTO getUoptDetail(String uddId);

	UoptDetailEntity getUoptDetailEntity(String uddId);

	/**
	 * Get the circular letters constant (definitionUdfId = 29, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getCircularLetters();

	/**
	 * Get the fund classifications constant (definitionUdfId = 56, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getFundClassifications();

	/**
	 * Get the risk classes constant (definitionUdfId = 57, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getRiskClasses();

	/**
	 * Get the risk profiles constant (definitionUdfId = 37, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getRiskProfiles();

	/**
	 * Get the risk currencies constant (definitionUdfId = 38, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getRiskCurrencies();

	/**
	 * Get the investment categories constant (definitionUdfId = 3, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getInvestmentCategories();

	/**
	 * Get the alternative funds constant (definitionUdfId = 41, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getAlternativeFunds();

	/**
	 * Get the type of POAs constant (definitionUdfId = 44, active = 1)
	 * 
	 * @return A list of circular letters constant or an empty list.
	 */
	List<UoptDetailDTO> getTypePOAs();

	/**
	 * Get the Sending rules (client policy profile) constant (definitionUdfId = 9, active = 1)
	 * 
	 * @return A list of sending rules constant or an empty list.
	 */
	List<UoptDetailDTO> getSendingRules();

	/**
	 * Return a list of valid options of the specified type.
	 * 
	 * @param title
	 * @return
	 */
	List<UoptDetailDTO> getOptions(UoptDefinitions title);

	/**
	 * Get the type of agent contacts (definitionUdfId = 62, active = 1)
	 * 
	 * @return A list of type of agent contact constants or an empty list.
	 */
	List<UoptDetailDTO> getTypeOfAgentContact();

	/**
	 * Get uopt details for a keyvalue
	 * 
	 * @param keyValue
	 * @return
	 */
	UoptDetailDTO getUoptDetailEntityForKeyValue(String keyValue);
}
