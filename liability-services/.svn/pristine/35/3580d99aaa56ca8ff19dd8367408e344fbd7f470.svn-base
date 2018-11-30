package lu.wealins.liability.services.core.mapper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class BrokerChangeMapper {

	@Autowired
	private PolicyService policyService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private UoptDetailMapper uoptDetailMapper;
	@Autowired
	private PolicyValuationService valuationService;

	public abstract BrokerChangeDTO asBrokerChangeDTO(AmendmentRequest in);

	@AfterMapping
	public void afterMapping(AmendmentRequest in, @MappingTarget BrokerChangeDTO target) {
		String policyId = in.getPolicyId();
		PolicyEntity policyEntity = policyService.getPolicyEntity(policyId);
		Assert.notNull(policyEntity, "Unknown policy " + policyId + ".");

		String mailToAgent = policyEntity.getMailToAgent();
		if (StringUtils.isNotBlank(mailToAgent)) {
			target.setMailToAgent(agentService.getAgentLite(mailToAgent));
		}
		
		target.setCategory(uoptDetailMapper.asUoptDetailDTO(policyEntity.getCategory()));
		target.setPolicyValuation(valuationService.getPolicyValuation(policyId, new Date()));


	}

}
