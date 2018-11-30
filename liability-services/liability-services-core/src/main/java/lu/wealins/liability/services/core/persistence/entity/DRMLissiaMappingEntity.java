package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DRM_LISSIA_MAPPING")
public class DRMLissiaMappingEntity implements Serializable {
	@Embeddable
	public static class DRMLissiaMappingEntityID implements Serializable {
		@Column(name = "TYPE", unique = true, nullable = false)
		private String type;
		
		@Column(name = "DATA_IN", unique = true, nullable = false)
		private String dataIn;

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
	}
	
	@EmbeddedId 
	private DRMLissiaMappingEntityID id;
	
	@Column(name = "DATA_OUT", unique = true, nullable = true)
	private String dataOut;
	
	@Column(name = "DESCRIPTION", unique = true, nullable = true)
	private String description;
	
	@Column(name = "STATUS", unique = true, nullable = true)
	private Boolean status;
	
	public DRMLissiaMappingEntityID getId() {
		return id;
	}

	public void setId(DRMLissiaMappingEntityID id) {
		this.id = id;
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
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
