package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ClientEndorsementEntity generated by hbm2java
 */
@Entity
@Table(name="CLIENT_ENDORSEMENTS"
    
)
public class ClientEndorsementEntity  implements java.io.Serializable {


     private long cleId;
     private ClientEntity client;
     private String residentialProfile;
     private String workTelNo;
     private String classification;
     private String voterIdNo;
     private String panNumber;
     private String ageAdmBasis;
     private String modifyProcess;
     private Date changeEffectiveDate;
     private String correspContactType;
     private String mobileTelNo;
     private String profile;
	private int clientId;
     private String name0;
     private Integer type;
     private String match;
     private Date dateOfBirth;
     private String riskCat;
     private String leisureRiskCat;
     private Integer subStatus;
     private String maidenName;
     private Integer documentationLanguage;
     private Integer nameStyle;
     private String title;
     private Integer ageAdmitted;
     private Integer sex;
     private Integer smoker;
     private Integer maritalStatus;
     private String nationality;
     private String otherFornames;
     private String firstName;
     private String createdProcess;
     private Integer status;
     private String createdBy;
     private Date createdDate;
     private Date createdTime;
     private BigDecimal weight;
     private Integer height;
     private String endorsementType;
     private String email;
     private String placeOfBirth;
     private String knowYourCustomer;
     private Date kycDate;
     private Date idExpiryDate;
     private String healthDeclaration;
     private Date healthDecDate;
   
     @Id 
    @Column(name="CLE_ID", unique=true, nullable=false, precision=15, scale=0)
    public long getCleId() {
        return this.cleId;
    }
    
