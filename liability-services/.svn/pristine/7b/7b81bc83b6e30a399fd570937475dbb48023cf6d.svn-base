package lu.wealins.liability.services.core.business.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.PolicyFeeRateDTO;
import lu.wealins.liability.services.core.business.PolicyFeeRateService;

@Service
public class PolicyFeeRateServiceImpl implements PolicyFeeRateService {

	/**
	 * This is the SQL query to get the policy fee rate. A policy might have several coverages. But they should have the same fee rate defined, so we only need to get the rate of the first active
	 * coverage and use it as the policy fee rate.
	 * 
	 * The fee rate of a coverage could be defined in policy level or product line level. Lissia stores them in PRODUCT_VALUES table. if the rate can not be found in both levels, we use the fall back
	 * value 0.
	 * 
	 */
	private static final String sqlFeeRate = "WITH  " + "MY_POLICY AS ( SELECT ? POLICY )    " + ",  "
			+ "POC_FEE_RATE AS (    " + "	select 1 as SORT, p.POL_ID, c.COVERAGE, v.NUMERIC_VALUE, c.poc_id as VAL "
			+ "	from POLICIES p join MY_POLICY pd on (p.POL_ID = pd.POLICY)   "
			+ "		join POLICY_COVERAGES c on (p.POL_ID = c.FK_POLICIESPOL_ID)    "
			+ "		join PRODUCT_VALUES v on (c.POC_ID = v.FK_POLICY_COVERPOC_ID)    " + "	where v.STATUS = 1     "
			+ "		and v.CONTROL = 'C12RAT'    " + "	union    "
			+ "	select 2 as SORT, p.POL_ID, c.COVERAGE, v.NUMERIC_VALUE, c.product_line as VAL "
			+ "	from POLICIES p join MY_POLICY pd on (p.POL_ID = pd.POLICY)   "
			+ "		join POLICY_COVERAGES c on (p.POL_ID = c.FK_POLICIESPOL_ID)    "
			+ "		join PRODUCT_VALUES v on (v.PRODUCT_LINE = c.PRODUCT_LINE)    " + "	where v.STATUS = 1     "
			+ "		and v.CONTROL = 'C12RAT' and v.FK_POLICY_COVERPOC_ID is null  " + "	union  "
			+ "	select 3 as SORT, p.POL_ID, c.COVERAGE, 0.00 as NUMERIC_VALUE, c.poc_id as VAL "
			+ "	from POLICIES p join MY_POLICY pd on (p.POL_ID = pd.POLICY) "
			+ "		join POLICY_COVERAGES c on (p.POL_ID = c.FK_POLICIESPOL_ID)  " + ") "
			+ "SELECT * FROM POC_FEE_RATE ORDER BY POL_ID, COVERAGE, SORT ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public PolicyFeeRateDTO getPolicyFeeRate(String policy) {

		List<PolicyFeeRateDTO> list = jdbcTemplate.query(sqlFeeRate, new Object[] { policy },
				new RowMapper<PolicyFeeRateDTO>() {

					@Override
					public PolicyFeeRateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						PolicyFeeRateDTO o = new PolicyFeeRateDTO();
						o.setPolicyId(rs.getString(2));
						o.setCoverage(rs.getString(3));
						o.setFeeRate(rs.getBigDecimal(4));
						return o;
					}

				});

		return !CollectionUtils.isEmpty(list) ? list.get(0) : null;
	}
}
