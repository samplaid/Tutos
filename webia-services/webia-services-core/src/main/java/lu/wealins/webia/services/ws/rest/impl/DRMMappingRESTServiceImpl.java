package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.DRMMappingDTO;
import lu.wealins.webia.services.core.service.DRMMappingService;
import lu.wealins.webia.services.ws.rest.DRMMappingRESTService;

@Component
public class DRMMappingRESTServiceImpl implements DRMMappingRESTService {

	@Autowired
	private DRMMappingService drmMappingService;

	@Override
	public List<DRMMappingDTO> getDRMMapping(SecurityContext context, String type) {
		return drmMappingService.findByType(type);
	}

}
