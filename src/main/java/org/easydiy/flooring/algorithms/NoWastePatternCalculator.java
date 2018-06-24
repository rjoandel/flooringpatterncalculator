package org.easydiy.flooring.algorithms;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.easydiy.flooring.ui.Board;

/**
 * Standard implementation of the PatternCalculator, generates a pattern which minimises waste by keeping cuts to the last plank of a row
 * and reuse the other piece for the start of the next row 
 */
public class NoWastePatternCalculator implements PatternCalculator
{
 
  int roomLength;
  int roomWidth; 
  int plankLength;
  int plankWidth;
  int expansionGap;
  int firstPlankLength;
  int firstPlankWidth;
  int plankNumber;
  List<Board> pattern = new ArrayList<>();
  
  @Override
  public List<Board> calculatePattern(int roomLength, int roomWidth, int plankLength, int plankWidth, int expansionGap, int firstPlankLength, int firstPlankWidth)
  {
    
    this.roomLength = roomLength;
    this.roomWidth = roomWidth; 
    this.plankLength = plankLength;
    this.plankWidth = plankWidth;
    this.expansionGap = expansionGap;
    this.firstPlankLength = firstPlankLength;
    this.firstPlankWidth = firstPlankWidth;
    
    plankNumber = 1;
    int nextplankwidth = firstPlankWidth;
    int nextplanklength = firstPlankLength;
    
    int numberOfRows = (int) Math.ceil((double)(roomWidth - (expansionGap*2)) / plankWidth);    
    System.out.println("Number of rows required = " + numberOfRows);
    
    //first row, can vary length and width;
    int rowIndex = 1;
    nextplanklength = doRow(rowIndex, nextplanklength,nextplankwidth);
    
    //middle rows, full size width
    nextplankwidth=plankWidth;
    for  (rowIndex = 2; rowIndex < numberOfRows; rowIndex++)
    {
      nextplanklength = doRow(rowIndex, nextplanklength,nextplankwidth);
    }
    //last row, calculate width from space left over
    nextplankwidth = Math.min(plankWidth, roomWidth-(expansionGap*2)-((rowIndex-1)*plankWidth));
    doRow(rowIndex, nextplanklength, nextplankwidth);
    
    System.out.println(pattern);
    return pattern;
  }
  
  private int doRow(int rowNumber, int firstPlankLength, int firstPlankWidth)
  { 
    System.out.print("Row " + rowNumber + " [");
    int x = 0 + expansionGap;
    int y = plankWidth*(rowNumber-1)+expansionGap;
    Point nextPlankPosition = putPlank(x, y, firstPlankLength, firstPlankWidth);
    plankNumber++;
    int nextRowsFirstPlankLength = plankLength;
    while(nextPlankPosition.x < roomLength-expansionGap)
    {
      x = nextPlankPosition.x;
      boolean doNotCountCutPlankTwice = false;
      if (roomLength-expansionGap-nextPlankPosition.x < plankLength)
      {
        nextRowsFirstPlankLength = plankLength - (roomLength-expansionGap-nextPlankPosition.x);
        doNotCountCutPlankTwice = true;
      }
      nextPlankPosition = putPlank(x, y, Math.min(plankLength, roomLength-expansionGap-nextPlankPosition.x),firstPlankWidth);
      if (!doNotCountCutPlankTwice)
      {
        plankNumber++;
      }
    }
    System.out.println("]");
    return nextRowsFirstPlankLength;
    
  }

  private Point putPlank(int x, int y, int length, int width)
  { 
    System.out.print(" (Plank #" + plankNumber + " at [" + x + "," + y + "], length="+length + ",width=" + width + ")");
    pattern.add(new Board(plankNumber,x,y, length, width));
    Point nextstartingPoint = new Point(x+length, y);
    return nextstartingPoint;
  }
}
