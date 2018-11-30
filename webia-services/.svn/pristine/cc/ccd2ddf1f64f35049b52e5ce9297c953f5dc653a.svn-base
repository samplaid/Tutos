package lu.wealins.webia.services.core.components;

import java.util.ArrayList;
import java.util.List;

public class PstIdWrapper {
	private String pstId;
	private List<Long> idSapAccList = new ArrayList<>();
	/**
	 * @return the pstId
	 */
	public String getPstId() {
		return pstId;
	}
	/**
	 * @param pstId the pstId to set
	 */
	public void setPstId(String pstId) {
		this.pstId = pstId;
	}
	/**
	 * @return the idSapAccList
	 */
	public List<Long> getIdSapAccList() {
		return idSapAccList;
	}
	/**
	 * @param idSapAccList the idSapAccList to set
	 */
	public void setIdSapAccList(List<Long> idSapAccList) {
		this.idSapAccList = idSapAccList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pstId == null) ? 0 : pstId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PstIdWrapper other = (PstIdWrapper) obj;
		if (pstId == null) {
			if (other.pstId != null)
				return false;
		} else if (!pstId.equals(other.pstId))
			return false;
		return true;
	}
	
}
