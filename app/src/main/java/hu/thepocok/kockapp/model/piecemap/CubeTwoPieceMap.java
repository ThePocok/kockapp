package hu.thepocok.kockapp.model.piecemap;

import java.util.ArrayList;
import java.util.Arrays;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;

public class CubeTwoPieceMap extends PieceMap {

    public CubeTwoPieceMap() {
        super();

        pieces.add(new Piece(new Position(Color.WHITE, 0, 0),
                new Position(Color.GREEN, 0, 0),
                new Position(Color.ORANGE, 0, 1)));
        pieces.add(new Piece(new Position(Color.WHITE, 0, 1),
                new Position(Color.BLUE, 0, 1),
                new Position(Color.ORANGE, 0, 0)));
        pieces.add(new Piece(new Position(Color.WHITE, 1, 0),
                new Position(Color.GREEN, 0, 1),
                new Position(Color.RED, 0, 0)));
        pieces.add(new Piece(new Position(Color.WHITE, 1, 1),
                new Position(Color.BLUE, 0, 0),
                new Position(Color.RED, 0, 1)));

        pieces.add(new Piece(new Position(Color.YELLOW, 0, 0),
                new Position(Color.GREEN, 1, 1),
                new Position(Color.RED, 1, 0)));
        pieces.add(new Piece(new Position(Color.YELLOW, 0, 1),
                new Position(Color.BLUE, 1, 0),
                new Position(Color.RED, 1, 1)));
        pieces.add(new Piece(new Position(Color.YELLOW, 1, 0),
                new Position(Color.GREEN, 1, 0),
                new Position(Color.ORANGE, 1, 1)));
        pieces.add(new Piece(new Position(Color.YELLOW, 1, 1),
                new Position(Color.BLUE, 1, 1),
                new Position(Color.ORANGE, 1, 0)));
    }

    public boolean isExistingPiece(Color... color) {
        ArrayList<Color> colorsOnPiece = new ArrayList<>();

        for (Piece positionList : pieces) {
            for (Position p : positionList.getPositions()) {
                colorsOnPiece.add(p.getFace());
            }
            if (colorsOnPiece.containsAll(Arrays.asList(color))) {
                return true;
            } else {
                colorsOnPiece.clear();
            }
        }

        return false;
    }
}