/**
 * Main method to read Kmers from a gzipped fastq file
 * @author Eli
 * Command Line Arguments
 * 	fm1Path fm2Path jump kSize fNames
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;

public class ReadKmer {
	/**
	 * Main method, reads all of the .fastq.gz files passed in
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		//get kSize
		int kSize = Integer.parseInt(args[3]);
		//get fmindices
		FMIndex2[] arr = readL(args[0],args[1],Integer.parseInt(args[2]));
		//index through file names in command line arguments 
		ArrayList<ArrayList<Double>> quals = new ArrayList<ArrayList<Double>>(200);
		for(int i = 4; i < args.length; i++) {
			quals.add(readFile(args[i],kSize,arr[1],arr[0]));
		}
		//print out all of the avgQualities
		//prints out as:
		//              AvgQual 1, ... AvgQual n, numKmer \n
		for(ArrayList<Double> qList : quals){
			for(double q : qList) {
				System.out.print(q + ", ");
			}
			System.out.println();
		}
	}
	/**
	 * Reads the kmers from a file
	 * @param fName
	 * @param kSize
	 * @throws IOException
	 */
	public static ArrayList<Double> readFile(String fName, int kSize,
							FMIndex2 fm1, FMIndex2 fm2) throws IOException{
		//get gzip input stream
		GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(fName));
		BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
		//read first line
		String tmp = br.readLine();
		//read again to get to the first read
		tmp = br.readLine();
		//declare string that holds the quality score
		String qualString;
		//declare the string corresponding to the kmer
		String kString;
		//declare Stringbuilder to hold the read with Ns replaced
		StringBuilder sb;
		//declare the int[] corresponding to the quality array
		int[] qualArr;
		//declare sum int
		int sum;
		//declare ArrayList which holds the average quality scores
		ArrayList<Double> avgQuals = new ArrayList<Double>(1000000);
		//String to hold intermediate lines
		String interLine;
		//int numLine = 0;
		//number of kmers
		int numKmer = 0;
		while(tmp != null){
			//replace all Ns with random bases
			sb = new StringBuilder(tmp);
			for(int i = 0; i < tmp.length(); i++) {
				if(sb.charAt(i) == 'N')
					sb.replace(i,i+1,randomBase());
			}
			//tmp.replaceAll("N", "");
			interLine = br.readLine();
			if(interLine == null)
				break;
			qualString = br.readLine();
			//go through the string and get all overlapping kmers of size kSize
			for(int j = 0; j < sb.length() - kSize; j++) {
				try {
					kString = sb.substring(j,j + kSize);
					//qualArr = new int[kSize];
					sum = 0;
					for(int i = j; i < kString.length(); i++) {
						sum += ((int) qualString.charAt(j)) - 32;
						//System.out.println(((int) qualString.charAt(j)) - 32);
					}
					//add average quality to arraylist
					
					Kmer k = new Kmer(kString);
					//check if the Kmer is in the fmindices
					if(!(fm1.contains(k) || fm2.contains(k) || 
							fm1.contains(k.reverseComplement()) || 
							fm2.contains(k.reverseComplement()))) {
						avgQuals.add((double)sum / (double)kString.length());
					}
					numKmer++;
					
				}
				catch(IllegalArgumentException e)  {
				}
					
			}
			interLine= br.readLine();
			if(interLine == null)
				break;
			tmp = br.readLine();
				
		}
			
		br.close();
		//add the total number of kmers not in the reference
		avgQuals.add((double)avgQuals.size());
		//add the total count to the end of the array 
		avgQuals.add((double)numKmer);
		return(avgQuals);
		
	}
		
	
	
	public static FMIndex2 readFMIndex(String fName) throws Exception{
		FileInputStream fin = new FileInputStream(fName);
		ObjectInputStream ois = new ObjectInputStream(fin);
		FMIndex2 out = (FMIndex2) ois.readObject();
		ois.close();
		return(out);
	}
	
	public static String randomBase() {
		double rand = Math.random();
		if(rand < .25)
			return("A");
		else if(rand > .25 & rand < .5)
			return("C");
		else if(rand > .5 & rand < .75)
			return("G");
		else
			return("T");
	}
	
	public static FMIndex2[] readL(String path1, String path2, int jump) throws Exception{
	  Hashtable<Character,Integer> counts1 = new Hashtable<Character,Integer>(4);
	        counts1.put('A', 0);
	        counts1.put('C', 0);
	        counts1.put('G', 0);
	        counts1.put('T', 0);
	        
	  BufferedReader reader1 = new BufferedReader(new FileReader(path1));
	  
	  ArrayList<Base> bases1 = new ArrayList<Base>(1680373127);
	  //Base[] bases1 = new Base[1680373125];
	  
	  //char[] bases1 = new char[1680373124];
	  //base classes for each type of base
	  Base a = new Base('A');
	  Base c = new Base('C');
	  Base g = new Base('G');
	  Base t = new Base('T');
	  
	  System.out.println("Reading Chromosomes 1-9");
	 
	  String current1;
	  int count = 0;
	  while((current1 = reader1.readLine()) != null){
	 if(count % 100000000 == 0)  
	     //System.out.println(count);
	    try{
	      for(int i = 0; i < current1.length(); i++){
	        char c1 = current1.charAt(i);
	        //bases1[count] = c1;
	        if(c1 == 'A') {
	         bases1.add(a);
	        }
	        else if(c1 == 'C') {
	         bases1.add(c);;
	        }
	        else if(c1 == 'G') {
	         bases1.add(g);
	        }
	        else if(c1 == 'T') {
	         bases1.add(t);
	        }
	        counts1.put(c1, counts1.get(c1) + 1);
	        count++;
	      }
	   }
	   catch(IllegalArgumentException e){
	     //System.out.println(current1);
	   }
	  }
	  //System.out.println(count);
	  reader1.close();
	  
	  //System.out.println("Converting counts 1 Hashtable to int[]");
	  int[] countArray1 = new int[4];
	  countArray1[0] = counts1.get('A');
	  countArray1[1] = counts1.get('C');
	  countArray1[2] = counts1.get('G');
	  countArray1[3] = counts1.get('T');
	  
	  //System.out.println("Converting ArrayList into Array");
	  Base[] basesArray1 = bases1.toArray(new Base[bases1.size()]);
	  
	  System.out.println("Creating FMIndex 1 for Chromosomes 1-9");
	  FMIndex2 fm1 = new FMIndex2(basesArray1, countArray1, jump);

	  //change what bases and fm1 are pointing to to have java do garbage collection
	  bases1 = new ArrayList<Base>();
	  
	  
	  System.out.println("Reading Chromosomes 10-end");
	  
	  Hashtable<Character,Integer> counts2 = new Hashtable<Character,Integer>(4);
	  counts2.put('A', 0);
	  counts2.put('C', 0);
	  counts2.put('G', 0);
	  counts2.put('T', 0);
	  
	  ArrayList<Base> bases2 = new ArrayList<Base>(1421431559);
	  //Base[] bases2 = new Base[1421431557];
	  
	  BufferedReader reader2 = new BufferedReader(new FileReader(path2));
	  
	  String current2;
	  int count2 = 0;
	  while((current2 = reader2.readLine()) != null){ 
	   if(count2 % 100000000 == 0)
	    //System.out.println(count2);
	   try{
	      for(int i = 0; i < current2.length(); i++){
	        char c2 = current2.charAt(i);
	        //bases2[count2] = new Base(c2);
	        if(c2 == 'A') {
	         bases2.add(a);
	        }
	        else if(c2 == 'C') {
	         bases2.add(c);
	        }
	        else if(c2 == 'G') {
	         bases2.add(g);
	        }
	        else if(c2 == 'T') {
	         bases2.add(t);
	        }
	        counts2.put(c2, counts2.get(c2) + 1);
	        count2++;
	      }
	   }
	   catch(IllegalArgumentException e){
	     //System.out.println(current2);
	   }
	  }
	  reader2.close();
	  //System.out.println("Converting counts 2 Hashtable to int[]");
	  
	  int[] countArray2 = new int[4];
	  countArray2[0] = counts2.get('A');
	  countArray2[1] = counts2.get('C');
	  countArray2[2] = counts2.get('G');
	  countArray2[3] = counts2.get('T');
	  
	  //System.out.println("Converting ArrayList into Array");
	  Base[] basesArray2 = bases2.toArray(new Base[bases2.size()]);
	   
	  System.out.println("Creating FMIndex 2 for Chromosomes 10-");
	  FMIndex2 fm2 = new FMIndex2(basesArray2, countArray2,jump);
	  
	  //return fmindices
	  FMIndex2[] arr = new FMIndex2[2];
	  arr[0] = fm1;
	  arr[1] = fm2;
	  return(arr);
	 }
	

}
