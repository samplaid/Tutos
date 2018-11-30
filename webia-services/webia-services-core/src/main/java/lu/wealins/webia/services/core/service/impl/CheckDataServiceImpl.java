package lu.wealins.webia.services.core.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.mapper.CheckDataMapper;
import lu.wealins.webia.services.core.persistence.entity.AppFormEntity;
import lu.wealins.webia.services.core.persistence.entity.CheckDataEntity;
import lu.wealins.webia.services.core.persistence.repository.AppFormRepository;
import lu.wealins.webia.services.core.persistence.repository.CheckDataRepository;
import lu.wealins.webia.services.core.service.CheckDataService;
import lu.wealins.webia.services.core.service.CheckStepService;

@Service
public class CheckDataServiceImpl implements CheckDataService {

	private static final String YES = "YES";
	private static final String NO = "NO";
	private static final String LPS19 = "LPS19";
	private static final String CTX6 = "CTX6";

	@Autowired
	private CheckDataRepository checkDataRepository;

	@Autowired
	private AppFormRepository appFormRepository;

	@Autowired
	private CheckDataMapper checkDataMapper;

	@Autowired
	private CheckStepService checkStepService;

	private final Logger log = LoggerFactory.getLogger(CheckDataServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#createCheckData(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public CheckDataDTO createCheckData(Integer checkId, Integer workflowItemId) {
		CheckDataDTO checkData = new CheckDataDTO();

		checkData.setCheckId(checkId);
		checkData.setWorkflowItemId(workflowItemId);

		return checkData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#updateContextualRules(lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public void updateContextualRules(StepDTO step) {

		String stepWorkflow = step.getStepWorkflow();
		if (stepWorkflow != null) {
			switch (StepTypeDTO.getStepType(stepWorkflow)) {
			case ACCEPTANCE:
				initTaxCompliant(step);

				break;
			default:
				break;
			}
		}
	}

	/**
	 * Init tax compliant. Initialize the tax compliant question with the value of the question LPS19.
	 * 
	 * @param step The step
	 */
	private void initTaxCompliant(StepDTO step) {
		CheckStepDTO ctx6 = checkStepService.getCheckStep(step, CTX6);

		if (ctx6 != null) {

			CheckStepDTO lps19 = checkStepService.getCheckStep(step, LPS19);

			if (lps19 != null) {
				CheckDataDTO lps19CheckData = lps19.getCheck().getCheckData();

				if (lps19CheckData == null) {
					return;
				}

				CheckWorkflowDTO check = ctx6.getCheck();
				CheckDataDTO checkData = check.getCheckData();

				if (checkData == null) {
					checkData = createCheckData(check.getCheckId(), step.getWorkflowItemId());
					check.setCheckData(checkData);
				}

				if (checkData.getDataValueYesNoNa() == null && lps19CheckData.getDataValueYesNoNa() != null) {
					if (hasYesValue(lps19CheckData)) {
						setNoValue(checkData);
					} else if (hasNoValue(lps19CheckData)) {
						setYesValue(checkData);
					}
				}
			}

		}
	}

	private void setYesValue(CheckDataDTO checkData) {
		checkData.setDataValueYesNoNa(YES);
	}

	private void setNoValue(CheckDataDTO checkData) {
		checkData.setDataValueYesNoNa(NO);
	}

	private boolean hasNoValue(CheckDataDTO lps19CheckData) {
		return NO.equalsIgnoreCase(lps19CheckData.getDataValueYesNoNa());
	}

	private boolean hasYesValue(CheckDataDTO lps19CheckData) {
		return YES.equalsIgnoreCase(lps19CheckData.getDataValueYesNoNa());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#getCheckData(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public CheckDataDTO getCheckData(Integer workflowItemId, Integer checkId) {
		CheckDataEntity checkData = checkDataRepository.findByWorkflowItemIdAndCheckId(workflowItemId, checkId);

		return checkDataMapper.asCheckDataDTO(checkData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#getCheckData(java.lang.Integer, java.util.List)
	 */
	@Override
	public Map<String, CheckDataDTO> getCheckData(Integer workflowItemId, List<String> checkCodes) {
		List<CheckDataEntity> checkData = checkDataRepository.findByWorkflowItemIdAndCheckCodeIn(workflowItemId, checkCodes);

		return checkData.stream().collect(Collectors.toMap(x -> x.getCheckWorkflow().getCheckCode(), x -> checkDataMapper.asCheckDataDTO(x)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#getCheckData(java.lang.Integer, java.lang.String)
	 */
	@Override
	public CheckDataDTO getCheckData(Integer workflowItemId, String checkCode) {
		List<CheckDataEntity> checkData = checkDataRepository.findByWorkflowItemIdAndCheckCodeIn(workflowItemId, Arrays.asList(checkCode));

		if (CollectionUtils.isEmpty(checkData)) {
			return null;
		}

		return checkDataMapper.asCheckDataDTO(CollectionUtils.extractSingleton(checkData));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#update(lu.wealins.common.dto.webia.services.CheckDataDTO)
	 */
	@Override
	public CheckDataDTO update(CheckDataDTO checkDataDTO) {
		Assert.notNull(checkDataDTO);
		Assert.notNull(checkDataDTO.getCheckId());
		Assert.notNull(checkDataDTO.getWorkflowItemId());

		CheckDataEntity checkDataEntity = checkDataMapper.asCheckDataEntity(checkDataDTO);
		checkDataEntity = checkDataRepository.save(checkDataEntity);
		log.debug("Save checkData (id={0}, value={1}).", checkDataEntity.getCheckDataId(), checkDataEntity.getDataValueYesNoNa());

		return checkDataMapper.asCheckDataDTO(checkDataEntity);
	}

	@Override
	public CheckDataContainerDTO update(CheckDataContainerDTO checkDataContainer) {
		Assert.notNull(checkDataContainer);

		Map<String, CheckDataDTO> checkData = new HashMap<>();
		for (Entry<String, CheckDataDTO> entrySet : checkDataContainer.getCheckData().entrySet()) {
			checkData.put(entrySet.getKey(), update(entrySet.getValue()));
		}

		checkDataContainer.setCheckData(checkData);

		return checkDataContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CheckDataService#hasValue(lu.wealins.common.dto.webia.services.CheckDataDTO)
	 */
	@Override
	public boolean hasValue(CheckDataDTO checkDataDTO) {
		if (checkDataDTO == null) {
			return false;
		}
		return checkDataDTO.hasValue();
	}

	@Override
	public CheckDataDTO getCheckData(String policyId, Integer checkId) {

		List<AppFormEntity> appForms = appFormRepository.findByPolicyId(policyId);
		CheckDataEntity checkData = null;
		if (appForms != null && !appForms.isEmpty()) {
			checkData = checkDataRepository.findByWorkflowItemIdAndCheckId(appForms.iterator().next().getWorkflowItemId(), checkId);
		}

		return checkDataMapper.asCheckDataDTO(checkData);
	}

	@Override
	public boolean hasYesValue(Integer workflowItemId, String checkCode) {
		CheckDataDTO checkData = getCheckData(workflowItemId, checkCode);
		return checkData != null && hasYesValue(checkData);
	}

}
