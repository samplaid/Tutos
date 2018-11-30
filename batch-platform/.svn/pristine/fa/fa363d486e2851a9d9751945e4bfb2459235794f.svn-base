package lu.wealins.batch.injection.clotured.vni;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.batch.injection.AbstractParser;
import lu.wealins.rest.model.CloturedVniInjectionControlRequest;
import lu.wealins.utils.CloturedVNIFundType;
import lu.wealins.utils.KeycloakUtils;

public class CloturedVniFidFasInjectionParser extends AbstractParser<CloturedVniInjectionControlRequest> {
	private static String CHECK_WEBIA_ACCOUNTING_NAV = "webia/accountingNav/checkCloturedVni";

	@Value("${cloturedVniFidFasInjectionSuccessPath:}")
	private String cloturedVniFidFasInjectionSuccessPath;

	@Value("${cloturedVniFidFasInjectionFailurePath:}")
	private String cloturedVniFidFasInjectionFailurePath;

	@Value("${piaRootContextURL}")
	String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Override
	public String getInjectionControlUrl() {

		return piaRootContextURL + CHECK_WEBIA_ACCOUNTING_NAV;
	}

	@Override
	public String getInjectionFailurePath() {
		return cloturedVniFidFasInjectionFailurePath;
	}

	@Override
	public String getInjectionSuccessPath() {
		return cloturedVniFidFasInjectionSuccessPath;
	}

	@Override
	public CloturedVniInjectionControlRequest buildInjectionControlRequest(String line) {
		CloturedVniInjectionControlRequest request = new CloturedVniInjectionControlRequest();

		String[] columns = line.split("\\t+");
		String[] priceDateTab = columns[0].split("/");
		String price = columns[2].contains(",") ? columns[2].replaceAll("\\,", ".") : columns[2];
		request.setPriceDate(priceDateTab[2] + "." + priceDateTab[1] + "." + priceDateTab[0]);
		request.setFundId(columns[1]);
		request.setPrice(new BigDecimal(price));
		request.setCurrency(columns[3]);
		request.setFundType(CloturedVNIFundType.FIDFAS.name());
		return request;
	}


}
