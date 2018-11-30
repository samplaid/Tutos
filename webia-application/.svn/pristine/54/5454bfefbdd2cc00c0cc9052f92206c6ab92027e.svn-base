package lu.wealins.webia.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ControlDTO;
import lu.wealins.webia.core.service.LiabilityControlService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityControlServiceImpl implements LiabilityControlService {

	private static final String SYSTEM_DATE = "SYSTEM_DATE";

	private static final String LIABILITY_LOAD_CONTROL = "liability/control/";

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityControlService#getControl(java.lang.String)
	 */
	@Override
	public ControlDTO getControl(String ctlId) {
		return restClientUtils.get(LIABILITY_LOAD_CONTROL, ctlId, ControlDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityControlService#getSystemDate()
	 */
	@Override
	public Date getSystemDate() {
		ControlDTO control = getControl(SYSTEM_DATE);
		Assert.notNull(control, "Cannot retrieve the system date.");

		return control.getDateValue();
	}

}
