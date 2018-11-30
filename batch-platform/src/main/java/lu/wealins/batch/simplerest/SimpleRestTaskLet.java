package lu.wealins.batch.simplerest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.service.BatchUtilityService;
import lu.wealins.service.RestUtilityService;

public abstract class SimpleRestTaskLet<Req, Resp> implements Tasklet, JobExecutionListener {
	
	private static final String COMPLETED = "COMPLETED";

	@Autowired
	protected RestUtilityService restUtilityService;

	private String url;

	private Resp response;

	@Autowired
	protected BatchUtilityService batchUtilityService;

	@SuppressWarnings("unchecked")
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Type mySuperclass = getClass().getGenericSuperclass();
		Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[1];

		response = restUtilityService.post(getUrl(), createRequest(chunkContext), (Class<Resp>) tType);

		return RepeatStatus.FINISHED;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// Nothing to do...
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		// Return a completed message.
		jobExecution.setExitStatus(new ExitStatus(COMPLETED, createExitMessage(response)));
	}

	public abstract Req createRequest(ChunkContext chunkContext);

	public abstract String createExitMessage(Resp Response);

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
