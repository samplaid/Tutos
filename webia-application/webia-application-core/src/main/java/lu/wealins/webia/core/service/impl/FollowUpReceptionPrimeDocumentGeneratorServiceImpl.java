/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.editing.common.webia.FollowUp.Destination;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;

/**
 * @author NGA
 * 
 */
@Component("FollowUpReceptionPrimeDocumentGeneratorService")
public class FollowUpReceptionPrimeDocumentGeneratorServiceImpl extends FollowUpDocumentGeneratorcCommonServiceImpl {

	@Override
	public DocumentGenerationResponse generateDocumentFromAppForm(SecurityContext context, AppFormDTO appForm) {

		if (!isBrokerEwealins(appForm)) {
			generateDocumentFromAppForm(context, appForm, getBrokerLanguage(appForm), true);
		}

		List<Destination> assetManagerDestinations = getAssetManagerAgentContact(appForm);
		if (assetManagerDestinations != null && !assetManagerDestinations.isEmpty()) {
			for (Destination destination : assetManagerDestinations) {
				generateDocumentFromAppForm(context, appForm,
						destination.getLanguage() == null || destination.getLanguage().trim().isEmpty()
								? getDefaultLanguage()
								: destination.getLanguage().trim(),
						false);
			}
		}

		return new DocumentGenerationResponse();
	}

}
