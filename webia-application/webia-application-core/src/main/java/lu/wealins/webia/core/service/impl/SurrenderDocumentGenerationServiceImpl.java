package lu.wealins.webia.core.service.impl;

import java.util.Objects;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.core.service.SurrenderDocumentGenerationService;
import lu.wealins.webia.core.service.helper.SurrenderDocumentServiceGenerator;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service("SurrenderDocumentGenerationService")
@Transactional(readOnly = true)
public class SurrenderDocumentGenerationServiceImpl implements SurrenderDocumentGenerationService {

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(SurrenderDocumentGenerationServiceImpl.class);

	@Autowired
	SurrenderDocumentServiceGenerator surrenderDocumentServiceGenerator;

	@Override
	public EditingRequest generateDocumentSurrender(SecurityContext context, EditingRequest request) {

		if (Objects.isNull(request)) {
			log.error("La requete de generation de l avenant ne peux etre nulle");
			}
		SurrenderDocumentGenerationService service = surrenderDocumentServiceGenerator
				.getServiceInstance(request.getPolicyOrigin());
		if (Objects.isNull(service)) {
			log.error("Le service de generation de l avenant ne peux etre nulle");
			}
		return service.generateDocumentSurrender(context, request);
		}


	}
