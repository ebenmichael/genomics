/*
 * args[0] : the first L file
 * args[1] : the second L file
 * args[2] : place you want first file saved
 * args[3] : place you want second file saved
 * args[4] : jump
 * 
 * firstFile secondFile outPath1 outPath2 
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class readL {
 
 public static void main(String[] args) throws IOException{
   
  Hashtable<Character,Integer> counts1 = new Hashtable<Character,Integer>(4);
        counts1.put('A', 0);
        counts1.put('C', 0);
        counts1.put('G', 0);
        counts1.put('T', 0);
        
 Hashtable<Character,Integer> counts2 = new Hashtable<Character,Integer>(4);
        counts2.put('A', 0);
        counts2.put('C', 0);
        counts2.put('G', 0);
        counts2.put('T', 0);
        
  BufferedReader reader1 = new BufferedReader(new FileReader(args[0]));
  BufferedReader reader2 = new BufferedReader(new FileReader(args[1]));
  
  ArrayList<Base> bases1 = new ArrayList<Base>(1750000000);
  ArrayList<Base> bases2 = new ArrayList<Base>(1750000000);
  System.out.println("Reading Chromosomes 1-9");
 
  String current1;
  int count = 0;
  while((current1 = reader1.readLine()) != null){ 
    System.out.println(current1);
    if(count % 10000 == 0){
      System.out.println(count + " lines read");
      count++;
    }
   try{
     for(int i = 0; i < current1.length(); i++){
       char c1 = current1.charAt(i);
       bases1.add(new Base(c1));
       counts1.put(c1, counts1.get(c1) + 1);
     }
   }
   catch(IllegalArgumentException e){
     System.out.println(current1);
   }
  }
  
  System.out.println("Reading Chromosomes 10-");
  
  reader1.close();
  String current2;
  while((current2 = reader2.readLine()) != null){ 
    if(count % 1000 == 0){
      System.out.println(count + " lines read");
      count++;
    }
   try{
     for(int i = 0; i < current2.length(); i++){
       char c2 = current2.charAt(i);
       bases2.add(new Base(c2));
       counts2.put(c2, counts2.get(c2) + 1);
     }
   }
   catch(IllegalArgumentException e){
     System.out.println(current2);
   }
  }
  
  System.out.println("Converting counts Hashtable to int[]");
  
  reader2.close();

  int[] countArray1 = new int[4];
  countArray1[0] = counts1.get('A');
  countArray1[1] = counts1.get('C');
  countArray1[2] = counts1.get('G');
  countArray1[3] = counts1.get('T');
  
  int[] countArray2 = new int[4];
  countArray2[0] = counts2.get('A');
  countArray2[1] = counts2.get('C');
  countArray2[2] = counts2.get('G');
  countArray2[3] = counts2.get('T');
  System.out.println("Casting ArrayList<Base>s to Base[]");
  Base[] baseArray1 = (Base[]) bases1.toArray();
  Base[] baseArray2 = (Base[]) bases2.toArray();
  System.out.println("Creating FMIndex 1 for Chromosomes 1-9");
  FMIndex2 fm1 = new FMIndex2(baseArray1, countArray1, Integer.parseInt(args[4]));
  System.out.println("Writing FMIndex 1 to disk");
  String outPath1 = args[2];
  FileOutputStream fout1 = new FileOutputStream(outPath1);
  ObjectOutputStream oos1 = new ObjectOutputStream(fout1);
  oos1.writeObject(fm1);
  oos1.close();
  
  System.out.println("Creating FMIndex 2 for Chromosomes 10-");
  FMIndex2 fm2 = new FMIndex2(baseArray2, countArray2, Integer.parseInt(args[4]));
  System.out.println("Writing FMIndex 2 to disk");
  String outPath2 = args[3];
  FileOutputStream fout2 = new FileOutputStream(outPath2);
  ObjectOutputStream oos2 = new ObjectOutputStream(fout2);
  oos2.writeObject(fm2);
  oos2.close();
 }
 
}