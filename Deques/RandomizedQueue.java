import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;         // number of elements on queue
    private Item[] s; // new string array
    private int head;
    private int tail;
    private int capacity;
    
    public RandomizedQueue()        
        // construct an empty randomized queue
    {
        capacity = 10;
        //StdOut.println("capacity is " + capacity);
        s = (Item[]) new Object[capacity];
        N = 0;
        tail = 0;
        head = 0;
        assert check();  
    }
    
    public boolean isEmpty()           // is the queue empty?
    {
        return N == 0;
    }
    public int size() 
        // return the number of items on the queue
    {
        return N;
    }
    
    //helper function
    private void resize()
    {        
        if (N >= capacity) {            
            Item[] copy = (Item[]) new Object[2*capacity];
            for (int i = 0; i < N; i++)
            {
                copy[i] = s[(i+head) % capacity];
            }
            head = 0;
            tail = head + N;
            capacity = capacity * 2;
            s = copy;
        }        
        else if (N < capacity/4) {
            Item[] copy = (Item[]) new Object[capacity/2];
            for (int i = 0; i < N; i++)
            {
                copy[i] = s[(i+head) % capacity];
            }
            head = 0;
            tail = head + N;
            capacity = capacity / 2;
            s = copy;
        }
    }
    
    public void enqueue(Item item)     // add the item
    {
        if (item == null)
        {
            throw new NullPointerException();
        }
        resize();
        s[tail % capacity] = item;
        tail++;
        N++;        
        assert check();
    }
    
    private boolean check() 
    {         
        return true;
    }    
    
    public Item dequeue()              // delete and return a random item
    {
        resize();
        if (N == 0)
        {
            throw new NoSuchElementException("The queue is empty");
        }         
        //StdOut.println("head is" + head +" tail is " + tail);
        int r = StdRandom.uniform(head, tail);
        Item temp = s[head % capacity];
        s[head % capacity] = s[r % capacity];
        s[r % capacity] = temp;
        Item item = s[head % capacity];
        s[head % capacity] = null;
        head++;
        N--;
        assert check();
        return item;        
    }
    
    public Item sample()
    {
        if (N == 0)
        {
            throw new NoSuchElementException("The queue is empty");
        }
        // return (but do not delete) a random item
        int r = StdRandom.uniform(head, tail);      
        Item item = s[r % capacity];
        return item;                 
    }
    
    public Iterator<Item> iterator()   
    // return an independent iterator over items in random order
    {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private int i = head;
        public boolean hasNext() {
            return i < tail && s[i % capacity] != null;
        }

        public void remove() {
            /* not supported */ 
            throw new UnsupportedOperationException("Not Supported");
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int j = i;
            i++;
            return s[j % capacity];
        }
    }
    
    //a test client
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        for (int i = 0; i < 50; i++)
        {
            String item = Integer.toString(i);            
            q.enqueue(item);
        }
        while (!q.isEmpty())
        {    
        StdOut.print(q.dequeue() + " ");
        }
        
        //Test 
        StdOut.println("test");
        RandomizedQueue<String> qa = new RandomizedQueue<String>();
        RandomizedQueue<String> qb = new RandomizedQueue<String>();
        qa.enqueue("a1");
        qb.enqueue("b1");
        Iterator<String> i = qa.iterator();
        Iterator<String> j = qb.iterator();
        while (i.hasNext())
        {
            String s1 = i.next();
            StdOut.println(s1);
        }  
         while (j.hasNext())
        {
            String s1 = j.next();
            StdOut.println(s1);
        }   
    }
}