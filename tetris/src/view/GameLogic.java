package view;

import static model.PropertyChangeEnabledBoardControls.*;

import model.Board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class GameLogic{
    private static final int EASY_LEVEL = 4;

    private static final int EASY_SPEED = 1000;

    private static final int MEDIUM_LEVEL = 9;

    private static final int MEDIUM_SPEED = 800;

    private static final int HARD_LEVEL = 14;

    private static final int HARD_SPEED = 600;

    private static final int ULTRA_HARD_LEVEL = 19;

    private static final int ULTRA_HARD_SPEED = 400;

    private static final int SEMI_TETRIS_MASTER_LEVEL = 24;

    private static final int SEMI_TETRIS_MASTER_SPEED = 200;

    private static final int TETRIS_MASTER_LEVEL = 30;

    private static final int TETRIS_MASTER_SPEED = 100;


    private Board myBoard;
    static Timer myTimer;
    protected static int myRowsCleared;
    static int myLevel;

    private static final int TIMER_INTERVAL = 1000;

    public GameLogic(final Board theBoard) {
        myBoard = theBoard;
        myRowsCleared = 0;
        myLevel = 1;
        myTimer = new Timer(TIMER_INTERVAL, theEvent -> {
            myBoard.step();
            System.out.println("Timer has ticked");
        });
        changeDifficulty();
    }


    public void startGame() {
        myBoard.newGame();
        myTimer.start();
    }

    public void pauseGame() {
        myTimer.stop();
    }

    public void resumeGame() {
        myTimer.start();
    }

    public void exitGame() {
        myTimer.stop();
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> myBoard.left();
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> myBoard.right();
            case KeyEvent.VK_W, KeyEvent.VK_UP -> myBoard.rotateCW();
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> myBoard.down();
            case KeyEvent.VK_Z -> myBoard.rotateCCW();
            case KeyEvent.VK_SPACE -> myBoard.drop();
        }
    }

    public int getRowsCleared() {
        return myRowsCleared;
    }

    public int getLevel() {
        return myLevel;
    }

    protected void changeDifficulty() {
        if (myRowsCleared <= EASY_LEVEL) {
            myTimer.setDelay(EASY_SPEED);
            myLevel = 1;
        } else if (myRowsCleared <= MEDIUM_LEVEL) {
            myTimer.setDelay(MEDIUM_SPEED);
            myLevel = 2;
        } else if (myRowsCleared <= HARD_LEVEL) {
            myTimer.setDelay(HARD_SPEED);
            myLevel = 3;
        } else if (myRowsCleared <= ULTRA_HARD_LEVEL) {
            myTimer.setDelay(ULTRA_HARD_SPEED);
            myLevel = 4;
        } else if (myRowsCleared <= SEMI_TETRIS_MASTER_LEVEL) {
            myTimer.setDelay(SEMI_TETRIS_MASTER_SPEED);
            myLevel = 5;
        } else {
            myTimer.setDelay(TETRIS_MASTER_SPEED);
            myLevel = 6;
        }
    }

    // Add getters and setters for other game-related data as needed
}
