package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.AbstractFundFormDTO;
import lu.wealins.webia.core.service.LiabilityFundService;

public abstract class AbstractFundFormService<T extends AbstractFundFormDTO> {

	@Autowired
	protected LiabilityFundService fundService;

	public boolean hasNewFid(Collection<T> fundForms) {
		return hasNewFundSubType(fundForms, FundSubType.FID);
	}

	public boolean hasNewFas(Collection<T> fundForms) {
		return hasNewFundSubType(fundForms, FundSubType.FAS);
	}

	public boolean hasFidOrFas(Collection<T> fundForms) {
		return hasFundWithPredicate(fundForms, fundService::isFIDorFAS);
	}

	public Collection<T> getFidOrFas(Collection<T> fundForms) {
		return getFundWithPredicate(fundForms, x -> fundService.isFIDorFAS(x.getFund()));
	}

	public boolean hasFEorFIC(Collection<T> fundForms) {
		return hasFundWithPredicate(fundForms, fundService::isFEorFIC);
	}

	private boolean hasNewFundSubType(Collection<T> fundForms, FundSubType fundSubType) {
		return hasFundWithPredicate(fundForms, fund -> hasFundSubType(fund, fundSubType.name()) && isNew(fund));
	}

	private boolean isNew(FundLiteDTO fund) {
		return fund != null && fund.getStatus() == 0;
	}

	private boolean hasFundSubType(FundLiteDTO fund, String fundSubType) {
		return fund != null && fundSubType.equals(fund.getFundSubType());
	}

	private boolean hasFundWithPredicate(Collection<T> fundForms, Predicate<FundLiteDTO> predicate) {
		return fundForms.stream().map(AbstractFundFormDTO::getFund).anyMatch(predicate);
	}

	private Collection<T> getFundWithPredicate(Collection<T> fundForms, Predicate<T> predicate) {
		return fundForms.stream().filter(predicate).collect(Collectors.toList());
	}

	protected abstract Collection<T> getFundForms(String policyId);

	public void enrichFunds(Collection<T> fundForms) {
		Collection<String> fundIds = fundForms.stream().map(x -> x.getFundId()).collect(Collectors.toList());
		Collection<FundLiteDTO> funds = fundService.getFunds(fundIds);
		Map<String, FundLiteDTO> fundsMap = funds.stream().collect(Collectors.toMap(x -> x.getFdsId(), x -> x));

		fundForms.forEach(fundRegistration -> fundRegistration.setFund(fundsMap.get(fundRegistration.getFundId())));
	}
}
