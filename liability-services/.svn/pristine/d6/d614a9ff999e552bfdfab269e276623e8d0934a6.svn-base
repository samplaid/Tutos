package lu.wealins.liability.services.core.business.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.FundValuationService;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundValuationDTO;
import lu.wealins.common.dto.liability.services.FundValuationSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

@Service
@Transactional
public class FundValuationServiceImpl implements FundValuationService {

	private static final String sqlvalForFEAndFIC = "WITH " +
			"FUND_UNITS AS ( " +
			"	SELECT FUND AS ID, SUM(UNITS) AS TOTAL " +
			"		FROM POLICY_FUND_HOLDINGS  " +
			"		WHERE FUND <> FK_POLICIESPOL_ID AND FUND = ? GROUP BY FUND " +
			"), " +
			"FUND_POL AS ( " +
			"	SELECT FK_POLICIESPOL_ID, FUND, SUM(UNITS) UNITS " +
			"		FROM POLICY_FUND_HOLDINGS JOIN FUND_UNITS on (FUND_UNITS.ID = POLICY_FUND_HOLDINGS.FUND) " +
			"		WHERE FUND <> FK_POLICIESPOL_ID GROUP BY FK_POLICIESPOL_ID, FUND HAVING SUM(UNITS) > 0 " +
			"), " +
			"FUND_PRI AS ( " +
			"	SELECT FK_FUNDSFDS_ID, PRICE, PRICE_TYPE, DATE0, CURRENCY, ROW_NUMBER() OVER (ORDER BY DATE0 DESC) r  " +
			"		FROM FUND_PRICES JOIN FUND_UNITS on (FUND_PRICES.FK_FUNDSFDS_ID = FUND_UNITS.ID) " +
			"		where PRICE_TYPE = 1 AND STATUS = 1 " +
			"), " +
			"FUND_VALUATION AS (SELECT p.POL_ID, p.DATE_OF_COMMENCEMENT, f.UNITS, round(i.PRICE * f.UNITS, 2) VALUE,  " +
			"		u.TOTAL, ROUND(f.UNITS/u.TOTAL*100, 2) PCT, i.PRICE, i.DATE0, i.CURRENCY," +
			"		ROW_NUMBER() OVER (ORDER BY p.POL_ID, f.UNITS) ROWNUM " +
			"	FROM FUND_POL f " +
			"	JOIN POLICIES p on (f.FK_POLICIESPOL_ID = p.POL_ID) " +
			"	JOIN FUND_PRI i on (i.r = 1 and i.FK_FUNDSFDS_ID = f.FUND) " +
			"	JOIN FUND_UNITS u on (u.ID = f.FUND) " +
			") ";

