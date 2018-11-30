package lu.wealins.liability.services.core.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Value;
import org.tempuri.wssinfpl.WssinfplImport;
import org.tempuri.wssinfpl.WssinfplImport.ImpValidationUsers;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ActivatePolicyRequest;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class WssinfplImportMapper {

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	@Mappings({
			@Mapping(source = "id", target = "impPolicyPolicies.polId"),
			@Mapping(source = "effectiveDate", target = "impPscPsc.statusChangeDate", dateFormat = "yyyyMMdd")
	})
	public abstract WssinfplImport asWssinfplImport(ActivatePolicyRequest in);

	@AfterMapping
	public WssinfplImport afterEntityMapping(ActivatePolicyRequest request, @MappingTarget WssinfplImport out) {

		ImpValidationUsers impValidationUsers = new ImpValidationUsers();
		impValidationUsers.setLoginId(wsLoginCredential);
		impValidationUsers.setPassword(wsPasswordCredential);

		out.setImpValidationUsers(impValidationUsers);
		return out;
	}

}
