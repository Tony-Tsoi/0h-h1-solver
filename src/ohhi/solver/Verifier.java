package ohhi.solver;

/**
 * A verifier that verifies if the puzzle is in format, and if is solved.
 * 
 * @author Wai Cheong Tsoi
 * @version 1.0
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
               rCount++;
            else if (puzzle[i][j] == 2)
               bCount++;
            else if (puzzle[i][j] != 0)
               return false;
         }
         
         if (rCount != n / 2 || bCount != n / 2)
            return false;
      }
      
      // now for the columns
      
      for (int i = 0; i < n; i++)
      {
         rCount = 0;
         bCount = 0;
         
         for (int j = 0; j < n; j++)
         {
            if (puzzle[j][i] == 1)
               rCount++;
            else if (puzzle[j][i] == 2)
               bCount++;
            else if (puzzle[j][i] != 0)
               return false;
         }
         
         if (rCount != n / 2 || bCount != n / 2)
            return false;
      }
      // Next step: optimize it by only traversing it once (instead of twice) for 2 results
      
      // rule 2: no two rows and no two columns are the same
      for (int row1 = 0; row1 < n - 1; row1++)
         for (int row2 = row1 + 1; row2 < n; row2++)
         {
            boolean twoRowsExactlySame = false;
            for (int x = 0; x < n; x++)
            {
               if (puzzle[row1][x] != puzzle[row2][x])
               {
                  twoRowsExactlySame = false;
                  break;
               }
               twoRowsExactlySame = true;
            }
            if (twoRowsExactlySame)
               return false;
         }
      // end of the nested for-loops
      
      // now for the columns
      for (int col1 = 0; col1 < n - 1; col1++)
         for (int col2 = col1 + 1; col2 < n; col2++)
         {
            boolean twoRowsExactlySame = false;
            for (int y = 0; y < n; y++)
            {
               if (puzzle[y][col1] != puzzle[y][col2])
               {
                  twoRowsExactlySame = false;
                  break;
               }
               twoRowsExactlySame = true;
            }
            if (twoRowsExactlySame)
               return false;
         }
      
      // rule 3: Three adjacent tiles of the same color in a row or column isn't allowed.
      for (int y = 0; y < n; y++)
         for (int x = 0; x < n - 2; x++)
            if (puzzle[y][x] == puzzle[y][x + 1] && puzzle[y][x + 1] == puzzle[y][x + 2])
               return false;
      
      for (int x = 0; x < n; x++)
         for (int y = 0; y < n - 2; y++)
            if (puzzle[y][x] == puzzle[y + 1][x] && puzzle[y + 1][x] == puzzle[y + 2][x])
               return false;
      
      // if it passes all the rules
      return true;
   }
}
