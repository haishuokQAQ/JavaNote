package study.inherit;

/**
 * ������������������صķ����Ƿ�����д
 * ���صķ�������������д�����ǲ���@Override
 * ��Ӧ���ᳫ������������ܻ���
 * @author kong.haishuo
 *
 */
public class OverrideOverload {

	public static void main(String[] args) {
		OverrideOverloadChild child = new OverrideOverloadChild();
		child.overridedMethod(123);
		child.overridedMethod("abc");
	}
}
class OverrideOverloadParent{
	public void overridedMethod(String param) {
		System.out.println("String " + param + " parent ");
	}
}

class OverrideOverloadChild extends OverrideOverloadParent{
	@Override
	public void overridedMethod(String param) {
		System.out.println("String " + param + " child");
		super.overridedMethod(param);
	}
	
	public void overridedMethod(int param) {
		System.out.println("Int " + param + " parent");
	}
}
