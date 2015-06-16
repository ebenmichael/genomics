import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class FMIndex2 {
    int[] countArray;
    int ckptJump;
    int[][] ckptArray;
    Base[] last;
    
    public FMIndex2(StringBuilder sequence, int jump){
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
    public FMIndex2(String sequence, int jump){
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
    public FMIndex2(Base[] bwt, int[] counts, int jump) {
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
    
    public boolean contains(String s){
        return(this.contains(new Kmer(s)));
    }
    
    public boolean contains(Kmer mer){
        int kmerLength = mer.length();
        
        if(kmerLength == 1){
            int index = baseConversion( mer.toString());
            if(countArray[index] > 0)
                return true;
            else
                return false;
        }
            
        //find the last letter of the kmer
        String lastBase = mer.baseAt(kmerLength - 1).toString();
        
        //obtain the start and end index for that letter to search last column
        int startIndex = getFIndex(lastBase, 0);
        int endIndex = getFIndex(lastBase, countArray[baseConversion(lastBase)] - 1);
        
        
        String base2 = mer.baseAt(kmerLength - 2).toString();
        
        int startRank = getLRank(startIndex-1, base2);
        int endRank = getLRank(endIndex, base2);
        
        if(startRank == endRank)
            return false;
        
        //immediately check if the kmer is not present
        if(endRank < 0) {
            System.out.println("endRank was 0");
            return(false);
        }
        
        //check if the start rank is negative, if it is, correct it
        LinkedList<RankedBase> baseStack = new LinkedList<RankedBase>();
        if(startRank >= 0){
            for(int i = startRank; i <= endRank; i++){
                RankedBase currentBase = new RankedBase(base2, i);
                baseStack.add(currentBase);
            }
        }
        else{
            for(int i = 0; i <= endRank; i++){
                RankedBase currentBase = new RankedBase(base2, i);
                baseStack.add(currentBase);
            }
        }
        
        boolean sequenceFound = false;
        
        //do a dfs to keep searching each potential match
        //to see if the k-mer exists in the reference,
        //until the sequence has been found or DNE
        while(!baseStack.isEmpty() && sequenceFound == false){
            sequenceFound = dfsContains(baseStack.removeLast(), mer.toString().substring(0, kmerLength-2));
        }
        return sequenceFound;
    }
    
    /**
     * @param b the starting base on L
     * @param s the rest of the string not yet verified
     * @return contains the string or not
     */
    private boolean dfsContains(RankedBase b, String s){
        if(s.length() == 0)
            return true;
        else{
            String nextLetter = Character.toString(s.charAt(s.length()-1));
            int rank = b.rank();
            int fIndex = getFIndex(b.toString(),rank);
            if(!last[fIndex].toString().equals(nextLetter))
                return false;
            else{
                int nextRank = getLRank(fIndex, nextLetter);
                RankedBase nextBase = new RankedBase(nextLetter,nextRank);
                return dfsContains(nextBase,s.substring(0,s.length()-1));
            }
        }
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