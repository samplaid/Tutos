package lu.wealins.webia.services.core.service.impl;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.webia.services.core.mapper.ApplicationParameterMapper;
import lu.wealins.webia.services.core.persistence.entity.ApplicationParameterEntity;
import lu.wealins.webia.services.core.persistence.repository.ApplicationParameterRepository;
import lu.wealins.webia.services.core.service.ApplicationParameterService;

@Service
public class ApplicationParameterServiceImpl implements ApplicationParameterService {

	private static final String CODE_CANNOT_BE_NULL = "Code cannot be null.";

	@Autowired
	private ApplicationParameterRepository applicationParameterRepository;
	@Autowired
	private ApplicationParameterMapper applicationParameterMapper;

	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.core.service.ApplicationParameterService#getStringValue(java.lang.String)
	 */
	@Override
	public String getStringValue(String code) {
		ApplicationParameterDTO applicationParameter = getApplicationParameter(code);

		return applicationParameter.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ApplicationParameterService#getIntegerValue(java.lang.String)
	 */
	@Override
	public Integer getIntegerValue(String code) {
		return Integer.valueOf(getStringValue(code));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ApplicationParameterService#getIntegerValues(java.lang.String)
	 */
	@Override
	public List<Integer> getIntegerValues(String code) {
		String stringValue = getStringValue(code);

		String[] tmp = stringValue.split(",");

		return Arrays.stream(tmp)
				.map(s -> (s != null) ? Integer.valueOf(s.trim()) : null)
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ApplicationParameterService#getStringValues(java.lang.String)
	 */
	@Override
	public List<String> getStringValues(String code) {
		String stringValue = getStringValue(code);

		return Arrays.asList(stringValue.split(","));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ApplicationParameterService#getApplicationParameter(java.lang.String)
	 */
	@Override
	public ApplicationParameterDTO getApplicationParameter(String code) {
		Assert.notNull(code, CODE_CANNOT_BE_NULL);
		ApplicationParameterEntity applicationParameter = applicationParameterRepository.findByCode(code);

		if (applicationParameter == null) {
			throw new IllegalArgumentException("The application parameter " + code + " does not exist.");
		}

		return applicationParameterMapper.asApplicationParameterDTO(applicationParameter);
	}

	@Override
	public BigDecimal getBigDecimalValue(String code) {
		return new BigDecimal(getStringValue(code));
	}

}
