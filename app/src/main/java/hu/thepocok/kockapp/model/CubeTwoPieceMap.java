package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class CubeTwoPieceMap extends PieceMap{
    private ArrayList<ArrayList<Position>> positions;

    public CubeTwoPieceMap() {
        positions = new ArrayList<>();

        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.WHITE, 0, 0),
                new Position(Color.GREEN, 0, 0),
                new Position(Color.ORANGE, 0, 1))));
        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.WHITE, 0, 1),
                new Position(Color.BLUE, 0, 1),
                new Position(Color.ORANGE, 0, 0))));
        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.WHITE, 1, 0),
                new Position(Color.GREEN, 0, 1),
                new Position(Color.RED, 0, 0))));
        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.WHITE, 1, 1),
                new Position(Color.BLUE, 0, 0),
                new Position(Color.RED, 0, 1))));

        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.YELLOW, 0, 0),
                new Position(Color.GREEN, 1, 1),
                new Position(Color.RED, 1, 0))));
        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.YELLOW, 0, 1),
                new Position(Color.BLUE, 1, 0),
                new Position(Color.RED, 1, 1))));
        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.YELLOW, 1, 0),
                new Position(Color.GREEN, 1, 0),
                new Position(Color.ORANGE, 1, 1))));
        positions.add(new ArrayList<>(Arrays.asList(new Position(Color.YELLOW, 1, 1),
                new Position(Color.BLUE, 1, 1),
                new Position(Color.ORANGE, 1, 0))));
    }

    @Override
    public ArrayList<Position> getPieceColorPositions(Position positionToFind) {
        for (ArrayList<Position> positionList : positions) {
            for (Position p : positionList) {
                if (p.equals(positionToFind)) {
                    return positionList;
                }
            }
        }

        return null;
    }
}
