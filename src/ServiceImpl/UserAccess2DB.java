package ServiceImpl;

import domain.blog_page;

public interface UserAccess2DB {//�������û��йص����ݿ�����ӿ�
	
	public int getTotalRecord();
	//����һ���û�
	public int insertUser(Object obj);
	//ɾ��һ���û�
	public int deleteUser(Object obj);
	//����һ���û�����Ϣ
	public int updateUser(Object obj);
	//��ѯһ���û����˴���Ҫ�����û�������
	public Object selectUser(String name);
	//�鿴�û��Ƿ����
	public boolean isExist(String name);
	
	public Object selectUserByIndex();
	
	public blog_page selectUserByList(int currentPage, int pageContain, int pageInFrame);
}
