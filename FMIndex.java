import java.util.*;
public class FMIndex {
	int[] countArray;
	int ckptJump = 100;
	int[][] ckptArray;
	Base[] last;
	
	public FMIndex(String sequence){
	
	}
	
	public boolean contains(String s){
		return true;
	}
	
	public boolean contains(Kmer mer){
		int kmerLength = mer.length();
		//find the last letter of the kmer
		String lastBase = mer.baseAt(kmerLength - 1).toString();
		
		//obtain the start and end index for that letter to search last column
		int startIndex = getFIndex(lastBase, 0);
		int endIndex = getFIndex(lastBase, countArray[baseConversion(lastBase)]);
		
		String base2 = mer.baseAt(kmerLength - 2).toString();
		int startRank = getLRank(startIndex, base2);
		int endRank = getLRank(endIndex, base2);
		
		//immediately check if the kmer is not present
		if(endRank < 0)
			return false;		
		
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
		if(sequenceFound == true)
			return true;
		else
			return false;
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
			if(last[fIndex].toString() != nextLetter)
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
		if(letter.equals("A"))
			return (1+rank);
		else if(letter.equals("C"))
			return (1 + countArray[0] + rank);
		else if(letter.equals("G"))
			return (1 + countArray[0] + countArray[1] + rank);
		else if (letter.equals("T"))
			return (1 + countArray[0] + countArray[1] + countArray[2] + rank);
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
		if(dist < (ckptJump/2)){
			ckptIndex = Findex - dist;
			currentNum = ckptArray[baseConversion(base)][ckptIndex/ckptJump];
			for(int i = ckptIndex/ckptJump + 1 ; i <= Findex ; i++){
				if(last[i].toString().equals(base))
					currentNum++;
			}
			return currentNum - 1;
			
		//closer to next checkpoint
		}
		else{
			ckptIndex = (Findex-dist) + ckptJump;
			currentNum = ckptArray[baseConversion(base)][ckptIndex/ckptJump];
			for(int i = ckptIndex/ckptJump - 1 ; i >= Findex ; i--){
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
}
