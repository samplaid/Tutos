package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus;
import lu.wealins.webia.services.core.persistence.entity.EncashmentFundFormEntity;
import lu.wealins.common.dto.webia.services.EncashmentFundFormDTO;

@Mapper(componentModel = "spring")
public abstract class EncashmentFundFormMapper {
	
	public abstract EncashmentFundFormDTO asEncashmentFundFormDTO(EncashmentFundFormEntity in);

	public abstract EncashmentFundFormEntity asEncashmentFundFormEntity(EncashmentFundFormDTO in);

	public abstract Collection<EncashmentFundFormDTO> asEncashmentFundFormDTOs(Collection<EncashmentFundFormEntity> in);

	
	@AfterMapping
	protected EncashmentFundFormDTO afterMapping(EncashmentFundFormEntity in, @MappingTarget EncashmentFundFormDTO target) {
		target.setCashStatus(in.getCashStatus().toString());
		return target;
	}
	
	@AfterMapping
	protected EncashmentFundFormEntity afterMapping(EncashmentFundFormDTO in, @MappingTarget EncashmentFundFormEntity target) {
		EncashmentFormFundStatus cashStatus = null;
		try {
			if (StringUtils.hasText(in.getCashStatus())){
				cashStatus = EncashmentFormFundStatus.valueOf(in.getCashStatus());
			} else {
				cashStatus = EncashmentFormFundStatus.NEW;
			}
		} catch (Exception e){
			cashStatus = EncashmentFormFundStatus.NEW;
		}
		target.setCashStatus(cashStatus);
		return target;
	}	
	
	
}
