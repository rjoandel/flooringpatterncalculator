package org.easydiy.flooring;

import javax.swing.SwingUtilities;

import org.easydiy.flooring.ui.GUIBuilder;

public class App
{

  static int default_boardLength = 1380;
  static int default_boardWidth = 156;
  static int default_roomLength = 3090;
  static int default_roomWidth = 2450;
  static int default_expansionGap = 10;

  public App()
  {
    ProjectParameters defaultParams = new ProjectParameters(default_roomLength, default_roomWidth, default_boardLength, default_boardWidth, default_boardLength, default_boardWidth, default_expansionGap);
    GUIBuilder builder = new GUIBuilder();
    builder.buildGUI(defaultParams);
  }

  public static void main(String[] args)
  { 
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
          new App();
      }
  });
  }

}
