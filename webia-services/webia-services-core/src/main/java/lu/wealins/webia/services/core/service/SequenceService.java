package lu.wealins.webia.services.core.service;

import org.springframework.stereotype.Component;

@Component
public interface SequenceService {

	String generateNextId(String target);
}
