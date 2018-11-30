
package lu.wealins.webia.services.core.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.enums.WorkflowStatus;
import lu.wealins.common.dto.webia.services.OperationDTO;
import lu.wealins.webia.services.core.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	private final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String sqlOperationsELissia = "  with policy as ("                                                                                         
			+"	select wim.value0 as polId, wim.FK_WORKFLOW_ITEM_ID as ID from WORKFLOW_ITEM_METADATA wim, WORKFLOW_METADATA_KEY wik"
			+"	where wim.FK_WORKFLOW_METADATA_KEY = wik.ID and wik.description='policy'),"
			+"  product as ("
			+"	select wim.value0 as productCd, wim.FK_WORKFLOW_ITEM_ID as ID from WORKFLOW_ITEM_METADATA wim, WORKFLOW_METADATA_KEY wik"
			+"	where wim.FK_WORKFLOW_METADATA_KEY = wik.ID and wik.description='Product'),"
			+"  client as ("
			+"	select wim.value0 as client, wim.FK_WORKFLOW_ITEM_ID as ID from WORKFLOW_ITEM_METADATA wim, WORKFLOW_METADATA_KEY wik"
			+"	where wim.FK_WORKFLOW_METADATA_KEY = wik.ID and wik.description='Client'),"
			+"  CPS1 as ("
			+"	select u.NAME0 as cps1, wim.FK_WORKFLOW_ITEM_ID as ID from WORKFLOW_ITEM_METADATA wim, WORKFLOW_METADATA_KEY wik, WORKFLOW_USERS u"
			+"	where wim.FK_WORKFLOW_METADATA_KEY = wik.ID and wik.description like '%first%cps%' and u.usr_id=wim.value0),"
			+"  CPS2 as ("
			+"	select u.NAME0 as cps2, wim.FK_WORKFLOW_ITEM_ID as ID from WORKFLOW_ITEM_METADATA wim, WORKFLOW_METADATA_KEY wik, WORKFLOW_USERS u"
			+"	where wim.FK_WORKFLOW_METADATA_KEY = wik.ID and wik.description like '%second%cps%' and u.usr_id=wim.value0)"
			+"  select wi.ID, polId, productCd, wi.status, client, cps1, cps2 from WORKFLOW_ITEM wi, policy, product, client, CPS1, CPS2"
			+"  where wi.ID = policy.ID and wi.ID=product.ID and wi.ID=client.ID and wi.ID=CPS1.ID "
			+"  and wi.ID=CPS2.ID and wi.STATUS != 4 and wi.STATUS != 3"   // <-- excluded "Ended" and "Completed" Item
			+"  and polId in (:policyIds)";

	
	@Override
	public Collection<OperationDTO> getOpenedOperations(List<String> policyIds){
		
		if (CollectionUtils.isEmpty(policyIds)){
			return new ArrayList<OperationDTO>();
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("policyIds", policyIds);
		
		Collection<OperationDTO> operations = jdbcTemplate.query(sqlOperationsELissia, paramMap,
				new RowMapper<OperationDTO>() {

					@Override
					public OperationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						OperationDTO o = new OperationDTO();

						o.setWorkflowItemId(rs.getString(1));
						o.setPolicyId(rs.getString(2));
						o.setProductCd(rs.getString(3));
						try {
							o.setStatusCd(WorkflowStatus.getWorkflowStatus(rs.getInt(4)).name());
						} catch (Exception e){
							o.setStatusCd(WorkflowStatus.ACTIVE.name()); //??
						}
						o.setClientName(rs.getString(5));
						o.setCPS1(rs.getString(6));
						o.setCPS2(rs.getString(7));

						return o;
					}

				});
		if (CollectionUtils.isEmpty(operations)){
			operations = new ArrayList<OperationDTO>();
		}

		operations.forEach(o -> logger.info("PolicId:" + o.getPolicyId() + " - WorkflowItemId:" + o.getWorkflowItemId()));
		return operations;

	}
	
}
