package sk.study.mea.core;

import lombok.Getter;

import java.util.Optional;

public abstract class BaseAgent<S extends AgentState> implements Agent<S>
{
	@Getter
	private final int maxTrials; // parameter

	@Getter
	private int lifePoints;

	@Getter
	protected S currentState;

	protected S bestMilestoneState;

	public BaseAgent (int maxTrials, int lifePoints, S currentState)
	{
		this.maxTrials = maxTrials;
		this.lifePoints = lifePoints;
		this.currentState = currentState;
		this.bestMilestoneState = null;
	}

	public Optional<S> getMilestoneState() {
		return Optional.ofNullable(bestMilestoneState);
	}

	public void resetMilestoneState() {
		if (bestMilestoneState == null //
			|| currentState.getFitness() <= bestMilestoneState.getFitness()){
			bestMilestoneState = currentState;
		}
	}

	/**
	 * local search (edited hill climing algorithm)
	 *
	 * @return {@code true} if current state was replaced by mutated state
	 */
	public boolean localSearch() {
		S localBestMutState = null; // TODO rename bestMutatedState

		for (int trial = 0; trial < maxTrials; trial++) {
			// MEA::	addCounterTrials(); // TODO counterTrial++;
			S mutatedState = mutateCurrentState();
			if (localBestMutState == null //
				|| mutatedState.getFitness() <= localBestMutState.getFitness()) {
				localBestMutState = mutatedState;
			}
		}

		boolean replaceCurrentState = localBestMutState != null //
			&& localBestMutState.getFitness() <= currentState.getFitness();
		if (replaceCurrentState) {
			currentState = localBestMutState;
		}

		return replaceCurrentState;
	}

	protected abstract S mutateCurrentState();

	@Override
	public int addLifePoints ()
	{
		return ++lifePoints;
	}

	@Override
	public int decLifePoints ()
	{
		return --lifePoints;
	}
}
