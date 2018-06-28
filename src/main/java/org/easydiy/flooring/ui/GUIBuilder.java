package org.easydiy.flooring.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.easydiy.flooring.ProjectParameters;
import org.easydiy.flooring.printing.FloorboardPrinter;

public class GUIBuilder
{
  public JFrame buildGUI(ProjectParameters initialParams)
  {
    final FloorCanvas canvas = new FloorCanvas(initialParams);
    JFrame frame = new JFrame();
    final JPanel basePanel = new JPanel();
    JLabel roomLengthLabel = new JLabel("Room length");
    final JTextField roomLengthField = new JTextField(String.valueOf(initialParams.getRoomLength()), 10);
    JLabel roomWidthLabel = new JLabel("Room width");
    final JTextField roomWidthField = new JTextField(String.valueOf(initialParams.getRoomWidth()), 10);
    JLabel boardLengthLabel = new JLabel("Board length");
    final JTextField boardLengthField = new JTextField(String.valueOf(initialParams.getBoardLength()), 10);
    JLabel boardWidthLabel = new JLabel("Board width");
    final JTextField boardWidthField = new JTextField(String.valueOf(initialParams.getBoardWidth()), 10);
    JLabel firstBoardLengthFieldLabel = new JLabel("First board length");
    final JTextField firstBoardLengthField = new JTextField(String.valueOf(initialParams.getFirstBoardLength()), 10);

    JLabel firstBoardWidthFieldLabel = new JLabel("First board width");
    final JTextField firstBoardWidthField = new JTextField(String.valueOf(initialParams.getFirstBoardWidth()), 10);

    JLabel expansionGapLabel = new JLabel("expansion Gap");
    final JTextField expansionGapField = new JTextField(String.valueOf(initialParams.getExpansionGap()), 10);

    JMenuBar menuBar = createMenuBar(canvas);

    frame.setJMenuBar(menuBar);

    JPanel optionsPanel = new JPanel(new FlowLayout());
    optionsPanel.add(roomLengthLabel);
    optionsPanel.add(roomLengthField);
    optionsPanel.add(roomWidthLabel);
    optionsPanel.add(roomWidthField);
    optionsPanel.add(boardLengthLabel);
    optionsPanel.add(boardLengthField);
    optionsPanel.add(boardWidthLabel);
    optionsPanel.add(boardWidthField);
    optionsPanel.add(firstBoardLengthField);
    optionsPanel.add(firstBoardWidthFieldLabel);
    optionsPanel.add(firstBoardWidthField);
    optionsPanel.add(firstBoardLengthFieldLabel);
    optionsPanel.add(firstBoardLengthField);
    optionsPanel.add(firstBoardWidthFieldLabel);
    optionsPanel.add(firstBoardWidthField);
    optionsPanel.add(expansionGapLabel);
    optionsPanel.add(expansionGapField);
    basePanel.setLayout(new BorderLayout());
    basePanel.add(optionsPanel, BorderLayout.NORTH);
    JScrollPane panel = new JScrollPane(canvas);
    basePanel.add(panel, BorderLayout.CENTER);
    frame.add(basePanel);

    /*
     * JPanel statusPanel = new JPanel(); statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED)); frame.add(statusPanel, BorderLayout.SOUTH); statusPanel.setPreferredSize(new
     * Dimension(frame.getWidth(), 16)); statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS)); JLabel statusLabel = new JLabel("");
     * statusLabel.setHorizontalAlignment(SwingConstants.LEFT); statusPanel.add(statusLabel);
     */

    ActionListener commonListener = new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        ProjectParameters project = canvas.getProjectParameters();
        project.setRoomLength(Integer.parseInt(roomLengthField.getText()));
        project.setRoomWidth(Integer.parseInt(roomWidthField.getText()));
        project.setBoardLength(Integer.parseInt(boardLengthField.getText()));
        project.setBoardWidth(Integer.parseInt(boardWidthField.getText()));
        project.setFirstBoardLength(Integer.parseInt(firstBoardLengthField.getText()));
        project.setFirstBoardWidth(Integer.parseInt(firstBoardWidthField.getText()));
        project.setExpansionGap(Integer.parseInt(expansionGapField.getText()));
        canvas.calculatePattern(project);
        frame.pack();
        frame.setVisible(true);
        canvas.repaint();
      }
    };

    roomLengthField.addActionListener(commonListener);
    roomWidthField.addActionListener(commonListener);    
    boardLengthField.addActionListener(commonListener);
    boardWidthField.addActionListener(commonListener);
    firstBoardLengthField.addActionListener(commonListener);
    firstBoardWidthField.addActionListener(commonListener);
    expansionGapField.addActionListener(commonListener);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    canvas.setComponentPopupMenu(createBoardPopupMenu(frame, canvas));
    canvas.repaint();

    canvas.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        Optional<Board> clickedBoard = canvas.getBoard(e.getX(), e.getY());
        if (clickedBoard.isPresent())
        {
          canvas.setSelectedBoard(clickedBoard);
          canvas.repaint();
        }
      }


      public void mouseReleased(MouseEvent e)
      {
        Optional<Board> clickedBoard = canvas.getBoard(e.getX(), e.getY());
        if (clickedBoard.isPresent())
        {
          canvas.setSelectedBoard(clickedBoard);
          canvas.repaint();
        }
      }

    });

    return frame;

  }

  private JPopupMenu createBoardPopupMenu(JFrame frame, FloorCanvas canvas)
  {
    JPopupMenu menu = new JPopupMenu();
    JMenuItem replaceMenuItem = new JMenuItem("Replace...");
    
   
    JTextField field1 = new JTextField(String.valueOf(canvas.getProjectParameters().getBoardLength()));
    JTextField field2 = new JTextField(String.valueOf(canvas.getProjectParameters().getBoardWidth()));
    JPanel replaceBoardFormPanel = new JPanel(new GridLayout(0, 1));
    replaceBoardFormPanel.add(new JLabel("Board Length:"));
    replaceBoardFormPanel.add(field1);
    replaceBoardFormPanel.add(new JLabel("Board Width:"));
    replaceBoardFormPanel.add(field2);
    replaceMenuItem.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        Board selectedBoard = canvas.getSelectedBoard().get();
        
        int result = JOptionPane.showConfirmDialog(null, replaceBoardFormPanel, "Replace board " + selectedBoard.boardNumber, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);       
        if (result == JOptionPane.OK_OPTION)
        {
          selectedBoard.x = selectedBoard.x - (Integer.valueOf(field1.getText())-selectedBoard.width);
          selectedBoard.y = selectedBoard.y - (Integer.valueOf(field2.getText())-selectedBoard.height);

          selectedBoard.width=Integer.valueOf(field1.getText());
          selectedBoard.height=Integer.valueOf(field2.getText());
          canvas.repaint();
        }
          
      }
    });
    menu.add(replaceMenuItem);
    return menu;
  }

  private JMenuBar createMenuBar(final FloorCanvas canvas)
  {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menuBar.add(menu);
    JMenuItem menuItem = new JMenuItem("Print...");
    menu.add(menuItem);

    menuItem.addActionListener(new FloorboardPrinter(canvas));
    return menuBar;
  }
}
