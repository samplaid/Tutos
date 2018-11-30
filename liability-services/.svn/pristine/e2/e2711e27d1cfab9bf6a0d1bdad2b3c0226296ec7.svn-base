package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.ClientSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

public interface ClientService {

	/**
	 * Get client.
	 * 
	 * @param clientId The client id.
	 * @return The client.
	 */
	ClientDTO getClient(Integer clientId);

	Collection<ClientDTO> getClients(Collection<Integer> ids);

	/**
	 * Get client (light).
	 * 
	 * @param clientId The client id.
	 * @return The client.
	 */
	ClientLiteDTO getClientLight(Integer clientId);

	/**
	 * Create a new client with the WSSUPERS soap service and the user name.
	 * 
	 * @param client The client.
	 * @param userName The user name.
	 * @return The created client.
	 */
	ClientDTO create(ClientDTO client, String userName);

	/**
	 * Update an existing client with the WSSUPERS soap service and the user name.
	 * 
	 * Client to be updated must exist in the database, otherwise the method will throw an exception. All the values of the client will be replaced with the data in the DTO parameter.
	 * 
	 * @param client The client.
	 * @param userName The user name.
	 * @return The updated client.
	 */
	ClientDTO update(ClientDTO client, String userName);

	/**
	 * Returns one page of the client's search results. The search can be done by the client's name, first name, the client id or/and the client's birth date. And two search parameters can not be both
	 * empty.
	 * 
	 * The page parameter is zero indexed, the first page has the number 0.
	 * 
	 * @param search
	 * @param birthDate
	 * @param page
	 * @param size
	 * @return
	 */
	public SearchResult<ClientLiteDTO> searchClient(ClientSearchRequest clientSearchRequest);

	/**
	 * Returns the clients who match the specified criteria.
	 * <ul>
	 * <li>Last name begins with the first letter of the name in arguments</li>
	 * <li>Date of Birth = date in argument if given</li>
	 * <li>Client id <> excludeId</li>
	 * </ul>
	 * 
	 * @param firstName
	 * @param lastName
	 * @param date
	 * @param clientId
	 * @return
	 */
	List<ClientLiteDTO> match(String name, Date date, Long excludedId);

	/**
	 * Load all client who are fiduciaries
	 * 
	 * @return the client list
	 */
	List<ClientLiteDTO> loadAllFiduciaries();

	/**
	 * GDPR client consent is accepted when the consent start date is filled in and the consent end date is empty.
	 * 
	 * @param clientId a set of client id.
	 * @return true if the condition is met.
	 */
	boolean isClientGdprConsentAccepted(Integer clientId);

	/**
	 * This method check if the client is dead.
	 * 
	 * @param client the client to check
	 * @return True if the condition is met
	 */
	boolean isDead(ClientEntity client);

	/**
	 * Indicates if the client type is a moral.
	 * 
	 * @param client the client to check
	 * @return true if the condition is met.
	 */
	boolean isMoral(ClientDTO client);

	/**
	 * Indicates if the client type is a physical.
	 * 
	 * @param client the client to check
	 * @return true if the condition is met.
	 */
	boolean isPhysical(ClientDTO client);

	/**
	 * Sort and distinct the clients.
	 * 
	 * @param clients a set of client
	 * @return The sorted and distinct clients.
	 * @see #sortClients(Collection) sortClients
	 */
	<T extends ClientLiteDTO> Collection<T> distinctSortClients(Collection<T> clients);

	/**
	 * Sort the clients.
	 * 
	 * @param clients a set of client
	 * @return The sorted clients.
	 */
	<T extends ClientLiteDTO> Collection<T> sortClients(Collection<T> clients);
	
}
