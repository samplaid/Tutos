package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.UpdateInputResponse;
import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface LiabilityUpdateInputService {

	UpdateInputResponse updateInput(AppFormDTO appForm, String usrId);
}
