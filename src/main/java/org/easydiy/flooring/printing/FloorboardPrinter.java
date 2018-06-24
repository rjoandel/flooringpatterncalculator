package org.easydiy.flooring.printing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.easydiy.flooring.ui.FloorCanvas;

public class FloorboardPrinter implements Printable, ActionListener
{

  private FloorCanvas comp;

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

    // Get the preferred size of the component...
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
    // Calculate the x/y position of the component, this will centre
    // the result on the page if it can
    double x = ((pf.getImageableWidth() - scaleWidth) / 2d) + pf.getImageableX();
    double y = ((pf.getImageableHeight() - scaleHeight) / 2d) + pf.getImageableY();
    // Create a new AffineTransformation
    AffineTransform at = new AffineTransform();
    // Translate the offset to out "centre" of page
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