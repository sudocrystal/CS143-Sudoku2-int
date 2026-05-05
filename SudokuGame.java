public class SudokuGame
{
	public static void main(String[] args)
	{
		SudokuBoard gameBoard = new SudokuBoard("boards/data1.sdk");
		System.out.println(gameBoard);
      
      System.out.println("Is the current layout valid? " + gameBoard.isValid());
      System.out.println("Is the puzzle fully solved? " + gameBoard.isSolved());
	}
}
