package org.easydiy.flooring.algorithms;

import java.util.List;

import org.easydiy.flooring.ui.Board;

/*
 * Extends this interface to implement different pattern calculator algorithms 
 */
public interface PatternCalculator
{

  List<Board> calculatePattern(int roomLength, int roomWidth, int plankLength, int plankWidth, int expansionGap, int firstPlankLength, int firstPlankWidth);

}