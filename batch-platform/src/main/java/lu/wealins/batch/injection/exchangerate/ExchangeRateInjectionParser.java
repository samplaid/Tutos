package lu.wealins.batch.injection.exchangerate;

import java.io.File;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lu.wealins.batch.injection.AbstractInjectionParser;
import lu.wealins.camel.utils.MessageUtils;
import lu.wealins.rest.model.ExchangeRateInjectionControlRequest;

import javax.jms.Message;
import javax.jms.MessageListener;
/**
 * @author xqv66
 *
 */
@Component
public class ExchangeRateInjectionParser extends AbstractExchangeRateInjectionParser<ExchangeRateInjectionControlRequest> implements MessageListener {
	private final static String INJECTION_DATE_PROPERTY = "injectionDate";
	private static final String LIABILITY_SERVICES_EXCHANGE_RATE_INJECTION_CONTROL = "liability-servicesExchangeRateInjectionControl";

	private String injectionDate;
	
	@Value("${exchangeRateInjectionSuccessPath:}")
	private String exchangeRateInjectionSuccessPath;

	@Value("${exchangeRateInjectionFailurePath:}")
	private String exchangeRateInjectionFailurePath;

	@Override
	public String getInjectionControlUrl() {
		return LIABILITY_SERVICES_EXCHANGE_RATE_INJECTION_CONTROL;
	}

	@Override
	public String getInjectionFailurePath() {
		return exchangeRateInjectionFailurePath;
	}

	@Override
	public String getInjectionSuccessPath() {
		return exchangeRateInjectionSuccessPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.batch.injection.AbstractInjectionParser#buildInjectionControlRequest(java.lang.String)
	 * 
	 * Example of line (from_currency,to_currency,rate,date0) : EUR,CAD,1.4587,07/09/2017
	 */
	@Override
	public ExchangeRateInjectionControlRequest buildInjectionControlRequest(String line) {
		ExchangeRateInjectionControlRequest request = new ExchangeRateInjectionControlRequest();

		String[] columns = line.split(SEMICOLON_DELIMITER);
		
		if(injectionDate == null) {
			String[] date = columns[5].split(" ")[0].split("/");
			request.setDate(date[2] + "." + date[1] + "." + date[0]);
		}
		else {
			request.setDate(injectionDate);
		}
		
		request.setFromCurrency(columns[1]);
		request.setToCurrency(columns[2]);
		request.setExchangeRate(new BigDecimal(columns[3]));

		return request;
	}

	@Override
	public void onMessage(Message message) {
		try {
			injectionDate = message.getStringProperty(INJECTION_DATE_PROPERTY);
			
			if(injectionDate != null) {
				if(!injectionDate.matches("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}")) {
					System.err.println(injectionDate + " is not a valid format (must be yyyy.MM.dd)");
					return;
				}
				System.out.println("Using date " + injectionDate);
			}
			
			File file = MessageUtils.saveTempFile(message);
			parse(file);
			file.delete();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
