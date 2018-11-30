package lu.wealins.batch.extract.sapaccounting;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class ExtractLissiaSapAccountingDecider implements JobExecutionDecider {
	public static FlowExecutionStatus flowExecutionStatus = FlowExecutionStatus.UNKNOWN;

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		return flowExecutionStatus;
	}

}
