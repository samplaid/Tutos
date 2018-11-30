package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.PostingSetService;
import lu.wealins.liability.services.core.persistence.repository.PostingSetRepository;
import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;

/**
 * @author xqv95
 *
 */
@Component
public class PostingSetServiceImpl implements PostingSetService {


	@Autowired
	private PostingSetRepository postingSetRepository;

	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.core.business.PostingSetService#updateComStatus(java.util.Collection)
	 */
	@Override
	public List<Long> updateComStatus(Collection<PstPostingSetsDTO> request) {
		List<Long> pstIds = new ArrayList<Long>();
		for (PstPostingSetsDTO pstPostingSetsDTO : request) {
			postingSetRepository.updateComStatus(pstPostingSetsDTO.getPstId(), Integer.valueOf(pstPostingSetsDTO.getComStatus()));
			pstIds.add(pstPostingSetsDTO.getPstId());
		}
		
		return pstIds;
	}

	/* (non-Javadoc)
	 * @see lu.wealins.liability.services.core.business.PostingSetService#updateReportStatus(java.util.Collection)
	 */
	@Override
	public List<Long> updateReportStatus(Collection<PstPostingSetsDTO> request) {
		List<Long> pstIds = new ArrayList<Long>();
		for (PstPostingSetsDTO pstPostingSetsDTO : request) {
			postingSetRepository.updateReportStatus(pstPostingSetsDTO.getPstId(), Integer.valueOf(pstPostingSetsDTO.getReportStatus()));
			pstIds.add(pstPostingSetsDTO.getPstId());
		}
		
		return pstIds;
	}

}
