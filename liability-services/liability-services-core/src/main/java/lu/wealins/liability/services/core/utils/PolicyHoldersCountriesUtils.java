/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * @author NGA
 *
 */
@Component
public class PolicyHoldersCountriesUtils {

	private static final StringBuilder SQL_POLICY_HOLDERS_COUNTRIES = new StringBuilder();

	@Autowired
	JdbcTemplate jdbcTemplate;

	static {
		SQL_POLICY_HOLDERS_COUNTRIES.append(" select COUNTRIES.ISO_CODE_2_POS as COUNTRY from CLI_POL_RELATIONSHIPS  ");
		SQL_POLICY_HOLDERS_COUNTRIES.append(
				" join CLIENT_CONTACT_DETAILS on CLI_POL_RELATIONSHIPS.FK_CLIENTSCLI_ID = CLIENT_CONTACT_DETAILS.FK_CLIENTSCLI_ID  ");
		SQL_POLICY_HOLDERS_COUNTRIES.append(" join COUNTRIES on COUNTRIES.CTY_ID = CLIENT_CONTACT_DETAILS.COUNTRY ");
		SQL_POLICY_HOLDERS_COUNTRIES.append(
				" where CLI_POL_RELATIONSHIPS.FK_POLICIESPOL_ID = ? and CLI_POL_RELATIONSHIPS.type = 1 and CLIENT_CONTACT_DETAILS.CONTACT_TYPE = 'CORRESP' ");
	}

	public static String getPolicyCountriesRequest() {
		return SQL_POLICY_HOLDERS_COUNTRIES.toString();
	}

	public List<String> getPolicyCountries(String policyId) {
		List<String> countries = jdbcTemplate.query(getPolicyCountriesRequest(),
				new Object[] { policyId }, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						String countryIsoCode2 = new String(rs.getString(1));		
						return countryIsoCode2;
					}

				});

		return countries;
	}
}
