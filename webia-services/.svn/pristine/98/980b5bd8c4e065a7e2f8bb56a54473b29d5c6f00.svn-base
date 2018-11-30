package lu.wealins.webia.services.core.service;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.webia.services.ws.rest.FormDataRESTService;

@Component
public class FormDataRESTServiceImpl implements FormDataRESTService {

	@Autowired
	private FormDataService formDataService;

	@Override
	public FormDataDTO initFormData(SecurityContext context, Integer workflowItemId, Integer workflowItemTypeId) {
		return formDataService.initFormData(workflowItemId, workflowItemTypeId);
	}

}
