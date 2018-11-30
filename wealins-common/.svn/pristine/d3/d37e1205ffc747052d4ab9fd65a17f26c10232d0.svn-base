package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Response used by the SignaletiqueRESTService.
 *
 */
public class CheckSignaletiqueResponse {

	/** The isin/currency pairs found in the database. */
	private Collection<SasIsinDTO> isinFound = new ArrayList<>();

	/** The isin/currency pairs not found in the database. */
	private Collection<SasIsinDTO> isinMissing = new ArrayList<>();

	public Collection<SasIsinDTO> getIsinFound() {
		return isinFound;
	}

	public void setIsinFound(Collection<SasIsinDTO> isinFound) {
		this.isinFound = isinFound;
	}

	public Collection<SasIsinDTO> getIsinMissing() {
		return isinMissing;
	}

	public void setIsinMissing(Collection<SasIsinDTO> isinMissing) {
		this.isinMissing = isinMissing;
	}
}
