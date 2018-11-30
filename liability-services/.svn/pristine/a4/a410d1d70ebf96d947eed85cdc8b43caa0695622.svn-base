package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.tempuri.wssimport.WSSIMPORT;
import org.tempuri.wssimport.WssimportExport;
import org.tempuri.wssimport.WssimportExport.ExpGrpWinDsp;
import org.tempuri.wssimport.WssimportExport.ExpGrpWinDsp.Row;
import org.tempuri.wssimport.WssimportExport.ExpGrpWinDsp.Row.ExpItmWinWindow;
import org.tempuri.wssimport.WssimportImport;
import org.tempuri.wssimport.WssimportImport.ImportdetailsImportParameters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lu.wealins.liability.services.ws.rest.ClientInjectionRESTService;
import lu.wealins.common.dto.liability.services.ClientInjectionRequest;
import lu.wealins.common.dto.liability.services.ClientInjectionResponse;

@Component
public class ClientInjectionRESTServiceImpl implements ClientInjectionRESTService {

	@Autowired
	private WSSIMPORT liabilityWsImport;

	private final Logger log = LoggerFactory.getLogger(ClientInjectionRESTServiceImpl.class);

	@Override
	public ClientInjectionResponse runImport(SecurityContext context, ClientInjectionRequest liabilityClientRequest) {
		WssimportImport request = new WssimportImport();

		ImportdetailsImportParameters importdetailsImportParameters = new ImportdetailsImportParameters();
		importdetailsImportParameters.setFilePath(liabilityClientRequest.getFilePath());
		request.setImportdetailsImportParameters(importdetailsImportParameters);

		// Call web service client
		try {
			WssimportExport importResponse = liabilityWsImport.wssimportcall(request);

			logImportResponse(liabilityClientRequest, importResponse);

			return createLiabilityClientResponse(importResponse);

		} catch (Exception e) {
			ClientInjectionResponse response = new ClientInjectionResponse();
			response.setErrorMessage(e.getMessage());

			return response;
		}
	}

	private ClientInjectionResponse createLiabilityClientResponse(WssimportExport importResponse) {
		ClientInjectionResponse response = new ClientInjectionResponse();

		// Case of success
		if (importResponse != null && importResponse.getExitState() == 0) {
			response.setSuccess(Boolean.TRUE);

		} else {

			ExpGrpWinDsp expGrpWinDsp = importResponse.getExpGrpWinDsp();
			String errorMessage = StringUtils.EMPTY;

			if (expGrpWinDsp != null && !CollectionUtils.isEmpty(expGrpWinDsp.getRows())) {

				for (Row row : expGrpWinDsp.getRows()) {
					if (row != null && row.getExpItmWinWindow() != null) {
						errorMessage += concateInformationRow(row.getExpItmWinWindow()) + " ";
					}
				}
			} else {
				if (importResponse.getEcomsCommunications() != null) {
					errorMessage = importResponse.getEcomsCommunications().getErrorNo() + " : " + importResponse.getEcomsCommunications().getErrorParameters();
				}
			}
			response.setErrorMessage(errorMessage);
			log.error("Call WS WsImport : " + errorMessage);
		}

		return response;
	}

	private String concateInformationRow(ExpItmWinWindow expItmWinWindow) {
		return expItmWinWindow.getDesc25() + " : " + expItmWinWindow.getDesc252();
	}

	private void logImportResponse(ClientInjectionRequest liabilityClientRequest, WssimportExport importResponse) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String answerToJson = ow.writeValueAsString(importResponse);

		log.info("Response for " + liabilityClientRequest.getFilePath() + " : " + answerToJson);
	}

}
