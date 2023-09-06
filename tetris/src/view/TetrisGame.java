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

/**
 * The Graphics to our Tetris Game.
 * @author Group 8
 * @version Winter 2023
 */
public class TetrisGame extends JPanel implements PropertyChangeListener {

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

    private static final int TETRIS_MASTER_SPEED = 100;


    /**
     * A generated serial version UID for object Serialization.
     */
    @Serial
    private static final long serialVersionUID = 30522620238L;

    /**
     * The size of our main JPanel, where all the other panels will be put into.
     * Height x Width.
     */
    private static final int WINDOW_HEIGHT = 900;

    private static final int WINDOW_WIDTH = 700;

    private static final int TIMER_INTERVAL = 1000;

    /**
     * The size of our JPanel for the Board. Height x Width.
     */
    private static final int PLAY_SCREEN_SIZE = 600;

    /**
     * The size of our JPanel for both next tetris piece panel and info panel.
     */
    private static final int MINOR_SCREEN_SIZE = 300;

    /**
     * The Main JPanel.
     */
    private final JPanel myTetrisPanel = new JPanel();

    /**
     * The Menu JPanel. It will hold Buttons
     */
    private final JPanel myTetrisMenu = new JPanel(new FlowLayout(FlowLayout.LEADING));

    private Board myBoard;

    private BoardPanel myBoardPanel;

    private NextPiecePanel myNextPiecePanel;

    private InfoPanel myInfoPanel;

    private Timer myTimer;

    private ControlKeyListener myCKL = new ControlKeyListener();

    private int myRowsCleared;

    private int myLevel;


    /**
     * Constructor that allows us to View our Tetris Game.
     */
    public TetrisGame() {
        super();
        myBoard = new Board();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        myRowsCleared = 0;
        myLevel = 1;
        myTimer = new Timer(TIMER_INTERVAL, theEvent -> {
            myBoard.step();
            System.out.println("Timer has ticked");
        });
        changeDifficulty();

        //sets up all the panels
        setUpPanels();

        //addKeyListener(new ControlKeyListener());
        setFocusable(true);
        requestFocus();
    }

    private void setUpPanels() {
        setMenu();
        setBoardPanel();
        setRightSide();
    }

    private void setBoardPanel() {
        myBoardPanel = new BoardPanel(myBoard, this);
        add(myBoardPanel, BorderLayout.CENTER);
    }

    /**
     * Sets up our menu and places it in the North area of the BorderLayout.
     * Creates JButtons and adds into our menu JPanel.
     * Each JButton has a specific Action performed.
     * The specific Actions are achieved through AbstractAction.
     */
    private void setMenu() {
        //myTetrisMenu.transferFocus();
        final JButton newGameButton = new JButton("New Game");
        final JButton exitButton = new JButton("End Game");
        final JButton helpButton = new JButton("Help");
        final JButton pauseButton = new JButton("Pause/Resume");

        AtomicBoolean gameIsActive = new AtomicBoolean(false);
        AtomicBoolean gameHasNotStarted = new AtomicBoolean(true);

        newGameButton.addActionListener(theE -> {


            final JFrame popUp = new JFrame();
//            final int decide = JOptionPane.showConfirmDialog(popUp,
//                    "Are you sure you want to start a new Game?",
//                    "New Game?", JOptionPane.YES_NO_CANCEL_OPTION);

            if (/*decide == JOptionPane.YES_OPTION && */gameHasNotStarted.get()) {
                gameIsActive.set(true);
                gameHasNotStarted.set(false);
                myBoard.newGame();
                myTimer.start();
                addKeyListener(myCKL);

                exitButton.addActionListener(theNextE -> {
                    final JFrame popNextUp = new JFrame();

                    if (/*exit == JOptionPane.YES_OPTION && */ gameIsActive.get()) {
                        gameIsActive.set(false);
                        gameHasNotStarted.set(true);
                        myTimer.stop();
                        removeKeyListener(myCKL);

                        JOptionPane.showMessageDialog(popNextUp
                                , "Game has ended");

                    }
                    exitButton.transferFocus();
                    helpButton.transferFocus();
                    pauseButton.transferFocus();
                });

                pauseButton.addActionListener(theOtherE -> {

                    final JFrame pausePopUp = new JFrame();
                    if (myTimer.isRunning() && gameIsActive.get()) {
                        myTimer.stop();
                        removeKeyListener(myCKL);
                        JOptionPane.showMessageDialog(pausePopUp
                                , "Game is Paused, press \"ok\" to resume");
                        myTimer.start();
                        addKeyListener(myCKL);
                    }
                    pauseButton.transferFocus();
                });

            } else {
                JOptionPane.showMessageDialog(popUp
                        , "You can only start a new game when your current one has ended");
            }
            newGameButton.transferFocus();
            exitButton.transferFocus();
            helpButton.transferFocus();
            pauseButton.transferFocus();
        });


        myTetrisMenu.add(newGameButton);
        //Exit Button
        //final JButton exitButton = new JButton("Exit");

        myTetrisMenu.add(exitButton);

        //About button
        //final JButton aboutButton = new JButton("About");
        helpButton.addActionListener(theE -> {
            final JFrame popUp = new JFrame();
            JOptionPane.showMessageDialog(popUp
                    , """
                            Move Left = Press "A" key or "←" Arrow key
                            
                            Move Right = Press "D" key or "→" Arrow key
                            
                            Rotate Clock Wise = Press "W" key or "↑" Arrow key
                            
                            Move Down = Press "S" key or "↓" Arrow key
                            
                            Drop = Press "Space bar" key
                            
                            Rotate Counter Clock Wise = Press "Z" key""");
            helpButton.transferFocus();
            pauseButton.transferFocus();

        });
        myTetrisMenu.add(helpButton);
        myTetrisMenu.add(pauseButton);

        myTetrisMenu.setBackground(Color.DARK_GRAY);
        add(myTetrisMenu, BorderLayout.NORTH);
    }

