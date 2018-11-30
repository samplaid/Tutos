package lu.wealins.liability.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.liability.services.core.business.AgentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
public class AgentServiceImplTest {
	@Autowired
	private AgentService agentService;

	private Date getDateOnly(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private Date getTimeOnly(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, 0);
		return calendar.getTime();
	}

	private boolean dateGreaterThan(Date d1, Date d2) {
		return getDateOnly(d1).after(getDateOnly(d2));
	}

	private boolean dateEquals(Date d1, Date d2) {
		return getDateOnly(d1).equals(getDateOnly(d2));
	}

	private boolean timeEqualsOrGreaterThan(Date d1, Date d2) {
		d1 = getTimeOnly(d1);
		d2 = getTimeOnly(d2);

		return d1.after(d2) || d1.equals(d2);
	}

	@Test
	@Ignore
	public void getAgentByCreationDate() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.HOUR, -1);
		Date today = gc.getTime(); // Automatically added in DTO

		AgentDTO agent = new AgentDTO();
		agent.setCreatedDate(today);
		agent.setName("AgentName");

		agentService.create(agent);

		SearchResult<AgentDTO> results = agentService.getAgentsCreatedSince(today, 1, 10);
		Assert.assertNotEquals(0, results.getTotalRecordCount());

		for (AgentDTO result : results.getContent()) {
			Assert.assertTrue(dateGreaterThan(result.getCreatedDate(), today) || (dateEquals(result.getCreatedDate(), today) && timeEqualsOrGreaterThan(result.getCreatedTime(), today)));
		}
	}

	@Test
	@Ignore
	public void getAgentByCreationDateAndCreator() {
		final String createdBy = "test"; // DTO field is populated with Keycloak data

		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.HOUR, -1);
		Date today = gc.getTime(); // Automatically added in DTO

		AgentDTO agent = new AgentDTO();
		agent.setName("AgentName");

		agentService.create(agent);

		SearchResult<AgentDTO> results = agentService.getAgentsCreatedSince(today, createdBy, 0, 10);
		Assert.assertTrue(results.getTotalRecordCount() > 0);

		for (AgentDTO result : results.getContent()) {
			Assert.assertTrue(dateGreaterThan(result.getCreatedDate(), today) || (dateEquals(result.getCreatedDate(), today) && timeEqualsOrGreaterThan(result.getCreatedTime(), today)));
			Assert.assertEquals(createdBy, result.getCreatedBy());
		}
	}
}
