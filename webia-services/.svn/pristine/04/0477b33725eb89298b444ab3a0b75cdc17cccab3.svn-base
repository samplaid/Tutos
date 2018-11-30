package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.mapper.RejectDataMapper;
import lu.wealins.webia.services.core.persistence.entity.RejectDataEntity;
import lu.wealins.webia.services.core.persistence.repository.RejectDataRepository;
import lu.wealins.webia.services.core.service.RejectDataService;
import lu.wealins.common.dto.webia.services.RejectDataDTO;

@Service
public class RejectDataServiceImpl implements RejectDataService {

	@Autowired
	private RejectDataRepository rejectDataRepository;

	@Autowired
	private RejectDataMapper rejectDataMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.RejectDataService#getRejectData(java.lang.Integer)
	 */
	@Override
	public Collection<RejectDataDTO> getRejectData(Integer workflowItemId) {
		Collection<RejectDataEntity> rejectData = rejectDataRepository.findByWorkflowItemIdOrderByCreationDtDesc(workflowItemId);
		return rejectDataMapper.asRejectDataDTOs(rejectData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.RejectDataService#getRejectData(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Collection<RejectDataDTO> getRejectData(Integer workflowItemId, Integer stepId) {
		Collection<RejectDataEntity> rejectData = rejectDataRepository.findByWorkflowItemIdAndStepIdOrderByCreationDtDesc(workflowItemId, stepId);
		return rejectDataMapper.asRejectDataDTOs(rejectData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.RejectDataService#save(lu.wealins.common.dto.webia.services.RejectDataDTO)
	 */
	@Override
	public RejectDataDTO save(RejectDataDTO rejectDataDTO) {
		Assert.notNull(rejectDataDTO);
		Assert.notNull(rejectDataDTO.getStep());
		Assert.notNull(rejectDataDTO.getStep().getStepId());
		Assert.notNull(rejectDataDTO.getWorkflowItemId());

		RejectDataEntity rejectDataEntity = rejectDataMapper.asRejectDataEntity(rejectDataDTO);
		rejectDataEntity = rejectDataRepository.save(rejectDataEntity);

		return rejectDataMapper.asRejectDataDTO(rejectDataEntity);
	}

}
