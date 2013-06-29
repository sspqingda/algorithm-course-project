
import java.awt.Color;
public class SeamCarver {
    private Picture pic;
    private int H;
    private int W;
    private double [][] energy_matrix;
    
    public SeamCarver(Picture picture)
    {
        pic = new Picture (picture);
        H = height();
        W = width();    
        energy_matrix = new double [W][H];
        for (int i = 0; i < W; i++)
        {
            for (int j = 0; j < H; j++)
            {
                energy_matrix[i][j] = energy(i, j);
            }
        }       
    }
    
    public Picture picture() 
        // current picture
    {
        return pic;
        
    }
    public     int width()  
        // width of current picture
    {
        return  pic.width();
    }
    public     int height()  
        // height of current picture
    {
        return pic.height();
    }
    public  double energy(int x, int y)   
        // energy of pixel at column x and row y
    {
        if (x == 0 || y ==0||x == W-1||y ==H-1) 
        {
            return 195075;
        }
        else 
        {
            Color pixel_x1 = pic.get(x-1, y);
            Color pixel_x2 = pic.get(x+1, y);
            int rx = pixel_x1.getRed() - pixel_x2.getRed();
            int gx = pixel_x1.getGreen() - pixel_x2.getGreen();
            int bx = pixel_x1.getBlue()- pixel_x2.getBlue();
           
            int pixel_x = rx*rx + gx*gx + bx*bx;
            
            Color pixel_y1 =pic.get(x, y-1);
            Color pixel_y2 =pic.get(x, y+1);
            int ry = pixel_y1.getRed() - pixel_y2.getRed();
            int gy = pixel_y1.getGreen() - pixel_y2.getGreen();
            int by = pixel_y1.getBlue()- pixel_y2.getBlue();
            int pixel_y = ry*ry + gy*gy + by*by;
            
            return pixel_x + pixel_y;
            }
   }
//   private double[][] transpose(double[][] a)
//   {
//       for (int i = 0; i < H; i++)
//       { 
//           for (int j = 0; j < W; j++)
//           {
//               double temp = a[i][j];
//               a[i][j] = a[j][i];
//               a[j][i] = temp;
//           }
//       }
//       return a;
//   }
   public   int[] findHorizontalSeam()            // sequence of indices for horizontal seam
   {
       
       ST<Double, int[]> st = new ST<Double, int[]>();
       MinPQ <Double> dist = new MinPQ <Double>();
       for (int i = 1; i < H; i++)
       {
           double distTo = energy_matrix[0][i];
           int[] edgeTo = new int[W];
           edgeTo[0] = i;
           for (int j = 0; j < W; j++)
           {
               double min_energy = energy_matrix[i-1][j+1];
               int min_index = i-1;
               
               if (min_energy > energy_matrix[i][j+1])
               {
                   min_energy = energy_matrix[i][j+1];
                   min_index = i;
               }
               if (min_energy > energy_matrix[i+1][j+1])
               {
                   min_energy = energy_matrix[i+1][j+1];
                   min_index = i+1;
               }
               distTo += min_energy;
               edgeTo[j+1] = min_index;                
           }
          
           st.put(distTo, edgeTo);
           dist.insert(distTo);
       }
       
       return st.get(dist.min());
       
       
   }
   
   public   int[] findVerticalSeam()              // sequence of indices for vertical seam
   {
       
       ST<Double, int[]> st = new ST<Double, int[]>();
       MinPQ <Double> dist = new MinPQ <Double>();
       for (int i = 1; i < W; i++)
       {
           double distTo = energy_matrix[i][0];
           int[] edgeTo = new int[H];
           edgeTo[0] = i;
           for (int j = 0; j < H; j++)
           {
               double min_energy = energy_matrix[i-1][j+1];
               int min_index = i-1;
               
               if (min_energy > energy_matrix[i][j+1])
               {
                   min_energy = energy_matrix[i][j+1];
                   min_index = i;
               }
               if (min_energy > energy_matrix[i+1][j+1])
               {
                   min_energy = energy_matrix[i+1][j+1];
                   min_index = i+1;
               }
               distTo += min_energy;
               edgeTo[j+1] = min_index;                
           }
          
           st.put(distTo, edgeTo);
           dist.insert(distTo);
       }
       
       return st.get(dist.min());
       
       
   }
   public    void removeHorizontalSeam(int[] a)   // remove horizontal seam from picture
   {
       {
           Picture target = new Picture(W-1, H-1);
           
           for (int tx = 0; tx < W-1; tx++) {
               for (int ty = 0; ty < H-1; ty++) {
                   int sy = ty;
                   if ( ty < a[tx])
                   {
                        sy = ty;
                   }
                   if (ty > a[tx])
                   {
                        sy = ty+1;
                   }
                   
                   Color color = pic.get(tx, sy);
                   target.set(tx, ty, color);
               }
           }
       }
   }
   
   public    void removeVerticalSeam(int[] a)
       // remove vertical seam from picture
   {
       Picture target = new Picture(W-1, H-1);
       
       for (int tx = 0; tx < W-1; tx++) {
           for (int ty = 0; ty < H-1; ty++) {
               int sx = tx;
               if (tx < a[ty])
               {
                   sx = tx;
               }
               if (tx > a[ty])
               {
                   sx = tx+1;
               }
             
               Color color = pic.get(sx, ty);
               target.set(tx, ty, color);
           }
       }
   }
  }