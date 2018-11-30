package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.UserDTO;
import lu.wealins.liability.services.core.mapper.UserMapper;
import lu.wealins.liability.services.core.persistence.repository.UserRepository;
import lu.wealins.liability.services.ws.rest.UserRESTService;

@Component
public class UserRestServiceImpl implements UserRESTService {

	/** The User repository. */
	@Autowired
	private UserRepository userRepository;

	/** The user mapper. */
	@Autowired
	private UserMapper userMapper;

	private static final String TRIGRAM_CANNOT_BE_NULL = "The trigram cannot be null";

	@Override
	public UserDTO getBytrigram(SecurityContext context, String trigram) {
		Assert.notNull(trigram, TRIGRAM_CANNOT_BE_NULL);
		return userMapper.asUserDTO(userRepository.findOne(trigram));
	}
}
