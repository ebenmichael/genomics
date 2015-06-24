/****
 * Counts the number of repeats for "AC" e.g. AC, ACAC, ACACAC
 * Command line arguments:
 * fm1Path fm2Path jump
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;


public class CountReps {

 public static void main(String[] args) {
  
  //FMIndex3[] arr = readL(args[0],args[1],Integer.parseInt(args[2]));
  FMIndex4 fm = new FMIndex4(args[0]);
  
  System.out.println(fm.count(new Kmer("ACACAC")));

 }
 
 
 public static FMIndex4[] readL(String path1, String path2, int jump) throws Exception{
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
       System.out.println(count);
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
    FMIndex3 fm1 = new FMIndex3(basesArray1, countArray1, jump);
    System.out.println(fm1.ckptArray[0].length);
    System.out.println(fm1.last.length);
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
      System.out.println(count2);
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
    FMIndex3 fm2 = new FMIndex3(basesArray2, countArray2,jump);
    System.out.println(fm2.ckptArray[0].length);
    System.out.println(fm2.last.length);
    //return fmindices
    FMIndex3[] arr = new FMIndex3[2];
    arr[0] = fm1;
    arr[1] = fm2;
    return(arr);
   }
}
