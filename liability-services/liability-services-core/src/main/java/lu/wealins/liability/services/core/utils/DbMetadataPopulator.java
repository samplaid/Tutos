package lu.wealins.liability.services.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.persistence.entity.ChangeMetadata;

@Component
public class DbMetadataPopulator {
	
	private static final String MODIFICATION_PROCESS = "Webia";
	
	@Autowired
	private SecurityContextUtils securityContextUtils;

	private String getChangeAuthor() {
		return securityContextUtils.getPreferredUsername();
	}
	
	private Date getChangeDate() {
		return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	private Date getChangeTime() {
		return Date.from((LocalDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.now()).atZone(ZoneId.systemDefault())).toInstant());
	}
	
	public void setCreationMetadata(ChangeMetadata target) {
		target.setCreatedBy(getChangeAuthor());
		target.setCreatedProcess(MODIFICATION_PROCESS);
		target.setCreatedDate(getChangeDate());
		target.setCreatedTime(getChangeTime());
	}
	
	public void setModificationMetadata(ChangeMetadata target) {
		target.setModifiedBy(getChangeAuthor());
		target.setModifiedProcess(MODIFICATION_PROCESS);
		target.setModifiedDate(getChangeDate());
		target.setModifiedTime(getChangeTime());
	}
}
