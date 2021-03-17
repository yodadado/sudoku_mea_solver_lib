package sk.study.mea.core.sudoku;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestSudokuFixedState
{

	// @DisplayName ("Single test successful")
	@Test
	void testSingleSuccessTest() {
		// log.info("Success");
		SudokuState state = new SudokuState(getSudokuArray());
		System.out.println(state);

		//System.out.println();
		SudokuRowsEmptyPositions fState = new SudokuRowsEmptyPositions(state);
		//System.out.println(fState.toStringOld());
		//System.out.println();
		//System.out.println(fState);


		SudokuTabuList tabu = new SudokuTabuList(state);
		//System.out.println();
		//System.out.println(tabu.toStringOld());
//		System.out.println();
//		System.out.println(tabu);

		SudokuAgentState agentState = new SudokuAgentState(state, new Random());
		System.out.println();
		System.out.println(agentState);
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
}
