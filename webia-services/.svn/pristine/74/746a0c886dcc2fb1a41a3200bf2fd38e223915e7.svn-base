package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.SasIsinEntity;
import lu.wealins.common.dto.webia.services.SasIsinDTO;

/**
 * Mapper used to make the convertions {@link SasIsinDTO} <-> {@link SasIsinEntity}.
 *
 */
@Mapper(componentModel = "spring")
public interface SasIsinMapper {

	/**
	 * Convert a {@link SasIsinEntity} in a {@link SasIsinDTO}.
	 * 
	 * @param in a {@link SasIsinEntity}
	 * @return a {@link SasIsinDTO}
	 */
	SasIsinDTO asSasIsinDTO(SasIsinEntity in);

	/**
	 * Convert a {@link SasIsinDTO} in a {@link SasIsinEntity}.
	 * 
	 * @param in a {@link SasIsinDTO}
	 * @return a {@link SasIsinEntity}
	 */
	SasIsinEntity asSasIsinEntity(SasIsinDTO in);

	/**
	 * Convert a list of {@link SasIsinEntity} into a list of {@link SasIsinDTO}
	 * 
	 * @param in a list of {@link SasIsinEntity}
	 * @return a list of {@link SasIsinDTO}
	 */
	List<SasIsinDTO> asSasIsinDTOList(List<SasIsinEntity> in);

	/**
	 * Convert a list of {@link SasIsinDTO} into a list of {@link SasIsinEntity}
	 * 
	 * @param in a list of {@link SasIsinDTO}
	 * @return a list of {@link SasIsinEntity}
	 */
	List<SasIsinEntity> asSasIsinEntityList(List<SasIsinDTO> in);
}
