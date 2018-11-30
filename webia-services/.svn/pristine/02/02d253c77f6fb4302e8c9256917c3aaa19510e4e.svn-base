package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.webia.services.core.persistence.entity.CheckWorkflowEntity;
import lu.wealins.webia.services.core.persistence.repository.ScoreBCFTRepository;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;

@Mapper(componentModel = "spring")
public abstract class CheckWorkflowMapper {

	public abstract CheckWorkflowDTO asCheckWorkflowDTO(CheckWorkflowEntity in);

	@Autowired
	private ScoreBCFTRepository repository;

	 @Autowired
	 private ScoreBCFTMapper scoreBCFTMapper;

	@AfterMapping
	protected CheckWorkflowDTO asCheckWorkflowDTO(CheckWorkflowEntity in, @MappingTarget CheckWorkflowDTO target) {
		 if (in.getCheckCode() != null) {
			target.setScoreBCFTs(scoreBCFTMapper.asScoreBCFTDTOs(repository.findByCheckCode(in.getCheckCode())));
		 }

		return target;
	}

	public abstract CheckWorkflowEntity asCheckWorkflowEntity(CheckWorkflowDTO in);

	public abstract Collection<CheckWorkflowDTO> asCheckWorkflowDTOs(Collection<CheckWorkflowEntity> in);

}
