package lu.wealins.common.dto.liability.services;

public class AgentHierarchyPageableRequest extends AgentHierarchyRequest {

	private Integer pageNum;
	private Integer pageSize;
		
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