	private static final String sqlval = 
			"WITH " +
			"FUND_UNITS AS ( " +
			"	SELECT FUND AS ID, SUM(UNITS) AS TOTAL " +
			"		FROM POLICY_FUND_HOLDINGS  " +
			"		WHERE FUND <> FK_POLICIESPOL_ID AND FUND = ? GROUP BY FUND " +
			"), " +
			"FUND_POL AS ( " +
			"	SELECT FK_POLICIESPOL_ID, FUND, SUM(UNITS) UNITS " +
			"		FROM POLICY_FUND_HOLDINGS JOIN FUND_UNITS on (FUND_UNITS.ID = POLICY_FUND_HOLDINGS.FUND) " +
			"		WHERE FUND <> FK_POLICIESPOL_ID GROUP BY FK_POLICIESPOL_ID, FUND " +
			"), " +
			"FUND_PRI AS ( " +
			"	SELECT FK_FUNDSFDS_ID, PRICE, PRICE_TYPE, DATE0, CURRENCY, ROW_NUMBER() OVER (ORDER BY DATE0 DESC) r  " +
			"		FROM FUND_PRICES JOIN FUND_UNITS on (FUND_PRICES.FK_FUNDSFDS_ID = FUND_UNITS.ID) " +
			"		WHERE PRICE_TYPE = 1 AND STATUS = 1 " +
			"), " +
			"POLICY_STATUS AS (" +
			"		SELECT 	NUMBER as SUB_STATUS, " +
			"				SUBSTRING(FK_OPTIONSOPT_ID, LEN('STATUS_POL') + 1, LEN(FK_OPTIONSOPT_ID)) as STATUS, " +
			"				DESCRIPTION " +
			"		FROM OPTION_DETAILS " +
			"		WHERE FK_OPTIONSOPT_ID like 'STATUS_POL%'" +
			"), " +
			"FUND_VALUATION AS (SELECT p.POL_ID, p.DATE_OF_COMMENCEMENT, f.UNITS, round(i.PRICE * f.UNITS, 2) VALUE,  " +
			"		u.TOTAL, " +
			"		( CASE	WHEN u.TOTAL <> 0 THEN ROUND(f.UNITS/u.TOTAL * 100, 2) ELSE 0  END) PCT, " +
			"		i.PRICE, i.DATE0, i.CURRENCY, ps.DESCRIPTION, " + 
			"		ROW_NUMBER() OVER (ORDER BY p.POL_ID, f.UNITS) ROWNUM " +
			"	FROM FUND_POL f " +
			"	JOIN POLICIES p on (f.FK_POLICIESPOL_ID = p.POL_ID) " +
			"	JOIN POLICY_STATUS ps on (ISNUMERIC(ps.STATUS) <> 0 and ps.STATUS = p.STATUS and ps.SUB_STATUS = p.SUB_STATUS) " +
			"	JOIN FUND_PRI i on (i.r = 1 and i.FK_FUNDSFDS_ID = f.FUND) " +
			"	JOIN FUND_UNITS u on (u.ID = f.FUND) " +
			") ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FundService fundService;

	@SuppressWarnings("boxing")
	@Override
	public SearchResult<FundValuationDTO> getFundValuations(FundValuationSearchRequest request) {
		Integer from = (request.getPageNum() - 1) * request.getPageSize() + 1;
		Integer to = from + request.getPageSize() - 1;
		
		FundDTO fund = fundService.getFund(request.getFdsId());
		String query = null;
		boolean isFeOrFic = fundService.isFeOrFic(fund);

		if (isFeOrFic) {
			query = sqlvalForFEAndFIC + " SELECT * FROM FUND_VALUATION WHERE ROWNUM BETWEEN " + from + " AND " + to;
		} else {
			query = sqlval + " SELECT * FROM FUND_VALUATION WHERE ROWNUM BETWEEN " + from + " AND " + to;
		}

		Object[] args = new Object[] { request.getFdsId() };
		List<FundValuationDTO> content = jdbcTemplate.query(
				query,
				args,
				new RowMapper<FundValuationDTO>() {

					@Override
					public FundValuationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						FundValuationDTO h = new FundValuationDTO();

						h.setPolicyId(rs.getString(1));
						h.setEffectiveDate(rs.getDate(2));
						h.setUnitsByPolicy(rs.getBigDecimal(3));
						h.setValue(rs.getBigDecimal(4));
						h.setFundId(request.getFdsId());
						h.setUnitsByFund(rs.getBigDecimal(5));
						h.setSplit(rs.getBigDecimal(6));
						h.setPrice(rs.getBigDecimal(7));
						h.setPriceDate(rs.getDate(8));
						h.setCurrency(rs.getString(9));
						if (!isFeOrFic) {
							h.setPolicyStatusDescription(rs.getString(10));
						}

						return h;
					}

				});

		if (isFeOrFic) {
			query = sqlvalForFEAndFIC + " SELECT COUNT(*) FROM FUND_VALUATION";
		} else {
			query = sqlval + " SELECT COUNT(*) FROM FUND_VALUATION";
		}

		Integer nbElements = jdbcTemplate.queryForObject(query, args, Integer.class);

		SearchResult<FundValuationDTO> r = new SearchResult<>();

		r.setPageSize(request.getPageSize());
		r.setTotalPageCount((nbElements / request.getPageSize()) + 1);
		r.setTotalRecordCount(nbElements);
		r.setCurrentPage(content.isEmpty() ? 1 : request.getPageNum());
		r.setContent(content);

		return r;

	}

}
