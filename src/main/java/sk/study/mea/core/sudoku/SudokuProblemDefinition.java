package sk.study.mea.core.sudoku;

import sk.study.mea.core.NPProblemDefinition;

import java.util.Optional;

public interface SudokuProblemDefinition extends NPProblemDefinition
{
	Optional<Integer> getValue(int row, int col);

	boolean isFixedPosition(int row, int col);

	boolean isEmptyPosition(int row, int col);
}
