package sk.study.mea.core.sudoku;

import sk.study.mea.core.AnsiColorCodes;

import java.util.*;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

public class SudokuAgentState
{
	private final int[][] state;
	private int fitness;
	private int[] colFitnessList;
	private int[] blockFitnessList;

	public SudokuAgentState (AnalysedSudokuProblemDefinition analysedProblemDef, Random random) {
		this.state = generateState(analysedProblemDef, random);
		this.colFitnessList = fitnessForColumns(state);
		this.blockFitnessList = fitnessForBlocks(state);
		this.fitness = sumColumnsAndBlockFitness(colFitnessList, blockFitnessList);
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

			// TODO remove
//			//////////////// OLD:
//		boolean[] usedValues = new boolean[N2];
			//			// set every ones to zero
//			for (int i = 0; i < N2; ++i) {
//				usedValues[i] = false;
//			}
//
//			// do pola usedValues, si poznacime, kotre cisla (valueIndexes) v riadku su uz pouzite
//			for (int col = 0; col < N2; ++col) {
//				Optional<Integer> value = problemDefinition.getValue(row, col);
//				if (value.isPresent()) {
//					usedValues[value.get() - 1] = true;
//				}
//			}
//			// postupne pre vsetky pozicie sa budu generovat cisla ktore este niesu pouzite
//			for (int col = 0; col < N2; ++col) {
//				if (NOT_USED == newState[row][col]) {
//					int randomValueIndex;
//					do {
//						randomValueIndex = random.nextInt(N2);
//					} while (usedValues[randomValueIndex]);
//					newState[row][col] = randomValueIndex + 1;
//					usedValues[randomValueIndex] = true;
//				}
//			}
		}

