package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowItemAllActionsDTO {

	private WorkflowActionSummaryDTO current = new WorkflowActionSummaryDTO();

	private List<WorkflowActionSummaryDTO> previous = new ArrayList<>();

	private List<WorkflowActionSummaryDTO> next = new ArrayList<>();

	public WorkflowActionSummaryDTO getCurrent() {
		return current;
	}

	public void setCurrent(WorkflowActionSummaryDTO current) {
		this.current = current;
	}

	public List<WorkflowActionSummaryDTO> getPrevious() {
		previous = (previous != null ? previous : new ArrayList<WorkflowActionSummaryDTO>());
		return previous;
	}

	public void setPrevious(List<WorkflowActionSummaryDTO> previous) {
		this.previous = previous;
	}

	public List<WorkflowActionSummaryDTO> getNext() {
		next = (next != null ? next : new ArrayList<WorkflowActionSummaryDTO>());
		return next;
	}

	public void setNext(List<WorkflowActionSummaryDTO> next) {
		this.next = next;
	}

}