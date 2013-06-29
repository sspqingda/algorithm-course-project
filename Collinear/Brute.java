import java.util.Arrays;
public class Brute {
    
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
        Arrays.sort(array);
        for (int p = 0; p < size-3; p++)
        {
            for (int q = p+1; q < size -2; q++)
            {
                for (int r = q+1; r < size -1; r++)
                {
                    for (int s = r+1; s < size; s++)
                    {
                        double spq = array[p].slopeTo(array[q]);
                        double spr = array[p].slopeTo(array[r]);
                        double sps = array[p].slopeTo(array[s]);
                        if (spq == spr && spq == sps)
                        {
                            Queue<Point> queueline = new Queue<Point>();
                            queueline.enqueue(array[p]);
                            queueline.enqueue(array[q]);
                            queueline.enqueue(array[r]);
                            queueline.enqueue(array[s]);
                            draw(queueline);
                        }
                    }
                    
                }
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