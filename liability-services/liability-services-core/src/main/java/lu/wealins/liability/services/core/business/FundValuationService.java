package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.FundValuationDTO;
import lu.wealins.common.dto.liability.services.FundValuationSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

public interface FundValuationService {

	SearchResult<FundValuationDTO> getFundValuations(FundValuationSearchRequest fund);

}
