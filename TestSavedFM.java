import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class TestSavedFM {


	
	public static void main(String[] args) throws Exception{
		//load fm indices
		System.out.println("Reading FMIndex 1");
		FMIndex2 f1 = readFMIndex(args[0]);
		System.out.println("Reading FMIndex 2");
		FMIndex2 f2 = readFMIndex(args[1]);
		//check if input is in either. Also check reverse complement
		Kmer kIn = new Kmer(args[2]);
		System.out.println("Input in first 1-9 Chromosomes: " + f1.contains(kIn));
		System.out.println("Input in first 10- Chromosomes: " + f2.contains(kIn));
		System.out.println("Input in first 1-9 Chromosomes: " + f1.contains(kIn.reverseComplement()));
		System.out.println("Input in first 10- Chromosomes: " + f2.contains(kIn.reverseComplement()));
		
	}
	
	public static FMIndex2 readFMIndex(String fName) throws Exception{
		FileInputStream fin = new FileInputStream(fName);
		ObjectInputStream ois = new ObjectInputStream(fin);
		FMIndex2 out = (FMIndex2) ois.readObject();
		ois.close();
		return(out);
	}
}
