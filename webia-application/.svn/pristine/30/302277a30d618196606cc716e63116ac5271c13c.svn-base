package lu.wealins.webia.core.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityPolicyAgentShareService;
import lu.wealins.webia.core.service.LiabilityProductValueService;

public abstract class AbstractPartnerFormMapper {

	@Autowired
	private LiabilityProductValueService productValueService;
	@Autowired
	private LiabilityAgentService agentService;

	@Autowired
	protected LiabilityPolicyAgentShareService policyAgentShareService;

	@Mappings({
			@Mapping(source = "agent.agtId", target = "partnerId"),
			@Mapping(source = "percentage", target = "mngtFeesPct"),
			@Mapping(source = "contractManagementFee.numericValue", target = "entryFeesAmt"),
			@Mapping(source = "modifyBy", target = "updateUser"),
			@Mapping(source = "modifyDate", target = "updateDt"),
			@Mapping(source = "createdDate", target = "creationDt"),
			@Mapping(source = "createdBy", target = "creationUser"),
	})
	public abstract PartnerFormDTO asPartnerFormDTO(PolicyAgentShareDTO in);

	@Mappings({
			@Mapping(source = "mngtFeesPct", target = "percentage"),
			@Mapping(source = "entryFeesAmt", target = "contractManagementFee.numericValue"),

			@Mapping(source = "updateUser", target = "modifyBy"),
			@Mapping(source = "updateDt", target = "modifyDate"),
			@Mapping(source = "creationDt", target = "createdDate"),
			@Mapping(source = "creationUser", target = "createdBy"),
	})
	public abstract PolicyAgentShareDTO asPolicyAgentShareDTO(PartnerFormDTO in);

	@AfterMapping
	protected PolicyAgentShareDTO asPolicyAgentShareDTOAfterMapping(PartnerFormDTO in, @MappingTarget PolicyAgentShareDTO target) {

		target.setType(PolicyAgentShareType.toPolicyAgentShareType(in.getPartnerCategory()).getType());

		target.setAgent(agentService.getAgentLite(in.getPartnerId()));

		return target;
	}

	@AfterMapping
	protected PartnerFormDTO asPartnerFormDTOAfterMapping(PolicyAgentShareDTO in, @MappingTarget PartnerFormDTO target) {

		target.setPartnerCategory(AgentCategory.toAgentCategory(in.getType(), in.getAgent().getCategory()).getCategory());

		return target;
	}

	public void updateBrokerFees(PolicyDTO policy, @MappingTarget PartnerFormDTO target) {

		String policyId = policy.getPolId();

		PolicyAgentShareDTO brokerEntryFees = getBrokerEntryFees(policyId);
		PolicyCoverageDTO coverage = getCoverage(policy);

		if (brokerEntryFees != null && coverage != null) {
			String productLineId = coverage.getProductLine();
			if (productValueService.isPercentagePolicyFee(productLineId)) {
				target.setEntryFeesPct(brokerEntryFees.getPercentage());
			} else {
				target.setEntryFeesAmt(brokerEntryFees.getSpecificIce());
			}
		}
	}

	protected abstract PolicyAgentShareDTO getBrokerEntryFees(String policyId);

	protected abstract PolicyCoverageDTO getCoverage(PolicyDTO policy);
}
