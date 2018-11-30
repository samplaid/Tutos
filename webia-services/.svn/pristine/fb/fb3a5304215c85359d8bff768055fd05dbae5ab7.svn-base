package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.CodeLabelService;
import lu.wealins.webia.services.ws.rest.CodeLabelRESTService;
import lu.wealins.common.dto.webia.services.CodeLabelDTO;

@Component
public class CodeLabelRESTServiceImpl implements CodeLabelRESTService {

	@Autowired
	private CodeLabelService codeLabelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.CodeLabelRESTService#getCodeLabels(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Collection<CodeLabelDTO> getCodeLabels(SecurityContext context, String typeCd) {
		return codeLabelService.getCodeLabels(typeCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.CodeLabelRESTService#getCodeLabel(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String)
	 */
	@Override
	public CodeLabelDTO getCodeLabel(SecurityContext context, String typeCd, String code) {
		return codeLabelService.getCodeLabel(typeCd, code);
	}

}
