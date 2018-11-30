package lu.wealins.webia.core.service.helper;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.webia.core.service.SurrenderDocumentGenerationService;
import lu.wealins.webia.core.service.impl.SurrenderDocumentGenerationServiceImpl;
import lu.wealins.webia.ws.rest.request.PolicyOrigin;

@Component
public class SurrenderDocumentServiceGenerator {
	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(SurrenderDocumentGenerationServiceImpl.class);

	@Autowired
	@Qualifier("SurrrenderDaliDocumentGenerationService")
	SurrenderDocumentGenerationService surrrenderDaliDocumentGenerationService;

	@Autowired
	@Qualifier("SurrrenderLissiaDocumentGenerationService")
	SurrenderDocumentGenerationService surrrenderLissiaDocumentGenerationService;

	public SurrenderDocumentGenerationService getServiceInstance(PolicyOrigin policyOrigin) {
		SurrenderDocumentGenerationService surrenderService = surrrenderLissiaDocumentGenerationService;
		if (Objects.isNull(policyOrigin)) {
			log.error("Le type de la requete de generation de l avenant ne peut etre null");
			return null;
		}

		switch (policyOrigin) {
		case DALI:
			surrenderService = surrrenderDaliDocumentGenerationService;
			break;
		case LISSIA:
			surrenderService = surrrenderLissiaDocumentGenerationService;
			break;
		default:
			log.error("Le type de la requete de generation de l avenant est inconnu");
			break;
		}

		return surrenderService;
	}

}
