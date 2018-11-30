package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.UoptDetailDTO;
import lu.wealins.webia.core.service.LiabilityUoptDetailService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityUoptDetailServiceImpl implements LiabilityUoptDetailService {

	private static final String UOP_DETAIL_ID_CANNOT_BE_NULL = "UoptDetail id cannot be null.";
	private static final String LIABILITY_UOP_DETAIL = "liability/uoptDetail/";

	@Autowired
	private RestClientUtils restClientUtils;

	public UoptDetailDTO getUoptDetail(String id) {
		Assert.notNull(id, UOP_DETAIL_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_UOP_DETAIL + "?id=", id, UoptDetailDTO.class);
	}

}
