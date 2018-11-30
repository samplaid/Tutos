package lu.wealins.liability.business.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tempuri.wssimport.Exception_Exception;
import org.tempuri.wssimport.WSSIMPORT;
import org.tempuri.wssimport.WssimportExport;
import org.tempuri.wssimport.WssimportImport;
import org.tempuri.wssimport.WssimportImport.ImportdetailsImportParameters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/mock/dev/test/clientInjectionRESTServiceTestContext.xml", "classpath:mock/dev/mockProperties.xml" })
public class ClientInjectionRESTServiceTest {

	@Autowired
	WSSIMPORT liabilityWSImport;

	@Test
	public void test() {
		WssimportImport request = new WssimportImport();
		ImportdetailsImportParameters importdetailsImportParameters = new ImportdetailsImportParameters();
		importdetailsImportParameters.setFilePath("test_import2.csv");
		request.setImportdetailsImportParameters(importdetailsImportParameters);

		WssimportExport response;
		try {
			response = liabilityWSImport.wssimportcall(request);
			response.getEcomsCommunications();

		} catch (Exception_Exception e) {
			e.printStackTrace();
		}
	}

}
