package lu.wealins.webia.core.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;

@Mapper(componentModel = "spring", uses = { ClientFormMapper.class, PolicyHolderMapper.class, PolicyTransferMapper.class })
public abstract class AdditionalPremiumFormMapper extends AbstractAppFormMapper {

	@AfterMapping
	protected AppFormDTO asAppFormDTOAfterMapping(PolicyDTO policy, @MappingTarget AppFormDTO appForm) {
		super.afterMapping(policy, appForm);
		return appForm;
	}
}
