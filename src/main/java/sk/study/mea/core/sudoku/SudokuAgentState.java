package sk.study.mea.core.sudoku;

import lombok.Getter;
import sk.study.mea.core.AgentState;
import sk.study.mea.core.AnsiColorCodes;
import sk.study.mea.core.ProblemState;

import java.util.*;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

public class SudokuAgentState implements AgentState
{
	private final int[][] state;
	private final int[] colFitnessList;
	private final int[] blockFitnessList;
	@Getter
	private int fitness;


	// used when generating new agents
	public SudokuAgentState (AnalysedSudokuProblemDefinition analysedProblemDef, Random random) {
		this.state = generateState(analysedProblemDef, random);
		this.colFitnessList = fitnessForColumns(state);
		this.blockFitnessList = fitnessForBlocks(state);
		this.fitness = sumColumnsAndBlockFitness(colFitnessList, blockFitnessList);
	}

	// used when create agent from random elite list state
	public SudokuAgentState (SudokuAgentState other) {
		this.state = Utils.cloneMatrix(other.state);
		this.colFitnessList = other.colFitnessList.clone();
		this.blockFitnessList = other.blockFitnessList.clone();
		this.fitness = other.getFitness();
	}

	@Deprecated
	public void setTo (SudokuAgentState other) {
		Utils.matrixcopy(other.state, this.state);
		Utils.arraycopy(other.colFitnessList, this.colFitnessList);
		Utils.arraycopy(other.blockFitnessList, this.blockFitnessList);
		this.fitness = other.getFitness();
	}

	private static int[][] generateState(AnalysedSudokuProblemDefinition analysedProblemDef, Random random) {
		int[][] newState = analysedProblemDef.getStateCopy();

		// for each row=(sequence)
		// i = colIndex
		for (int row = 0; row < N2; ++row) {
			List<Integer> missingValues = analysedProblemDef.getMissingRowValues().getAllMissingValuesInRow(row);
			if (missingValues.size() != analysedProblemDef.getEmptyRowColumns().getNotFixedPositionsCountForRow(row)) {
				throw new IllegalStateException("Missing values count differs with not fixed positions count");
			}

			if (missingValues.size() > 0) {
				Collections.shuffle(missingValues, random);
				for (int valueIdx = 0; valueIdx < missingValues.size(); valueIdx++) {
					int col = analysedProblemDef.getEmptyRowColumns().getNotFixedPositionIndexForRow(row, valueIdx);
					newState[row][col] = missingValues.get(valueIdx);
				}
			}
		}

		return newState;
	}

	private static int[] fitnessForColumns(int[][] state) {
		int[] fitnessList = new int[N2];
		boolean[] valueUsed = new boolean[N2];

		for (int col = 0; col < N2; col++) {
			fitnessList[col] = fitnessForColumn (state, valueUsed, col);
		}

		return fitnessList;
	}

	private static int fitnessForColumn (int[][] state, boolean[] valueUsed, int col) {
		int columnFitness = 0;
		for (int i = 0; i < N2; i++) {
			valueUsed[i] = false;
		}

		for (int row = 0; row < N2; row++) {
			int value = state[row][col];
			if (valueUsed[value - 1]) {
				columnFitness++;
			} else {
				valueUsed[value - 1] = true;
			}
		}

		return columnFitness;
	}

	private static int[] fitnessForBlocks(int[][] state) {
		int[] fitnessList = new int[N2];
		boolean[] valueUsed = new boolean[N2];

		for (int blockBow = 0; blockBow < N; blockBow++) {
			for (int blockCol = 0; blockCol < N; blockCol++) {
				int block = blockBow * N + blockCol;
				int blockStartRow = blockBow * N;
				int blockStartCol = blockCol * N;

				fitnessList[block] = fitnessForBlock(state, valueUsed, blockStartRow, blockStartCol);
			}
		}

		return fitnessList;
	}

	private static int fitnessForBlock(int[][] state, boolean[] valueUsed, int blockStartRow, int blockStartCol) {
		int blockFitness = 0;
		for (int i = 0; i < N2; i++) {
			valueUsed[i] = false;
		}

		for (int row = blockStartRow + N-1; row >= blockStartRow; row--) {
			for (int col = blockStartCol + N-1; col >= blockStartCol; col--) {
				int value = state[row][col];
				if (valueUsed[value - 1]) {
					blockFitness++;
				} else {
					valueUsed[value - 1] = true;
				}
			}
		}

		return blockFitness;
	}

	private static int sumColumnsAndBlockFitness(int[] colFitnessList, int[] blockFitnessList) {
		int fitnessSum = 0;
		for (int i = 0; i < N2; i++) {
			fitnessSum += colFitnessList[i] + blockFitnessList[i];
		}

		return fitnessSum;
	}

	public SudokuAgentState mutate(Random random, AnalysedSudokuProblemDefinition analysedProblemDef) {
		SudokuAgentState newState = new SudokuAgentState(this);
		mutateAndRecalculateFitness (random, analysedProblemDef, newState);

		return newState;
	}

