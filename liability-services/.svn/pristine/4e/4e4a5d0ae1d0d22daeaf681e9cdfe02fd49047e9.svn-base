package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.ProductEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ProductDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface ProductMapper {

	ProductDTO asProductDTO(ProductEntity in);

	List<ProductDTO> asProductDTOs(List<ProductEntity> in);

}
