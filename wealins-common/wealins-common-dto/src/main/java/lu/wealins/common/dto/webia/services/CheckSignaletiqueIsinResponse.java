package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Response used by the SignaletiqueRESTService.
 *
 */
public class CheckSignaletiqueIsinResponse {

	/** The isin/currency pairs found in the database. */
	private Collection<SasIsinDTO> isinFound = new ArrayList<>();

	/** The isin/currency pairs not found in the database. */
	private Collection<String> isinMissing = new ArrayList<>();

	public Collection<SasIsinDTO> getIsinFound() {
		return isinFound;
	}

	public void setIsinFound(Collection<SasIsinDTO> isinFound) {
		this.isinFound = isinFound;
	}

	public Collection<String> getIsinMissing() {
		return isinMissing;
	}

	public void setIsinMissing(Collection<String> isinMissing) {
		this.isinMissing = isinMissing;
	}
}
