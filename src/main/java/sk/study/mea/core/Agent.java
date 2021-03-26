package sk.study.mea.core;

import java.util.Optional;

public interface Agent<S extends AgentState>
{
	S getCurrentState();

	Optional<S> getMilestoneState();


	/**
	 * local search (edited hill climing algorithm)
	 *
	 * @return {@code true} if current state was replaced by mutated state
	 */
	// TODO return enum [BETTER ,EQ, WORSE ]
	boolean localSearch();

	int addLifePoints();

	int decLifePoints();

	void resetMilestoneState();
}
