package ohhi.solver;

/**
 * The solver class. 
 * Client supplies the puzzle in int[][] and calls the solve() method.
 * 
 * @author Wai Cheong Tsoi
 * @version 1.0
 */
public class Solver
{
   /**
    * A static copy of the puzzle for easier change-making.
    */
   static int[][] mPuzzle;
   
   /**
    * The matrix size of the puzzle.
    */
   static int n;
   
   /**
    * For indicating if this iteration has any changes, and thus to terminate the loop or not.
    */
   static boolean mHasChange;
   
   /**
    * The public method for solving the puzzle.
    * @param puzzle the puzzle to be solved.
    * @return a copy of the solved puzzle.
    */
   public static int[][] solve(int[][] puzzle)
   {
      n = puzzle.length;
      mPuzzle = puzzle;
      
      do
      {
         mHasChange = false;
         
         while (scanIndividual());
         while (scanRowsAndColumns());
         
         if (!mHasChange)
            while (compareRowsAndColumns());
      }
      while(mHasChange);
      
      // if final check passes
      return puzzle;
   }
   
   /**
    * A private method for scanning any individual spots for changes.
    * @return true if anything is changed.
    */
   private static boolean scanIndividual()
   {
      boolean hasChange = false;
      
      for (int x = 0; x < n; x++)
         for (int y = 0; y < n; y++)
            if (mPuzzle[y][x] == 0)
               if (scanIndividual(x, y))
                  hasChange = true;
      
      if (hasChange)
         mHasChange = true;
      
      return hasChange;
   }
   
   /**
    * A private method for scanning the rows and columns for changes.
    * @return true if anything is changed.
    */
   private static boolean scanRowsAndColumns()
   {
      boolean hasChange = false;
      
      for (int y = 0; y < n; y++)
         if (scanRows(y))
            hasChange = true;
      
      for (int x = 0; x < n; x++)
         if (scanColumns(x))
            hasChange = true;
      
      if (hasChange)
         mHasChange = true;
      
      return hasChange;
   }
   
   /**
    * A private method for comparing the rows and columns for changes.
    * @return true if anything is changed.
    */
   private static boolean compareRowsAndColumns()
   {
      boolean hasChange = false;
      
      // compare the rows
      for (int y = 0; y < n; y++)
         if (compareRows(y))
            hasChange = true;
      
      // compare the columns
      for (int x = 0; x < n; x++)
         if (compareColumns(x))
            hasChange = true;
      
      if (hasChange)
         mHasChange = true;
      
      return hasChange;
   }
   
   /**
    * Checks the individual spot for applying rule "no 3 same color stick together".
    * @param x the x-coordinate of the current spot.
    * @param y the y-coordinate of the current spot.
    * @return true if that spot is changed.
    */
   private static boolean scanIndividual(int x, int y)
   {
      if (mPuzzle[y][x] != 0)
         return false;
      
      // Rule 1: No 3 same color stick together
      if (x != 0 && x != n - 1)
         if (mPuzzle[y][x-1] == mPuzzle[y][x+1])
            if (mPuzzle[y][x-1] == 1)
            {
               mPuzzle[y][x] = 2;
               return true;
            }
            else if (mPuzzle[y][x-1] == 2)
            {
               mPuzzle[y][x] = 1;
               return true;
            }
      
      if (y != 0 && y != n - 1)
         if (mPuzzle[y-1][x] == mPuzzle[y+1][x])
            if (mPuzzle[y-1][x] == 1)
            {
               mPuzzle[y][x] = 2;
               return true;
            }
            else if (mPuzzle[y-1][x] == 2)
            {
               mPuzzle[y][x] = 1;
               return true;
            }
      
      if (x > 1)
         if (mPuzzle[y][x-1] == mPuzzle[y][x-2])
            if (mPuzzle[y][x-1] == 1)
            {
               mPuzzle[y][x] = 2;
               return true;
            }
            else if (mPuzzle[y][x-1] == 2)
            {
               mPuzzle[y][x] = 1;
               return true;
            }
      
      if (x < n - 2)
         if (mPuzzle[y][x+1] == mPuzzle[y][x+2])
            if (mPuzzle[y][x+1] == 1)
            {
               mPuzzle[y][x] = 2;
               return true;
            }
            else if (mPuzzle[y][x+1] == 2)
            {
               mPuzzle[y][x] = 1;
               return true;
            }
      
      if (y > 1)
         if (mPuzzle[y-1][x] == mPuzzle[y-2][x])
            if (mPuzzle[y-1][x] == 1)
            {
               mPuzzle[y][x] = 2;
               return true;
            }
            else if (mPuzzle[y-1][x] == 2)
            {
               mPuzzle[y][x] = 1;
               return true;
            }
      
      if (y < n - 2)
         if (mPuzzle[y+1][x] == mPuzzle[y+2][x])
            if (mPuzzle[y+1][x] == 1)
            {
               mPuzzle[y][x] = 2;
               return true;
            }
            else if (mPuzzle[y+1][x] == 2)
            {
               mPuzzle[y][x] = 1;
               return true;
            }
      
      
      return false; // if all those rules doesn't apply, just return false
   }
   
