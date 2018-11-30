package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;
import org.tempuri.wssnbset.WssnbsetImport;
import org.tempuri.wssnbset.WssnbsetImport.ImpValidationUsers;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class NewBusinessMapper {

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	public abstract WssnbsetImport asWssnbsetImport(NewBusinessDTO in);

	public abstract WssnbsetImport.ImpUseridUsers asImpUseridUsers(NewBusinessDTO.ImpUseridUsers in);

	public abstract WssnbsetImport.ImpGrpCpr asImpGrpCpr(NewBusinessDTO.ImpGrpCpr in);

	public abstract WssnbsetImport.ImpGrpControls asImpGrpControls(NewBusinessDTO.ImpGrpControls in);

	public abstract WssnbsetImport.ImpGrpPas asImpGrpPas(NewBusinessDTO.ImpGrpPas in);

	public abstract WssnbsetImport.ImpCallMethodCommunications asImpCallMethodCommunications(NewBusinessDTO.ImpCallMethodCommunications in);

	public abstract WssnbsetImport.ImpGrpPbc asImpGrpPbc(NewBusinessDTO.ImpGrpPbc in);

	public abstract WssnbsetImport.ImpGrpCvg asImpGrpCvg(NewBusinessDTO.ImpGrpCvg in);

	public abstract WssnbsetImport.ImpGrpPfd asImpGrpPfd(NewBusinessDTO.ImpGrpPfd in);

	public abstract WssnbsetImport.ImpPolicyPolicies asImpPolicyPolicies(NewBusinessDTO.ImpPolicyPolicies in);

	public abstract WssnbsetImport.ImpPonGeneralNotes asImpPonGeneralNotes(NewBusinessDTO.ImpPonGeneralNotes in);

	public abstract WssnbsetImport.ImpValidationUsers asImpValidationUsers(NewBusinessDTO.ImpValidationUsers in);

	public abstract WssnbsetImport.ImpGrpCpr.Row asImpGrpCprRow(NewBusinessDTO.ImpGrpCpr.Row in);

	public abstract List<WssnbsetImport.ImpGrpCpr.Row> asImpGrpCprRows(List<NewBusinessDTO.ImpGrpCpr.Row> in);

	public abstract WssnbsetImport.ImpGrpControls.Row asImpGrpControlRow(NewBusinessDTO.ImpGrpControls.Row in);

	public abstract List<WssnbsetImport.ImpGrpControls.Row> asImpGrpControlRows(List<NewBusinessDTO.ImpGrpControls.Row> in);

	public abstract WssnbsetImport.ImpGrpPas.Row asImpGrpPasRow(NewBusinessDTO.ImpGrpPas.Row in);

	public abstract List<WssnbsetImport.ImpGrpPas.Row> asImpGrpPasRows(List<NewBusinessDTO.ImpGrpPas.Row> in);

	public abstract WssnbsetImport.ImpGrpPbc.Row asImpGrpPbcRow(NewBusinessDTO.ImpGrpPbc.Row in);

	public abstract List<WssnbsetImport.ImpGrpPbc.Row> asImpGrpPbcRows(List<NewBusinessDTO.ImpGrpPbc.Row> in);

	public abstract WssnbsetImport.ImpGrpCvg.Row asImpGrpCvgRow(NewBusinessDTO.ImpGrpCvg.Row in);

	public abstract List<WssnbsetImport.ImpGrpCvg.Row> asImpGrpCvgRows(List<NewBusinessDTO.ImpGrpCvg.Row> in);

	public abstract WssnbsetImport.ImpGrpPfd.Row asImpGrpPfdRow(NewBusinessDTO.ImpGrpPfd.Row in);

	public abstract List<WssnbsetImport.ImpGrpPfd.Row> asImpGrpPfdRows(List<NewBusinessDTO.ImpGrpPfd.Row> in);

	public abstract WssnbsetImport.ImpGrpCpr.Row.ImpItmCprCliPolRelationships asImpItmCprCliPolRelationships(NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships in);

	public abstract WssnbsetImport.ImpGrpControls.Row.ImpItmControlProductValues asImpItmControlProductValues(NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues in);

	public abstract WssnbsetImport.ImpGrpPas.Row.ImpItmPrlProductLines asImpItmPrlProductLines(NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines in);

	public abstract WssnbsetImport.ImpGrpPas.Row.ImpItmPasPolicyAgentShares asImpItmPasPolicyAgentShares(NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares in);

	public abstract WssnbsetImport.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses asImpItmPbcPolicyBeneficiaryClauses(NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses in);

	public abstract WssnbsetImport.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives asImpItmPclAssured2PolicyCvgLives(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives in);

	public abstract WssnbsetImport.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives asImpItmPclAssured1PolicyCvgLives(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives in);

	public abstract WssnbsetImport.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums asImpItmPcpPolicyPremiums(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums in);

	public abstract WssnbsetImport.ImpGrpCvg.Row.ImpItmPocPolicyCoverages asImpItmPocPolicyCoverages(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages in);

	public abstract WssnbsetImport.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections asImpItmPfdPolicyFundDirections(NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections in);

	@AfterMapping
	public WssnbsetImport afterEntityMapping(NewBusinessDTO in, @MappingTarget WssnbsetImport out) {

		ImpValidationUsers ImpValidationUsers = out.getImpValidationUsers();
		ImpValidationUsers.setLoginId(wsLoginCredential);
		ImpValidationUsers.setPassword(wsPasswordCredential);
		return out;
	}

}
