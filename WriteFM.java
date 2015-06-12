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
		String temp = br.readLine();
		String line;
		while(temp != null) {
			line = temp;
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
			temp =br.readLine();
		}
		
		br.close();
		//combine into two different stringbuilders
		
		//System.out.println(genome[0].length());
		
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
		
		
		//checkpoint jump
		int jump = Integer.parseInt(args[1]);
		
		//construct FM Index
		
		FMIndex fm1 = new FMIndex(genome[0].toString(),jump);
		
		//write to file
		String outPath = args[2] + "_1";
		FileOutputStream fout = new FileOutputStream(outPath);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(fm1);
		oos.close();
		
		//read the file written to
		FileInputStream fin = new FileInputStream(outPath);
		ObjectInputStream ois = new ObjectInputStream(fin);
		FMIndex out = (FMIndex) ois.readObject();
		ois.close();
		
		if(fm1.equals(out)) {
			System.out.println(true);
		}
		
		FMIndex fm2 = new FMIndex(genome[0].toString(),jump);
		
		//write to file
		String outPath2 = args[2] + "_2";
		FileOutputStream fout2 = new FileOutputStream(outPath);
		ObjectOutputStream oos2 = new ObjectOutputStream(fout);
		oos.writeObject(fm1);
		oos.close();
		
		//read the file written to
		FileInputStream fin = new FileInputStream(outPath);
		ObjectInputStream ois = new ObjectInputStream(fin);
		FMIndex out = (FMIndex) ois.readObject();
		ois.close();
		
		if(fm1.equals(out)) {
			System.out.println(true);
		}
		
		

	}

}
