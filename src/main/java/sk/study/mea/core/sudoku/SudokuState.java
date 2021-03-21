package sk.study.mea.core.sudoku;

import sk.study.mea.core.NPProblemDefinition;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

@Deprecated
public class SudokuState implements NPProblemDefinition
{
	private final int[] state = new int[N4];

	public SudokuState (int[] inputState) {
		validateState(inputState);
		System.arraycopy(inputState, 0, this.state, 0, N4);
	}

	public SudokuState (SudokuState inputState) {
		System.arraycopy(inputState.state, 0, this.state, 0, N4);
	}

	private void validateState(int[] inputState) {
		Objects.requireNonNull(inputState, "Input sudoku state");
		if (inputState.length != SudokuConstants.N4) {
			throw new IllegalArgumentException("Input sudoku state with invalid length");
		}
	}

	@Deprecated
	public int getValue(int idx) {
		return state[idx];
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
}
