package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.webia.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class PartnerFormMapper extends AbstractPartnerFormMapper {

	@Override
	protected PolicyAgentShareDTO getBrokerEntryFees(String policyId) {
		return policyAgentShareService.getBrokerEntryFees(policyId);
	}

	@Override
	protected PolicyCoverageDTO getCoverage(PolicyDTO policy) {
		return policy.getFirstPolicyCoverages();
	}

}
