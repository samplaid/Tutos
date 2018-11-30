package lu.wealins.batch.injection.drm;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentSearchByCreationRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.rest.model.drm.Partner;
import lu.wealins.service.RestUtilityService;

public class InjectLissiaAgentIdsForDRMTasklet implements Tasklet {
	static class AgentDTOSearchResult extends SearchResult<AgentDTO> {
	}

	private static final String EXTRACT_LISSIA_AGENTS_URL = "liability/agent/createdSince";
	private static final String INJECT_AGENTS_DRM_URL = "updateDRMPartners";
	private static final String INJECT_JOB = "injectLissiaAgentIdsForDRM";
	private static final int PAGE_SIZE = 5;

	private Log logger = LogFactory.getLog(InjectLissiaAgentIdsForDRMTasklet.class);

	@Autowired
	private JobService jobService;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Value("${injectLissiaAgentIdsForDRMUser}")
	private String user;

	@Autowired
	private RestUtilityService restUtilityService;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		Date lastExecutionDate;
		Collection<JobExecution> executions = jobService.listJobExecutionsForJob(INJECT_JOB, 0, 2);

		if (executions.size() < 2) {
			lastExecutionDate = new Date(0);
		} else {
			JobExecution je = (JobExecution) executions.toArray()[1];

			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(je.getCreateTime());
			gc.add(Calendar.DATE, -1);

			lastExecutionDate = gc.getTime();
		}

		logger.info("Synchronize Lissia ID of agents created after " + lastExecutionDate);

		AgentSearchByCreationRequest request = new AgentSearchByCreationRequest();
		request.setCreatedBy(user);
		request.setCreationDate(lastExecutionDate);
		request.setPageSize(PAGE_SIZE);

		int pageNum = 0;
		int injected = 0;
		SearchResult<AgentDTO> results;

		do {
			pageNum++;
			request.setPageNum(pageNum);

			results = (SearchResult<AgentDTO>) restUtilityService.post(piaRootContextURL + EXTRACT_LISSIA_AGENTS_URL, request, AgentDTOSearchResult.class);
			logger.info("Page " + pageNum + "/" + results.getTotalPageCount());

			for (AgentDTO agent : results.getContent()) {
				if (agent.getCrmRefererence() == null) {
					continue;
				}

				try {
					Partner partner = new Partner();
					partner.setWealins_partner_id_c(agent.getCrmRefererence());
					partner.setLissia_id_c(agent.getAgtId());

					Partner updatedPartner = restUtilityService.post(piaRootContextURL + INJECT_AGENTS_DRM_URL, partner, Partner.class);

					if (updatedPartner.getLissia_id_c().equals(agent.getAgtId())) {
						injected++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} while (results.getTotalPageCount() > pageNum);

		logger.info(injected + "/" + results.getTotalRecordCount() + " Lissia ID injected in DRM");

		return RepeatStatus.FINISHED;
	}

}
