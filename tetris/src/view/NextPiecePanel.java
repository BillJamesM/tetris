package view;

import model.Board;
import model.Point;
import model.TetrisPiece;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static model.PropertyChangeEnabledBoardControls.PREVIEW_PIECE;

public class NextPiecePanel extends JPanel implements PropertyChangeListener {

    private static final int RECTANGLE_WIDTH = 40;
    private static final int RECTANGLE_HEIGHT = 41;


    private Board myBoard;
    private TetrisPiece myNextPiece;

    public NextPiecePanel(final Board theBoard) {
        super();
        myBoard = theBoard;
        myBoard.addPropertyChangeListener(this);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(theBoard.getWidth() * RECTANGLE_WIDTH / 2,
                theBoard.getHeight() * RECTANGLE_HEIGHT / 4));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (myNextPiece != null) {
            final int startX = getWidth() / 2 - myNextPiece.getWidth() * RECTANGLE_WIDTH / 2;
            final int startY = getHeight() / 2 - myNextPiece.getHeight() * RECTANGLE_HEIGHT / 2;

            for (Point point : myNextPiece.getPoints()) {
                final int tetPosX = startX + point.x() * RECTANGLE_WIDTH;
                final int tetPosY = startY + (myNextPiece.getHeight() - 1 - point.y()) * RECTANGLE_HEIGHT;

                // Create a rounded rectangle with smoother edges
                Shape roundedRect = new RoundRectangle2D.Double(
                        tetPosX, tetPosY, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, 10, 10);

                // Draw a gradient-filled Tetris piece with a border
                g2d.setPaint(new GradientPaint(
                        tetPosX, tetPosY,
                        tetrisColorChooser(myNextPiece),
                        tetPosX, tetPosY + RECTANGLE_HEIGHT,
                        Color.WHITE));
                g2d.fill(roundedRect);
                g2d.setColor(Color.BLACK);
                g2d.draw(roundedRect);
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
            color = Color.YELLOW;
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
        if (PREVIEW_PIECE.equals(evt.getPropertyName())) {
            myNextPiece = (TetrisPiece) evt.getNewValue();
        }
        repaint();
    }
}
