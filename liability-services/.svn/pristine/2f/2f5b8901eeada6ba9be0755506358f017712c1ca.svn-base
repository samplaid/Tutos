package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyRegularPaymentEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyRegularPaymentDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyRegularPaymentMapper {
	@Mappings({
			@Mapping(source = "policy.polId", target = "polId")
	})
	PolicyRegularPaymentDTO toPolicyRegularPaymentDTO(PolicyRegularPaymentEntity in);

	List<PolicyRegularPaymentDTO> toPolicyRegularPaymentDTOs(List<PolicyRegularPaymentEntity> in);

}
