package view;

import model.*;
import model.Point;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static model.PropertyChangeEnabledBoardControls.*;

public class BoardPanel extends JPanel implements PropertyChangeListener {


    /**
     * The width for the rectangle.
     */
    private static final int RECTANGLE_WIDTH = 43;

    /**
     * The height for the rectangle.
     */
    private static final int RECTANGLE_HEIGHT = 43;

    private Rectangle2D myShape;

    private final Board myBoard;

    private MovableTetrisPiece myMovableTetrisPiece;

    private int myTimerCounter = 0;

    private TetrisGame myTGame;

    private List<Block[]> myFrozenBlocks = new LinkedList<>();


    public BoardPanel(final Board theBoard, final TetrisGame theTG) {
        super();

        myBoard = theBoard;
        myBoard.addPropertyChangeListener(this);

        myTGame = theTG;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(theBoard.getWidth() * RECTANGLE_WIDTH,
                theBoard.getHeight() * RECTANGLE_HEIGHT));

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.BLACK);
        for (int row = 0; row < myBoard.getHeight(); row++) {
            for (int col = 0; col < myBoard.getWidth(); col++) {
                g2d.draw(new Rectangle2D.Double(col * RECTANGLE_WIDTH, row * RECTANGLE_HEIGHT,
                        RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
            }
        }

        //TODO Draw the frozen Blocks.

        //You have the choice of using normal for loop or for each loop

//        if (myFrozenBlocks != null) {
//            for (int height = 0; height < myFrozenBlocks.size(); height++) {
//                for (int width = 0; width < myFrozenBlocks.get(width).length; width++) {
//                    if (myFrozenBlocks.get(height)[width] != null) {
//                        final int tetPosY = myBoard.getHeight() - height;
//                        g2d.fill(new Rectangle2D.Double(width * RECTANGLE_WIDTH,
//                                tetPosY * RECTANGLE_HEIGHT,
//                                RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
//                    }
//                }
//            }
//        }

        //There is no bug in this one
        int height = 1;
        for (Block[] blockArr: myFrozenBlocks) {

            int width = 0;
            for (Block singleBlock: blockArr) {

                if (singleBlock != null) {
                    Shape myRect = new Rectangle(width * RECTANGLE_WIDTH,
                            (myBoard.getHeight() - height) * RECTANGLE_HEIGHT
                            , RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setPaint(Color.BLACK);
                    g2d.draw(myRect);
                    g2d.setColor(tetrisColorChooser(singleBlock));
                    g2d.fill(myRect);
                }
                width++;
            }
            height++;
        }


        // Draw anything when you press new Game button,
        //It senses that a property change occurred,
        // and will then fire a property change.
        // which instantiates a Movable Tetris Piece.
        if (myMovableTetrisPiece != null) {
            for (Point point: rotatedPoints(myMovableTetrisPiece.getRotation())) {
                final int tetPosX = point.x() + myMovableTetrisPiece.getPosition().x();
                //System.out.println("x:" + myMovableTetrisPiece.getPosition().x() + "; y:" + myMovableTetrisPiece.getPosition().y());
                final int tetPosY = myBoard.getHeight()
                        - (point.y() + myMovableTetrisPiece.getPosition().y() + 1);
                Shape myRect = new Rectangle(tetPosX * RECTANGLE_WIDTH,
                        tetPosY * RECTANGLE_HEIGHT
                        , RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                g2d.setStroke(new BasicStroke(2));
                g2d.setPaint(Color.BLACK);
                g2d.draw(myRect);
                g2d.setPaint(tetrisColorChooser(
                        myMovableTetrisPiece.getTetrisPiece().getBlock()));
                g2d.fill(myRect);

            }

        }
    }

    private List<Point> rotatedPoints(Rotation theRotation) {

        final List<Point> rotatedPoints = new ArrayList<>();

        if (myMovableTetrisPiece.getRotation() == Rotation.NONE) {
            int rotationLength = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.NONE).length;
            for (int i = 0; i < rotationLength; i++) {
                int x = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.NONE)[i][0];
                int y = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.NONE)[i][1];
                rotatedPoints.add(new Point(x, y));
            }
        } else if (myMovableTetrisPiece.getRotation() == Rotation.QUARTER) {
            int rotationLength = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.QUARTER).length;
            for (int i = 0; i < rotationLength; i++) {
                int x = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.QUARTER)[i][0];
                int y = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.QUARTER)[i][1];
                rotatedPoints.add(new Point(x, y));
            }
        } else if (myMovableTetrisPiece.getRotation() == Rotation.HALF) {
            int rotationLength = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.HALF).length;
            for (int i = 0; i < rotationLength; i++) {
                int x = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.HALF)[i][0];
                int y = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.HALF)[i][1];
                rotatedPoints.add(new Point(x, y));
            }
        } else if (myMovableTetrisPiece.getRotation() == Rotation.THREEQUARTER) {
            int rotationLength = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.THREEQUARTER).length;
            for (int i = 0; i < rotationLength; i++) {
                int x = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.THREEQUARTER)[i][0];
                int y = myMovableTetrisPiece.getTetrisPiece().getPointsByRotation(Rotation.THREEQUARTER)[i][1];
                rotatedPoints.add(new Point(x, y));
            }
        }
        return rotatedPoints;
    }

    private Color tetrisColorChooser(Block theTP) {
        Color color = null;
        if (theTP == Block.I) {
            color = Color.CYAN;
        } else if (theTP == Block.J) {
            color = Color.BLUE;
        } else if (theTP == Block.L) {
            color = Color.ORANGE;
        } else if (theTP == Block.O) {
            color = Color.yellow;
        } else if (theTP == Block.S) {
            color = Color.GREEN;
        } else if (theTP == Block.T) {
            color = Color.MAGENTA;
        } else if (theTP == Block.Z) {
            color = Color.RED;
        }
        return color;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (CURRENT_PIECE.equals(evt.getPropertyName())) {
            myMovableTetrisPiece = (MovableTetrisPiece) evt.getNewValue();
            myTimerCounter++;
        }

        if (FROZEN_BLOCKS.equals(evt.getPropertyName())) {
            myFrozenBlocks = (List<Block[]>) evt.getNewValue();
        }

        if (END_GAME.equals(evt.getPropertyName())) {
            //boolean temp = (Boolean) evt.getNewValue();
            myTGame.gameOver();
            final JFrame popUp = new JFrame();
            JOptionPane.showMessageDialog(popUp
                    , "Game Over, press end game and start a new game!");

        }

        repaint();
        //System.out.println("Board Panel: " + myTimerCounter);
    }
}
