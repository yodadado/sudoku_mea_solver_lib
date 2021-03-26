package sk.study.mea.core;

import lombok.Builder;
import lombok.Data;

/**
 * MeaConfiguration
 * @author David Durcak
 */
@Data
@Builder
public class MeaConfiguration
{
	// Parameters
	private final int numAgents;
	private final int maxGenerations;
	private final int maxTrials;
	private final int startLifePoints;
	private final int birthStep;
	private final int milestoneStep;
	private final int elitelistSize;
}
