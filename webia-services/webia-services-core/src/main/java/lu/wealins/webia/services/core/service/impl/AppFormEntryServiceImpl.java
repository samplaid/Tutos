package lu.wealins.webia.services.core.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.webia.services.core.service.AppFormEntryService;
import lu.wealins.webia.services.core.service.WorkflowUserService;

@Service
@Transactional
public class AppFormEntryServiceImpl implements AppFormEntryService {

	private static final String EMPTY_STRING = "";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private WorkflowUserService workflowUserService;

	/**
	 * The logger
	 */
	private final Logger logger = LoggerFactory.getLogger(AppFormEntryServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.AppFormEntryService#getAppFormEntries(int, int, java.lang.String, java.lang.Integer, java.util.List, java.util.List)
	 */
	@Override
	@SuppressWarnings("boxing")
	public PageResult<AppFormEntryDTO> getAppFormEntries(int page, int size, String fundId, Integer clientId, List<String> excludedPolicies, List<String> status) {
		if (!(StringUtils.isBlank(fundId) ^ clientId == null)) {
			throw new IllegalArgumentException("Only one value must be set for fundId and clientId.");
		}
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT distinct af.FORM_ID, af.WORKFLOW_ITEM_ID, POLICY_ID, PRODUCT_CD, STATUS_CD, CLIENT_NAME, FIRST_CPS_USER, SECOND_CPS_USER from APP_FORM af");
		
		if(fundId != null) {
			queryBuilder.append(" inner join FUND_FORM ff on af.FORM_ID = ff.FORM_ID where ff.FUND_ID = :fundId");
			parameters.addValue("fundId", fundId);
		}
		
		if (clientId != null) {
			queryBuilder.append(" inner join CLIENT_FORM cf on af.FORM_ID = cf.FORM_ID where cf.CLIENT_ID = :clientId");
			parameters.addValue("clientId", clientId);
		}

		if (CollectionUtils.isNotEmpty(excludedPolicies)) {
			queryBuilder.append(" and POLICY_ID not in (:excludedPolicies)");
			parameters.addValue("excludedPolicies", excludedPolicies);
		}

		if (CollectionUtils.isNotEmpty(status)) {
			queryBuilder.append(" and af.STATUS_CD in (:status)");
			parameters.addValue("status", status);
		}

		return executeAppFormQuery(parameters, queryBuilder.toString(), page, size, (rs) -> createAppFormEntries(rs));
	}

	@Override
	@SuppressWarnings("boxing")
	public PageResult<AppFormEntryDTO> getAppFormEntries(int page, int size, String partnerId, String partnerCategory, Collection<String> fundIds, List<String> status) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		// ***** Setup properties *******
		StringBuilder queryBuilder = new StringBuilder("SELECT distinct af.FORM_ID, af.WORKFLOW_ITEM_ID, af.POLICY_ID, af.PRODUCT_CD, af.STATUS_CD, af.CLIENT_NAME, FIRST_CPS_USER, SECOND_CPS_USER from APP_FORM af");

		if(partnerId != null) {
			queryBuilder.append(" inner join PARTNER_FORM pf on af.FORM_ID = pf.FORM_ID");
		}
		if (fundIds != null) {
			queryBuilder.append(" inner join FUND_FORM ff on ff.FORM_ID = af.FORM_ID");
		}
		// ******************************

		// ******* Setup filters ********
		queryBuilder.append(" where 1 = 1");
		if(partnerId != null) {
		
			queryBuilder.append(" and pf.PARTNER_ID = :partnerId");
			if (!AgentCategory.getCategories().contains(partnerCategory)) {
				throw new IllegalStateException("Unknown partner category " + partnerCategory + ".");
			}
			queryBuilder.append(" and pf.PARTNER_CATEGORY = :partnerCategory");
		}
		if (fundIds != null) {
			if (CollectionUtils.isEmpty(fundIds)) {
				logger.warn("No fundIds.");
			}

			queryBuilder.append(" and ff.FUND_ID in (:fundIds)");

		}
		// ******************************

		// ****** Setup parameters ******
		if(partnerId != null) {
			parameters.addValue("partnerId", partnerId);
			parameters.addValue("partnerCategory", partnerCategory);
		}
		if (fundIds != null) {
			if(fundIds.size() == 0) {
				parameters.addValue("fundIds", Arrays.asList(EMPTY_STRING));
			} else {
				parameters.addValue("fundIds", fundIds);
			}
		}
		// ******************************

		if (CollectionUtils.isNotEmpty(status)) {
			queryBuilder.append(" and af.STATUS_CD in (:status)");
			parameters.addValue("status", status);
		}

		return executeAppFormQuery(parameters, queryBuilder.toString(), page, size, (rs) -> createAppFormEntries(rs));

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.AppFormEntryService#getAppFormEntries(int, int, java.lang.String, java.util.List)
	 */
	@Override
	@SuppressWarnings("boxing")
	public PageResult<AppFormEntryDTO> getAppFormEntries(int page, int size, String policyId, List<String> status) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT distinct af.FORM_ID, af.WORKFLOW_ITEM_ID, POLICY_ID, PRODUCT_CD, STATUS_CD, CLIENT_NAME, FIRST_CPS_USER, SECOND_CPS_USER from APP_FORM af");
		queryBuilder.append(" where 1 = 1");
		
		if (policyId != null) {
			queryBuilder.append(" and upper(af.POLICY_ID) = upper(:policyId)");
			parameters.addValue("policyId", policyId);
		}

		if (CollectionUtils.isNotEmpty(status)) {
			queryBuilder.append(" and af.STATUS_CD in (:status)");
			parameters.addValue("status", status);
		}

		return executeAppFormQuery(parameters, queryBuilder.toString(), page, size, (rs) -> createAppFormEntries(rs));
	}

