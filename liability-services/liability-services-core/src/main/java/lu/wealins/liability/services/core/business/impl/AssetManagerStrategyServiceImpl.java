package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.AssetManagerStrategyService;
import lu.wealins.liability.services.core.business.exceptions.AssetManagerSaveException;
import lu.wealins.liability.services.core.business.exceptions.ReportExceptionHelper;
import lu.wealins.liability.services.core.mapper.AssetManagerStrategyMapper;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.persistence.entity.AssetManagerStrategyEntity;
import lu.wealins.liability.services.core.persistence.repository.AgentRepository;
import lu.wealins.liability.services.core.persistence.repository.AssetManagerStrategyRepository;
import lu.wealins.liability.services.core.utils.HistoricManager;
import lu.wealins.liability.services.core.validation.AssetManagerStrategyValidationService;
import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

@Service
public class AssetManagerStrategyServiceImpl implements AssetManagerStrategyService {
	
	@Autowired
	private HistoricManager historicManager;

	@Autowired
	private AssetManagerStrategyRepository assetManagerStrategyRepository;

	@Autowired
	private AssetManagerStrategyValidationService assetManagerStrategyValidationService;

	@Autowired
	private AssetManagerStrategyMapper assetManagerStrategyMapper;
	
	@Autowired
	private AgentRepository agentRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AssetManagerStrategyService#getAssetManagerStrategies(java.lang.String)
	 */
	@Override
	public Collection<AssetManagerStrategyDTO> getAssetManagerStrategies(String assetManagerId) {
		return assetManagerStrategyMapper.asAssetManagerStrategyDTOs(assetManagerStrategyRepository.findByAgentId(assetManagerId));
	}
	
	
	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.core.business.AssetManagerStrategyService#saveOrUpdate(lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO)
	 */
	@Override
	public AssetManagerStrategyDTO saveOrUpdate(AssetManagerStrategyDTO managerStrategy) {
		Assert.notNull(managerStrategy);
		Assert.notNull(managerStrategy.getAmsId());
		List<String> errors = validateAssetManager(managerStrategy);

		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, AssetManagerSaveException.class);
		AssetManagerStrategyEntity entity = assetManagerStrategyRepository.findOne(managerStrategy.getAmsId());

		if (Objects.nonNull(entity)) {
			assetManagerStrategyMapper.updateEntity(managerStrategy, entity);
		} else {
			entity = assetManagerStrategyMapper.asAssetManagerStrategyEntity(managerStrategy);
		}

		AgentEntity agentEntity = agentRepository.findOne(managerStrategy.getAgentNo());

		Assert.notNull(agentEntity);

		entity.setAgent(agentEntity);
		upperRiskProfile(entity);
		boolean canSave = historicManager.historize(entity);

		if (historicManager.historize(entity)) {
			entity = assetManagerStrategyRepository.save(entity);
		}

