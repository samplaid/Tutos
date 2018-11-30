package lu.wealins.webia.ws.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTranscoResponse {

	private Transco transco;

	/**
	 * @return the transco
	 */
	public Transco getTransco() {
		return transco;
	}

	/**
	 * @param transco the transco to set
	 */
	public void setTransco(Transco transco) {
		this.transco = transco;
	}
	
	

}
