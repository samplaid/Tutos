package lu.wealins.liability.services.core.business.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.tempuri.wssupers.Exception_Exception;
import org.tempuri.wssupers.WSSUPERS;
import org.tempuri.wssupers.WssupersExport;
import org.tempuri.wssupers.WssupersImport;
import org.tempuri.wssupers.WssupersImport.ImpCallMethodCommunications;
import org.tempuri.wssupers.WssupersImport.ImpCreateModifyDatesCreateModify;
import org.tempuri.wssupers.WssupersImport.ImpGrpClc;
import org.tempuri.wssupers.WssupersImport.ImpGrpClc.Row;
import org.tempuri.wssupers.WssupersImport.ImpGrpClc.Row.ImpItmClcClientContactDetails;
import org.tempuri.wssupers.WssupersImport.ImpGrpClp;
import org.tempuri.wssupers.WssupersImport.ImpGrpClp.Row.ImpItmClpClientLinkedPersons;
import org.tempuri.wssupers.WssupersImport.ImpPonGeneralNotes;
import org.tempuri.wssupers.WssupersImport.ImpPonIdnumberGeneralNotes;
import org.tempuri.wssupers.WssupersImport.ImpValidationUsers;

import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.business.client.account.BankingAccountService;
import lu.wealins.liability.services.core.mapper.ClientContactDetailsMapper;
import lu.wealins.liability.services.core.mapper.ClientLinkedPersonMapper;
import lu.wealins.liability.services.core.mapper.ClientMapper;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.liability.services.core.persistence.repository.ClientRepository;
import lu.wealins.liability.services.core.persistence.repository.ClientRepositorySpecs;
import lu.wealins.liability.services.core.persistence.specification.ClientSearchSpecification;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.HistoricManager;
import lu.wealins.liability.services.core.validation.ClientValidator;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.ClientSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.enums.ClientType;
import lu.wealins.liability.services.ws.rest.exception.ObjectNotFoundException;
import lu.wealins.liability.services.ws.rest.exception.WssUpdateClientException;

@Service
public class ClientServiceImpl implements ClientService {
	private static final String CLIENT_ID_CANNOT_BE_NULL = "Client id cannot be null.";
	private static final String CLIENTS_CANNOT_BE_NULL = "Clients cannot be null.";
	private static final String CLIENT_CANNOT_BE_NULL = "Client cannot be null.";
	private static final String USER_NAME_CANNOT_BE_NULL = "User name cannot be null.";
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	private enum ACTION {
		CREATE, UPDATE
	}

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	private ClientContactDetailsMapper clientContactDetailsMapper;

	@Autowired
	private ClientLinkedPersonMapper clientLinkedPersonMapper;

	@Autowired
	private WSSUPERS wssupers;

	@Autowired
	private ClientValidator<ClientDTO> clientValidator;
	
	@Autowired
	private BankingAccountService bankingAccountService;

	@Autowired
	private HistoricManager historicManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientDTO getClient(Integer clientId) {
		return clientMapper.asClientDTO(clientRepository.findOne(clientId));
	}

