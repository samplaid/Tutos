package lu.wealins.liability.services.ws.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
public class WssUpdateClientExceptionMapper implements ExceptionMapper<WssUpdateClientException> {

	@Override
	public Response toResponse(WssUpdateClientException e) {

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new RESTServiceExceptionResponse("WssUpdateClientException", e.getMessage()))
				.build();
	}

}
