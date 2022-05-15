package hu.thepocok.kockapp.model.piecemap;

import java.io.Serializable;
import java.util.ArrayList;

import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;

public abstract class PieceMap implements Serializable {
    protected ArrayList<Piece> pieces;

    public PieceMap() {
        pieces = new ArrayList<>();
    }

    public Piece getPieceByPosition(Position positionToFind) {
        for (Piece positionList : pieces) {
            for (Position p : positionList.getPositions()) {
                if (p.equals(positionToFind)) {
                    return positionList;
                }
            }
        }

        return null;
    }

    public ArrayList<Piece> getAllPieces() {
        return new ArrayList<>(pieces);
    }
}
