package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.ClientClaimsDetailDTO;

public interface ClientClaimsDetailService {

	/**
	 * Get client.
	 * 
	 * @param clientId The client id.
	 * @return The client.
	 */
	Collection<ClientClaimsDetailDTO> getClientClaimsDetails(Integer clientId);

	/**
	 * Create or update a client claim detail with the user name.
	 * 
	 * @param clientClaimsDetail The client claim detail.
	 * @param userName The user name.
	 * @return The client claim detail saved.
	 */
	ClientClaimsDetailDTO save(ClientClaimsDetailDTO clientClaimsDetail, String userName);

}
