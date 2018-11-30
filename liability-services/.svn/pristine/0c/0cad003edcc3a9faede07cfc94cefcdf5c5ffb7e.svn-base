package lu.wealins.liability.services.ws.rest.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5364707498900948045L;

	private String id;
	private Class typeOfClass;

	public ObjectNotFoundException(Class typeOfClass, String id) {
		super();
		this.id = id;
		this.typeOfClass = typeOfClass;
	}

	public String getId() {
		return id;
	}
	
	public String getClassName() {
		return typeOfClass == null ? "" : typeOfClass.getCanonicalName();
	}
}
