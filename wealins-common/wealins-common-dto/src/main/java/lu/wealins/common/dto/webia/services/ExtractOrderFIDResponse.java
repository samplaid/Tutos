package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Extract orderfid object used for the extract order batch
 * 
 * @author xqt5q
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractOrderFIDResponse {

	/**
	 * The list of FID LISSIA
	 */
	private List<OrderFIDDTO> lissiaFID = new ArrayList<>();

	/**
	 * @return the lissiaFID
	 */
	public List<OrderFIDDTO> getLissiaFID() {
		if (this.lissiaFID == null) {
			lissiaFID = new ArrayList<>();
		}
		return lissiaFID;
	}

	/**
	 * @param lissiaFID the lissiaFID to set
	 */
	public void setLissiaFID(List<OrderFIDDTO> lissiaFID) {
		this.lissiaFID = lissiaFID;
	}

	
}
