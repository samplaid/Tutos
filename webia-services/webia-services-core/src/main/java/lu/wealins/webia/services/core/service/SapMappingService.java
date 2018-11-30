package lu.wealins.webia.services.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.SapMappingDTO;

@Component
public interface SapMappingService {

	List<SapMappingDTO> findByType(String type);
}
