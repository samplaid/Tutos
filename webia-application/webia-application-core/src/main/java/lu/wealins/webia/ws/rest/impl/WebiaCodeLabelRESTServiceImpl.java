package lu.wealins.webia.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CodeLabelDTO;
import lu.wealins.webia.core.service.WebiaCodeLabelService;
import lu.wealins.webia.ws.rest.WebiaCodeLabelRESTService;

@Component
public class WebiaCodeLabelRESTServiceImpl implements WebiaCodeLabelRESTService {

	@Autowired
	private WebiaCodeLabelService codeLabelService;

	@Override
	public Collection<CodeLabelDTO> getCodeLabels(SecurityContext context, String typeCd) {
		return codeLabelService.getCodeLabels(typeCd);
	}

}
