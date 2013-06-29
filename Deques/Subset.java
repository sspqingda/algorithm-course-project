public class Subset {
    public  static void main(String[] args)
    {        
        RandomizedQueue<String> ra = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) 
        {
            String item = StdIn.readString();            
            //if (item.equals("exit")) break;  
            ra.enqueue(item);
        }
        
        int k = Integer.parseInt(args[0]);
        int N = ra.size();
        //StdOut.println("k is" + k + " N is " + N);
        if (k > N)
        {
            throw new UnsupportedOperationException("Not Supported");
        }
        else { 
            for (int i = 0; i < k; i++)
            {
                StdOut.println(ra.dequeue());
            }
        }
    }
}