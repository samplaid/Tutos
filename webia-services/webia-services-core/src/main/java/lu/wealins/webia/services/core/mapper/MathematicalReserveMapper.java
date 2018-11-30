package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


import lu.wealins.webia.services.core.persistence.entity.MathematicalReserveEntity;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.common.dto.webia.services.MathematicalReserveDTO;
import lu.wealins.common.dto.webia.services.SapAccountingRowNoEntityDTO;


@Mapper(componentModel = "spring")
public interface MathematicalReserveMapper {

	MathematicalReserveDTO asMathematicalReserveDTO(MathematicalReserveEntity in);

	MathematicalReserveEntity asMathematicalReserveEntity(MathematicalReserveDTO in);

	List<MathematicalReserveDTO> asMathematicalReserveDTOList(List<MathematicalReserveEntity> in);

	List<MathematicalReserveEntity> asMathematicalReserveEntityList(List<MathematicalReserveDTO> in);
	
	SapAccountingEntity asSapAccountingEntity(SapAccountingRowNoEntityDTO in);
	
	SapAccountingRowNoEntityDTO asSapAccountingRowNoEntityDTO(SapAccountingEntity in);
	
	List<SapAccountingRowNoEntityDTO> asSapAccountingRowNoEntityDTOList(List<SapAccountingEntity> in);
	
	List<SapAccountingEntity> asSapAccountingEntityList(List<SapAccountingRowNoEntityDTO> in);
	
	

}
