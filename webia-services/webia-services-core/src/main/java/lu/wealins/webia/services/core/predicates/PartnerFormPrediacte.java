package lu.wealins.webia.services.core.predicates;

import org.apache.commons.collections4.Predicate;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;

public class PartnerFormPrediacte implements Predicate<PartnerFormEntity> {

	private static final String PARTNER_CATEGORY_CANNOT_BE_NULL = "Partner category cannot be null.";
	private String partnerCategory;

	public PartnerFormPrediacte(String partnerCategory) {
		Assert.notNull(partnerCategory, PARTNER_CATEGORY_CANNOT_BE_NULL);
		this.partnerCategory = partnerCategory;
	}

	@Override
	public boolean evaluate(PartnerFormEntity partnerForm) {
		return partnerForm != null && partnerCategory.equals(partnerForm.getPartnerCategory());
	}

}
