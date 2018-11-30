package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * ExportDefinitionEntity generated by hbm2java
 */
@Entity
@Table(name = "EXPORT_DEFINITIONS"

)
public class ExportDefinitionEntity implements java.io.Serializable {

	private int expId;
	private String name;
	private Integer filename;
	private String filenamePrefix;
	private String filenameSuffix;
	private Integer headerFields;
	private int dataFields;
	private Integer trailerFields;
	private String firstRequest;
	private String standardRequest;
	private String repeatRequest;
	private Integer newEftAction;
	private Integer type;
	private int status;
	private String createdBy;
	private Date createdTime;
	private Date createdDate;
	private String createdProcess;
	private String modifyBy;
	private Date modifyTime;
	private Date modifyDate;
	private String modifyProcess;
	private String actualFilename;
	private Integer systemId;
	private Integer fileFormat;
	private Set<ExportDetailEntity> exportDetails = new HashSet<ExportDetailEntity>(0);

	@Id
	@Column(name = "EXP_ID", unique = true, nullable = false)
	public int getExpId() {
		return this.expId;
	}

	public void setExpId(int expId) {
		this.expId = expId;
	}

	@Column(name = "NAME", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FILENAME")
	public Integer getFilename() {
		return this.filename;
	}

	public void setFilename(Integer filename) {
		this.filename = filename;
	}

	@Column(name = "FILENAME_PREFIX", length = 10)
	public String getFilenamePrefix() {
		return this.filenamePrefix;
	}

	public void setFilenamePrefix(String filenamePrefix) {
		this.filenamePrefix = filenamePrefix;
	}

	@Column(name = "FILENAME_SUFFIX", length = 4)
	public String getFilenameSuffix() {
		return this.filenameSuffix;
	}

	public void setFilenameSuffix(String filenameSuffix) {
		this.filenameSuffix = filenameSuffix;
	}

	@Column(name = "HEADER_FIELDS")
	public Integer getHeaderFields() {
		return this.headerFields;
	}

	public void setHeaderFields(Integer headerFields) {
		this.headerFields = headerFields;
	}

	@Column(name = "DATA_FIELDS", nullable = false)
	public int getDataFields() {
		return this.dataFields;
	}

	public void setDataFields(int dataFields) {
		this.dataFields = dataFields;
	}

	@Column(name = "TRAILER_FIELDS")
	public Integer getTrailerFields() {
		return this.trailerFields;
	}

	public void setTrailerFields(Integer trailerFields) {
		this.trailerFields = trailerFields;
	}

	@Column(name = "FIRST_REQUEST", length = 2)
	public String getFirstRequest() {
		return this.firstRequest;
	}

	public void setFirstRequest(String firstRequest) {
		this.firstRequest = firstRequest;
	}

	@Column(name = "STANDARD_REQUEST", length = 2)
	public String getStandardRequest() {
		return this.standardRequest;
	}

	public void setStandardRequest(String standardRequest) {
		this.standardRequest = standardRequest;
	}

	@Column(name = "REPEAT_REQUEST", length = 2)
	public String getRepeatRequest() {
		return this.repeatRequest;
	}

	public void setRepeatRequest(String repeatRequest) {
		this.repeatRequest = repeatRequest;
	}

	@Column(name = "NEW_EFT_ACTION")
	public Integer getNewEftAction() {
		return this.newEftAction;
	}

	public void setNewEftAction(Integer newEftAction) {
		this.newEftAction = newEftAction;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "MODIFY_BY", length = 5)
	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "MODIFY_PROCESS", length = 15)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column(name = "ACTUAL_FILENAME", length = 100)
	public String getActualFilename() {
		return this.actualFilename;
	}

	public void setActualFilename(String actualFilename) {
		this.actualFilename = actualFilename;
	}

	@Column(name = "SYSTEM_ID")
	public Integer getSystemId() {
		return this.systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	@Column(name = "FILE_FORMAT")
	public Integer getFileFormat() {
		return this.fileFormat;
	}

	public void setFileFormat(Integer fileFormat) {
		this.fileFormat = fileFormat;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exportDefinition")
	@NotFound(action = NotFoundAction.IGNORE)
	public Set<ExportDetailEntity> getExportDetails() {
		return this.exportDetails;
	}

	public void setExportDetails(Set<ExportDetailEntity> exportDetails) {
		this.exportDetails = exportDetails;
	}

}
