package sk.study.mea.core;

public interface AgentState<T>
{
	int getFitness ();

	T getState ();
}
