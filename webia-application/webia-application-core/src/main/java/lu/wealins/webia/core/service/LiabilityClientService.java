package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;

public interface LiabilityClientService {

	/**
	 * Get client according its id.
	 * 
	 * @param clientId The client id.
	 * @return The client.
	 */
	ClientDTO getClient(Integer clientId);

	Collection<ClientDTO> getClients(Collection<Integer> clientIds);

	boolean isPEP(ClientDTO client);

	boolean isRCA(ClientDTO client);

	boolean isPhysical(ClientDTO client);

	boolean isMoral(ClientDTO client);

	boolean isDap(ClientDTO client);

	boolean hasCriticalProfession(ClientDTO client);

	boolean hasCriticalActivity(ClientDTO client);

	/**
	 * GDPR client consent is accepted when the consent start date is filled in and the consent end date is empty.
	 * 
	 * @param context security content
	 * @param clientId a set of client id.
	 * @return true if the condition is met.
	 */
	boolean isClientGdprConsentAccepted(Integer clientId);

	String getClientNames(String polId);

	String getClientNames(Collection<PolicyHolderLiteDTO> policyHolders);

}
