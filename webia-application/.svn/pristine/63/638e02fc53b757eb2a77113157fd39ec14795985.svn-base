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
public class SequenceExceptionMapper implements ExceptionMapper<SequenceException> {

	private final Logger log = LoggerFactory.getLogger(SetupExceptionExceptionMapper.class);

	@Override
	public Response toResponse(SequenceException exception) {

		log.error("Sequence generation error: " + exception.getMessage());

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}
