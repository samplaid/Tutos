package lu.wealins.rest.model.ods;

import lu.wealins.common.security.ACLContext;

/**
 * The search request class
 * 
 * Any request which need to the pagination service must extend this class
 * 
 * @author xqv60
 *
 */
public class PaginateSearchRequest extends ACLContext {

	/**
	 * The default page size
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * The max page size
	 */
	public static final int MAX_PAGE_SIZE = 200;

	/**
	 * The page number
	 */
	private Integer pageNum;

	/**
	 * The page size
	 */
	private Integer pageSize;

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
