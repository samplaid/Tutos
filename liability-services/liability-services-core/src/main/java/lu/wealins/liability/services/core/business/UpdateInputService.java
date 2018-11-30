package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.UpdateInputResponse;

public interface UpdateInputService {

	UpdateInputResponse updateInput(UpdateInputRequest request);

}
