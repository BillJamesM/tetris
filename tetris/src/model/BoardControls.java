package model;

import model.TetrisPiece;

import java.util.List;

public interface BoardControls {

    int getWidth();

    int getHeight();

    void setPieceSequence(List<TetrisPiece> thePieces);

    void step();

    void down();

    void left();

    void right();

    void rotateCW();

    void rotateCCW();

    void drop();

    void newGame();
}
