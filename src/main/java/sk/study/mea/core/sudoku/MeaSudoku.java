package sk.study.mea.core.sudoku;

import sk.study.mea.core.*;

/**
 * This is implemetation of {@link Mea} for solving Sudoku problem.
 *
 * @author David Durcak
 */
public class MeaSudoku extends Mea<SudokuProblemDefinition, >
{
	private final SudokuEmptyRowColumns fixedLists; // TODO rename
	private final SudokuTabuListOld tabuList; // TODO rename

	public MeaSudoku (MeaConfiguration cfg, SudokuProblemDefinition problemDefinition)
	{
		super(cfg, problemDefinition);

		fixedLists = null; // TODO new SudokuRowsEmptyPositions(problemDefinition);
		tabuList = null; // TODO new SudokuTabuList(problemDefinition);
	}

	@Override
	protected AgentSudoku generateAgent () {

		return new AgentSudoku(............);
	}

	@Override protected Agent createAgentWithState (AgentState state)
	{
		return new AgentSudoku(............);
	}
}
