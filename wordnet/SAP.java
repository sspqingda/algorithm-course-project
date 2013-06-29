public class SAP{
    private static final int INFINITY = Integer.MAX_VALUE;    
    private int common_ancestor = -1;
    private int sap_ancestor = -1;
    private BreadthFirstDirectedPaths bfs;
    private Digraph DG;
// constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
     DG = new Digraph(G.reverse());  
     }
    
    
// length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        int sap = DG.V();        
        for (int c = 0; c < DG.V(); c++)
        {
            bfs = new BreadthFirstDirectedPaths(DG,c);   
            if(bfs.hasPathTo(v) && bfs.hasPathTo(w))
            {
            int new_sap = bfs.distTo(v) + bfs.distTo(w);        
            if (new_sap < sap)
            {
                sap = new_sap;
                common_ancestor = c;
            }
            }
        }
        StdOut.println("sap is " + sap);
        if (sap == DG.V()) sap = -1;
        return sap; 
    }
    
// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        int length = length(v, w);
        if(length == -1) return -1;
        return common_ancestor;
        
    }
    
// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        int sap_length = INFINITY;
        for(int i:v)
        {
            for (int j:w)
            {
                if (sap_length > length(i, j))
                {
                    sap_length = length(i, j);
                    sap_ancestor = ancestor(i, j);
                }                
            }
        }
        return sap_length;
    }
    
// a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        int length = length(v,w);
        if(length == -1) return -1;
        return sap_ancestor;
    }
    
    
// for unit testing of this class (such as the one below)
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
    
}