	@SuppressWarnings("boxing")
	private PageResult<AppFormEntryDTO> executeAppFormQuery(MapSqlParameterSource parameters, String query, int page, int size, Function<ResultSet, AppFormEntryDTO> createAppFormEntryFunction) {
		Integer from = (page - 1) * size + 1;
		Integer to = from + size - 1;

		String queryWithLimit = "WITH QUERY_RESULT as ( SELECT T.*, ROW_NUMBER() OVER (ORDER BY WORKFLOW_ITEM_ID) AS ROWNUM FROM ( "
				+ query
				+ ") as T ) SELECT * FROM QUERY_RESULT WHERE ROWNUM BETWEEN " + from + " AND " + to;

		List<AppFormEntryDTO> content = jdbcTemplate.query(
				queryWithLimit,
				parameters,
				new RowMapper<AppFormEntryDTO>() {

					@Override
					public AppFormEntryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						return createAppFormEntryFunction.apply(rs);
					}

				});

		Integer nbElements = jdbcTemplate.queryForObject("with unrestricted as (" + query + ") select count(*) from unrestricted", parameters, Integer.class);

		return createPageResult(page, size, content, nbElements);
	}

	private AppFormEntryDTO createAppFormEntries(ResultSet rs) {
		AppFormEntryDTO h = new AppFormEntryDTO();

		try {
			h.setFormId(rs.getInt(1));
			h.setWorkflowItemId(rs.getString(2));
			h.setPolicyId(rs.getString(3));
			h.setProductCd(rs.getString(4));
			h.setStatusCd(rs.getString(5));
			h.setClientName(rs.getString(6));
			h.setCPS1(getUserName(rs.getString(7)));
			h.setCPS2(getUserName(rs.getString(8)));
		} catch (SQLException e) {
			logger.error("Impossible to create the AppForm entries.", e);
			throw new IllegalStateException("Impossible to create the AppForm entries.", e);
		}

		return h;

	}
	
	private String getUserName(String userId){
		if (StringUtils.isNotBlank(userId)){
			WorkflowUserDTO workflowUser = workflowUserService.getWorkflowUser(userId);
			if (workflowUser != null){
				return workflowUser.getName0();
			}
		}
		return null;
	}

	@SuppressWarnings("boxing")
	private PageResult<AppFormEntryDTO> createPageResult(int page, int size, List<AppFormEntryDTO> content, Integer nbElements) {
		PageResult<AppFormEntryDTO> r = new PageResult<>();

		r.setSize(size);
		r.setTotalPages((nbElements / size) + 1);
		r.setTotalRecordCount(nbElements);
		r.setCurrentPage(content.isEmpty() ? 1 : page);
		r.setContent(content);

		return r;
	}
}
