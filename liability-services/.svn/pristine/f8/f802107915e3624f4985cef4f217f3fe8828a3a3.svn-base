package lu.wealins.liability.services.core.persistence.repository; 
import java.util.List;

import lu.wealins.liability.services.core.persistence.entity.GeneralNoteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
public interface GeneralNoteRepository extends JpaRepository<GeneralNoteEntity, Long> {
	
	public List<GeneralNoteEntity> findByPolicy(String PolicyId);
	
	public List<GeneralNoteEntity> findByPolicyAndType(String PolicyId, int type);
} 
