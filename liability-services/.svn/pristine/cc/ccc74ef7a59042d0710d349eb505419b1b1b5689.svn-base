package lu.wealins.liability.services.core.business.impl;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.ClientNameService;
import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;

@Component
public class ClientNameServiceImpl implements ClientNameService {

	@Autowired
	private ClientService clientService;

	@Override
	public String generate(ClientEntity c) {

		StringBuilder sb = new StringBuilder();

		// client's last name or company name
		sb.append(c.getName() != null ? c.getName().trim() : "");

		// First name
		if (StringUtils.hasText(c.getFirstName())) {
			sb.append(" " + c.getFirstName().trim());
		}

		// Birth date
		if (c.getDateOfBirth() != null && DateFormatUtils.format(c.getDateOfBirth(), "yyyy").compareTo("1800") > 0) {
			String date = DateFormatUtils.format(c.getDateOfBirth(), "(dd/MM/yyyy)");
			sb.append(" " + date);
		}

		// PEP
		if (StringUtils.hasText(c.getPoliticallyExposedPerson()) &&
				c.getPoliticallyExposedPerson().trim().toUpperCase().indexOf("PEP YES") >= 0) {
			sb.append(" _#PEP#_");
		}

		// DAP
		if ("1".equals(c.getDap())) {
			sb.append(" _#STR#_");
		}

		// RCA
		if ("Y".equals(c.getRelativeCloseAssoc())) {
			sb.append(" _#RCA#_");
		}

		// FATCA
		if (StringUtils.hasText(c.getClassification()) &&
				c.getClassification().trim().toUpperCase().indexOf("FATCA") >= 0) {
			sb.append(" _#FATCA#_");
		}

		// MEP
		if (StringUtils.hasText(c.getMediaExposedPerson()) &&
				("Y".equals(c.getMediaExposedPerson()) || "1".equals(c.getMediaExposedPerson()))) {
			sb.append(" _#MEP#_");
		}

		if (StringUtils.hasText(c.getInsiderTrading()) &&
				(c.getInsiderTrading().trim().toUpperCase().indexOf("IT YES") >= 0 ||
						c.getInsiderTrading().trim().toUpperCase().indexOf("IT_YES") >= 0)) {
			sb.append(" _#Insider Trading#_");
			if (StringUtils.hasText(c.getRiskCat())) {
				sb.append(" _#").append(c.getRiskCat()).append("#_");
			}
		}

		if (clientService.isDead(c)) {
			sb.append(" _#").append("(Deceased)").append("#_");
		}

		return sb.toString();
	}

}
