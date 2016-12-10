package ohhi.solver;

/**
 * The tester class for testing the verifier and solver.
 * 
 * @author Wai Cheong Tsoi
 * @version 1.0
 */
public class TestClass
{
   /**
    * The main.
    * @param args not used.
    */
   public static void main(String[] args)
   {
     /* int[][] puzzle = { {2,2,1,1},
                         {1,2,1,2},
                         {2,1,2,1},
                         {1,1,2,2} };*/
      
      // case 1: a 4x4 normal matrix
      
    /*  int[][] puzzle = { {2,2,1,1,2,1},
                         {2,1,1,2,2,1},
                         {2,1,1,2,2,1},
                         {1,2,2,1,1,2},
                         {1,1,2,2,1,2},
                         {1,2,2,1,1,2} };*/
      // case 2: rows and columns same
      
      int[][] puzzle = { {0,0,0,0,2,0,0,0},
                         {0,2,0,0,0,0,0,0},
                         {1,0,0,0,0,0,0,2},
                         {0,0,1,0,2,0,0,0},
                         {0,2,0,1,0,1,0,2},
                         {0,0,0,0,0,0,0,0},
                         {0,0,1,1,0,0,0,0},
                         {0,2,0,0,0,0,0,0} };
      
      // case 3: a 8x8 normal solvable matrix
      
   /*   int[][] puzzle = { {0,0,0,0,0,1,0,0,0,0,0,0},
                         {1,0,0,0,2,0,0,0,0,0,0,2},
                         {0,0,2,0,0,0,2,0,0,1,0,2},
                         {0,0,2,2,0,0,2,0,0,0,0,0},
                         {1,0,0,0,0,1,1,2,1,2,0,1},
                         {0,0,0,0,0,1,2,0,1,0,0,0},
                         {0,2,1,0,0,0,0,2,2,0,0,1},
                         {2,1,0,2,0,0,2,2,0,2,2,1},
                         {0,2,0,0,0,2,0,0,0,0,1,0},
                         {0,0,0,0,0,0,2,2,0,2,0,0},
                         {1,0,0,1,0,0,0,0,0,0,0,1},
                         {2,0,0,0,0,0,0,1,1,0,0,0} }; */
      
      // case 4: a 12x12 normal solvable matrix w/o comparing rows and columns
      
      printPuzzle(puzzle);
      
      puzzle = Solver.solve(puzzle);
      printPuzzle(puzzle);
      
      System.out.println("Is solved: " + Verifier.verify(puzzle));
   }
   
   /**
    * A private helper method that prints out the puzzle.
    * @param puzzle the puzzle to be printed.
    */
   private static void printPuzzle(int[][] puzzle)
   {
      if (puzzle == null)
         return;
      
      for (int y = 0; y < puzzle.length; y++)
      {
         String str = "";
         for (int x = 0; x < puzzle[y].length; x++)
            str += puzzle[y][x] + " ";
         System.out.println(str);
      }
      System.out.println("   end.");
   }
}
