package sk.study.mea.core.sudoku;

import sk.study.mea.core.NPProblemDefinition;

import java.util.Arrays;
import java.util.Objects;

import static sk.study.mea.core.sudoku.SudokuConstants.N2;
import static sk.study.mea.core.sudoku.SudokuConstants.N4;

public class BaseSudokuState implements NPProblemDefinition
{
	protected int[] state = new int[N4];

	public BaseSudokuState (int[] inputState) {
		validateState(inputState);
		System.arraycopy(inputState, 0, this.state, 0, N4);
	}

	public BaseSudokuState (BaseSudokuState inputState) {
		System.arraycopy(inputState.state, 0, this.state, 0, N4);
	}

	private void validateState(int[] inputState) {
		Objects.requireNonNull(inputState, "Input sudoku state");
		if (inputState.length != SudokuConstants.N4) {
			throw new IllegalArgumentException("Input sudoku state with invalid length");
		}
	}

	public int getValue(int row, int col) {
		return state[row * N2 + col];
	}

	public int[] getStateCopy() {
		return Arrays.copyOf(state, state.length);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();

		for (int r=0; r<SudokuConstants.N2; r++) {
			for (int c=0; c<SudokuConstants.N2; c++) {
				int v = getValue(r,c);
				b.append(v == 0 ? "." : String.valueOf(v));
				b.append(" ");
				if (c % SudokuConstants.N == 2 && c+1<SudokuConstants.N2) {
					b.append(" ");
				}
			}
			if (r+1<SudokuConstants.N2) {
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}

	// TODO remove
	public static String toString(int[] state) {
		StringBuilder b = new StringBuilder();

		for (int r=0; r<SudokuConstants.N2; r++) {
			for (int c=0; c<SudokuConstants.N2; c++) {
				int v = state[r * N2 + c];
				b.append(v == 0 ? "." : String.valueOf(v));
				b.append(" ");
				if (c % SudokuConstants.N == 2 && c+1<SudokuConstants.N2) {
					b.append(" ");
				}
			}
			if (r+1<SudokuConstants.N2) {
				b.append(System.lineSeparator());
			}
		}
		return b.toString();
	}
}
