package lu.wealins.webia.services.core.service.impl;

import org.springframework.core.GenericTypeResolver;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.webia.services.core.service.impl.form.data.WorkflowFormService;

public abstract class WorkflowFormServiceImpl<T extends FormDataDTO> implements WorkflowFormService<T> {

	private final Class<T> genericType;

	@SuppressWarnings("unchecked")
	public WorkflowFormServiceImpl() {
		this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), WorkflowFormServiceImpl.class);
	}

	@Override
	public T initFormData(Integer workflowItemId) {
		T formData = null;
		try {
			formData = genericType.newInstance();

			formData.setWorkflowItemId(workflowItemId);
			formData.setStatusCd(OperationStatus.NEW.name());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Cannot create an instance of " + genericType.getName() + ".", e);
		}

		return formData;
	}

}