	/**
	 * Mutate input agent stated and recalculate its fitness.
	 * <br>
	 * Mutation is performed by swapping to random columns in random row selected with using heuristic optimalization algorithm.
	 *
	 * @param random the random
	 * @param analysedProblemDef the analysed problem definiton
	 * @param agentState the mutated agent
	 */
	private static void mutateAndRecalculateFitness (Random random, AnalysedSudokuProblemDefinition analysedProblemDef, SudokuAgentState agentState) {
		int[][] state = agentState.state;
		int[] colFitnessList = agentState.colFitnessList;
		int[] blockFitnessList = agentState.blockFitnessList;

		SwapPositionModel swapModel = getRandomSwapPositionUsingHeuristic (state, analysedProblemDef, random );
		int row = swapModel.getRow();
		int col1 = swapModel.getCol1();
		int col2 = swapModel.getCol2();

		// swap positions
		swapValuesInRow(state[row], col1, col2);

		//  Fitness , evaluate only changes------------------------------------------
		boolean[] valueUsed = new boolean[N2];

		// columns fitness difference
		int fitDiffCol1 = fitnessForColumn (state, valueUsed, col1) - colFitnessList[col1];
		int fitDiffCol2 = fitnessForColumn (state, valueUsed, col2) - colFitnessList[col2];
		colFitnessList[col1] += fitDiffCol1;
		colFitnessList[col2] += fitDiffCol2;

		// blocks fitness difference
		// row < 3 ? 0 : (row < 6 ? 3 : 6)
		int blockStartRow = row < N ? 0 : ((row < N + N) ? N : N + N);
		int blockStartCol1 = col1 < N ? 0 : ((col1 < N + N) ? N : N + N);
		int blockStartCol2 = col2 < N ? 0 : ((col2 < N + N) ? N : N + N);

		int block1 = blockStartRow + col1 % N;
		int block2 = blockStartRow + col2 % N;

		int fitDiffBlock1 = fitnessForBlock(state, valueUsed, blockStartRow, blockStartCol1) - blockFitnessList[col1];
		int fitDiffBlock2 = fitnessForBlock(state, valueUsed, blockStartRow, blockStartCol2) - blockFitnessList[col2];
		blockFitnessList[block1] += fitDiffBlock1;
		blockFitnessList[block2] += fitDiffBlock2;

		// update fitness
		agentState.fitness += fitDiffCol1 + fitDiffCol2 + fitDiffBlock1 + fitDiffBlock2;
	}

	private static SwapPositionModel getRandomSwapPositionUsingHeuristic (int[][] state, AnalysedSudokuProblemDefinition analysedProblemDef, Random random ) {
		int	numConflicts;
		// random row with nofixed positions >= 2
		int	tabuCounter = 0;
		int row;
		int col1;
		int col2;
		do {
			// find random row with least 2 not fixed positions
			do {
				row = random.nextInt(N2);
			} while (analysedProblemDef.getEmptyRowColumns().getNotFixedPositionsCountForRow(row) < 2);

			col1 = analysedProblemDef.getEmptyRowColumns().getRandomNotFixedColumnInRow(row, random);
			do {
				col2 = analysedProblemDef.getEmptyRowColumns().getRandomNotFixedColumnInRow(row, random);
			} while (col1 == col2);

			// Tabu heuristics

			tabuCounter++;
			numConflicts = 0;
			// 1) ci bude cislo s pozicie1 tabu na pozicii2
			// 2) ci bude cislo s pozicie2 tabu na pozicii1
			// 3) ci je cislo s pozicie1 tabu na pozicii1
			// 4) ci je cislo s pozicie2 tabu na pozicii2



			if (analysedProblemDef.getTabuList().isTabuByValue(row, col2, state[row][col1])) {
				numConflicts += 2;
			}
			if (analysedProblemDef.getTabuList().isTabuByValue(row, col1, state[row][col2])) {
				numConflicts += 2;
			}
			if (analysedProblemDef.getTabuList().isTabuByValue(row, col1, state[row][col1])) {
				numConflicts--;
			}
			if (analysedProblemDef.getTabuList().isTabuByValue(row, col2, state[row][col2])) {
				numConflicts--;
			}
		} while ((0 < numConflicts && tabuCounter < 4)
			|| (1 < numConflicts && tabuCounter < 8));

		return new SwapPositionModel(row, col1, col2);
	}

	private static void swapValuesInRow(int[] rowValues, int col1, int col2) {
		// swap positions
		int swap = rowValues[col1];
		rowValues[col1] = rowValues[col2];
		rowValues[col2] = swap;
	}

	@Override
	public String toString ()
	{
		return "Fitness=" //
			+ AnsiColorCodes.ANSI_GREEN + fitness + AnsiColorCodes.ANSI_GREEN //
			+ " colFitness=" + Arrays.toString(colFitnessList) //
			+ " blockFitness=" + Arrays.toString(blockFitnessList) //
			+ "\n" + stateToStringBuilder();
	}

	private StringBuilder stateToStringBuilder () {
		StringBuilder b = new StringBuilder();
		for (int row = 0; row < N2; row++) {
			for (int col = 0; col < N2; col++) {
				int value = state[row][col];
				// TODO mark fixed values by colors
				b.append(value == 0 ? "." : String.valueOf(value));
				b.append(" ");
				if (col % N == 2 && col + 1 < N2) {
					b.append(" ");
				}
			}
			if (row + 1 < N2) {
				b.append(System.lineSeparator());
			}
		}

		return b;
	}
}
