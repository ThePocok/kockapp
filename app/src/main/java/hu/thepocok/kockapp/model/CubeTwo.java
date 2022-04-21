package hu.thepocok.kockapp.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;

public class CubeTwo extends Cube{
    private Piece referencePiece;
    private Piece secondPiece;

    public CubeTwo() {
        super(2);
        pieceMap = new CubeTwoPieceMap();
    }

    @Override
    public void solve() throws UnsolvableCubeException {
        solution.clear();
        /* First task: find the reference piece, and orient the cuba in a way,
           that the reference piece's white color faces up */

        referencePiece = findReferencePiece();
        Color faceUp = null;
        Color faceTowardsPlayer = null;
        Color faceRight = null;
        for (Position p : referencePiece.getPositions()) {
            if (p.getColor().equals(Color.WHITE)) {
                faceUp = p.getFace();
            }
            if (p.getColor().equals(Color.RED)) {
                faceTowardsPlayer = p.getFace();
            }
            if (p.getColor().equals(Color.BLUE)) {
                faceRight = p.getFace();
            }
        }
        try {
            setOrientation(faceUp, faceTowardsPlayer, orientation.getOppositeColor(faceRight));
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        /* Second task: solve the white-blue-orange piece */
        solveSecondPiece();

        /* Third task: solve the white-green-orange piece*/
        solveThirdPiece();

        /* Fourth task: solve the white-green-red piece*/
        solveFourthPiece();
    }

    private void solveFourthPiece() throws UnsolvableCubeException {
        Piece thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
        Piece fourthPiece = getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

        if (thirdPiece.isAdjacent(fourthPiece, Color.WHITE, Color.GREEN)) {
            return; //The two pieces are adjacent to each other
        }

        try {
            setOrientation(thirdPiece.getPosition(Color.WHITE).getFace(),
                        thirdPiece.getPosition(Color.GREEN).getFace(),
                        thirdPiece.getPosition(Color.ORANGE).getFace());
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        ArrayList<Position> topLayerPositions = getTopLayerPositions();
        if (topLayerPositions.contains(fourthPiece.getPosition(Color.WHITE))) {
            mapKeysToRotation("R'", "D'", "R");
        }

        // At this point, the fourth piece is on the bottom layer

        fourthPiece = getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

        //Moving to the right place
        int rotations = 0;
        while(rotations < 4 && !fourthPiece.hasPosition(thirdPiece.getPosition(Color.GREEN).getPositionAcross(2))) {
            mapKeyToRotation("D");
            thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
            fourthPiece = getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);
            rotations++;
        }
        if (rotations == 4) {
            throw new UnsolvableCubeException();
        }

        if (fourthPiece.getPosition(Color.RED).getFace().equals(orientation.getFaceFront())) {
            mapKeysToRotation("F", "D'", "F'", "D", "D");
        }
        fourthPiece = getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

        if (fourthPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceFront())) {
            mapKeysToRotation("D'", "R'", "D", "R");
            return;
        } else if(fourthPiece.getPosition(Color.GREEN).getFace().equals(orientation.getFaceFront())) {
            mapKeysToRotation("R'", "D'", "R");
            return;
        } else {
            throw new UnsolvableCubeException();
        }
    }

    private void solveThirdPiece() throws UnsolvableCubeException {
        referencePiece = findReferencePiece();
        secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
        Piece thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);

        if (secondPiece.isAdjacent(thirdPiece, Color.WHITE, Color.ORANGE)) {
            return; //The two pieces are adjacent to each other
        }

