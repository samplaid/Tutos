package lu.wealins.common.dto.liability.services;

import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductLineDTO {

	private String prlId;
	private String name;
	private ProductDTO product;
	private Collection<ProductValueDTO> productValues = new HashSet<>(0);

	public String getPrlId() {
		return prlId;
	}

	public void setPrlId(String prlId) {
		this.prlId = prlId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<ProductValueDTO> getProductValues() {
		return productValues;
	}

	public void setProductValues(Collection<ProductValueDTO> productValues) {
		this.productValues = productValues;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

}
