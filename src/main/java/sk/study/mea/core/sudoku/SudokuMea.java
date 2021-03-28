package sk.study.mea.core.sudoku;

import lombok.Getter;
import sk.study.mea.core.*;

import static sk.study.mea.core.sudoku.SudokuConstants.OPTIMAL_FITNESS;

/**
 * This is implemetation of {@link Mea} for solving Sudoku problem.
 */
public class SudokuMea extends Mea<SudokuProblemDefinition, SudokuAgentState>
{

	@Getter private final SudokuAnalysedProblemDefinition analysedProblemDef;

	public SudokuMea (MeaConfiguration cfg, SudokuProblemDefinition problemDef)
	{
		super(cfg, problemDef);
		analysedProblemDef = new SudokuAnalysedProblemDefinition(problemDef);
	}

	public int getOptimalFitness() {
		return OPTIMAL_FITNESS;
	}

	@Override protected SudokuAgent generateAgent ()
	{
		return new SudokuAgent(getCfg().getAgentCfg(), getRandom(), getAnalysedProblemDef());
	}

	@Override protected Agent<SudokuAgentState> createAgentWithState (SudokuAgentState state)
	{
		return new SudokuAgent(getCfg().getAgentCfg(), getRandom(), getAnalysedProblemDef(), state);
	}
}
