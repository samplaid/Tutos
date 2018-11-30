package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.webia.services.OrderFIDDTO;
import lu.wealins.webia.services.core.persistence.entity.SasOrderAggEntity;
import lu.wealins.webia.services.core.persistence.entity.SasOrderEntity;
import lu.wealins.webia.services.core.persistence.entity.SasOrderFIDEntity;

@Mapper(componentModel = "spring")
public interface SasOrderMapper {

	OrderDTO asSasOrderDTO(SasOrderEntity in);

	SasOrderEntity asSasOrderEntity(OrderDTO in);

	List<SasOrderEntity> asSasOrderEntityList(List<OrderDTO> in);
	
	OrderFIDDTO asSasOrderFIDDTO(SasOrderFIDEntity in);

	SasOrderFIDEntity asSasOrderFIDEntity(OrderFIDDTO in);

	List<SasOrderFIDEntity> asSasOrderFIDEntityList(List<OrderFIDDTO> in);
	
	SasOrderEntity asSasOrder(SasOrderEntity in);
	
	SasOrderAggEntity asSasOrderEntityAgg(SasOrderEntity in);
	
	List<SasOrderAggEntity> asSasOrderEntityAggList(List<SasOrderEntity> in);

}
