public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode()
    {
         String s = BinaryStdIn.readString();
         char[] input = s.toCharArray();
         int N = s.length();
         CircularSuffixArray suffix = new CircularSuffixArray(s);
         int first = 0;
         String result = "";
         for (int i = 0; i < N; i++)
         {
             int t = suffix.index(i);
             if(t == 0)
             {
                 first = i;
                 result = result + input[N-1];
             }
             else
             {
                 result = result + input[t-1];
             }
         }
         BinaryStdOut.write(first);
         BinaryStdOut.write(result);

    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        int N = s.length();
        BinaryStdOut.write(s);
        
        
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args)
    {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new RuntimeException("Illegal command line argument");
    }
}