		return newState;
	}

	private static int[] fitnessForColumns(int[][] state) {
		int[] fitnessList = new int[N2];
		boolean[] valueUsed = new boolean[N2];

		for (int col = 0; col < N2; col++) {
			int colFitness = 0;
			for (int i = 0; i < N2; i++) {
				valueUsed[i] = false;
			}

			for (int row = 0; row < N2; row++) {
				int value = state[row][col];
				if (valueUsed[value - 1]) {
					colFitness++;
				} else {
					valueUsed[value - 1] = true;
				}
			}
			fitnessList[col] = colFitness;
		}

		return fitnessList;
	}

	private static int[] fitnessForBlocks(int[][] state) {
		int[] fitnessList = new int[N2];
		boolean[] valueUsed = new boolean[N2];

		for (int blockBow = 0; blockBow < N; blockBow++) {
			for (int blockCol = 0; blockCol < N; blockCol++) {
				int blockFitness = 0;
				for (int i = 0; i < N2; i++) {
					valueUsed[i] = false;
				}

				int block = blockBow * N + blockCol;
				int blockStartRow = blockBow * N;
				int blockStartCol = blockCol * N;

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
				fitnessList[block] = blockFitness;
			}
		}

		return fitnessList;
	}

	private static int sumColumnsAndBlockFitness(int[] colFitnessList, int[] blockFitnessList) {
		int fitnessSum = 0;
		for (int i = 0; i < N2; i++) {
			fitnessSum += colFitnessList[i] + blockFitnessList[i];
		}

		return fitnessSum;
	}

	@Override public String toString ()
	{
		return new StringBuilder()
			.append("Fitness=")
			.append(AnsiColorCodes.ANSI_GREEN)
			.append(fitness)
			.append(AnsiColorCodes.ANSI_GREEN)
			.append(" colFitness=")
			.append(Arrays.toString(colFitnessList))
			.append(" blockFitness=")
			.append(Arrays.toString(blockFitnessList))
			.append("\n")
			.append(stateToStringBuilder())
			.toString();
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

	// mutation + heuristic + fast fitness function (via change)
	//int	 AgentSudoku:: mutationUseHeurRetFitness(int *newState, const int *newFitnessList) const {
	// mutation  MUT-5  only 1 row WITHOUT probability using fixedList, new fitness

	public static void mutationUseHeurRetFitnessFrom (int[] state, int[] fitnessListCols, int[] fitnessListBlocks )
	{

		// TODO
//		mutationUseHeurRetFitness (
//			Arrays.copyOf(state, state.length),
//			Arrays.copyOf(fitnessListCols, fitnessListCols.length),
//			Arrays.copyOf(fitnessListBlocks, fitnessListBlocks.length));
	}

	public static void mutateUseHeurRetFitness ( SudokuAgentState agentState, AnalysedSudokuProblemDefinition analysedProblemDef, Random random ) {
		int[][] state = agentState.state;
		int[] fitnessListCols = agentState.colFitnessList; // TODO rename
		int[] fitnessListBlocks = agentState.blockFitnessList; // TODO rename

		//		memcpy(newState, currentState, N4 * sizeof(int));
		//		memcpy((void *)newFitnessList, currentFitnessList, 2*N2*sizeof(int));

		int i;
		int	position1;
		int	position2;
		int	offset;
		int	counter;
		int	numConflicts;
		// random row with nofixed positions >= 2
		int	tabuCounter = 0;

		boolean[] existArray = new boolean[N2];

//		{ ///////////// TODO remove brackets
//			int row;
//			do {
//				// find row with least 2 not fixed positions
//				do {
//					i = random.nextInt(N2);
//					offset = i * N2;
//					// counter = fixedList[offset + i];
//					row = i;
//					counter = analysedProblemDef.getEmptyRowColumns().getNotFixedPositionsCountForRow(row);
//				} while (counter < 2);
//
//				// random 2 not the same positions
//				position1 = random.nextInt(counter);
//				do {
//					position2 = random.nextInt(counter);
//				} while (position1 == position2);
//
//				// get positions from fixedList
//				position1 = analysedProblemDef.getEmptyRowColumns().getNotFixedPositionIndexForRow (row, position1);
//				position2 = analysedProblemDef.getEmptyRowColumns().getNotFixedPositionIndexForRow (row, position2);
//
//				// Tabu heuristics
//
//				tabuCounter++;
//				numConflicts = 0;
//				// 1) ci bude cislo s pozicie1 tabu na pozicii2
//				// 2) ci bude cislo s pozicie2 tabu na pozicii1
//				// 3) ci je cislo s pozicie1 tabu na pozicii1
//				// 4) ci je cislo s pozicie2 tabu na pozicii2
//
//
//
//				if (analysedProblemDef.getTabuList().isTabuByValue(row, position2, state[row][position1])) {
//					numConflicts += 2;
//				}
//				if (analysedProblemDef.getTabuList().isTabuByValue(row, position1, state[row][position2])) {
//					numConflicts += 2;
//				}
//				if (analysedProblemDef.getTabuList().isTabuByValue(row, position1, state[row][position1])) {
//					numConflicts--;
//				}
//				if (analysedProblemDef.getTabuList().isTabuByValue(row, position2, state[row][position2])) {
//					numConflicts--;
//				}
//			} while ((0 < numConflicts  && 4 > tabuCounter)
//				|| (1 < numConflicts  && 8 > tabuCounter));
//
//			// swap positions
//			swapValuesInRow(state[row], position1, position2);
//
//
//
//
//		}




		////////////////

		int row; // TODO == 1
		int col1; // TODO == position1
		int col2; // TODO == position2
		do {
			// find random row with least 2 not fixed positions
			do {
				row = random.nextInt(N2);
				// i = row; // TODO
				// offset = row * N2;
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

		// swap positions
//		int swap = state[row][position1];
//		state[row][position1] = state[row][position2];
//		state[row][position2] = swap;
		swapValuesInRow(state[row], col1, col2);

		/////////////////////
		
		/*

		//  Fitness , evaluate only changes------------------------------------------
		int fc1, fc2, fb1, fb2, fit, k, j1, j2, c, pos, offs;

		if(3 > i) k = 0;
		else if(5 < i) k = 2;
		else k = 1;

		if(3 > position1) j1 = 0;
		else if(5 < position1) j1 = 2;
		else j1 = 1;

		if(3 > position2) j2 = 0;
		else if(5 < position2) j2 = 2;
		else j2 = 1;

		// column 1
		fit = 0;
		for(c = 0; c < N2; ++c)
			existArray[c] = NOT_USED;
		pos = position1;
		for(c = 0; c < N2; ++c){
			if(existArray[ newState[pos] - 1 ])
				++fit;
			else
				existArray[ newState[pos] - 1 ] = USED;
			pos += N2;
		}
		fc1 = fit - mutFitnessList[position1];
		mutFitnessList[position1] += fc1; ;

		// column 2
		fit = 0;
		for(c = 0; c < N2; ++c)
			existArray[c] = NOT_USED;
		pos = position2;
		for(c = 0; c < N2; ++c){
			if(existArray[ newState[pos] - 1 ])
				++fit;
			else
				existArray[ newState[pos] - 1 ] = USED;
			pos += N2;
		}
		fc2 = fit - mutFitnessList[position2];
		mutFitnessList[position2] += fc2; ;


		// block1
		offs = k * N2 * N  + j1 * N;
		fit = 0;
		for(c = 0; c < N2; ++c)
			existArray[c] = NOT_USED;

		for(c = 0; c < N2; ++c){
			if(c == N)
				offs = offs + N + N;
			if(c == N + N)
				offs = offs + N + N;
			pos = c  + offs;

			if(existArray[ newState[pos] - 1 ])
				++fit;
			else
				existArray[ newState[pos] - 1 ] = USED;
		}
		fb1 = fit - mutFitnessList[k*3 + j1 + N2];
		mutFitnessList[k*3 + j1 + N2] += fb1; ;

		// block2
		fb2 = 0;
		if(j1 != j2 ){
			offs = k * N2 * N  + j2 * N;
			fit = 0;

			for(c = 0; c < N2; ++c)
				existArray[c] = NOT_USED;

			for(c = 0; c < N2; ++c){
				if(c == N)
					offs = offs + N + N;
				if(c == N + N)
					offs = offs + N + N;
				pos = c  + offs;

				if(existArray[ newState[pos] - 1 ])
					++fit;
				else
					existArray[ newState[pos] - 1 ] = USED;
			}
			fb2 = fit - mutFitnessList[k*3 + j2 + N2];
			mutFitnessList[k*3 + j2 + N2] += fb2;
		}

		// fitness
		return currentFitness + fc1 + fc2 + fb1 + fb2;

	}

		 */

	}

	private static final void swapValuesInRow(int rowValues[], int col1, int col2) {
		// swap positions
		int swap = rowValues[col1];
		rowValues[col1] = rowValues[col2];
		rowValues[col2] = swap;
	}
}
