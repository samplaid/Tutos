package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sas order FID entity : Map the table SAS_ORDER_FID
 * 
 * @author xqt5q
 *
 */
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "SapAccountingRowNoEntity", classes = @ConstructorResult(targetClass = SapAccountingRowNoEntity.class, columns = {
			@ColumnResult(name = "COMPANY" , type=String.class),
			@ColumnResult(name = "PIECE" , type=String.class),
			@ColumnResult(name = "CURRENCY" , type=String.class),
			@ColumnResult(name = "PIECE_NB" , type=String.class),
			@ColumnResult(name = "ACCOUNT_DT" , type=Date.class),
			@ColumnResult(name = "CHANGE_RATE" , type=BigDecimal.class),
			@ColumnResult(name = "ACCOUNT" , type=String.class),
			@ColumnResult(name = "ACCOUNT_GENERAL" , type=String.class),
			@ColumnResult(name = "DEBIT_CREDIT" , type=String.class),
			@ColumnResult(name = "AMOUNT" , type=BigDecimal.class),
			@ColumnResult(name = "RECONCILIATION" , type=String.class),
			@ColumnResult(name = "EXPLAIN" , type=String.class),
			@ColumnResult(name = "PRODUCT" , type=String.class),
			@ColumnResult(name = "COUNTRY_OF_PRODUCT" , type=String.class),
			@ColumnResult(name = "SUPPORT" , type=String.class),
			@ColumnResult(name = "FUND" , type=String.class),
			@ColumnResult(name = "ORIGIN" , type=String.class),
			@ColumnResult(name = "STATUS_CD" , type=String.class),
			@ColumnResult(name = "ORIGIN_ID" , type=Long.class)
	})),
	
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "MathematicalReserveEntity.findMathematicalReserveToExport", query = "select" + 
			" '1600' AS COMPANY, 'PR' AS PIECE, currency AS CURRENCY, concat('1706' , ROW_NUMBER() over (order by currency, fund, ISIN, nl_product ,  nl_country, mp.DATA_OUT)) AS PIECE_NB , max(calcul_dt) AS ACCOUNT_DT, max(change_rate) AS CHANGE_RATE" + 
			", '8020000500' AS ACCOUNT, 'V' AS ACCOUNT_GENERAL,'D' AS DEBIT_CREDIT ,sum(amount) AS AMOUNT, 'PM ' AS RECONCILIATION, fund AS EXPLAIN" + 
			", nl_product AS PRODUCT, nl_country AS COUNTRY_OF_PRODUCT, mp.DATA_OUT AS SUPPORT, isnull(ISIN, fund) AS FUND, 'LISSIA' AS ORIGIN, 'EQUAL' AS STATUS_CD, max(prov_maths_id) AS ORIGIN_ID" + 
			" from prov_maths" + 
			" left join SAP_MAPPING mp" + 
			" on mp.type = 'FUND_TYPE'" + 
			" and  fund_sub_type = DATA_IN" + 
			" where prov_maths.sap_dt is null and mode = ?1" + 
			" group by currency, fund, ISIN, nl_product , nl_country, mp.DATA_OUT" + 
			" order by currency, fund, ISIN, nl_product , nl_country, mp.DATA_OUT", resultSetMapping = "SapAccountingRowNoEntity", hints={@QueryHint(name="javax.persistence.query.timeout", value="300000")})
})
@Table(name = "PROV_MATHS")
@Entity
public class MathematicalReserveEntity implements Serializable {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8798542751194224509L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROV_MATHS_ID")
	private Long id;

	@Column(name = "CALCUL_DT")
	@Temporal(TemporalType.DATE)
	private Date calculDate;

	@Column(name = "MODE")
	private String mode;
	
	@Column(name = "POLICY")
	private String policyId;

	@Column(name = "PRODUCT")
	private String productId;

	@Column(name = "NL_PRODUCT")
	private String accountingProduct;
	
	@Column(name = "NL_COUNTRY")
	private String productCountry;
	
	@Column(name = "FUND")
	private String fundId;
	
	@Column(name = "FUND_SUB_TYPE")
	private String fundSubType;
	
	@Column(name = "ISIN")
	private String isin;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "CHANGE_RATE")
	private BigDecimal changeRate;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "UNITS")
	private BigDecimal units;
	
	@Column(name = "NAV")
	private BigDecimal nav;

	@Column(name = "NAV_DT")
	@Temporal(TemporalType.DATE)
	private Date navDate;
	
	@Column(name = "CREATION_DT")
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Column(name = "SAP_DT")
	@Temporal(TemporalType.DATE)
	private Date extractionDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the calculDate
	 */
	public Date getCalculDate() {
		return calculDate;
	}

	/**
	 * @param calculDate the calculDate to set
	 */
	public void setCalculDate(Date calculDate) {
		this.calculDate = calculDate;
	}

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the accountingProduct
	 */
	public String getAccountingProduct() {
		return accountingProduct;
	}

	/**
	 * @param accountingProduct the accountingProduct to set
	 */
	public void setAccountingProduct(String accountingProduct) {
		this.accountingProduct = accountingProduct;
	}

	/**
	 * @return the productCountry
	 */
	public String getProductCountry() {
		return productCountry;
	}

	/**
	 * @param productCountry the productCountry to set
	 */
	public void setProductCountry(String productCountry) {
		this.productCountry = productCountry;
	}

	/**
	 * @return the fundId
	 */
	public String getFundId() {
		return fundId;
	}

	/**
	 * @param fundId the fundId to set
	 */
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	/**
	 * @return the fundSubType
	 */
	public String getFundSubType() {
		return fundSubType;
	}

	/**
	 * @param fundSubType the fundSubType to set
	 */
	public void setFundSubType(String fundSubType) {
		this.fundSubType = fundSubType;
	}
	
	

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the changeRate
	 */
	public BigDecimal getChangeRate() {
		return changeRate;
	}

	/**
	 * @param changeRate the changeRate to set
	 */
	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}

	

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the units
	 */
	public BigDecimal getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	/**
	 * @return the nav
	 */
	public BigDecimal getNav() {
		return nav;
	}

	/**
	 * @param nav the nav to set
	 */
	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	/**
	 * @return the navDate
	 */
	public Date getNavDate() {
		return navDate;
	}

	/**
	 * @param navDate the navDate to set
	 */
	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the extractionDate
	 */
	public Date getExtractionDate() {
		return extractionDate;
	}

	/**
	 * @param extractionDate the extractionDate to set
	 */
	public void setExtractionDate(Date extractionDate) {
		this.extractionDate = extractionDate;
	}

	

	
}
