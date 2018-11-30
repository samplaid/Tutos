package lu.wealins.webia.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lu.wealins.webia.services.core.persistence.entity.StepEntity;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.LabelDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;

@Mapper(componentModel = "spring", uses = { CheckStepMapper.class })
public abstract class StepMapper {

	public abstract StepEntity asStepEntity(StepDTO in);

	public abstract StepDTO asStepDTO(StepEntity in);

	public abstract StepLightDTO asStepLightDTO(StepEntity in);

	public abstract Collection<StepDTO> asStepDTOs(Collection<StepEntity> in);

	@AfterMapping
	protected StepDTO afterMapping(StepEntity in, @MappingTarget StepDTO target) {

		return sortCheckSteps(target);
	}

	private StepDTO sortCheckSteps(StepDTO target) {
		List<CheckStepDTO> checkSteps = new ArrayList<>(target.getCheckSteps());
		checkSteps.sort((c1, c2) -> {
			LabelDTO label1 = c1.getLabel();
			LabelDTO label2 = c2.getLabel();

			if (label1 == null && label2 == null) {
				return 0;
			}

			if (label1 == null) {
				return -1;
			}

			if (label2 == null) {
				return 1;
			}

			int comparaison = label1.getLabelOrder().compareTo(label2.getLabelOrder());

			if (comparaison == 0) {
				return c1.getCheckOrder().compareTo(c2.getCheckOrder());
			}
			return comparaison;
		});

		target.setCheckSteps(checkSteps);

		return target;
	}

}
