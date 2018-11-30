package lu.wealins.webia.services.core.predicates;

import org.apache.commons.collections4.Predicate;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;

public class ClientRelationTypeAndClientIdPredicate implements Predicate<ClientFormEntity> {

	private ClientRelationType clientRelationType;
	private int clientId;

	public ClientRelationTypeAndClientIdPredicate(ClientRelationType clientRelationType, int clientId) {
		Assert.notNull(clientRelationType);

		this.clientRelationType = clientRelationType;
		this.clientId = clientId;
	}

	@Override
	public boolean evaluate(ClientFormEntity client) {
		if (client.getClientRelationTp() == null || client.getClientId() == null) {
			return false;
		}
		return clientRelationType.getValue() == client.getClientRelationTp().intValue() && clientId == client.getClientId().intValue();
	}

}
