package lu.wealins.common.dto.webia.services;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckDataContainerDTO {

	private Map<String, CheckDataDTO> checkData = new HashMap<>();

	public Map<String, CheckDataDTO> getCheckData() {
		return checkData;
	}

	public void setCheckData(Map<String, CheckDataDTO> checkData) {
		this.checkData = checkData;
	}
}
