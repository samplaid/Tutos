package lu.wealins.webia.ws.rest.impl.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.ReportExceptionDTO;


@Provider
@Component
public class ReportExceptionMapper implements ExceptionMapper<ReportException> {

	private final Logger log = LoggerFactory.getLogger(ReportExceptionMapper.class);

	@Autowired
	private lu.wealins.webia.core.mapper.ReportExceptionMapper reportExceptionMapper;

	@Override
	public Response toResponse(ReportException exception) {

		ReportExceptionDTO reportException = reportExceptionMapper.asReportExceptionDTO(exception);

		log.error("Exception: " + reportException.getErrors() + ", " + reportException.getWarns() + ".", exception);

		return Response
				.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(reportException)
				.build();
	}

}
