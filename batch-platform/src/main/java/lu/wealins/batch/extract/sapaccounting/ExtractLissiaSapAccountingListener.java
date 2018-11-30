package lu.wealins.batch.extract.sapaccounting;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author xqv99
 *
 */
public class ExtractLissiaSapAccountingListener  implements JobExecutionListener {

	Log logger = LogFactory.getLog(ExtractLissiaSapAccountingListener.class);
	
	private JobExecution activeExecution;
	
	@Autowired
	private JobRepository jobRepository;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	        if(activeExecution != null && activeExecution.isRunning()) {
	            jobExecution.stop();
	            //jobExecution.setExitStatus(new ExitStatus(jobExecution.getExitStatus().getExitCode(), "A job has already started. Please wait until the end of the current job before start a new jobs."));
	        } else {
	            activeExecution = jobExecution;
	            // reset
	            AbstractExtractLissiaSapAccountingTaskLet.reset();
	            AbstractExtractLissiaSapAccountingTaskLet.setJobExecutionAbstract(jobExecution);
	        }
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	          if(jobExecution == activeExecution) {
	              activeExecution = null; 
	          }
	          
	          if (AbstractExtractLissiaSapAccountingTaskLet.getJobErrorExecution() != null) {
	        	  AbstractExtractLissiaSapAccountingTaskLet.throwBusinessExceptionJobError(jobRepository);
	          }
	      }
	}


}