        try {
            setOrientation(secondPiece.getPosition(Color.WHITE).getFace(),
                    secondPiece.getPosition(Color.ORANGE).getFace(),
                    secondPiece.getPosition(Color.BLUE).getFace());
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        ArrayList<Position> topLayerPositions = getTopLayerPositions();
        if (topLayerPositions.contains(thirdPiece.getPosition(Color.WHITE))) {
            if (thirdPiece.isAdjacent(referencePiece, 2) && thirdPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceBack())) {
                mapKeyToRotation("R'");
                return;
            }

            if (thirdPiece.isAdjacent(referencePiece, 2)) {
                mapKeysToRotation("R", "D'");
            } else if (thirdPiece.isAdjacent(secondPiece, 2)) {
                mapKeysToRotation("R", "R", "D'");
            } else {
                throw new UnsolvableCubeException();
            }
        } else if (thirdPiece.isAdjacent(referencePiece, 2) && thirdPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceUp())) {
            mapKeysToRotation("R", "D'");
        }

        //At this point, the third piece is on the bottom layer

        thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);

        //Moving to the right place
        int rotations = 0;
        while(rotations < 4 && !thirdPiece.hasPosition(secondPiece.getPosition(Color.ORANGE).getPositionAcross(2))) {
            mapKeyToRotation("D");
            secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
            thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
            rotations++;
        }
        if (rotations == 4) {
            throw new UnsolvableCubeException();
        }

        if (thirdPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceFront())) {
            mapKeyToRotation("R");
        } else if (thirdPiece.getPosition(Color.ORANGE).getFace().equals(orientation.getFaceFront())) {
            mapKeysToRotation("R'", "D'", "R");
        } else {
            mapKeysToRotation("D", "R", "R");
        }

    }

    private void solveSecondPiece() throws UnsolvableCubeException {
        referencePiece = findReferencePiece();
        secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
        Color whiteTileFace = secondPiece.getPosition(Color.WHITE).getFace();


        // White tile can be on the top, first layer of the sides, second layer of the sides or on the bottom
        // Checking if on top
        if (whiteTileFace.equals(orientation.getFaceUp())) {
            //Check if positioned correctly
            if (referencePiece.isAdjacent(secondPiece, Color.WHITE, Color.BLUE)) {
                return; //The two pieces are adjacent to each other
            }
            if (referencePiece.isAdjacent(secondPiece, Color.WHITE)) {
                mapKeysToRotation("F'", "D'", "F", "D", "D", "B'", "D", "B");
                return;
            }
            mapKeysToRotation("L'", "D'", "L", "D'", "B'", "D", "B");
            return;
        }

        // Check if white tile faces sideways
        if (!whiteTileFace.equals(orientation.getFaceDown())) {
            /* We know, that the blue and red tiles are located on the first layer of the side faces
               If in this orientation, the reference piece's row values are equal,
               the row value of the second piece's white tile should be checked.
               If they are all the same, the white tile is in the first layer. */
            ArrayList<Position> topLayerPositions = getTopLayerPositions();
            if (topLayerPositions.contains(secondPiece.getPosition(Color.WHITE))) {
                // If the white tile faces left, and the two pieces are not adjacent, one rotation can solve the second piece
                if (secondPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceLeft()) &&
                        !secondPiece.isAdjacent(referencePiece, 2)) {
                    mapKeyToRotation("B'");
                    return;
                }

                if (secondPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceFront()) &&
                        secondPiece.isAdjacent(referencePiece, 2)) {
                    mapKeysToRotation("L", "D'", "L'");
                } else {
                    try {
                        //Hold the cube in a way, when the white-blue-orange piece is on the top left position
                        while (!secondPiece.isFacing(orientation.getFaceUp(), orientation.getFaceFront(),
                                orientation.getFaceRight())) {
                            turnCubeClockwise();
                        }
                    } catch (InvalidOrientationException e) {
                        throw new UnsolvableCubeException();
                    }

                    mapKeysToRotation("R'", "D'", "R");
                }
            }

            referencePiece = findReferencePiece();
            secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);

            try {
                setOrientation(referencePiece.getPosition(Color.WHITE).getFace(),
                        referencePiece.getPosition(Color.BLUE).getFace(),
                        referencePiece.getPosition(Color.RED).getFace());
            } catch (InvalidOrientationException e) {
                throw new UnsolvableCubeException();
            }

            //At this point, the piece should be on the bottom layer

            if (referencePiece.isAdjacent(secondPiece, Color.WHITE, Color.BLUE)) {
                return; //The two pieces are adjacent to each other
            }

            //Moving to the right place
            int rotations = 0;
            while(rotations < 4 && !secondPiece.hasPosition(referencePiece.getPosition(Color.BLUE).getPositionAcross(2))) {
                mapKeyToRotation("D");
                referencePiece = findReferencePiece();
                secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
                rotations++;
            }
            if (rotations == 4) {
                throw new UnsolvableCubeException();
            }

            if (referencePiece.isAdjacent(secondPiece, Color.WHITE, Color.BLUE)) {
                return; //The two pieces are adjacent to each other
            }

            if (secondPiece.getPosition(Color.WHITE).getFace().equals(referencePiece.getPosition(Color.BLUE).getFace())) {
                mapKeyToRotation("R");
                return;
            } else if(secondPiece.getPosition(Color.BLUE).getFace().equals(referencePiece.getPosition(Color.BLUE).getFace())) {
                mapKeysToRotation("R'", "D'", "R");
                return;
            } else {
                mapKeysToRotation("D", "R", "R");
                return;
            }

        } else {
            while (!referencePiece.isAdjacent(secondPiece, Color.BLUE)) {
                mapKeyToRotation("D");
                referencePiece = findReferencePiece();
                secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
            }
            mapKeysToRotation("B'", "D", "D", "B", "D'", "B'", "D", "B");
        }
    }

    /**
     * The reference piece is the one having a white, a blue and a red color on it.
     */
    public Piece findReferencePiece() {
        return getPieceByColor(Color.WHITE, Color.RED, Color.BLUE);
    }

    public void getPiece(Color faceColor, int row, int column) {
        Position positionToFind = new Position(faceColor, row, column);
        Piece piece = pieceMap.getPieceColorPositions(positionToFind);

        ArrayList<Color> colors = new ArrayList<>();
        for (Position p : piece.getPositions()) {
            colors.add(getColorFromPosition(p));
        }
    }

    @Override
    public Position getPositionByColor(Color... colors) {
        if (colors.length != 3) {
            return null;
        }

        for (Piece piece : pieceMap.getAllPieces()) {
            ArrayList<Color> colorsInPiece = mapPieceToColor(piece);

            if (colorsInPiece.containsAll(Arrays.asList(colors))) {
                return piece.getPositions().get(0);
            }
        }

        return null;
    }

    public Piece getPieceByColor(Color... colors) {
        if (colors.length != 3) {
            return null;
        }

        for (Piece piece : pieceMap.getAllPieces()) {
            Piece positionsWithColor = mapPieceToColorInPlace(piece);
            List<Color> colorsInPiece = positionsWithColor.getPositions().stream().map(Position::getColor).collect(Collectors.toList());

            if (colorsInPiece.containsAll(Arrays.asList(colors))) {
                return piece;
            }
        }

        return null;
    }

    @Override
    public boolean isValidCube() {
        ArrayList<Piece> allPieces = pieceMap.getAllPieces();

        Cube referenceCube = new CubeTwo();
        ArrayList<Piece> referencePieces = referenceCube.getPieceMap().getAllPieces();
        for (Piece piece : referencePieces) {
            referenceCube.mapPieceToColorInPlace(piece);
        }

        for (Piece piece : allPieces) {
            mapPieceToColorInPlace(piece);
        }

        Iterator<Piece> it = allPieces.iterator();
        while (it.hasNext()) {
            Piece piece = it.next();

            for (Piece referencePiece : referencePieces) {
                if (piece.equals(referencePiece)) {
                    it.remove();
                    break;
                }
            }
        }

        return allPieces.isEmpty();
    }

    public void makeCubeInvalid() {
        if (whiteFace.getNthRow(0).getNthPiece(0).equals(Color.WHITE)) {
            whiteFace.setNthRow(0, new Layer(Color.RED, Color.WHITE));
        } else {
            whiteFace.setNthRow(0, new Layer(Color.WHITE, Color.WHITE));
        }
    }
}
