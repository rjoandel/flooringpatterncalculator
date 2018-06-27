package org.easydiy.flooring;

public class ProjectParameters
{
  private int boardLength;
  private int boardWidth;
  private int roomLength;
  private int roomWidth;
  private int firstBoardLength;
  private int firstBoardWidth;
  private int expansionGap;

  public ProjectParameters(int roomLength, int roomWidth, int boardLength, int boardWidth, int firstBoardLength, int firstBoardWidth, int expansionGap)
  {
    this.boardLength = boardLength;
    this.boardWidth = boardWidth;
    this.roomLength = roomLength;
    this.roomWidth = roomWidth;
    this.firstBoardLength = firstBoardLength;
    this.firstBoardWidth = firstBoardWidth;
    this.expansionGap = expansionGap;
  }

  public int getBoardLength()
  {
    return boardLength;
  }

  public void setBoardLength(int boardLength)
  {
    this.boardLength = boardLength;
  }

  public int getBoardWidth()
  {
    return boardWidth;
  }

  public void setBoardWidth(int boardWidth)
  {
    this.boardWidth = boardWidth;
  }

  public int getRoomLength()
  {
    return roomLength;
  }

  public void setRoomLength(int roomLength)
  {
    this.roomLength = roomLength;
  }

  public int getRoomWidth()
  {
    return roomWidth;
  }

  public void setRoomWidth(int roomWidth)
  {
    this.roomWidth = roomWidth;
  }

  public int getFirstBoardLength()
  {
    return firstBoardLength;
  }

  public void setFirstBoardLength(int firstBoardLength)
  {
    this.firstBoardLength = firstBoardLength;
  }

  public int getFirstBoardWidth()
  {
    return firstBoardWidth;
  }

  public void setFirstBoardWidth(int firstBoardWidth)
  {
    this.firstBoardWidth = firstBoardWidth;
  }

  public int getExpansionGap()
  {
    return expansionGap;
  }

  public void setExpansionGap(int expansionGap)
  {
    this.expansionGap = expansionGap;
  }

  
}
