/**
 * This class represents a string which starts at a index i of a string 
 * and wraps around and ends at the i-1th index.
 * @author Eli
 *
 */
public class StringCycle implements Comparable<StringCycle>{
	static String str;
	
	private int index; //start index
	
	/**
	 * Constructor, takes a start index i for the string to start at
	 * @param i, start index
	 */
	public StringCycle(int i) {
		index = i;
	}
	/**
	 * Sets the String which the StringCycles are based off of
	 * @param classString
	 */
	public void setString(String classString) {
		str = classString;
	}
	
	/**
	 * returns the start index
	 */
	public int getStartIndex() {
		return(index);
	}
	
	/**
	 * CompareTo method, does String compareTo for strings starting at 
	 * the given start indices
	 */
	public int compareTo(StringCycle other) {
		int n = str.length(); //length of class string
		//loop through the elements of the strings and do string compareTo
		int val;
		int j = this.index; //start index for this
		int k = other.getStartIndex(); //start index for other
		int thisInd; //current index being considered for this StringCycle 
		int otherInd; //current index being considered for other StringCycle
		for(int i = 0; i < n; i++) {
			thisInd = (j + i) % n;
			otherInd = (k + i) % n;
			//compare the current indices of the strings
			String strAtThisInd = String.valueOf(str.charAt(thisInd));
			String strAtOtherInd = String.valueOf(str.charAt(otherInd));
			val = strAtThisInd.compareTo(strAtOtherInd);
			//if val isn't 0, then return it because this is the answer
			//if val is 0, continue, since the strings were the same
			if(val != 0) {
				return(val);
			}
		}
		//if we get this far, that means that the strings were the same, so
		//return 0
		return(0);
	}

}
