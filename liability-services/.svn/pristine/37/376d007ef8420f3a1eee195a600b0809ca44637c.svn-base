package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.ProductLineEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ProductLineDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, ProductMapper.class })
public interface ProductLineWithoutPVsMapper {

	@Mappings({
			@Mapping(ignore = true, target = "productValues"),
	})
	ProductLineDTO asProductLineDTO(ProductLineEntity in);

	Collection<ProductLineDTO> asProductLineDTOs(Collection<ProductLineEntity> in);

}