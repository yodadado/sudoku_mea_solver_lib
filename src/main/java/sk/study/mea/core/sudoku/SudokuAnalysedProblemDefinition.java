package sk.study.mea.core.sudoku;

import lombok.Getter;

import java.util.Optional;

@Getter
public final class SudokuAnalysedProblemDefinition implements SudokuProblemDefinition
{
	private final SudokuProblemDefinition problemDef;
	private final SudokuMissingRowValues missingRowValues;
	private final SudokuEmptyRowColumns emptyRowColumns;
	private final SudokuTabuList tabuList;

	public SudokuAnalysedProblemDefinition (SudokuProblemDefinition problemDef) {
		this.problemDef = problemDef;
		this.missingRowValues = new SudokuMissingRowValues(problemDef);
		this.emptyRowColumns = new SudokuEmptyRowColumns(problemDef);
		this.tabuList = new SudokuTabuList(problemDef);
	}

	@Override
	public Optional<Integer> getValue (int row, int col)
	{
		return getProblemDef().getValue(row, col);
	}

	@Override
	public boolean isFixedPosition (int row, int col)
	{
		return getProblemDef().isFixedPosition(row, col);
	}

	@Override
	public boolean isEmptyPosition (int row, int col)
	{
		return getProblemDef().isEmptyPosition(row, col);
	}

	@Override
	public int[][] getStateCopy ()
	{
		return getProblemDef().getStateCopy();
	}

	@Override public String toString ()
	{
		return "AnalysedSudokuProblemDefinition{"
			+ "problemDef=\n" + problemDef
			+ ",\nmissingRowValues=\n" + missingRowValues
			+ ",\nemptyRowColumns=\n" + emptyRowColumns
			+ ",\ntabuList=\n" + tabuList + '}';
	}
}
