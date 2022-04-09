package hu.thepocok.kockapp.model;

import java.util.ArrayList;

public abstract class PieceMap {
    protected ArrayList<ArrayList<Position>> positions;

    public abstract ArrayList<Position> getPieceColorPositions(Position positionToFind);

    public ArrayList<ArrayList<Position>> getAllPositions() {
        return positions;
    }
}
