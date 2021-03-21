package sk.study.mea.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sk.study.mea.core.sudoku.AgentSudoku;
import sun.management.resources.agent;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//@Slf4j
public abstract class Mea<D extends NPProblemDefinition, S extends ProblemState>
{

	@Getter
	private final D fixedState; // TODO rename
	@Getter
	private final MeaConfiguration cfg;
	private final Random random;
	private final EliteList eliteList;
	private List<Agent<S>> agents;

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
	public Mea(MeaConfiguration cfg, D problemDefinition)
	{
		this.fixedState = problemDefinition;
		this.cfg = cfg;
		this.random = new Random();

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

	/**
	 * main MEA optimalization method ( function of MEA)
	 * @return
	 */
	int optimize ()
	{
		eliteList.clear();
		agents = generateAgents();

		return -1; // TODO not implemented
	}


//	/**
//	 * start initialization, load & prepare data
//	 * @return
//	 */
//	private int initialization ()
//	{
//		return -1; // TODO not implemented
//	}

	/**
	 * generate initial ppopulation of agents
	 * @return
	 */
	private List<Agent<S>> generateAgents ()
	{
		return Stream.generate(this::generateAgent)
			.limit(getCfg().getNumAgents())
			.collect(Collectors.toList());
	}

	protected abstract Agent<S> generateAgent ();

	// chceck if solution was found
	//private const	int		*controlFitness() const;
	// TODO toto vracalo pointer
	private Optional<S> controlFitness ()
	{
		return agents.stream()
			.filter(Agent::isCurrentFitnessOptimal)
			.map(Agent::getCurrentState)
			.findAny();
	}

	/**
	 * decrease of lifespan and elimination of agents, that don't have any life points
	 * (push bestmilestone state to Elit List)
	 * @return
	 */
	private void decLifePointsAndEliminate ()
	{
		Iterator<Agent<S>> agentIt = agents.iterator();
		while (agentIt.hasNext()) {
			Agent<S> agent = agentIt.next();
			// log.trace() //outstr.Format("\nM(%d)LF %3d   CF  %3d  %p",(*auxIt)->getName(),(*auxIt)->getLifePoints(), (*auxIt)->getCurrentFitness(),(*auxIt)->getCurrentState() );

			int agentLifePoints = agent.decLifePoints();
			if (agentLifePoints <= 0) {
				// TODO
				// check if agents has milestone state
				/*
				if(MAX_FITNESS == (*auxIt)->getBestMilestoneFitness() ){
					// if no , last state become elit state
					eliteList.pushState((*auxIt)->getCurrentFitness(), (*auxIt)->getCurrentState() );
					(*auxIt)->setCurrentState(NULL);
				}else{
					eliteList.pushState((*auxIt)->getBestMilestoneFitness(), (*auxIt)->getBestMilestoneState());
					(*auxIt)->setBestMilestoneState(NULL);
				}
				*/
				// remove agent without lifepoints
				agentIt.remove();
			}
		}
	}

	/**
	 * birth new agent(get state from elit list)
	 * @return
	 */
	private void birthNewAgent (int generation)
	{
		if( agents.size() < getCfg().getNumAgents()
			&& 0 == generation % getCfg().getBirthStep()) {

			Optional<Agent<S>> randomAgentState = eliteList.getRandomState();
			//outstr.Format("EliteList:: getRandomState() get state %3d %p", randomState.fitness, randomState.state); CLogger::Instance()->write(outstr);

			final Agent<S> newAgent;
			if (randomAgentState.isPresent()) {
				// TODO
				// newAgent =
				// agents.push_back(new AgentSudoku(
				// parStartLifePoints, fixedState, fixedLists, randomState.state, randomState.fitness, parMaxTrials,	tabuList));
			} else {
				// TODO
				// newAgent =
				// agents.push_back(new AgentSudoku(
				// parStartLifePoints, fixedState, fixedLists, parMaxTrials, tabuList));
			}
			// TODO
			// agents.add(newAgent);

			//++counterAgents;
			//outstr.Format("agent back %3d.  F %3d   CS  %p,",agents.back()->getName(), agents.back()->getCurrentFitness(), agents.back()->getCurrentState()); m_listbox->AddString(outstr);
		}
	}

	/**
	 * local search
	 * @param generation
	 * @return
	 */
	private void localSearch (int generation)
	{
//		log.debug ("#{} gen. Local search", generation);
		for (Agent<S> agent: agents) {
			localSearch (generation, agent);
		}
	}

	protected void localSearch (int generation, Agent<S> agent) {
		// reward or punishment
		if (agent.localSearchUseHeuristic()) {
			agent.addLifePoints();
		}

		// punishment hasnt be
		if (0 != generation && 0 == generation % getCfg().getMilestoneStep() ){
			agent.resetMilestoneState();
		}
	}
}
