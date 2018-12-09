package study.inherit;

/**
 * 这个类是用来测试重载的方法是否能重写
 * 重载的方法可以正常重写，但是不能@Override
 * 不应该提倡这种做法，会很混乱
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
