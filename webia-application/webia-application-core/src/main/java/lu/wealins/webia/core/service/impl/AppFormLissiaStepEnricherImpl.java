package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.core.mapper.AbstractAppFormMapper;
import lu.wealins.webia.core.mapper.AppFormMapper;

@Service("lissiaStepEnricher")
public class AppFormLissiaStepEnricherImpl extends LissiaStepEnricher {

	@Autowired
	private AppFormMapper appFormMapper;

	@Override
	protected AbstractAppFormMapper getFormMapper() {
		return appFormMapper;
	}

}
