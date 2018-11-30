package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.tempuri.wssupdagt.Exception_Exception;
import org.tempuri.wssupdagt.WSSUPDAGT;
import org.tempuri.wssupdagt.WssupdagtExport;
import org.tempuri.wssupdagt.WssupdagtImport;
import org.tempuri.wssupdagt.WssupdagtImport.ImpAgtAgents;
import org.tempuri.wssupdagt.WssupdagtImport.ImpCallMethodCommunications;
import org.tempuri.wssupdagt.WssupdagtImport.ImpCreateModifyDatesAgents;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAco;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAco.Row;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAco.Row.ImpItmAcoAgentContacts;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgb;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgb.Row.ImpItmAgbAgentBankAccounts;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgh;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgh.Row.ImpItmAghAgentHierarchies;
import org.tempuri.wssupdagt.WssupdagtImport.ImpUseridUsers;
import org.tempuri.wssupdagt.WssupdagtImport.ImpValidationUsers;

import lu.wealins.common.dto.liability.services.AgentBankAccountDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentHierarchyDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.PaymentMethodsDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.AssetManagerStrategyService;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.exceptions.AgentCreationException;
import lu.wealins.liability.services.core.business.exceptions.ReportExceptionHelper;
import lu.wealins.liability.services.core.mapper.AgentBankAccountMapper;
import lu.wealins.liability.services.core.mapper.AgentContactMapper;
import lu.wealins.liability.services.core.mapper.AgentHierarchyMapper;
import lu.wealins.liability.services.core.mapper.AgentMapper;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.persistence.repository.AgentContactRepository;
import lu.wealins.liability.services.core.persistence.repository.AgentHierarchyRepository;
import lu.wealins.liability.services.core.persistence.repository.AgentRepository;
import lu.wealins.liability.services.core.persistence.repository.PatternAccountRootRepository;
import lu.wealins.liability.services.core.persistence.specification.AgentSpecifications;
import lu.wealins.liability.services.core.utils.SecurityContextUtils;
import lu.wealins.liability.services.core.validation.AgentContactValidationService;
import lu.wealins.liability.services.core.validation.AgentValidationService;
import lu.wealins.liability.services.ws.rest.exception.ObjectNotFoundException;
import lu.wealins.liability.services.ws.rest.exception.WssUpdateAgentException;

/**
 * @author XQV89
 *
 */
@Service
public class AgentServiceImpl implements AgentService {

