import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;


public class GameText {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File source = new File("D:/game.txt");
		File dest = new File("D:/game2.txt");
		BufferedReader reader = new BufferedReader(new FileReader(source));
		PrintStream ps = new PrintStream(new FileOutputStream(dest));
		String line = null;
		
		line = reader.readLine();
		while(line != null){
			if(line.length() > 4)
				line = line.substring(5);
			ps.println(line);
			line = reader.readLine();
		}
		
	}

}
