import java.io.Serializable;
import java.util.BitSet;
public class Base implements Comparable<Base>, Serializable{
    
    public BitSet base;
    
    public Base(String nucleotide) {
        /**Constructor for string input**/
        //if nucleotide is the start symbol $, create an empty Base
        
        if(nucleotide.equals("$")) {
            base = null;
        }
        else {
            //declare the BitSet array
            base = new BitSet(2);
            //go through each case for the nucleotide and instantiate the array
            //A = 00
            //C = 01
            //G = 10
            //T = 11
            if(nucleotide.equals("A")) {
                
            }
            else if(nucleotide.equals("C")) {
                base.set(0);
            }
            else if(nucleotide.equals("G")) {
                base.set(1);
            }
            else if(nucleotide.equals("T")) {
                base.set(0);
                base.set(1);
            }
            else {
                IllegalArgumentException e;
                e = new IllegalArgumentException("Only A,C,G,T can be entered");
                throw e;
                
            }
        }
        
    }
    
    public Base(char nucleotide){
        /**Constructor for char input**/
        //convert char to string and do the string constructor
        this(String.valueOf(nucleotide));
    }
    
    public Base() {
        /** Empty constructor, sets base to null **/
        base = null;
    }
    
    public String toString() {
        /**Returns string representation of base**/
        //go through cases
        BitSet a = new BitSet(2);
        BitSet c = new BitSet(2);
        c.set(0);
        BitSet g = new BitSet(2);
        g.set(1);
        BitSet t = new BitSet(2);
        t.set(0);
        t.set(1);
        
        if(this.base == null) { return("$");}
        else if(this.base.equals(a)) { return("A");}
        else if(this.base.equals(c)) { return("C");}
        else if(this.base.equals(g)) { return("G");}
        else { return("T");}
        
    }
    
    public int compareTo(Base other) {
        /**Compares two base objects**/
        //if this or other is null, make the one that is null less
        if(this.isEmpty() & !other.isEmpty()) {
            return(-10);
        }
        else if(!this.isEmpty() & other.isEmpty()) {
            return(10);
        }
        else if(this.isEmpty() & other.isEmpty()) {
            return(0);
        }
        else {
            //convert to string and return compared strings
            String baseString = this.toString();
            String otherString = other.toString();
            return(baseString.compareTo(otherString));
        }
    }
    
    public boolean equals(Base other) {
        /**Checks for equality**/
        //handle the case where this or other is null
        if(this.isEmpty() ^ other.isEmpty()) {
            return(false);
        }
        else if(this.isEmpty() & other.isEmpty()) {
            return(true);
        }
        else {
            return(this.base.equals(other.base));
        }
    }
    
    public boolean isEmpty(){
        /**Checks if the base is null**/
        return(this.base == null);
        
    }
    
    public String complement(){
        if(this.toString().equals("A"))
            return "T";
        else if(this.toString().equals("T"))
            return "A";
        else if(this.toString().equals("C"))
            return "G";
        else if(this.toString().equals("G"))
            return "C";
        else
            return "ERROR";
    }
    
    public void setBase(char c) {
    	
    	String nucleotide = String.valueOf(c);
    	if(nucleotide.equals("$")) {
            base = null;
        }
        else {
            //declare the BitSet array
            base = new BitSet(2);
            //go through each case for the nucleotide and instantiate the array
            //A = 00
            //C = 01
            //G = 10
            //T = 11
            if(nucleotide.equals("A")) {
                
            }
            else if(nucleotide.equals("C")) {
                base.set(0);
            }
            else if(nucleotide.equals("G")) {
                base.set(1);
            }
            else if(nucleotide.equals("T")) {
                base.set(0);
                base.set(1);
            }
            else {
                IllegalArgumentException e;
                e = new IllegalArgumentException("Only A,C,G,T can be entered");
                throw e;
                
            }
        }
    }
    
}