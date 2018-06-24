package org.easydiy.flooring.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.easydiy.flooring.ProjectParameters;
import org.easydiy.flooring.printing.FloorboardPrinter;

public class GUIBuilder
{
  public JFrame buildGUI(ProjectParameters params)
  {
    final FloorCanvas canvas = new FloorCanvas(params);
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

    menuItem.addActionListener(new FloorboardPrinter(canvas));

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
    return frame;
  }
}
