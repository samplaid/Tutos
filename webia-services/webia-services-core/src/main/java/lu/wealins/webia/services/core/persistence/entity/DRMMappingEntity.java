package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "DRM_LISSIA_MAPPING")
@IdClass(SapMappingEntityId.class)
public class DRMMappingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TYPE")
	private String type;
	@Id
	@Column(name = "DATA_IN")
	private String dataIn;

	@Column(name = "DATA_OUT")
	private String dataOut;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "STATUS")
	private int status;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataIn() {
		return dataIn;
	}

	public void setDataIn(String dataIn) {
		this.dataIn = dataIn;
	}

	public String getDataOut() {
		return dataOut;
	}

	public void setDataOut(String dataOut) {
		this.dataOut = dataOut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

/*
 * Composite Primary Key for SAP_MAPPING
 */
class DRMMappingEntityId implements Serializable {
	String type;
	String dataIn;
	String dataOut;
}