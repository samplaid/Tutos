package lu.wealins.batch.injection.kids;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.xml.sax.SAXException;

import lu.wealins.rest.model.BatchInjectionControlResponse;
import lu.wealins.rest.model.Error;
import lu.wealins.rest.model.KidsInjectionControlResponse;
import lu.wealins.rest.model.alfresco.InjectKidsFileRequest;
import lu.wealins.rest.model.alfresco.InjectKidsFileResponse;
import lu.wealins.rest.model.alfresco.KidsDocument;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * Control and inject kneip file (KIDS) into alfresco system
 * 
 * @author TEY
 *
 */
@EnableScheduling
public class KneipKidsTasklet implements Tasklet {

	/**
	 * Constant job name
	 */
	private static final String EXTRACT_JOB = "kneipKidsJob";

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	private static final String TYPE_KID_GENERIC = "TypeKIDGeneric";

	private static final String KID_INTERNATIONAL = "InternationalProduct";

	private static final String MISSING_METADATA = "MISSING METADATA";
	
	private static String INJECT_KIDS_ALFRESCO = "alfresco/kneipKids/injectKids";

	private Log logger = LogFactory.getLog(KneipKidsTasklet.class);

	/**
	 * Manage keycloack authentification
	 */
	@Autowired
	private KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Value("${receiptsSuccessFilePath}")
	private String receiptsSuccessFilePath;

	@Value("${kidsSuccessFilePath}")
	private String kidsSuccessFilePath;

	@Value("${failureFilePath}")
	private String failureFilePath;

	@Value("${receiptsCurrentFilePath}")
	private String receiptsCurrentFilePath;

	@Value("${kidsCurrentFilePath}")
	private String kidsCurrentFilePath;

	/**
	 * Requird metadata into kid file
	 */
	private List<String> metadatas;

	/**
	 * The path where correct kids files will be
	 */
	private Path kidsSuccessPath;

	/**
	 * The path where correct receipt files will be
	 */
	private Path receiptsSuccessPath;

	/**
	 * The path where incorrect files will be
	 */
	private Path failurePath;

	/**
	 * The path where files will be
	 */
	private Path kidsCurrentPath;

	/**
	 * The path where files will be
	 */
	private Path receiptCurrentPath;

	/**
	 * The required metadata into the PDF
	 */
	private List<String> requiredMetadata;


	/**
	 * Add cron on the job
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "${kneipCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the kneip Kids Job");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		initPaths();

		// Parse kids file
		Files.list(kidsCurrentPath).forEach(file -> {
			logger.info("Kids file parsed : " + file.getFileName());
			KidsInjectionControlResponse controleResponse = controleFile(file);

			if (controleResponse != null && BooleanUtils.isTrue(controleResponse.isSuccess())) {
				moveSuccessFile(file, controleResponse);
				// alfrescoInjection(file);
			} else { // If the control file failed
				moveFailedFile(file, controleResponse);

			}
			logger.info("End of parsing : " + file.getFileName());

		});

		// Parse receipt file
		Files.list(receiptCurrentPath).forEach(file -> {
			logger.info("Receipt file parsed : " + file.getFileName());
			try {
				Files.move(receiptCurrentPath.resolve(file.getFileName()), receiptsSuccessPath.resolve(file.getFileName()));
			} catch (IOException e) {
				logger.error("Result receipt file parsing " + file.getFileName() + " is failed : " + e.getMessage());
			}

			logger.info("End of parsing : " + file.getFileName());

		});

		return RepeatStatus.FINISHED;

	}

	/**
	 * Initialize paths
	 */
	private void initPaths() {
		receiptsSuccessPath = Paths.get(receiptsSuccessFilePath);
		kidsSuccessPath = Paths.get(kidsSuccessFilePath);
		failurePath = Paths.get(failureFilePath);
		kidsCurrentPath = Paths.get(kidsCurrentFilePath);
		receiptCurrentPath = Paths.get(receiptsCurrentFilePath);
	}

