package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TaskSetEntity generated by hbm2java
 */
@Entity
@Table(name = "TASK_SETS"

)
public class TaskSetEntity implements java.io.Serializable {

	private int tssId;
	private Integer status;
	private Integer runBasis;
	private int set0;
	private int task;
	private String freqDaily;
	private String freqMonthly;
	private String suppl1Text;
	private Long suppl1No;
	private Integer suppl1DateIncrement;
	private String name;

	@Id
	@Column(name = "TSS_ID", unique = true, nullable = false)
	public int getTssId() {
		return this.tssId;
	}

	public void setTssId(int tssId) {
		this.tssId = tssId;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "RUN_BASIS")
	public Integer getRunBasis() {
		return this.runBasis;
	}

	public void setRunBasis(Integer runBasis) {
		this.runBasis = runBasis;
	}

	@Column(name = "SET0", nullable = false)
	public int getSet0() {
		return this.set0;
	}

	public void setSet0(int set0) {
		this.set0 = set0;
	}

	@Column(name = "TASK", nullable = false)
	public int getTask() {
		return this.task;
	}

	public void setTask(int task) {
		this.task = task;
	}

	@Column(name = "FREQ_DAILY", length = 7)
	public String getFreqDaily() {
		return this.freqDaily;
	}

	public void setFreqDaily(String freqDaily) {
		this.freqDaily = freqDaily;
	}

	@Column(name = "FREQ_MONTHLY", length = 40)
	public String getFreqMonthly() {
		return this.freqMonthly;
	}

	public void setFreqMonthly(String freqMonthly) {
		this.freqMonthly = freqMonthly;
	}

	@Column(name = "SUPPL1_TEXT", length = 20)
	public String getSuppl1Text() {
		return this.suppl1Text;
	}

	public void setSuppl1Text(String suppl1Text) {
		this.suppl1Text = suppl1Text;
	}

	@Column(name = "SUPPL1_NO", precision = 10, scale = 0)
	public Long getSuppl1No() {
		return this.suppl1No;
	}

	public void setSuppl1No(Long suppl1No) {
		this.suppl1No = suppl1No;
	}

	@Column(name = "SUPPL1_DATE_INCREMENT")
	public Integer getSuppl1DateIncrement() {
		return this.suppl1DateIncrement;
	}

	public void setSuppl1DateIncrement(Integer suppl1DateIncrement) {
		this.suppl1DateIncrement = suppl1DateIncrement;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
