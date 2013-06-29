public class WordNet{
    private Digraph H;
    private ST<String, Integer> word_st = new ST<String, Integer>();
    private ST<Integer,String> id_st = new ST<Integer, String>();
    private ST<String, Integer> defi_st = new ST<String, Integer>();
// constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        
        In in = new In(synsets);
        In in1 = new In(hypernyms);
        while(in.hasNextLine())
        {
            String s_line = in.readLine();
            String[] s_fields = s_line.split("\\,");
            int id = Integer.parseInt(s_fields[0]);
            String word = s_fields[1];
            word_st.put(word, id);
            id_st.put(id, word);
            String defi = s_fields[2];
            defi_st.put(defi, id);
        }
        
        int v_size = word_st.size();
        H = new Digraph(v_size);
        
        while ( in1.hasNextLine())
        {
            String line = in1.readLine();
            String[] fields = line.split("\\,");
            int idv = Integer.parseInt(fields[0]);
            int N = fields.length;
            for(int i = 1; i < N; i++)
            {
                int idw = Integer.parseInt(fields[i]);
                H.addEdge(idv,idw);
            }
            
            
        }
    }
        
// returns all WordNet nouns
    public Iterable<String> nouns()
        { return word_st; }
        
// is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        return word_st.contains(word);
    }
        
// distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        int id_A = word_st.get(nounA);
        int id_B = word_st.get(nounB);
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(H, id_A);
        return bfs.distTo(id_B);
    }
        
        
// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
// in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        int id_A = word_st.get(nounA);
        int id_B = word_st.get(nounB);
        SAP sap = new SAP(H);
        return id_st.get(sap.ancestor(id_A,id_B));
    }       
       
        
// for unit testing of this class
    public static void main(String[] args)
    {
    }
}