
public class RankedBase extends Base {
	
	private int rank;
	
	public RankedBase(String nucleotide, int r) {
		super(nucleotide);
		rank = r;
	}
	
	public RankedBase(char nucleotide, int r) {
		super(nucleotide);
		rank = r;
	}

	public RankedBase() {
		super();
		rank = -1;
	}
	
	public int rank() {
		/**Returns the rank of the base**/
		return(rank);
	}
	
	public String toString() {
		/**Returns a String representation**/
		
		return(super.toString() + String.valueOf(rank));
	}

}
