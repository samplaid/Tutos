package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SapAccountingDTO {

	private Long idSapAcc;
	private String company;
	private String piece;
	private String currency;
	private String pieceNb;
	private BigDecimal changeRate;
	private String account;
	private String accountGeneral;
	private String debitCredit;
	private BigDecimal amount;
	private String reconciliation;
	private String explain;
	private String product;
	private String policy;
	private String agent;
	private String country;
	private String countryOfProduct;
	private String support;
	private String fund;
	private Date creationDate;
	private Date exportDate;
	private Date accountDate;
	private String origin;
	private Long originId;

	/**
	 * @return the idSapAcc
	 */
	public Long getIdSapAcc() {
		return idSapAcc;
	}

	/**
	 * @param idSapAcc the idSapAcc to set
	 */
	public void setIdSapAcc(Long idSapAcc) {
		this.idSapAcc = idSapAcc;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the piece
	 */
	public String getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(String piece) {
		this.piece = piece;
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
	 * @return the pieceNb
	 */
	public String getPieceNb() {
		return pieceNb;
	}

	/**
	 * @param pieceNb the pieceNb to set
	 */
	public void setPieceNb(String pieceNb) {
		this.pieceNb = pieceNb;
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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the accountGeneral
	 */
	public String getAccountGeneral() {
		return accountGeneral;
	}

	/**
	 * @param accountGeneral the accountGeneral to set
	 */
	public void setAccountGeneral(String accountGeneral) {
		this.accountGeneral = accountGeneral;
	}

	/**
	 * @return the Debit Credit
	 */
	public String getDebitCredit() {
		return debitCredit;
	}

	/**
	 * @param debitCredit the Debit Credit to set
	 */
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
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
	 * @return the reconciliation
	 */
	public String getReconciliation() {
		return reconciliation;
	}

	/**
	 * @param reconciliation the reconciliation to set
	 */
	public void setReconciliation(String reconciliation) {
		this.reconciliation = reconciliation;
	}

	/**
	 * @return the explain
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * @param explain the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the country of product
	 */
	public String getCountryOfProduct() {
		return countryOfProduct;
	}

	/**
	 * @param countryOfProduct the country of product to set
	 */
	public void setCountryOfProduct(String countryOfProduct) {
		this.countryOfProduct = countryOfProduct;
	}

	/**
	 * @return the support
	 */
	public String getSupport() {
		return support;
	}

	/**
	 * @param support the support to set
	 */
	public void setSupport(String support) {
		this.support = support;
	}

	/**
	 * @return the fund
	 */
	public String getFund() {
		return fund;
	}

	/**
	 * @param fund
	 */
	public void setFund(String fund) {
		this.fund = fund;
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
	 * @return the exportDate
	 */
	public Date getExportDate() {
		return exportDate;
	}

	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String buildLineForFile() {
		String build = null;
		String build_952010 = String.format("%-4s", Optional.ofNullable(this.company).orElse("")).substring(0, 4);
		String build_952020 = String.format("%-2s", Optional.ofNullable(this.piece).orElse("")).substring(0, 2);
		SimpleDateFormat dt_952030 = new SimpleDateFormat("ddMMyyyy");
		String build_952030 = dt_952030.format(this.accountDate).substring(0, 8);
		String build_952040 = String.format("%-5s", Optional.ofNullable(this.currency).orElse("")).substring(0, 5);
		String build_952050 = String.format("%-8s", Optional.ofNullable(this.pieceNb).orElse("")).substring(0, 8).replace(" ", "0");
		String build_952060 = String.format("%-8s", "").substring(0, 8);
		String build_952070 = String.format("%-25s", Optional.ofNullable(this.origin).orElse("")).substring(0, 25);

		String build_952080_1 = null;
		String build_952080_2 = null;
		if (this.changeRate.toString().contains(".")) {
			String[] changeRateSplit = this.changeRate.toString().split("\\.");
			build_952080_1 = String.format("%5s", changeRateSplit[0].toString()).replace(" ", "0");
			build_952080_2 = String.format("%-5s", changeRateSplit[1]).replace(" ", "0");
		} else {
			build_952080_1 = String.format("%5s", Optional.ofNullable(this.changeRate.toString()).orElse("")).replace(" ", "0");
			build_952080_2 = String.format("%-5s", "").replace(" ", "0");
		}
		String build_952080 = (build_952080_1 + build_952080_2);

		String build_952090 = String.format("%-1s", "").substring(0, 1);
		String build_952100 = String.format("%-2s", "").substring(0, 2);
		String build_952110 = String.format("%-1s", "").substring(0, 1);
		String build_952120 = String.format("%-4s", "").substring(0, 4);
		String build_952130 = String.format("%17s", Optional.ofNullable(this.account).orElse("")).substring(0, 17).replace(" ", "0");
		String build_952140 = String.format("%-1s", Optional.ofNullable(this.accountGeneral).orElse("")).substring(0, 1);
		String build_952150 = String.format("%-1s", Optional.ofNullable(this.debitCredit).orElse("")).substring(0, 1);
		String build_952160 = String.format("%15s", Optional.ofNullable(this.amount).orElse(new BigDecimal(0)).toString().replace(".", "")).replace(" ", "0").substring(0, 15);
		String build_952170 = String.format("%-10s", "").substring(0, 10);
		String build_952180 = String.format("%-12s", "").substring(0, 12);
		String build_952190 = String.format("%-18s", Optional.ofNullable(this.reconciliation).orElse("")).substring(0, 18);
		String build_952200 = String.format("%-50s", Optional.ofNullable(this.explain).orElse("")).substring(0, 50);
		String build_9522010_to_952360 = String.format("%-103s", "").substring(0, 103);
		String build_952370 = String.format("%-2s", Optional.ofNullable(this.product).orElse("")).substring(0, 2);
		String build_952380 = String.format("%-11s", Optional.ofNullable(this.policy).orElse("")).substring(0, 11);
		String build_952390 = String.format("%-8s", Optional.ofNullable(this.agent).orElse("")).substring(0, 8);
		String build_952400 = String.format("%-5s", Optional.ofNullable(this.country).orElse("")).substring(0, 5);
		String build_952410_1 = String.format("%-4s", Optional.ofNullable(this.countryOfProduct).orElse("")).substring(0, 4);
		String build_952410_2 = String.format("%-8s", "").substring(0, 8);
		String build_952410_3 = String.format("%-8s", "").substring(0, 8);
		String build_952410_4 = String.format("%-3s", "").substring(0, 3);
		String build_952410_5 = String.format("%-2s", Optional.ofNullable(this.support).orElse("")).substring(0, 2);
		String build_952410_6 = String.format("%-24s", Optional.ofNullable(this.fund).orElse("")).substring(0, 24);
		String build_952410 = build_952410_1 + build_952410_2 + build_952410_3 + build_952410_4 + build_952410_5 + build_952410_6;
		String build_952420 = String.format("%-120s", "").substring(0, 120);

		build = build_952010 + build_952020 + build_952030 + build_952040 + build_952050 + build_952060 + build_952070 + build_952080 + build_952090 + build_952100 + build_952110 + build_952120
				+ build_952130 + build_952140 + build_952150 + build_952160 + build_952170 + build_952180 + build_952190 + build_952200 + build_9522010_to_952360 + build_952370 + build_952380
				+ build_952390 + build_952400 + build_952410 + build_952420;
		return build;
	}

	@Override
	public String toString() {
		return "SapAccountingEntity [idSapAcc=" + idSapAcc + ", company=" + company + ", piece=" + piece + ", currency=" + currency + ", pieceNb=" + pieceNb + ", changeRate=" + changeRate
				+ ", account=" + account + ", accountGeneral=" + accountGeneral + ", debitCredit=" + debitCredit + ", amount=" + amount + ", reconciliation=" + reconciliation + ", explain=" + explain
				+ ", product=" + product + ", policy=" + policy + ", agent=" + agent + ", country=" + country + ", countryOfProduct=" + countryOfProduct + ", support=" + support + ", fund=" + fund
				+ ", creationDate=" + creationDate + ", exportDate=" + exportDate + ", origin=" + origin + "]";
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

}
