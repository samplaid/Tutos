package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.SapMappingService;
import lu.wealins.webia.services.ws.rest.SapMappingRESTService;
import lu.wealins.common.dto.webia.services.SapMappingDTO;

@Component
public class SapMappingRESTServiceImpl implements SapMappingRESTService {

	@Autowired
	private SapMappingService sapMappingService;

	@Override
	public List<SapMappingDTO> getSapMapping(SecurityContext context, String type) {
		return sapMappingService.findByType(type);
	}

}
