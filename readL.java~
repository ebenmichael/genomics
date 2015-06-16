/*
 * args[0] : the first L file
 * args[1] : the second L file
 * args[2] : place you want first file saved
 * args[3] : place you want second file saved
 * args[4] : jump
 * 
 * firstFile secondFile outPath1 outPath2 
 */
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
        
  FileReader inputFile1 = new FileReader(args[0]);
  FileReader inputFile2 = new FileReader(args[1]);
  
  ArrayList<Base> bases1 = new ArrayList<Base>(1750000000);
  ArrayList<Base> bases2 = new ArrayList<Base>(1750000000);
 
  int current1;
  while((current1 = inputFile1.read()) != -1){ 
   char c1 = (char)current1;
   if(c1 == '\n')
     continue;
   try{
    bases1.add(new Base(Character.toString(c1)));
    counts1.put(c1, counts1.get(c1) + 1);
   }
   catch(IllegalArgumentException e){
     System.out.println(c1);
   }
  }
  
  int current2;
  while((current2 = inputFile2.read()) != -1){
   char c2 = (char)current2;
   if(c1 == '\n')
     continue;
   bases2.add(new Base(Character.toString(c2)));
   counts2.put(c2, counts2.get(c2) + 1);
  }
  
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
  
  Base[] baseArray1 = (Base[]) bases1.toArray();
  Base[] baseArray2 = (Base[]) bases2.toArray();
  
  FMIndex2 fm1 = new FMIndex2(baseArray1, countArray1, Integer.parseInt(args[4]));
  String outPath1 = args[2];
  FileOutputStream fout1 = new FileOutputStream(outPath1);
  ObjectOutputStream oos1 = new ObjectOutputStream(fout1);
  oos1.writeObject(fm1);
  oos1.close();
  
  FMIndex2 fm2 = new FMIndex2(baseArray2, countArray2, Integer.parseInt(args[4]));
  String outPath2 = args[3];
  FileOutputStream fout2 = new FileOutputStream(outPath2);
  ObjectOutputStream oos2 = new ObjectOutputStream(fout2);
  oos2.writeObject(fm2);
  oos2.close();
 }
 
}