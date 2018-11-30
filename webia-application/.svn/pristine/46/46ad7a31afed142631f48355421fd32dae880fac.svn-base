package lu.wealins.webia.ws.rest.impl.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Provider
@Component
public class EntrepriseServiceBusExceptionMapper implements ExceptionMapper<EntrepriseServiceBusException> {

	private final Logger log = LoggerFactory.getLogger(EntrepriseServiceBusExceptionMapper.class);

	@Override
	public Response toResponse(EntrepriseServiceBusException exception) {

		log.error("Entreprise Service Bus exception: " + exception.getMessage());

		return Response
				.status(exception.getStatus())
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}
