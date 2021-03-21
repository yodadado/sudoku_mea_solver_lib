package sk.study.mea.core.sudoku;

import lombok.Getter;
import sk.study.mea.core.BaseAgent;

import java.util.Random;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

/**
 * Class AgentSudoku represent implemetation of Sudoku agent, which is used by MEA Sudoku algorithm.
 * Its provide all needed methods(generate new state, mutation, fitness function , local search methods)
 * for searching in states space of Sudoku agent.
 *
 * @author David Durcak
 */
@Getter
public class AgentSudoku extends BaseAgent<SudokuAgentState>
{
	private final Random random;
	private final AnalysedSudokuProblemDefinition analysedProblemDef;

	// parStartLifePoints, fixedState, fixedLists, parMaxTrials, tabuList

	public AgentSudoku (int parMaxTrials, int lifePoints, Random random, AnalysedSudokuProblemDefinition analysedProblemDef)
	{
		super (parMaxTrials, lifePoints, new SudokuAgentState(analysedProblemDef, random));
		this.random = random;
		this.analysedProblemDef = analysedProblemDef;
	}

//	public AgentSudoku (int parMaxTrials, int lifePoints, Random random, AnalysedSudokuProblemDefinition analysedProblemDef, SudokuAgentState inputState)
//	{
//		super (parMaxTrials, lifePoints, new SudokuAgentState(analysedProblemDef, random));
//		this.random = random;
//		this.analysedProblemDef = analysedProblemDef;
//	}


	public int getOptimalFitness() {
		return OPTIMAL_FITNESS;
	}

	protected SudokuAgentState mutateCurrentState() {
		return getCurrentState().mutate(random, analysedProblemDef);
	}
}
