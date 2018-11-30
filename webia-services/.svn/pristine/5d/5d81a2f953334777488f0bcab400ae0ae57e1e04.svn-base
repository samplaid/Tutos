package lu.wealins.webia.services.core.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import lu.wealins.common.persistence.GenericSpecification;
import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;

public final class PartnerFormSpecifications {

	private PartnerFormSpecifications() {

	}

	public static Specification<PartnerFormEntity> initial() {
		return new GenericSpecification<PartnerFormEntity>().initial("partnerFormId");
	}

	public static Specification<PartnerFormEntity> withFormId(final Integer formId) {
		return new GenericSpecification<PartnerFormEntity>().withEqualCriteria(formId, "formId");
	}

	public static Specification<PartnerFormEntity> withPartnerCategory(final String partnerCategory) {
		return new GenericSpecification<PartnerFormEntity>().withEqualCriteria(partnerCategory, "partnerCategory");
	}

}
