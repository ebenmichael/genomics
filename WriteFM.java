/***
 * Creates and then writes an FM class to file
 * command line arguments
 * file jump outPath
 */

import java.io.*;
import java.util.ArrayList;



public class WriteFM {

	public static void main(String[] args) throws Exception{
		
		String file = args[0];
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		
		ArrayList<String> genomeTmp = new ArrayList<String>();
		
		//index through lines and add to ArrayList
		String temp = br.readLine();
		while(temp != null) {
			String line = temp;
			//if the line length is 60, include it
			if(line.length() == 60) {
				genomeTmp.add(line.replace("N",""));
			}
			temp = br.readLine();
		}
		
		br.close();
		
		
		//go through arraylist and add to new BigArray
		long len = 60 * genomeTmp.size();
		long counter = 0;
		BigArray<Character> genome = new BigArray<Character>(len);
		for(int i = 0; i < genomeTmp.size(); i++) {
			//index through string 
			String seq = genomeTmp.get(i);
			for(int j = 0; j < seq.length(); j++) {
				genome.set(counter, seq.charAt(j));
			}
		}
		//checkpoint jump
		int jump = Integer.parseInt(args[1]);
		
		//construct FM Index
		
		FMIndex fm = new FMIndex(genome,jump);
		
		//write to file
		String outPath = args[2];
		FileOutputStream fout = new FileOutputStream(outPath);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(fm);
		oos.close();
		
		//read the file written to
		FileInputStream fin = new FileInputStream(outPath);
		ObjectInputStream ois = new ObjectInputStream(fin);
		FMIndex out = (FMIndex) ois.readObject();
		ois.close();
		
		if(fm.equals(out)) {
			System.out.println(true);
		}
		
		

	}

}
