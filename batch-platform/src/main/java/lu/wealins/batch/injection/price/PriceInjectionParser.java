package lu.wealins.batch.injection.price;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lu.wealins.batch.injection.AbstractInjectionParser;
import lu.wealins.rest.model.PriceInjectionControlRequest;

/**
 * @author xqv66
 *
 */
@Component
public class PriceInjectionParser extends AbstractInjectionParser<PriceInjectionControlRequest> {

	private static final String LIABILITY_SERVICES_PRICE_INJECTION_CONTROL = "liability-servicesPriceInjectionControl";

	@Value("${priceInjectionSuccessPath:}")
	private String priceInjectionSuccessPath;

	@Value("${priceInjectionFailurePath:}")
	private String priceInjectionFailurePath;

	@Override
	public String getInjectionControlUrl() {
		return LIABILITY_SERVICES_PRICE_INJECTION_CONTROL;
	}

	@Override
	public String getInjectionFailurePath() {
		return priceInjectionFailurePath;
	}

	@Override
	public String getInjectionSuccessPath() {
		return priceInjectionSuccessPath;
	}

	/*
	 * 1(non-Javadoc)
	 * 
	 * @see lu.wealins.batch.injection.AbstractInjectionParser#buildInjectionControlRequest(java.lang.String)
	 * 
	 * Example format of a line : 30/06/2017 DE0009807057 52,69 EUR 0000036390 INJECT
	 */
	@Override
	public PriceInjectionControlRequest buildInjectionControlRequest(String line) {
		PriceInjectionControlRequest request = new PriceInjectionControlRequest();

		String[] columns = line.split("\\s+");
		String[] priceDateTab = columns[0].split("/");
		String price = columns[2].contains(",") ? columns[2].replaceAll("\\,", ".") : columns[2];
		request.setPriceDate(priceDateTab[2] + "." + priceDateTab[1] + "." + priceDateTab[0]);
		request.setIsinCode(columns[1]);
		request.setPrice(new BigDecimal(price));
		request.setCurrency(columns[3]);

		return request;
	}
}
