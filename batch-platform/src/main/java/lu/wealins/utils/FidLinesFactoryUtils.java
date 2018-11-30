package lu.wealins.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundPriceDTO;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;

@Component
public class FidLinesFactoryUtils {	
	Log logger = LogFactory.getLog(FidLinesFactoryUtils.class);
	
	private final String FD = "FD";
	private final String FID = "FID";
	public final int COUNTRY_SWIFT_END = 6;
	public final int COUNTRY_SWIFT_START = 4;
	private final String SEPARATOR = "\t";
	public final String C_CLE = "CLE";
	public final String C_ISIN = "ISIN";
	public final String C_INTERNE = "INTERNE";
	private final String C_GR1 = "GR1";
	public final String C_TCV = "TCV";
	public final String C_AUT = "AUT";
	public final String C_GESSEP = "GESSEP";
	public final String C_L = "L";
	public final String C_PMP = "PMP";
	public final String C_B23 = "B23";
	public final String C_DEDIE = "DEDIE";
	public final String C_UNI = "UNI";
	public final String C_SJDEDIE = "SJDEDIE";
	public final String C_PTFCLIE = "PTFCLIE";
	public final String C_PTF = "PTF";
	public final String C_MIRCLIE = "MIRCLIE";
	public final String C_MIR = "MIR";
	public final String C_DLP = "DLP";
	public final String C_N = "N";
	public final String C_COURANT = "COURANT";
	public final String C_INJECT = "INJECT";
	private final String C_EUR = "EUR";
	public final String C_100 = "100";
	public final String C_JOU = "JOU";
	public final String C_AJOUT = "AJOUT";
	public final String C_DEPOSIT = "DEPOSIT";
	public final String C_EN = "EN";
	public final String C_FINANC = "FINANC";
	public final String C_SLI = "SLI";
	public final String C_Y = "Y";
	public final String C_BASE = "BASE";
	public final String C_IWIENT = "IWIENT";
	public final String C_DEPOT = "DEPOT";
	public final String EMPTY = "";
	public final String TAX = "TAX";
	public final String FR_GES = "FR_GES";
	public final String FR_MIN = "FR_MIN";
	public final String FR_MAX = "FR_MAX";
	public final String TAX_BQ = "TAX_BQ";
	public final String FR_BQE = "FR_BQE";
	public final String PERCENT = "%";
	private final int CPTE_TITRES_LINES_TOTAL = 119;
	private final int CPTES_BANCAIRES_LINES_TOTAL = 19;
	private final int CONTRATS_PTF_LINES_TOTAL = 60;
	private final int VALEURS_FID_LINES_TOTAL = 143;
	private final int INJ_PRIX_LINES_TOTAL = 8;
	private final int TIERS_BANQUE_LINES_TOTAL = 18;
	private final int CPTES_DEPOTS_PTF_LINES_TOTAL = 12;

	private final String[] currencies = {"AUD","CAD","CHF","DKK","EUR","GBP","JPY","NOK","SEK","USD"};	

	private static final String WEBIA_LOAD_APPLI_PARAM = "webia/applicationParameter/";

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	public List<String> tiersBanqueLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		if (fund == null)
			return result;
		AgentDTO agent = fund.getDepositBankAgent();
		String swiftCode = agent == null ? EMPTY : agent.getPaymentAccountBic().trim();
		// Line 1
		String[] line1 = new String[TIERS_BANQUE_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);

		line1[0] = rightPadString(C_AJOUT, -1);
		line1[1] = rightPadString(swiftCode, -1);
		line1[2] = rightPadString(agent == null ? EMPTY : agent.getName(), -1);
		line1[3] = rightPadString(C_DEPOSIT, -1);
		line1[4] = rightPadString(C_EN, -1);
		line1[5] = rightPadString(C_FINANC, -1);
		line1[6] = rightPadString(C_SLI, -1);
		line1[7] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line1[8] = rightPadString(C_Y, -1);
		line1[10] = rightPadString(swiftCode + "XXX", -1);
		line1[11] = rightPadString(C_Y, -1);
		line1[13] = rightPadString(C_Y, -1);
		line1[15] = rightPadString(C_Y, -1);
		line1[17] = rightPadString(C_BASE, -1);

