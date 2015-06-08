import java.io.*;
public class FMContains {

	public static void main(String[] args) throws IOException{
		
		String file = args[0];
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int numLines = Integer.parseInt(args[1]);
		String[] lines = new String[numLines];
		for(int i = 0; i < numLines; i++) {
			lines[i] = br.readLine();
		}
		
		br.close();
		
		System.out.println(lines[0].length());
		System.out.println(lines[1].length());
		

	}

}
