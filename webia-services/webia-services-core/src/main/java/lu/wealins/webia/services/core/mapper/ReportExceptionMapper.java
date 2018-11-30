package lu.wealins.webia.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.ReportExceptionDTO;
import lu.wealins.webia.services.core.exceptions.ReportException;

@Mapper(componentModel = "spring")
public interface ReportExceptionMapper {

	ReportExceptionDTO asReportExceptionDTO(ReportException in);

}
