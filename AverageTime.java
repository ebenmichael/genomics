/****
 * Counts the number of repeats for "AC" e.g. AC, ACAC, ACACAC
 * Command line arguments:
 * fm1Path fm2Path jump
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;


public class AverageTime {

 public static void main(String[] args) {
  String s = "CTATTCTACTGTTGTTGAACATGTTGACCTAAAAGAAGGAGGCTGAAGCACAAAATATAATTTAGAGTTTACTTGAGTCAACATGAAGACAACTGCCTGGAAGACTCACACCAAGGAAGCTTGGATTTGAGCTCCCTTTGGCCTTTGTCACAAGCAGGTTTTTATTTTTTATTTTTTATTTATTTATTTATTTTGAGATGGAGTCTCACTCTGTTGCCCAGGCTGGAGTGCAGTGGCGCGAACTCTGCTCCGCCTCCCGGGTTCTCGCCATTCTCCTGCCTCAGCCTCCCTAGTAGCTGGGATTACAGGCACCCGCCACCACGCCCAGCTAATTTTTTTTGTATTTTTTTAATAGAGACGGGGTTTCACCG"
    + "TGTTAGCCAGGATGGTCTCGATCTCCTGACCTCATGATCCGCCTGCCTC"
 + "CTCTTCATTTTCAGATTTCATTTCTGTAAAATAGCAATGGGCTTTGTAGAATTGCTTTTA"
+"GAGTATTGTAAGATAACTTAGGTAATACCTCTAACAAAGAGTCAAACAGATATTGGACAT"
+"ATAACCAATATCTGGTACCGGCTTTCCCCTCCTTTCACAAGCTTAATGGCAATGCAGTTA"
+"AGTCAAGGACCTGGATCAGAATTACGGCATGAGGAACACACTCCATGTTTGTGCACACCC"
+"ATTCCTGGATGACTTTTCAAGGGTCTTCTCTTTTCTTCCACCTGACAACAAGTTCTCCAA"
+"CTTCCCTACTTGGAAGTGGCTGCCTGCCATGAGATAATCCCCTTTCTAATTTGTCCTCCC"
+"ATGAACAATGCACCCAAGGAGTCCTTCCTCTTACTCCATGTCTCCTTGAGCCTGCAGAAT"
+"GGAGCTCAAGTTTCCTAGAGCAGCCACGGTTCCCGCACCACCTCCCCTTCTGCCTTGTGG"
+"GCTGCCAAGCCTGCGTCCACCGTGCGCATGAACTCCAGCATCAGGAAGTTTCATGCGTGT"
+"GCCCCGTGCTCCTATCTGGGCTCACGTGAACGTGCTCTTCTCTCTTTCTGAAATGCCCCT";
  FMIndex4 fm = new FMIndex4(args[0], 100);
  for(int i = 1; i <= 100; i++){
    System.out.print(i + ", ");
    for(int j = 0; j < Math.min(s.length()/i, 50); j++){
      Kmer k = new Kmer(s.substring(j, j+i+1));
      final long startTime = System.nanoTime();
      fm.count(k);
      final long endTime = System.nanoTime();
      final double delta = (endTime - startTime) / (double) 1000;
      System.out.print(delta + ", ");
    }
    System.out.println("end");
  }
 }

 public static FMIndex4 readL(String path1, String path2, int jump) throws Exception{
    Hashtable<Character,Integer> counts1 = new Hashtable<Character,Integer>(4);
          counts1.put('A', 0);
          counts1.put('C', 0);
          counts1.put('G', 0);
          counts1.put('T', 0);
          
    BufferedReader reader1 = new BufferedReader(new FileReader(path1));
    ArrayList<Base> bases1 = new ArrayList<Base>(1680373127);

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
    FMIndex4 fm1 = new FMIndex4(basesArray1, countArray1, jump);
    return fm1;
   }
}
