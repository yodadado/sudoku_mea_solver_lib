package sk.study.mea.core;

import lombok.Builder;
import lombok.Data;

/**
 * Mea agent configuration parameters.
 */
@Data
@Builder
public class AgentConfiguration
{
	private final int maxTrials;
	private final int initialLifePoints;
}
