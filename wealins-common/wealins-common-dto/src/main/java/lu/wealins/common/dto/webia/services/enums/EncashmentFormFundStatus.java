package lu.wealins.common.dto.webia.services.enums;

/**
 * The status codes enumeration.
 *
 */
public enum EncashmentFormFundStatus {
	NEW,        //  encaissement saisi, modifiable, supprimable physiquement, non encore validé et non passé à la comptabilité
	NEW_POSTED, //  encaissement validé, passé en comptabilité, non modifiable, annulable 
	CANCEL,     //  encaissement passé à la comptabilité, annulé, extourne non encore passé en comptabilité, non visible
	CCL_POSTED;  //  encaissement annulé passé en comptabilité, non modifiable et non annulable, non visible
	
}
