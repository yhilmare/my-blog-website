package ServiceImpl;

import domain.blog_page;

public interface DataObject2DB<T> {
	
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord();
	
	//����һ����Ŀ
	public int insertData(Object obj);
	
	//ɾ��һ����Ŀ
	public int deleteData(String id);
	
	//�޸�һ����Ŀ
	public int updateData(Object obj);
	
	//��ѯһ����Ŀ
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame);
	
	//��ѯ��������һ����Ŀ
	public blog_page selectIndexData(int currentPage, int pageContain, int pageInFrame);
	
	//��ѯ��id������һ����Ŀ
	public <T> T selectByID(String id);

}
