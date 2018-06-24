package org.easydiy.flooring.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.easydiy.flooring.ProjectParameters;
import org.easydiy.flooring.algorithms.NoWastePatternCalculator;
import org.easydiy.flooring.algorithms.PatternCalculator;

public class FloorCanvas extends JComponent
{

  private static final long serialVersionUID = -5947655097622587907L;

  int plankLength;
  int plankWidth;
  int roomLength;
  int roomWidth; 
  int inputlength;
  int inputwidth;
  int expansionGap;
  List<Board> pattern = new ArrayList<>();
    
  int dpi;

  private boolean printable;
  
  public FloorCanvas(ProjectParameters params)
  {
    this.roomLength = params.getRoomLength();
    this.roomWidth = params.getRoomWidth();
    this.plankLength = params.getPlankLength();
    this.plankWidth = params.getPlankWidth();
    this.expansionGap = params.getExpansionGap();
    this.inputlength = params.getFirstPlankLength();
    this.inputwidth = params.getFirstPlankWidth();
    
    this.dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
    System.out.println("screen DPI=" + dpi);
  }
  
  public int mmTopx(int mm)
  {
    if (printable)
    {
      return  (int) ((mm * 72) * 254);
    }
    else
    {
      return (int) (mm*dpi/254);      
    }
  }

  public void paintComponent(Graphics g1)
  {
    super.paintComponent(g1);
    Graphics2D g = (Graphics2D) g1;
    
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(Color.black);

    int nextplankwidth = inputwidth;
    int nextplanklength = inputlength;
    
    PatternCalculator calc = new NoWastePatternCalculator();
    pattern = calc.calculatePattern(roomLength, roomWidth, plankLength, plankWidth,expansionGap, nextplanklength, nextplankwidth);
    
    drawPattern(g, false);
    
  }

  public void drawPattern(Graphics2D g, boolean printable)
  { 
    this.printable = printable;
    for (Board board : pattern)
    {
      
      g.drawRect(mmTopx(board.x), mmTopx(board.y), mmTopx(board.width), mmTopx(board.height));
      String display = "#" + board.boardNumber +"-->" + board.width + "x" + board.height; 
      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(display);
      int h = fm.getAscent();
      g.drawString(display, mmTopx(board.x) + (mmTopx(board.width) / 2) - (mmTopx(w) / 2), mmTopx(board.y) + (mmTopx(board.height) /2) + (mmTopx(h) / 4));
    }
  }

  public Dimension getPreferredSize()
  {    
    return new Dimension(mmTopx(roomLength), mmTopx(roomWidth));
  }
}
