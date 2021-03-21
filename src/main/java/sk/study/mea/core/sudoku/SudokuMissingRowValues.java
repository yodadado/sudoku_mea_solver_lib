package sk.study.mea.core.sudoku;


import sk.study.mea.core.AnsiColorCodes;

import javax.swing.text.html.Option;
import java.util.*;

import static sk.study.mea.core.sudoku.SudokuConstants.N2;

/**
 * Sudoku rows empty columns by each row.
 *
 *
 *
 */
public class SudokuMissingRowValues
{
	private final Set<Integer>[] missingRowValues;

	public SudokuMissingRowValues (SudokuProblemDefinition problemDef)
	{
		missingRowValues = init(problemDef);
	}

	// zapise sa pre kazdy riadkok, pocet prazdnych cisel a ich indexu, kde sa nachadzaju = zapisuju sa indexy cisel kde su nuly


	private static Set<Integer>[] init (SudokuProblemDefinition problemDef) {
		Set<Integer>[] newMissingRowValues = new Set[N2];
		for (int row = 0; row < N2; row++) {
			newMissingRowValues[row] = getMissingValuesForRow (row, problemDef);
		}

		return newMissingRowValues;
	}

	private static Set<Integer> getMissingValuesForRow (int row, SudokuProblemDefinition problemDef) {
		Set<Integer> missingValues = new HashSet<>(N2);
		for (int value = 1; value <= N2; value++) {
			missingValues.add(value);
		}

		for (int col = 0; col < N2; col++) {
			Optional<Integer> value = problemDef.getValue(row, col);
			if (value.isPresent()) {
				missingValues.remove(value);
			}
		}

		return missingValues;
	}

	public List<Integer> getAllMissingValuesInRow(int row) {
		return new ArrayList<>(missingRowValues[row]);
	}

	public String toString() {
		StringBuilder b = new StringBuilder();

		for (int row = 0; row < N2; row++) {
			b.append(AnsiColorCodes.ANSI_YELLOW)
				.append(String.valueOf(row))
				.append(AnsiColorCodes.ANSI_RESET)
				.append(": ")
				.append(getAllMissingValuesInRow(row));
			if (row+1<N2) {
				b.append(System.lineSeparator());
			}
		}

		return b.toString();
	}
}
