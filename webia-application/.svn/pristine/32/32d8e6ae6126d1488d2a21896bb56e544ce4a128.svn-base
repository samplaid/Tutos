package lu.wealins.webia.core.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lu.wealins.common.dto.webia.services.StatementDTO;
import lu.wealins.common.dto.webia.services.StatementWrapperDTO;


@Mapper(componentModel = "spring")
public abstract class StatementDTOMapper {


//	private static final String BROKER_BIL_CODE = "A04160";
//	private static final String BROKER_BIL = "BROKER_BIL";
	private static final String OPCVM_TYPE = "OPCVM";
	private static final String ADM_TYPE = "ADM";
	private static final String ENTRY_TYPE = "ENTRY";
	private static final String SURR_TYPE = "SURR";
	private static final String SWITCH_TYPE = "SWITCH";
		
	private static volatile List<String> ENTRY_TYPE_VALUE = Arrays.asList(ENTRY_TYPE);
	private static volatile List<String> ADM_TYPE_VALUE = Arrays.asList(ADM_TYPE, OPCVM_TYPE, SURR_TYPE, SWITCH_TYPE);
	private static volatile List<String> OPCVM_TYPE_VALUE = Arrays.asList(OPCVM_TYPE);
	private static final Map<String, List<String>> COMMISSION_TYPE = initCommissionType();
	
	public abstract StatementDTO asStatementRequest(StatementWrapperDTO in);

	public abstract StatementWrapperDTO asStatementWrapperDTO(StatementDTO in);
	
	@AfterMapping
	protected StatementWrapperDTO asStatementWrapperDTO(StatementDTO in, @MappingTarget StatementWrapperDTO target) {
		target.setPeriodValue(getPeriodValue(in.getPeriod(), in.getStatementType()));
		target.setStatementTypeValue(getTypeValues(in.getStatementType()));

		return target;
	}
	
	private List<String> getPeriodValue(String period, String statementType){
			List<String> periodValues = new ArrayList<String>();
			if (period != null) {
				period = period.toUpperCase().trim();
				if (period.length() == 6) {  // ex : "Q22018" for second quarter of 2018
					if (ADM_TYPE.equals(statementType) || OPCVM_TYPE.equals(statementType)) {
						if (period.startsWith("Q1", 4)) {
							periodValues.add(period.replace("Q1", "01"));
							periodValues.add(period.replace("Q1", "02"));
							periodValues.add(period.replace("Q1", "03"));
						}
						if (period.startsWith("Q2", 4)) {
							periodValues.add(period.replace("Q2", "04"));
							periodValues.add(period.replace("Q2", "05"));
							periodValues.add(period.replace("Q2", "06"));
						}	
						if (period.startsWith("Q3", 4)) {
							periodValues.add(period.replace("Q3", "07"));
							periodValues.add(period.replace("Q3", "08"));
							periodValues.add(period.replace("Q3", "09"));
						}
						if (period.startsWith("Q4", 4)) {
							periodValues.add(period.replace("Q4", "10"));
							periodValues.add(period.replace("Q4", "11"));
							periodValues.add(period.replace("Q4", "12"));
						}	
					}
					if (ENTRY_TYPE.equals(statementType)) { // ex : "201809" for september-2018
						if (period.matches("[0-9]+")) {
							String mounth = period.substring(4, 6);
							if (Integer.valueOf(mounth) < 13) {
								periodValues.add(period);
							}
						}						
					}
				}
			}
			return periodValues;

	}
	
	private List<String> getTypeValues(String commissionType){
			if (commissionType != null) {
				if (COMMISSION_TYPE.containsKey(commissionType.toUpperCase().trim())) {
					return COMMISSION_TYPE.get(commissionType);
				}
			}
			throw new IllegalArgumentException("Invalid commission type");
	}
	
	/**
	 * Init commission type variable
	 */
	private static Map<String, List<String>> initCommissionType() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put(ENTRY_TYPE, ENTRY_TYPE_VALUE);
		map.put(ADM_TYPE, ADM_TYPE_VALUE);
		map.put(OPCVM_TYPE, OPCVM_TYPE_VALUE);
		return Collections.unmodifiableMap(map);
	}
}



