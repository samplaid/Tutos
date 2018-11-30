package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.liability.services.core.persistence.entity.ProductValueEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface ProductValueMapper {

	ProductValueDTO asProductValueDTO(ProductValueEntity in);

	Collection<ProductValueDTO> asProductValueDTOs(Collection<ProductValueEntity> in);

	ProductValueEntity asProductValueEntity(ProductValueEntity in);
}
