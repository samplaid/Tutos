package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.CodeLabelDTO;

public interface WebiaCodeLabelService {

	/**
	 * Get code labels according to the type code.
	 * 
	 * @param typeCd The type code.
	 * @return The code labels.
	 */
	Collection<CodeLabelDTO> getCodeLabels(String typeCd);

	/**
	 * Get code label according to the type code and the code.
	 * 
	 * @param typeCd The type code.
	 * @param code The code.
	 * @return The code label.
	 */
	CodeLabelDTO getCodeLabel(String typeCd, String code);

	CodeLabelDTO getDeathCoverageCodeLabel(String code);

	/**
	 * Retrieve the death coverage code label by the product line id and the alpha value of the death coverage.
	 * 
	 * @param productLineId the product line id
	 * @param aplhaValue the death coverage number.
	 * @return Null if the object is not found.
	 */
	CodeLabelDTO getDeathCoverageCodeLabel(String productLineId, String aplhaValue);
}
