package sk.study.mea.core;

public interface Agent<S extends ProblemState>
{
	int getOptimalFiltness();
	int getCurrentFiltness();
	S getCurrentState();

	int decLifePoints();

	default boolean isCurrentFitnessOptimal() {
		return getOptimalFiltness() == getCurrentFiltness();
	}

	// TODO rename
	boolean localSearchUseHeuristic();
	void addLifePoints();
	void resetMilestoneState();
}
