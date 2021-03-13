package sk.study.mea.core.sudoku;

import static sk.study.mea.core.sudoku.SudokuConstants.*;
/**
 * Sudoku rows empty positions definitions.
 *
 *
 *
 */
// TODO originaly SudokuFixedState
public class SudokuTabuList
{
	private final int[] tabuList; // TODO delete
	private final boolean[][][] tabu;

//	// Count of emtpty positions for each row
//	private final int[] rowsEmptyPositionsCounts;
//
//	// Indexes of empty positions for each rows.
//	private final int[] rowsEmptyPositions;

	public SudokuTabuList (SudokuState sourceState)
	{
		tabuList = new int[N4 * N2];

		tabu = new boolean[N2][N2][N2];

//		rowsEmptyPositionsCounts = new int[SudokuConstants.N2];
//		rowsEmptyPositions = new int[SudokuConstants.N4];

		initOld (sourceState);
		init (sourceState);
	}

	// zapise sa pre kazdy riadkok, pocet prazdnych cisel a ich indexu, kde sa nachadzaju = zapisuju sa indexy cisel kde su nuly

	@Deprecated
	private void initOld (SudokuState sourceState) {

		// =====================================================
		// initialization of TabuList
		// it NNxNNxNN 3D array where is check for each cell, if every number can be there

		// make tabu cube2
		//memset( tabuList, 0,  (N4)*N2 * sizeof(int));
		//Arrays.fill(tabuList, 0);

		int offset = 0;
		for (int i = 0; i < N2; i++) {
			for (int j = 0; j < N2; j++) {
				int oneFixed = sourceState.getValue(i*N2 + j) - 1;
				if (0 <= oneFixed) {
					// TABUing in column
					for (int k = 0; k < N2; k++) {
						tabuList[(k*N2 + j)*N2 + oneFixed] = TABU;
					}
					// TABUing in block
					offset = (i/N) * N2 * N  + (j/3) * N;
					for (int k = 0; k < N2; ++k) {
						if (k == N) {
							offset = offset + N + N;
						}
						if (k == N + N) {
							offset = offset + N + N;
						}
						tabuList[(k  + offset)*N2 + oneFixed] = TABU;
					}
				}
			}
		}
	}

	private void init (SudokuState sourceState) {
		for (int valueRow = 0; valueRow < N2; valueRow++) {
			for (int valueCol = 0; valueCol < N2; valueCol++) {
				int value = sourceState.getValue(valueRow, valueCol);
				initForValue(valueRow, valueCol, value);
			}
		}
	}

	private void initForValue (int valueRow, int valueCol, int value) {
		// if is not empty position
		if (value > 0) {
			int tabuValueIndex = value - 1;

			// tabu value in whole colum
			for (int row = 0; row < N2; row++) {
				tabu[row][valueCol][tabuValueIndex] = true;
			}

			// tabu value in whole block
			int rowOffset = valueRow - (valueRow % N); // block first row index
			int colOffset = valueCol - (valueCol % N); // block first col index
			for (int row = rowOffset; row < rowOffset + N; row++) {
				for (int col = colOffset; col < colOffset + N; col++) {
					tabu[row][col][tabuValueIndex] = true;
				}
			}


			for (int v = 0; v < N2; v++) {
				// TODO mayby mark whole fixed value as tabu, for nicer string
				// tested for toString
				// NOT tested, that this values are not read in final algorithm, so could be used after final test
				// tabu[valueRow][valueCol][v] = true;
			}
		}
	}

	// TODO
	public boolean isTabuByValue(int row, int col, int value) {
		return tabu[row][col][value-1];
	}

	// TODO
	boolean isTabuByValueIndex(int row, int col, int valueIndex) {
		return tabu[row][col][valueIndex];
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int r=0; r<SudokuConstants.N2; r++) {
			for (int c=0; c<SudokuConstants.N2; c++) {
				b.append(String.valueOf(r+1));
				b.append("x");
				b.append(String.valueOf(c+1));
				b.append(":");
				for (int k=0; k<SudokuConstants.N2; k++) {
					boolean isTabu = tabu[r][c][k];
					if (!isTabu) {
						b.append(String.valueOf(k+1));
					}
				}
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}

	public String toStringOld() {
		StringBuilder b = new StringBuilder();
		for (int r=0; r<SudokuConstants.N2; r++) {
			for (int c=0; c<SudokuConstants.N2; c++) {
				b.append(String.valueOf(r+1));
				b.append("x");
				b.append(String.valueOf(c+1));
				b.append(":");
				for (int k=0; k<SudokuConstants.N2; k++) {
					int v = tabuList[(r*N2 + c)*N2 + k];
					if (v!=TABU) {
						b.append(String.valueOf(k+1));
					}
				}
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}


}