		return assetManagerStrategyMapper.asAssetManagerStrategyDTO(entity);
	}

	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.core.business.AssetManagerStrategyService#bulkSaveOrUpdate(java.util.Collection)
	 */
	@Override
	public Collection<AssetManagerStrategyDTO> bulkSaveOrUpdate(Collection<AssetManagerStrategyDTO> managerStrategies) {
		Assert.notNull(managerStrategies);
		managerStrategies.forEach(item -> Assert.notNull(item.getAmsId()));
		List<String> errors = validateAssetManagers(managerStrategies);
		ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, AssetManagerSaveException.class);

		List<AssetManagerStrategyEntity> strategyEntities = asFullStrategyEntities(managerStrategies);
		List<AssetManagerStrategyEntity> updatedStrategyEntities = new ArrayList<>();
		fillAgent(strategyEntities);

		for (AssetManagerStrategyEntity entity : strategyEntities) {
			upperRiskProfile(entity);
			if (historicManager.historize(entity)) {
				entity = assetManagerStrategyRepository.save(entity);
			}
			updatedStrategyEntities.add(entity);
		}

		return assetManagerStrategyMapper.asAssetManagerStrategyDTOs(updatedStrategyEntities);
	}


	/**
	 * @param strategyEntities
	 */
	private void fillAgent(List<AssetManagerStrategyEntity> strategyEntities) {
		List<String> agentIds = strategyEntities.stream().filter(entity -> Objects.isNull(entity.getAgent()))
				.map(entity -> StringUtils.trimToEmpty(entity.getAgentNo()))
				.distinct()
				.collect(Collectors.toList());

		if (!agentIds.isEmpty()) {
			final List<AgentEntity> agents = agentRepository.findAll(agentIds);
			strategyEntities.stream()
					.filter(entity -> Objects.isNull(entity.getAgent()))
					.forEach(entity -> {
						AgentEntity agentEntity = agents.stream()
								.filter(agent -> StringUtils.trimToEmpty(agent.getAgtId()).equals(StringUtils.trimToEmpty(entity.getAgentNo())))
								.findFirst().orElse(null);

						if (Objects.nonNull(agentEntity)) {
							entity.setAgent(agentEntity);
						}

					});
		}
	}

	/**
	 * Map a collection of strategies dto to a collection of strategy entities.
	 * 
	 * @param managerStrategies the set of dto
	 * @return a list of strategy entities
	 */
	private List<AssetManagerStrategyEntity> asFullStrategyEntities(Collection<AssetManagerStrategyDTO> managerStrategies) {
		List<AssetManagerStrategyEntity> strategyEntities = new ArrayList<>();
		List<AssetManagerStrategyDTO> idAwareStrategies = managerStrategies.stream()
				.filter(strategy -> Objects.nonNull(strategy.getAmsId()))
				.collect(Collectors.toList());

		// All the item in the collection are assumed to have an id. Even for new items.
		// A new id should assigned to the new item in web application layer. If not, check it.
		if (!idAwareStrategies.isEmpty()) {
			List<AssetManagerStrategyEntity> strategiesFromDb = assetManagerStrategyRepository.findAll(idAwareStrategies.stream().map(strategy -> strategy.getAmsId()).collect(Collectors.toList()));

			if (!strategiesFromDb.isEmpty()) {
				idAwareStrategies.forEach(idAwareStrategy -> {
					AssetManagerStrategyEntity strategyFromDb = strategiesFromDb.stream()
							.filter(item -> idAwareStrategy.getAmsId().equals(item.getAmsId()))
							.findFirst().orElse(null);

					if (Objects.nonNull(strategyFromDb)) {
						assetManagerStrategyMapper.updateEntity(idAwareStrategy, strategyFromDb);
					} else {
						strategyFromDb = assetManagerStrategyMapper.asAssetManagerStrategyEntity(idAwareStrategy);
					}

					strategyEntities.add(strategyFromDb);

				});

			} else {
				strategyEntities.addAll(assetManagerStrategyMapper.asAssetManagerStrategyEntities(idAwareStrategies));
			}

		}

		return strategyEntities;
	}
	
	@Override
	public Collection<AssetManagerStrategyDTO> bulkSaveOrUpdate(Collection<AssetManagerStrategyDTO> managerStrategies, String agentId) {
		Assert.notNull(managerStrategies);
		Assert.notNull(StringUtils.trimToNull(agentId));

		Collection<AssetManagerStrategyDTO> copyList = new ArrayList<>(managerStrategies);
		copyList.forEach(strategy -> strategy.setAgentNo(agentId));
		return bulkSaveOrUpdate(copyList);
	}

	private void upperRiskProfile(AssetManagerStrategyEntity entity) {
		if (entity != null) {
			entity.setRiskProfile(StringUtils.trimToEmpty(entity.getRiskProfile()).toUpperCase());
		}
	}

	private List<String> validateAssetManager(AssetManagerStrategyDTO assetManagerStrategy) {
		List<String> errors = new ArrayList<>();
		assetManagerStrategyValidationService.validateAgentNumberNotBlank(assetManagerStrategy, errors);
		assetManagerStrategyValidationService.validateCodeNotBlank(assetManagerStrategy, errors);
		assetManagerStrategyValidationService.validateCodeUniqueInDb(assetManagerStrategy, errors);
		assetManagerStrategyValidationService.validateRiskTypeNotBlank(assetManagerStrategy, errors);
		assetManagerStrategyValidationService.validateRiskDescriptionNotBlank(assetManagerStrategy, errors);
		assetManagerStrategyValidationService.validateReadOnlyCodeIfLinkedToFund(assetManagerStrategy, errors);
		Collection<String> messages = new ArrayList<>(errors);
		errors.clear();
		errors.addAll(messages.stream().distinct().collect(Collectors.toList()));
		return errors;
	}

	private List<String> validateAssetManagers(Collection<AssetManagerStrategyDTO> assetManagerStrategies) {
		List<String> errors = new ArrayList<>();
		assetManagerStrategyValidationService.validateAgentNumbersNotBlank(assetManagerStrategies, errors);
		assetManagerStrategyValidationService.validateCodeNotBlankInList(assetManagerStrategies, errors);
		assetManagerStrategyValidationService.validateCodeUniqueInDbList(assetManagerStrategies, errors);
		assetManagerStrategyValidationService.validateCodeUniqueInList(assetManagerStrategies, errors);
		assetManagerStrategyValidationService.validateRiskTypeNotBlankInList(assetManagerStrategies, errors);
		assetManagerStrategyValidationService.validateRiskDescriptionNotBlankInList(assetManagerStrategies, errors);
		assetManagerStrategyValidationService.validateReadOnlyCodeIfLinkedToFundInList(assetManagerStrategies, errors);
		Collection<String> messages = new ArrayList<>(errors);
		errors.clear();
		errors.addAll(messages.stream().distinct().collect(Collectors.toList()));
		return errors;
	}

}
