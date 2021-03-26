package sk.study.mea.core.sudoku;

import sk.study.mea.core.ProblemDefinition;

import java.util.Optional;

public interface SudokuProblemDefinition extends ProblemDefinition
{
	Optional<Integer> getValue(int row, int col);

	boolean isFixedPosition(int row, int col);

	boolean isEmptyPosition(int row, int col);

	@Deprecated // TODO used for generating new aget state
	int[][] getStateCopy();
}
