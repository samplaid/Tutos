package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The request used by the {@link SignaletiqueRESTService}.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveSignaletiqueRequest {

	/** The isin/currency pairs to process. */
	private Collection<SasIsinDTO> isinData = new ArrayList<>();

	public Collection<SasIsinDTO> getIsinData() {
		return isinData;
	}

	public void setIsinData(Collection<SasIsinDTO> isinData) {
		this.isinData = isinData;
	}
}
