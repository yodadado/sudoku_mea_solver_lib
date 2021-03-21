package sk.study.mea.core.sudoku;

import sk.study.mea.core.Agent;
import sk.study.mea.core.Mea;
import sk.study.mea.core.MeaConfiguration;
import sk.study.mea.core.ProblemState;

/**
 * This is implemetation of {@link Mea} for solving Sudoku problem.
 *
 * @author David Durcak
 */
public class MeaSudoku extends Mea<SudokuProblemDefinition, ProblemState>
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
	protected Agent generateAgent () {
		//agents.push_back(new AgentSudoku(parStartLifePoints, fixedState, fixedLists, parMaxTrials, tabuList));

		// TODO
		//(*itAgent)->generateNewState();

		return null; // TODOnew AgentSudoku (getFixedState(), fixedLists, tabuList, getCfg().getMaxTrials(), getCfg().getStartLifePoints());
	}
}
