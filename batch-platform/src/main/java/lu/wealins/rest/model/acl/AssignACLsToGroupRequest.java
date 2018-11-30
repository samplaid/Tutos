package lu.wealins.rest.model.acl;

import java.util.List;

public class AssignACLsToGroupRequest {
	
	Long groupId;
	
	List<Long> aclIds;



	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the aclIds
	 */
	public List<Long> getAclIds() {
		return aclIds;
	}

	/**
	 * @param aclIds the aclIds to set
	 */
	public void setAclIds(List<Long> aclIds) {
		this.aclIds = aclIds;
	}

	
	
	
}
