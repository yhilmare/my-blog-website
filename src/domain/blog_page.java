package domain;

import java.util.List;

public class blog_page {//注意，当endPage小于startPage时说明当前没有页数
	
	private List list;//查出的数据放在此处
	private int totalRecord;//总共有多少条记录，构造函数传入
	private int currentPage;//当前的页码，构造函数传入
	private int pageContain;//每一页包含的信息条数，构造函数传入
	private int startPage;//网页上显示的开始页
	private int endPage;//网页上显示的结束页
	private int pageInFrame;//网页上显示多少个页码
	private int totalPage;//一共有多少页
	
	public blog_page(int totalRecord, int currentPage, int pageContain, int pageInFrame){
		this.totalRecord = totalRecord;
		this.pageContain = pageContain;
		totalPage = (totalRecord / pageContain) + ((totalRecord % pageContain) == 0?0:1);
		this.currentPage = currentPage > totalPage?totalPage:currentPage;
		this.pageInFrame = totalPage >= pageInFrame?pageInFrame:totalPage;
		int former = this.pageInFrame / 2;
		startPage = (this.currentPage - former) < 1?1:(this.currentPage - former);
		endPage = this.pageInFrame - 1 + startPage;
		if(endPage > totalPage){
			startPage -= (endPage - totalPage);
			endPage = totalPage;
		}
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageContain() {
		return pageContain;
	}

	public void setPageContain(int pageContain) {
		this.pageContain = pageContain;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPageInFrame() {
		return pageInFrame;
	}

	public void setPageInFrame(int pageInFrame) {
		this.pageInFrame = pageInFrame;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
