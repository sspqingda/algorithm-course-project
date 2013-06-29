import java.util.Arrays;
import java.util.Hashtable;

public class CircularSuffixArray {
    private final String[] suffixes;
    private final int N;
    Hashtable<String, Integer> numbers;;
    
    public CircularSuffixArray(String s)  // circular suffix array of s
    {   
        N = s.length();
        suffixes = new String[N];
        numbers  = new Hashtable<String, Integer>();
        suffixes[0] = s;
        numbers.put(s,0);
        for (int i = 1; i < N; i++)
        {
            suffixes[i] = s.substring(1)+s.substring(0,1);
            s = suffixes[i];
            numbers.put(suffixes[i],i);
        }
        Arrays.sort(suffixes);    
    }
    public int length()                   // length of s
    {
        return N;
    }
    public int index(int i)               // returns orginal array index of ith sorted suffix
    {
        String s = suffixes[i];
        //StdOut.println(s);
        return numbers.get(s);
    }
    
        public static void main(String[] args) {
        //String s = StdIn.readAll().trim();
        String s = "ABRACADABRA!";
        CircularSuffixArray suffix = new CircularSuffixArray(s);

        StdOut.println("  i ind ");
        StdOut.println("---------------------------");
        StdOut.printf("%3d %3d \n",
                0, suffix.index(0));
        for (int i = 1; i < s.length(); i++) {
            int index = suffix.index(i);
            StdOut.printf("%3d %3d\n", i, index);
        }
    }

}