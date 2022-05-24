package hu.thepocok.kockapp.model.cube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;
import hu.thepocok.kockapp.model.piecemap.CubeTwoPieceMap;

public class CubeTwo extends Cube implements Serializable {
    private Piece referencePiece;
    private Piece secondPiece;

    public CubeTwo() {
        super(2);
        pieceMap = new CubeTwoPieceMap();
    }

    public CubeTwo(Face whiteFace, Face redFace, Face greenFace, Face orangeFace, Face blueFace, Face yellowFace) {
        super(whiteFace, redFace, greenFace, orangeFace, blueFace, yellowFace, 2);
        pieceMap = new CubeTwoPieceMap();
    }

    @Override
    public void solve() throws UnsolvableCubeException {
        solution.clear();

        if (isSolved()) {
            addSection(0);
            return;
        }
        /* First task: find the reference piece, and orient the cube in a way,
           that the reference piece's white color faces up */
        setInitialOrientation();

        /* Second task: solve the white-blue-orange piece */
        solveSecondPiece();

        /* Third task: solve the white-green-orange piece*/
        solveThirdPiece();

        /* Fourth task: solve the white-green-red piece*/
        try {
            solveFourthPiece();
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        /* Fifth task: get all the yellow tiles to the correct face */
        try {
            moveYellowTilesUp();
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        /* Last task: swap the two pieces not matching on the bottom layer*/
        swapNotMatchingPieces();

        /* During the solution, is it possible, that more steps were made, then necessary. */
        simplifySolution();
    }

    private void swapNotMatchingPieces() throws UnsolvableCubeException {
        int algorithmCount = 0;
        while (algorithmCount < 10 && !isSolved()) {
            ArrayList<Position> topLayerPositions = getTopLayerPositions();

            int i = 0;
            while (i < topLayerPositions.size()) {
                if (topLayerPositions.get(i).getColor().equals(topLayerPositions.get(i + 1).getColor())) {
                    Color colorShouldBeCompleted = topLayerPositions.get(i).getColor();
                    ArrayList<Color> colors = mapPieceToColor(pieceMap.getPieceByPosition(topLayerPositions.get(i)));
                    ArrayList<Color> colorsToDetermineFace = mapPieceToColor(pieceMap.getPieceByPosition(topLayerPositions.get(i)));

                    colorsToDetermineFace.remove(Color.YELLOW);
                    colorsToDetermineFace.remove(colorShouldBeCompleted);

                    if (colorsToDetermineFace.size() != 1) {
                        throw new UnsolvableCubeException();
                    }
                    Piece piecePlacedCorrectly = getPieceByColor(Color.WHITE, colorShouldBeCompleted, colorsToDetermineFace.get(0));

                    Piece pieceToMatch = getPieceByColor(colors.toArray(new Color[3]));
                    int rotationCount = 0;
                    while (rotationCount < 4 && !piecePlacedCorrectly.isAdjacent(pieceToMatch, colorShouldBeCompleted, colorsToDetermineFace.get(0))) {
                        mapKeyToRotation("U");
                        pieceToMatch = getPieceByColor(colors.toArray(new Color[3]));
                        rotationCount++;
                    }
                    if (rotationCount == 4) {
                        throw new UnsolvableCubeException();
                    }

                    try {
                        setOrientation(orientation.getOppositeColor(piecePlacedCorrectly.getPosition(Color.WHITE).getFace()),
                                orientation.getOppositeColor(piecePlacedCorrectly.getPosition(colorShouldBeCompleted).getFace()));
                    } catch (InvalidOrientationException e) {
                        try {
                            setOrientation(orientation.getOppositeColor(piecePlacedCorrectly.getPosition(Color.WHITE).getFace()),
                                    orientation.getOppositeColor(piecePlacedCorrectly.getPosition(colorShouldBeCompleted).getFace()));
                        } catch (InvalidOrientationException invalidOrientationException) {
                            throw new UnsolvableCubeException();
                        }
                    }

                    break;
                }
                i = i + 2;
            }

            if (i == topLayerPositions.size()) {
                Piece pieceBottomRight = getPieceByFaceColor(orientation.getFaceDown(), orientation.getFaceFront(), orientation.getFaceRight());
                Piece p1 = getPieceByColor(Color.YELLOW, pieceBottomRight.getColorOnFace(orientation.getFaceFront()), pieceBottomRight.getColorOnFace(orientation.getFaceRight()));

                while (!pieceBottomRight.isAdjacent(p1, pieceBottomRight.getColorOnFace(orientation.getFaceFront()), pieceBottomRight.getColorOnFace(orientation.getFaceRight()))) {
                    mapKeyToRotation("U");
                    p1 = getPieceByColor(Color.YELLOW, pieceBottomRight.getColorOnFace(orientation.getFaceFront()), pieceBottomRight.getColorOnFace(orientation.getFaceRight()));
                }

                try {
                    setOrientation(orientation.getOppositeColor(pieceBottomRight.getPosition(pieceBottomRight.getColorOnFace(orientation.getFaceDown())).getFace()),
                            pieceBottomRight.getPosition(pieceBottomRight.getColorOnFace(orientation.getFaceFront())).getFace());
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }
            }

            if (isSolved()) {
                addSection(5);
                return;
            }

            mapKeysToRotation("R'", "F", "R'", "B", "B", "R", "F'", "R'", "B", "B", "R", "R", "U'");

            addSection(5);
            algorithmCount++;
        }

        if (algorithmCount == 10) {
            throw new UnsolvableCubeException();
        }
    }

    private void moveYellowTilesUp() throws UnsolvableCubeException, InvalidOrientationException {
        referencePiece = findReferencePiece();

        try {
            setOrientation(orientation.getOppositeColor(referencePiece.getPosition(Color.WHITE).getFace()),
                    orientation.getFaceBack());
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        int yellowTilesOnTop = getFace(orientation.getFaceUp()).getColorCount(Color.YELLOW);

        while (yellowTilesOnTop != 4) {
            Piece p1 = mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceFront(), orientation.getFaceLeft()));
            Piece p2 = mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceBack(), orientation.getFaceLeft()));
            Piece p3 = mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceBack(), orientation.getFaceRight()));
            Piece p4 = mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceFront(), orientation.getFaceRight()));


            if (yellowTilesOnTop == 0) {
                if (p2.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceBack())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceLeft());//, orientation.getFaceBack());
                } else if (p3.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceRight())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceBack());//, orientation.getFaceRight());
                } else if (p4.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceBack())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceRight());//, orientation.getFaceFront());
                } else if (!p1.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceLeft())) {
                    throw new UnsolvableCubeException();
                }
            } else if (yellowTilesOnTop == 1) {
                if (p2.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceUp())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceLeft());//, orientation.getFaceBack());
                } else if (p3.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceUp())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceBack());//, orientation.getFaceRight());
                } else if (p4.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceUp())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceRight());//, orientation.getFaceFront());
                } else if (!p1.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceUp())) {
                    throw new UnsolvableCubeException();
                }
            } else {
                if (p2.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceLeft())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceLeft());//, orientation.getFaceBack());
                } else if (p3.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceBack())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceBack());//, orientation.getFaceRight());
                } else if (p4.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceRight())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceRight());//, orientation.getFaceFront());
                } else if (!p1.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceFront())) {
                    throw new UnsolvableCubeException();
                }
            }

            mapKeysToRotation("R", "U", "R'", "U", "R", "U", "U", "R'");
            yellowTilesOnTop = getFace(orientation.getFaceUp()).getColorCount(Color.YELLOW);

            addSection(4);
        }
    }

    private void solveFourthPiece() throws UnsolvableCubeException, InvalidOrientationException {
        Piece thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
        Piece fourthPiece = getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

        if (thirdPiece.isAdjacent(fourthPiece, Color.WHITE, Color.GREEN)) {
            addSection(3);
            return; //The two pieces are adjacent to each other
        }

        setOrientation(thirdPiece.getPosition(Color.WHITE).getFace(),
                thirdPiece.getPosition(Color.GREEN).getFace());

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
        } else if(fourthPiece.getPosition(Color.GREEN).getFace().equals(orientation.getFaceFront())) {
            mapKeysToRotation("R'", "D'", "R");
        } else {
            throw new UnsolvableCubeException();
        }

        addSection(3);
    }

    private void solveThirdPiece() throws UnsolvableCubeException {
        referencePiece = findReferencePiece();
        secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
        Piece thirdPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);

        if (secondPiece.isAdjacent(thirdPiece, Color.WHITE, Color.ORANGE)) {
            addSection(2);
            return; //The two pieces are adjacent to each other
        }

        try {
            setOrientation(secondPiece.getPosition(Color.WHITE).getFace(),
                    secondPiece.getPosition(Color.ORANGE).getFace());
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        ArrayList<Position> topLayerPositions = getTopLayerPositions();
        if (topLayerPositions.contains(thirdPiece.getPosition(Color.WHITE))) {
            if (thirdPiece.isAdjacent(referencePiece, 2) && thirdPiece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceBack())) {
                mapKeyToRotation("R'");
                addSection(2);
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

        addSection(2);
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
                addSection(1);
                return; //The two pieces are adjacent to each other
            }
            if (referencePiece.isAdjacent(secondPiece, Color.WHITE)) {
                mapKeysToRotation("F'", "D'", "F", "D", "D", "B'", "D", "B");
                addSection(1);
                return;
            }
            mapKeysToRotation("L'", "D'", "L", "D'", "B'", "D", "B");
            addSection(1);
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
                    //Hold the cube in a way, when the white-blue-orange piece is on the top left position
                    while (!secondPiece.isFacing(orientation.getFaceUp(), orientation.getFaceFront(),
                            orientation.getFaceRight())) {
                        turnCubeClockwise();
                    }

                    mapKeysToRotation("R'", "D'", "R");
                }
            }

            referencePiece = findReferencePiece();
            secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);

            try {
                setOrientation(referencePiece.getPosition(Color.WHITE).getFace(),
                        referencePiece.getPosition(Color.BLUE).getFace());
                        //referencePiece.getPosition(Color.RED).getFace());
            } catch (InvalidOrientationException e) {
                throw new UnsolvableCubeException();
            }

            //At this point, the piece should be on the bottom layer

            if (referencePiece.isAdjacent(secondPiece, Color.WHITE, Color.BLUE)) {
                addSection(1);
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
                addSection(1);
                return; //The two pieces are adjacent to each other
            }

            if (secondPiece.getPosition(Color.WHITE).getFace().equals(referencePiece.getPosition(Color.BLUE).getFace())) {
                mapKeyToRotation("R");
            } else if(secondPiece.getPosition(Color.BLUE).getFace().equals(referencePiece.getPosition(Color.BLUE).getFace())) {
                mapKeysToRotation("R'", "D'", "R");
            } else {
                mapKeysToRotation("D", "R", "R");
            }

        } else {
            while (!referencePiece.isAdjacent(secondPiece, Color.BLUE)) {
                mapKeyToRotation("D");
                referencePiece = findReferencePiece();
                secondPiece = getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
            }
            mapKeysToRotation("B'", "D", "D", "B", "D'", "B'", "D", "B");
        }

        addSection(1);
    }

    private void setInitialOrientation() throws UnsolvableCubeException {
        referencePiece = findReferencePiece();
        Color faceUp = null;
        Color faceFront = null;
        for (Position p : referencePiece.getPositions()) {
            if (p.getColor().equals(Color.WHITE)) {
                faceUp = p.getFace();
            }
            if (p.getColor().equals(Color.RED)) {
                faceFront = p.getFace();
            }
        }
        try {
            setOrientation(faceUp, faceFront);
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        addSection(0);
    }

    /**
     * The reference piece is the one having a white, a blue and a red color on it.
     */
    public Piece findReferencePiece() {
        return getPieceByColor(Color.WHITE, Color.RED, Color.BLUE);
    }

    @Override
    public boolean isValidCube() {
        ArrayList<Piece> allPieces = pieceMap.getAllPieces();

        Color[] colors = new Color[]{Color.WHITE, Color.RED, Color.GREEN, Color.ORANGE, Color.BLUE, Color.YELLOW};

        for (Color color : colors) {
            int colorCount = 0;
            colorCount += getFace(Color.WHITE).getColorCount(color);
            colorCount += getFace(Color.RED).getColorCount(color);
            colorCount += getFace(Color.GREEN).getColorCount(color);
            colorCount += getFace(Color.ORANGE).getColorCount(color);
            colorCount += getFace(Color.BLUE).getColorCount(color);
            colorCount += getFace(Color.YELLOW).getColorCount(color);

            if (colorCount != Math.pow(dimensions, 2)) {
                return false;
            }
        }

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
