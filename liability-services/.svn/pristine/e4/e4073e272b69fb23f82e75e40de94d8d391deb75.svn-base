package lu.wealins.liability.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.UoptDetailDTO;
import lu.wealins.liability.services.core.business.UoptDefinitions;
import lu.wealins.liability.services.core.business.UoptDetailService;
import lu.wealins.liability.services.ws.rest.UoptDetailRESTService;

@Component
public class UoptDetailRESTServiceImpl implements UoptDetailRESTService {

	@Autowired
	private UoptDetailService uoptDetailService;

	@Override
	public UoptDetailDTO getUoptDetail(SecurityContext context, String uddId) {
		return uoptDetailService.getUoptDetail(uddId);
	}

	@Override
	public List<UoptDetailDTO> getCircularLetters(SecurityContext context) {
		return uoptDetailService.getCircularLetters();
	}

	@Override
	public List<UoptDetailDTO> getFundClassifications(SecurityContext context) {
		return uoptDetailService.getFundClassifications();
	}

	@Override
	public List<UoptDetailDTO> getRiskClasses(SecurityContext context) {
		return uoptDetailService.getRiskClasses();
	}

	@Override
	public List<UoptDetailDTO> getRiskProfiles(SecurityContext context) {
		return uoptDetailService.getRiskProfiles();
	}

	@Override
	public List<UoptDetailDTO> getRiskCurrencies(SecurityContext context) {
		return uoptDetailService.getRiskCurrencies();
	}

	@Override
	public List<UoptDetailDTO> getInvestmentCategories(SecurityContext context) {
		return uoptDetailService.getInvestmentCategories();
	}

	@Override
	public List<UoptDetailDTO> getAlternativeFunds(SecurityContext context) {
		return uoptDetailService.getAlternativeFunds();
	}

	@Override
	public List<UoptDetailDTO> getTypePOAs(SecurityContext context) {
		return uoptDetailService.getTypePOAs();
	}

	@Override
	public List<UoptDetailDTO> getSendingRules(SecurityContext context) {
		return uoptDetailService.getSendingRules();
	}

	@Override
	public List<UoptDetailDTO> getTypeOfAgentContact(SecurityContext context) {
		return uoptDetailService.getTypeOfAgentContact();
	}

	@Override
	public List<UoptDetailDTO> getTitles(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CLIENT_TITLE);
	}

	@Override
	public List<UoptDetailDTO> getClientProfiles(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CLIENT_INVESTMENT_PROFILE);
	}

	@Override
	public List<UoptDetailDTO> getClientComplianceRisks(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CLIENT_COMPLIANCE_RISK);
	}

	@Override
	public List<UoptDetailDTO> getClientProfessions(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CLIENT_PROFESSION);
	}

	@Override
	public List<UoptDetailDTO> getClientActivitySectors(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CLIENT_ACTIVITY_SECTOR);
	}

	@Override
	public List<UoptDetailDTO> getEntityType(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.ENTITY_TYPE);
	}

	@Override
	public List<UoptDetailDTO> getCrsStatus(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CRS_STATUS);
	}

	@Override
	public List<UoptDetailDTO> getCrsExactStatus(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.CRS_EXACT_STATUS);
	}

	@Override
	public List<UoptDetailDTO> getTypeOfControl(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.TYPE_OF_CONTROL);
	}

	@Override
	public List<UoptDetailDTO> getAgentTitle(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.AGENT_TITLE);
	}

	@Override
	public List<UoptDetailDTO> getDeathCauses(SecurityContext ctx) {
		return uoptDetailService.getOptions(UoptDefinitions.DEATH_CAUSE);
	}

	@Override
	public UoptDetailDTO getUoptDetailForKeyValue(SecurityContext context, String keyValue) {
		return uoptDetailService.getUoptDetailEntityForKeyValue(keyValue);
	}

}