	/**
	 * Move the file in the appropriate arborescence
	 * 
	 * @param file the file to move
	 * @param controleResponse the information related to the file
	 */
	private void moveSuccessFile(Path file, KidsInjectionControlResponse controleResponse) {
		List<lu.wealins.rest.model.alfresco.Metadata> metadatas = controleResponse.getKidsDocument().getMetadatas();

		String product = getSpecificMetadataValue("Product", metadatas).trim();
		String investmentStrategy = getSpecificMetadataValue("InvestmentStrategy", metadatas).trim();
		String country = getSpecificMetadataValue("Country", metadatas).trim();
		String language = getSpecificMetadataValue("Language", metadatas).trim();
		String internationalProduct = getSpecificMetadataValue("InternationalProduct", metadatas);

		// If the document is related to International
		if (StringUtils.isNotEmpty(internationalProduct)) {
			country = "INT";
		}

		// Build path with the metadata
		String finalNameFile = product + "_";
		String pathDestination = kidsSuccessPath.toString() + File.separator + country + File.separator + product;
		try {

			// If the document is a generic type
			if (controleResponse.getKidsDocument().isGeneric()) {
				finalNameFile += language + ".pdf";
				pathDestination += File.separator + "Generic KID";
			} else {
				finalNameFile += investmentStrategy + "_" + language + ".pdf";
				pathDestination += File.separator + "KIDs per investment option";

			}

			// We create the folders, if it does not exist
			Path pathFile = Paths.get(pathDestination);
			if (!Files.exists(pathFile)) {
				Files.createDirectories(pathFile);
			}
			Files.move(kidsCurrentPath.resolve(file.getFileName()), pathFile.resolve(finalNameFile));
			
			injectKidsInAlfresco(pathDestination, finalNameFile,generateStringWithMetadatas(metadatas));
		} catch (IOException e) {
			logger.error("Result parsing " + file.getFileName() + " is failed : " + e.getMessage());

		}
		logger.info("move kids document : " + file.getFileName() + " as " + finalNameFile + " into " + pathDestination);

	}

	/**
	 * Search a specific metadata
	 * 
	 * @param key of the metadata to search
	 * @param metadatas the metadata list
	 * @return the metadata's value
	 */
	private String getSpecificMetadataValue(String key, List<lu.wealins.rest.model.alfresco.Metadata> metadatas) {
		for (lu.wealins.rest.model.alfresco.Metadata metadata : metadatas) {
			if (metadata.getCode().equals(key)) {
				return metadata.getValue();
			}
		}
		return null;
	}
	
	private String generateStringWithMetadatas(List<lu.wealins.rest.model.alfresco.Metadata> metadatas) {
		String toReturn = "";
		
		for(lu.wealins.rest.model.alfresco.Metadata m : metadatas) {
			toReturn += m.getCode()+"="+m.getValue()+";";
		}
		
		return toReturn;
	}

	/**
	 * Manage failed file : log error and move the file into failure path
	 * 
	 * @param file the failed file
	 * @param controleResponse the control response
	 */
	private void moveFailedFile(Path file, BatchInjectionControlResponse controleResponse) {

		List<Error> errors = controleResponse.getErrors();
		logger.warn("Result parsing " + file.getFileName() + " is failed : ");
		for (Error error : errors) {
			logger.warn(" - " + error.getCode() + " : " + error.getMessage());
		}
		try {
			Files.move(kidsCurrentPath.resolve(file.getFileName()), failurePath.resolve(file.getFileName()));
		} catch (IOException e) {
			logger.error("Result parsing " + file.getFileName() + " is failed : " + e.getMessage());
		}

	}

	/**
	 * Apply some control on the kid file
	 * 
	 * @param input the kid file
	 * @return the control result
	 */
	private KidsInjectionControlResponse controleFile(Path input) {
		KidsInjectionControlResponse response = checkMetadatas(input.toFile());
		return response;
	}

