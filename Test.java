import java.io.*;


public class Test {
	
	public static void main(String[] args) throws Exception{
		String s = "ACTGTAGCTACGCATCGCTACGCATCGACTCGCTAGACTACGCATCGCATCGCATCG";
		
		FMIndex2 fm = new FMIndex2(s,5);
		
		System.out.println(fm.contains(args[0]));
		String outPath2 = args[1];
		FileOutputStream fout2 = new FileOutputStream(outPath2);
		ObjectOutputStream oos2 = new ObjectOutputStream(fout2);
		oos2.writeObject(fm);
		oos2.close();
		
		FileInputStream fin2 = new FileInputStream(outPath2);
		ObjectInputStream ois2 = new ObjectInputStream(fin2);
		FMIndex2 out2 = (FMIndex2) ois2.readObject();
		ois2.close();
		
		if(out2.last.equals(fm.last))
			System.out.println("IT WORKED!(?)");
	}

}
