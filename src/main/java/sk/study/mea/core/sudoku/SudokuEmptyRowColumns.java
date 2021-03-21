package sk.study.mea.core.sudoku;


import sk.study.mea.core.AnsiColorCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

/**
 * Sudoku rows empty columns by each row.
 *
 *
 *
 */
// TODO originaly SudokuFixedState
public class SudokuEmptyRowColumns
{
	private final List<Integer>[] emptyRowColumns;

	public SudokuEmptyRowColumns (SudokuProblemDefinition problemDef)
	{
		emptyRowColumns = new List[N2];

		init (problemDef);
	}

	// zapise sa pre kazdy riadkok, pocet prazdnych cisel a ich indexu, kde sa nachadzaju = zapisuju sa indexy cisel kde su nuly


	private void init (SudokuProblemDefinition problemDef) {
		for (int row = 0; row < N2; row++) {
			List<Integer> emptyPositions = new ArrayList<>(N2);
			for (int col = 0; col < N2; col++) {
				if (problemDef.isEmptyPosition(row, col)) {
					emptyPositions.add(col);
				}
			}
			emptyRowColumns[row] = emptyPositions;
		}
	}

	public int getNotFixedPositionsCountForRow(int row) {
		return emptyRowColumns[row].size();
	}

	@Deprecated
	public int getNotFixedPositionIndexForRow (int row, int index) {
		return emptyRowColumns[row].get(index);
	}

	public int getRandomNotFixedColumnInRow (int row, Random random) {
		List<Integer> emptyColumns = emptyRowColumns[row];

		return emptyColumns.get(random.nextInt(emptyColumns.size()));
	}

	public String toString() {
		StringBuilder b = new StringBuilder();

		for (int row = 0; row < N2; row++) {
			b.append(AnsiColorCodes.ANSI_YELLOW)
				.append(String.valueOf(getNotFixedPositionsCountForRow(row)))
				.append(AnsiColorCodes.ANSI_RESET)
				.append(": ")
				.append(emptyRowColumns[row]);
			if (row+1<N2) {
				b.append(System.lineSeparator());
			}
		}

		return b.toString();
	}
}
