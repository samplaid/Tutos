package lu.wealins.webia.ws.rest.request;

public enum TranscoType {
	PAYS("PAYS"), LANGUAGE("LANGUAGE"), DOCUMENT_LANGUAGE("DOCUMENT_LANGUAGE"), DESTINATION_COURRIER("DESTINATION_COURRIER"), TYPE_SUPPORT("TYPE_SUPPORT"), CODE_EVENT(
			"CODE_EVENT"), PERSONNALITE_JURIDIQUE("PERSONNALITE_JURIDIQUE"), TITLE(
					"TITLE"), GENDER("GENDER"), END_ON_INSURED_DEATH("END_ON_INSURED_DEATH");

	private String type;

	/**
	 * @param text
	 */
	private TranscoType(final String type) {
		this.type = type;
	}

}
