package lu.wealins.webia.core.service.validations.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.validations.FundFormValidationService;
import lu.wealins.webia.core.service.validations.FundValidationService;

@Service
public class FundFormValidationServiceImpl implements FundFormValidationService {

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private FundValidationService fundValidationService;

	@Override
	public void validateExternalFunds(Collection<FundFormDTO> fundForms, PartnerFormDTO broker, List<String> errors) {
		if (!CollectionUtils.isEmpty(fundForms)) {
			Collection<FundLiteDTO> funds = fundForms.stream().map(x -> x.getFund()).collect(Collectors.toList());
			fundValidationService.validateExternalFunds(funds, broker, errors);
		}
	}

	@Override
	public void validateFIDorFASForDocumentation(Collection<FundFormDTO> fundForms, List<String> errors) {

		Set<FundLiteDTO> funds = fundForms.stream().map(f -> f.getFund()).filter(f -> fundService.isFIDorFAS(f)).collect(Collectors.toSet());
		funds.forEach(f -> validateFundForDocumentation(f.getFdsId(), errors));
	}

	@Override
	public void validateFundForDocumentation(String fdsId, List<String> errors) {
		FundDTO f = fundService.getFund(fdsId);

		if (f.getStatus() != 1) {
			errors.add("Fund " + f.getIban() + " must be active.");
		}

		if (!fundService.isFIDorFAS(f)) {
			errors.add("'Mandat de Gestion' must be generated for FID or FAS.");
			return;
		}

		validateMandatoryField(f.getGroupingCode(), "Circular", f.getFdsId(), errors);
		validateMandatoryField(f.getFundClassification(), "Fund Classification", f.getFdsId(), errors);
		validateMandatoryField(f.getInvestCat(), "Invest Cat", f.getFdsId(), errors);
		validateMandatoryField(f.getAlternativeFunds(), "Alternative Fund", f.getFdsId(), errors);

		if (f.getInvestCashLimit() == null) {
			errors.add("Invest Cash Limit is mandatory for the fund " + f.getFdsId() + ".");
		}

		if (fundService.isFas(f) && f.getFinancialAdvisorAgent() != null
				&& AgentCategory.PRESTATION_SERVICE_INVEST.getCategory().equals(f.getFinancialAdvisorAgent().getCategory())
				&& (f.getFinAdvisorFee() == null || f.getFinAdvisorFee().intValue() < 0)) {
			errors.add("Fund: (" + f.getIban() + "): The financial advisor fee is mandatory for the prestation service investment (a.k.a PSI).");
		}
	}

	private void validateMandatoryField(String fieldValue, String label, String fdsId, List<String> errors) {
		if (StringUtils.isEmpty(fieldValue)) {
			errors.add(label + " is mandatory for the fund " + fdsId + ".");
		}
	}

}
