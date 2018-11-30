package lu.wealins.webia.services.core.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import lu.wealins.webia.services.core.persistence.entity.WorkflowItemEntity;
import lu.wealins.webia.services.core.persistence.repository.WorkflowItemRepository;
import lu.wealins.webia.services.core.service.AppFormService;
import lu.wealins.webia.services.core.service.WorkflowUtilityService;

@Service
public class WorkflowUtilityServiceImpl implements WorkflowUtilityService {

	private static final String DISTINCT_WORKFLOW_ITEM_ID_SQL = "SELECT DISTINCT workflow_item_id FROM app_form WHERE policy_id = :policyId";

	@Autowired
	@Qualifier(value = "appFormService")
	private AppFormService appFormService;

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private WorkflowItemRepository workflowItemRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getWorkflowItemId(String policyId) {
		SqlParameterSource parameters = new MapSqlParameterSource("policyId", policyId);
		return template.queryForObject(DISTINCT_WORKFLOW_ITEM_ID_SQL, parameters, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("WORKFLOW_ITEM_ID");
			}
		});
	}

	@Override
	public Collection<Long> getWorkflowItemIds(Integer workflowItemType, Integer actionRequired, List<Integer> excludedStatus) {

		Collection<WorkflowItemEntity> workflowItems = workflowItemRepository.findByWorkflowItemType(workflowItemType, actionRequired, excludedStatus);

		return workflowItems.stream().map(x -> x.getId()).collect(Collectors.toSet());

	}
}
