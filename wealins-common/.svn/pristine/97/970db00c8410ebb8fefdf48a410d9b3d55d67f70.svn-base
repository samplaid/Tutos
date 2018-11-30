package lu.wealins.common.dto;

import java.util.List;

public class PageResultBuilder<T> {

	@SuppressWarnings("boxing")
	public PageResult<T> createPageResult(int page, int size, List<T> content, int nbElements) {
		PageResult<T> r = new PageResult<>();

		r.setSize(size);
		r.setTotalPages((nbElements / size) + 1);
		r.setTotalRecordCount(nbElements);
		r.setCurrentPage(content.isEmpty() ? 1 : page);
		r.setContent(content);

		return r;
	}

}
