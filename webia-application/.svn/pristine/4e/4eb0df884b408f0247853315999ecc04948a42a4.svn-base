/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.core.exceptions.CsvFileIOException;
import lu.wealins.webia.core.service.WebiaCommissionPaymentSlipGeneratorService;
import lu.wealins.webia.core.service.WebiaCommissionsService;
import lu.wealins.webia.core.utils.FilesUtils;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestRequest;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestResponse;

/**
 * Default implementation of the {@link WebiaCommissionPaymentSlipGeneratorService} interface
 * 
 * @author ORO
 *
 */
@Service
public class WebiaCommissionPaymentSlipGeneratorServiceImpl implements WebiaCommissionPaymentSlipGeneratorService {
	private static final char COLON = ';';
	private static final String HYPHEN = "_";
	private static final String WEBIA_GET_PAYMENT_SLIP_DATA = "webia/paymentCommissionSlip/";
	private static final String UPDATE_EDITING_REQUEST = "updateEditingRequest";
	private static final Logger LOGGER = LoggerFactory.getLogger(WebiaCommissionPaymentSlipGeneratorServiceImpl.class);

	private static final String[] CSV_HEADERS = new String[] {
			"Montant", "Devise", "Communication", "IBAN_donneur", "BIC_donneur", "LIB_donneur", "IBAN_benef", "BIC_benef", "LIB_benef"
	};

	@Value("${commission.payment.slip.path}")
	private String csvFileLocation;

	@Autowired
	private WebiaCommissionsService commissionsService;

