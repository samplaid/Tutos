package lu.wealins.rest.model.editing;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEditingRequestResponse {

	List<EditingRequest> editingRequests = new ArrayList<>();

	public List<EditingRequest> getEditingRequests() {
		return editingRequests;
	}

	public void setEditingRequests(List<EditingRequest> editingRequests) {
		this.editingRequests = editingRequests;
	}

}
