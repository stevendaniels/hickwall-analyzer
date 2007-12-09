

public class ArrayListTest {

	public static String getHandler(String[] config, String requestUri)
	  {
		String current = "";
	    for(int i=0; i<config.length; i+=2){
	    	if(requestUri.startsWith(config[i]) || requestUri.equals(config[i])){
	    		if(config[i].length() > current.length()){
	    			current = config[i+1];
	    		}
	    	}	
	    }
	    if(current.equals(""))
	    	return "QCCfoL";
	    else return current;
	  }
}
