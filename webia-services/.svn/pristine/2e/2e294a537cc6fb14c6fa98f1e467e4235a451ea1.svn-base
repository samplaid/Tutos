package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;

@Mapper(componentModel = "spring")
public interface CommissionToPayMapper {

	CommissionToPayDTO asCommissionToPayDTO(CommissionToPayEntity in);

	List<CommissionToPayDTO> asCommissionToPayDTOs(List<CommissionToPayEntity> in);

	CommissionToPayEntity asCommissionToPayEntity(CommissionToPayDTO in);

	List<CommissionToPayEntity> asCommissionToPayEntities(List<CommissionToPayDTO> in);

}
