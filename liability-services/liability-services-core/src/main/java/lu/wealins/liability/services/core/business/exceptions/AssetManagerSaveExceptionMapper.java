/**
 * 
 */
package lu.wealins.liability.services.core.business.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Intercepts the exception and map it in REST response.
 * 
 * @author oro
 *
 */
public class AssetManagerSaveExceptionMapper implements ExceptionMapper<AssetManagerSaveException> {

	private static final Logger LOG = LoggerFactory.getLogger(AssetManagerSaveException.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(AssetManagerSaveException exception) {
		LOG.error("Asset manager exception: " + exception.getMessage(), exception);

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.TEXT_PLAIN)
				.entity(exception.getMessage())
				.build();
	}

}
