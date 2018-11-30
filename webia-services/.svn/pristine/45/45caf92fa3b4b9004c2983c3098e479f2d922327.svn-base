/**
 * 
 */
package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.service.validations.appform.OtherClientRelationshipValidationService;
import lu.wealins.common.dto.webia.services.ClientFormDTO;

@Service
public class OtherClientRelationshipValidationServiceImpl implements OtherClientRelationshipValidationService {

	private boolean hasCessionRole(ClientFormDTO otherClient) {
		return otherClient.getClientRelationTp() != null && ClientRelationType.CESSION_ROLES.contains(otherClient.getClientRelationTp().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.OtherClientRelationshipValidationService#assertClientCessionPartNotNull(java.util.Collection, java.util.List)
	 */
	@Override
	public void assertClientCessionPartsNotNull(Collection<ClientFormDTO> otherClients, List<String> errors) {
		if (otherClients != null) {
			otherClients.forEach(client -> assertClientCessionPartNotNull(client, errors));
		}
	}

	@Override
	public void assertClientCessionPartsNotNullForRole(Collection<ClientFormDTO> otherClients, ClientRelationType roleType, List<String> errors) {
		otherClients.forEach(client -> {
			if (roleType != null && client != null && roleType.getRoleNumber().equals(client.getClientRelationTp())) {
				assertClientCessionPartNotNull(client, errors);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.OtherClientRelationshipValidationService#assertClientCessionPartNotNull(lu.wealins.common.dto.webia.services.ClientFormDTO,
	 * java.util.List)
	 */
	@Override
	public void assertClientCessionPartNotNull(ClientFormDTO otherClient, List<String> errors) {
		Assert.notNull(errors);

		if (otherClient != null && hasCessionRole(otherClient) && otherClient.getSplit() == null) {
			errors.add("The other client with id (" + otherClient.getClientId() + ") part is mandatory.");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.OtherClientRelationshipValidationService#assertClientCessionPartEq100(java.util.Collection, java.util.List)
	 */
	@Override
	public void assertClientCessionPartsEq100(Collection<ClientFormDTO> otherClients, List<String> errors) {
		if (otherClients != null) {
			otherClients.forEach(client -> assertClientCessionPartEq100(client, errors));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.OtherClientRelationshipValidationService#assertClientCessionPartEq100(lu.wealins.common.dto.webia.services.ClientFormDTO,
	 * java.util.List)
	 */
	@Override
	public void assertClientCessionPartEq100(ClientFormDTO otherClient, List<String> errors) {
		Assert.notNull(errors);

		if (otherClient != null && hasCessionRole(otherClient) && otherClient.getSplit() != null) {

			if (otherClient.getSplit().doubleValue() < 0) {
				errors.add("The other client with id (" + otherClient.getClientId() + ") part must not be negative.");
			} else if (otherClient.getSplit().doubleValue() > 100) {
				errors.add("The other client with id (" + otherClient.getClientId() + ") part must be equal to 100%.");
			}

		}
	}

}
