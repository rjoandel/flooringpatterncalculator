package org.easydiy.flooring;

import java.awt.Rectangle;

public class Board extends Rectangle
{

  private static final long serialVersionUID = 1060086834007212604L;
  int boardNumber;
  
  public Board(int boardNumber, int x, int y, int width, int height) 
  { 
    super(x,y,width,height);
    this.boardNumber = boardNumber;
  }
  
  public String toString()
  {
    return "#" + boardNumber + "," + super.toString();
  }
}
