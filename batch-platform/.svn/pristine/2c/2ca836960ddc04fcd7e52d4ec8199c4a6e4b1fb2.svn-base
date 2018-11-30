/**
 * 
 */
package lu.wealins.filepoller;

import java.io.File;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * @author xqv66
 *
 */
public class ChannelInterceptor extends ChannelInterceptorAdapter {

	private JobExecutor jobExecutor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.messaging.support.ChannelInterceptorAdapter# afterSendCompletion(org.springframework.messaging.Message, org.springframework.messaging.MessageChannel, boolean,
	 * java.lang.Exception)
	 */
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		File file = (File) message.getPayload();

		try {
			jobExecutor.launchJob(file);
		} catch (JobExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setJobExecutor(JobExecutor jobExecutor) {
		this.jobExecutor = jobExecutor;
	}

}
