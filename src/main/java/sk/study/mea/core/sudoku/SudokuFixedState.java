package sk.study.mea.core.sudoku;

public class SudokuFixedState extends SudokuState
{
	private final int[] colNoFixedPosCount;

	public SudokuFixedState (SudokuState sourceState)
	{
		super(sourceState);
		colNoFixedPosCount = new int[SudokuConstants.N2];
	}


	//===================================================
	// initialization of fixedLists
	// its matrix NN rows x (NN+1) columns ,  1. in column is count non fixed positions in row
	private void init () {

//		offset = 0;
//		for(i = 0; i < N2; i++){
//			counter = 0;
//			k = offset + i;   // = i * (NN + 1);
//
//			for(j = 0; j < N2; j++){
//				if(0 == fixedState[offset + j]){
//					k++;
//					fixedLists[k] = j;
//					counter++;
//				}
//			}
//			fixedLists[offset + i] = counter;
//			offset += N2;

	}

}
