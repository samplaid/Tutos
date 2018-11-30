package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.PolicyTransferFormEntity;
import lu.wealins.common.dto.webia.services.PolicyTransferFormDTO;

@Mapper(componentModel = "spring")
public interface PolicyTransferFormMapper {

	PolicyTransferFormEntity asPolicyTransferForm(PolicyTransferFormDTO in);

	PolicyTransferFormDTO asPolicyTransferFormDTO(PolicyTransferFormEntity in);

	Collection<PolicyTransferFormDTO> asPolicyTransferFormDTOs(Collection<PolicyTransferFormEntity> in);
}
