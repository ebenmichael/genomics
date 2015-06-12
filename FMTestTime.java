/**
 * command line arguments:
 * 		filePath numLines jumpSize stringToSearch numTrials typeOfTrial
 */
import java.io.*;
public class FMTestTime {
	
	public static void main(String[] args) throws IOException{
		
		final long readStartTime = System.currentTimeMillis();

		String file = args[0];
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int numLines = Integer.parseInt(args[1]);
		
		//sizes of chromosomes
		int[] genSizes = new int[24];
		genSizes[0] = 226000000;
		genSizes[1] = 239000000;
		genSizes[2] = 195000000;
		genSizes[3] = 188000000;
		genSizes[4] = 178000000;
		genSizes[5] = 168000000;
		genSizes[6] = 156000000;
		genSizes[7] = 143000000;
		genSizes[8] = 121000000;
		genSizes[9] = 132000000;
		genSizes[10] = 132000000;
		genSizes[11] = 131000000;
		genSizes[12] = 96000000;
		genSizes[13] = 89000000;
		genSizes[14] = 82000000;
		genSizes[15] = 79000000;
		genSizes[16] = 78000000;
		genSizes[17] = 75000000;
		genSizes[18] = 56000000;
		genSizes[19] = 60000000;
		genSizes[20] = 36000000;
		genSizes[21] = 35000000;
		genSizes[22] = 152000000;
		genSizes[23] = 23000000;
		int len1 = 0;
		int len2 = 0;
		
		for(int i = 0; i < genSizes.length; i++) {
			if(i < 8) {
				len1 += genSizes[i];
			}
			else {
				len2 += genSizes[i];
			}
		}
		
		genSizes[0] = len1;
		genSizes[8] = len2;
		StringBuilder[] genome = new StringBuilder[24];
		
		//index through lines
		int chrom = -1;
		for(int i = 0; i < numLines; i++) {
			String line = br.readLine();
			//if the line length is 60, include it
			if(line.length() == 60) {
				//replace non-read bases
				line = line.replace("N","");
				genome[chrom].append(line);
			}
			else {
				chrom++;
				genome[chrom] = new StringBuilder(genSizes[chrom]);
			}
		}
		
		br.close();
		//combine into two different stringbuilders
		
		System.out.println(genome[0].length());
		
		StringBuilder[] parts = new StringBuilder[2];
		parts[0] = genome[0];
		parts[1] = genome[8];
		
		for(int i = 1; i < genSizes.length; i++) {
			if(i < 8) {
				if(genome[i] != null) {
					parts[0].append(genome[i]);

				}
			}
			else if(i > 8) {
				if(genome[i] != null) {
					parts[1].append(genome[i]);
				}
			}
		}
		
		genome = parts;
		//System.out.println(genome);
		final long readEndTime = System.currentTimeMillis();
		System.out.println((readEndTime - readStartTime) / 1000.0);
		
		int jump = Integer.parseInt(args[2]);
		String input = args[3];
		int numTrials = Integer.parseInt(args[4]);
		
		String type = args[5];
		//Time the time to create the FM Index for given lengths
		if(type.equals("create")) {
			for(int i = 1; i <= numTrials; i++) {
				final long startTime = System.currentTimeMillis();
				int len = (int) ((double) i / (double) numTrials * genome[0].length());
				FMIndex fm = new FMIndex(genome[0].substring(0, len),jump);
				final long endTime = System.currentTimeMillis();
				final double delta = (endTime - startTime) / (double) 1000;
				System.out.println(len + ", " + delta);
			}
		}

		//Time the time to search for a string given original length
		if(type.equals("search")) {
			for(int i = 2; i <= numTrials; i++) {
				
				int len = (int) ((double) i / (double) numTrials * genome[0].length());
				FMIndex fm = new FMIndex(genome[0].substring(0, len),jump);
				final long startTime = System.currentTimeMillis();
				fm.contains(input);
				final long endTime = System.currentTimeMillis();
				final double delta = (endTime - startTime) / (double) 1000;
				System.out.println(len + ", " + delta);
			}
		}		
		
		//Time the time to search for a string given that string's length
		if(type.equals("substring")) {
			FMIndex fm = new FMIndex(genome[0].toString(),jump);
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
			FMIndex fm = new FMIndex(genome[0].toString(),jump);
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
