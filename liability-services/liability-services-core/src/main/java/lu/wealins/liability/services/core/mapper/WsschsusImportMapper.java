package lu.wealins.liability.services.core.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Value;
import org.tempuri.wsschsus.WsschsusImport;
import org.tempuri.wsschsus.WsschsusImport.ImpValidationUsers;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class WsschsusImportMapper {

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	@Mappings({
			@Mapping(source = "polId", target = "impPolicyPolicies.polId"),
			@Mapping(target = "impCashPaymentTransactionSets.amount", expression = "java(in.getAmount().setScale(2, java.math.RoundingMode.HALF_DOWN).toPlainString())"),
			@Mapping(source = "reference", target = "impCashPaymentTransactionSets.reference"),
			@Mapping(source = "currency", target = "impCashPaymentTransactionSets.currency"),
			@Mapping(source = "details", target = "impCashPaymentTransactionSets.details"),
			@Mapping(source = "effectiveDate", target = "impCashPaymentTransactionSets.effectiveDate", dateFormat = "yyyyMMdd")
	})
	public abstract WsschsusImport asWsschsusImport(CreatePolicyCashSuspenseRequest in);

	@AfterMapping
	public WsschsusImport afterEntityMapping(CreatePolicyCashSuspenseRequest in, @MappingTarget WsschsusImport out) {

		ImpValidationUsers impValidationUsers = new ImpValidationUsers();
		impValidationUsers.setLoginId(wsLoginCredential);
		impValidationUsers.setPassword(wsPasswordCredential);

		out.setImpValidationUsers(impValidationUsers);
		return out;
	}
}
