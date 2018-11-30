package lu.wealins.common.dto.liability.services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.account.ClientAccountDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO extends ClientLiteDTO {

	private Short documentationLanguage;
	private String title;
	private Short sex;
	private Short maritalStatus;
	private String nationality;
	private String otherFornames;
	private String profile;
	private String mobileTelNo;
	private String workTelNo;
	private String classification;
	private String email;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date idExpiryDate;
	private String placeOfBirth;
	private String politicallyExposedPerson;
	private String pepFunction;
	private String insiderTrading;
	private String insiderTradingDetails;
	private String dap;
	private String countryOfBirth;
	private String provinceOfBirth;
	private String profession;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfRevision;
	private Short fiduciary;
	private String vatNumber;
	private String circularLetter;
	private String classOfRisk;
	private String statementByEmail;
	private String mediaExposedPerson;
	private String mepDetail;
	private String relativeCloseAssoc;
	private String rcaDetail;
	private String crsStatus;
	private String crsExactStatus;
	private String commercialEntity;
	private String nationalIdNo;
	private String natIdCountry;
	private String nationalId2;
	private String natId2Country;
	private Short activityRiskCat;
	private String classificationDetails;
	private String riskCat;
	private Integer subStatus;
	private Date dateOfSelfCert;
	private Date crsDateOfLastDec;
	private Date healthDecDate;
	private String healthDeclaration;
	private String knowYourCustomer;
	private Date kycDate;
	private Date gdprStartdate;
	private Date gdprEnddate;
	private Boolean exceptRisk;
	private Boolean exceptActivityRisk;

	private GeneralNoteDTO idNumber;
	private GeneralNoteDTO clientNote;

	private ClientContactDetailDTO homeAddress;
	private ClientContactDetailDTO correspondenceAddress;

	private Collection<ClientLinkedPersonDTO> personsRepresentingCompany = new HashSet<>(0);
	private Collection<ClientLinkedPersonDTO> controllingPersons = new HashSet<>(0);
	private Collection<ClientClaimsDetailDTO> clientClaimsDetails = new HashSet<>(0);
	private Collection<ClientAccountDTO> clientAccounts = new HashSet<>(0);
	
	/**
	 * @return the exceptRisk
	 */
	public Boolean getExceptRisk() {
		return exceptRisk;
	}

	/**
	 * @param exceptRisk the exceptRisk to set
	 */
	public void setExceptRisk(Boolean exceptRisk) {
		this.exceptRisk = exceptRisk;
	}

	/**
	 * @return the exceptActivityRisk
	 */
	public Boolean getExceptActivityRisk() {
		return exceptActivityRisk;
	}

	/**
	 * @param exceptActivityRisk the exceptActivityRisk to set
	 */
	public void setExceptActivityRisk(Boolean exceptActivityRisk) {
		this.exceptActivityRisk = exceptActivityRisk;
	}

	/**
	 * @return the healthDecDate
	 */
	public Date getHealthDecDate() {
		return healthDecDate;
	}

	/**
	 * @param healthDecDate the healthDecDate to set
	 */
	public void setHealthDecDate(Date healthDecDate) {
		this.healthDecDate = healthDecDate;
	}

	/**
	 * @return the healthDeclaration
	 */
	public String getHealthDeclaration() {
		return healthDeclaration;
	}

	/**
	 * @param healthDeclaration the healthDeclaration to set
	 */
	public void setHealthDeclaration(String healthDeclaration) {
		this.healthDeclaration = healthDeclaration;
	}

	/**
	 * @return the knowYourCustomer
	 */
	public String getKnowYourCustomer() {
		return knowYourCustomer;
	}

	/**
	 * @param knowYourCustomer the knowYourCustomer to set
	 */
	public void setKnowYourCustomer(String knowYourCustomer) {
		this.knowYourCustomer = knowYourCustomer;
	}

	/**
	 * @return the kycDate
	 */
	public Date getKycDate() {
		return kycDate;
	}

	/**
	 * @param kycDate the kycDate to set
	 */
	public void setKycDate(Date kycDate) {
		this.kycDate = kycDate;
	}

	/**
	 * @return the gdprStartdate
	 */
	public Date getGdprStartdate() {
		return gdprStartdate;
	}

	/**
	 * @param gdprStartdate the gdprStartdate to set
	 */
	public void setGdprStartdate(Date gdprStartdate) {
		this.gdprStartdate = gdprStartdate;
	}

	/**
	 * @return the gdprEnddate
	 */
	public Date getGdprEnddate() {
		return gdprEnddate;
	}

	/**
	 * @param gdprEnddate the gdprEnddate to set
	 */
	public void setGdprEnddate(Date gdprEnddate) {
		this.gdprEnddate = gdprEnddate;
	}

	public Collection<ClientClaimsDetailDTO> getClientClaimsDetails() {
		return clientClaimsDetails;
	}

	public void setClientClaimsDetails(Collection<ClientClaimsDetailDTO> clientClaimsDetails) {
		this.clientClaimsDetails = clientClaimsDetails;
	}

	public Date getDateOfSelfCert() {
		return dateOfSelfCert;
	}

	public void setDateOfSelfCert(Date dateOfSelfCert) {
		this.dateOfSelfCert = dateOfSelfCert;
	}

	public Date getCrsDateOfLastDec() {
		return crsDateOfLastDec;
	}

	public void setCrsDateOfLastDec(Date crsDateOfLastDec) {
		this.crsDateOfLastDec = crsDateOfLastDec;
	}

	public Short getDocumentationLanguage() {
		return documentationLanguage;
	}

	public void setDocumentationLanguage(Short documentationLanguage) {
		this.documentationLanguage = documentationLanguage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Short getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Short maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getOtherFornames() {
		return otherFornames;
	}

	public void setOtherFornames(String otherFornames) {
		this.otherFornames = otherFornames;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getMobileTelNo() {
		return mobileTelNo;
	}

	public void setMobileTelNo(String mobileTelNo) {
		this.mobileTelNo = mobileTelNo;
	}

	public String getWorkTelNo() {
		return workTelNo;
	}

	public void setWorkTelNo(String workTelNo) {
		this.workTelNo = workTelNo;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getIdExpiryDate() {
		return idExpiryDate;
	}

	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getPoliticallyExposedPerson() {
		return politicallyExposedPerson;
	}

	public void setPoliticallyExposedPerson(String politicallyExposedPerson) {
		this.politicallyExposedPerson = politicallyExposedPerson;
	}

	public String getPepFunction() {
		return pepFunction;
	}

	public void setPepFunction(String pepFunction) {
		this.pepFunction = pepFunction;
	}

	public String getInsiderTrading() {
		return insiderTrading;
	}

	public void setInsiderTrading(String insiderTrading) {
		this.insiderTrading = insiderTrading;
	}

	public String getInsiderTradingDetails() {
		return insiderTradingDetails;
	}

	public void setInsiderTradingDetails(String insiderTradingDetails) {
		this.insiderTradingDetails = insiderTradingDetails;
	}

	public String getDap() {
		return dap;
	}

	public void setDap(String dap) {
		this.dap = dap;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getProvinceOfBirth() {
		return provinceOfBirth;
	}

	public void setProvinceOfBirth(String provinceOfBirth) {
		this.provinceOfBirth = provinceOfBirth;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Date getDateOfRevision() {
		return dateOfRevision;
	}

	public void setDateOfRevision(Date dateOfRevision) {
		this.dateOfRevision = dateOfRevision;
	}

	public Short getFiduciary() {
		return fiduciary;
	}

	public void setFiduciary(Short fiduciary) {
		this.fiduciary = fiduciary;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getCircularLetter() {
		return circularLetter;
	}

	public void setCircularLetter(String circularLetter) {
		this.circularLetter = circularLetter;
	}

	public String getClassOfRisk() {
		return classOfRisk;
	}

	public void setClassOfRisk(String classOfRisk) {
		this.classOfRisk = classOfRisk;
	}

	public String getStatementByEmail() {
		return statementByEmail;
	}

	public void setStatementByEmail(String statementByEmail) {
		this.statementByEmail = statementByEmail;
	}

	public String getMediaExposedPerson() {
		return mediaExposedPerson;
	}

	public void setMediaExposedPerson(String mediaExposedPerson) {
		this.mediaExposedPerson = mediaExposedPerson;
	}

	public String getMepDetail() {
		return mepDetail;
	}

	public void setMepDetail(String mepDetail) {
		this.mepDetail = mepDetail;
	}

	public String getRelativeCloseAssoc() {
		return relativeCloseAssoc;
	}

	public void setRelativeCloseAssoc(String relativeCloseAssoc) {
		this.relativeCloseAssoc = relativeCloseAssoc;
	}

	public String getRcaDetail() {
		return rcaDetail;
	}

	public void setRcaDetail(String rcaDetail) {
		this.rcaDetail = rcaDetail;
	}

	public String getCrsStatus() {
		return crsStatus;
	}

	public void setCrsStatus(String crsStatus) {
		this.crsStatus = crsStatus;
	}

	public String getCrsExactStatus() {
		return crsExactStatus;
	}

	public void setCrsExactStatus(String crsExactStatus) {
		this.crsExactStatus = crsExactStatus;
	}

	public String getCommercialEntity() {
		return commercialEntity;
	}

	public void setCommercialEntity(String commercialEntity) {
		this.commercialEntity = commercialEntity;
	}

	public String getNationalIdNo() {
		return nationalIdNo;
	}

	public void setNationalIdNo(String nationalIdNo) {
		this.nationalIdNo = nationalIdNo;
	}

	public String getNatIdCountry() {
		return natIdCountry;
	}

	public void setNatIdCountry(String natIdCountry) {
		this.natIdCountry = natIdCountry;
	}

	public String getNationalId2() {
		return nationalId2;
	}

	public void setNationalId2(String nationalId2) {
		this.nationalId2 = nationalId2;
	}

	public String getNatId2Country() {
		return natId2Country;
	}

	public void setNatId2Country(String natId2Country) {
		this.natId2Country = natId2Country;
	}

	public Short getActivityRiskCat() {
		return activityRiskCat;
	}

	public void setActivityRiskCat(Short activityRiskCat) {
		this.activityRiskCat = activityRiskCat;
	}

	public String getClassificationDetails() {
		return classificationDetails;
	}

	public void setClassificationDetails(String classificationDetails) {
		this.classificationDetails = classificationDetails;
	}

	public String getRiskCat() {
		return riskCat;
	}

	public void setRiskCat(String riskCat) {
		this.riskCat = riskCat;
	}

	/**
	 * @return the subStatus
	 */
	public Integer getSubStatus() {
		return subStatus;
	}

	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}

	public GeneralNoteDTO getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(GeneralNoteDTO idNumber) {
		this.idNumber = idNumber;
	}

	public GeneralNoteDTO getClientNote() {
		return clientNote;
	}

	public void setClientNote(GeneralNoteDTO clientNote) {
		this.clientNote = clientNote;
	}

	public ClientContactDetailDTO getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(ClientContactDetailDTO homeAddress) {
		this.homeAddress = homeAddress;
	}

	public ClientContactDetailDTO getCorrespondenceAddress() {
		return correspondenceAddress;
	}

	public void setCorrespondenceAddress(ClientContactDetailDTO correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}

	public Collection<ClientLinkedPersonDTO> getPersonsRepresentingCompany() {
		return personsRepresentingCompany;
	}

	public void setPersonsRepresentingCompany(Collection<ClientLinkedPersonDTO> personsRepresentingCompany) {
		this.personsRepresentingCompany = personsRepresentingCompany;
	}

	public Collection<ClientLinkedPersonDTO> getControllingPersons() {
		return controllingPersons;
	}

	public void setControllingPersons(Collection<ClientLinkedPersonDTO> controllingPersons) {
		this.controllingPersons = controllingPersons;
	}

	/**
	 * @return the clientAccounts
	 */
	public Collection<ClientAccountDTO> getClientAccounts() {
		return clientAccounts;
	}

	/**
	 * @param clientAccounts the clientAccounts to set
	 */
	public void setClientAccounts(Collection<ClientAccountDTO> clientAccounts) {
		this.clientAccounts = clientAccounts;
	}
	
	
}
