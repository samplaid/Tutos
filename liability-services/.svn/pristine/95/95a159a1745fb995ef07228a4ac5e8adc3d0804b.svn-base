package lu.wealins.liability.services.core.persistence.specification;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
public final class FundSpecifications {

	private FundSpecifications() {

	}

	public static Specification<FundEntity> initial() {
		return new GenericSpecification<FundEntity>().initial("fdsId");
	}

	public static Specification<FundEntity> withFundSubType(String fundSubType) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(fundSubType, "fundSubType");
	}

	public static Specification<FundEntity> withFundTypeIn(Object... values) {
		return new GenericSpecification<FundEntity>().withInCriteria(Arrays.asList(values), "fundType");
	}

	public static Specification<FundEntity> withCode(String fdsId) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(fdsId, "fdsId");
	}

	public static Specification<FundEntity> withStatus(Integer status) {
		return new GenericSpecification<FundEntity>().withEqualCriteria(status, "status");
	}

	public static Specification<FundEntity> withStatusNotEq(Integer status) {
		return new GenericSpecification<FundEntity>().withNotEqualCriteria(status, "status");
	}

	public static Specification<FundEntity> withIsinCode(String isinCode) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(isinCode, "isinCode");
	}

	public static Specification<FundEntity> withRoot(String accountRoot) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(accountRoot, "accountRoot");
	}

	public static Specification<FundEntity> withIban(String iban) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(iban, "iban");
	}

	public static Specification<FundEntity> withDepositBank(String depositBank) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(depositBank, "depositBank");
	}

	public static Specification<FundEntity> withAssetManager(String assetManager) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(assetManager, "assetManager");
	}

	public static Specification<FundEntity> withName(String name) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(name, "name");
	}

	public static Specification<FundEntity> withBroker(String brokerId) {
		return new GenericSpecification<FundEntity>().withEqualCriteria(brokerId, "broker");
	}

	public static Specification<FundEntity> or(List<Specification<FundEntity>> others) {
		return new GenericSpecification<FundEntity>().or(others);
	}

	public static Specification<FundEntity> and(List<Specification<FundEntity>> others) {
		return new GenericSpecification<FundEntity>().and(others);
	}

	public static Specification<FundEntity> withFinancialAdvisor(String financialAdvisor) {
		return new GenericSpecification<FundEntity>().withContainsCriteria(financialAdvisor, "financialAdvisor");
	}

	public static Specification<FundEntity> withNoBroker() {
		return new GenericSpecification<FundEntity>().withEmptyValueCriteria("broker");
	}

}
