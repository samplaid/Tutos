package lu.wealins.rest.model.editing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEditingResponse {

	private Long requestId;
	private EditingRequestStatus status;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public EditingRequestStatus getStatus() {
		return status;
	}

	public void setStatus(EditingRequestStatus status) {
		this.status = status;
	}

}
