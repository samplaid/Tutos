/**
 * 
 */
package lu.wealins.webia.core.service.validations.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;
import lu.wealins.webia.core.service.validations.SurrenderDocumentValidationService;
import lu.wealins.webia.ws.rest.request.EditingRequest;
/**
 * @author NGA
 *
 */
@Service
public class SurrenderValidationServiceImpl implements SurrenderDocumentValidationService {

	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat messageFormater = new SimpleDateFormat("dd/MM/YYYY");
	@Autowired
	private LiabilityPolicyValuationService policyValuationService;

	@Override
	public Optional<String> validate(EditingRequest editingRequest) {

		if (editingRequest == null || (StringUtils.isBlank(editingRequest.getPolicy()))
				|| (Objects.isNull(editingRequest.getEventDate()))) {
			String message = "Bad request please specify, policy and effect date of transaction";
			return Optional.of(message);
		}

		PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(editingRequest.getPolicy(),
				StringUtils.EMPTY, editingRequest.getEventDate());
		Date eventDate = editingRequest.getEventDate();

		if (policyValuation == null || policyValuation.getHoldings() == null
				|| policyValuation.getHoldings().isEmpty()) {
			return Optional.empty();
		}

		Optional<PolicyValuationHoldingDTO> fidorFasWithoutvniAteventDate = policyValuation.getHoldings().stream()
				.filter(isExistFidOrFasWithoutvniAtEventDate(eventDate)).findFirst();

		if (fidorFasWithoutvniAteventDate.isPresent()) {
			String message = " The tax certificate letter can’t be generated because the NAV price of the fund "
					+ fidorFasWithoutvniAteventDate.get().getFundDisplayName()
					+ " is not found on " + messageFormater.format(eventDate);
			return Optional.of(message);
		}

		return Optional.empty();
	}

	public Predicate<PolicyValuationHoldingDTO> isExistFidOrFasWithoutvniAtEventDate(Date eventDate) {
		return valuation -> valuation != null && (isFID(valuation) || isFAS(valuation))
				&& !(formater.format(eventDate).compareTo(formater.format(valuation.getPriceDate())) == 0);
	 }

	private boolean isFID(PolicyValuationHoldingDTO valuation) {
		return valuation != null && valuation.getFundSubType() != null
				&& FundSubType.FID.name().equals(valuation.getFundSubType().trim());
	}

	private boolean isFAS(PolicyValuationHoldingDTO valuation) {
		return valuation != null && valuation.getFundSubType() != null
				&& FundSubType.FAS.name().equals(valuation.getFundSubType().trim());
	}

}
