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
public class PolicyExceptionMapper implements ExceptionMapper<PolicyException> {

	private final Logger log = LoggerFactory.getLogger(PolicyExceptionMapper.class);

	@Override
	public Response toResponse(PolicyException exception) {

		log.error("Policy exception: " + exception.getMessage());

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}