package org.easydiy.flooring.algorithms;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.easydiy.flooring.ProjectParameters;
import org.easydiy.flooring.ui.Board;

/**
 * Standard implementation of the PatternCalculator, generates a pattern which minimises waste by keeping cuts to the last Board of a row
 * and reuse the other piece for the start of the next row 
 */
public class NoWastePatternCalculator implements PatternCalculator
{
 
  private ProjectParameters project;
  private int boardNumber;
  private List<Board> pattern = new ArrayList<>();
  
  @Override
  public List<Board> calculatePattern(ProjectParameters pp)
  {
    this.project = pp;
    boardNumber = 1;
    int nextBoardwidth = project.getFirstBoardWidth();
    int nextBoardlength = project.getFirstBoardLength();
    
    int numberOfRows = (int) Math.ceil((double)(project.getRoomWidth() - (project.getExpansionGap()*2)) / project.getBoardWidth());    
    System.out.println("Number of rows required = " + numberOfRows);
    
    //first row, can vary length and width;
    int rowIndex = 1;
    nextBoardlength = doRow(rowIndex, nextBoardlength,nextBoardwidth);
    
    //middle rows, full size width
    nextBoardwidth=project.getBoardWidth();
    for  (rowIndex = 2; rowIndex < numberOfRows; rowIndex++)
    {
      nextBoardlength = doRow(rowIndex, nextBoardlength,nextBoardwidth);
    }
    //last row, calculate width from space left over
    nextBoardwidth = Math.min(project.getBoardWidth(), project.getRoomWidth()-(project.getExpansionGap()*2)-((rowIndex-1)*project.getBoardWidth()));
    doRow(rowIndex, nextBoardlength, nextBoardwidth);
    
    System.out.println(pattern);
    return pattern;
  }
  
  private int doRow(int rowNumber, int firstBoardLength, int firstBoardWidth)
  { 
    System.out.print("Row " + rowNumber + " [");
    int x = 0 + project.getExpansionGap();
    int y = project.getBoardWidth()*(rowNumber-1)+project.getExpansionGap();
    Point nextBoardPosition = putBoard(x, y, firstBoardLength, firstBoardWidth);
    boardNumber++;
    int nextRowsFirstBoardLength = project.getBoardLength();
    while(nextBoardPosition.x < project.getRoomLength()-project.getExpansionGap())
    {
      x = nextBoardPosition.x;
      boolean doNotCountCutBoardTwice = false;
      if (project.getRoomLength()-project.getExpansionGap()-nextBoardPosition.x < project.getBoardLength())
      {
        nextRowsFirstBoardLength = project.getBoardLength() - (project.getRoomLength()-project.getExpansionGap()-nextBoardPosition.x);
        doNotCountCutBoardTwice = true;
      }
      nextBoardPosition = putBoard(x, y, Math.min(project.getBoardLength(), project.getRoomLength()-project.getExpansionGap()-nextBoardPosition.x),firstBoardWidth);
      if (!doNotCountCutBoardTwice)
      {
        boardNumber++;
      }
    }
    System.out.println("]");
    return nextRowsFirstBoardLength;
    
  }

  private Point putBoard(int x, int y, int length, int width)
  { 
    System.out.print(" (Board #" + boardNumber + " at [" + x + "," + y + "], length="+length + ",width=" + width + ")");
    pattern.add(new Board(boardNumber,x,y, length, width));
    Point nextstartingPoint = new Point(x+length, y);
    return nextstartingPoint;
  }


}
