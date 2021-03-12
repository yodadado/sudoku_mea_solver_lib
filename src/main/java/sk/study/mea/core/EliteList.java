package sk.study.mea.core;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import lombok.Getter;

@Getter
public class EliteList<T>
{


	private final Random random;
	private final int maxSize;
	private final RandomAccessPriorityQueue<AgentState<T>> list;

	public EliteList (Random random, int maxSize)
	{
		this.random = random;
		this.maxSize = maxSize;

		// (from biggest fitnes to smallest fitness)
		Comparator<AgentState<T>> fitnessDescComparator = (state1, state2) -> state2.getFitness() - state1.getFitness();

		list = new RandomAccessPriorityQueue<>(maxSize, fitnessDescComparator);
	}

	/**
	 * Add state to elite list. If list is already full,
	 * new state isinserted only if has better or same fitness than the worst state in list.
	 *
	 * @param item
	 * @return <code>true</code> if new state for added.
	 */
	// TODO rename
	public boolean pushState (AgentState<T> agentState)
	{
		Objects.requireNonNull(agentState, "Inserted agent state must be defined");
		if (list.size() < maxSize)
		{
			return list.offer(agentState);
		}
		else
		{
			AgentState<T> worstFitnessItem = list.peek();
			if (agentState.getFitness() <= worstFitnessItem.getFitness())
			{
				list.poll();
				return list.offer(agentState);
			}
		}

		return false;
	}

	public Optional<AgentState<T>> getRandomState ()
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}
		int randomIndex = random.nextInt(list.size());
		return Optional.of(list.get(randomIndex));
	}
}
