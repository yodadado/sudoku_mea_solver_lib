package sk.study.mea.core.sudoku;

import java.util.Arrays;
import java.util.Random;

import static sk.study.mea.core.sudoku.SudokuConstants.*;

@Deprecated
public class SudokuAgentStateOld
{
	private final int[] state;
	private int fitness;
	private int[] colFitnessList;
	private int[] blockFitnessList;

	public SudokuAgentStateOld (SudokuState sudokuProblem, Random random) {
		this.state = generateState(sudokuProblem, random);
		this.colFitnessList = fitnessForColumns(this);
		this.blockFitnessList = fitnessForBlocks(this);

		this.fitness = 0;
		for (int i=0; i<N2; i++) {
			this.fitness += colFitnessList[i] + blockFitnessList[i];
		}
	}

	public int getValue(int row, int col) {
		return state[row * N2 + col];
	}


	// TODO refactor
	public static int[] generateState(SudokuState sudokuProblem, Random random) {
		int[] newState = sudokuProblem.getStateCopy();
		boolean[] usedValues = new boolean[N2];

		// for each row=(sequence)
		// i = colIndex
		for (int valueRow = 0; valueRow < N2; ++valueRow) {
			// set every ones to zero
			for (int i = 0; i < N2; ++i) {
				usedValues[i] = false;
			}

			// do pola usedValues, si poznacime, kotre cisla (valueIndexes) v riadku su uz pouzite
			for (int valueCol = 0; valueCol < N2; ++valueCol) {
				int value = sudokuProblem.getValue(valueRow, valueCol);
				if (0 != value) {
					usedValues[value - 1] = true;
				}
			}
			// postupne pre vsetky pozicie sa budu generovat cisla ktore este niesu pouzite
			int offset = valueRow * N2; // 0,9,18,27,36,45  (rowOffset)
			for (int valueCol = 0; valueCol < N2; ++valueCol) {
				if (NOT_USED == newState[offset + valueCol]) {
					int randomValueIndex;
					do {
						randomValueIndex = random.nextInt(N2);
					} while (usedValues[randomValueIndex]);
					newState[offset + valueCol] = randomValueIndex + 1;
					usedValues[randomValueIndex] = true;
				}
			}
		}

		return newState;
	}



	// fitness funcion
	// TODO rename
	// int  AgentSudoku:: fitnessFunctionAllOverState(const int *actualState, int *fitnessList) {
	public static int fitnessFunctionAllOverState(SudokuAgentStateOld state)
	{
		int fitnessSum = 0;
		int[] c= fitnessForColumns(state);
		int[] b= fitnessForBlocks(state);

		for (int i=0; i<N2; i++) {
			fitnessSum += c[i] + b[i];
		}


		return fitnessSum;
	}

	public static int[] fitnessForColumns(SudokuAgentStateOld state)
	{
		int[] fitnessList = new int[N2];
		boolean[] valueUsed = new boolean[N2];

		for (int col = 0; col < N2; col++) {
			int colFitness = 0;
			for (int i = 0; i < N2; i++) {
				valueUsed[i] = false;
			}

			for (int row = 0; row < N2; row++) {
				int value = state.getValue(row, col);
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

	public static int[]  fitnessForBlocks(SudokuAgentStateOld state)
	{
		int[] fitnessList = new int[N2];
		boolean[] valueUsed = new boolean[N2];

		for (int blockBow = 0; blockBow < N; blockBow++) {
			for (int blockCol = 0; blockCol < N; blockCol++) {
				int blockFitness = 0;
				for (int i = 0; i < N2; i++) {
					valueUsed[i] = false;
				}

				int block= blockBow * N + blockCol;
				int blockStartRow = blockBow * N;
				int blockStartCol = blockCol * N;

				for (int row = blockStartRow + N-1; row >= blockStartRow; row--) {
					for (int col = blockStartCol + N-1; col >= blockStartCol; col--) {
						int value = state.getValue(row, col);
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

	@Override public String toString ()
	{
		return new StringBuilder()
			.append("fitness=")
			//.append(ANSI_GREEN)
			.append(fitness)
			//.append(ANSI_RESET)
			.append("colFitness=")
			.append(Arrays.toString(colFitnessList))
			.append("blockFitness=")
			.append(Arrays.toString(blockFitnessList))
			.append("\n")
			.append(BaseSudokuState.toString(state))
			.toString();
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

	public static void mutationUseHeurRetFitness (
		int[] newState, int[] newFitnessListCols, int[] newFitnessListBlocks,
		SudokuEmptyRowColumns fixedList, SudokuTabuListOld tabuList, Random random
	) {

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

		do {
			// find row with least 2 not fixed positions
			int row;
			do {
				i = random.nextInt(N2);
				offset = i * N2;
				// counter = fixedList[offset + i];
				row = i;
				counter = fixedList.getNotFixedPositionsCountForRow(row);
			} while (counter < 2);

			// random 2 not the same positions
			position1 = random.nextInt(counter);
			do {
				position2 = random.nextInt(counter);
			} while (position1 == position2);

			// get positions from fixedList
			position1 = fixedList.getNotFixedPositionIndexForRow (row, position1);
			position2 = fixedList.getNotFixedPositionIndexForRow (row, position2);

			// Tabu heuristics

			tabuCounter++;
			numConflicts = 0;
			// 1) ci bude cislo s pozicie1 tabu na pozicii2
			// 2) ci bude cislo s pozicie2 tabu na pozicii1
			// 3) ci je cislo s pozicie1 tabu na pozicii1
			// 4) ci je cislo s pozicie2 tabu na pozicii2

			if (tabuList.isTabuByValue(row, position2, newState[offset + position1])) {
				numConflicts += 2;
			}
			if (tabuList.isTabuByValue(row, position1, newState[offset + position2])) {
				numConflicts += 2;
			}
			if (tabuList.isTabuByValue(row, position1, newState[offset + position1])) {
				numConflicts--;
			}
			if (tabuList.isTabuByValue(row, position2, newState[offset + position2])) {
				numConflicts--;
			}
		} while ((0 < numConflicts  && 4 > tabuCounter)
			|| (1 < numConflicts  && 8 > tabuCounter));

		// swap positions
		int swap = newState[position1 + offset];
		newState[position1 + offset] = newState[position2 + offset];
		newState[position2 + offset] = swap;

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
}
