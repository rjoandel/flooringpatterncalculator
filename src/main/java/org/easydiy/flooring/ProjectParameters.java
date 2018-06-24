package org.easydiy.flooring;

public class ProjectParameters
{
  private int plankLength;
  private int plankWidth;
  private int roomLength;
  private int roomWidth;
  private int firstPlankLength;
  private int firstPlankWidth;
  private int expansionGap;

  public ProjectParameters(int roomLength, int roomWidth, int plankLength, int plankWidth, int firstPlankLength, int firstPlankWidth, int expansionGap)
  {
    this.plankLength = plankLength;
    this.plankWidth = plankWidth;
    this.roomLength = roomLength;
    this.roomWidth = roomWidth;
    this.firstPlankLength = firstPlankLength;
    this.firstPlankWidth = firstPlankWidth;
    this.expansionGap = expansionGap;
  }

  public int getPlankLength()
  {
    return plankLength;
  }

  public void setPlankLength(int plankLength)
  {
    this.plankLength = plankLength;
  }

  public int getPlankWidth()
  {
    return plankWidth;
  }

  public void setPlankWidth(int plankWidth)
  {
    this.plankWidth = plankWidth;
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

  public int getFirstPlankLength()
  {
    return firstPlankLength;
  }

  public void setFirstPlankLength(int firstPlankLength)
  {
    this.firstPlankLength = firstPlankLength;
  }

  public int getFirstPlankWidth()
  {
    return firstPlankWidth;
  }

  public void setFirstPlankWidth(int firstPlankWidth)
  {
    this.firstPlankWidth = firstPlankWidth;
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
