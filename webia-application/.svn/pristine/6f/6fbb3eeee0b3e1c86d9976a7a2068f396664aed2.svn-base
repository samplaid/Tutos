package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.LiabilityAnalysisService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class LiabilityAnalysisServiceImpl implements LiabilityAnalysisService {

	private static final String ANALYSIS_CANNOT_BE_NULL = "A cannot be null.";
	private static final String LIABILITY_ENRICH_ANALYSIS = "liability/analysis/enrich";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public AppFormDTO enrichAnalysis(AppFormDTO analysis) {
		Assert.notNull(analysis, ANALYSIS_CANNOT_BE_NULL);

		return restClientUtils.post(LIABILITY_ENRICH_ANALYSIS, analysis, AppFormDTO.class);
	}

}
