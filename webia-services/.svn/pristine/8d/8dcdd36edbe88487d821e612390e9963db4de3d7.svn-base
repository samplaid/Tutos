package lu.wealins.webia.services.core.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.persistence.entity.SequenceEntity;
import lu.wealins.webia.services.core.service.SequenceService;

@Service
@Transactional
public class SequenceServiceImpl implements SequenceService {

	@Autowired
	private EntityManager em;

	@Override
	public String generateNextId(String target) {
		 SequenceEntity sequence = em.find(SequenceEntity.class, target, LockModeType.PESSIMISTIC_WRITE);
		
		 Assert.notNull(sequence, "There is no sequence for " + target);
		
		 StringBuilder idBuilder = new StringBuilder();
		 if (sequence.getPrefix() != null) {
		 idBuilder.append(sequence.getPrefix());
		 }
		
		 Integer nextId = Integer.valueOf(sequence.getSequence().intValue() + 1);
		 idBuilder.append(String.format("%05d", nextId));
		 sequence.setSequence(nextId);
		
		 return idBuilder.toString();
	}

}
