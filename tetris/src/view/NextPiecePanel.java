package view;

import model.Board;
import model.BoardControls;
import model.MovableTetrisPiece;
import model.Point;
import model.TetrisPiece;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import static model.PropertyChangeEnabledBoardControls.PREVIEW_PIECE;

public class NextPiecePanel extends JPanel implements PropertyChangeListener {


    /**
     * The width for the rectangle.
     */
    private static final int RECTANGLE_WIDTH = 54;

    /**
     * The height for the rectangle.
     */
    private static final int RECTANGLE_HEIGHT = 54;

    private Rectangle2D myShape;

    private Board myBoard;

    private TetrisPiece myNextPiece;

    private int myTimerCounter = 0;


    public NextPiecePanel(final Board theBoard) {
        super();

        myBoard = theBoard;
        myBoard.addPropertyChangeListener(this);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(theBoard.getWidth()/2 * RECTANGLE_WIDTH,
                theBoard.getHeight()/4 * RECTANGLE_HEIGHT));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

//        g2d.setPaint(Color.BLACK);
//        for (int row = 0; row < myBoard.getHeight()/4; row++) {
//            for (int col = 0; col < myBoard.getWidth()/2; col++) {
//                g2d.draw(new Rectangle2D.Double(col * RECTANGLE_WIDTH, row * RECTANGLE_HEIGHT,
//                        RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
//            }
//        }

        if (myNextPiece != null) {
            for (Point point: myNextPiece.getPoints()) {
                final int tetPosY = (myBoard.getHeight()/4) - point.y();
                g2d.setStroke(new BasicStroke(2));
                Shape myRect = new Rectangle2D.Double((point.x() + 1) * RECTANGLE_WIDTH,
                        tetPosY * RECTANGLE_HEIGHT
                        , RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                g2d.setPaint(Color.BLACK);
                g2d.draw(myRect);
                g2d.setPaint(tetrisColorChooser(myNextPiece));
                //final int tetPosX = point.x() + myMovableTetrisPiece.getPosition().x();
                //System.out.println("x:" + point.x() + "; y:" + point.y());

                g2d.fill(myRect);
            }
        }


    }

    private Color tetrisColorChooser(TetrisPiece theTP) {
        Color color = null;
        if (theTP == TetrisPiece.I) {
            color = Color.CYAN;
        } else if (theTP == TetrisPiece.J) {
            color = Color.BLUE;
        } else if (theTP == TetrisPiece.L) {
            color = Color.ORANGE;
        } else if (theTP == TetrisPiece.O) {
            color = Color.yellow;
        } else if (theTP == TetrisPiece.S) {
            color = Color.GREEN;
        } else if (theTP == TetrisPiece.T) {
            color = Color.MAGENTA;
        } else if (theTP == TetrisPiece.Z) {
            color = Color.RED;
        }
        return color;
    }

    public void propertyChange(final PropertyChangeEvent evt) {
//        final Graphics2D g2d = (Graphics2D) evt.getNewValue();
//        final int rectWidth = getWidth() / myBoard.getWidth();
//        final int rectHeight = getHeight() / myBoard.getHeight();
//        for (int i = 4; i < myArray.length; i++) {
//            int x = 0;
//            int y = (i - 4) * rectHeight;
//            String r = myArray[i];
//            int count = 0;
//            for (int f = 0; f < r.length(); f++) {
//                if (r.charAt(f) != ' ') {
//                    myPiece = new Rectangle(x, y, rectWidth, rectHeight);
//                    g2d.setStroke(new BasicStroke(4));
//                    g2d.setPaint(Color.BLACK);
//                    g2d.draw(myPiece);
//                    g2d.setPaint(Color.MAGENTA);
//                    g2d.fill(myPiece);
//                }
//                count++;
//            }
//        }
        if (PREVIEW_PIECE.equals(evt.getPropertyName())) {
            myTimerCounter++;
            myNextPiece = (TetrisPiece) evt.getNewValue();
            //System.out.println("Next Piece: ");
        }
        repaint();
        //System.out.println("Next Piece: " + myTimerCounter);

    }
}
