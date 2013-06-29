import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    public Deque()   
        // construct an empty deque
    {
        first = null;
        last  = null;
        N = 0;
        assert check();      
        
    }
    
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (N == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
        }
        else {
            if (first == last)      return false;
            if (first.next == null) return false;
            if (last.next  != null) return false;
            
            // check internal consistency of instance variable N
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != N) return false;
            
            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }
        
        
        return true;
    } 
    
    public boolean isEmpty()   
        // is the deque empty?
    {
        return N == 0;
    }
    public int size()                  // return the number of items on the deque
    {
        return N; 
    }
    
    public void addFirst(Item item)    // insert the item at the front
    {
        if (item == null) 
        {
            throw new NullPointerException("item is null"); 
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (N == 0) 
        {
            last = first;
        }
        else {
            first.next = oldfirst;
            oldfirst.prev = first;            
        }
        N++;
        assert check();
    }
    
    public void addLast(Item item)     // insert the item at the end
    {
        if (item == null) 
        {
            throw new NullPointerException("item is null"); 
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (N == 0) first = last;
        else { 
            oldlast.next = last;
            last.prev = oldlast;
        }
        N++;
        assert check();
    }
    public Item removeFirst()          // delete and return the item at the front
    {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        N--;
        if (N == 0)
        {
            last = null;
        }
        assert check();
        return item;                   // return the saved item
    }
    public Item removeLast()           // delete and return the item at the end
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.prev;
        //last.next = null;
        N--;
        if (N == 0) {
            first = null;
            last = null;   // to avoid loitering
        }
        //assert check();
        return item;
    }
    
    public Iterator<Item> iterator()  
        // return an iterator over items in order from front to end
    {
        return new ListIterator();  
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;        
        public boolean hasNext()  { return current != null;   }
        public void remove() {
            throw new UnsupportedOperationException(); 
        }        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        for (int i = 0; i < 10; i++)
        {
            String item = Integer.toString(i);
            q.addFirst(item);
        }
        while (!q.isEmpty())
        {    
        StdOut.print(q.removeFirst() + " ");
        //StdOut.println("(" + q.size() + " left on deque)");
        }      
        
        for (int i = 0; i < 10; i++)
        {
            String item = Integer.toString(i);
            q.addLast(item);
        }
        while (!q.isEmpty())
        {    
        StdOut.print(q.removeLast() + " ");
        }
        
        for (int i = 0; i < 10; i++)
        {
            String item = Integer.toString(i);
            q.addFirst(item);
        }
        while (!q.isEmpty())
        {    
        StdOut.print(q.removeLast() + " ");
        }
        
        for (int i = 0; i < 10; i++)
        {
            String item = Integer.toString(i);
            q.addLast(item);
        }
        while (!q.isEmpty())
        {    
        StdOut.print(q.removeFirst() + " ");
        }
        
        //Test 10
        StdOut.println("\nTest 10");
        q.addFirst("1");
        q.addLast("2"); 
        q.removeFirst();
        q.addFirst("5");
        q.removeLast();
        q.addLast("6"); 
        q.addLast("7");
        q.addFirst("8");
        
        Iterator<String> i = q.iterator();
        while (i.hasNext())
        {
            String s = i.next();
            StdOut.println(s);
        }
        q.removeFirst();
        q.removeLast();
    }
}
