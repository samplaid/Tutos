package lu.wealins.webia.services.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.StatusCode;
import lu.wealins.webia.services.core.persistence.entity.SasIsinEntity;
import lu.wealins.webia.services.core.persistence.repository.SasIsinRepository;
import lu.wealins.webia.services.core.service.SignaletiqueService;
import lu.wealins.common.dto.webia.services.SasIsinDTO;

/**
 * Implementation of {@link SignaletiqueService}.
 *
 */
@Service
public class SignaletiqueImpl implements SignaletiqueService {

	@Autowired
	private SasIsinRepository sasIsinRepository;

	/** {@inheritDoc} */
	@Override
	public boolean isAvailableInBloomberg(SasIsinDTO isinDto) {
		
		List<SasIsinEntity> elements = sasIsinRepository.findByIsinWithStatusSendOrReceived(isinDto.getIsin());
		
		// Search if the ISIN already exists with SEND or RECEIVED status
		if (elements.isEmpty()) {
			
			return true;
		} else {
			SasIsinEntity found = sasIsinRepository.findByIsinAndCurrency(isinDto.getIsin(), isinDto.getCurrency());
			
			if(found != null) {
				// Update fund title and bank bic
				found.setFundTitle(isinDto.getFundTitle());
				found.setBankBic(isinDto.getBankBic());
				sasIsinRepository.save(found);
			}else {
				SasIsinEntity newOne = new SasIsinEntity();				
				newOne.setIsin(isinDto.getIsin());
				newOne.setCurrency(isinDto.getCurrency());
				newOne.setStatusCode(StatusCode.SEND);
				newOne.setCreationDate(new Date());
				newOne.setFundTitle(isinDto.getFundTitle());
				newOne.setBankBic(isinDto.getBankBic());
				sasIsinRepository.save(newOne);

			}
		}

		return false;
	}

	/** {@inheritDoc} */
	@Override
	public List<SasIsinEntity> findByIsin(String isin) {
		return sasIsinRepository.findByIsin(isin);
	}

	/** {@inheritDoc} */
	@Override
	public SasIsinEntity createOrUpdate(SasIsinDTO isinDto) {
		SasIsinEntity sasIsin = sasIsinRepository.findByIsinAndCurrency(isinDto.getIsin(), isinDto.getCurrency());

		if (sasIsin == null) { // not already in database -> create
			sasIsin = new SasIsinEntity();
			sasIsin.setIsin(isinDto.getIsin());
			sasIsin.setCurrency(isinDto.getCurrency());
			sasIsin.setBankBic(isinDto.getBankBic());
			sasIsin.setFundTitle(isinDto.getFundTitle());
			// use provided status if provided
			sasIsin.setStatusCode(isinDto.getStatusCode() == null ? StatusCode.SEND : StatusCode.valueOf(isinDto.getStatusCode()));
			sasIsin.setCreationDate(new Date());
		} else { // already in database -> update status code and persist
			sasIsin.setStatusCode(StatusCode.valueOf(isinDto.getStatusCode()));
			sasIsin.setBankBic(isinDto.getBankBic());
			sasIsin.setFundTitle(isinDto.getFundTitle());
		}
		return sasIsinRepository.save(sasIsin);
	}

	/** {@inheritDoc} */
	@Override
	public SasIsinEntity inject(SasIsinDTO isinDto) {
		SasIsinEntity sasIsin = sasIsinRepository.findByIsinAndCurrency(isinDto.getIsin(), isinDto.getCurrency());
		if (sasIsin == null) { // not already in database -> create
			sasIsin = new SasIsinEntity();
			sasIsin.setIsin(isinDto.getIsin());
			sasIsin.setCurrency(isinDto.getCurrency());
			sasIsin.setBankBic(isinDto.getBankBic());
			sasIsin.setFundTitle(isinDto.getFundTitle());
			// use provided status if provided
			sasIsin.setStatusCode(isinDto.getStatusCode() == null ? StatusCode.SEND : StatusCode.valueOf(isinDto.getStatusCode()));
			sasIsin.setCreationDate(new Date());
			sasIsin = sasIsinRepository.save(sasIsin);
		}
		return sasIsin;
	}
}
