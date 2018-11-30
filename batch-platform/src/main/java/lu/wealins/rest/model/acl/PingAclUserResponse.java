package lu.wealins.rest.model.acl;

/**
 * The ping user response
 * 
 * @author xqv60
 *
 */
public class PingAclUserResponse {

	/**
	 * Flag to indicate if the user exist or not
	 */
	private boolean exist;

	/**
	 * @return the exist
	 */
	public boolean isExist() {
		return exist;
	}

	/**
	 * @param exist the exist to set
	 */
	public void setExist(boolean exist) {
		this.exist = exist;
	}

}