	/**
	 * Check if the required metada are in the pdf file
	 * 
	 * @param file the pdf file
	 * @return the result of the check
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	private KidsInjectionControlResponse checkMetadatas(File file) {
		KidsInjectionControlResponse response = buildKidsInjectionControlResponse(file.getAbsolutePath());

		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadataFromPdf = new Metadata();
		ParseContext pcontext = new ParseContext();
		PDFParser pdfparser = new PDFParser();
		FileInputStream pdf = null;

		try {
			// parsing the document using PDF parser
			pdf = new FileInputStream(file);
			pdfparser.parse(pdf, handler, metadataFromPdf, pcontext);
			if (metadataFromPdf != null) {
				// Check if the file is a generic kid or an option kid
				if (metadataFromPdf.get(TYPE_KID_GENERIC) != null) {
					response.getKidsDocument().setGeneric(Boolean.TRUE);
				}

				// search optionnal metadata
				if (metadataFromPdf.get(KID_INTERNATIONAL) != null) {
					lu.wealins.rest.model.alfresco.Metadata meta = new lu.wealins.rest.model.alfresco.Metadata();
					meta.setCode(KID_INTERNATIONAL);
					meta.setValue(metadataFromPdf.get(KID_INTERNATIONAL));
					response.getKidsDocument().getMetadatas().add(meta);
				}

				// Search required metadata
				for (String metadata : metadatas) {
					// IF the metadata does not exist
					if (!existMetadata(metadataFromPdf, metadata)) {
						logger.debug("Metadata " + metadata + " does not exist in the pdf : " + file.getName());
						response.setSuccess(Boolean.FALSE);
						response.getErrors().add(buildError(MISSING_METADATA, metadata));
					} else {
						lu.wealins.rest.model.alfresco.Metadata meta = new lu.wealins.rest.model.alfresco.Metadata();
						meta.setCode(metadata);
						meta.setValue(metadataFromPdf.get(metadata));
						response.getKidsDocument().getMetadatas().add(meta);
					}
				}
			}
			// FIXME Useful ?
			pdf.close();

		} catch (IOException | SAXException | TikaException e) {
			logger.error("Result parsing " + file.getName() + " is failed : " + e.getMessage());
			response.setSuccess(Boolean.FALSE);
		}

		logger.debug("Result check metadata : " + response.isSuccess());
		return response;
	}

	/**
	 * Initialize the response
	 * 
	 * @param path the path
	 * @return the response
	 */
	private KidsInjectionControlResponse buildKidsInjectionControlResponse(String path) {
		KidsInjectionControlResponse response = new KidsInjectionControlResponse();
		KidsDocument kidsDocument = new KidsDocument();
		kidsDocument.setPath(path);
		response.setKidsDocument(kidsDocument);
		response.setSuccess(Boolean.TRUE);

		return response;
	}
	
	/**
	 * inject kids
	 * 
	 */
	public InjectKidsFileResponse injectKidsInAlfresco(String folderPath, String fileName, String metadatas){
		ParameterizedTypeReference<InjectKidsFileResponse> typeRef = new ParameterizedTypeReference<InjectKidsFileResponse>() {};
		InjectKidsFileRequest request = new InjectKidsFileRequest();
		request.setPath(folderPath);
		request.setFileName(fileName);
		request.setMetadatas(metadatas);
		ResponseEntity<InjectKidsFileResponse> responseCall = RestCallUtils.postRest(piaRootContextURL+INJECT_KIDS_ALFRESCO, request, InjectKidsFileRequest.class, typeRef, keycloackUtils, logger);
		InjectKidsFileResponse response = responseCall.getBody();
		return response;

	}

	/**
	 * Build an error
	 * 
	 * @param code the code error
	 * @param message the message error
	 * @return the error object
	 */
	private Error buildError(String code, String message) {
		Error error = new Error();
		error.setCode(code);
		error.setMessage(message);
		return error;
	}

	/**
	 * Test if the metadata exist in the PDF
	 * 
	 * @param metadataFromPdf the metadata from the pdf
	 * @param metadata the metadata to test
	 * @return the result
	 */
	private boolean existMetadata(Metadata metadataFromPdf, String metadata) {
		if (metadataFromPdf.get(metadata) != null) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * @return the requiredMetadata
	 */
	public List<String> getRequiredMetadata() {
		return requiredMetadata;
	}

	/**
	 * @param requiredMetadata the requiredMetadata to set
	 */
	public void setRequiredMetadata(List<String> requiredMetadata) {
		this.requiredMetadata = requiredMetadata;
	}

	/**
	 * @return the metadatas
	 */
	public List<String> getMetadatas() {
		return metadatas;
	}

	/**
	 * @param metadatas the metadatas to set
	 */
	public void setMetadatas(List<String> metadatas) {
		this.metadatas = metadatas;
	}
}
