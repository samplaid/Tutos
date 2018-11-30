package lu.wealins.liability.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.tempuri.wssupdfds.WssupdfdsImport.ImpFdsFunds;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.FundNameService;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.mapper.factory.FundFactory;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.repository.FundPriceRepository;
import lu.wealins.liability.services.core.utils.PrimitiveShortToBoolean;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { FundFactory.class, AgentMapper.class, PrimitiveShortToBoolean.class, StringToTrimString.class })
public abstract class FundMapper {

	@Autowired
	private PolicyService policyService;
	@Autowired
	private FundNameService fundNameService;
	@Autowired
	private FundPriceRepository fundPriceRepository;
	@Autowired
	private FundTransactionService fundTransactionService;
	@Autowired
	private AgentService agentService;

	public abstract void asFundEntity(FundDTO in, @MappingTarget FundEntity out);

	@BeanMapping(resultType = FundDTO.class)
	public abstract FundDTO asFundDTO(FundEntity in);

	public List<FundDTO> asFundDTOs(List<FundEntity> in) {
		List<FundDTO> funds = new ArrayList<>();

		in.forEach(x -> funds.add(asFundDTO(x)));

		return funds;
	}

	public Collection<FundLiteDTO> asFundLiteDTOs(Collection<FundEntity> in) {
		List<FundLiteDTO> funds = new ArrayList<>();

		in.forEach(x -> funds.add(asFundLiteDTO(x)));

		return funds;
	}

	@BeanMapping(resultType = FundLiteDTO.class)
	public abstract FundLiteDTO asFundLiteDTO(FundEntity in);

	@Mappings({
			@Mapping(source = "cutOffTime", target = "cutOffTime", dateFormat = "hhmmss"),
			@Mapping(source = "pricingTime", target = "pricingTime", dateFormat = "hhmmss"),
			@Mapping(source = "riskProfileDate", target = "riskProfileDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "poaDate", target = "poaDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "pocDate", target = "pocDate", dateFormat = "yyyyMMdd"),
	})
	public abstract ImpFdsFunds asFundDTO(FundDTO in);

	@AfterMapping
	public void asImpFdsFunds(FundDTO in, @MappingTarget ImpFdsFunds target) {
		target.setExAllInFees(BooleanUtils.toString(in.getExAllInFees(), "1", "0", null));
	}

	@AfterMapping
	public FundDTO afterEntityMapping(FundEntity in, @MappingTarget FundDTO target) {
		setupFund(in, target);
		// normalize the fund name to display
		target.setHasTransaction(Boolean.valueOf(fundTransactionService.countTransactions(target.getFdsId()) > 0));

		return target;
	}

	@AfterMapping
	public FundLiteDTO afterEntityMapping(FundEntity in, @MappingTarget FundLiteDTO target) {
		setupFund(in, target);

		return target;
	}

	public void setupFund(FundEntity in, FundLiteDTO target) {
		// determine the fund pool
		int count = policyService.countPolicies(target.getFdsId());
		target.setPool(Boolean.valueOf(count > 1));
		target.setDisplayName(fundNameService.generate(target));
		int linkedPriceCount = fundPriceRepository.countLinkedFundPrice(in.getFdsId());
		target.setIsValorized(Boolean.valueOf(linkedPriceCount > 0));

		if (!StringUtils.isEmpty(in.getFinancialAdvisor()) && !in.getFinancialAdvisor().trim().isEmpty()) {
			AgentDTO financialAdvisor = agentService.getAgent(in.getFinancialAdvisor());
			if (financialAdvisor != null) {
				target.setFinancialAdvisorCategory(financialAdvisor.getCategory());
			}
		}
	}
}
