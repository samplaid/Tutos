package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.ReportExceptionDTO;
import lu.wealins.liability.services.core.business.exceptions.ReportException;

@Mapper(componentModel = "spring")
public interface ReportExceptionMapper {

	ReportExceptionDTO asReportExceptionDTO(ReportException in);

}
