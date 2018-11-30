package lu.wealins.liability.services.core.persistence.listener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.liability.services.core.persistence.entity.LissiaAuditEntity;

public class LissiaAuditListener {

	private static final String MODIFICATION_PROCESS = "Webia";

	private String getChangeAuthor() {
		return SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
	}

	private Date getChangeDate() {
		return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	private Date getChangeTime() {
		return Date.from((LocalDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.now()).atZone(ZoneId.systemDefault())).toInstant());
	}

	@PrePersist
	public void setCreationMetadata(LissiaAuditEntity target) {
		target.setCreatedBy(getChangeAuthor());
		target.setCreationProcess(MODIFICATION_PROCESS);
		target.setCreatedDate(getChangeDate());
		target.setCreatedTime(getChangeTime());
	}

	@PreUpdate
	public void setModificationMetadata(LissiaAuditEntity target) {
		target.setModifiedBy(getChangeAuthor());
		target.setModificationProcess(MODIFICATION_PROCESS);
		target.setModificationDate(getChangeDate());
		target.setModificationTime(getChangeTime());
	}
}
