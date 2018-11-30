package lu.wealins.common.dto.liability.services;

public class FundValuationSearchRequest extends PageableSearchRequest {

	private String fdsId;

	public String getFdsId() {
		return fdsId;
	}

	public void setFdsId(String fdsId) {
		this.fdsId = fdsId;
	}

}
