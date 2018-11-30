package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.webia.services.core.persistence.entity.RejectDataEntity;
import lu.wealins.webia.services.core.service.StepService;
import lu.wealins.common.dto.webia.services.RejectDataDTO;

@Mapper(componentModel = "spring")
public abstract class RejectDataMapper {

	@Autowired
	private StepService stepService;

	public abstract RejectDataEntity asRejectDataEntity(RejectDataDTO in);

	@AfterMapping
	protected RejectDataEntity afterMapping(RejectDataDTO in, @MappingTarget RejectDataEntity target) {
		if (in.getStep() != null) {
			target.setStepId(in.getStep().getStepId());
		}

		return target;
	}

	public abstract RejectDataDTO asRejectDataDTO(RejectDataEntity in);

	public abstract Collection<RejectDataDTO> asRejectDataDTOs(Collection<RejectDataEntity> in);

	@AfterMapping
	protected RejectDataDTO afterMapping(RejectDataEntity in, @MappingTarget RejectDataDTO target) {
		if (in.getStepId() != null) {

		}
		target.setStep(stepService.getStep(in.getStepId()));
		return target;
	}

}