	@Autowired
	private RestClientUtils restClientUtils;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generatePaymentSlip(Long statementId) {
		Assert.notNull(statementId, "Generation payment error: The statement id is null");
		LOGGER.info("Generating the commission payment slip related to the statement id " + statementId);

		StatementDTO statement = commissionsService.getStatement(statementId.toString());

		if (Objects.isNull(statement)) {
			return StringUtils.EMPTY;
		}

		MultivaluedMap<String, Object> query = new MultivaluedHashMap<>();
		query.putSingle("statementId", statementId.toString());
		Response response = restClientUtils.get(WEBIA_GET_PAYMENT_SLIP_DATA, "findBy", query, Response.class);

		if (response.getStatus() != Status.OK.getStatusCode()) {
			return StringUtils.EMPTY;
		}

		String returnPath = StringUtils.EMPTY;
		Object object = response.getEntity();

		if (object instanceof List<?>) {
			List<String[]> dataRecords = convertToCsvDataRecordsStructure((List<?>) object);
			Consumer<CSVPrinter> csvPrinter = getCsvPrinter(statementId.toString(), dataRecords);

			if (Objects.nonNull(csvPrinter)) {
				try {
					String location = createFilePath(statement);
					FilesUtils.createCsvFile(location, CSVFormat.EXCEL.withDelimiter(COLON)
							.withIgnoreEmptyLines(true)
							.withTrim(true)
							.withHeader(CSV_HEADERS), csvPrinter);
					returnPath = location;
				} catch (IOException e) {
					LOGGER.error("Cannot create the commission payment slip related to the statement " + statementId.toString(), e);
				}
			}
		}

		return returnPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditingRequest generatePaymentSlip(EditingRequest editingRequest) {
		Assert.notNull(editingRequest, "Generation commission payment slip throws error: The editing request is null");
		Assert.notNull(editingRequest.getStatement(), "Generation commission payment slip throws error: The statement id inside the editing request is emtpy or null");
		Assert.isTrue(!editingRequest.getStatement().isEmpty(),
				"Generation commission payment slip throws error: The statement id inside the editing request is emtpy");

		StatementDTO statement = commissionsService.getStatement(editingRequest.getStatement());

		if (Objects.isNull(statement)) {
			return editingRequest;
		}

		MultivaluedMap<String, Object> query = new MultivaluedHashMap<>();
		query.putSingle("statementId", editingRequest.getStatement().trim());
		List<TransferDTO> response = restClientUtils.get(WEBIA_GET_PAYMENT_SLIP_DATA, "findBy", query, new GenericType<List<TransferDTO>>() {
		});

		if (response.isEmpty()) {
			LOGGER.error(
					"The payement slip has not been generated. No transfer record was not present related to the statement id "
							+ editingRequest.getStatement().trim() + ". Editing state will remain to 'NEW'");
			return editingRequest;
		}


		String transferIds = response.stream().map(t -> t.getTransferId().toString()).collect(Collectors.joining(","));
		editingRequest.setTransferIds(transferIds);
		EditingRequestStatus intialState = editingRequest.getStatus();
		editingRequest.setStatus(EditingRequestStatus.IN_PROGRESS);
		EditingRequest updatedRequest = updateEditingRequest(editingRequest);
		editingRequest.setStatus(intialState);


		List<String[]> dataRecords = convertToCsvDataRecordsStructure(response);
		String path = "";

		try {
			Consumer<CSVPrinter> csvPrinter = getCsvPrinter(editingRequest.getStatement(), updatedRequest, dataRecords);
			path = createFilePath(statement);
			FilesUtils.createCsvFile(path, CSVFormat.EXCEL.withDelimiter(COLON)
					.withIgnoreEmptyLines(true)
					.withTrim(true)
					.withHeader(CSV_HEADERS),
					csvPrinter);
			updatedRequest.setOutputStreamPath(path);
			updatedRequest.setStatus(EditingRequestStatus.GENERATED);
		} catch (IOException e) {
			String errorMsg = "Cannot write the commission payment slip CSV related to the statement " + editingRequest.getStatement() + " in the location [" + path + "]";
			LOGGER.error(errorMsg, e);
			updatedRequest.setStatus(EditingRequestStatus.IN_ERROR);
			updateEditingRequest(updatedRequest);
			throw new CsvFileIOException(errorMsg, e);
		}

		return updateEditingRequest(updatedRequest);
	}

	private Consumer<CSVPrinter> getCsvPrinter(String statementId, List<String[]> dataRecords) {
		return (printer) -> {
			try {
				printer.printRecords(dataRecords);
			} catch (IOException e) {
				String errorMsg = "Cannot create the commission payment slip related to the statement " + statementId + ". Error was occurred during the print of the csv records.";
				LOGGER.error(errorMsg, e);
				throw new CsvFileIOException(errorMsg, e);
			}
		};
	}

	private Consumer<CSVPrinter> getCsvPrinter(String statementId, EditingRequest updatedRequest, List<String[]> dataRecords) {
		return (printer) -> {
			try {
				printer.printRecords(dataRecords);
			} catch (IOException e) {
				String errorMsg = "Cannot create the commission payment slip related to the statement " + statementId + ". Error was occurred during the print of the csv records.";
				LOGGER.error(errorMsg, e);
				updatedRequest.setStatus(EditingRequestStatus.IN_ERROR);
				updateEditingRequest(updatedRequest);
				throw new CsvFileIOException(errorMsg, e);
			}
		};
	}

	private List<String[]> convertToCsvDataRecordsStructure(List<?> list) {
		List<String[]> result = new ArrayList<>();

		for (Object object : list) {
			if (object instanceof TransferDTO) {
				TransferDTO transfer = (TransferDTO) object;

				String[] rows = new String[] {
						Objects.nonNull(transfer.getTrfMt()) ? transfer.getTrfMt().toString() : null,
						transfer.getTrfCurrency(),
						transfer.getTrfComm(),
						transfer.getIbanDonOrd(),
						transfer.getSwiftDonOrd(),
						transfer.getLibDonOrd(),
						transfer.getIbanBenef(),
						transfer.getSwiftBenef(),
						transfer.getLibBenef(),
				};

				result.add(rows);
			}
		}

		return result;
	}

	private EditingRequest updateEditingRequest(EditingRequest editingRequest) {
		UpdateEditingRequestRequest updateEditingRequestRequest = new UpdateEditingRequestRequest();
		updateEditingRequestRequest.setRequest(editingRequest);
		UpdateEditingRequestResponse updateEditingRequestResponse = restClientUtils.post(UPDATE_EDITING_REQUEST, updateEditingRequestRequest, UpdateEditingRequestResponse.class);
		return updateEditingRequestResponse.getRequest();
	}

	/**
	 * Create the file name using statement data.
	 * 
	 * @param statement the statement
	 * @return the file name
	 */
	private String createFileName(StatementDTO statement) {
		StringBuilder filenameBuilder = new StringBuilder("Payment_BROKER");
		filenameBuilder.append(HYPHEN);
		filenameBuilder.append(statement.getStatementType());
		filenameBuilder.append(HYPHEN);
		filenameBuilder.append(statement.getPeriod());
		filenameBuilder.append(HYPHEN);
		filenameBuilder.append(statement.getStatementId());
		filenameBuilder.append(HYPHEN);
		filenameBuilder.append(DateFormatUtils.format(new Date(), "yyyyddMMHHmmss"));

		return filenameBuilder.toString();
	}

	private String createFilePath(StatementDTO statement) {
		return csvFileLocation + FilenameUtils.separatorsToSystem(DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/" + createFileName(statement));
	}

}
