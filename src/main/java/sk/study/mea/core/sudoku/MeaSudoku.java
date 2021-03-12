package sk.study.mea.core.sudoku;

import sk.study.mea.core.Mea;
import sk.study.mea.core.MeaConfiguration;

/**
 * This is implemetation of {@link Mea} for solving Sudoku problem.
 *
 * @author David Durcak
 */
public class MeaSudoku extends Mea<SudokuState>
{
	public MeaSudoku (MeaConfiguration cfg, SudokuState problemDefinition)
	{
		super(cfg, problemDefinition);
	}
}
