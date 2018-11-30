package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.FormDataDTO;

public interface WebiaFormDataService {

	<T extends FormDataDTO> T initFormData(Integer workflowItemId, Integer workflowItemTypeId, Class<T> type);

}
