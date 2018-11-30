/**
 * 
 */
package lu.wealins.batch.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import lu.wealins.rest.model.common.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * @author NGA
 *
 */
public class CloturedVniItemWriter implements ItemWriter<AccountingNavToInject> {

	private Log logger = LogFactory.getLog(CloturedVniItemWriter.class);
	private static String INJECT_WEBIA_ACCOUNTING_NAV = "webia/accountingNav/injectCloturedVni";

	@Value("${piaRootContextURL}")
	String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Override
	public void write(List<? extends AccountingNavToInject> items) throws Exception {

		String url = piaRootContextURL + INJECT_WEBIA_ACCOUNTING_NAV;

		ParameterizedTypeReference<CloturedVniInjectionControlResponse> typeRef = new ParameterizedTypeReference<CloturedVniInjectionControlResponse>() {
		};

		items.stream().forEach(item -> {
			RestCallUtils.postRest(url, item,
					AccountingNavToInject.class, typeRef, keycloackUtils, logger);
		});

	}

}
