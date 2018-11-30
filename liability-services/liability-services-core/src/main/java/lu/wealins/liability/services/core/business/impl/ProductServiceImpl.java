package lu.wealins.liability.services.core.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.GetProductSpecificitiesRequest;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.ProductLightDTO;
import lu.wealins.liability.services.core.business.ProductService;
import lu.wealins.liability.services.core.mapper.ProductLightMapper;
import lu.wealins.liability.services.core.mapper.ProductMapper;
import lu.wealins.liability.services.core.persistence.entity.ProductEntity;
import lu.wealins.liability.services.core.persistence.entity.ProductSpecificitiesEntity;
import lu.wealins.liability.services.core.persistence.repository.ProductRepository;
import lu.wealins.liability.services.core.persistence.repository.ProductSpecificitiesRepository;

@Component
public class ProductServiceImpl implements ProductService {

	private static final char WHITE_SPACE = ' ';
	private static final int PRD_ID_SIZE = 12;

	private static final String PRODUCT_CODE_CANNOT_BE_NULL = "Product code cannot be null";

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductSpecificitiesRepository productSpecificitiesRepository;

	@Autowired
	private ProductLightMapper productLightMapper;

	@Autowired
	private ProductMapper productMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductService#getActiveProducts()
	 */
	@Override
	public List<ProductLightDTO> getActiveLightProducts() {
		return productLightMapper.asProductLightDTOs(productRepository.findActiveProducts());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductService#getProduct(java.lang.String)
	 */
	@Override
	public ProductDTO getProduct(String prdId) {
		return productMapper.asProductDTO(getProductEntity(prdId));
	}

	public ProductEntity getProductEntity(String prdId) {
		Assert.notNull(prdId, PRODUCT_CODE_CANNOT_BE_NULL);
		String prdIdWithRightPad = org.apache.commons.lang.StringUtils.rightPad(prdId, PRD_ID_SIZE, WHITE_SPACE);

		return productRepository.findOne(prdIdWithRightPad);
	}

	@Override
	public String getCountryCode(String prdId) {
		Assert.notNull(prdId, PRODUCT_CODE_CANNOT_BE_NULL);
		return productRepository.findCountryCode(prdId);
	}

	@Override
	public String getProductSpecificities(GetProductSpecificitiesRequest request) {
		String generalCondition = "";

		List<ProductSpecificitiesEntity> entities = productSpecificitiesRepository.findProductSpecificitiesAfterStartDate(request.getPrdId(), request.getStartDate());

		if (entities != null && !entities.isEmpty()) {
			for (ProductSpecificitiesEntity entity : entities) {
				if (compareDatesForGeneralConditions(entity.getStartDate(), request.getStartDate()) <= 0) {
					if (entity.getEndDate() == null) {
						generalCondition = entity.getGeneralConditions();
						break;
					} else if (compareDatesForGeneralConditions(entity.getEndDate(), request.getStartDate()) >= 0) {
						generalCondition = entity.getGeneralConditions();
						break;
					}
				}
			}
		}

		return generalCondition;
	}

	private int compareDatesForGeneralConditions(Date date1, Date date2) {
		int returnInteger = 0;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);

		if (cal1.before(cal2)) {
			returnInteger = -1;
		}

		if (cal1.after(cal2)) {
			returnInteger = 1;
		}

		return returnInteger;
	}
}
