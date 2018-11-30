package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
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

/**
 * ControlDefinitionEntity generated by hbm2java
 */
@Entity
@Table(name = "CONTROL_DEFINITIONS"

)
public class ControlDefinitionEntity implements java.io.Serializable {

	private String cdfId;
	private Integer unitLinked;
	private Integer rateType;
	private BigDecimal maxValue;
	private String window;
	private String fieldFormat;
	private String optionValue;
	private int dataType;
	private Integer subDataType;
	private String createdProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String name;
	private int level0;
	private Set<ProductValueEntity> productValues = new HashSet<ProductValueEntity>(0);

	@Id
	@Column(name = "CDF_ID", unique = true, nullable = false, length = 6)
	public String getCdfId() {
		return this.cdfId;
	}

	public void setCdfId(String cdfId) {
		this.cdfId = cdfId;
	}

	@Column(name = "UNIT_LINKED")
	public Integer getUnitLinked() {
		return this.unitLinked;
	}

	public void setUnitLinked(Integer unitLinked) {
		this.unitLinked = unitLinked;
	}

	@Column(name = "RATE_TYPE")
	public Integer getRateType() {
		return this.rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	@Column(name = "MAX_VALUE", precision = 15, scale = 5)
	public BigDecimal getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name = "WINDOW", length = 15)
	public String getWindow() {
		return this.window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	@Column(name = "FIELD_FORMAT", length = 5)
	public String getFieldFormat() {
		return this.fieldFormat;
	}

	public void setFieldFormat(String fieldFormat) {
		this.fieldFormat = fieldFormat;
	}

	@Column(name = "OPTION_VALUE", nullable = false, length = 20)
	public String getOptionValue() {
		return this.optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	@Column(name = "DATA_TYPE", nullable = false)
	public int getDataType() {
		return this.dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	@Column(name = "SUB_DATA_TYPE")
	public Integer getSubDataType() {
		return this.subDataType;
	}

	public void setSubDataType(Integer subDataType) {
		this.subDataType = subDataType;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
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
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "NAME", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LEVEL0", nullable = false)
	public int getLevel0() {
		return this.level0;
	}

	public void setLevel0(int level0) {
		this.level0 = level0;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "controlDefinition")
	public Set<ProductValueEntity> getProductValues() {
		return this.productValues;
	}

	public void setProductValues(Set<ProductValueEntity> productValues) {
		this.productValues = productValues;
	}

}
