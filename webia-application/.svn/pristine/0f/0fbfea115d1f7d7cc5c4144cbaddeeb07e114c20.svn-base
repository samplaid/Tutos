package lu.wealins.webia.core.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;
import lu.wealins.common.dto.webia.services.AppFormDTO;

@Mapper(componentModel = "spring")
public abstract class CreatePolicyCashSuspenseRequestMapper {

	@Mappings({
			@Mapping(target = "polId", source = "policyId"),
			@Mapping(target = "amount", source = "paymentAmt"),
			@Mapping(target = "currency", source = "contractCurrency"),
			@Mapping(target = "effectiveDate", source = "paymentDt")
	})
	public abstract CreatePolicyCashSuspenseRequest asCreatePolicyCashSuspenseRequest(AppFormDTO in);

	@AfterMapping
	protected CreatePolicyCashSuspenseRequest asCreatePolicyCashSuspenseRequestAfterMapping(AppFormDTO in, @MappingTarget CreatePolicyCashSuspenseRequest out) {
		
		out.setReference(in.getPolicyId());

		return out;
	}

}
