package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.webia.services.core.service.SapAccountingService;
import lu.wealins.webia.services.ws.rest.SapAccountingRESTService;

@Component
public class SapAccountingRESTServiceImpl implements SapAccountingRESTService {

	@Autowired
	private SapAccountingService sapAccountingService;

	@Override
	public List<SapAccountingDTO> getSapAccountingByOriginId(SecurityContext context, Long originId, String fundId) {
		return sapAccountingService.findByOriginIdForEncashments(originId, fundId);
	}

}
