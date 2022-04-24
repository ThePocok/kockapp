package hu.thepocok.kockapp.model.piecemap;

import java.util.ArrayList;

import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;

public abstract class PieceMap {
    protected ArrayList<Piece> pieces;

    public abstract Piece getPieceByPosition(Position positionToFind);

    public ArrayList<Piece> getAllPieces() {
        return new ArrayList<>(pieces);
    }
}
