package sk.study.mea.core.sudoku;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

/**
 * Class AgentSudoku represent implemetation of Sudoku agent, which is used by MEA Sudoku algorithm.
 * Its provide all needed methods(generate new state, mutation, fitness function , local search methods)
 * for searching in states space of Sudoku agent.
 *
 * @author David Durcak
 */
public class AgentSudoku // TODO implements Agent
{
	private final SudokuState fixed; // TODO rename
	private final SudokuEmptyRowColumns fixedList; // TODO rename
	private final SudokuTabuListOld tabuList; // TODO rename

	private final int parMaxTrials; // parameter
	private final int lifePoints;


	// TODO extract, rename, change to double array if possible
	private final int currentFitness;
	private final int bestMilestoneFitness;

	private final int[] currentState;
	private final int[] currentFitnessList;
	private final int[] bestMilestoneState;
	private final int[] mutState;
	private final int[] mutFitnessList;
	private final int[] localBestMutState;
	private final int[] localBestMutFitnessList;


	// parStartLifePoints, fixedState, fixedLists, parMaxTrials, tabuList

	public AgentSudoku (SudokuState fixed, SudokuEmptyRowColumns fixedList, SudokuTabuListOld tabuList, int parMaxTrials, int lifePoints)
	{
		// TODO AnalysedSudokuProblemDefinition problemDef;

		this.fixed = fixed;
		this.fixedList = fixedList;
		this.tabuList = tabuList;
		this.parMaxTrials = parMaxTrials;
		this.lifePoints = lifePoints;

		this.currentFitness			= MAX_FITNESS;
		this.bestMilestoneFitness	= MAX_FITNESS;

		// TODO refactor
		this.currentState		= new int[N4];
		this.bestMilestoneState	= new int[N4];
		this.mutState			= new int[N4];
		this.currentFitnessList  = new int[2 * N2];
		this.mutFitnessList		= new int[2 * N2];
		this.localBestMutState		= new int[N4] ;
		this.localBestMutFitnessList = new int[2 * N2];
	}

	public int getOptimalFiltness() {
		return OPTIMAL_FITNESS;
	};

	public boolean localSearchUseHeuristic() {
		return false; // TODO
	};
}



//AgentSudoku:: AgentSudoku(int newLifePoints, int *fixedState, int *nfixedLists, int nparMaxTrials,  int *parTabuList){

//	}









