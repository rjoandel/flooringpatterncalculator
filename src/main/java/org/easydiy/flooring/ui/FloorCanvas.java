package org.easydiy.flooring.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JComponent;

import org.easydiy.flooring.ProjectParameters;
import org.easydiy.flooring.algorithms.NoWastePatternCalculator;
import org.easydiy.flooring.algorithms.PatternCalculator;

public class FloorCanvas extends JComponent
{

  private static final long serialVersionUID = -5947655097622587907L;

  private ProjectParameters project;
  private List<Board> pattern = new ArrayList<>();

  private int dpi;

  private Optional<Board> selectedBoard = Optional.empty();

  private int displayOffset_x = 100;
  private int displayOffset_y = 100;
  private PatternCalculator calc;

  public FloorCanvas(ProjectParameters params)
  {
    this.project = params;
    this.dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
    System.out.println("screen DPI=" + dpi);
    calc = new NoWastePatternCalculator(); //TODO should be built from a factory based on an algorithm chosen in ProjectParameters
    calculatePattern(project);
  }

  public int mmTopx(int mm)
  {
    return (int) (mm * dpi / 254);
  }
  
  public void calculatePattern(ProjectParameters project)
  {
    pattern = calc.calculatePattern(project);
  }

  public void paintComponent(Graphics g1)
  {
    super.paintComponent(g1);
    Graphics2D g = (Graphics2D) g1;

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(Color.black);

    drawPattern(g);

  }

  public void drawPattern(Graphics2D g)
  {
    for (Board board : pattern)
    {
      drawBoard(board, g);
    }
    g.translate(displayOffset_x, displayOffset_y);
  }

  public Dimension getPreferredSize()
  {
    return new Dimension(mmTopx(project.getRoomLength()), mmTopx(project.getRoomWidth()));
  }

  public ProjectParameters getProjectParameters()
  {
    return project;
  }

  public Optional<Board> getBoard(int x, int y)
  {
    Optional<Board> result = Optional.empty();
    for (Board board : pattern)
    {
      if (mmTopx((int) board.getX()) <= x && x < mmTopx((int) board.getX()) + mmTopx((int) board.getWidth()) && mmTopx((int) board.getY()) <= y
          && y < mmTopx((int) board.getY()) + mmTopx((int) board.getHeight()))
      {
        result = Optional.of(board);
        break;
      }
    }
    return result;
  }

  public void drawBoard(Board board, Graphics g)
  {

    g.setColor(selectBoardColor(board));
    g.fillRect(mmTopx(board.x), mmTopx(board.y), mmTopx(board.width), mmTopx(board.height));
    g.setColor(Color.black);
    String display = "#" + board.boardNumber + "-->" + board.width + "x" + board.height;
    FontMetrics fm = g.getFontMetrics();
    int w = fm.stringWidth(display);
    int h = fm.getAscent();
    g.drawString(display, mmTopx(board.x) + (mmTopx(board.width) / 2) - (mmTopx(w) / 2), mmTopx(board.y) + (mmTopx(board.height) / 2) + (mmTopx(h) / 4));
    g.setColor(Color.black);
    g.drawRect(mmTopx(board.x), mmTopx(board.y), mmTopx(board.width), mmTopx(board.height));
  }

  private Color selectBoardColor(Board board)
  {
    if (selectedBoard.isPresent())
    {
      return (selectedBoard.get().equals(board)) ? Color.yellow : Color.white;
    }
    else
    {
      return Color.white;
    }
  }

  public void drawBoard(Board board, Color color)
  {
    Graphics g = super.getGraphics();
    drawBoard(board, g);
  }

  public void setSelectedBoard(Optional<Board> selectedBoard)
  {
    this.selectedBoard = selectedBoard;
  }

  public Optional<Board> getSelectedBoard()
  {
    return selectedBoard;
  }
}
