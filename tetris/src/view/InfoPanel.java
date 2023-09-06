package view;

import model.Board;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static model.PropertyChangeEnabledBoardControls.*;

public class InfoPanel extends JPanel implements PropertyChangeListener {

    /**
     * Points to be Added when a block freezes.
     */
    private static final int ADD_POINTS = 4;

    /**
     * Font size for JLabels.
     */
    private static final int FONT_SIZE = 20;

    /**
     * Font size for JLabels when string is too long.
     */
    private static final int SPECIAL_FONT_SIZE = 13;

    /**
     * Clearing 1 line.
     */
    private static final int ONE_LINE_CLEARED = 1;

    /**
     * Points for clearing 1 line.
     */
    private static final int ONE_LINE_POINTS = 40;

    /**
     * Clearing 2 lines.
     */
    private static final int TWO_LINE_CLEARED = 2;

    /**
     * Points for clearing 2 line.
     */
    private static final int TWO_LINE_POINTS = 100;

    /**
     * Clearing 3 lines.
     */
    private static final int THREE_LINE_CLEARED = 3;

    /**
     * Points for clearing 3 line.
     */
    private static final int THREE_LINE_POINTS = 300;

    /**
     * Clearing 4 lines.
     */
    private static final int FOUR_LINE_CLEARED = 4;

    /**
     * Points for clearing 4 line.
     */
    private static final int FOUR_LINE_POINTS = 1200;



    /**
     * The width for the rectangle.
     */
    private static final int RECTANGLE_WIDTH = 54;

    /**
     * The height for the rectangle.
     */
    private static final int RECTANGLE_HEIGHT = 54;

    /**
     * The Board class we're referencing.
     */
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
        setLayout(new GridLayout(0, 1));
        add(myScoreLabel);
        add(myLinesClearedLabel);
        add(myCurrentLevelLabel);
        add(myNextLevelLabel);

        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(myBoard.getWidth()/2 * RECTANGLE_WIDTH,
                myBoard.getHeight()/4 * RECTANGLE_HEIGHT));

        playMusic();
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    private void createVariables() {
        myScore = 0;
        myLinesCleared = 0;
        myLinesClearedCounter = 0;
        myCurrentLevel = 0;
    }


    private void createLabels() {
        myScoreLabel = new JLabel("Score: " + myScore);
        myScoreLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));

        myLinesClearedLabel = new JLabel("\nLines Cleared: " + myLinesClearedCounter);
        myLinesClearedLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));

        myCurrentLevelLabel = new JLabel("\nCurrent Level: " + level());
        myCurrentLevelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));

        myNextLevelLabel = new JLabel("\nClear " + howManyUntilNextLevel()
                + " lines until next level");
        myNextLevelLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, SPECIAL_FONT_SIZE));
    }


    public void propertyChange(PropertyChangeEvent evt) {

        if (NEW_GAME.equals(evt.getPropertyName())) {
            myScore = 0;

        }

        if (FROZEN_BLOCKS.equals(evt.getPropertyName())) {
            if (myTG.getTimerRunning()) {
                myScore += ADD_POINTS;
            }
        }

        if (CHECK_ROW.equals(evt.getPropertyName())) {
            if (myTG.getTimerRunning()) {
                myLinesCleared += 1;
                myLinesClearedCounter += 1;
                System.out.println();
                //System.out.println(myCheckedRows.get(0));

                //system.out.println("You have completed a row");
            }
        }

//        if (END_GAME.equals(evt.getPropertyName())) {
//            myScore -= 4;
//        }
        updateLabels();
        System.out.println("You have completed this many");
        System.out.println(myLinesCleared);
        myLinesCleared = 0;

    }

    private void updateLabels() {

        if (myLinesCleared == ONE_LINE_CLEARED) {
            myScore += ONE_LINE_POINTS * level();
        } else if (myLinesCleared == TWO_LINE_CLEARED) {
            myScore += TWO_LINE_POINTS * level();
        } else if (myLinesCleared == THREE_LINE_CLEARED) {
            myScore += THREE_LINE_POINTS * level();
        } else if (myLinesCleared == FOUR_LINE_CLEARED) {
            myScore += FOUR_LINE_POINTS * level();
        }



        myScoreLabel.setText("Score: " + myScore);
        myLinesClearedLabel.setText("\nLines Cleared: " + myLinesClearedCounter);
        myCurrentLevelLabel.setText("\nCurrent Level: " + level());
        myNextLevelLabel.setText("\nClear " + howManyUntilNextLevel() + " until next level");

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
        return ((myLinesClearedCounter/5) + 1);
    }

        public void playMusic() {
            try {
                AudioInputStream soundIn = AudioSystem.getAudioInputStream(new FileInputStream("Tetris.mp3"));
                AudioFormat format = soundIn.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(soundIn);
                clip.start();
                sleep(clip.getMicrosecondLength() / 1000);// Thread.yield();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void sleep(long sleep)
    {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

