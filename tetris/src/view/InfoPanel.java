package view;

import model.Board;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static model.PropertyChangeEnabledBoardControls.*;

public class InfoPanel extends JPanel implements PropertyChangeListener {

    private static final int FONT_SIZE = 15;

    private static final int SPECIAL_FONT_SIZE = 10;

    private static final int RECTANGLE_WIDTH = 100;

    private static final int RECTANGLE_HEIGHT = 55;

    private final Board myBoard;

    private int myScore;

    private JLabel myScoreLabel;

    private int myLinesCleared;

    private int myLinesClearedCounter;

    private JLabel myLinesClearedLabel;

    private int myCurrentLevel;

    private JLabel myCurrentLevelLabel;

    private JLabel myNextLevelLabel;

    private TetrisGame myTG;

    private List<Integer> myCheckedRows = new ArrayList<>();

    private boolean gameRunning;

    public InfoPanel(final Board theBoard, final TetrisGame theTG) {
        super();

        myBoard = theBoard;
        myBoard.addPropertyChangeListener(this);

        myTG = theTG;

        createVariables();

        createLabels();

        setPanel();

    }

    private void setPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Using FlowLayout
        setPreferredSize(new Dimension(RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
        setBackground(new Color(50, 150, 255));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        add(myScoreLabel);
        add(myLinesClearedLabel);
        add(myCurrentLevelLabel);
        add(myNextLevelLabel);
    }

    private void createVariables() {
        myScore = 0;
        myLinesCleared = 0;
        myLinesClearedCounter = 0;
        myCurrentLevel = 0;
    }

    private void createLabels() {
        myScoreLabel = createStyledLabel("Score: " + myScore);
        myLinesClearedLabel = createStyledLabel("Lines Cleared: " + myLinesClearedCounter);
        myCurrentLevelLabel = createStyledLabel("Current Level: " + level());
        myNextLevelLabel = createSpecialStyledLabel("Clear " + howManyUntilNextLevel() + " lines until next level");
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel createSpecialStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, SPECIAL_FONT_SIZE));
        label.setForeground(Color.WHITE);
        return label;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (NEW_GAME.equals(evt.getPropertyName())) {
            myScore = 0;
        }

        if (FROZEN_BLOCKS.equals(evt.getPropertyName())) {
            if (myTG.getTimerRunning()) {
                myScore += 4; // Updated from ADD_POINTS
            }
        }

        if (CHECK_ROW.equals(evt.getPropertyName())) {
            if (myTG.getTimerRunning()) {
                myLinesCleared += 1;
                myLinesClearedCounter += 1;
            }
        }

        updateLabels();
        myLinesCleared = 0;
    }

    private void updateLabels() {
        myScoreLabel.setText("Score: " + myScore);
        myLinesClearedLabel.setText("Lines Cleared: " + myLinesClearedCounter);
        myCurrentLevelLabel.setText("Current Level: " + level());
        myNextLevelLabel.setText("Clear " + howManyUntilNextLevel() + " until next level");
    }

    private int howManyUntilNextLevel() {
        int i = 0;
        if (myLinesClearedCounter % 5 == 0) {
            i = 5;
        } else {
            i = 5 - (myLinesClearedCounter % 5);
        }
        return i;
    }

    private int level() {
        return ((myLinesClearedCounter / 5) + 1);
    }
}
