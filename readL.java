import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


public class readL {
 
 public static void main(String[] args) throws IOException{
  Hashtable<Character,Integer> counts = new Hashtable<Character,Integer>(4);
        counts.put('A', 0);
        counts.put('C', 0);
        counts.put('G', 0);
        counts.put('T', 0);
        
  String file = args[0];
  FileReader inputFile = new FileReader(args[0]);
  ArrayList<Base> bases = new ArrayList<Base>(1750000000);
 
  int current;
  while((current = inputFile.read()) != -1){
   char c = (char)current;
   bases.add(new Base(Character.toString(c)));
   counts.put(c, counts.get(c) + 1);
  }
  
  int[] countArray = new int[4];
  countArray[0] = counts.get('A');
  countArray[1] = counts.get('C');
  countArray[2] = counts.get('G');
  countArray[3] = counts.get('T');
  
  Base[] baseArray = (Base[]) bases.toArray();
 }
 
}