    /**
     * Sets up the East side panel of the BorderLayout.
     * Splits the East panel into two JPanels (one on top and one on bottom).
     * One Panel holds the info and the other holds the next piece.
     */
    private void setRightSide() {
        /*
         * JPanel that holds the info.
         */
        myInfoPanel = new InfoPanel(myBoard, this);

        /*
         * JPanel that holds the next tetris piece that will come to the Board.
         */
        myNextPiecePanel = new NextPiecePanel(myBoard);


        /*
         * The JPanel next to the Board JPanel.
         * It will contain two additional JPanels that consist of
         * the next tetris piece to be shown and the info.
         */
        final JPanel rightPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(myInfoPanel);
        rightPanel.add(myNextPiecePanel);

        //add the right side into our tetris screen
        add(rightPanel, BorderLayout.EAST);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (CHECK_ROW.equals(evt.getPropertyName())) {
            myRowsCleared++;
        }
//        if (END_GAME.equals(evt.getPropertyName())) {
//            if ((Boolean) evt.getNewValue()) {
//                myLevel = 0;
//                myTimer.stop();
//                removeKeyListener(myCKL);
//                System.out.println("Stop everything!");
//                //myLevel = 0;
//            }
//        }

//        if (END_GAME.equals(evt.getPropertyName())) {
//            //boolean temp = (Boolean) evt.getNewValue();
//            final JFrame popUp = new JFrame();
//            JOptionPane.showMessageDialog(popUp
//                    , "Game Over, butDifferent");
//        }
        changeDifficulty();
    }

    private void changeDifficulty() {
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

    class ControlKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(final KeyEvent theEvent) {

            switch (theEvent.getKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> myBoard.left();
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> myBoard.right();
                case KeyEvent.VK_W, KeyEvent.VK_UP -> myBoard.rotateCW();
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> myBoard.down();
                case KeyEvent.VK_Z -> myBoard.rotateCCW();
                case KeyEvent.VK_SPACE -> myBoard.drop();
                default -> { //followed what intelliJ suggestion and checkstyle warnings wanted
                }
            }


        }
    }

    /**
     * How we're Able to view our JPanel.
     * @param theArgs the String arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> { // Lambda Expression.
            final TetrisGame window = new TetrisGame();

            final JFrame frame = new JFrame("Tetris");
            frame.setResizable(false);
            frame.setContentPane(window);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);
            frame.pack();

            frame.setVisible(true);
        });
    }

    public void gameOver() {
        myTimer.stop();
        removeKeyListener(myCKL);
    }

    public boolean getTimerRunning() {
        return myTimer.isRunning();
    }

    public int getTheLevel() {
        return myLevel;
    }
}
