package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.service.LiabilityUoptDetailService;
import lu.wealins.webia.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, PartnerFormMapper.class })
public abstract class BrokerChangeFormMapper {

	@Autowired
	private LiabilityAgentService agentService;
	@Autowired
	private LiabilityUoptDetailService uoptDetailService;

	@Autowired
	private LiabilityPolicyCoverageService policyCoverageService;
	@Autowired
	private LiabilityProductValueService productValueService;
	@Autowired
	private LiabilityPolicyService policyService;

	@Mappings({
			@Mapping(target = "sendingRules", source = "category.uddId"),
			@Mapping(target = "mailToAgent", source = "mailToAgent.agtId"),
	})
	public abstract BrokerChangeFormDTO asBrokerChangeFormDTO(BrokerChangeDTO in);

	@Mappings({
			@Mapping(target = "sendingRules", source = "category.uddId"),
			@Mapping(target = "mailToAgent", source = "mailToAgent.agtId"),
	})
	public abstract void asBrokerChangeFormDTO(BrokerChangeDTO in, @MappingTarget BrokerChangeFormDTO target);

	@AfterMapping
	public void afterEntityMapping(BrokerChangeDTO in, @MappingTarget BrokerChangeFormDTO target) {
		String policyId = in.getPolicyId();
		PolicyLightDTO policyLight = policyService.getPolicyLight(policyId);

		PolicyCoverageDTO firstCoverage = policyLight.getFirstCoverage();
		if (firstCoverage != null) {
			target.setContractMngtFeesPct(productValueService.getNumericValue(productValueService.getContractManagementFees(policyId, firstCoverage.getProductLine(), firstCoverage.getCoverage())));
		}

		PolicyAgentShareDTO broker = policyLight.getBroker();
		if (broker != null) {
			target.setOriginalPartnerId(broker.getAgent().getAgtId());
		}
	}

	@Mappings({
			@Mapping(target = "category.uddId", source = "sendingRules"),
			@Mapping(target = "mailToAgent.agtId", source = "mailToAgent"),
	})
	public abstract BrokerChangeDTO asBrokerChangeDTO(BrokerChangeFormDTO in);

	@AfterMapping
	public BrokerChangeDTO afterEntityMapping(BrokerChangeFormDTO in, @MappingTarget BrokerChangeDTO target) {

		String mailToAgent = in.getMailToAgent();
		if (StringUtils.isNotBlank(mailToAgent)) {
			target.setMailToAgent(agentService.getAgentLite(mailToAgent));
		}
		String sendingRules = in.getSendingRules();
		if (StringUtils.isNotBlank(sendingRules)) {
			target.setCategory(uoptDetailService.getUoptDetail(sendingRules));
		}

		initDefaultValues(in, target.getBroker());
		initDefaultValues(in, target.getBrokerContact());
		initDefaultValues(in, target.getSubBroker());
		
		return target;
	}

	private void initDefaultValues(BrokerChangeFormDTO in, PolicyAgentShareDTO policyAgentShare) {
		if (policyAgentShare == null) {
			return;
		}
		policyAgentShare.setPrimaryAgent(Boolean.TRUE);
		String policyId = in.getPolicyId();
		policyAgentShare.setPolId(policyId);
		policyAgentShare.setSpecificIce(BigDecimal.ZERO);

		if (policyId != null) {
			PolicyCoverageDTO lastPolicyCoverage = policyCoverageService.getLastPolicyCoverage(policyId);
			if (lastPolicyCoverage != null) {
				policyAgentShare.setCoverage(lastPolicyCoverage.getCoverage());
			}
		}
	}

}
