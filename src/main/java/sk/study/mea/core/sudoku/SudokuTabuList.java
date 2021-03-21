package sk.study.mea.core.sudoku;

import sk.study.mea.core.AnsiColorCodes;

import java.util.Optional;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

/**
 * Sudoku rows empty positions definitions.
 *
 *
 *
 */
public class SudokuTabuList
{
	private final boolean[][][] tabu;

//	// Count of emtpty positions for each row
//	private final int[] rowsEmptyPositionsCounts;
//
//	// Indexes of empty positions for each rows.
//	private final int[] rowsEmptyPositions;

	public SudokuTabuList (SudokuProblemDefinition problemDef)
	{
		tabu = new boolean[N2][N2][N2];

		init (problemDef);
	}

	// zapise sa pre kazdy riadkok, pocet prazdnych cisel a ich indexu, kde sa nachadzaju = zapisuju sa indexy cisel kde su nuly

	// TODO make this create and return this array
	private void init (SudokuProblemDefinition problemDef) {
		for (int row = 0; row < N2; row++) {
			for (int col = 0; col < N2; col++) {
				Optional<Integer> value = problemDef.getValue(row, col);
				if (value.isPresent()) {
					initForValue(row, col, value.get());
				}
			}
		}
	}

	private void initForValue (int valueRow, int valueCol, int value) {
		int valueIdx = value - 1;

		// tabu value in whole colum
		for (int row = 0; row < N2; row++) {
			tabu[row][valueCol][valueIdx] = true;
		}

		// tabu value in whole block
		int rowOffset = valueRow - (valueRow % N); // block first row index
		int colOffset = valueCol - (valueCol % N); // block first col index
		for (int row = rowOffset; row < rowOffset + N; row++) {
			for (int col = colOffset; col < colOffset + N; col++) {
				tabu[row][col][valueIdx] = true;
			}
		}

		for (int v = 0; v < N2; v++) {
			// TODO mayby mark whole fixed value as tabu, for nicer string
			// tested for toString
			// NOT tested, that this values are not read in final algorithm, so could be used after final test
			// tabu[valueRow][valueCol][v] = true;
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
		for (int row = 0; row < N2; row++) {
			for (int col = 0; col < N2; col++) {
				b.append("[")
					.append(AnsiColorCodes.ANSI_YELLOW)
					.append(String.valueOf(row))
					.append(AnsiColorCodes.ANSI_RESET)
					.append(", ")
					.append(AnsiColorCodes.ANSI_YELLOW)
					.append(String.valueOf(col))
					.append(AnsiColorCodes.ANSI_RESET)
					.append("]: ");
				for (int valueIdx = 0; valueIdx < N2; valueIdx++) {
					boolean isTabu = tabu[row][col][valueIdx];
					if (!isTabu) {
						b.append(String.valueOf(valueIdx+1));
					}
				}
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}
}
