package lu.wealins.webia.ws.rest.impl.exception;

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
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

	private final Logger log = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

	@Override
	public Response toResponse(RuntimeException exception) {

		log.error("Unknown exception: " + exception.getMessage(), exception);

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getClass().getName() + " in webia-application : " + (exception.getMessage() == null ? "no message." : exception.getMessage()))
				.build();
	}

}
