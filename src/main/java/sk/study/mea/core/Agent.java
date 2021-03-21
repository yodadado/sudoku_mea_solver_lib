package sk.study.mea.core;

import java.util.Optional;

public interface Agent<S extends AgentState>
{
	int getOptimalFitness();
	S getCurrentState();
	Optional<S> getMilestoneState();
	boolean isCurrentFitnessOptimal();


	/**
	 * local search (edited hill climing algorithm)
	 *
	 * @return {@code true} if current state was replaced by mutated state
	 */
	boolean localSearch();
	int addLifePoints();
	int decLifePoints();
	void resetMilestoneState();
}
