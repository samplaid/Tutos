package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.liability.services.core.business.ProductLineService;
import lu.wealins.liability.services.core.mapper.ProductLineMapper;
import lu.wealins.liability.services.core.mapper.ProductLineWithoutPVsMapper;
import lu.wealins.liability.services.core.mapper.ProductValueMapper;
import lu.wealins.liability.services.core.persistence.entity.ProductLineEntity;
import lu.wealins.liability.services.core.persistence.entity.ProductValueEntity;
import lu.wealins.liability.services.core.persistence.repository.ProductLineRepository;
import lu.wealins.liability.services.core.persistence.repository.ProductValueRepository;

@Service
public class ProductLineServiceImpl implements ProductLineService {

	private static final char WHITE_SPACE = ' ';
	private static final int PRL_ID_SIZE = 12;
	private static final String CONTROL_TYPES_CANNOT_BE_NULL = "Control types cannot be null.";
	private static final String PRODUCT_ID_CANNOT_BE_NULL = "Product Id cannot be null.";
	private static final String PRODUCT_LINE_ID_CANNOT_BE_NULL = "Product line Id cannot be null.";

	@Autowired
	private ProductLineRepository productLineRepository;

	@Autowired
	private ProductValueRepository productValueRepository;

	@Autowired
	private ProductLineMapper productLineMapper;

	@Autowired
	private ProductValueMapper productValueMapper;

	@Autowired
	private ProductLineWithoutPVsMapper productLineWithoutPVsMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductLineService#getProductLines(java.lang.Integer, java.util.List)
	 */
	@Override
	public Collection<ProductLineDTO> getProductLines(String prdId, List<String> controls) {
		Assert.notNull(prdId, PRODUCT_ID_CANNOT_BE_NULL);
		Assert.notNull(controls, CONTROL_TYPES_CANNOT_BE_NULL);
		return productLineMapper.asProductLineDTOs(productLineRepository.findByProductAndControls(prdId, controls));
	}

	public String getProductLine(String polId, Integer coverage) {
		return productLineRepository.findByPolIdAndCoverage(polId, coverage);
	}

	@Override
	public Collection<ProductLineDTO> getFilteredProductLines(String prdId, List<String> controls) {
		Assert.notNull(prdId, PRODUCT_ID_CANNOT_BE_NULL);
		Assert.notNull(controls, CONTROL_TYPES_CANNOT_BE_NULL);
		List<ProductValueEntity> productAndControlsForPL = productValueRepository.findByProductAndControlsForPL(prdId, controls);
		Map<ProductLineEntity, Collection<ProductValueEntity>> map = new HashMap<>();

		for (ProductValueEntity pv : productAndControlsForPL) {
			ProductLineEntity key = pv.getProductLine();
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<>());
			}
			map.get(key).add(pv);
		}

		Collection<ProductLineDTO> response = new ArrayList<>();
		for (ProductLineEntity key : map.keySet()) {
			ProductLineDTO productLine = productLineWithoutPVsMapper.asProductLineDTO(key);
			productLine.setProductValues(productValueMapper.asProductValueDTOs(map.get(key)));

			response.add(productLine);
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductLineService#getProductLine(java.lang.String)
	 */
	@Override
	public ProductLineDTO getProductLine(String prlId) {
		Assert.notNull(prlId, PRODUCT_LINE_ID_CANNOT_BE_NULL);
		return productLineMapper.asProductLineDTO(getProductLineEntity(prlId));
	}

	private ProductLineEntity getProductLineEntity(String prlId) {
		Assert.notNull(prlId, PRODUCT_LINE_ID_CANNOT_BE_NULL);
		String prlIdWithRightPad = org.apache.commons.lang.StringUtils.rightPad(prlId, PRL_ID_SIZE, WHITE_SPACE);

		return productLineRepository.findOne(prlIdWithRightPad);
	}
}
