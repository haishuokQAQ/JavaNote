package study.inherit;

/**
 * 这里测试的是，调用父类方法（未重写）时，此调用归属于哪个类
 * 经过测试可以看出，子类的对象将继承的父类元素（方法、变量）全部编译到自己的字节码中，在子类对象中无论如何调用父类继承的方法，最终的对象目标全是子类对象。
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