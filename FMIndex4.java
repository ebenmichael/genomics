import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class FMIndex4 implements Serializable{
    int[] countArray;
    int ckptJump;
    int[][] ckptArray;
    Base[] last;
    
    public FMIndex4(StringBuilder sequence, int jump){
        /**Constructor. Gets the Burrows-Wheeler transform for the FM Index.
         * Also gets the checkpoint array
         */
        //add start symbol $ and convert sequence into a kmer
        //Kmer kSeq = new Kmer(sequence + "$");
        //do the burrows-wheeler transform
        last = this.bw(sequence.append("$").toString());
        ckptJump = jump;
        //get ckptArray and countArray
        this.getCkptArray();
    }
    /**
     * Constructor for strings
     */
    public FMIndex4(String sequence, int jump){
        //add start symbol $ and convert sequence into a kmer
        //Kmer kSeq = new Kmer(sequence + "$");
        //do the burrows-wheeler transform
        last = this.bw(sequence);// + "$");
        ckptJump = jump;
        //get ckptArray and countArray
        this.getCkptArray();
    }
    /**
     * Constructor for when the bwt is already calculated
     * @param genome
     * @param counts
     * @param jump
     */
    public FMIndex4(Base[] bwt, int[] counts, int jump) {
     ckptJump = jump;
     last = bwt;
     countArray = counts;
     this.getCkptArray();
    }
    
    public void setJump(int jump) {
     ckptJump = jump;
     this.getCkptArray();
    }
    
    private Base[] bw(String input) {
        /**Do the burrows-wheeler transform using the burrows-wheeler matrix**/
        //rotate matrices and sort rows
        
        //bwm matrix with StringCycles
        StringCycle[] bwm = new StringCycle[input.length()];
        
        for(int i = 0; i < input.length(); i++) {
            //rotate strings with StringCycle
            bwm[i] = new StringCycle(i);
        }
        
        //add the input string as the class string for the String Cycles
        bwm[0].setString(input);
        
        //sort rows
        
        Arrays.sort(bwm);
        
        //get last column
        Base[] l = new Base[input.length()];
        for(int i = 0; i < input.length(); i++) {
            l[i] = new Base(bwm[i].charAt(input.length() - 1));
        }
        
        return(l);
    }
    
    private Kmer rotateKmer(Kmer kmer) {
        /**Puts the end of the kmer at the beginning and does the
         * cyclic permutation**/
        //copy kmer into a new Kmer object
        Kmer kCopy = new Kmer(kmer);
        Base temp = kCopy.baseAt(kCopy.length() -1);
        
        for(int i = kCopy.length() - 1; i > 0; i--) {
            kCopy.setBaseAt(kCopy.baseAt(i - 1), i);
        }
        
        //set temp into the front
        
        kCopy.setBaseAt(temp,0);
        
        return(kCopy);
    }
    
    private void getCkptArray() {
        /**Computes the checkpoint Array **/
        //initialize the array
        
        int numRows;
        if(last.length % ckptJump != 0)
            numRows = last.length/ this.ckptJump + 1;
        else
            numRows = last.length/ this.ckptJump;
        int[][] arr = new int[4][numRows];
        //create and populate counts hashtable
        Hashtable<String,Integer> counts = new Hashtable<String,Integer>(4);
        counts.put("A", 0);
        counts.put("C", 0);
        counts.put("G", 0);
        counts.put("T", 0);
        
        //go through last and count the values of the bases, adding to arr
        //every ckptJump steps
        
        for(int row = 0; row < this.last.length; row++) {
            //get Base as string
            String key = this.last[row].toString();
            //pass this iteration if key is the start index
            if(key.equals("$")) {
                //account for the case where $ falls on a row which is in the
                //checkpoint array
                if(row % ckptJump == 0){
                    for(int b = 0; b < 4; b++) {
                        arr[b][ row / this.ckptJump] = counts.get(intToBase(b));
                    }
                }
                continue;
            }
            //add to the appropriate count
            counts.put(key, counts.get(key) + 1);
            //add current counts to ckptArray if row % ckptJump = 0
            if(row % ckptJump == 0) {
                for(int b = 0; b < 4; b++) {
                    arr[b][ row / this.ckptJump] = counts.get(intToBase(b));
                }
            }
        }
        
        //set the chekpoint array to arr
        this.ckptArray = arr;
        //set countArray to the values in counts
        this.countArray = new int[4];
        for(int b = 0; b < 4; b++) {
            this.countArray[b] = counts.get(intToBase(b));
        }
        
        
    }
    
    public Base[] getLast() {
        /**Returns the last column of the burrows-wheeler matrix**/
        return(this.last);
    }
    
    public int count(Kmer mer){
        int kmerLength = mer.length();
        
        if(kmerLength == 1){
            int index = baseConversion( mer.toString());
                return countArray[index];
        }
        String lastBase = mer.baseAt(kmerLength - 1).toString();
        int sIndex = getFIndex(lastBase, 0);
        int eIndex = getFIndex(lastBase, countArray[baseConversion(lastBase)] - 1);
     int LRank1 = -1, LRank2 = -2;
     int LCheck;
        for(int i = kmerLength - 2; i >= 0; i--){
         String currentBase = mer.baseAt(i).toString();
         LCheck = getLRank(sIndex - 1, currentBase);
         LRank1 = getLRank(sIndex , currentBase);
         LRank2 = getLRank(eIndex, currentBase);
         if(LCheck == LRank2|| LRank2 < 0)
          return 0;
         sIndex = getFIndex(currentBase, LRank1);
         eIndex = getFIndex(currentBase, LRank2);
        }
        
        return LRank2- LRank1 + 1;
    }
    
    /**
     * @param letter the current letter
     * @param rank the rank of the letter
     * @return the index of the specified letter and rank
     */
    private int getFIndex(String letter, int rank){
        if(letter.equals("A")){
            return (rank);
        }
        else if(letter.equals("C")){
            return (countArray[0] + rank);
        }
        else if(letter.equals("G")){
            return (countArray[0] + countArray[1] + rank);
        }
        else if (letter.equals("T")){
            return (countArray[0] + countArray[1] + countArray[2] + rank);
        }
        else{
            System.out.println("Error in getFIndex");
            return -1;
        }
    }
    
    /**
     * @param index, the index in last you want to see the rank of
     * @return the distance to the last checkpoint
     */
    private int distanceToLastCkpt(int index){
        return (index % ckptJump);
    }
    
    /**
     * Get the rank of the specified index - base in L
     * @param index
     * @param base
     * @return the rank
     */
    private int getLRank(int Findex, String base){
        //get the distance between the index and the last checkpoint
        int dist = distanceToLastCkpt(Findex);
        int ckptIndex, currentNum;
        
        //closer to prior checkpoint
        if(dist <= (ckptJump/2) || (Findex > (ckptJump * (ckptArray[0].length - 1)))){
            ckptIndex = Findex - dist;
            currentNum = ckptArray[baseConversion(base)][ckptIndex/ckptJump];
            for(int i = ckptIndex + 1 ; i <= Findex ; i++){
                if(last[i].toString().equals(base)){
                    currentNum++;
                }
            }
            return currentNum - 1;
            
            //closer to next checkpoint
        }
        else{
            ckptIndex = (Findex-dist) + ckptJump;
            currentNum = ckptArray[baseConversion(base)][ckptIndex/ckptJump];
            for(int i = ckptIndex ; i > Findex ; i--){
                if(last[i].toString().equals(base))
                    currentNum--;
            }
            return currentNum - 1;
        }
    }
    
    /**
     * @param base the string of the base to be converted
     * @return index of the base in countArray and ckptArray
     */
    private int baseConversion(String base){
        if(base.equals("A"))
            return 0;
        else if(base.equals("C"))
            return 1;
        else if(base.equals("G"))
            return 2;
        else if(base.equals("T"))
            return 3;
        else{
            System.out.println("Error in baseConversion function: not A,T,C,or G");
            return -1;
        }
    }
    /**
     * @param index of base in countArray and ckptArray
     * @returns base corresponding to index
     **/
    private String intToBase(int index) {
        if(index == 0) {
            return("A");
        }
        else if(index == 1) {
            return("C");
        }
        else if(index == 2) {
            return("G");
        }
        else if(index == 3) {
            return("T");
        }
        else {
            IllegalArgumentException e;
            e = new IllegalArgumentException("Only 0,1,2,3 can be entered");
            throw e;
        }
    }
}