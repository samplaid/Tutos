package lu.wealins.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lu.wealins.persistence.entity.LissiaFilesInjectionEntity;

@EnableJpaRepositories
public interface LissiaFilesInjectionRepository extends JpaRepository<LissiaFilesInjectionEntity, Long> {

	public List<LissiaFilesInjectionEntity> findByFileNameAndStatus(String filename, int status);
}
