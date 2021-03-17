package sk.study.mea.core.sudoku;

public class Utils
{
	private Utils() {
	}

	public static int[][] cloneMatrix (int[][] matrix) {
		int [][] matrix2 = new int[matrix.length][];
		for(int i = 0; i < matrix.length; i++) {
			matrix2[i] = matrix[i].clone();
		}

		return matrix2;
	}
}
