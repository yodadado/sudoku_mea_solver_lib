package sk.study.mea.core.sudoku;

import sk.study.mea.core.Agent;
import sk.study.mea.core.Mea;
import sk.study.mea.core.MeaConfiguration;

/**
 * This is implemetation of {@link Mea} for solving Sudoku problem.
 *
 * @author David Durcak
 */
public class MeaSudoku extends Mea<SudokuProblemDefinition>
{
	private final SudokuRowsEmptyPositions fixedLists; // TODO rename
	private final SudokuTabuList tabuList; // TODO rename

	public MeaSudoku (MeaConfiguration cfg, SudokuProblemDefinition problemDefinition)
	{
		super(cfg, problemDefinition);

		fixedLists = new SudokuRowsEmptyPositions(problemDefinition);
		tabuList = new SudokuTabuList(problemDefinition);
	}

	@Override
	protected Agent generateAgent () {
		//agents.push_back(new AgentSudoku(parStartLifePoints, fixedState, fixedLists, parMaxTrials, tabuList));

		// TODO
		//(*itAgent)->generateNewState();

		return new AgentSudoku (getFixedState(), fixedLists, tabuList,
			getCfg().getMaxTrials(), getCfg().getStartLifePoints());
	}
}
