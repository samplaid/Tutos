package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.ProductEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ProductLightDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface ProductLightMapper {

	ProductLightDTO asProductLightDTO(ProductEntity in);

	List<ProductLightDTO> asProductLightDTOs(List<ProductEntity> in);

}