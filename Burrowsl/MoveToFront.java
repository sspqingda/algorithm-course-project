public class MoveToFront {    
    // alphabet size of extended ASCII
    private static final int R = 256;
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        
        int[] freq = new int[R];
        for (int i =0; i< R; i++)
        {
            freq[i] = i;
        }
        
        for (int i = 0; i < input.length; i++)
        {
            int ascii = (int) input[i];
            int index;
            for(index =0;;index++)
            {
                if(ascii == freq[index]) break;
            }
            
            for(int j = index; j>0; j--)
            {
                freq[j] = freq[j-1];
            }
            freq[0] = ascii;
            //StdOut.printf("%02x", index & 0xff);
            //StdOut.print(" ");
            BinaryStdOut.write((char) index);
        }       
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        
        int[] freq = new int[R];
        for (int i =0; i< R; i++)
        {
            freq[i] = i;
        }
        
        for (int i = 0; i < input.length; i++)
        {
            int index = (int) input[i];  
            int ascii = freq[index];
            for(int j = index; j>0; j--)
            {
                freq[j] = freq[j-1];
            }
            freq[0] = ascii;
            BinaryStdOut.write((char) ascii);
        }       
    }


    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args)
    {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new RuntimeException("Illegal command line argument");

    }
}