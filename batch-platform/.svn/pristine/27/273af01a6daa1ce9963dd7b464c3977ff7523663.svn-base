package lu.wealins.batch.extract.commissiontopay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * 
 * @author xqv95
 *
 */
public class ExtractLissiaCommissionToPayJobListener  implements JobExecutionListener {

	Log logger = LogFactory.getLog(ExtractLissiaCommissionToPayJobListener.class);
	
	private JobExecution activeExecution;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	        if(activeExecution != null && activeExecution.isRunning()) {
	            jobExecution.stop();
	            //jobExecution.setExitStatus(new ExitStatus(jobExecution.getExitStatus().getExitCode(), "A job has already started. Please wait until the end of the current job before start a new jobs."));
	        } else {
	            activeExecution = jobExecution;
	            // reset
	    		AbstractExtractLissiaCommissionToPayTaskLet.reset();
	        }
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	          if(jobExecution == activeExecution) {
	              activeExecution = null; 
	          }
	      }
	}

}
