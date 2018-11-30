package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SepaDocumentRequest {

	private List<Long> ids = new ArrayList<>();
	private Long editingId;

	public Long getEditingId() {
		return editingId;
	}

	public void setEditingId(Long editingId) {
		this.editingId = editingId;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
