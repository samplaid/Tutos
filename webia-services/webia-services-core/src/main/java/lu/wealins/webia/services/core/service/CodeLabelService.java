package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.CodeLabelDTO;

public interface CodeLabelService {

	/**
	 * Get code labels according to the type code.
	 * 
	 * @param typeCd The type code.
	 * @return The code labels.
	 */
	Collection<CodeLabelDTO> getCodeLabels(String typeCd);

	/**
	 * Get code label according its type code and its code.
	 * 
	 * @param typeCd The type code.
	 * @param code The code.
	 * @return The code label.
	 */
	CodeLabelDTO getCodeLabel(String typeCd, String code);
}