   /**
    * Scans the current row for applying the rule of "no more than n/2 same color on each row"
    * @param y the y-coordinate of the row.
    * @return true if the row is changed.
    */
   private static boolean scanRows(int y)
   {
      int bCount = 0;
      int rCount = 0;
      
      // each row has equal number of blues and reds
      for (int x = 0; x < n; x++)
         if (mPuzzle[y][x] == 1)
            rCount++;
         else if (mPuzzle[y][x] == 2)
            bCount++;
      
      if (bCount + rCount == n)
         return false;
      
      if (bCount == n / 2)
      {
         for (int x = 0; x < n; x++)
            if (mPuzzle[y][x] == 0)
               mPuzzle[y][x] = 1; // make it red
         return true;
      }
      
      if (rCount == n / 2)
      {
         for (int x = 0; x < n; x++)
            if (mPuzzle[y][x] == 0)
               mPuzzle[y][x] = 2; // make it blue
         return true;
      }
      return false;
   }
   
   /**
    * Scans the current row for applying the rule of "no more than n/2 same color on each column"
    * @param x the x-coordinate of the column.
    * @return true if the column is changed.
    */
   private static boolean scanColumns(int x)
   {
      int bCount = 0;
      int rCount = 0;
      
      // each row has equal number of blues and reds
      for (int y = 0; y < n; y++)
         if (mPuzzle[y][x] == 1)
            rCount++;
         else if (mPuzzle[y][x] == 2)
            bCount++;
      
      if (bCount + rCount == n)
         return false;
      
      if (bCount == n / 2)
      {
         for (int y = 0; y < n; y++)
            if (mPuzzle[y][x] == 0)
               mPuzzle[y][x] = 1; // make it red
         return true;
      }
      
      if (rCount == n / 2)
      {
         for (int y = 0; y < n; y++)
            if (mPuzzle[y][x] == 0)
               mPuzzle[y][x] = 2; // make it blue
         return true;
      }
      return false;
   }
   
   /**
    * Compares the row and apply the rule "no two rows are the same".
    * @param y the y-coordinate of the rows to be compared with.
    * @return true if this row is changed.
    */
   private static boolean compareRows(int y)
   {
      int index1, index2;
      int rCount = 0;
      int bCount = 0;
      boolean errFlag;
      
      // initial check
      for (int x = 0; x < n; x++)
         if (mPuzzle[y][x] == 1)
            rCount++;
         else if (mPuzzle[y][x] == 2)
            bCount++;
      
      // do not compare if any statement is true
      if (bCount != rCount || bCount != n / 2 - 1 || rCount != n / 2 - 1)
         return false;
      
      // for y2 is the row being compared
      for (int y2 = 0; y2 < n; y2++)
      {
         // reset storing index
         index1 = -1;
         index2 = -1;
         errFlag = false;
         
         if (y == y2)
            y2++;
         if (y2 >= n)
            return false;
         
         // for x is the square being compared with y2's x
         for (int x = 0; x < n; x++)
         {
            if (mPuzzle[y][x] != 0)
            {
               if (mPuzzle[y][x] != mPuzzle[y2][x])
               {
                  errFlag = true;
                  break;
               }
            }
            else if (mPuzzle[y][x] == 0 && mPuzzle[y2][x] != 0)
               if (index1 == -1)
                  index1 = x;
               else if (index2 == -1)
                  index2 = x;
               else
               {
                  errFlag = true; // more than 2 empty slots, cannot compare
                  break;
               }
         }
         
         if (!errFlag)
            if (index1 != -1 && index2 != -1)
            {
               // found a match, make it and return
               mPuzzle[y][index1] = mPuzzle[y2][index2];
               mPuzzle[y][index2] = mPuzzle[y2][index1];
               return true;
            }
      }
      
      // no matches found
      return false;
   }
   
   /**
    * Compares the column and apply the rule "no two columns are the same".
    * @param x the x-coordinate of the columns to be compared with.
    * @return true if this column is changed.
    */
   private static boolean compareColumns(int x)
   {
      int index1, index2;
      int rCount = 0;
      int bCount = 0;
      boolean errFlag;
      
      // initial check
      for (int y = 0; y < n; y++)
         if (mPuzzle[y][x] == 1)
            rCount++;
         else if (mPuzzle[y][x] == 2)
            bCount++;
      
      // do not compare if any statement is true
      if (bCount != rCount || bCount != n / 2 - 1 || rCount != n / 2 - 1)
         return false;
      
      for (int x2 = 0; x2 < n; x2++)
      {
         // reset storing index
         index1 = -1;
         index2 = -1;
         errFlag = false;
         
         if (x == x2)
            x2++;
         if (x >= n)
            return false;
         
         for (int y = 0; y < n; y++)
         {
            if (mPuzzle[y][x] != 0)
            {
               if (mPuzzle[y][x] != mPuzzle[y][x2])
               {
                  errFlag = true;
                  break;
               }
            }
            else if (mPuzzle[y][x] == 0 && mPuzzle[y][x2] != 0)
               if (index1 == -1)
                  index1 = y;
               else if (index2 == -1)
                  index2 = y;
               else
               {
                  errFlag = true; // more than 2 empty slots, cannot compare
                  break;
               }
         }
         
         if (!errFlag)
            if (index1 != -1 && index2 != -1)
            {
               // found a match, make it and return
               mPuzzle[index1][x] = mPuzzle[index2][x2];
               mPuzzle[index2][x] = mPuzzle[index1][x2];
               return true;
            }
      }
      
      // no matches found
      return false;
   }
}
