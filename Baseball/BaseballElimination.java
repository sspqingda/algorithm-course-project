public class BaseballElimination {
    private int V;
    private ST<String,Integer> teamID;
    private ST<Integer,String> teamName;
    private int [][] teamInfo;
    private Bag<Integer> [] subset; 
    private Boolean [] elimin;
    private Boolean debug = false;
    
    public BaseballElimination(String filename)   
    {
        In in = new In(filename);
        V = in.readInt();
        teamID = new ST<String, Integer>();   
        teamName = new ST<Integer, String>();  
        teamInfo = new int[V][V+3];
        subset = (Bag<Integer>[]) new Bag[V];
        elimin = new Boolean[V];
        for(int i = 0; i<V; i++)
        {
            subset[i] = new Bag<Integer>();
        }
        for( int i = 0; i< V; i++)
        {
            String name = in.readString();
            teamID.put(name,i);
            teamName.put(i,name);
            for(int j=0; j<V+3; j++)
            {
                teamInfo[i][j] = in.readInt();
            }
        }
         
        int offset = 1 + V*(V-1)/2;
        for(int x= 0; x < V; x++)
        {
            if(trivalElimination(x)) 
            {
                //StdOut.println("team " + teamName.get(x) + "is trivally eliminated");                
                elimin[x] = true;
                continue;
            }
            FlowNetwork G = new FlowNetwork(offset + V + 1);
            int s = 0;
            int t = offset + V;
            int point = 1;
            int remainGameNumber = 0;
            for(int i =0; i<V; i++)
            {
                for(int j= i+1; j<V; j++)
                {
                    if(i==x || j == x) 
                    {
                        point++;
                        continue;
                    }
                    if(teamInfo[i][j+3] > 0)
                    {
                        FlowEdge edge = new FlowEdge(s,point,teamInfo[i][j+3]);
                        remainGameNumber += teamInfo[i][j+3];
                        G.addEdge(edge);   
                        FlowEdge edge1 = new FlowEdge(point,offset + i, Double.POSITIVE_INFINITY);
                        FlowEdge edge2 = new FlowEdge(point,offset + j, Double.POSITIVE_INFINITY);
                        G.addEdge(edge1);
                        G.addEdge(edge2);
                    }
                    point++;
                }    
            }
            for(int i=0; i<V; i++)
            {
                if (i == x) continue;
                int capacity = teamInfo[x][0] + teamInfo[x][2] - teamInfo[i][0];
                FlowEdge edge = new FlowEdge(offset + i, t,capacity);
                G.addEdge(edge);                                      
            }
            FordFulkerson maxflow = new FordFulkerson(G, s, t);
            if(maxflow.value() == remainGameNumber)
            {
                elimin[x] = false;
            }
            else
            {
                elimin[x] = true;
                if(debug == true)
                {
                    StdOut.println("FlowNetWork" + G.toString());
                }
                for (int i = 0; i < V; i++) 
                {
                    if(i == x) continue;
                    if (maxflow.inCut(offset + i)) 
                    {
                        subset[x].add(i);
                    }
                }
            }
        }
    }
    
    private Boolean trivalElimination(int i)
    {
        int baseline = teamInfo[i][0] + teamInfo[i][2];
        for(int j=0; j<V; j++)
        {
            if(j == i) continue;
            if (baseline < teamInfo[j][0]) 
            {
                subset[i].add(j);
                return true;          
            }
        }
        return false;
    }
    
    public int numberOfTeams()
        // number of teams
    {
        return V;
    }
    
    public Iterable<String> teams()                                // all teams
    {
        return teamID.keys();
    }
    public int wins(String team)            
    {
        int index = 0;
        if( teamID.contains(team) )
        {
            int id = teamID.get(team);
            return teamInfo[id][index];
        }
        else
        {
            throw new java.lang.IllegalArgumentException("Wrong team name input:" + team);
        }
    }
    
    public int losses(String team)                    // number of losses for given team
    {
        int index = 1;
        if( teamID.contains(team) )
        {
            int id = teamID.get(team);
            return teamInfo[id][index];
        }
        else
        {
            throw new java.lang.IllegalArgumentException("Wrong team name input:" + team);
        }
    }
    
    public int remaining(String team)       
    {
        int index = 2;
        if( teamID.contains(team) )
        {
            int id = teamID.get(team);
            return teamInfo[id][index];
        }
        else
        {
            throw new java.lang.IllegalArgumentException("Wrong team name input:" + team);
        }
    }

    public int against(String team1, String team2)
    {
        if(!teamID.contains(team1) || !teamID.contains(team2) )
               throw new java.lang.IllegalArgumentException("Wrong team name input in against function");
        int id1 = teamID.get(team1);
        int id2 = teamID.get(team2);
        return teamInfo[id1][id2+3];        
    }
    
    public boolean isEliminated(String team)
    {
        int id = teamID.get(team);
        return elimin[id];
        
    }
    
    public Iterable<String> certificateOfElimination(String team) 
    // subset R of teams that eliminates given team; null if not eliminated
    {
       int id = teamID.get(team);
       Bag<String> temp = new Bag<String>();
       for (int i:subset[id])
       {
           String name = teamName.get(i);
           if(name != null) temp.add(name);
       }
       return temp;
    }
    
    public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team))
                StdOut.print(t + " ");
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }
    }
}
}