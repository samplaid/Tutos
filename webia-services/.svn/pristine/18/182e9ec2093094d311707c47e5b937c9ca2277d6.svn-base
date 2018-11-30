package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.ReportingComEntity;
import lu.wealins.common.dto.webia.services.ReportingComDTO;

@Mapper(componentModel = "spring")
public interface ReportingComMapper {

	ReportingComEntity asReportingComEntity(ReportingComDTO in);

	ReportingComDTO asReportingComDTO(ReportingComEntity in);


	Collection<ReportingComDTO> asReportingComDTOs(Collection<ReportingComEntity> in);
	
	Collection<ReportingComEntity> asReportingComEntities(Collection<ReportingComDTO> in);

}
