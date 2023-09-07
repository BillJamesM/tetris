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


    private static boolean GAME_STATUS = false;

    private final GameMenu myTetrisMenu = new GameMenu();

    private Board myBoard;

    private BoardPanel myBoardPanel;

    private NextPiecePanel myNextPiecePanel;

    private InfoPanel myInfoPanel;

    protected ControlKeyListener myCKL = new ControlKeyListener();

    private GameLogic gameLogic;

    /**
     * Constructor that allows us to View our Tetris Game.
     */
    public TetrisGame() {
        super();
        myBoard = new Board();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        gameLogic = new GameLogic(myBoard);

        //sets up all the panels
        setUpPanels();

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
        myTetrisMenu.setNewGameAction(theE -> {
            if (!GAME_STATUS) {
                gameLogic.startGame();
                addKeyListener(myCKL);
                requestFocusInWindow();
            } else {
                gameLogic.resumeGame();
                requestFocusInWindow();
            }
        });

        myTetrisMenu.setExitAction(e -> {
            gameLogic.exitGame();
            requestFocusInWindow();
        });

        myTetrisMenu.setPauseAction(e -> {
            gameLogic.pauseGame();
            requestFocusInWindow();
        });

        myTetrisMenu.setHelpAction(e -> {
            final JFrame popUp = new JFrame();
            JOptionPane.showMessageDialog(popUp
                    , """
                            Move Left = Press "A" key or "←" Arrow key

                            Move Right = Press "D" key or "→" Arrow key

                            Rotate Clock Wise = Press "W" key or "↑" Arrow key

                            Move Down = Press "S" key or "↓" Arrow key

                            Drop = Press "Space bar" key

                            Rotate Counter Clock Wise = Press "Z" key""");
            this.requestFocusInWindow();
        });
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
            gameLogic.myRowsCleared++;
        }
        gameLogic.changeDifficulty();
    }

    class ControlKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            gameLogic.handleKeyPress(theEvent);
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
        gameLogic.myTimer.stop();
        removeKeyListener(myCKL);
    }

    public boolean getTimerRunning() {
        return gameLogic.myTimer.isRunning();
    }

    public int getTheLevel() {
        return gameLogic.myLevel;
    }
}
