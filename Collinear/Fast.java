import java.util.Arrays;
public class Fast {
    
    public static void main(String[] args)
    {
        In file = new In(args[0]);        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768); 
        StdDraw.setPenRadius(0.02);
        Queue<Point> input = new Queue<Point>();
        int size = file.readInt();
        while (!file.isEmpty()) {
            int x = file.readInt();
            int y = file.readInt();
            //StdOut.println("x is" + x + ". y is " + y);
            Point pt = new Point(x, y);
            input.enqueue(pt);          
        }
        Point[] array = new Point[size];
        int i = 0;
        for (Point s:input)
        {
            array[i] = s;
            i++;
        }       
        for (int p = 0; p < size -1; p++)
        {
            Arrays.sort(array, p, size, array[p].SLOPE_ORDER);
            double slope1 = array[p].slopeTo(array[p+1]);
            String coord = array[p].toString() + "->" + array[p+1].toString();
            Queue<Point> queueline = new Queue<Point>();
            queueline.enqueue(array[p]);
            queueline.enqueue(array[p+1]);
            int num = 2;
            String tag = "";
            
            for (int q = 0; q < p; q++)
            {
                double slope = array[p].slopeTo(array[q]);
                if (slope == slope1)
                {
                    tag = "dumplicate";
                    break;
                }
            }
            if (tag.equals("dumplicate")) continue;

            
            for (int q = p+2; q < size; q++)
            {
                double slope = array[p].slopeTo(array[q]);               
                if (slope == slope1)
                {
                    //StdOut.println("next is " + array[q].toString());
                    coord = coord + "->" + array[q].toString();
                    queueline.enqueue(array[q]);
                    num++;
                }
                else
                {
                    if (num > 3)
                    {
                        for (int qa = 0; qa < p; qa++)
                        {
                            double slopea = array[p].slopeTo(array[qa]);
                            if (slopea == slope1)
                            {
                                tag = "duplicate";
                                break;
                            }
                        }
                        if (!tag.equals("duplicate"))  draw(queueline);
                    }
                    slope1 = array[p].slopeTo(array[q]);
                    coord = array[p].toString() + "->" + array[q].toString();
                    while (!queueline.isEmpty())
                    {
                        queueline.dequeue();
                    }
                    queueline.enqueue(array[p]);
                    queueline.enqueue(array[q]);
                    num = 2;
                    tag = "";                   
                }                
            }
            if (num > 3)
            {
                for (int qa = 0; qa < p; qa++)
                {
                    double slopea = array[p].slopeTo(array[qa]);
                    if (slopea == slope1)
                    {
                        tag = "duplicate";
                        break;
                    }
                }
                if (!tag.equals("duplicate"))  draw(queueline);
            }              
        }
    }
    
    private static void draw(Queue<Point> pts)
    {
        int size = pts.size();
        Point[] array = new Point[size];
        int ii = 0;
        for (Point s:pts)
        {
            array[ii] = s;
            ii++;
        }
        Arrays.sort(array);
        String coord = "";  
        for (int i = 0; i < size-1; i++)
        {
            array[i].draw();
            //array[i].drawTo(array[i+1]);
            coord = coord + array[i].toString() + " -> ";
        }
        coord = coord  + array[size-1].toString();
        array[size-1].draw();
        array[0].drawTo(array[size-1]);
        StdOut.println(coord);
    }
}