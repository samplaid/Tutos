package lu.wealins.utils.exceptions;

public class JobPlannerException extends Exception {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5474428516516L;

	public JobPlannerException () {

    }

    public JobPlannerException (String message) {
        super (message);
    }

    public JobPlannerException (Throwable cause) {
        super (cause);
    }

    public JobPlannerException (String message, Throwable cause) {
        super (message, cause);
    }
}