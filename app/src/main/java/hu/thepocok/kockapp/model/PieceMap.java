package hu.thepocok.kockapp.model;

import java.util.ArrayList;

public abstract class PieceMap {
    protected ArrayList<Piece> pieces;

    public abstract Piece getPieceByPosition(Position positionToFind);

    public ArrayList<Piece> getAllPieces() {
        return new ArrayList<>(pieces);
    }
}
