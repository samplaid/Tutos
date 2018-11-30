
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
public class FundCreationExceptionMapper implements ExceptionMapper<FundCreationException> {

	private final Logger log = LoggerFactory.getLogger(FundCreationExceptionMapper.class);

	@Override
	public Response toResponse(FundCreationException exception) {

		log.error("Fund Creation exception: " + exception.getMessage());

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}
