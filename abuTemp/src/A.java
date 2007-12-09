
public class A {

	private int a;
	
	public boolean compare(A other){
		if(a > other.a)//在A类里面，可以直接访问类A的实例other的private变量a
			return true;
		else return false;
	}
	
}
