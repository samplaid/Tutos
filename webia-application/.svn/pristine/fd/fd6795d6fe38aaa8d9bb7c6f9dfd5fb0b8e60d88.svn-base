/**
 * 
 */
package lu.wealins.webia.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.editing.common.webia.Account;
import lu.wealins.editing.common.webia.SubscriptionFollowUp;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;

@Mapper(componentModel = "spring")
public abstract class FollowUpFundMapper {
	@Autowired
	LiabilityAgentService agentService;

	@Autowired
	FollowUpDocumentContentHelper followUpHelper;

	@Autowired
	LiabilityFundService fundService;

	@Mappings({ @Mapping(source = "fdsId", target = "fundId"), @Mapping(source = "salesRep", target = "contactPerson"),
			@Mapping(source = "fundSubType", target = "fundSubType"),
			@Mapping(source = "financialAdvisor", target = "psi"),
			@Mapping(source = "assetManager", target = "assetManager"), })
	public abstract SubscriptionFollowUp.Fund asFollowUpFund(FundLiteDTO in);

	@Mappings({ @Mapping(source = "iban", target = "iban"),
			@Mapping(source = "depositBank", target = "bankName"),
			@Mapping(source = "currency", target = "currency"), })
	public abstract Account asFollowUpFundAccount(FundLiteDTO in);

	@AfterMapping
	protected SubscriptionFollowUp.Fund followUpFundAfterMapping(FundLiteDTO in,
			@MappingTarget SubscriptionFollowUp.Fund target) {

		if (target == null) {
			return target;
		}

		if (isFAS(in)) {
			target.setAssetManager(null);
		} else if (isFID(in)) {
			target.setPsi(null);
		}

		target = transformFund(in, target);
		return target;
	}

	@AfterMapping
	protected Account followUpFundAccountAfterMapping(FundLiteDTO in, @MappingTarget Account target) {

		if (target == null) {
			return target;
		}

		String bankName = getBankName(in.getDepositBank());
		target.setBankName(bankName == null || bankName.trim().isEmpty() ? null : bankName);
		target.setIban(target.getIban() == null || target.getIban().trim().isEmpty() ? null
				: followUpHelper.formatIban(target.getIban()));
		FundDTO fundDTO = fundService.getFund(in.getFdsId());

		if (fundDTO != null) {
			target.setAccountNumber(fundDTO.getAccountRoot() != null && !fundDTO.getAccountRoot().trim().isEmpty()
					? fundDTO.getAccountRoot()
					: null);
		}
		return target;
	}

	private boolean isFAS(FundLiteDTO fundDTO) {
		return FundSubType.FAS.name().equals(fundDTO.getFundSubType());
	}

	private boolean isFID(FundLiteDTO fundDTO) {
		return FundSubType.FID.name().equals(fundDTO.getFundSubType());
	}


	private String getBankName(String BankId) {
		String bankName = BankId;
		if (BankId != null && !BankId.isEmpty()) {
			AgentDTO agent = agentService.getAgent(BankId);
			if (agent != null) {
				bankName = agent.getName();
			}
		}

		return bankName;
	}

	private String getPersonName(String personId) {
		String personName = personId;
		if (personId != null && !personId.trim().isEmpty()) {
			AgentDTO agent = agentService.getAgent(personId);
			if (agent != null) {
				personName = String.join(StringUtils.SPACE, agent.getName(),
						agent.getFirstname() != null ? agent.getFirstname() : StringUtils.EMPTY);
			}
		}
		return personName;
	}

	private SubscriptionFollowUp.Fund transformFund(FundLiteDTO fundLite, SubscriptionFollowUp.Fund followUpFund) {

		if (fundLite != null) {
			String assetManager = fundLite.getAssetManager();
			String psi = fundLite.getFinancialAdvisor();
			String accountRoot = fundLite.getAccountRoot();
			followUpFund.setFundId(StringUtils.stripToNull(accountRoot));
			String contactPerson = getPersonName(fundLite.getSalesRep());
			followUpFund
					.setContactPerson(StringUtils.stripToNull(contactPerson));

			if (isFAS(fundLite)) {
				followUpFund.setPsi(StringUtils.stripToNull(getPersonName(psi)));
				followUpFund.setAssetManager(null);
			} else if (isFID(fundLite)) {
				followUpFund.setPsi(null);
				followUpFund.setAssetManager(StringUtils.stripToNull(getPersonName(assetManager)));
			} else {
				followUpFund.setPsi(null);
				followUpFund.setAssetManager(null);
			}

			List<String> policies = (List<String>) followUpHelper.getPoliciesOfFunds(fundLite);
			if (policies != null && !policies.isEmpty()) {
				followUpFund.setPolicyIds(policies.stream().map(policyId -> StringUtils.stripToEmpty(policyId))
						.collect(Collectors.toList()));
			}

		}

		return followUpFund;
	}
}
