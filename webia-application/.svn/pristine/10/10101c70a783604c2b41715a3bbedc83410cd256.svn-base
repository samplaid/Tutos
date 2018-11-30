package lu.wealins.webia.core.service.impl;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.StepEnricher;

@Service("webiaStepEnricher")
public class WebiaStepEnricherImpl extends AbstractStepEnricher implements StepEnricher {

	@Override
	public AppFormDTO enrichAppForm(AppFormDTO appForm) {
		enrichProduct(appForm);
		enrichFunds(appForm);
		enrichClients(appForm);

		return appForm;
	}


}
