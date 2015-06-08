/**
 * command line arguments:
 * 		filePath numLines jumpSize stringToSearch numTrials typeOfTrial
 */
import java.io.*;
public class FMTestTime {
	
	public static void main(String[] args) throws IOException{
		
		String file = args[0];
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int numLines = Integer.parseInt(args[1]);
		
		String genome = "";
		
		//index through lines
		for(int i = 0; i < numLines; i++) {
			String line = br.readLine();
			//if the line length is 60, include it
			if(line.length() == 60) {
				genome += line.replace("N","");
			}
		}
		
		br.close();
		
		//System.out.println(genome);
		
		int jump = Integer.parseInt(args[2]);
		String input = args[3];
		int numTrials = Integer.parseInt(args[4]);
		
		String type = args[5];
		//Time the time to create the FM Index for given lengths
		if(type.equals("create")) {
			for(int i = 1; i <= numTrials; i++) {
				final long startTime = System.currentTimeMillis();
				int len = (int) ((double) i / (double) numTrials * genome.length());
				FMIndex fm = new FMIndex(genome.substring(0, len),jump);
				final long endTime = System.currentTimeMillis();
				final double delta = (endTime - startTime) / (double) 1000;
				System.out.println(len + ", " + delta);
			}
		}

		//Time the time to search for a string given original length
		if(type.equals("search")) {
			for(int i = 2; i <= numTrials; i++) {
				
				int len = (int) ((double) i / (double) numTrials * genome.length());
				FMIndex fm = new FMIndex(genome.substring(0, len),jump);
				final long startTime = System.currentTimeMillis();
				fm.contains(input);
				final long endTime = System.currentTimeMillis();
				final double delta = (endTime - startTime) / (double) 1000;
				System.out.println(len + ", " + delta);
			}
		}		
		
		//Time the time to search for a string given that string's length
		if(type.equals("substring")) {
			FMIndex fm = new FMIndex(genome,jump);
			for(int i = 2; i <= numTrials; i++) {
				
				int len = (int) ((double) i / (double) numTrials * input.length());
				final long startTime = System.currentTimeMillis();
				fm.contains(input.substring(0, len));
				final long endTime = System.currentTimeMillis();
				final double delta = (endTime - startTime) / (double) 1000;
				System.out.println(len + ", " + delta);
			}
		}		
		//Time the time to search given checkpoint jump size
		if(type.equals("jump")) {
			FMIndex fm = new FMIndex(genome,jump);
			for(int i = 1; i <= numTrials; i++) {
				
				int jumpi = (int) ((double) i / (double) numTrials * jump);
				final long startTime = System.currentTimeMillis();
				fm.setJump(jumpi);
				fm.contains(input);
				final long endTime = System.currentTimeMillis();
				final double delta = (endTime - startTime) / (double) 1000;
				System.out.println(jumpi + ", " + delta);
			}
		}				
		
		
		
		//System.out.println(fm.contains(input));

	}

}
