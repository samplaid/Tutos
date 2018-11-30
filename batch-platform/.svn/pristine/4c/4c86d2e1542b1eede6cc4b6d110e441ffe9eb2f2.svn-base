/**
 * 
 */
package lu.wealins.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.service.BatchUtilityService;
import lu.wealins.service.DateUtilityService;

/**
 * @author xqv66
 *
 */
@Component
public class BatchUtilityServiceImpl implements BatchUtilityService {

	@Autowired
	private DateUtilityService dateUtilityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.service.BatchUtilityService#retrieveJobParameters(org.
	 * springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public Map<String, Object> getJobParameters(ChunkContext chunkContext) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (chunkContext == null) {
			return parameters;
		}

		StepContext stepContext = chunkContext.getStepContext();

		if (stepContext == null) {
			return parameters;
		}

		return stepContext.getJobParameters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lu.wealins.service.BatchUtilityService#retrieveStringJobParameter(org.
	 * springframework.batch.core.scope.context.ChunkContext, java.lang.Object)
	 */
	@Override
	public String getStringJobParameter(ChunkContext chunkContext, Object key) {
		Map<String, Object> jobParameters = getJobParameters(chunkContext);

		return (String) jobParameters.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.service.BatchUtilityService#getDateJobParameter(org.springframework.batch.core.scope.context.ChunkContext, java.lang.Object)
	 */
	@Override
	public Date getDateJobParameter(ChunkContext chunkContext, Object key) {
		return dateUtilityService.createDate(getStringJobParameter(chunkContext, key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.service.BatchUtilityService#getIntegerJobParameter(org.springframework.batch.core.scope.context.ChunkContext, java.lang.Object)
	 */
	@Override
	public Integer getIntegerJobParameter(ChunkContext chunkContext, Object key) {
		Map<String, Object> jobParameters = getJobParameters(chunkContext);

		Object jobParameter = jobParameters.get(key);
		if (jobParameter == null) {
			return null;
		}

		if (jobParameter instanceof Integer) {
			return (Integer) jobParameter;
		}

		return Integer.decode((String) jobParameter);
	}

}
