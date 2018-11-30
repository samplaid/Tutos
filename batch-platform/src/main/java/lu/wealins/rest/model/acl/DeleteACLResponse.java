package lu.wealins.rest.model.acl;

import java.util.List;

public class DeleteACLResponse {

	private List<Long> aclIds;

	public List<Long> getAclIds() {
		return aclIds;
	}

	public void setAclIds(List<Long> aclIds) {
		this.aclIds = aclIds;
	}

}
