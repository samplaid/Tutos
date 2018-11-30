package lu.wealins.liability.services.core.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.liability.services.core.persistence.entity.AssetManagerStrategyEntity;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class AssetManagerStrategyMapper {
	
	@Autowired
	private FundRepository fundRepository;
		
	public abstract AssetManagerStrategyDTO asAssetManagerStrategyDTO(AssetManagerStrategyEntity in);

	public abstract Collection<AssetManagerStrategyDTO> asAssetManagerStrategyDTOs(Collection<AssetManagerStrategyEntity> in);
	
	public abstract Collection<AssetManagerStrategyEntity> asAssetManagerStrategyEntities(Collection<AssetManagerStrategyDTO> in);
	
	public abstract AssetManagerStrategyEntity asAssetManagerStrategyEntity(AssetManagerStrategyDTO in);
	
	public abstract void updateEntity(AssetManagerStrategyDTO source, @MappingTarget AssetManagerStrategyEntity entity);
	
	@AfterMapping
	public AssetManagerStrategyDTO afterEntityMapping(AssetManagerStrategyEntity in, @MappingTarget AssetManagerStrategyDTO target) {
		List<FundEntity> linkedFunds = fundRepository.findByRiskProfile(in.getRiskProfile().trim());
		target.setLinkedToFund(CollectionUtils.isNotEmpty(linkedFunds));
		return target;
	}
}
