package lu.wealins.batch.extract;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.utils.FidLinesFactoryUtils;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * 
 * @author xqv99
 *
 */

public class ExtractLissiaFIDTaskLet implements Tasklet {

	private static final String EXTRACT_LISSIA_FID_URL = "liability/fund/extract-lissia-fid";
	private static final String UPDATE_LISSIA_FID_FLAG_URL = "liability/fund/update-lissia-fid-flag";
	private final static String FILE_EXTENSION = ".txt";

	private final static String TIERS_BANQUE = "01-tiers/01_Tiers_Banque";
	private final static String CPTES_DEPOT_PTF_FID = "02-cptes_depots/02_Cptes_depots_PTF_FID";
	private final static String CPTES_BANCAIRES_PTF_FID = "03-cptes_bancaires/03_Cptes_bancaires_PTF_FID";
	private final static String CPTES_TITRES_FID = "04-cptes_titres/04_Cptes_Titres_FID";
	private final static String CONTRATS_PTF_FD = "05-contrats/05_Contrats_PTF_FD";
	private final static String VALEURS_FID = "06-valeurs/06_valeurs_FID";
	private final static String INJ_PRIX = "07-cours_valeurs/07_inj_prix";
	private final static String ERROR_PATH = "error/errorExtractLissiaFID";

	private Log logger = LogFactory.getLog(ExtractLissiaFIDTaskLet.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	/**
	 * Folder with the output files
	 */
	@Value("${lissiaFundFidFolder}")
	private String lissiaFundFidFolder;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private FidLinesFactoryUtils fidLinesFactoryUtils;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<FundDTO> funds = null;
		try {
			funds = extractFidFund();
			if (funds != null && !funds.isEmpty()) {
				logger.info("Trying to create fid files");
				for (FundDTO fund : funds)
					if (isFundDataOK(fund))
						writeFundFid(fund);
				logger.info("Successfully created fid files");

				updateLissiaFlag(funds.stream().filter(fund -> isFundDataOK(fund)).map(fund -> fund.getFdsId()).collect(Collectors.toList()));

			} else {
				logger.info("Empty result");
			}
		} catch (Exception e) {
			writeDownExceptionError(e);
			throw e;
		}
		return RepeatStatus.FINISHED;
	}

	private boolean isFundDataOK(FundDTO fund) {
		boolean isOk = StringUtils.isNotBlank(fund.getAccountRoot());
		isOk = isOk && StringUtils.isNotBlank(fund.getCurrency());
		isOk = isOk && StringUtils.isNotBlank(fund.getFundClassification());
		isOk = isOk && StringUtils.isNotBlank(fund.getGroupingCode());
		isOk = isOk && StringUtils.isNotBlank(fund.getDepositBankAgent() == null ? StringUtils.EMPTY : fund.getDepositBankAgent().getPaymentAccountBic());
		isOk = isOk && StringUtils.isNotBlank(fund.getDepositBankAgent() == null ? StringUtils.EMPTY : fund.getDepositBankAgent().getName());
		if (!isOk)
			logger.warn(fund.toString());
		return isOk;
	}

	private void writeDownExceptionError(Exception e) {
		List<String> lines = new ArrayList<>();
		lines.add(e.toString());
		try {
			writeLines(lines, formatPath(ERROR_PATH));
		} catch (IOException e1) {
			logger.error("Error when generating file error, first exception = " + e + " \n  second exception =" + e1);
		}
	}

	private void updateLissiaFlag(List<String> fundIds) throws Exception {
		Long result = null;
		logger.info("Trying to update Lissia exported fund flag...");
		ParameterizedTypeReference<Long> typeRef = new ParameterizedTypeReference<Long>() {};
		ResponseEntity<Long> response = RestCallUtils.postRest(getPiaRootContextURL() + UPDATE_LISSIA_FID_FLAG_URL, fundIds, String.class,
				typeRef, keycloackUtils, logger);
		if (response.getStatusCode().is2xxSuccessful()) {
			result = response.getBody();
			logger.info("Successfully updated Lissia exported fund flag, records updated = " + result);
		} else {
			logger.error("Failed to update Lissia exported fund flag");
			throw new Exception(response.getStatusCode().getReasonPhrase());
		}
	}

	private void writeFundFid(FundDTO fund) throws IOException {
		writeLines(fidLinesFactoryUtils.tiersBanqueLineFactory(fund), formatPath(TIERS_BANQUE, fund.getFdsId()));
		writeLines(fidLinesFactoryUtils.cptesDepotsLineFactory(fund), formatPath(CPTES_DEPOT_PTF_FID, fund.getFdsId()));
		writeLines(fidLinesFactoryUtils.cptesBancairesLineFactory(fund), formatPath(CPTES_BANCAIRES_PTF_FID, fund.getFdsId()));
		writeLines(fidLinesFactoryUtils.cptesTitresLineFactory(fund), formatPath(CPTES_TITRES_FID, fund.getFdsId()));
		writeLines(fidLinesFactoryUtils.contratsPTFLineFactory(fund), formatPath(CONTRATS_PTF_FD, fund.getFdsId()));
		writeLines(fidLinesFactoryUtils.valeursFidLineFactory(fund), formatPath(VALEURS_FID, fund.getFdsId()));
		writeLines(fidLinesFactoryUtils.injPrixLineFactory(fund), formatPath(INJ_PRIX, fund.getFdsId()));
	}

	private void writeLines(List<String> lines, String path) throws IOException {
		Charset charset = StandardCharsets.UTF_8;

		Path folder = Paths.get(lissiaFundFidFolder);
		Path filePath = folder.resolve(path);
		try {
			Files.createDirectories(filePath.getParent());

			try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
				for (String line : lines) {
					writer.write(line);
					writer.newLine();
				}
			}
		} catch (IOException e) {
			logger.error("Error when writing fid files" + e);
			throw e;
		}
	}

	private List<FundDTO> extractFidFund() throws Exception {
		List<FundDTO> datas;
		ResponseEntity<List<FundDTO>> response = get(getPiaRootContextURL() + EXTRACT_LISSIA_FID_URL);
		if (response.getStatusCode().is2xxSuccessful()) {
			datas = response.getBody();
		} else
			throw new Exception(response.getStatusCode().getReasonPhrase());
		return datas;
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<List<FundDTO>> get(String url) {
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate template = new RestTemplate();
		logger.info("Trying to extract Fid Fund ...");
		ParameterizedTypeReference<List<FundDTO>> typeRef = new ParameterizedTypeReference<List<FundDTO>>() {
		};
		ResponseEntity<?> response = template.exchange(url, HttpMethod.GET, entity, typeRef);
		logger.info("Successfully extracted Fid Fund ");
		return (ResponseEntity<List<FundDTO>>) response;
	}

	/**
	 * Get the current date
	 * 
	 * @return the current date
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return sdf.format(date);
	}

	private String formatPath(String filename, String id) {
		return filename + "_" + id + getCurrentDate() + FILE_EXTENSION;
	}

	private String formatPath(String filename) {
		return filename + "_" + getCurrentDate() + FILE_EXTENSION;
	}

	/**
	 * @return the piaRootContextURL
	 */
	public String getPiaRootContextURL() {
		return piaRootContextURL;
	}

	/**
	 * @param piaRootContextURL the piaRootContextURL to set
	 */
	public void setPiaRootContextURL(String piaRootContextURL) {
		this.piaRootContextURL = piaRootContextURL;
	}

}
