package sk.study.mea.core.sudoku;

public class SudokuConstants
{

	// TODO
	public static final int N = 3;
	public static final int N2 = N * N;
	public static final int N4 = N2 * N2;
	public static final int COLS = N2;
	public static final int ROWS = N2;

	public static final int TABU = 1;
	public static final int MAX_FITNESS = 243;
	public static final int OPTIMAL_FITNESS = 0;
	public static final int USED = 1;
	public static final int NOT_USED = 0;

	// default parameters
	public static final int EPOPSIZE = 80; // start nums of agents
	public static final int EELITESIZE = 80; // max size of elite list
	public static final int ELIFESPAN = 300; // life points of agent
	public static final int EBIRTHPERIOD = 60; // calls between birth
	public static final int EMILESTONEPERIOD = 60; // calls between best milestone reseting
	public static final int ELOCALTRIALS = 2; // max calls in local search
	public static final int EMAXCALLS = 100000; // max calls together

	public static final int MPOPSIZE = 150;
	public static final int MELITESIZE = 150;
	public static final int MLIFESPAN = 400;
	public static final int MBIRTHPERIOD = 60;
	public static final int MMILESTONEPERIOD = 60;
	public static final int MLOCALTRIALS = 3;
	public static final int MMAXCALLS = 500000;

	public static final int HPOPSIZE = 300;
	public static final int HELITESIZE = 300;
	public static final int HLIFESPAN = 500;
	public static final int HBIRTHPERIOD = 60;
	public static final int HMILESTONEPERIOD = 60;
	public static final int HLOCALTRIALS = 5;
	public static final int HMAXCALLS = 1000000;
}


//
//typedef struct parameters{
//	int popSize;
//	int elitSize;
//	int lifespan;
//	int birthPeriod;
//	int milestonePeriod;
//	int localTrials;
//	int maxCalls;
//	} PARAMETERS;
