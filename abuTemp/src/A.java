
public class A {

	private int a;
	
	public boolean compare(A other){
		if(a > other.a)//��A�����棬����ֱ�ӷ�����A��ʵ��other��private����a
			return true;
		else return false;
	}
	
}
