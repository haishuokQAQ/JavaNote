package study.inherit;

/**
 * ������Ե��ǣ����ø��෽����δ��д��ʱ���˵��ù������ĸ���
 * �������Կ��Կ���������Ķ��󽫼̳еĸ���Ԫ�أ�������������ȫ�����뵽�Լ����ֽ����У������������������ε��ø���̳еķ��������յĶ���Ŀ��ȫ���������
 * @author kong.haishuo
 *
 */
public class ClassAscription {

	public static void main(String[] args) {
		Child child = new Child();
		System.out.println(child);
		child.getPaentClassName();
		child.getClassName();
		System.out.println("----------");
		child.invokeOther();
	}
	
	
}
class Parent{
	public void getClassName() {
		System.out.println(this.getClass().getName());
		System.out.println(this);
	}
	
	public void invokeOther() {
		invoked();
	}
	
	public void invoked() {
		System.out.println("Parent invoked.");
	}
}

class Child extends Parent{
	
	@Override
	public void getClassName() {
		super.getClassName();
	}
	
	@Override
	public void invoked() {
		System.out.println("Child invoked.");
	}
	public void getPaentClassName() {
		getClassName();
	}
}