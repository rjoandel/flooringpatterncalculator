package org.easydiy.flooring;

import org.easydiy.flooring.ui.GUIBuilder;

public class App
{

  int default_plankLength = 1380;
  int default_plankWidth = 156;
  int default_roomLength = 3090;
  int default_roomWidth = 2450;
  int default_expansionGap = 10;

  public App()
  {
    ProjectParameters defaultParams = new ProjectParameters(default_roomLength, default_roomWidth, default_plankLength, default_plankWidth, default_plankLength, default_plankWidth, default_expansionGap);
    GUIBuilder builder = new GUIBuilder();
    builder.buildGUI(defaultParams);
  }

  public static void main(String[] args)
  {
    App app = new App();
  }

}
