package sk.study.mea.core.sudoku;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SwapPositionModel
{
	private final int row;
	private final int col1;
	private final int col2;
}
