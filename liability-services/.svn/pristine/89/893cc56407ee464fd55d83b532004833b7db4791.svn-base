package lu.wealins.liability.services.core.utils.predicate;

import org.apache.commons.collections4.Predicate;
import org.springframework.util.Assert;

import uk.co.liss.webservice.workflowitem.domain.xsd.WorkflowItemMetadata;

public class WorkflowItemMetadaPredicate implements Predicate<WorkflowItemMetadata> {

	private static final String WORKFLOW_METADATA_NAME_CANNOT_BE_NULL = "Workflow metadata name cannot be null.";
	private String name;

	public WorkflowItemMetadaPredicate(String name) {
		Assert.notNull(name, WORKFLOW_METADATA_NAME_CANNOT_BE_NULL);
		this.name = name;
	}

	@Override
	public boolean evaluate(WorkflowItemMetadata workflowItemMetadata) {
		return name.equals(workflowItemMetadata.getName());
	}

}