package sk.study.mea.core;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class MEA represent implementation of MultiAgent Evolution Algorithm created by David Chalupa.
 *
 * It is optimization algorithm, that can be used for finding optimal or nearly optimal solutions of NP problems.
 * Algorithm use population of agents and every one of them represents any suboptimal or optimal solution rated by fitness function.
 * Agents are dynamically created and destroyed depending of their fitness value and number of life points.
 * At the beginning, agents states (problem proposed solution representation) are randomly generated.
 * In cycle, their are randomly mutated and their fitness rate is evaluated until solution with optimal fitness rate is found
 * or maximum generation is reached.
 *
 * @see <a href="https://opac.crzp.sk/?fn=detailBiblioForm&sid=839654DF12ADE2F5228B95FB778B">Dávid Durčák: Využitie multiagentového evolučného algoritmu v probléme z umelej inteligencie - Batchelor thesis</a>
 * @see <a href="https://opac.crzp.sk/?fn=detailBiblioForm&sid=DEAC73F7171E81681622175D4860">David Chudoba: Evolučný algoritmus pre optimalizáciu tried - Master thesis</a>
 * @see <a href="http://www2.fiit.stuba.sk/~kvasnicka/Seminar_of_AI/Chalupa_seminar%20UI%20Maj2011.pdf">David Chudoba: Evolučný algoritmus pre optimalizáciu tried</a>
 *
 * @author David Durcak
 */
//@Slf4j
public abstract class Mea<P extends ProblemDefinition, S extends AgentState>
{
	@Getter
	private final MeaConfiguration cfg;

	@Getter
	private final P problemDef;

	@Getter
	private final Random random;

	private final EliteList<S> eliteList;

	private List<Agent<S>> agents;

//	private int counterAgents; // TODO
//	private int counterTrial; // TODO

	/**
	 * Create new instance of Mea class.
	 *
	 * @param cfg the Mea algorithm configuration parameters
	 * @param problemDef the optimization problem definition
	 */
	public Mea(MeaConfiguration cfg, P problemDef)
	{
		this.problemDef = problemDef;
		this.cfg = cfg;
		this.random = new Random();
		this.eliteList = new EliteList<>(random, cfg.getElitelistSize());

		// TODO counterTrial = 0;
	}

	/**
	 * Main MEA optimization algorithm method.
	 * @return optional problem solution
	 */
	public Optional<S> optimize ()
	{
		eliteList.clear();
		agents = generateAgents();

		int generation = 0;
		Optional<S> solution;
		int counterTrial = 0;

		int maxFitTrias = getCfg().getMaxGenerations() - 2 * getCfg().getNumAgents();
		while (counterTrial < maxFitTrias) {
			generation++;
			solution = controlFitness();
			if (solution.isPresent()) {
				return solution;
			}

			decreaseAgentsLifePointsAndEliminate();

			if ( 0 == generation % cfg.getBirthStep() //
				&& agents.size() < getCfg().getNumAgents()) {
				birthNewAgent(generation);
			}

			localSearch(generation);
		}
		// TODO return best one found (for other problem types)
		return Optional.empty();
	}

	/**
	 * Generate a initial agents population.
	 * @return list of generated agents
	 */
	private List<Agent<S>> generateAgents ()
	{
		return Stream.generate(this::generateAgent)
			.limit(getCfg().getNumAgents())
			.collect(Collectors.toList());
	}

	/**
	 * Generate agent with initial state.
	 * @return generated agent.
	 */
	protected abstract Agent<S> generateAgent ();

	/**
	 * Control all agents current states whether optimal solution was found.
	 * @return optional problem solution
	 */
	private Optional<S> controlFitness ()
	{
		return agents.stream()
			.filter(this::isAgentCurrentStateOptimal)
			.map(Agent::getCurrentState)
			.findAny();
	}

	/**
	 * Control if input agent current state is optimal solution was found.
	 * @param agent the agent
	 * @return {@code true} if agent current state is optimal
	 */
	protected boolean isAgentCurrentStateOptimal(Agent<S> agent) {
		return getOptimalFitness() == agent.getCurrentState().getFitness();
	}

	/**
	 * Get optimal fitness value.
	 * @return optimal fitness rate value
	 */
	protected abstract int getOptimalFitness();

	/**
	 * Decrease all agents life points and eliminate these which dont left any life points.
	 * When agents is eliminated its best milestone state (or current state, if milestone is not exist) is pushed to elite list structure.
	 */
	private void decreaseAgentsLifePointsAndEliminate ()
	{
		Iterator<Agent<S>> agentIt = agents.iterator();
		while (agentIt.hasNext()) {
			Agent<S> agent = agentIt.next();
			// log.trace() //outstr.Format("\nM(%d)LF %3d   CF  %3d  %p",(*auxIt)->getName(),(*auxIt)->getLifePoints(), (*auxIt)->getCurrentFitness(),(*auxIt)->getCurrentState() );

			int agentLifePoints = agent.decLifePoints();
			if (agentLifePoints <= 0) {
				// if agent does not have any milestone state, then current state will by used
				S agentMilestoneState = agent.getMilestoneState().orElse(agent.getCurrentState());
				eliteList.pushState(agentMilestoneState);

				// remove agent without lifepoints
				agentIt.remove();
			}
		}
	}

	/**
	 * Birth new agents to renew agents population to its maximum size.
	 * New agents are bornt with states, that are randomly picked from elite list.
	 * @param generation the current generation
	 */
	private void birthNewAgent (int generation)
	{
		if (agents.size() < getCfg().getNumAgents()
			&& 0 == generation % getCfg().getBirthStep()) {

			Optional<S> randomAgentState = eliteList.getRandomState();
			//outstr.Format("EliteList:: getRandomState() get state %3d %p", randomState.fitness, randomState.state); CLogger::Instance()->write(outstr);

			final Agent<S> newAgent = randomAgentState.isPresent()
				? createAgentWithState (randomAgentState.get())
				: generateAgent();

			agents.add(newAgent);

			//++counterAgents;
			//outstr.Format("agent back %3d.  F %3d   CS  %p,",agents.back()->getName(), agents.back()->getCurrentFitness(), agents.back()->getCurrentState()); m_listbox->AddString(outstr);
		}
	}

	/**
	 * Create (birth) new agent with input state to renew agents population to its maximum size.
	 * New agents are bornt with states, that are randomly picked from elite list.
	 * @param state the initial agent state
	 */
	protected abstract Agent<S> createAgentWithState (S state);

	/**
	 * The local search algorithm step. For each agent individually, process of local search is run.
	 * @param generation the current optimization generation
	 */
	private void localSearch (int generation)
	{
//		log.debug ("#{} gen. Local search", generation);
		for (Agent<S> agent: agents) {
			localSearch (generation, agent);
		}
	}

	/**
	 * The imput agent local search.
	 * @param generation the current optimization generation
	 * @param agent the agent
	 */
	protected void localSearch (int generation, Agent<S> agent) {
		// reward or punishment
		if (agent.localSearch()) {
			agent.addLifePoints();
		}

		// punishment hasnt be
		if (0 != generation && 0 == generation % getCfg().getMilestoneStep() ){
			agent.resetMilestoneState();
		}
	}

	// TODO toString
}
