package sk.study.mea.core.utils;

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

	public static void matrixcopy (int[][] src, int[][] dest) {
		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
		}
	}

	public static void arraycopy (int[] src, int[] dest) {
		System.arraycopy(src, 0, dest, 0, src.length);
	}
}
