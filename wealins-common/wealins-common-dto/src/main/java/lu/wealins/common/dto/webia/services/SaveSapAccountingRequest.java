package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveSapAccountingRequest {


	
	private List<SapAccountingRowNoEntityDTO> sapAccountingList = new ArrayList<>();

	public List<SapAccountingRowNoEntityDTO> getSapAccountingList() {
		return sapAccountingList;
	}

	public void setSapAccountingList(List<SapAccountingRowNoEntityDTO> sapAccountingList) {
		this.sapAccountingList = sapAccountingList;
	}

	

	
	

}