		result.add(String.join(SEPARATOR, line1));

		return result;

	}

	public List<String> cptesDepotsLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		if (fund == null)
			return result;
		AgentDTO agent = fund.getDepositBankAgent();
		String swiftCode = agent == null ? EMPTY : agent.getPaymentAccountBic().trim();
		// Line 1
		String[] line1 = new String[CPTES_DEPOTS_PTF_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);

		line1[0] = rightPadString(C_AJOUT, -1);
		line1[1] = rightPadString(C_IWIENT, -1);
		line1[2] = rightPadString("CD FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[3] = rightPadString("CD FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[4] = rightPadString(C_DEPOT, -1);
		line1[5] = rightPadString(swiftCode, -1);
		line1[7] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);

		result.add(String.join(SEPARATOR, line1));
		// line 2
		String[] line2 = new String[CPTES_DEPOTS_PTF_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line2, EMPTY);
		line2[0] = rightPadString(C_AJOUT, -1);
		line2[1] = rightPadString(C_IWIENT, -1);
		line2[2] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[3] = rightPadString("CD SJ FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[4] = rightPadString(C_DEPOT, -1);
		line2[5] = rightPadString(swiftCode, -1);
		line2[7] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);

		result.add(String.join(SEPARATOR, line2));

		return result;

	}

	public List<String> cptesBancairesLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		if (fund == null)
			return result;
		AgentDTO agent = fund.getDepositBankAgent();
		String swiftCode = agent == null ? EMPTY : agent.getPaymentAccountBic().trim();
		// Line 1
		String[] line1 = new String[CPTES_BANCAIRES_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);

		line1[0] = rightPadString(C_AJOUT, -1);
		line1[1] = rightPadString(C_IWIENT, -1);
		line1[2] = rightPadString("CB FD" + "_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[3] = rightPadString("CB FD" + "_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[4] = rightPadString(C_COURANT, -1);
		line1[5] = rightPadString(C_N, -1);
		line1[6] = rightPadString(fund.getCurrency(), -1);
		line1[7] = rightPadString(swiftCode, -1);
		line1[9] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line1[10] = rightPadString(C_N, -1);
		line1[11] = rightPadString(C_N, -1);

		result.add(String.join(SEPARATOR, line1));
		// line 2
		String[] line2 = new String[CPTES_BANCAIRES_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line2, EMPTY);

		line2[0] = rightPadString(C_AJOUT, -1);
		line2[1] = rightPadString(C_IWIENT, -1);
		line2[2] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[3] = rightPadString("CB SJ FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[4] = rightPadString(C_COURANT, -1);
		line2[5] = rightPadString(C_N, -1);
		line2[6] = rightPadString(fund.getCurrency(), -1);
		line2[7] = rightPadString(swiftCode, -1);
		line2[9] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line2[10] = rightPadString(C_N, -1);
		line2[11] = rightPadString(C_N, -1);

		result.add(String.join(SEPARATOR, line2));

		for(int i = 0; i < currencies.length; i++) {
			if(!currencies[i].equals(fund.getCurrency())) {
				// other lines
				String[] line = new String[CPTES_BANCAIRES_LINES_TOTAL];
				// set total column empty
				Arrays.fill(line, EMPTY);

				line[0] = rightPadString(C_AJOUT, -1);
				line[1] = rightPadString(C_IWIENT, -1);
				line[2] = rightPadString(fund.getAccountRoot() + "_" + currencies[i], -1);
				line[3] = rightPadString("CB SJ FD_" + fund.getAccountRoot() + "_" + currencies[i], -1);
				line[4] = rightPadString(C_COURANT, -1);
				line[5] = rightPadString(C_N, -1);
				line[6] = rightPadString(currencies[i], -1);
				line[7] = rightPadString(swiftCode, -1);
				line[9] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
				line[10] = rightPadString(C_N, -1);
				line[11] = rightPadString(C_N, -1);

				result.add(String.join(SEPARATOR, line));
			}
		}

		return result;

	}

	public List<String> cptesTitresLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		if (fund == null)
			return result;
		AgentDTO agent = fund.getDepositBankAgent();
		String swiftCode = agent == null ? EMPTY : agent.getPaymentAccountBic().trim();
		String formatedSwiftCode = formatSwiftCode(swiftCode);
		// Line 1
		String[] line1 = new String[CPTE_TITRES_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);

		line1[0] = rightPadString(C_AJOUT, -1);
		line1[1] = rightPadString(C_DLP, -1);
		line1[2] = rightPadString("MIR FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[3] = rightPadString("MIR FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[4] = rightPadString(C_MIR, -1);
		line1[5] = rightPadString(C_MIRCLIE, -1);
		line1[6] = rightPadString(C_N, -1);
		line1[8] = rightPadString(C_Y, -1);
		line1[28] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line1[29] = rightPadString("CD FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[33] = rightPadString(swiftCode, -1);
		line1[34] = rightPadString(swiftCode, -1);

		result.add(String.join(SEPARATOR, line1));

		// Line 2
		String[] line2 = new String[CPTE_TITRES_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line2, EMPTY);

		line2[0] = rightPadString(C_AJOUT, -1);
		line2[1] = rightPadString(C_DLP, -1);
		line2[2] = rightPadString("MIR_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[3] = rightPadString("MIR SJ_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[4] = rightPadString(C_MIR, -1);
		line2[5] = rightPadString(C_MIRCLIE, -1);
		line2[6] = rightPadString(C_N, -1);
		line2[8] = rightPadString(C_Y, -1);
		line2[28] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line2[29] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[33] = rightPadString(swiftCode, -1);
		line2[34] = rightPadString(swiftCode, -1);

		result.add(String.join(SEPARATOR, line2));

		// Line 3
		String[] line3 = new String[CPTE_TITRES_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line3, EMPTY);

		line3[0] = rightPadString(C_AJOUT, -1);
		line3[1] = rightPadString(C_DLP, -1);
		line3[2] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line3[3] = rightPadString("SJ FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line3[4] = rightPadString(C_PTF, -1);
		line3[5] = rightPadString(C_PTFCLIE, -1);
		line3[6] = rightPadString(C_N, -1);
		line3[7] = rightPadString(C_SJDEDIE, -1);
		line3[8] = rightPadString(C_Y, -1);
		line3[10] = rightPadString(C_PMP, -1);
		line3[11] = rightPadString(C_B23, -1);
		line3[12] = rightPadString(C_Y, -1);
		line3[13] = rightPadString(C_N, -1);
		line3[14] = rightPadString(C_N, -1);
		line3[15] = rightPadString(C_N, -1);
		line3[16] = rightPadString(C_N, -1);
		line3[17] = rightPadString(C_GESSEP, -1);
		line3[18] = rightPadString(C_BASE, -1);
		line3[19] = rightPadString(C_BASE, -1);
		line3[21] = rightPadString(C_N, -1);
		line3[26] = rightPadString(C_N, -1);
		line3[30] = rightPadString("MIR_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line3[38] = rightPadString(C_Y, -1);
		line3[39] = rightPadString(C_DEDIE, -1);
		line3[40] = rightPadString(formatedSwiftCode, -1);
		line3[43] = rightPadString(fund.getCurrency(), -1);
		line3[51] = rightPadString(formatedSwiftCode, -1);
		line3[97] = rightPadString(C_L + fund.getGroupingCode(), -1);
		line3[98] = rightPadString(C_UNI, -1);
		line3[99] = rightPadString("TYPE " + fund.getFundClassification(), -1);

		result.add(String.join(SEPARATOR, line3));

		// Line 4
		String[] line4 = new String[CPTE_TITRES_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line4, EMPTY);

		line4[0] = rightPadString(C_AJOUT, -1);
		line4[1] = rightPadString(C_DLP, -1);
		line4[2] = rightPadString("FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line4[3] = rightPadString("FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line4[4] = rightPadString(C_PTF, -1);
		line4[5] = rightPadString(C_PTFCLIE, -1);
		line4[6] = rightPadString(C_N, -1);
		line4[7] = rightPadString(C_DEDIE, -1);
		line4[8] = rightPadString(C_Y, -1);
		line4[10] = rightPadString(C_PMP, -1);
		line4[11] = rightPadString(C_B23, -1);
		line4[12] = rightPadString(C_Y, -1);
		line4[13] = rightPadString(C_N, -1);
		line4[14] = rightPadString(C_N, -1);
		line4[15] = rightPadString(C_N, -1);
		line4[16] = rightPadString(C_N, -1);
		line4[17] = rightPadString(C_GESSEP, -1);
		line4[18] = rightPadString(C_BASE, -1);
		line4[19] = rightPadString(C_BASE, -1);
		line4[21] = rightPadString(C_Y, -1);
		line4[26] = rightPadString(C_Y, -1);
		line4[30] = rightPadString("MIR FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line4[35] = rightPadString(fund.getCurrency(), -1);
		line4[36] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line4[37] = rightPadString("CB FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line4[43] = rightPadString(fund.getCurrency(), -1);
		line4[50] = rightPadString(C_B23, -1);
		line4[59] = rightPadString("0", -1);
		line4[98] = rightPadString(C_AUT, -1);

		String agentId = getApplicationParameter("BKDEP_BIL_SOLIAM").getValue();
		String BILVatRate = getApplicationParameter("BKDEP_SOLIAM_BIL").getValue();
		String OthersVatRate = getApplicationParameter("BKDEP_SOLIAM_AUTRES").getValue();
		String vatRate;
		if (agentId.equals(agent.getAgtId())) {
			vatRate = BILVatRate;
		} else {
			vatRate = OthersVatRate;
		}
		line4[105] = rightPadString(TAX, -1);
		line4[106] = rightPadString(vatRate, -1);
		line4[109] = rightPadString(FR_GES, -1);
		if (Objects.nonNull(fund.getAssetManagerFee())) {
			line4[110] = rightPadString(StringUtils.join(new String[] {fund.getAssetManagerFee().toString(), PERCENT}), -1);
		} else if (Objects.nonNull(fund.getFinFeesFlatAmount())) {
			line4[110] = rightPadString(StringUtils.join(new String[] {fund.getFinFeesFlatAmount().toString(), fund.getAssetManFeeCcy()}), -1);
		}
		line4[111] = rightPadString(FR_MIN, -1);
		if (Objects.nonNull(fund.getFinFeesMinAmount())) {
			line4[112] = rightPadString(fund.getFinFeesMinAmount().toString(), -1);
		}
		line4[113] = rightPadString(FR_MAX, -1);
		if (Objects.nonNull(fund.getFinFeesMaxAmount())) {
			line4[114] = rightPadString(fund.getFinFeesMaxAmount().toString(), -1);
		}
		line4[115] = rightPadString(TAX_BQ, -1);
		line4[116] = rightPadString(vatRate, -1);
		line4[117] = rightPadString(FR_BQE, -1);
		if (Objects.nonNull(fund.getBankDepositFee())) {
			line4[118] = rightPadString(StringUtils.join(new String[] {fund.getBankDepositFee().toString(), PERCENT}), -1);
		} else if (Objects.nonNull(fund.getDepositBankFlatFee())) {
			line4[118] = rightPadString(StringUtils.join(new String[] {fund.getDepositBankFlatFee().toString(), fund.getBankDepFeeCcy()}), -1);
		}
		

		result.add(String.join(SEPARATOR, line4));

		return result;

	}

	public List<String> contratsPTFLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		if (fund == null)
			return result;
		AgentDTO agent = fund.getDepositBankAgent();
		String swiftCode = agent == null ? EMPTY : agent.getPaymentAccountBic().trim();

		// Line 1
		String[] line1 = new String[CONTRATS_PTF_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);
		line1[0] = rightPadString(C_AJOUT, -1);
		line1[1] = rightPadString(C_DLP, -1);
		line1[2] = rightPadString("CT" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[3] = rightPadString("CT" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[4] = rightPadString("CT FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[5] = rightPadString(C_COURANT, -1);
		line1[6] = rightPadString("FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[7] = rightPadString(fund.getCurrency(), -1);
		line1[8] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line1[9] = rightPadString("CB FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[10] = rightPadString(swiftCode, -1);
		line1[11] = rightPadString(C_Y, -1);
		line1[12] = rightPadString(C_N, -1);

		result.add(String.join(SEPARATOR, line1));

		// Line 2
		String[] line2 = new String[CONTRATS_PTF_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line2, EMPTY);

		line2[0] = rightPadString(C_AJOUT, -1);
		line2[1] = rightPadString(C_DLP, -1);
		line2[2] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[3] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[4] = rightPadString("CT SJ FD_" + fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[5] = rightPadString(C_COURANT, -1);
		line2[6] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[7] = rightPadString(fund.getCurrency(), -1);
		line2[8] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line2[9] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line2[10] = rightPadString(swiftCode, -1);
		line2[11] = rightPadString(C_Y, -1);
		line2[12] = rightPadString(C_Y, -1);
		line2[32] = rightPadString(fund.getGroupingCode().replace('/', '0').replaceAll("[^0-9]", "") + "_E2", -1);
		line2[59] = rightPadString("L" + fund.getGroupingCode(), -1);

		result.add(String.join(SEPARATOR, line2));

		for(int i = 0; i < currencies.length; i++) {
			if(!currencies[i].equals(fund.getCurrency())) {
				// other lines
				String[] line = new String[CONTRATS_PTF_LINES_TOTAL];
				// set total column empty
				Arrays.fill(line, EMPTY);

				line[0] = rightPadString(C_AJOUT, -1);
				line[1] = rightPadString(C_DLP, -1);
				line[2] = rightPadString(fund.getAccountRoot() + "_" + currencies[i], -1);
				line[3] = rightPadString(fund.getAccountRoot() + "_" + currencies[i], -1);
				line[4] = rightPadString("CT SJ FD_" + fund.getAccountRoot() + "_" + currencies[i], -1);
				line[5] = rightPadString(C_COURANT, -1);
				line[6] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
				line[7] = rightPadString(currencies[i], -1);
				line[8] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
				line[9] = rightPadString(fund.getAccountRoot() + "_" + currencies[i], -1);
				line[10] = rightPadString(swiftCode, -1);
				line[11] = rightPadString(C_Y, -1);
				line[12] = rightPadString(C_Y, -1);
				line[32] = rightPadString(fund.getGroupingCode().replace('/', '0').replaceAll("[^0-9]", "") + "_E2", -1);
				line[59] = rightPadString("L" + fund.getGroupingCode(), -1);

				result.add(String.join(SEPARATOR, line));
			}
		}

		return result;

	}

	public List<String> valeursFidLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		if (fund == null)
			return result;
		AgentDTO agent = fund.getDepositBankAgent();
		String swiftCode = agent == null ? EMPTY : agent.getPaymentAccountBic().trim();
		String formatedSwiftCode = formatSwiftCode(swiftCode);

		// Line 1
		String[] line1 = new String[VALEURS_FID_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);

		line1[0] = rightPadString(C_AJOUT, -1);
		line1[1] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[2] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[3] = isFid(fund) ? rightPadString(FD, -1) : rightPadString(fund.getFundSubType(), -1);
		line1[5] = rightPadString(C_IWIENT, -1);
		line1[6] = rightPadString(StringUtils.substring(swiftCode, COUNTRY_SWIFT_START, COUNTRY_SWIFT_END), -1);
		line1[9] = rightPadString(fund.getCurrency(), -1);
		line1[10] = rightPadString(fund.getCurrency(), -1);
		line1[12] = rightPadString("5", -1);
		line1[20] = rightPadString(C_TCV, -1);
		line1[21] = rightPadString(C_JOU, -1);
		line1[58] = rightPadString(C_CLE, -1);
		line1[59] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[60] = rightPadString(C_INTERNE, -1);
		line1[61] = rightPadString(fund.getFdsId(), -1);
		line1[62] = rightPadString(C_ISIN, -1);
		line1[63] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[74] = rightPadString(C_GR1, -1);
		line1[75] = rightPadString(formatedSwiftCode, -1);
		line1[141] = rightPadString(C_DLP, -1);
		line1[142] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);

		result.add(String.join(SEPARATOR, line1));

		return result;
	}

	private String formatSwiftCode(String swiftCode) {
		return swiftCode.length() < 6 ? EMPTY : swiftCode.substring(0, 3) + swiftCode.charAt(4);
	}

	private boolean isFid(FundDTO fund) {
		return FID.equalsIgnoreCase(fund.getFundSubType());
	}

	public List<String> injPrixLineFactory(FundDTO fund) {
		List<String> result = new ArrayList<>();
		// Line 1
		String[] line1 = new String[INJ_PRIX_LINES_TOTAL];
		// set total column empty
		Arrays.fill(line1, EMPTY);
		FundPriceDTO minFundPrice = getMinFundPrice(fund);
		line1[0] = rightPadString(C_ISIN, -1);
		line1[1] = rightPadString(fund.getAccountRoot() + "_" + fund.getCurrency(), -1);
		line1[2] = rightPadString(C_JOU, -1);
		line1[3] = rightPadString(minFundPrice.getPrice().toString(), -1);
		line1[4] = rightPadString(fund.getCurrency(), -1);

		if (minFundPrice != null && minFundPrice.getDate0() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			line1[5] = rightPadString(dateFormat.format(minFundPrice.getDate0()), -1);
		} else {
			line1[5] = rightPadString(EMPTY, -1);
		}

		line1[7] = rightPadString(C_INJECT, -1);

		result.add(String.join(SEPARATOR, line1));

		return result;

	}

	public FundPriceDTO getMinFundPrice(FundDTO fund) {
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(piaRootContextURL + "liability/fundPrice/minFundPrice/" + fund.getFdsId()).build();

		RestTemplate template = new RestTemplate();
		ResponseEntity<FundPriceDTO> response = template.exchange(uriComponents.toUri(), HttpMethod.GET, entity, FundPriceDTO.class);

		if (response != null && response.getBody() != null) {
			return response.getBody();
		}

		return null;
	}

	private String rightPadString(String string, int length) {
		if (string == null)
			return EMPTY;
		if (length <= 0)
			return string;
		return StringUtils.rightPad(string, length);
	}

	/**
	 * get method for application parameter.
	 * 
	 * @param queryParams
	 */
	private ApplicationParameterDTO getApplicationParameter(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ParameterizedTypeReference<ApplicationParameterDTO> typeRef = new ParameterizedTypeReference<ApplicationParameterDTO>() {};
		logger.info("Trying to get application parameter from Webia DB ...");
		ResponseEntity<ApplicationParameterDTO> response = RestCallUtils.get(getPiaRootContextURL() + WEBIA_LOAD_APPLI_PARAM + "/" + code, params, ApplicationParameterDTO.class, typeRef, keycloackUtils, logger);
		ApplicationParameterDTO datas = response.getBody();
		logger.info("Successfully got application parameter from Webia DB");
		return datas;
	}

	/**
	 * @return the piaRootContextURL
	 */
	public String getPiaRootContextURL() {
		return piaRootContextURL;
	}

	/**
	 * @param piaRootContextURL
	 *            the piaRootContextURL to set
	 */
	public void setPiaRootContextURL(String piaRootContextURL) {
		this.piaRootContextURL = piaRootContextURL;
	}
}
