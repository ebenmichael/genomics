
public class Test {
	
	public static void main(String[] args) {
		String s = "ACTGTAGCTACGCATCGCTACGCATCGACTCGCTAGACTACGCATCGCATCGCATCG";
		
		FMIndex2 fm = new FMIndex2(s,5);
		
		System.out.println(fm.contains(args[0]));
	}

}
