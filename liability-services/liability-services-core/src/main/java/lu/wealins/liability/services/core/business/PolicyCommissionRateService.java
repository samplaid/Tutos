package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.PolicyCommissionRateDTO;

public interface PolicyCommissionRateService {
	PolicyCommissionRateDTO getPolicyCommissionRate(String policy);
}
