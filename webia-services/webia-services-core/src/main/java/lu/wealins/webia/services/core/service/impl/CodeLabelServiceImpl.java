package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.mapper.CodeLabelMapper;
import lu.wealins.webia.services.core.persistence.repository.CodeLabelRepository;
import lu.wealins.webia.services.core.service.CodeLabelService;
import lu.wealins.common.dto.webia.services.CodeLabelDTO;

@Service
public class CodeLabelServiceImpl implements CodeLabelService {

	private static final String TYPE_CANNOT_BE_NULL = "Type cannot be null";
	@Autowired
	private CodeLabelRepository codeLabelRepository;
	@Autowired
	private CodeLabelMapper codeLabelMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.CodeLabelService#getCodeLabels(java.lang.String)
	 */
	@Override
	public Collection<CodeLabelDTO> getCodeLabels(String typeCd) {
		Assert.notNull(typeCd, TYPE_CANNOT_BE_NULL);

		return codeLabelMapper.asCodeLabelDTOs(codeLabelRepository.findByTypeCd(typeCd));
	}

	public CodeLabelDTO getCodeLabel(String typeCd, String code) {
		Assert.notNull(typeCd, TYPE_CANNOT_BE_NULL);

		return codeLabelMapper.asCodeLabelDTO(codeLabelRepository.findByTypeCdAndCode(typeCd, code));
	}
}