	@Override
	public Collection<ClientDTO> getClients(Collection<Integer> ids) {
		List<ClientEntity> clientEntities = clientRepository.findAll(ids);

		return clientEntities.stream().map(x -> clientMapper.asClientDTO(x)).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientLiteDTO getClientLight(Integer clientId) {
		ClientEntity client = clientRepository.findOne(clientId);

		if (client == null) {
			throw new ObjectNotFoundException(ClientEntity.class, clientId.toString());
		}

		return clientMapper.asClientLiteDTO(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("boxing")
	@Override
	public ClientDTO create(ClientDTO client, String userName) {
		Assert.notNull(client, CLIENT_CANNOT_BE_NULL);
		Assert.notNull(userName, USER_NAME_CANNOT_BE_NULL);

		clientValidator.validate(client);

		WssupersExport resp = _internal_soap(client, ACTION.CREATE, userName);

		log.info("WSSUPERS has successfully created the client {}", client.getCliId());

		return getClient(resp.getExpCliClients().getCliId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends ClientLiteDTO> Collection<T> distinctSortClients(Collection<T> clients) {
		Assert.notNull(clients, CLIENTS_CANNOT_BE_NULL);
		return sortClients(clients).stream().collect(Collectors.toSet());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends ClientLiteDTO> Collection<T> sortClients(Collection<T> clients) {
		Assert.notNull(clients, CLIENTS_CANNOT_BE_NULL);

		List<T> sortedClients = clients.stream().collect(Collectors.toList());
		sortedClients.sort((left, right) -> left.getDisplayName().compareTo(right.getDisplayName()));
		return sortedClients;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientDTO update(ClientDTO updatedClient, String userName) {
		Assert.notNull(updatedClient, CLIENT_CANNOT_BE_NULL);
		Assert.notNull(userName, USER_NAME_CANNOT_BE_NULL);

		clientValidator.validate(updatedClient);

		_internal_soap(updatedClient, ACTION.UPDATE, userName);
		
		bankingAccountService.updateClientAccounts(updatedClient.getClientAccounts(), updatedClient.getCliId());

		update(updatedClient);

		log.info("WSSUPERS has successfully updated the client {}", updatedClient.getCliId());

		return getClient(updatedClient.getCliId());
	}

	// FIXME: workarround, becuase lissia does not update the risk cat.
	private ClientEntity update(ClientDTO clientDto) {
		Assert.notNull(clientDto, "The client to save must not be null.");

		if (clientDto.getCliId() != null) {
			ClientEntity clientEntity = clientRepository.findOne(clientDto.getCliId());
			clientEntity.setRiskCat(clientDto.getRiskCat());
			clientEntity.setGdprStartdate(clientDto.getGdprStartdate());
			clientEntity.setGdprEnddate(clientDto.getGdprEnddate());
			if (historicManager.historize(clientEntity)) {
				return clientRepository.save(clientEntity);
			} else {
				return clientEntity;
			}
		}

		throw new IllegalArgumentException("Cannot update the client with a null id.");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public SearchResult<ClientLiteDTO> searchClient(ClientSearchRequest clientSearchRequest) {
		if (clientSearchRequest == null)
			return null;

		int pageNum = (clientSearchRequest.getPageNum() == null || clientSearchRequest.getPageNum().intValue() < 1) ? 1 : clientSearchRequest.getPageNum().intValue();
		int pageSize = (clientSearchRequest.getPageSize() == null || clientSearchRequest.getPageSize().intValue() <= 1 || clientSearchRequest.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE)
				? SearchResult.DEFAULT_PAGE_SIZE
				: clientSearchRequest.getPageSize().intValue();

		Pageable pageable = new PageRequest(--pageNum, pageSize, Sort.Direction.DESC, "cliId");
		Specifications<ClientEntity> spec = Specifications.where(ClientSearchSpecification.initial());

		if (StringUtils.hasText(clientSearchRequest.getFilter())) {
			List<Specification<ClientEntity>> codeNameList = new ArrayList<>();
			if (NumberUtils.isNumber(clientSearchRequest.getFilter())) {
				codeNameList.add(ClientSearchSpecification.withCode(NumberUtils.toInt(clientSearchRequest.getFilter().trim())));
			} else {
				codeNameList.add(ClientSearchSpecification.withName("%" + clientSearchRequest.getFilter().trim() + "%"));
			}

			spec = spec.and(ClientSearchSpecification.or(codeNameList));
		}

		if (StringUtils.hasText(clientSearchRequest.getFirstName())) {
			spec = spec.and(ClientSearchSpecification.withFirstName("%" + clientSearchRequest.getFirstName().trim() + "%"));
		}

		if (StringUtils.hasText(clientSearchRequest.getDate())) {
			try {
				Date date = DateUtils.parseDate(clientSearchRequest.getDate(), new String[] { DateFormatUtils.ISO_DATE_FORMAT.getPattern() });
				spec = spec.and(ClientSearchSpecification.withBirthDay(date));
			} catch (ParseException e) {
			}
		}
		if (clientSearchRequest.getStatus() != null) {
			spec = spec.and(ClientSearchSpecification.withStatus(clientSearchRequest.getStatus()));
		}
		if (clientSearchRequest.getType() != null) {
			spec = spec.and(ClientSearchSpecification.withType(clientSearchRequest.getType()));
		}

		if (StringUtils.hasText(clientSearchRequest.getMaidenName())) {
			spec = spec.and(ClientSearchSpecification.withMaidenName("%" + clientSearchRequest.getMaidenName().trim() + "%"));
		}

		Page<ClientEntity> pageResult = clientRepository.findAll(spec, pageable);
		SearchResult<ClientLiteDTO> r = new SearchResult<ClientLiteDTO>();

		r.setPageSize(pageSize);
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.hasContent() ? pageResult.getNumber() + 1 : 1);

		if (pageResult.getContent() != null) {
			List<ClientLiteDTO> clientDtos = clientMapper.asClientLiteDTOs(pageResult.getContent());

			if (BooleanUtils.isFalse(clientSearchRequest.getIncludeDeceased())) {
				r.setContent(clientDtos.stream().filter(clientDto -> BooleanUtils.isNotTrue(clientDto.isDead())).collect(Collectors.toList()));
			} else {
				r.setContent(clientDtos);
			}

		}

		return r;
	}

	private WssupersExport _internal_soap(ClientDTO client, ACTION action, String usrId) throws WssUpdateClientException {
		WssupersImport req = new WssupersImport();
		Date now = new Date();

		// Client create or modification date
		ImpCreateModifyDatesCreateModify modify = new ImpCreateModifyDatesCreateModify();
		switch (action) {
		case CREATE:
			client.setCliId(Integer.valueOf(0));
			modify.setCreatedDate(DateFormatUtils.format(now, "yyyyMMdd"));
			modify.setCreatedTime(DateFormatUtils.format(now, "HHmmss"));
			break;
		case UPDATE:
			modify.setModifyDate(DateFormatUtils.format(now, "yyyyMMdd"));
			modify.setModifyTime(DateFormatUtils.format(now, "HHmmss"));
		}
		req.setImpCreateModifyDatesCreateModify(modify);

		// Action
		ImpCallMethodCommunications method = new ImpCallMethodCommunications();
		method.setCallFunction(action.name());
		req.setImpCallMethodCommunications(method);

		// Log
		log.info("{} client {} by user {}", action.name(), client.getCliId(), usrId);

		// Client
		req.setImpCliClients(clientMapper.asImpCliClients(client));

		// home address
		ImpGrpClc grpclc = new ImpGrpClc();
		req.setImpGrpClc(grpclc);

		Row home = new Row();
		grpclc.getRows().add(home);

		if (client.getHomeAddress() != null) {
			client.getHomeAddress().setContactType("CORRESP");

			ImpItmClcClientContactDetails impItmClcClientContactDetails = clientContactDetailsMapper.asImpItmClcClientContactDetails(client.getHomeAddress());

			home.setImpItmClcClientContactDetails(impItmClcClientContactDetails);
		}

		// Correspondence address
		Row corresp = new Row();
		grpclc.getRows().add(corresp);

		if (client.getCorrespondenceAddress() != null) {
			client.getCorrespondenceAddress().setContactType("CORRESP1");

			ImpItmClcClientContactDetails impItmClcClientContactDetails = clientContactDetailsMapper.asImpItmClcClientContactDetails(client.getCorrespondenceAddress());

			corresp.setImpItmClcClientContactDetails(impItmClcClientContactDetails);
		}

		// client note
		if (client.getClientNote() != null) {
			ImpPonGeneralNotes impPonGeneralNotes = new ImpPonGeneralNotes();
			impPonGeneralNotes.setDetails(client.getClientNote().getDetails());
			req.setImpPonGeneralNotes(impPonGeneralNotes);
		}

		// id number
		if (client.getIdNumber() != null) {

			ImpPonIdnumberGeneralNotes value = new ImpPonIdnumberGeneralNotes();
			value.setDetails(client.getIdNumber().getDetails());
			req.setImpPonIdnumberGeneralNotes(value);
		}

		// client linked persons.
		ImpGrpClp impGrpClp = new ImpGrpClp();
		req.setImpGrpClp(impGrpClp);

		setupClientLinkedPersons(client.getPersonsRepresentingCompany(), impGrpClp);
		setupClientLinkedPersons(client.getControllingPersons(), impGrpClp);

		// Set credential to access the WS
		ImpValidationUsers value = new ImpValidationUsers();
		value.setLoginId(wsLoginCredential);
		value.setPassword(wsPasswordCredential);
		req.setImpValidationUsers(value);

		// Call web service client
		try {
			WssupersExport resp = wssupers.wssuperscall(req);

			if (resp.getExpErrorMessageBrowserFields() != null
					&& resp.getExpErrorMessageBrowserFields().getErrorMessage() != null
					&& resp.getExpErrorMessageBrowserFields().getErrorMessage().trim().length() != 0) {

				throw new WssUpdateClientException(
						resp.getExpErrorMessageBrowserFields().getErrorTxt(),
						resp.getExpErrorMessageBrowserFields().getErrorMessage());
			}

			return resp;

		} catch (Exception_Exception e) {

			// Just wrap it to an interface exception
			throw new WssUpdateClientException(e);
		}
	}

	private void setupClientLinkedPersons(Collection<ClientLinkedPersonDTO> clientLinkedPersons, ImpGrpClp impGrpClp) {
		for (ClientLinkedPersonDTO clientLinkedPerson : clientLinkedPersons) {
			ImpItmClpClientLinkedPersons impItmClpClientLinkedPersons = clientLinkedPersonMapper.asImpItmClpClientLinkedPersons(clientLinkedPerson);

			org.tempuri.wssupers.WssupersImport.ImpGrpClp.Row row = new org.tempuri.wssupers.WssupersImport.ImpGrpClp.Row();
			row.setImpItmClpClientLinkedPersons(impItmClpClientLinkedPersons);
			impGrpClp.getRows().add(row);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientLiteDTO> match(String name, Date date, Long excludedId) {

		Assert.notNull(name);
		Assert.notNull(date);

		Specifications<ClientEntity> spec = Specifications
				.where(ClientRepositorySpecs.equalIgnoreCase(name)) // BugFix: use entire name as clause. Ignore the case sensitive.
				.and(ClientRepositorySpecs.withDateOfBirth(date));

		if (excludedId != null) {
			spec = spec.and(ClientRepositorySpecs.exclude(excludedId));
		}

		List<ClientEntity> list = clientRepository.findAll(spec);
		log.info("{} similar clients found by the match service.", list.size());

		return clientMapper.asClientLiteDTOs(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientLiteDTO> loadAllFiduciaries() {
		log.debug("Load all fiduciaries client");

		List<ClientEntity> elements = clientRepository.findAllFiduciaries();
		log.info("Result of fiduciaries client loading : {} found", elements.size());
		return clientMapper.asClientLiteDTOs(elements);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClientGdprConsentAccepted(Integer clientId) {
		Assert.notNull(clientId, CLIENT_ID_CANNOT_BE_NULL);
		return clientRepository.isClientGdprConsentAccepted(clientId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ClientService#isDead(lu.wealins.liability.services.core.persistence.entity.ClientEntity)
	 */
	@Override
	public boolean isDead(ClientEntity client) {
		return client != null
				&& CollectionUtils.isNotEmpty(client.getClientClaimsDetails())
				&& (client.getClientClaimsDetails().stream().anyMatch(clientClaims -> clientClaims.getDateOfDeath() != null
						&& CalendarUtils.nonNull(clientClaims.getDateOfDeath())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ClientService#isMoral(lu.wealins.common.dto.liability.services.ClientDTO)
	 */
	@Override
	public boolean isMoral(ClientDTO client) {
		Assert.notNull(client, "Cannot check the type of a null client object.");
		Assert.notNull(client.getType(), "Cannot determine the type of the client" + client.getCliId() + " because its type is null.");
		return client.getType().shortValue() == ClientType.MORAL.getType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ClientService#isPhysical(lu.wealins.common.dto.liability.services.ClientDTO)
	 */
	@Override
	public boolean isPhysical(ClientDTO client) {
		Assert.notNull(client, "Cannot check the type of a null client object.");
		Assert.notNull(client.getType(), "Cannot determine the type of the client" + client.getCliId() + " because its type is null.");
		return client.getType().shortValue() == ClientType.PHYSICAL.getType();
	}

}
