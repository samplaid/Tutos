package lu.wealins.liability.services.core.business.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Provider
@Component
public class WebServiceInvocationExceptionMapper implements ExceptionMapper<WebServiceInvocationException> {

	private final Logger log = LoggerFactory.getLogger(PolicyNotFoundExceptionMapper.class);

	@Override
	public Response toResponse(WebServiceInvocationException exception) {

		log.error("Web Service Invocation exception: " + exception.getMessage(), exception);

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}
