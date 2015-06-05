
public class Kmer {
	
	Base[] kmer;
	
	public Kmer(String input) {
		/**Constructor**/
		//split the input into a char array and get a Base array
		char[] letters = input.toCharArray();
		int n = input.length();
		//initialize kmer
		kmer = new Base[n];
		for(int i = 0; i < n; i++){
			if(letters[i] != ' ') {
				kmer[i] = new Base(letters[i]);				
			}

		}
	}
	
	public Kmer(Kmer k) {
		/**Clone constructor**/
		this(k.toString());
	}
	
	public String toString() {
		/**Returns a string representation of the k-mer**/
		//initialize string
		String s = "";
		for(Base b : this.kmer) {
			//concatenate string representation of each base
			s += b.toString();
		}
		
		return(s);
	}
	
	public int compareTo(Kmer other) {
		/**compareTo to implement Comparable Interface **/
		//convert to strings and use them for compareTo
		String thisString = this.toString();
		String otherString = other.toString();
		
		return(thisString.compareTo(otherString));
	}
	
	public boolean equals(Kmer other) {
		//convert to string and check equality
		String thisString = this.toString();
		String otherString = other.toString();
		
		return(thisString.equals(otherString));		
	}
	
	public int length() {
		/**Get the length of the kmer**/
		return(kmer.length);
	}
	
	public Base baseAt(int i) {
		/**Returns the Base at position i**/
		return(kmer[i]);
	}
	
	public void setBaseAt(Base b, int i) {
		/**Set the base at location i to be b **/
		kmer[i] = b;
	}
	
	public Base[] toBaseArray() {
		/**Converts a kmer into an array of bases**/
		return(this.kmer);
	}

}
