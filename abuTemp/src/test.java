
import org.junit.Assert;
import org.junit.Test;



public class test {

	@Test
	public void test(){
		String [] config = { "/", "TestServlet", "/test", "TestServlet2" };
		String request = "/";
		Assert.assertEquals("TestServlet", ArrayListTest.getHandler(config, request));
	}
}
