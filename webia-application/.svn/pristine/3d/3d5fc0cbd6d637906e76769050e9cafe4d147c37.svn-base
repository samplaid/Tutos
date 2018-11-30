package lu.wealins.webia.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.PolicyTransferDTO;
import lu.wealins.common.dto.webia.services.PolicyTransferFormDTO;
import lu.wealins.editing.common.webia.PolicySchedule.Transfer;

@Mapper(componentModel = "spring")
public abstract class PolicyTransferMapper {

	public abstract PolicyTransferDTO asPolicyTransferDTO(PolicyTransferFormDTO in);

	public abstract Collection<PolicyTransferDTO> asPolicyTransferDTOs(Collection<PolicyTransferFormDTO> in);

	public abstract PolicyTransferFormDTO asPolicyTransferFormDTO(PolicyTransferDTO in);

	public abstract Collection<PolicyTransferFormDTO> asPolicyTransferFormDTOs(Collection<PolicyTransferDTO> in);

	@Mappings({
			@Mapping(target = "fromPolicyId", source = "fromPolicy"),
			@Mapping(target = "fromPolicyEffectDate", source = "fromPolicyEffectDt")

	})
	public abstract Transfer asTransfer(PolicyTransferDTO in);

	public abstract Collection<Transfer> asTransferList(Collection<PolicyTransferDTO> in);

}
