package sk.study.mea.core;

import java.util.Random;

/**
 * Class MEA represent implemetation of Multiagent Evolution Algorithm invented by David Chudoba.
 *
 * David Chalupa: Population-based and learning-based metaheuristic algorithms for the graph coloring problem. GECCO 2011: 465-472
 *
 * Main method is optimize().
 * Agents are represented by list of AgentSudoku objects.
 * Elite_list object represent elite list data structure.
 *
 * @author David Durcak
 */

public abstract class Mea<P extends NPProblemDefinition>
{
	private final Random random;
	private final MeaConfiguration cfg;
	private final P fixedState; // TODO rename

	private final EliteList eliteList;
//	private final List<Agent> agents;

//	private final Object fixedState; // TODO *int
//	private final List<Object> fixedList; // TODO *int
//	private final List<Object> tabuList; // TODO *int
//
//	private int counterAgents; // TODO
//	private int counterTrial; // TODO

//	private final SudokuFixedState fixedState;


	//		std::list< AgentSudoku * >  agents;    // list of agents
	//
	//		static int	counterTrial;
	//		int			counterAgents;
	//
	//		int		*tabuList;
	//		int		*fixedState;
	//		int		*fixedLists;
	public Mea(MeaConfiguration cfg, P problemDefinition )
	{
		this.random = new Random();
		this.cfg = cfg;
		this.fixedState = problemDefinition;

		this.eliteList = new EliteList<>(random, cfg.getElitelistSize());

		// initialization of fixedLists
		// its matrix NN rows x (NN+1) columns ,  1. in column is count non fixed positions in row
		//fixedLists = new int[N4 + N2]);



		//		private final EliteList eliteList;
		//		private final List<Agent> agents;
		//
		//		private final Object fixedState; // TODO *int
		//		private final List<Object> fixedList; // TODO *int
		//		private final List<Object> tabuList; // TODO *int

		//fixedState = new SudokuFixedState(givenState);
		// TODO initialize random
		// counterTrial = 0;
		// fixedLists	= NULL;
		// tabuList	= NULL;
	}

	public P getFixedState ()
	{
		return fixedState;
	}
//	private void initFixedList ()
//	{
//
//	}

	/**
	 * main MEA optimalization method ( function of MEA)
	 * @return
	 */
	int optimize ()
	{
		return -1; // TODO not implemented
	}


	/**
	 * start initialization, load & prepare data
	 * @return
	 */
	private int initialization ()
	{
		return -1; // TODO not implemented
	}

	/**
	 * generate initial ppopulation of agents
	 * @return
	 */
	private int generateAgents ()
	{
		return -1; // TODO not implemented
	}

	// chceck if solution was found
	//private const	int		*controlFitness() const;
	// TODO toto vracalo pointer
	private int controlFitness ()
	{
		return -1; // TODO not implemented
	}

	/**
	 * decrease of lifespan and elimination of agents, that don't have any life points
	 * (push bestmilestone state to Elit List)
	 * @return
	 */
	private int decLifePointsAndEliminate ()
	{
		return -1; // TODO not implemented
	}

	/**
	 * birth new agent(get state from elit list)
	 * @return
	 */
	private int birthNewAgent ()
	{
		return -1; // TODO not implemented
	}

	/**
	 * local search
	 * @param generation
	 * @return
	 */
	private int localSearch (int generation)
	{
		return -1; // TODO not implemented
	}

}
