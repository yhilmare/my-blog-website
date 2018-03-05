package domain;

import java.util.List;

public class blog_page {//ע�⣬��endPageС��startPageʱ˵����ǰû��ҳ��
	
	private List list;//��������ݷ��ڴ˴�
	private int totalRecord;//�ܹ��ж�������¼�����캯������
	private int currentPage;//��ǰ��ҳ�룬���캯������
	private int pageContain;//ÿһҳ��������Ϣ���������캯������
	private int startPage;//��ҳ����ʾ�Ŀ�ʼҳ
	private int endPage;//��ҳ����ʾ�Ľ���ҳ
	private int pageInFrame;//��ҳ����ʾ���ٸ�ҳ��
	private int totalPage;//һ���ж���ҳ
	
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
