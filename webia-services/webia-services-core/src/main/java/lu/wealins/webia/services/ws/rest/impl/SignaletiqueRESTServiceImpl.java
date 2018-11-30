package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.mapper.SasIsinMapper;
import lu.wealins.webia.services.core.persistence.entity.SasIsinEntity;
import lu.wealins.webia.services.core.service.SignaletiqueService;
import lu.wealins.webia.services.ws.rest.SignaletiqueRESTService;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueIsinRequest;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueIsinResponse;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueResponse;
import lu.wealins.common.dto.webia.services.SasIsinDTO;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueResponse;

@Component
public class SignaletiqueRESTServiceImpl implements SignaletiqueRESTService {

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(SignaletiqueRESTServiceImpl.class);

	@Autowired
	private SasIsinMapper sasIsinMapper;

	@Autowired
	private SignaletiqueService signaletiqueService;

	@Override
	public CheckSignaletiqueResponse exist(SecurityContext context, final CheckSignaletiqueRequest request) {

		CheckSignaletiqueResponse response = new CheckSignaletiqueResponse();

		for (SasIsinDTO isinDto : request.getIsinData()) {
			if (signaletiqueService.isAvailableInBloomberg(isinDto)) {
				response.getIsinMissing().add(isinDto);
			} else {
				response.getIsinFound().add(isinDto);
			}
		}
		return response;
	}

	@Override
	public CheckSignaletiqueIsinResponse isinExist(SecurityContext context, CheckSignaletiqueIsinRequest request) {

		CheckSignaletiqueIsinResponse response = new CheckSignaletiqueIsinResponse();

		for (String isinCode : request.getIsin()) {
			List<SasIsinEntity> entities = signaletiqueService.findByIsin(isinCode);
			if (!entities.isEmpty()) {
				response.getIsinFound().addAll(sasIsinMapper.asSasIsinDTOList(entities));
			} else {
				response.getIsinMissing().add(isinCode);
			}
		}
		return response;
	}

	@Override
	public SaveSignaletiqueResponse save(SecurityContext context, final SaveSignaletiqueRequest request) {

		SaveSignaletiqueResponse response = new SaveSignaletiqueResponse();

		try {
			for (SasIsinDTO isinDto : request.getIsinData()) {
				response.getProcessedIsin().add(sasIsinMapper.asSasIsinDTO(signaletiqueService.createOrUpdate(isinDto)));
			}
			response.setMessage("Operation succeeded");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return response;
	}

	@Override
	public SaveSignaletiqueResponse inject(SecurityContext context, SaveSignaletiqueRequest request) {

		SaveSignaletiqueResponse response = new SaveSignaletiqueResponse();

		try {
			for (SasIsinDTO isinDto : request.getIsinData()) {
				response.getProcessedIsin().add(sasIsinMapper.asSasIsinDTO(signaletiqueService.inject(isinDto)));
			}
			response.setMessage("Operation succeeded");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return response;

	}
}
