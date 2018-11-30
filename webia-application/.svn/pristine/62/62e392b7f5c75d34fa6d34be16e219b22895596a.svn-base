package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.CodeLabelDTO;
import lu.wealins.webia.core.service.WebiaCodeLabelService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaCodeLabelServiceImpl implements WebiaCodeLabelService {

	private static final String GD = "GD";

	private static final String WEBIA_LOAD_CODE_LABEL = "webia/codeLabel/";

	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaCodeLabelService#getCodeLabels(java.lang.String)
	 */
	@Override
	public Collection<CodeLabelDTO> getCodeLabels(String typeCd) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		return restClientUtils.get(WEBIA_LOAD_CODE_LABEL, typeCd, params, new GenericType<Collection<CodeLabelDTO>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaCodeLabelService#getCodeLabel(java.lang.String, java.lang.String)
	 */
	@Override
	public CodeLabelDTO getCodeLabel(String typeCd, String code) {
		return restClientUtils.get(WEBIA_LOAD_CODE_LABEL, typeCd + "/" + code, CodeLabelDTO.class);
	}

	@Override
	public CodeLabelDTO getDeathCoverageCodeLabel(String code) {
		return getCodeLabels(GD).stream().filter(guaranteeDeathCodeLabel -> code.equals(guaranteeDeathCodeLabel.getCode())).findFirst().orElse(null);
	}

	@Override
	public CodeLabelDTO getDeathCoverageCodeLabel(String productLine, String aplhaValue) {
		return getDeathCoverageCodeLabel(productLine + "_" + aplhaValue);
	}

}
