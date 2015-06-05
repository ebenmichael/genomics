import java.util.BitSet;

public class baseTest {

	public static void main(String[] args) {

		/*Kmer k1 = new Kmer("$CTA");
		Kmer k2 = rotateKmer(k1);
		System.out.println(k1);
		System.out.println(k2);
		System.out.println(k2.compareTo(k1) > 0);*/
		FMIndex fm = new FMIndex("CTAAG");
		Base[] last = fm.last;
		for(Base b : last) {
			System.out.println(b);
		}
		System.out.println(fm.contains("CTA"));
	
	}
	
	
}
