public class Outcast
{
    private WordNet _wordnet;
// constructor takes a WordNet object
public Outcast(WordNet wordnet)
{
    _wordnet = wordnet;
}

// given an array of WordNet nouns, return an outcast
public String outcast(String[] nouns)
{
    int size = nouns.length;
    int max_dist = 0;
    String least_noun = nouns[0];
    for (int i = 0; i < size; i++)
    {
        int dist = 0;
        for (int j = 0; j <size; j++)
        {
            dist += _wordnet.distance(nouns[i], nouns[j]);
        }
        if (dist > max_dist)
        {
            least_noun = nouns[i];
        }
        
    }
    return least_noun;
}

// for unit testing of this class (such as the one below)
    public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
        String[] nouns = In.readStrings(args[t]);
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
}
}