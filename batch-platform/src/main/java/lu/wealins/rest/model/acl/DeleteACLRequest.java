package lu.wealins.rest.model.acl;

import java.util.List;

import lu.wealins.rest.model.acl.common.ACL;

public class DeleteACLRequest {

	private List<ACL> acls;

	public List<ACL> getAcls() {
		return acls;
	}

	public void setAcls(List<ACL> acls) {
		this.acls = acls;
	}

}
