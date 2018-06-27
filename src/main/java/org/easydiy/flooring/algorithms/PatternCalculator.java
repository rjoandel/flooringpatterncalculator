package org.easydiy.flooring.algorithms;

import java.util.List;

import org.easydiy.flooring.ProjectParameters;
import org.easydiy.flooring.ui.Board;

/*
 * Extends this interface to implement different pattern calculator algorithms 
 */
public interface PatternCalculator
{

  List<Board> calculatePattern(ProjectParameters project);

}