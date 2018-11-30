package lu.wealins.liability.services.ws.rest.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.utils.SecurityContextUtils;
import lu.wealins.liability.services.ws.rest.ClientRESTService;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.ClientSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.ws.rest.exception.WssUpdateClientException;

@Component
public class ClientRESTServiceImpl implements ClientRESTService {

	@Autowired
	private ClientService clientService;
	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;
	@Autowired
	private SecurityContextUtils securityContextUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ClientRESTService#getClient(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public ClientDTO getClient(SecurityContext context, Integer clientId) {
		return clientService.getClient(clientId);
	}

	public Collection<ClientDTO> getClients(SecurityContext context, List<Integer> ids) {
		return clientService.getClients(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ClientRESTService#getClientLight(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public ClientLiteDTO getClientLight(SecurityContext context, Integer clientId) {
		return clientService.getClientLight(clientId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ClientRESTService#create(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FullClientDTO)
	 */
	@Override
	public ClientDTO create(SecurityContext context, ClientDTO client) {

		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(client);

		String userName = securityContextUtils.getPreferredUsername(context);
		return clientService.create(client, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ClientRESTService#update(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.FullClientDTO)
	 */
	@Override
	public ClientDTO update(SecurityContext context, ClientDTO client) {

		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(client);

		if (StringUtils.isEmpty(client.getCliId())) {
			throw new WssUpdateClientException("Client Id must not be empty.");
		}

		String userName = securityContextUtils.getPreferredUsername(context);
		return clientService.update(client, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ClientRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.ClientSearchRequest)
	 */
	@Override
	public SearchResult<ClientLiteDTO> search(SecurityContext context, ClientSearchRequest request) throws ParseException {
		return clientService.searchClient(request);
	}

	@Override
	public List<ClientLiteDTO> match(SecurityContext ctx,
			String name, String birthday, Long clientId) throws ParseException {

		if (name == null || birthday == null) {
			return new ArrayList<ClientLiteDTO>();
		}

		Date date = null;
		if (StringUtils.hasText(birthday)) {
			date = DateUtils.parseDate(birthday, new String[] { DateFormatUtils.ISO_DATE_FORMAT.getPattern() });
		}

		return clientService.match(name, date, clientId);

	}

	@Override
	public List<ClientLiteDTO> loadAllFiduciaries(SecurityContext ctx) {
		return clientService.loadAllFiduciaries();
	}
	
	@Override
	public boolean canClientDeathBeNotified(SecurityContext context, Integer id){
		return cliPolRelationshipService.canClientDeathBeNotified(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClientGdprConsentAccepted(SecurityContext context, Integer clientId) {
		return clientService.isClientGdprConsentAccepted(clientId);
	};

}
