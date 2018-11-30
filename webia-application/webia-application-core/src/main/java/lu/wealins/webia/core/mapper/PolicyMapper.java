package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.editing.common.webia.Policy;

/**
 * 
 * @author bqv55
 *
 */
@Mapper(componentModel = "spring", uses = { PolicyHolderMapper.class })
public interface PolicyMapper {

	@Mappings({
			@Mapping(target = "policyId", source = "polId"),
			@Mapping(target = "applicationForm", source = "additionalId"),
			@Mapping(target = "product", source = "product.name"),
			@Mapping(target = "effectDate", source = "dateOfCommencement"),
			@Mapping(target = "contractDuration", source = "firstPolicyCoverages.term"),
			@Mapping(target = "noCoolOff", source = "noCooloff"),
			@Mapping(ignore = true, target = "insureds")
	})
	Policy asPolicy(PolicyDTO in);

}
