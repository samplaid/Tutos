/**
 * 
 */
package lu.wealins.batch.editing;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author bqv55
 *
 */
@Component
public class GenerateEditingListener implements JobExecutionListener {

	private JobExecution activeExecution;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	        if(activeExecution != null && activeExecution.isRunning()) {
	            jobExecution.stop();
	        } else {
	            activeExecution = jobExecution;
	        }
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	          if(jobExecution.equals(activeExecution)) {
	              activeExecution = null; 
	          }
	      }
	}

}
