package lu.wealins.liability.services.core.business.impl;

import static lu.wealins.liability.services.core.utils.constantes.Constantes.WS_LISSIA_EXPECTED_CODE;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.wsspscup.WsspscupImport;
import org.tempuri.wsspscup.WsspscupImport.ImpCallMethodCommunications;
import org.tempuri.wsspscup.WsspscupImport.ImpGrpPoc;
import org.tempuri.wsspscup.WsspscupImport.ImpGrpPoc.Row;
import org.tempuri.wsspscup.WsspscupImport.ImpGrpPoc.Row.ImpItmPocPolicyCoverages;
import org.tempuri.wsspscup.WsspscupImport.ImpPolPolicies;
import org.tempuri.wsspscup.WsspscupImport.ImpPscPsc;
import org.tempuri.wsspscup.WsspscupImport.ImpValidationUsers;

import lu.wealins.common.dto.liability.services.enums.PolicyCallFunction;

@Component
public class PolicyWsRequestHelper {

	private static final String LISS_DATE_FORMAT = "yyyyMMdd";

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	public WsspscupImport createAbortRequest(String policyId, Date changeDate, Integer coverage) {

		WsspscupImport request = createBasicWsspscupRequest(policyId, changeDate, PolicyCallFunction.ABORT);

		if (coverage != null) {
			request.setImpGrpPoc(getGrpPoc(coverage));
		}

		return request;
	}

	public WsspscupImport createSurrenderRequest(String policyId, Date changeDate) {
		return createBasicWsspscupRequest(policyId, changeDate, PolicyCallFunction.SURRENDER);
	}

	private WsspscupImport createBasicWsspscupRequest(String policyId, Date changeDate, PolicyCallFunction callFunction) {
		WsspscupImport request = new WsspscupImport();
		request.setImpPolPolicies(getPolicyDetails(policyId));
		request.setImpValidationUsers(getWsUser());
		request.setExitState(WS_LISSIA_EXPECTED_CODE);
		request.setImpPscPsc(getChangeDetails(changeDate));
		request.setImpCallMethodCommunications(getCallMethod(callFunction));
		return request;
	}

	private ImpGrpPoc getGrpPoc(Integer coverage) {
		ImpGrpPoc impGrpPoc = new ImpGrpPoc();
		impGrpPoc.getRows().add(new Row());
		impGrpPoc.getRows().get(0).setImpItmPocPolicyCoverages(new ImpItmPocPolicyCoverages());
		impGrpPoc.getRows().get(0).getImpItmPocPolicyCoverages().setCoverage(coverage.shortValue());
		return impGrpPoc;
	}

	private ImpPscPsc getChangeDetails(Date changeDate) {
		ImpPscPsc changeDetails = new ImpPscPsc();
		SimpleDateFormat dateFormat = new SimpleDateFormat(LISS_DATE_FORMAT);
		changeDetails.setStatusChangeDate(dateFormat.format(changeDate));

		return changeDetails;
	}

	private ImpCallMethodCommunications getCallMethod(PolicyCallFunction callFunction) {
		ImpCallMethodCommunications callMethod = new ImpCallMethodCommunications();
		callMethod.setCallFunction(callFunction.getCode());
		return callMethod;
	}

	private ImpValidationUsers getWsUser() {
		ImpValidationUsers impValidationUsers = new ImpValidationUsers();
		impValidationUsers.setLoginId(wsLoginCredential);
		impValidationUsers.setPassword(wsPasswordCredential);

		return impValidationUsers;
	}

	private ImpPolPolicies getPolicyDetails(String policyId) {
		ImpPolPolicies policyDetails = new ImpPolPolicies();
		policyDetails.setPolId(policyId);
		
		return policyDetails;
	}
}
