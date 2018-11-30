package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;
import org.tempuri.wssadcvg.WssadcvgImport;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class AdditionalPremiumMapper {

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	public abstract WssadcvgImport asWssadcvgImport(AdditionalPremiumDTO in);

	public abstract WssadcvgImport.ImpGrpPfd asImpGrpPfd(AdditionalPremiumDTO.ImpGrpPfd in);

	public abstract WssadcvgImport.ImpPolPolicies asImpPolicyPolicies(AdditionalPremiumDTO.ImpPolPolicies in);

	public abstract WssadcvgImport.ImpPocPolicyCoverages ImpPocPolicyCoverages(AdditionalPremiumDTO.ImpPocPolicyCoverages in);

	public abstract WssadcvgImport.ImpPcpPolicyPremiums ImpImpPcpPolicyPremiums(AdditionalPremiumDTO.ImpPcpPolicyPremiums in);

	public abstract WssadcvgImport.ImpValidationUsers asImpValidationUsers(AdditionalPremiumDTO.ImpValidationUsers in);

	public abstract WssadcvgImport.ImpGrpPas asImpGrpPas(AdditionalPremiumDTO.ImpGrpPas in);

	public abstract WssadcvgImport.ImpGrpPfd.Row asImpGrpPfdRow(AdditionalPremiumDTO.ImpGrpPfd.Row in);

	public abstract List<WssadcvgImport.ImpGrpPfd.Row> asImpGrpPfdRows(List<AdditionalPremiumDTO.ImpGrpPfd.Row> in);

	public abstract WssadcvgImport.ImpGrpPas.Row AsImpGrpPasRow(AdditionalPremiumDTO.ImpGrpPas.Row in);

	public abstract List<WssadcvgImport.ImpGrpPas.Row> AsImpGrpPasRows(List<AdditionalPremiumDTO.ImpGrpPas.Row> in);

	public abstract WssadcvgImport.ImpGrpPas.Row.ImpItmPrlProductLines asImpItmPrlProductLines(AdditionalPremiumDTO.ImpGrpPas.Row.ImpItmPrlProductLines in);

	public abstract WssadcvgImport.ImpGrpPas.Row.ImpItmPasPolicyAgentShares asImpItmPasPolicyAgentShares(AdditionalPremiumDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares in);

	public abstract WssadcvgImport.ImpGrpControls asImpGrpControls(AdditionalPremiumDTO.ImpGrpControls in);

	public abstract WssadcvgImport.ImpGrpControls.Row asImpGrpControlRow(AdditionalPremiumDTO.ImpGrpControls.Row in);

	public abstract List<WssadcvgImport.ImpGrpControls.Row> asImpGrpControlRows(List<AdditionalPremiumDTO.ImpGrpControls.Row> in);

	public abstract WssadcvgImport.ImpGrpControls.Row.ImpItmControlProductValues asImpItmControlProductValues(AdditionalPremiumDTO.ImpGrpControls.Row.ImpItmControlProductValues in);

	public abstract WssadcvgImport.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections asImpItmPfdPolicyFundDirections(AdditionalPremiumDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections in);

	@AfterMapping
	public WssadcvgImport afterEntityMapping(AdditionalPremiumDTO in, @MappingTarget WssadcvgImport out) {

		WssadcvgImport.ImpValidationUsers ImpValidationUsers = out.getImpValidationUsers();
		ImpValidationUsers.setLoginId(wsLoginCredential);
		ImpValidationUsers.setPassword(wsPasswordCredential);
		return out;
	}

}
