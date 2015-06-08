
public class Test {
	
	public static void main(String[] args) {
		String str = "ELLOELLO";
		StringCycle[] sc = new StringCycle[str.length()];
		for(int i = 0; i < str.length(); i++) {
			sc[i] = new StringCycle(i);
		}
		
		sc[0].setString(str);
		
		System.out.println(sc[0].compareTo(sc[1]));
		System.out.println(sc[0].compareTo(sc[4]));
		System.out.println(sc[7].compareTo(sc[2]));
	}

}
