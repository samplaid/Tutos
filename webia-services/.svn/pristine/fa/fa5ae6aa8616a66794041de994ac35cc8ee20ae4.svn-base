package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.CheckType;
import lu.wealins.webia.services.core.mapper.CheckStepMapper;
import lu.wealins.webia.services.core.persistence.entity.CheckStepEntity;
import lu.wealins.webia.services.core.persistence.repository.CheckStepRepository;
import lu.wealins.webia.services.core.service.CheckDataService;
import lu.wealins.webia.services.core.service.CheckStepService;

@Service
public class CheckStepServiceImpl implements CheckStepService {

	/**
	 * 
	 */
	private static final String GRP1 = "GRP1";
	private static final String CHECK_CODE_CANNOT_BE_NULL = "CheckCode cannot be null";
	private static final String STEP_CANNOT_BE_NULL = "Step cannot be null";

	@Autowired
	private CheckStepRepository checkStepRepository;

	@Autowired
	private CheckStepMapper checkStepMapper;

	@Autowired
	private CheckDataService checkDataService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<CheckStepDTO> getCommentsHistory(Integer workflowItemId, Integer workflowItemTypeId) {
		List<CheckStepEntity> checkSteps = checkStepRepository.findByCheckCodesAndWorkflowItemTypeId(Arrays.asList(GRP1), workflowItemTypeId);
		
		List<CheckStepDTO> checkStepDTOs = new ArrayList<>(checkStepMapper.asCheckStepDTOs(checkSteps));
		addCheckDataToCheckStep(workflowItemId, checkStepDTOs);

		// remove empty comments and order by creation date
		checkStepDTOs.removeIf(x -> (x.getCheck() != null && x.getCheck().getCheckData() == null || !x.getCheck().getCheckData().hasValue()) || !x.getIsUpdatable());
		Collections.sort(checkStepDTOs, new Comparator<CheckStepDTO>() {
			@Override
			public int compare(CheckStepDTO o1, CheckStepDTO o2) {
				return o1.getCheck().getCheckData().getCreationDt().compareTo(o2.getCheck().getCheckData().getCreationDt());
			}
		});

		return checkStepDTOs;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckStepService#getCheckStep(lu.wealins.common.dto.webia.services.StepDTO, java.lang.String)
	 */
	@Override
	public CheckStepDTO getCheckStep(StepDTO step, String checkCode) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		Assert.notNull(checkCode, CHECK_CODE_CANNOT_BE_NULL);

		return step.getCheckSteps().stream().filter(x -> x.getCheck() != null && checkCode.equals(x.getCheck().getCheckCode())).findFirst().orElse(null);
	}
	
	@Override
	public void addCheckDataAtCheckStepLevel(Integer workflowItemId, StepDTO step) {
		addCheckDataToCheckStep(workflowItemId, step.getCheckSteps());
		checkDataService.updateContextualRules(step);
	}
	
	private void addCheckDataToCheckStep(Integer workflowItemId, Collection<CheckStepDTO> checkStep){
		for (CheckStepDTO checkStepDTO : checkStep) {
			CheckWorkflowDTO check = checkStepDTO.getCheck();
			if (check != null) {
				CheckDataDTO checkData = checkDataService.getCheckData(workflowItemId, check.getCheckId());
				if (checkData == null && StringUtils.hasText(check.getDefaultValue())) {
					CheckDataDTO defaultCheckData = checkDataService.createCheckData(check.getCheckId(), workflowItemId);
					if (CheckType.YES_NO_NA.getType().equals(check.getCheckType()) || CheckType.YES_NO.getType().equals(check.getCheckType())) {
						defaultCheckData.setDataValueYesNoNa(check.getDefaultValue().toUpperCase().trim());
					} else if (CheckType.NUMBER.getType().equals(check.getCheckType())) {
						defaultCheckData.setDataValueNumber(new BigDecimal(check.getDefaultValue().trim()));
					} else if (CheckType.AMOUNT.getType().equals(check.getCheckType())) {
						defaultCheckData.setDataValueAmount(new BigDecimal(check.getDefaultValue().trim()));
					} else if (CheckType.TEXT.getType().equals(check.getCheckType())) {
						defaultCheckData.setDataValueText(check.getDefaultValue().trim());
					}
					check.setCheckData(defaultCheckData);
				} else {
					check.setCheckData(checkData);
				}
			}
		}		
	}

}
