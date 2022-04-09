package hu.thepocok.kockapp.model;

import java.util.ArrayList;

public abstract class PieceMap {

    public abstract ArrayList<Position> getPieceColorPositions(Position positionToFind);
}
