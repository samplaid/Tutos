package lu.wealins.webia.core.service;

import java.util.Date;

import lu.wealins.common.dto.liability.services.ControlDTO;

public interface LiabilityControlService {

	/**
	 * Get control according its id.
	 * 
	 * @param ctlId The control id.
	 * @return The control.
	 */
	ControlDTO getControl(String ctlId);

	/**
	 * Get the system date.
	 * 
	 * @return The system date.
	 */
	Date getSystemDate();
}
