package lu.wealins.common.dto.liability.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentRequestDTO {

	private long drqId;
	private Integer cliId;
	private String polId;
	private Long trnId;
	private String productLine;
	private String errorNo;
	private Integer coverage;
	private Long clientId;
	private String policyId;
	private Integer type;
	private String printedProcess;
	private String printedBy;
	private Date printedDate;
	private Date printedTime;
	private String transientData;
	private String event;
	private String document;
	private Date lastPrintedDate;
	private String lastPrintedBy;
	private String description;
	private String pathname;
	private Long transaction0;
	private String createdProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String printer;
	private Date effectiveDate;
	private Date docSignedDate;
	private Date docSentDate;

	public long getDrqId() {
		return drqId;
	}

	public void setDrqId(long drqId) {
		this.drqId = drqId;
	}

	public Integer getCliId() {
		return cliId;
	}

	public void setCliId(Integer cliId) {
		this.cliId = cliId;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public Long getTrnId() {
		return trnId;
	}

	public void setTrnId(Long trnId) {
		this.trnId = trnId;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPrintedProcess() {
		return printedProcess;
	}

	public void setPrintedProcess(String printedProcess) {
		this.printedProcess = printedProcess;
	}

	public String getPrintedBy() {
		return printedBy;
	}

	public void setPrintedBy(String printedBy) {
		this.printedBy = printedBy;
	}

	public Date getPrintedDate() {
		return printedDate;
	}

	public void setPrintedDate(Date printedDate) {
		this.printedDate = printedDate;
	}

	public Date getPrintedTime() {
		return printedTime;
	}

	public void setPrintedTime(Date printedTime) {
		this.printedTime = printedTime;
	}

	public String getTransientData() {
		return transientData;
	}

	public void setTransientData(String transientData) {
		this.transientData = transientData;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Date getLastPrintedDate() {
		return lastPrintedDate;
	}

	public void setLastPrintedDate(Date lastPrintedDate) {
		this.lastPrintedDate = lastPrintedDate;
	}

	public String getLastPrintedBy() {
		return lastPrintedBy;
	}

	public void setLastPrintedBy(String lastPrintedBy) {
		this.lastPrintedBy = lastPrintedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public Long getTransaction0() {
		return transaction0;
	}

	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getDocSignedDate() {
		return docSignedDate;
	}

	public void setDocSignedDate(Date docSignedDate) {
		this.docSignedDate = docSignedDate;
	}

	public Date getDocSentDate() {
		return docSentDate;
	}

	public void setDocSentDate(Date docSentDate) {
		this.docSentDate = docSentDate;
	}


}
