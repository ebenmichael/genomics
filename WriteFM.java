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
   if(line.charAt(0) == '>') {
    chrom++;
    //if we've exceeded 24 chromosomes (includes X and Y) stop
    if(chrom > 23) {
     break;
    }
    System.out.println(line);
    System.out.println(chrom);
    genome[chrom] = new StringBuilder(genSizes[chrom]);
   }
   else {
    //replace non-read bases
    line = line.replace("N","");
    genome[chrom].append(line);
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
  
  FMIndex fm1 = new FMIndex(genome[0],jump);
  
  //write to file
  String outPath1 = args[2] + "_1";
  FileOutputStream fout1 = new FileOutputStream(outPath1);
  ObjectOutputStream oos1 = new ObjectOutputStream(fout1);
  oos1.writeObject(fm1);
  oos1.close();
  
  //read the file written to
  FileInputStream fin1 = new FileInputStream(outPath1);
  ObjectInputStream ois1 = new ObjectInputStream(fin1);
  FMIndex out1 = (FMIndex) ois1.readObject();
  ois1.close();
  
  if(fm1.equals(out1)) {
   System.out.println(true);
  }
  
  FMIndex fm2 = new FMIndex(genome[1],jump);
  
  //write to file
  String outPath2 = args[2];
  FileOutputStream fout2 = new FileOutputStream(outPath2);
  ObjectOutputStream oos2 = new ObjectOutputStream(fout2);
  oos2.writeObject(fm2);
  oos2.close();
  
  //read the file written to
  FileInputStream fin2 = new FileInputStream(outPath2);
  ObjectInputStream ois2 = new ObjectInputStream(fin2);
  FMIndex out2 = (FMIndex) ois2.readObject();
  ois2.close();
  
  if(fm1.equals(out2)) {
   System.out.println(true);
  }
  
  

 }

}
