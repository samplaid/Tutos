package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.enums.ClientType;
import lu.wealins.webia.core.service.LiabilityClientService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityClientServiceImpl implements LiabilityClientService {

	private final Logger log = LoggerFactory.getLogger(LiabilityClientServiceImpl.class);
	private static final String EMPTY_STRING = "";
	private static final String FT = "FT";
	private static final String CLIENT_CANNOT_BE_NULL = "Client cannot be null.";
	private static final String CLIENT_ID_CANNOT_BE_NULL = "Client id cannot be null.";
	private static final String LIABILITY_CLIENT = "liability/client/";
	private static final String DAP_ENABLED = "1";
	private static final int CLIENTS_MAX_LENGTH = 50;

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityPolicyService policyService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityClientService#getClient(java.lang.Integer)
	 */
	@Override
	public ClientDTO getClient(Integer clientId) {
		return restClientUtils.get(LIABILITY_CLIENT, clientId.toString(), ClientDTO.class);
	}

	@Override
	public Collection<ClientDTO> getClients(Collection<Integer> clientIds) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();

		clientIds.forEach(x -> queryParams.add("ids", x));

		return restClientUtils.get(LIABILITY_CLIENT, EMPTY_STRING, queryParams, new GenericType<Collection<ClientDTO>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientService#isPEP(lu.wealins.webia.ws.rest.dto.ClientDTO)
	 */
	@Override
	public boolean isPEP(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		// PEP is not defined on moral person.
		if (isMoral(client)) {
			return false;
		}

		return client.getPoliticallyExposedPerson() != null && client.getPoliticallyExposedPerson().trim().toUpperCase().indexOf("PEP YES") >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientService#isRCA(lu.wealins.webia.ws.rest.dto.ClientDTO)
	 */
	@Override
	public boolean isRCA(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		// RCA is not defined on moral person.
		if (isMoral(client)) {
			return false;
		}

		return client.getRelativeCloseAssoc() != null && "Y".equalsIgnoreCase(client.getRelativeCloseAssoc());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientService#isPhysical(lu.wealins.webia.ws.rest.dto.ClientDTO)
	 */
	@Override
	public boolean isPhysical(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		return client.getType() != null && client.getType().intValue() == ClientType.PHYSICAL.getType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientService#hasCriticalProfession(lu.wealins.webia.ws.rest.dto.ClientDTO)
	 */
	@Override
	public boolean hasCriticalProfession(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		if (StringUtils.isBlank(client.getProfession())) {
			return false;
		}

		String profession = client.getProfession();

		try {
			Integer id = new Integer(profession.replaceAll(FT, EMPTY_STRING));
			return id.intValue() < 100;
		} catch (NumberFormatException e) {
			log.error("Client " + client.getCliId() + " has a bad format for the profession id " + profession + " (it should be start with FT).", e);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientService#hasCriticalActivity(lu.wealins.webia.ws.rest.dto.ClientDTO)
	 */
	@Override
	public boolean hasCriticalActivity(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		Short activityRiskCat = client.getActivityRiskCat();
		if (activityRiskCat == null) {
			return false;
		}

		return activityRiskCat.intValue() < 100;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.ClientService#isMoral(lu.wealins.webia.ws.rest.dto.ClientDTO)
	 */
	@Override
	public boolean isMoral(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		return client.getType() != null && client.getType().intValue() == ClientType.MORAL.getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClientGdprConsentAccepted(Integer clientId) {
		Assert.notNull(clientId, CLIENT_ID_CANNOT_BE_NULL);
		MultivaluedMap<String, Object> queryParam = new MultivaluedHashMap<>();
		queryParam.add("clientId", clientId);
		return restClientUtils.get(LIABILITY_CLIENT, "gdprConsentAccepted", queryParam, Boolean.class);
	}

	@Override
	public boolean isDap(ClientDTO client) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);

		return DAP_ENABLED.equals(client.getDap());
	}

	@Override
	public String getClientNames(String polId) {
		if (StringUtils.isEmpty(polId)) {
			return null;
		}

		PolicyLightDTO policyLightDTO = policyService.getPolicyLight(polId);

		if (policyLightDTO == null) {
			throw new IllegalStateException("Unknown policy " + polId + ".");
		}

		return getClientNames(policyLightDTO.getPolicyHolders());
	}

	@Override
	public String getClientNames(Collection<PolicyHolderLiteDTO> policyHolders) {
		if (CollectionUtils.isEmpty(policyHolders)) {
			return null;
		}

		// retrive and save the client(s), i.e. the policy holder(s)
		String clients = policyHolders.stream().map(phlDTO -> phlDTO.getFirstName() + " " + phlDTO.getName()).collect(Collectors.joining(","));
		return StringUtils.abbreviate(clients, CLIENTS_MAX_LENGTH);
	}
}
