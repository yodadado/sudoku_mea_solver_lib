package sk.study.mea.core.sudoku;

import java.util.Objects;
import java.util.Optional;

import static sk.study.mea.core.sudoku.SudokuConstants.N;
import static sk.study.mea.core.sudoku.SudokuConstants.N2;
import static sk.study.mea.core.sudoku.SudokuConstants.N4;

public final class SudokuProblemDefinitionMatrix implements SudokuProblemDefinition
{
	public static final int EMPTY_POSITION_VALUE = 0;

	final int[][] state;

	public SudokuProblemDefinitionMatrix (int[][] inputState) {
		validateState(inputState);
		state = Utils.cloneMatrix(inputState);
	}

	public SudokuProblemDefinitionMatrix (int[] inputState) {
		validateState(inputState);
		state = transformToMatrixState(inputState);
	}

	public Optional<Integer> getValue (int row, int col) {
		int value = state[row][col];
		return value == EMPTY_POSITION_VALUE //
			? Optional.empty()
			: Optional.of(value);
	}

	public boolean isFixedPosition (int row, int col) {
		return !isEmptyPosition(row, col);
	}

	public boolean isEmptyPosition (int row, int col) {
		return state[row][col] == EMPTY_POSITION_VALUE;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();

		for (int row = 0; row < N2; row++) {
			for (int col = 0; col < N2; col++) {
				String valueString = getValue(row, col)
					.map(String::valueOf)
					.orElse(" ");
				b.append(valueString);
				b.append(" ");
				if (col % N == 2 && col + 1 < N2) {
					b.append(" ");
				}
			}
			if (row + 1 < N2) {
				b.append(System.lineSeparator());
			}
		}

		return b.toString();
	}

	private void validateState (int[] inputState) {
		Objects.requireNonNull(inputState, "Input sudoku state");
		if (inputState.length != N4) {
			throw new IllegalArgumentException("Input sudoku state with invalid length");
		}
		for (int i = 0; i < N4; i++) {
			checkInputValue(inputState[i]);
		}
	}

	private void validateState (int[][] inputState) {
		Objects.requireNonNull(inputState, "Input sudoku state");
		if (inputState.length != N2) {
			throw new IllegalArgumentException("Input sudoku state with invalid length");
		}
		for (int row = 0; row < N2; row++) {
			int[] rowState = inputState[row];
			Objects.requireNonNull(rowState, "Input sudoku row state");
			if (rowState.length != N2) {
				throw new IllegalArgumentException("Input row state with invalid length");
			}
			for (int col = 0; col < N2; col++) {
				checkInputValue(rowState[col]);
			}
		}
	}

	private void checkInputValue (int value) {
		if (value < 0 || 9 < value) {
			throw new IllegalArgumentException("Input sudoku state contains invalid value");
		}
	}

	private int[][] transformToMatrixState (int[] source) {
		int[][] newState = new int[N2][N2];
		for (int row = 0; row < N2; row++) {
			int sourceOffset = row * N2;
			System.arraycopy(source, sourceOffset, newState[row], 0, N2);
		}

		return newState;
	}
}
