package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.ControlDTO;

public interface ControlService {

	/**
	 * Get control according its id.
	 * 
	 * @param ctlId The control id.
	 * @return The control.
	 */
	ControlDTO getControl(String ctlId);
}
