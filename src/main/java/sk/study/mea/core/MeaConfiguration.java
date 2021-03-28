package sk.study.mea.core;

import lombok.Builder;
import lombok.Data;

/**
 * Mea algorithm configuration parameters.
 */
@Data
@Builder
public class MeaConfiguration
{
	private final int agentPopulationMaxSize;
	private final int generationsMaxCount;
	private final int birthStep;
	private final int milestoneStep;
	private final int elitelistMaxSize;
	private final AgentConfiguration agentCfg;

}