	private static final String FUND_ID_CANNOT_BE_NULL = "Fund id cannot be null.";
	private static final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);
	private static final String AGENT_CANNOT_BE_NULL = "Agent cannot be null.";


	/**
	 * The value of this constant is <b>{@value}</b>. <br>
	 * <br>
	 * <b>Note:</b>The default SQL query parameter is limited to <b>2100</b>
	 */
	private static final int SQL_QUERY_LIMIT = 1000;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private AssetManagerStrategyService assetManagerStrategyService;

	@Autowired
	private AgentContactRepository agentContactRepository;

	@Autowired
	private AgentHierarchyRepository agentHierarchyRepository;

	@Autowired
	private AgentMapper agentMapper;

	@Autowired
	private AgentContactMapper agentContactMapper;

	@Autowired
	private AgentHierarchyMapper agentHierarchyMapper;

	@Autowired
	private AgentBankAccountMapper agentBankAccountMapper;

	@Autowired
	private AgentContactValidationService agentContactValidationService;

	@Autowired
	private AgentValidationService agentValidationService;

	@Autowired
	private SecurityContextUtils securityContextUtils;

	@Autowired
	private WSSUPDAGT wssupdagt;

	@Autowired
	private PatternAccountRootRepository patternAccountRootRepository;

	@Autowired
	private FundService fundService;

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	private enum ACTION {
		CREATE, UPDATE, ADDAGB, ADDAGH, ADDACO
	}

	private boolean isAssetManager(AgentDTO agent) {
		return (agent != null && AgentCategory.ASSET_MANAGER.getCategory().equals(agent.getCategory()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#create(lu.wealins.common.dto.liability.services.AgentDTO, java.lang.String)
	 */
	@Override
	public AgentDTO create(AgentDTO newAgent) {
		return saveAgent(newAgent, (agent) -> {
			WssupdagtExport resp = _internal_soap(agent, ACTION.CREATE);
			if (resp != null && resp.getExpAgtAgents() != null) {
				log.info("WSSUPDAGT has successfully created the agent {}", resp.getExpAgtAgents().getAgtId());
				return resp;
			}
			return null;
		});
	}

	@Override
	public AgentDTO update(AgentDTO updatedAgent) {
		return saveAgent(updatedAgent, (agent) -> _internal_soap(agent, ACTION.UPDATE));
	}

	private AgentDTO saveAgent(AgentDTO agent, Function<AgentDTO, WssupdagtExport> saveFunction) {
		Assert.notNull(agent, AGENT_CANNOT_BE_NULL);
		validateAgent(agent);

		WssupdagtExport ret = saveFunction.apply(agent);

		if (ret != null && ret.getExpAgtAgents() != null && StringUtils.isNotBlank(ret.getExpAgtAgents().getAgtId())) {
			log.info("WSSUPDAGT has successfully updated the agent {}", ret.getExpAgtAgents().getAgtId());

			if (isAssetManager(agent)) {
				assetManagerStrategyService.bulkSaveOrUpdate(agent.getAssetManagerStrategies(), ret.getExpAgtAgents().getAgtId());
			}

			AgentEntity loadedAgent = agentRepository.findOne(StringUtils.trimToEmpty(ret.getExpAgtAgents().getAgtId()));

			if (Objects.nonNull(loadedAgent)) {
				loadedAgent.setCentralizedCommunication(agent.getCentralizedCommunication());
				loadedAgent.setStatementByEmail(agent.getStatementByEmail());
				agentRepository.save(loadedAgent);
			}

			return getAgent(ret.getExpAgtAgents().getAgtId().trim());
		}

		return agent;
	}

	@Override
	public Collection<AgentLightDTO> getAgentWithPartnerID(String search) {
		Specifications<AgentEntity> specifs = createSearchSpecificationForPartnerID(search);
		List<AgentEntity> list = agentRepository.findAll(specifs);

		return agentMapper.asAgentLightDTOs(list);
	}

	@Override
	public AgentDTO addAgentHierarchy(AgentDTO masterBroker) {
		return saveAgent(masterBroker, (agentArg) -> _internal_soap(agentArg, ACTION.ADDAGH));
	}

	@Override
	public AgentDTO addAgentContact(AgentDTO agent) {
		return saveAgent(agent, (agentArg) -> {
			if (CollectionUtils.isEmpty(agentArg.getAgentContacts())) {
				return null;
			}

			agentContactValidationService.validateContactFunction(agentArg.getAgtId(), agentArg.getAgentContacts());
			return _internal_soap(agentArg, ACTION.ADDACO);
		});
	}

	@Override
	public AgentDTO addBankAccount(AgentDTO agent) {
		return saveAgent(agent, (agentArg) -> _internal_soap(agentArg, ACTION.ADDAGB));
	}

	private void validateAgent(AgentDTO agent) {
		List<String> errors = new ArrayList<>();

		if (StringUtils.isEmpty(agent.getName())) {
			errors.add("The name is mandatory.");
		}

		errors.addAll(agentValidationService.validateOnlyOneActiveCpsContact(agent));
		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, AgentCreationException.class);
	}

	private WssupdagtExport _internal_soap(AgentDTO agent, ACTION action) throws WssUpdateAgentException {
		WssupdagtImport req = new WssupdagtImport();
		Date now = new Date();

		// Agent create or modification date
		ImpCreateModifyDatesAgents modify = new ImpCreateModifyDatesAgents();
		switch (action) {
		case CREATE:
			// agent.setAgtId("");
			modify.setCreatedDate(DateFormatUtils.format(now, "yyyyMMdd"));
			modify.setCreatedTime(DateFormatUtils.format(now, "HHmmss"));
			break;
		case UPDATE:
			modify.setModifyDate(DateFormatUtils.format(now, "yyyyMMdd"));
			modify.setModifyTime(DateFormatUtils.format(now, "HHmmss"));

			if (CollectionUtils.isNotEmpty(agent.getAgentHierarchies())) {
				Supplier<Stream<AgentHierarchyDTO>> streamSupplier = () -> agent.getAgentHierarchies().stream()
						.filter(agentHierarchy -> agentHierarchy != null && agentHierarchy.getAghId() != null && agentHierarchy.isUpdated());
				if (streamSupplier.get().count() > 0) {
					List<AgentHierarchyDTO> agentHierarchies = streamSupplier.get().collect(Collectors.toList());
					agent.setAgentHierarchies(agentHierarchies);
					mapAgentHierarchyToWsInput(agentHierarchies, req);
				}
			}
			if (CollectionUtils.isNotEmpty(agent.getAgentContacts())) {
				Supplier<Stream<AgentContactLiteDTO>> streamSupplier = () -> agent.getAgentContacts().stream().filter(agentContact -> agentContact != null
						&& agentContact.getAgcId() != null
						&& agentContact.getContact() != null
						&& !StringUtils.isEmpty(agentContact.getContact().getAgtId())
						&& !StringUtils.isEmpty(agentContact.getAgentId())
						&& !StringUtils.isEmpty(agentContact.getContactFunction()));
				if (streamSupplier.get().count() > 0) {
					agent.setAgentContacts(streamSupplier.get().collect(Collectors.toList()));
					mapAgentContactToWsInput(agent, req);
				}
			}

			mapAgentBankAccountToWsInput(new ArrayList<>(agent.getBankAccounts()), req);
			break;
		case ADDACO:
			// contact Agents
			if (CollectionUtils.isNotEmpty(agent.getAgentContacts())) {
				Supplier<Stream<AgentContactLiteDTO>> streamSupplier = () -> agent.getAgentContacts().stream().filter(agentContact -> agentContact != null
						&& agentContact.getAgcId() == null
						&& agentContact.getContact() != null
						&& !StringUtils.isEmpty(agentContact.getContact().getAgtId())
						&& !StringUtils.isEmpty(agentContact.getAgentId())
						&& !StringUtils.isEmpty(agentContact.getContactFunction()));
				if (streamSupplier.get().count() > 0) {
					agent.setAgentContacts(streamSupplier.get().collect(Collectors.toList()));
					mapAgentContactToWsInput(agent, req);
				}
			}
			break;
		case ADDAGB:
			// agent bank accounts
			mapAgentBankAccountToWsInput(new ArrayList<>(agent.getBankAccounts()), req);
			break;
		case ADDAGH:
			// agent hierarchies
			if (CollectionUtils.isNotEmpty(agent.getAgentHierarchies())) {
				Supplier<Stream<AgentHierarchyDTO>> streamSupplier = () -> agent.getAgentHierarchies().stream()
						.filter(agentHierarchy -> agentHierarchy != null && agentHierarchy.getAghId() == null && agentHierarchy.isUpdated());
				if (streamSupplier.get().count() > 0) {
					List<AgentHierarchyDTO> agentHierarchies = streamSupplier.get().collect(Collectors.toList());
					agent.setAgentHierarchies(streamSupplier.get().collect(Collectors.toList()));
					mapAgentHierarchyToWsInput(agentHierarchies, req);
				}
			}
			break;
		}

		req.setImpCreateModifyDatesAgents(modify);

		// Action
		ImpCallMethodCommunications method = new ImpCallMethodCommunications();
		method.setCallFunction(action.name());
		req.setImpCallMethodCommunications(method);

		String usrId;
		if (agent.getModifyBy() != null && !agent.getModifyBy().isEmpty()) {
			usrId = agent.getModifyBy();
		} else if (agent.getCreatedBy() != null && !agent.getCreatedBy().isEmpty()) {
			usrId = agent.getCreatedBy();
		} else {
			usrId = securityContextUtils.getPreferredUsername();
		}

		// Log
		log.info("{} agent {} by user {}", action.name(), agent.getAgtId(), usrId);

		// set the id of the user who trigger this action.
		ImpUseridUsers user = new ImpUseridUsers();
		user.setUsrId(usrId);
		req.setImpUseridUsers(user);

		// Agent
		ImpAgtAgents impAgtAgents = agentMapper.asImpAgtAgents(agent);
		impAgtAgents.setBranch("1"); // only one branch in the application call Lissi
		req.setImpAgtAgents(impAgtAgents);

		// Set credential to access the WS
		ImpValidationUsers value = new ImpValidationUsers();
		value.setLoginId(wsLoginCredential);
		value.setPassword(wsPasswordCredential);
		req.setImpValidationUsers(value);

		// Call web service agent
		try {
			WssupdagtExport resp = wssupdagt.wssupdagtcall(req);

			if (resp.getExpErrormessageBrowserFields() != null
					&& resp.getExpErrormessageBrowserFields().getErrorMessage() != null
					&& resp.getExpErrormessageBrowserFields().getErrorMessage().trim().length() != 0) {

				throw new WssUpdateAgentException(
						resp.getExpErrormessageBrowserFields().getErrorTxt(),
						resp.getExpErrormessageBrowserFields().getErrorMessage());
			}

			return resp;

		} catch (Exception_Exception e) {

			// Just wrap it to an interface exception
			throw new WssUpdateAgentException(e);
		}

	}

	private void mapAgentContactToWsInput(AgentDTO agent, WssupdagtImport req) {
		Collection<ImpItmAcoAgentContacts> agentContacts = agentContactMapper.asImpGrpAgentContacts(agent.getAgentContacts());
		ImpGrpAco agco = new ImpGrpAco();
		agentContacts.forEach(agc -> {
			Row newRow = new Row();
			newRow.setImpItmAcoAgentContacts(agc);
			agco.getRows().add(newRow);
		});
		req.setImpGrpAco(agco);
	}

	private void mapAgentHierarchyToWsInput(List<AgentHierarchyDTO> agentHierarchies, WssupdagtImport req) {
		Collection<ImpItmAghAgentHierarchies> agentHierarchyDTOs = agentHierarchyMapper.asImpGrpAgentHierarchies(agentHierarchies);
		ImpGrpAgh groupAgh = new ImpGrpAgh();
		agentHierarchyDTOs.forEach(agentHierarchy -> {
			org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgh.Row newRow = new org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgh.Row();
			newRow.setImpItmAghAgentHierarchies(agentHierarchy);
			groupAgh.getRows().add(newRow);
		});
		req.setImpGrpAgh(groupAgh);
	}

	private void mapAgentBankAccountToWsInput(List<AgentBankAccountDTO> bankAccounts, WssupdagtImport req) {
		Collection<ImpItmAgbAgentBankAccounts> agentBankAccountDTOs = agentBankAccountMapper.asImpGrpAgentBankAccounts(bankAccounts);
		ImpGrpAgb groupAgb = new ImpGrpAgb();
		agentBankAccountDTOs.forEach(agentBankAccount -> {
			org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgb.Row newRow = new org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgb.Row();
			newRow.setImpItmAgbAgentBankAccounts(agentBankAccount);
			groupAgb.getRows().add(newRow);
		});
		req.setImpGrpAgb(groupAgb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getAgent(java.lang.String)
	 */
	@Override
	public AgentDTO getAgent(String agtId) {
		String agtIdRightPad = agtId;
		// We need to rightpad the id otherwise the collection in the entity won't be built.
		if (agtId != null && !"0".equals(agtId.trim())) {
			agtIdRightPad = org.apache.commons.lang.StringUtils.rightPad(agtId, 8, ' ');
		}

		AgentEntity agent = agentRepository.findOne(agtIdRightPad);
		if (agent == null) {
			throw new ObjectNotFoundException(AgentEntity.class, agtId);
		}

		return agentMapper.asAgentDTO(agent);
	}

	@Override
	public Collection<AgentDTO> getAgents(Collection<String> agtIds) {
		return agentMapper.asAgentDTOs(agentRepository.findAll(agtIds));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getAgent(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public SearchResult<AgentLightDTO> getAgent(String search, List<String> categories, Integer status, int page, int size) {
		Specifications<AgentEntity> specifs = createSearchSpecification(search, categories, status);
		Pageable pagable = new PageRequest(page, size, Sort.Direction.ASC, "name", "agtId");
		Page<AgentEntity> pageResult = agentRepository.findAll(specifs, pagable);

		return getAgentLightSearchResult(size, pageResult);
	}

	private SearchResult<AgentLightDTO> getAgentLightSearchResult(int size, Page<AgentEntity> pageResult) {
		SearchResult<AgentLightDTO> r = new SearchResult<AgentLightDTO>();
		r.setPageSize(size);
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.getNumber() + 1);
		r.setContent(agentMapper.asAgentLightDTOs(pageResult.getContent()));
		return r;
	}

	private SearchResult<AgentDTO> getAgentSearchResult(int size, Page<AgentEntity> pageResult) {
		SearchResult<AgentDTO> r = new SearchResult<AgentDTO>();
		r.setPageSize(size);
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.getNumber() + 1);
		r.setContent(agentMapper.asAgentDTOs(pageResult.getContent()));
		return r;
	}

	private Specifications<AgentEntity> createSearchSpecification(String search, List<String> categories, Integer status) {
		Specifications<AgentEntity> specifs = Specifications.where(AgentSpecifications.initial());
		if (StringUtils.isNotBlank(search)) {
			specifs = specifs.and(AgentSpecifications.withCodeOrName(search));
		}
		if (CollectionUtils.isNotEmpty(categories)) {
			specifs = specifs.and(AgentSpecifications.withCategories(categories));
		}
		if (status != null) {
			specifs = specifs.and(AgentSpecifications.withStatus(status));
		}
		return specifs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getSalesManager(java.lang.String)
	 */
	@Override
	public AgentDTO getSalesManager(String fdsId) {
		Assert.notNull(fdsId, FUND_ID_CANNOT_BE_NULL);
		return agentMapper.asAgentDTO(agentRepository.findSalesManager(fdsId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getSubBroker(java.lang.String)
	 */
	@Override
	public Collection<AgentLightDTO> getSubBroker(String masterBrokerId) {
		return agentMapper.asAgentLightDTOs(agentHierarchyRepository.findActiveSubBroker(masterBrokerId));
	}

	@Override
	// TODO : move this to a higher level service.
	public Collection<AgentLightDTO> getCommunicationAgents(String brokerId, String subBrokerId, String policyId) {
		Assert.notNull(policyId, "The policy id can't be null");

		List<AgentEntity> communicationAgents = new ArrayList<>();

		// retrieve the broker (if specified)
		if (brokerId != null) {
			communicationAgents.add(agentRepository.findByAgtId(brokerId));
		}

		// retrieve the sub broker (if specified)
		if (subBrokerId != null) {
			communicationAgents.add(agentRepository.findByAgtId(subBrokerId));
		}

		// retrieve the asset manager and deposit banks corresponding to the funds of the policy (is specified)
		if (policyId != null) {
			fundService.getInvestedFunds(policyId).stream().forEach(fund -> {
				if (!StringUtils.isEmpty(fund.getAssetManager())) {
					communicationAgents.add(agentRepository.getOne(fund.getAssetManager()));
				}
				if (!StringUtils.isEmpty(fund.getDepositBank())) {
					communicationAgents.add(agentRepository.getOne(fund.getDepositBank()));
				}
			});
		}

		return agentMapper.asAgentLightDTOs(communicationAgents).stream().distinct().collect(Collectors.toList());
	}

	@Override
	public Collection<AgentLightDTO> getAgent(String search, List<String> categories, Integer status) {
		List<AgentEntity> result = agentRepository.findAll(createSearchSpecification(search, categories, status));
		return agentMapper.asAgentLightDTOs(result);
	}

	@Override
	public AgentContactLiteDTO getAgentContact(Integer agcId) {
		if (agcId == null) {
			return null;
		}
		return agentContactMapper.asAgentContactLiteDTO(agentContactRepository.findOne(agcId));
	}

	@Override
	public void delete(String agentId) {
		agentRepository.removeLike("%" + agentId + "%");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#searchFinancialAdvisor(java.lang.String, java.util.List, java.lang.Integer, int, int)
	 */
	@Override
	public SearchResult<AgentLightDTO> searchFinancialAdvisor(String search, List<String> categories, Integer status, int page, int size) {
		Assert.notNull(categories);

		Pageable pageable = new PageRequest(page, size);

		Page<AgentEntity> searchResult = agentRepository.findFinancialAdvisors(search, categories, status, pageable);

		return getAgentLightSearchResult(size, searchResult);
	}

	private Specifications<AgentEntity> createSearchSpecificationForPartnerID(String search) {
		Specifications<AgentEntity> specifs = Specifications.where(AgentSpecifications.initial());
		if (StringUtils.isNotBlank(search)) {
			specifs = specifs.and(AgentSpecifications.withPartnerID(search));
		}
		return specifs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getAgentLite(java.lang.String)
	 */
	@Override
	public AgentLightDTO getAgentLite(String agtId) {
		Assert.notNull(StringUtils.trimToNull(agtId));
		return agentMapper.asAgentLightDTO(agentRepository.findOne(agtId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AgentLightDTO> retrievePayableCommissionAgentOwner(List<String> agentIds) {
		List<AgentLightDTO> agenLites = new ArrayList<>();
		List<List<String>> partionedList = ListUtils.partition(agentIds, SQL_QUERY_LIMIT);

		for (List<String> agentIdList : partionedList) {
			List<AgentLightDTO> items = agentMapper.asAgentLightDTOs(agentRepository.findPayableCommissionAgentOwnerByIds(agentIdList));
			agenLites.addAll(items);
		}

		return agenLites;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getAccountRootPatternExample(java.lang.String)
	 */
	@Override
	public SearchResult<AgentDTO> getAgentsCreatedSince(Date date, int page, int size) {
		return getAgentsCreatedSince(date, null, page, size);
	}

	@Override
	public SearchResult<AgentDTO> getAgentsCreatedSince(Date date, String createdBy, int page, int size) {
		Assert.notNull(date);

		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "name", "agtId");
		Page<AgentEntity> pageResult;

		if (createdBy == null) {
			pageResult = agentRepository.findByCreationDate(date, pageable);
		} else {
			pageResult = agentRepository.findByCreationDate(date, createdBy, pageable);
		}

		return getAgentSearchResult(size, pageResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getAccountRootPatternExample(java.lang.String)
	 */
	@Override
	public Collection<String> getAccountRootPatternExample(String depositBank) {
		AgentEntity agent = agentRepository.findOne(depositBank);
		if (agent == null) {
			return Collections.<String>emptyList();
		} else {
			return patternAccountRootRepository.findExampleByAccountBic(agent.getPaymentAccountBic());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentService#getAccountRootPattern(java.lang.String)
	 */
	@Override
	public Collection<String> getAccountRootPattern(String depositBank) {
		AgentEntity agent = agentRepository.findOne(depositBank);
		if (agent == null) {
			return Collections.<String>emptyList();
		} else {
			return patternAccountRootRepository.findPatternByAccountBic(agent.getPaymentAccountBic());
		}
	}

	@Override
	public Collection<PaymentMethodsDTO> getPaymentMethodsByFundIds(Collection<String> fundIds) {
		Assert.notEmpty(fundIds, "Fund ids can't be empty");

		return agentRepository.findByFundIds(fundIds);
	}

}
