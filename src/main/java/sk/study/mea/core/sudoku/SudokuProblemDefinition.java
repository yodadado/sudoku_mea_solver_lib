package sk.study.mea.core.sudoku;

import sk.study.mea.core.NPProblemDefinition;

import java.util.Optional;

import static sk.study.mea.core.sudoku.SudokuConstants.N;
import static sk.study.mea.core.sudoku.SudokuConstants.N2;

public interface SudokuProblemDefinition extends NPProblemDefinition
{
	Optional<Integer> getValue(int row, int col);

	boolean isFixedPosition(int row, int col);

	boolean isEmptyPosition(int row, int col);

	@Deprecated // TODO used for generating new aget state
	int[][] getStateCopy();
}
