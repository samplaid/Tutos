package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDetailDTO {

	private String memId;
	private Integer cliId;
	private Long ponId;
	private String polId;
	private String certificateNo;
	private BigDecimal salary;
	private String category;
	private Date dateValidFrom;
	private Long clientNo;
	private String policyId;
	private String billDestinationRef;
	private String location;
	private Set<MemberHistoryDetailDTO> memberHistoryDetails = new HashSet<MemberHistoryDetailDTO>(0);

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public Integer getCliId() {
		return cliId;
	}

	public void setCliId(Integer cliId) {
		this.cliId = cliId;
	}

	public Long getPonId() {
		return ponId;
	}

	public void setPonId(Long ponId) {
		this.ponId = ponId;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDateValidFrom() {
		return dateValidFrom;
	}

	public void setDateValidFrom(Date dateValidFrom) {
		this.dateValidFrom = dateValidFrom;
	}

	public Long getClientNo() {
		return clientNo;
	}

	public void setClientNo(Long clientNo) {
		this.clientNo = clientNo;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getBillDestinationRef() {
		return billDestinationRef;
	}

	public void setBillDestinationRef(String billDestinationRef) {
		this.billDestinationRef = billDestinationRef;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<MemberHistoryDetailDTO> getMemberHistoryDetails() {
		return memberHistoryDetails;
	}

	public void setMemberHistoryDetails(Set<MemberHistoryDetailDTO> memberHistoryDetails) {
		this.memberHistoryDetails = memberHistoryDetails;
	}

}
