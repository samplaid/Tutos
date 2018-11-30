package lu.wealins.liability.services.core.mapper;

import static lu.wealins.liability.services.core.utils.constantes.Constantes.WS_LISSIA_EXPECTED_CODE;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;
import org.tempuri.wsspfhwd.WsspfhwdImport;
import org.tempuri.wsspfhwd.WsspfhwdImport.ImpValidationUsers;

import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;

@Mapper(componentModel = "spring")
public abstract class WsspfhwdImportMapper {

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	public abstract WsspfhwdImport asWsspfhwdImport(WithdrawalInputDTO in);

	public abstract WsspfhwdImport.ImpPasPolicyAgentShares asImpPasPolicyAgentShares(WithdrawalInputDTO.ImpPasPolicyAgentShares in);

	public abstract WsspfhwdImport.ImpPocPolicyCoverages asImpPocPolicyCoverages(WithdrawalInputDTO.ImpPocPolicyCoverages in);

	public abstract WsspfhwdImport.ImpGrpPfi asImpGrpPfi(WithdrawalInputDTO.ImpGrpPfi in);

	public abstract WsspfhwdImport.ImpGrpPfi.Row asImpGrpPfi(WithdrawalInputDTO.ImpGrpPfi.Row in);

	public abstract WsspfhwdImport.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions asImpItmPfiPolicyFundInstructions(WithdrawalInputDTO.ImpGrpPfi.Row.ImpItmPfiPolicyFundInstructions in);

	public abstract WsspfhwdImport.ImpGrpPfi.Row.ItmSelPfiIefSupplied asItmSelPfiIefSupplied(WithdrawalInputDTO.ImpGrpPfi.Row.ItmSelPfiIefSupplied in);

	@AfterMapping
	public WsspfhwdImport afterMapping(WithdrawalInputDTO in, @MappingTarget WsspfhwdImport out) {
		out.setExitState(WS_LISSIA_EXPECTED_CODE);
		out.setImpValidationUsers(getWsUser());
		return out;
	}

	private ImpValidationUsers getWsUser() {
		ImpValidationUsers wsUser = new ImpValidationUsers();
		wsUser.setLoginId(wsLoginCredential);
		wsUser.setPassword(wsPasswordCredential);
		return wsUser;
	}

}
