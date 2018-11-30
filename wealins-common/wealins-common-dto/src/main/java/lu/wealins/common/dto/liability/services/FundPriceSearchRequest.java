package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FundPriceSearchRequest extends PageableSearchRequest {

	private String fdsId;

	private Date date;

	private List<Integer> types = new ArrayList<>();

	public String getFdsId() {
		return fdsId;
	}

	public void setFdsId(String fdsId) {
		this.fdsId = fdsId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

}