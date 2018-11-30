package lu.wealins.liability.services.core.business.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.PolicyCommissionRateDTO;
import lu.wealins.liability.services.core.business.PolicyCommissionRateService;

@Service
public class PolicyCommissionRateServiceImpl implements PolicyCommissionRateService {

	/**
	 * Same purpose as the above query, but this time to get the commission rate. The commission is defined in the policy level, otherwise could be none.
	 * 
	 */
	private static final String sqlCommissionRate = "WITH  " + "MY_POLICY AS ( SELECT ? POLICY )    " + ",  "
			+ "POC_COMMISSION_RAT as (    "
			+ "	SELECT 1 AS SORT, P.POL_ID, C.COVERAGE, S.PERCENTAGE, RTRIM(S.AGENT) AGENT, S.PRIMARY_AGENT    "
			+ "	FROM POLICIES P JOIN MY_POLICY PD ON (P.POL_ID = PD.POLICY)   "
			+ "		JOIN POLICY_COVERAGES C ON (P.POL_ID = C.FK_POLICIESPOL_ID)    "
			+ "		JOIN POLICY_AGENT_SHARES S ON (S.FK_POLICIESPOL_ID = C.FK_POLICIESPOL_ID AND S.COVERAGE = C.COVERAGE)    "
			+ "			JOIN AGENTS A ON (S.AGENT = A.AGT_ID)	 " + "	WHERE S.TYPE = 5 AND S.STATUS = 1    "
			+ "			AND A.CATEGORY = 'BK' 	 " + "	UNION    "
			+ "	SELECT 2 AS SORT, P.POL_ID, C.COVERAGE, 0.00 AS PERCENTAGE, '' AS AGENT, 1 AS PRIMARY_AGENT  "
			+ "	FROM POLICIES P JOIN MY_POLICY PD ON (P.POL_ID = PD.POLICY)   "
			+ "		JOIN POLICY_COVERAGES C ON (P.POL_ID = C.FK_POLICIESPOL_ID)  " + ") "
			+ "SELECT * FROM POC_COMMISSION_RAT ORDER BY POL_ID, COVERAGE, SORT, PRIMARY_AGENT";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public PolicyCommissionRateDTO getPolicyCommissionRate(String policy) {

		List<PolicyCommissionRateDTO> list = jdbcTemplate.query(sqlCommissionRate, new Object[] { policy },
				new RowMapper<PolicyCommissionRateDTO>() {

					@Override
					public PolicyCommissionRateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						PolicyCommissionRateDTO o = new PolicyCommissionRateDTO();
						o.setPolicyId(rs.getString(2));
						o.setCoverage(rs.getString(3));
						o.setCommissionRate(rs.getBigDecimal(4));
						o.setAgentId(rs.getString(5));
						return o;
					}

				});

		return !CollectionUtils.isEmpty(list) ? list.get(0) : null;
	}

}
