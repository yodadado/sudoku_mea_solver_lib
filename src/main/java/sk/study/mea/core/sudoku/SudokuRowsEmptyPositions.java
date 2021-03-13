package sk.study.mea.core.sudoku;

/**
 * Sudoku rows empty positions definitions.
 *
 *
 *
 */
// TODO originaly SudokuFixedState
public class SudokuRowsEmptyPositions
{
	public final int[] fixedLists; // TODO delete

	// Count of emtpty positions for each row
	private final int[] rowsEmptyPositionsCounts;

	// Indexes of empty positions for each rows.
	private final int[] rowsEmptyPositions;

	public SudokuRowsEmptyPositions (SudokuState sourceState)
	{
		fixedLists = new int[SudokuConstants.N2 + SudokuConstants.N4];
		rowsEmptyPositionsCounts = new int[SudokuConstants.N2];
		rowsEmptyPositions = new int[SudokuConstants.N4];

		initOld (sourceState);
		init (sourceState);
	}

	// zapise sa pre kazdy riadkok, pocet prazdnych cisel a ich indexu, kde sa nachadzaju = zapisuju sa indexy cisel kde su nuly

	//===================================================
	// initialization of fixedLists
	// its matrix NN rows x (NN+1) columns ,  1. in column is count non fixed positions in row
	@Deprecated
	private void initOld (SudokuState sourceState) {
		int offset = 0;
		for(int i = 0; i < SudokuConstants.N2; i++){
			int counter = 0;
			int k = offset + i;   // = i * (NN + 1);

			for(int j = 0; j < SudokuConstants.N2; j++){
				if(0 == sourceState.getValue(offset + j)){
					k++;
					fixedLists[k] = j;
					counter++;
				}
			}
			fixedLists[offset + i] = counter;
			offset += SudokuConstants.N2;
		}
	}

	private void init (SudokuState sourceState) {
		for (int row = 0; row < SudokuConstants.N2; row++) {
			int offset = row * SudokuConstants.N2;
			int counter = 0;
			for (int col = 0; col < SudokuConstants.N2; col++) {
				if (0 == sourceState.getValue(row, col)) {
					rowsEmptyPositions[offset + counter] = col;
					counter++;
				}
			}
			rowsEmptyPositionsCounts[row] = counter;
		}
	}


	public String toString() {
		StringBuilder b = new StringBuilder();

		for (int r=0; r<SudokuConstants.N2; r++) {
			int emptyPositionsCount = rowsEmptyPositionsCounts[r];
			b.append(String.valueOf(emptyPositionsCount));
			b.append(": ");
			for (int c=0; c<emptyPositionsCount; c++) {
				int v = rowsEmptyPositions[(r * SudokuConstants.N2) + c];
				b.append(String.valueOf(v));
			}
			if (r+1<SudokuConstants.N2) {
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}

	public String toStringOld() {
		StringBuilder b = new StringBuilder();

		for (int r=0; r<SudokuConstants.N2; r++) {
			for (int c=0; c<=fixedLists[r*(SudokuConstants.N2+1)]; c++) {
				int v = fixedLists[(r * (1+SudokuConstants.N2)) + c];
				b.append(String.valueOf(v));
				if (c == 0) {
					b.append(": ");
				}
			}
			if (r+1<SudokuConstants.N2) {
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}


}
