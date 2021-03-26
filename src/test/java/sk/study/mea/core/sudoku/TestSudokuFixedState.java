package sk.study.mea.core.sudoku;

import org.junit.jupiter.api.Test;
import sk.study.mea.core.MeaConfiguration;

import java.util.Optional;

public class TestSudokuFixedState
{
	// @DisplayName ("Single test successful")
	@Test
	void testSudokuProblemDefinitionMatrix() {
		SudokuProblemDefinition problemDefM = new SudokuProblemDefinitionMatrix (getSudokuMatrix());
		SudokuProblemDefinition problemDefA = new SudokuProblemDefinitionMatrix (getSudokuArray());
		System.out.println("\n\n"+problemDefM);
		System.out.println("\n\n"+problemDefA);

		MeaConfiguration easyCfg = MeaConfiguration.builder()
			.numAgents(80)
			.maxGenerations(100_000)
			.maxTrials(2)
			.startLifePoints(300)
			.birthStep(60)
			.milestoneStep(60)
			.elitelistSize(80)
			.build();

		MeaConfiguration mediumCfg = MeaConfiguration.builder()
			.numAgents(150)
			.maxGenerations(500_000)
			.maxTrials(3)
			.startLifePoints(400)
			.birthStep(60)
			.milestoneStep(60)
			.elitelistSize(150)
			.build();

		MeaConfiguration hardCfg = MeaConfiguration.builder()
			.numAgents(300)
			.maxGenerations(1_000_000)
			.maxTrials(5)
			.startLifePoints(500)
			.birthStep(60)
			.milestoneStep(60)
			.elitelistSize(300)
			.build();

		SudokuMea mea = new SudokuMea(easyCfg, problemDefM);
		//System.out.println(mea.getAnalysedProblemDef());


		Optional<SudokuAgentState> solution = mea.optimize();
		if (solution.isPresent()) {
			System.out.println("Solution:");
			System.out.println(solution.get());
		} else {
			System.out.println("Solution NOT found.");
		}
	}

	@Test
	void testSingleSuccessTest() {


			//		SudokuState state = new SudokuState(getSudokuArray());

//		SudokuState state = new SudokuState(getSudokuArray());
//		System.out.println(state);

		//System.out.println();
//		SudokuRowsEmptyPositions fState = new SudokuRowsEmptyPositions(state);
		//System.out.println(fState.toStringOld());
		//System.out.println();
		//System.out.println(fState);


//		SudokuTabuList tabu = new SudokuTabuList(state);
		//System.out.println();
		//System.out.println(tabu.toStringOld());
//		System.out.println();
//		System.out.println(tabu);

//		SudokuAgentState agentState = new SudokuAgentState(state, new Random());
//		System.out.println();
//		System.out.println(agentState);
	}

	private int[] getSudokuArray() {
		return new int[] { //
			0,2,6, 0,0,0, 8,1,0, //
			3,0,0, 7,0,8, 0,0,6, //
			4,0,0, 0,5,0, 0,0,7, //
			0,5,0, 1,0,7, 0,9,0, //
			0,0,3, 9,0,5, 1,0,0, //
			0,4,0, 3,0,2, 0,5,0, //
			1,0,0, 0,3,0, 0,0,2, //
			5,0,0, 2,0,4, 0,0,9, //
			0,3,8, 0,0,0, 4,6,0 //
		};
	}

	private int[][] getSudokuMatrix() {
		return new int[][] { //
			{0,0,2, 7,0,6, 5,0,0}, //
			{0,9,0, 0,5,0, 0,2,0}, //
			{5,0,0, 0,0,0, 0,0,7}, //
			{1,0,0, 0,7,0, 0,0,5}, //
			{0,2,0, 4,0,5, 0,7,0}, //
			{7,0,0, 0,3,0, 0,0,4}, //
			{2,0,0, 0,0,0, 0,0,8}, //
			{0,8,0, 0,0,0, 0,9,0}, //
			{0,0,3, 2,0,7, 6,0,0} //
		};
	}

	private int[][] getSudokuMatrix_easy() {
		return new int[][] { //
			{0,2,6, 0,0,0, 8,1,0}, //
			{3,0,0, 7,0,8, 0,0,6}, //
			{4,0,0, 0,5,0, 0,0,7}, //
			{0,5,0, 1,0,7, 0,9,0}, //
			{0,0,3, 9,0,5, 1,0,0}, //
			{0,4,0, 3,0,2, 0,5,0}, //
			{1,0,0, 0,3,0, 0,0,2}, //
			{5,0,0, 2,0,4, 0,0,9}, //
			{0,3,8, 0,0,0, 4,6,0} //
		};
	}
}
