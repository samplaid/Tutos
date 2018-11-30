package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Response used by the SignaletiqueRESTService.
 *
 */
public class SaveSignaletiqueResponse {

	/** Boolean indicating if operation succeeded or not. */
	private boolean success = true;

	/** The message. */
	private String message;

	/** The isin/currency pairs processed in the database. */
	private Collection<SasIsinDTO> processedIsin = new ArrayList<>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<SasIsinDTO> getProcessedIsin() {
		return processedIsin;
	}

	public void setProcessedIsin(Collection<SasIsinDTO> processedIsin) {
		this.processedIsin = processedIsin;
	}
}