    public void setCleId(long cleId) {
        this.cleId = cleId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FK_CLIENTSCLI_ID")
    public ClientEntity getClient() {
        return this.client;
    }
    
    public void setClient(ClientEntity client) {
        this.client = client;
    }

    
    @Column(name="RESIDENTIAL_PROFILE", length=8)
    public String getResidentialProfile() {
        return this.residentialProfile;
    }
    
    public void setResidentialProfile(String residentialProfile) {
        this.residentialProfile = residentialProfile;
    }

    
    @Column(name="WORK_TEL_NO", length=12)
    public String getWorkTelNo() {
        return this.workTelNo;
    }
    
    public void setWorkTelNo(String workTelNo) {
        this.workTelNo = workTelNo;
    }

    
    @Column(name="CLASSIFICATION", length=8)
    public String getClassification() {
        return this.classification;
    }
    
    public void setClassification(String classification) {
        this.classification = classification;
    }

    
    @Column(name="VOTER_ID_NO", length=12)
    public String getVoterIdNo() {
        return this.voterIdNo;
    }
    
    public void setVoterIdNo(String voterIdNo) {
        this.voterIdNo = voterIdNo;
    }

    
    @Column(name="PAN_NUMBER", length=12)
    public String getPanNumber() {
        return this.panNumber;
    }
    
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    
    @Column(name="AGE_ADM_BASIS", length=8)
    public String getAgeAdmBasis() {
        return this.ageAdmBasis;
    }
    
    public void setAgeAdmBasis(String ageAdmBasis) {
        this.ageAdmBasis = ageAdmBasis;
    }

    
    @Column(name="MODIFY_PROCESS", length=12)
    public String getModifyProcess() {
        return this.modifyProcess;
    }
    
    public void setModifyProcess(String modifyProcess) {
        this.modifyProcess = modifyProcess;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CHANGE_EFFECTIVE_DATE", length=23)
    public Date getChangeEffectiveDate() {
        return this.changeEffectiveDate;
    }
    
    public void setChangeEffectiveDate(Date changeEffectiveDate) {
        this.changeEffectiveDate = changeEffectiveDate;
    }

    
    @Column(name="CORRESP_CONTACT_TYPE", length=10)
    public String getCorrespContactType() {
        return this.correspContactType;
    }
    
    public void setCorrespContactType(String correspContactType) {
        this.correspContactType = correspContactType;
    }

    
    @Column(name="MOBILE_TEL_NO", length=12)
    public String getMobileTelNo() {
        return this.mobileTelNo;
    }
    
    public void setMobileTelNo(String mobileTelNo) {
        this.mobileTelNo = mobileTelNo;
    }

    
    @Column(name="PROFILE", length=8)
    public String getProfile() {
        return this.profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }

    
    @Column(name="CLIENT", nullable=false)
	public int getClientId() {
		return this.clientId;
    }
    
	public void setClientId(int clientId) {
		this.clientId = clientId;
    }

    
    @Column(name="NAME0", length=40)
    public String getName0() {
        return this.name0;
    }
    
    public void setName0(String name0) {
        this.name0 = name0;
    }

    
    @Column(name="TYPE")
    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    
    @Column(name="MATCH", length=20)
    public String getMatch() {
        return this.match;
    }
    
    public void setMatch(String match) {
        this.match = match;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_OF_BIRTH", length=23)
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    @Column(name="RISK_CAT", length=8)
    public String getRiskCat() {
        return this.riskCat;
    }
    
    public void setRiskCat(String riskCat) {
        this.riskCat = riskCat;
    }

    
    @Column(name="LEISURE_RISK_CAT", length=8)
    public String getLeisureRiskCat() {
        return this.leisureRiskCat;
    }
    
    public void setLeisureRiskCat(String leisureRiskCat) {
        this.leisureRiskCat = leisureRiskCat;
    }

    
    @Column(name="SUB_STATUS")
    public Integer getSubStatus() {
        return this.subStatus;
    }
    
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    
    @Column(name="MAIDEN_NAME", length=30)
    public String getMaidenName() {
        return this.maidenName;
    }
    
    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    
    @Column(name="DOCUMENTATION_LANGUAGE")
    public Integer getDocumentationLanguage() {
        return this.documentationLanguage;
    }
    
    public void setDocumentationLanguage(Integer documentationLanguage) {
        this.documentationLanguage = documentationLanguage;
    }

    
    @Column(name="NAME_STYLE")
    public Integer getNameStyle() {
        return this.nameStyle;
    }
    
    public void setNameStyle(Integer nameStyle) {
        this.nameStyle = nameStyle;
    }

    
    @Column(name="TITLE", length=8)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="AGE_ADMITTED")
    public Integer getAgeAdmitted() {
        return this.ageAdmitted;
    }
    
    public void setAgeAdmitted(Integer ageAdmitted) {
        this.ageAdmitted = ageAdmitted;
    }

    
    @Column(name="SEX")
    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    
    @Column(name="SMOKER")
    public Integer getSmoker() {
        return this.smoker;
    }
    
    public void setSmoker(Integer smoker) {
        this.smoker = smoker;
    }

    
    @Column(name="MARITAL_STATUS")
    public Integer getMaritalStatus() {
        return this.maritalStatus;
    }
    
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    
    @Column(name="NATIONALITY", length=20)
    public String getNationality() {
        return this.nationality;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    
    @Column(name="OTHER_FORNAMES", length=40)
    public String getOtherFornames() {
        return this.otherFornames;
    }
    
    public void setOtherFornames(String otherFornames) {
        this.otherFornames = otherFornames;
    }

    
    @Column(name="FIRST_NAME", length=30)
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    @Column(name="CREATED_PROCESS", length=12)
    public String getCreatedProcess() {
        return this.createdProcess;
    }
    
    public void setCreatedProcess(String createdProcess) {
        this.createdProcess = createdProcess;
    }

    
    @Column(name="STATUS")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    @Column(name="CREATED_BY", length=5)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE", nullable=false, length=23)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TIME", nullable=false, length=23)
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    @Column(name="WEIGHT", precision=6)
    public BigDecimal getWeight() {
        return this.weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    
    @Column(name="HEIGHT", precision=5, scale=0)
    public Integer getHeight() {
        return this.height;
    }
    
    public void setHeight(Integer height) {
        this.height = height;
    }

    
    @Column(name="ENDORSEMENT_TYPE", length=6)
    public String getEndorsementType() {
        return this.endorsementType;
    }
    
    public void setEndorsementType(String endorsementType) {
        this.endorsementType = endorsementType;
    }

    
    @Column(name="EMAIL", length=40)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="PLACE_OF_BIRTH", length=40)
    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }
    
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    
    @Column(name="KNOW_YOUR_CUSTOMER", length=8)
    public String getKnowYourCustomer() {
        return this.knowYourCustomer;
    }
    
    public void setKnowYourCustomer(String knowYourCustomer) {
        this.knowYourCustomer = knowYourCustomer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="KYC_DATE", length=23)
    public Date getKycDate() {
        return this.kycDate;
    }
    
    public void setKycDate(Date kycDate) {
        this.kycDate = kycDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ID_EXPIRY_DATE", length=23)
    public Date getIdExpiryDate() {
        return this.idExpiryDate;
    }
    
    public void setIdExpiryDate(Date idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    
    @Column(name="HEALTH_DECLARATION", length=8)
    public String getHealthDeclaration() {
        return this.healthDeclaration;
    }
    
    public void setHealthDeclaration(String healthDeclaration) {
        this.healthDeclaration = healthDeclaration;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="HEALTH_DEC_DATE", length=23)
    public Date getHealthDecDate() {
        return this.healthDecDate;
    }
    
    public void setHealthDecDate(Date healthDecDate) {
        this.healthDecDate = healthDecDate;
    }




}


