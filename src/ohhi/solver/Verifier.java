package ohhi.solver;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A verifier that verifies if the puzzle is in format, and if is solved.
 * 
 * @author Wai Cheong Tsoi
 * @version 1.1
 */
public class Verifier
{
   /**
    * Verifies if the puzzle is solved.
    * @param puzzle the puzzle to be verified.
    * @return true if the puzzle is solved, false if not.
    */
   public static boolean verify(int[][] puzzle)
   {
      // first, it should be a square matrix with m & n % 2 == 0
      // or at least declared as a square matrix
      int n = puzzle.length;
      int rCount, bCount;
      int[] crCount, cbCount;
      crCount = new int[n];
      cbCount = new int[n];
      Queue<Tuple> rule2Q = new LinkedList<Tuple>();
      
      if (n % 2 != 0)
         return false;
      
      for (int i = 0; i < n; i++)
         if (puzzle[i].length != n)
            return false;
      
      // rule 1: rows and columns have an equal number of each color
      // reds are represented in 1, and blues are 2s. Blanks are 0s.
      for (int i = 0; i < n; i++)
      {
         // reset counters
         rCount = 0;
         bCount = 0;
         
         for (int j = 0; j < n; j++)
         {
            if (puzzle[i][j] == 1)
            {
               rCount++;
               crCount[j]++;
            }
            else if (puzzle[i][j] == 2)
            {
               bCount++;
               cbCount[j]++;
            }
            else if (puzzle[i][j] != 0)
               return false;
         }
         
         if (rCount != n / 2 || bCount != n / 2)
            return false;
      }
      
      // now for the columns
      for (int j = 0; j < n; j++)
      {
         if (crCount[j] != n / 2 || cbCount[j] != n / 2)
            return false;
      }
      
      // rule 2: no two rows and no two columns are the same
      for (int row1 = 0; row1 < n-1; row1++)
         for (int row2 = row1+1; row2 < n; row2++)
            if (puzzle[row1][0] == puzzle[row2][0])
               rule2Q.add(new Tuple(row1, row2));
      for (int j = 1; j < n; j++)
      {
         for (int k = 0; k < rule2Q.size(); k++)
         {
            Tuple tup = rule2Q.remove();
            if (puzzle[tup.a][j] == puzzle[tup.b][j])
               rule2Q.add(tup);
         }
         if (rule2Q.isEmpty())
            break;
      }
      if (!rule2Q.isEmpty())  // has 2 rows same
         return false;
      // end of the rows
      
      rule2Q.clear();  // clear queue
      
      // now for the columns
      for (int col1 = 0; col1 < n - 1; col1++)
         for (int col2 = col1 + 1; col2 < n; col2++)
            if (puzzle[0][col1] == puzzle[0][col2])
               rule2Q.add(new Tuple(col1, col2));
      for (int i = 1; i < n; i++)
      {
         for (int k = 0; k < rule2Q.size(); k++)
         {
            Tuple tup = rule2Q.remove();
            if (puzzle[i][tup.a] == puzzle[i][tup.b])
               rule2Q.add(tup);
         }
         if (rule2Q.isEmpty())
            break;
      }
      
      if (!rule2Q.isEmpty())  // has 2 column same
         return false;
      
      // rule 3: Three adjacent tiles of the same color in a row or column isn't allowed.
      for (int y = 0; y < n; y++)
         for (int x = 0; x < n - 2; x++)
            if (puzzle[y][x] == puzzle[y][x + 2])
               if (puzzle[y][x] == puzzle[y][x + 1])
                  return false;
      
      for (int x = 0; x < n; x++)
         for (int y = 0; y < n - 2; y++)
            if (puzzle[y][x] == puzzle[y + 2][x])
               if (puzzle[y][x] == puzzle[y + 1][x])
                  return false;
      
      // if it passes all the rules
      return true;
   }
   
   private static class Tuple
   {
      int a, b;
      
      Tuple(int a, int b)
      {
         this.a = a;
         this.b = b;
      }
   }
}
