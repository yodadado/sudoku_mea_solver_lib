package sk.study.mea.core.sudoku;

import lombok.Getter;
import sk.study.mea.core.AgentConfiguration;
import sk.study.mea.core.BaseAgent;

import java.util.Random;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

/**
 * Class AgentSudoku represent implemetation of Sudoku agent, which is used by MEA Sudoku algorithm.
 * Its provide all needed methods(generate new state, mutation, fitness function , local search methods)
 * for searching in states space of Sudoku agent.
 */
@Getter
public class SudokuAgent extends BaseAgent<SudokuAgentState>
{
	private final Random random;
	private final SudokuAnalysedProblemDefinition analysedProblemDef;

	// parStartLifePoints, fixedState, fixedLists, parMaxTrials, tabuList

	public SudokuAgent (AgentConfiguration cfg, Random random, SudokuAnalysedProblemDefinition analysedProblemDef)
	{
		this (cfg, random, analysedProblemDef, new SudokuAgentState(analysedProblemDef, random));
	}

	public SudokuAgent (AgentConfiguration cfg, Random random, SudokuAnalysedProblemDefinition analysedProblemDef, SudokuAgentState currentState)
	{
		super (cfg, currentState);
		this.random = random;
		this.analysedProblemDef = analysedProblemDef;
	}

	protected SudokuAgentState mutateCurrentState() {
		return getCurrentState().mutate(random, analysedProblemDef);
	}
}
