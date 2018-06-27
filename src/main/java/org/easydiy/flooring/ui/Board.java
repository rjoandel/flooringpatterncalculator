package org.easydiy.flooring.ui;

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

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + boardNumber;
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    if (boardNumber != other.boardNumber)
      return false;
    return true;
  }
  
  
}
