package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The request used by the {@link SignaletiqueRESTService}.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckSignaletiqueIsinRequest {

	/** The isin/currency pairs to process. */
	private Collection<String> isin = new ArrayList<>();

	public Collection<String> getIsin() {
		return isin;
	}

	public void setIsin(Collection<String> isin) {
		this.isin = isin;
	}
}
