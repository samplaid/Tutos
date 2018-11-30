/**
 * 
 */
package lu.wealins.webia.services.core.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AcceptanceReportDTO;
import lu.wealins.webia.services.core.service.AcceptanceReportService;
import lu.wealins.webia.services.ws.rest.AcceptanceReportRESTService;

/**
 * This implements the interface {@link AcceptanceReportRESTService} to realize the acceptance report.
 * 
 * @author oro
 *
 */
@Service
public class AcceptanceReportServiceImpl implements AcceptanceReportService {

	private static final String ACCEPTANCE_REPORT_SQL;

	static {
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" select cs.STEP_ID, lb.LABEL_ORDER, CHECK_ORDER, lb.LABEL_DESC, ");
		queryBuilder.append(" check_desc, DATA_VALUE_YESNONA, DATA_VALUE_TEXT, DATA_VALUE_NUMBER, cd.COMMENT_IF, WORKFLOW_ITEM_ID, sb.SCORE, CHECK_TYPE, ");
		queryBuilder.append(" cw.CHECKCODE ");
		queryBuilder.append(" from CHECK_DATA cd ");
		queryBuilder.append(" INNER JOIN CHECK_WORKFLOW cw on cw.CHECK_ID = cd.CHECK_ID ");
		queryBuilder.append(" INNER JOIN CHECK_STEP cs on cs.CHECK_ID = cd.CHECK_ID ");
		queryBuilder.append(" INNER JOIN STEP s on cs.STEP_ID = s.STEP_ID  ");
		queryBuilder.append(" LEFT OUTER JOIN label lb on lb.label_id = cs.LABEL_ID ");
		queryBuilder.append(" LEFT OUTER JOIN SCORE_BCFT sb on sb.CHECKCODE = cw.CHECKCODE ");
		queryBuilder.append("  and sb.RESPONSE = ( ");
		queryBuilder.append("  SELECT	CASE ");
		queryBuilder.append("  WHEN cw.CHECK_TYPE='YesNo' THEN cd.DATA_VALUE_YESNONA ");
		queryBuilder.append("  WHEN cw.CHECK_TYPE='Number' THEN CONVERT(varchar, cd.DATA_VALUE_NUMBER) ");
		queryBuilder.append("  WHEN cw.CHECK_TYPE='Text' THEN cd.DATA_VALUE_TEXT ");
		queryBuilder.append("  END) ");

		queryBuilder.append(" WHERE (s.STEP_WORKFLOW = 'Acceptance'");
		queryBuilder.append(
				" OR  (s.STEP_WORKFLOW = 'Acceptance Bis' AND cs.CHECK_ID NOT IN(SELECT cs.CHECK_ID FROM CHECK_STEP cs INNER JOIN STEP s on s.STEP_ID=cs.STEP_ID and s.STEP_WORKFLOW='Acceptance')) ");
		queryBuilder.append(
				" OR ( s.STEP_WORKFLOW = 'Request to Client/Partner' and cs.CHECK_ID NOT IN(select cs.CHECK_ID from CHECK_STEP cs INNER JOIN STEP s ON s.STEP_ID=cs.STEP_ID AND s.STEP_WORKFLOW in ('Acceptance Bis', 'Acceptance')))) ");
		queryBuilder.append(" AND WORKFLOW_ITEM_ID = :workFlowItemId");
		queryBuilder.append(" AND s.WORKFLOW_ITEM_TYPE_ID=(select FK_WORKFLOW_ITEM_TYPE from WORKFLOW_ITEM where ID = :workFlowItemId)");
		queryBuilder.append(" ORDER BY STEP_ID, lb.LABEL_ORDER, CHECK_ORDER ");
		ACCEPTANCE_REPORT_SQL = queryBuilder.toString();
	}

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AcceptanceReportDTO> retrieveAcceptanceReport(int workflowId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("workFlowItemId", workflowId);
		return jdbcTemplate.query(ACCEPTANCE_REPORT_SQL, paramMap, new AcceptanceReportRowMapper());
	}

	/**
	 * This inner class aims to map the acceptance data retrieve from the database to the acceptance report object
	 * 
	 * @author oro
	 *
	 */
	private final class AcceptanceReportRowMapper implements RowMapper<AcceptanceReportDTO> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public AcceptanceReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AcceptanceReportDTO acceptanceDto = new AcceptanceReportDTO();
			acceptanceDto.setStepId(rs.getInt("STEP_ID"));
			acceptanceDto.setLabelOrder(rs.getInt("LABEL_ORDER"));
			acceptanceDto.setCheckOrder(rs.getInt("CHECK_ORDER"));
			acceptanceDto.setLabelDesciption(rs.getString("LABEL_DESC"));
			acceptanceDto.setCheckDesciption(rs.getString("CHECK_DESC"));
			acceptanceDto.setDataValueYesNoNa(rs.getString("DATA_VALUE_YESNONA"));
			acceptanceDto.setDataValueText(rs.getString("DATA_VALUE_TEXT"));
			acceptanceDto.setDataValueNumber(rs.getBigDecimal("DATA_VALUE_NUMBER"));
			acceptanceDto.setCommentIf(rs.getString("COMMENT_IF"));
			acceptanceDto.setWorkflowItemId(rs.getInt("WORKFLOW_ITEM_ID"));
			acceptanceDto.setScore(rs.getString("SCORE"));
			acceptanceDto.setCheckType(rs.getString("CHECK_TYPE"));
			acceptanceDto.setCheckCode(rs.getString("CHECKCODE"));
			return acceptanceDto;
		}

	}

}
