package lu.wealins.webia.services.core.predicates;

import org.apache.commons.collections4.Predicate;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;

public class ClientRelationTypePredicate implements Predicate<ClientFormEntity> {

	private ClientRelationType clientRelationType;

	public ClientRelationTypePredicate(ClientRelationType clientRelationType) {
		Assert.notNull(clientRelationType);
		this.clientRelationType = clientRelationType;
	}

	@Override
	public boolean evaluate(ClientFormEntity clientForm) {
		return clientForm.getClientRelationTp() != null && clientRelationType.getValue() == clientForm.getClientRelationTp().intValue();
	}

}
