package sk.study.mea.core.sudoku;

import sk.study.mea.core.NPProblemDefinition;

import java.util.Objects;

public class SudokuState implements NPProblemDefinition
{
	private final int[] state = new int[SudokuConstants.N4];

	public SudokuState (int[] inputState) {
		validateState(inputState);

		System.arraycopy(inputState, 0, this.state, 0, SudokuConstants.N4);
	}

	public SudokuState (SudokuState inputState) {
		System.arraycopy(inputState.state, 0, this.state, 0, SudokuConstants.N4);
	}

	private void validateState(int[] inputState) {
		Objects.requireNonNull(inputState, "Input sudoku state");
		if (inputState.length != SudokuConstants.N4) {
			throw new IllegalArgumentException("Input sudoku state with invalid length");
		}
	}
}
