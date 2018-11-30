package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.ReportExceptionDTO;
import lu.wealins.webia.ws.rest.impl.exception.ReportException;

@Mapper(componentModel = "spring")
public interface ReportExceptionMapper {

	ReportExceptionDTO asReportExceptionDTO(ReportException in);

}
 