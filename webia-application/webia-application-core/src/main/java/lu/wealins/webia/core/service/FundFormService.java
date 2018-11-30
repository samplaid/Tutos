package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;

public interface FundFormService {

	Collection<FundFormDTO> getFundForms(PolicyValuationDTO policyValuation);

	void enrichFunds(Collection<FundFormDTO> fundForms, Date paymentDate);
}
