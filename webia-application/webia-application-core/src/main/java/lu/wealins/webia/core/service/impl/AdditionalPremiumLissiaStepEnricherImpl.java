package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.core.mapper.AbstractAppFormMapper;
import lu.wealins.webia.core.mapper.AdditionalPremiumFormMapper;

@Service("additionalPremiumLissiaStepEnricher")
public class AdditionalPremiumLissiaStepEnricherImpl extends LissiaStepEnricher {

	@Autowired
	private AdditionalPremiumFormMapper appFormMapper;

	@Override
	protected AbstractAppFormMapper getFormMapper() {
		return appFormMapper;
	}
}
