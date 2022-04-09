package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class CubeTwo extends Cube{
    private CubeTwoPieceMap pieceMap;

    public CubeTwo() {
        super(2);
        pieceMap = new CubeTwoPieceMap();
    }

    @Override
    public void solve() {
        return;
    }

    /**
     * The reference piece is the one having a white, a blue and a red color on it.
     */
    public Position findReferencePiece() {
        return getPositionByColor(Color.WHITE, Color.RED, Color.BLUE);
    }

    public void getPiece(Color faceColor, int row, int column) {
        Position positionToFind = new Position(faceColor, row, column);
        ArrayList<Position> positions = pieceMap.getPieceColorPositions(positionToFind);

        ArrayList<Color> colors = new ArrayList<>();
        for (Position p : positions) {
            colors.add(getColorFromPosition(p));
        }
    }

    @Override
    public Position getPositionByColor(Color... colors) {
        if (colors.length != 3) {
            return null;
        }

        for (ArrayList<Position> positions : pieceMap.getAllPositions()) {
            ArrayList<Color> colorsInPiece = mapPositionToColor(positions);

            if (colorsInPiece.containsAll(Arrays.asList(colors))) {
                return positions.get(0);
            }
        }

        return null;
    }
}
