package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.webia.core.mapper.FundFormMapper;
import lu.wealins.webia.core.service.FundFormService;
import lu.wealins.webia.core.service.LiabilityPolicyValuationService;

@Service
public class FundFormServiceImpl extends AbstractFundFormService<FundFormDTO> implements FundFormService {

	@Autowired
	private LiabilityPolicyValuationService valuationService;

	@Autowired
	private FundFormMapper fundFormMapper;

	@Override
	public Collection<FundFormDTO> getFundForms(String policyId) {
		return getFundForms(valuationService.getPolicyValuation(policyId, null, null));
	}

	@Override
	public Collection<FundFormDTO> getFundForms(PolicyValuationDTO policyValuation) {
		if (policyValuation == null || CollectionUtils.isEmpty(policyValuation.getHoldings())) {
			return new ArrayList<>();
		}

		return policyValuation
				.getHoldings()
				.stream()
				.map(pvh -> toFundForm(pvh, policyValuation.getTotalPolicyCurrency()))
				.collect(Collectors.toList());

	}

	private FundFormDTO toFundForm(PolicyValuationHoldingDTO pvh, BigDecimal total) {
		FundFormDTO fundForm = fundFormMapper.asFundFormDTO(pvh);

		fundForm.setSplit(pvh.getHoldingValuePolicyCurrency().multiply(BigDecimal.valueOf(100)).divide(total, 2, RoundingMode.HALF_UP));

		return fundForm;
	}

	@Override
	public void enrichFunds(Collection<FundFormDTO> fundForms, Date paymentDate) {
		super.enrichFunds(fundForms);

		fundForms.forEach(fundRegistration -> fundRegistration.setAddOnValuableAmount(fundService.canAddFIDorFASFundValuationAmount(fundRegistration.getFundId(), paymentDate, (short) 1)));
	}

}
