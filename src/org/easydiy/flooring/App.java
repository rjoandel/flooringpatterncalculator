package org.easydiy.flooring;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class App
{

  int default_plankLength = 1380;
  int default_plankWidth = 156;
  int default_roomLength = 3090;
  int default_roomWidth = 2450;
  int default_expansionGap = 10;

  public App()
  {

    final FloorCanvas canvas = new FloorCanvas(default_roomLength, default_roomWidth, default_plankLength, default_plankWidth, default_expansionGap);
    //System.out.println("Canvas size in px=" + canvas.getPreferredSize());
    JFrame frame = new JFrame();
    final JPanel basePanel = new JPanel();
    JLabel roomLengthLabel = new JLabel("Room length");
    final JTextField roomLengthField = new JTextField(String.valueOf(canvas.roomLength), 10);
    JLabel roomWidthLabel = new JLabel("Room width");
    final JTextField roomWidthField = new JTextField(String.valueOf(canvas.roomWidth), 10);
    JLabel firstPlankLengthFieldLabel = new JLabel("First plank length");
    final JTextField firstPlankLengthField = new JTextField(String.valueOf(canvas.inputlength), 10);

    JLabel firstPlankWidthFieldLabel = new JLabel("First plank width");
    final JTextField firstPlankWidthField = new JTextField(String.valueOf(canvas.inputwidth), 10);

    JLabel expansionGapLabel = new JLabel("expansionGap");
    final JTextField expansionGapField = new JTextField(String.valueOf(canvas.expansionGap), 10);

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menuBar.add(menu);
    JMenuItem menuItem = new JMenuItem("Print...");
    menu.add(menuItem);

    menuItem.addActionListener(this.new FloorboardPrinter(canvas));

    frame.setJMenuBar(menuBar);

    JPanel optionsPanel = new JPanel(new FlowLayout());
    optionsPanel.add(roomLengthLabel);
    optionsPanel.add(roomLengthField);
    optionsPanel.add(roomWidthLabel);
    optionsPanel.add(roomWidthField);
    optionsPanel.add(firstPlankLengthFieldLabel);
    optionsPanel.add(firstPlankLengthField);
    optionsPanel.add(firstPlankWidthFieldLabel);
    optionsPanel.add(firstPlankWidthField);
    optionsPanel.add(expansionGapLabel);
    optionsPanel.add(expansionGapField);
    basePanel.setLayout(new BorderLayout());
    basePanel.add(optionsPanel, BorderLayout.NORTH);
    JScrollPane panel = new JScrollPane(canvas);
    basePanel.add(panel, BorderLayout.CENTER);
    frame.add(basePanel);

    ActionListener commonListener = new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        canvas.inputlength = Integer.parseInt(firstPlankLengthField.getText());
        canvas.inputwidth = Integer.parseInt(firstPlankWidthField.getText());
        canvas.roomLength = Integer.parseInt(roomLengthField.getText());
        canvas.roomWidth = Integer.parseInt(roomWidthField.getText());
        canvas.expansionGap = Integer.parseInt(expansionGapField.getText());
        basePanel.repaint();

      }

    };

    firstPlankLengthField.addActionListener(commonListener);
    firstPlankWidthField.addActionListener(commonListener);
    roomLengthField.addActionListener(commonListener);
    roomWidthField.addActionListener(commonListener);
    expansionGapField.addActionListener(commonListener);

    //frame.setPreferredSize(new Dimension(1000,500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    App app = new App();
  }

  public class FloorboardPrinter implements Printable, ActionListener
  {

    FloorCanvas comp;

    public FloorboardPrinter(FloorCanvas canvasToPrint)
    {
      this.comp = canvasToPrint;
    }

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException
    {

      if (page > 0)
      { /* We have only one page, and 'page' is zero-based */
        return NO_SUCH_PAGE;
      }

      //        /* User (0,0) is typically outside the imageable area, so we must
      //         * translate by the X and Y values in the PageFormat to avoid clipping
      //         */
      //        Graphics2D g2d = (Graphics2D)g; 
      //        g2d.translate(pf.getImageableX(), pf.getImageableY());
      //      
      //        theCanvas.drawPattern(g2d, false);

      // Get the preferred size ofthe component...
      Dimension compSize = comp.getPreferredSize();
      // Make sure we size to the preferred size
      comp.setSize(compSize);
      // Get the the print size
      Dimension printSize = new Dimension();
      printSize.setSize(pf.getImageableWidth(), pf.getImageableHeight());

      // Calculate the scale factor
      double scaleFactor = getScaleFactorToFit(compSize, printSize);
      // Don't want to scale up, only want to scale down
      if (scaleFactor > 1d)
      {
        scaleFactor = 1d;
      }

      // Calculate the scaled size...
      double scaleWidth = compSize.width * scaleFactor;
      double scaleHeight = compSize.height * scaleFactor;

      // Create a clone of the graphics context.  This allows us to manipulate
      // the graphics context without begin worried about what effects
      // it might have once we're finished
      Graphics2D g2 = (Graphics2D) g.create();
      // Calculate the x/y position of the component, this will center
      // the result on the page if it can
      double x = ((pf.getImageableWidth() - scaleWidth) / 2d) + pf.getImageableX();
      double y = ((pf.getImageableHeight() - scaleHeight) / 2d) + pf.getImageableY();
      // Create a new AffineTransformation
      AffineTransform at = new AffineTransform();
      // Translate the offset to out "center" of page
      at.translate(x, y);
      // Set the scaling
      at.scale(scaleFactor, scaleFactor);
      // Apply the transformation
      g2.transform(at);
      // Print the component
      comp.printAll(g2);
      // Dispose of the graphics context, freeing up memory and discarding
      // our changes
      g2.dispose();

      comp.revalidate();

      /* tell the caller that this page is part of the printed document */
      return PAGE_EXISTS;
    }

    public double getScaleFactorToFit(Dimension original, Dimension toFit)
    {
      double dScale = 1d;

      if (original != null && toFit != null)
      {

        double dScaleWidth = getScaleFactor(original.width, toFit.width);
        double dScaleHeight = getScaleFactor(original.height, toFit.height);

        dScale = Math.min(dScaleHeight, dScaleWidth);

      }

      return dScale;

    }

    public double getScaleFactor(int iMasterSize, int iTargetSize)
    {

      double dScale = 1;
      if (iMasterSize > iTargetSize)
      {

        dScale = (double) iTargetSize / (double) iMasterSize;

      }
      else
      {

        dScale = (double) iTargetSize / (double) iMasterSize;

      }

      return dScale;

    }

    public void actionPerformed(ActionEvent e)
    {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(this);
      boolean ok = job.printDialog();
      if (ok)
      {
        try
        {
          job.print();
        }
        catch (PrinterException ex)
        {
          ex.printStackTrace();
        }
      }
    }

  }

}
