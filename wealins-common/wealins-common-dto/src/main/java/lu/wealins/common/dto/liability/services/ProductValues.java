package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductValues {
	
	private Collection<ProductValueDTO> list = new ArrayList<ProductValueDTO>();

	public Collection<ProductValueDTO> getList() {
		return list;
	}

	public void setList(Collection<ProductValueDTO> list) {
		this.list = list;
	}
	
	public ProductValues(){}
	
	public ProductValues(Collection<ProductValueDTO> list) {
		this.list = list;
	}
	
}
