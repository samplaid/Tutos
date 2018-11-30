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
public class PolicyNotFoundExceptionMapper implements ExceptionMapper<PolicyNotFoundException> {

	private final Logger log = LoggerFactory.getLogger(PolicyNotFoundExceptionMapper.class);

	@Override
	public Response toResponse(PolicyNotFoundException exception) {

		log.error("Policy Not Found exception: " + exception.getMessage(), exception);

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}