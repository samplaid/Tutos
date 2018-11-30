package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResult<T> {

	public static final int DEFAULT_PAGE_SIZE = 20000;

	private long totalRecordCount = 0;

	private int totalPages = 0;

	private int currentPage = 1;

	private int size = DEFAULT_PAGE_SIZE;

	private List<T> content = new ArrayList<T>();

	/**
	 * Optional : indicate the last id found in the current page
	 */
	private Long lastId;

	public PageResult(int currentPage, int size) {
		this.currentPage = currentPage;
		this.size = size;
	}

	public PageResult(int currentPage) {
		this.currentPage = currentPage;
	}

	public PageResult() {

	}

	public List<T> getContent() {
		if (content == null) {
			content = new ArrayList<>();
		}
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getOffset() {
		return currentPage * size;
	}

	public int getPageNumber() {
		return currentPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public boolean hasNext() {
		return getCurrentPage() + 1 < getTotalPages();
	}

	public boolean hasPrevious() {
		return currentPage > 0;
	}

	public boolean isLast() {
		return !hasNext();
	}

	/**
	 * @return the lastId
	 */
	public Long getLastId() {
		return lastId;
	}

	/**
	 * @param lastId the lastId to set
	 */
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

}
