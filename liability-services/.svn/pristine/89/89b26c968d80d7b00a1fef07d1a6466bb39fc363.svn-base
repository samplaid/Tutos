package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.liability.services.core.utils.Historizable;

@NamedNativeQueries({
	@NamedNativeQuery(name = "PolicyAgentShareEntity.findByAgentAndTypeAndCoverageExternalFunds", query = "SELECT po.POL_ID from POLICIES po "
	+"inner join TRANSACTIONS tr on tr.POLICY = po.POL_ID "
	+"inner join POLICY_AGENT_SHARES pas on pas.type = ?2 "
	+"AND pas.coverage = ?3 "
	+"AND pas.FK_POLICIESPOL_ID = po.POL_ID "
	+"where po.STATUS = 2 and tr.EVENT_TYPE in (4,17,21) "
	+"and pas.AGENT = ?1 "
	+"group by po.pol_id "
	+"Having max(tr.EFFECTIVE_DATE) > '2017-12-27' "
	+"UNION SELECT po.POL_ID from POLICIES po "
	+"inner join POLICY_AGENT_SHARES pas on pas.type = ?2 "
	+"AND pas.coverage = ?3 "
	+"AND pas.FK_POLICIESPOL_ID = po.POL_ID "
	+"where po.STATUS = 1 "
	+"and pas.AGENT = ?1")
})
@Entity
@Table(name = "POLICY_AGENT_SHARES")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "createdProcess", "modifyProcess", "createdBy", "createdDate", "createdTime", "modifyDate", "modifyTime", "modifyBy" })
@Historizable(id = "pasId")
public class PolicyAgentShareEntity
		implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5487489546877L;
	private Long pasId;
	private String polId;
	private BigDecimal sacrificePercent;
	private BigDecimal sacrificeAmount;
	private Boolean primaryAgent;
	private BigDecimal specificIce;
	@JsonIgnore
	private AgentEntity agent;
	private String agentId;
	private Integer type;
	private int status;
	private Integer coverage;
	private BigDecimal percentage;
	private String fund;
	private Set<ProductValueEntity> productValues = new HashSet<>(0);
	private Boolean partnerAuthorized;
	private Date endDate;
	private Date activeDate;

	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String createdProcess;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String modifyProcess;

	@Id
	@Column(name = "PAS_ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getPasId() {
		return this.pasId;
	}

	public void setPasId(Long pasId) {
		this.pasId = pasId;
	}

	@Column(name = "FK_POLICIESPOL_ID", nullable = true, length = 14)
	public String getPolId() {
		return this.polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	@Column(name = "SACRIFICE_PERCENT", precision = 7, scale = 4)
	public BigDecimal getSacrificePercent() {
		return this.sacrificePercent;
	}

	public void setSacrificePercent(BigDecimal sacrificePercent) {
		this.sacrificePercent = sacrificePercent;
	}

	@Column(name = "SACRIFICE_AMOUNT", precision = 10)
	public BigDecimal getSacrificeAmount() {
		return this.sacrificeAmount;
	}

	public void setSacrificeAmount(BigDecimal sacrificeAmount) {
		this.sacrificeAmount = sacrificeAmount;
	}

	@Column(name = "PRIMARY_AGENT", length = 1, nullable = true)
	public Boolean getPrimaryAgent() {
		return this.primaryAgent;
	}

	public void setPrimaryAgent(Boolean primaryAgent) {
		this.primaryAgent = primaryAgent;
	}

	@Column(name = "SPECIFIC_ICE", precision = 15)
	public BigDecimal getSpecificIce() {
		return this.specificIce;
	}

	public void setSpecificIce(BigDecimal specificIce) {
		this.specificIce = specificIce;
	}

	@Column(name = "AGENT")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENT", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public AgentEntity getAgent() {
		return this.agent;
	}

	public void setAgent(AgentEntity agent) {
		this.agent = agent;
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

	@Column(name = "COVERAGE")
	public Integer getCoverage() {
		return this.coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

	@Column(name = "PERCENTAGE", precision = 12, scale = 6)
	public BigDecimal getPercentage() {
		return this.percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
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
	@Column(name = "MODIFY_DATE", length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "MODIFY_PROCESS", length = 15)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setPartnerAuthorized(Boolean partnerAuthorized) {
		this.partnerAuthorized = partnerAuthorized;
	}

	@Column(name = "PARTNER_AUTHORIZED")
	public Boolean getPartnerAuthorized() {
		return this.partnerAuthorized;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", nullable = true)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ACTIVE_DATE", nullable = true)
	public Date getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column(name = "FUND", length = 8)
	public String getFund() {
		return this.fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policyAgentShare")
	public Set<ProductValueEntity> getProductValues() {
		return this.productValues;
	}

	public void setProductValues(Set<ProductValueEntity> productValues) {
		this.productValues = productValues;
	}
}
