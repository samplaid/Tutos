package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.CodeLabelEntity;
import lu.wealins.webia.services.core.persistence.entity.CodeLabelEntityId;

public interface CodeLabelRepository extends JpaRepository<CodeLabelEntity, CodeLabelEntityId> {

	public CodeLabelEntity findByTypeCdAndCode(String typeCd, String code);

	public List<CodeLabelEntity> findByTypeCd(String typeCd);
}
