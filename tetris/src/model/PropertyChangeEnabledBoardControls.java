package model;

import java.beans.PropertyChangeListener;

public interface PropertyChangeEnabledBoardControls extends BoardControls {
    /**
     * A Property name for the current piece that's being changed.
     */
    String CURRENT_PIECE = "This Piece";

    /**
     * A Property name for when there's a new game or if the game is over.
     */
    String NEW_GAME = "New Game or Game over";

    String END_GAME = "fhged";

    /**
     * A Property name for the next piece to be previewed.
     */
    String PREVIEW_PIECE = "Preview Piece";

    /**
     * A Property name for the frozen blocks on the board.
     */
    String FROZEN_BLOCKS = "Frozen Blocks";

    String CHECK_ROW = "Checked Rows";

    String ONE_ROW_COMPLETE = "One row";

    String TWO_ROW_COMPLETE = "Two row";

    String THREE_ROW_COMPLETE = "Three row";

    String FOUR_COMPLETE = "Four row";


    void addPropertyChangeListener(PropertyChangeListener theListener);


    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);


    void removePropertyChangeListener(PropertyChangeListener theListener);

    void removePropertyChangeListener(String thePropertyName,
                                      PropertyChangeListener theListener);
}