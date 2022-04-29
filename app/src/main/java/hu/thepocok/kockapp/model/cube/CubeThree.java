package hu.thepocok.kockapp.model.cube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;
import hu.thepocok.kockapp.model.piecemap.CubeThreePieceMap;

public class CubeThree extends Cube{

    public CubeThree() {
        super(3);
        pieceMap = new CubeThreePieceMap();
    }

    @Override
    public void solve() throws UnsolvableCubeException {
        solution.clear();
        /* First task: create a white cross on the yellow face */
        createWhiteCross();

        /* Second task: turn the white pieces from the cross to the right place */
        createWhiteCrossOnWhiteFace();

        /* Third task: solve white edges */
        solveWhiteCorners();

        /* Fourth task: solve the middle layer */
        solveMiddleLayer();

        /* Fifth task: create a yellow cross on the yellow face */
        createYellowCross();
        
        /* Sixth task: complete yellow corners */
        try {
            completeYellowFace();
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        /* Seventh task: reposition yellow corners to their correct place */
        try {
            repositionYellowCorners();
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        /* Eighth task: solve middle pieces on yellow face */
        solveMiddlePiecesOnYellowFace();
    }

    private void solveMiddlePiecesOnYellowFace() throws UnsolvableCubeException {
        if (isSolved()) {
            return;
        }

        int algorithmCount = 0;
        while (algorithmCount < 3 && !isSolved()) {
            while (!(getFace(orientation.getFaceLeft()).getColorCount(orientation.getFaceLeft()) == 8 &&
                    getFace(orientation.getFaceFront()).getColorCount(orientation.getFaceFront()) == 8 &&
                    getFace(orientation.getFaceRight()).getColorCount(orientation.getFaceRight()) == 8)) {
                turnCubeClockwise();
            }

            if (getColorFromPosition(new Position(orientation.getFaceFront(), 2, 1)).equals(orientation.getFaceLeft())) {
                mapKeysToRotation("F", "F", "U", "L", "R'", "F", "F", "L'", "R", "U", "F", "F");
            } else {
                mapKeysToRotation("F", "F", "U'", "L", "R'", "F", "F", "L'", "R", "U'", "F", "F");
            }

            algorithmCount++;
        }

        if (algorithmCount == 3) {
            throw new UnsolvableCubeException();
        }
    }

    private void repositionYellowCorners() throws InvalidOrientationException {
        ArrayList<Piece> cornerPieces = getYellowCornerPieces();
        int yellowCornersInCorrectPosition = (int) cornerPieces.stream().filter(this::isPieceInCorrectPosition).count();

        while (yellowCornersInCorrectPosition != 4) {
            while (yellowCornersInCorrectPosition < 2) {
                mapKeyToRotation("U");

                cornerPieces = getYellowCornerPieces();
                yellowCornersInCorrectPosition = (int) cornerPieces.stream().filter(this::isPieceInCorrectPosition).count();
            }

            if (yellowCornersInCorrectPosition == 4) {
                return;
            }

            // Check if the two matching corners are on the same place
            if (getFace(orientation.getFaceLeft()).getColorCount(orientation.getFaceLeft()) == 8) {
                turnCubeClockwise();
            } else if (getFace(orientation.getFaceFront()).getColorCount(orientation.getFaceFront()) == 8) {
                setOrientation(Color.YELLOW, orientation.getOppositeColor(orientation.getFaceFront()));
            } else if (getFace(orientation.getFaceRight()).getColorCount(orientation.getFaceRight()) == 8) {
                turnCubeCounterClockwise();
            }

            mapKeysToRotation("R'", "F", "R'", "B", "B", "R", "F'", "R'", "B", "B", "R", "R", "U'");

            cornerPieces = getYellowCornerPieces();
            yellowCornersInCorrectPosition = (int) cornerPieces.stream().filter(this::isPieceInCorrectPosition).count();
        }
    }

    private void completeYellowFace() throws InvalidOrientationException, UnsolvableCubeException {
        ArrayList<Piece> cornerPieces = getYellowCornerPieces();

        int yellowCornersOnTop = (int) cornerPieces.stream()
                .filter(p -> p.getPosition(Color.YELLOW).getFace().equals(Color.YELLOW))
                .count();

        while (yellowCornersOnTop != 4) {
            Piece p1 = cornerPieces.get(0);
            Piece p2 = cornerPieces.get(1);
            Piece p3 = cornerPieces.get(2);
            Piece p4 = cornerPieces.get(3);
            if (yellowCornersOnTop == 0) {
                if (p2.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceBack())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceLeft());//, orientation.getFaceBack());
                } else if (p3.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceRight())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceBack());//, orientation.getFaceRight());
                } else if (p4.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceBack())) {
                    setOrientation(orientation.getFaceUp(), orientation.getFaceRight());//, orientation.getFaceFront());
                } else if (!p1.getPosition(Color.YELLOW).getFace().equals(orientation.getFaceLeft())) {
                    throw new UnsolvableCubeException();
                }
            } else if (yellowCornersOnTop == 1) {
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

            cornerPieces = getYellowCornerPieces();

            yellowCornersOnTop = (int) cornerPieces.stream()
                    .filter(p -> p.getPosition(Color.YELLOW).getFace().equals(Color.YELLOW))
                    .count();
        }
    }

    private ArrayList<Piece> getYellowCornerPieces() {
        ArrayList<Piece> pieces = new ArrayList<>();

        pieces.add(mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceFront(), orientation.getFaceLeft())));
        pieces.add(mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceBack(), orientation.getFaceLeft())));
        pieces.add(mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceBack(), orientation.getFaceRight())));
        pieces.add(mapPieceToColorInPlace(getPieceByFaceColor(orientation.getFaceUp(), orientation.getFaceFront(), orientation.getFaceRight())));

        return pieces;
    }

    private void createYellowCross() {
        while (!isYellowCrossCompleted()) {
            boolean foundCorrectStartingState = false;
            foundCorrectStartingState = foundCorrectStartingState ||
                    getFaceWithCurrentOrientation(Color.YELLOW)
                            .matchPattern("_", "_", "_", "YELLOW", "YELLOW", "YELLOW", "_", "_", "_");
            foundCorrectStartingState = foundCorrectStartingState ||
                    getFaceWithCurrentOrientation(Color.YELLOW)
                            .matchPattern("_", "YELLOW", "_", "YELLOW", "YELLOW", "_", "_", "_", "_");
            //foundCorrectStartingState = foundCorrectStartingState ||
            //        getFaceWithCurrentOrientation(Color.YELLOW)
            //                .matchPattern("_", "_", "_", "_", "Y", "_", "_", "_", "_");
            int turnCount = 0;
            while (turnCount < 4 && !foundCorrectStartingState) {
                turnCubeClockwise();
                foundCorrectStartingState = foundCorrectStartingState ||
                        getFaceWithCurrentOrientation(Color.YELLOW)
                                .matchPattern("_", "_", "_", "YELLOW", "YELLOW", "YELLOW", "_", "_", "_");
                foundCorrectStartingState = foundCorrectStartingState ||
                        getFaceWithCurrentOrientation(Color.YELLOW)
                                .matchPattern("_", "YELLOW", "_", "YELLOW", "YELLOW", "_", "_", "_", "_");
                //foundCorrectStartingState = foundCorrectStartingState ||
                //        getFaceWithCurrentOrientation(Color.YELLOW)
                //                .matchPattern("_", "_", "_", "_", "Y", "_", "_", "_", "_");

                turnCount++;
            }

            mapKeysToRotation("F", "U", "R", "U'", "R'", "F'");
        }
    }

    private boolean isYellowCrossCompleted() {
        boolean l = true;

        l = l && getColorFromPosition(new Position(Color.YELLOW, 0, 1)).equals(Color.YELLOW);
        l = l && getColorFromPosition(new Position(Color.YELLOW, 1, 0)).equals(Color.YELLOW);
        l = l && getColorFromPosition(new Position(Color.YELLOW, 1, 2)).equals(Color.YELLOW);
        l = l && getColorFromPosition(new Position(Color.YELLOW, 2, 1)).equals(Color.YELLOW);

        return l;
    }

    private void solveMiddleLayer() throws UnsolvableCubeException {
        try {
            setOrientation(Color.YELLOW, orientation.getFaceFront());
        } catch (InvalidOrientationException e) {
            e.printStackTrace(); // This will never occur
        }
        // Get all the side middle pieces, which are not in their correct place
        ArrayList<Piece> misplacedMiddlePieces = getMisplacedMiddlePieces();

        while (misplacedMiddlePieces.size() != 0) {
            Piece piece = misplacedMiddlePieces.get(0);
            ArrayList<Color> colorsOnPiece = piece.getColors();

            if (piece.hasFace(Color.YELLOW)) {
                // Get the color, which has to match with the side face's center piece
                Position sidePosition = piece.getOtherColor(piece.getColorOnFace(Color.YELLOW));
                int rotationCount = 0;
                // Match color with the center piece
                while (rotationCount < 4 && !sidePosition.getFace().equals(sidePosition.getColor())) {
                    mapKeyToRotation("U");

                    piece = getPieceByColor(colorsOnPiece.toArray(new Color[2]));
                    sidePosition = piece.getOtherColor(piece.getColorOnFace(Color.YELLOW));
                    rotationCount++;
                }

                if (rotationCount == 4) {
                    throw new UnsolvableCubeException();
                }

                try {
                    setOrientation(Color.YELLOW, sidePosition.getFace());
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }

                Color otherColor = piece.getOtherColor(orientation.getFaceFront()).getColor();
                if (otherColor.equals(orientation.getFaceLeft())) {
                    mapKeysToRotation("U'", "L'", "U", "L", "U", "F", "U'", "F'");
                } else {
                    mapKeysToRotation("U", "R", "U'", "R'", "U'", "F'", "U", "F");
                }
            } else {
                try {
                    setOrientation(Color.YELLOW, piece.getPositions().get(0).getFace());
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }

                if (isPieceOnLeftSide(piece)) {
                    // Get piece to the top
                    mapKeysToRotation("U'", "L'", "U", "L", "U", "F", "U'", "F'");

                    // Orient it correctly
                    piece = getPieceByColor(colorsOnPiece.toArray(new Color[2]));
                    Color sideColorOnPiece = piece.getOtherColor(piece.getColorOnFace(Color.YELLOW)).getColor();
                    int rotationCount = 0;
                    while (rotationCount < 4 && !piece.getPosition(sideColorOnPiece).getFace().equals(sideColorOnPiece)) {
                        mapKeyToRotation("U");
                        piece = getPieceByColor(colorsOnPiece.toArray(new Color[2]));
                        rotationCount++;
                    }

                    if (rotationCount == 4) {
                        throw new UnsolvableCubeException();
                    }

                    try {
                        setOrientation(Color.YELLOW, sideColorOnPiece);
                    } catch (InvalidOrientationException e) {
                        throw new UnsolvableCubeException();
                    }

                    // Put piece back in a correct way
                    mapKeysToRotation("U'", "L'", "U", "L", "U", "F", "U'", "F'");
                } else {
                    // Get piece to the top
                    mapKeysToRotation("U", "R", "U'", "R'", "U'", "F'", "U", "F");

                    // Orient it correctly
                    piece = getPieceByColor(colorsOnPiece.toArray(new Color[2]));
                    Color sideColorOnMisplacedPiece = piece.getOtherColor(piece.getColorOnFace(Color.YELLOW)).getColor();
                    int rotationCount = 0;
                    while (rotationCount < 4 && !piece.getPosition(sideColorOnMisplacedPiece).getFace().equals(sideColorOnMisplacedPiece)) {
                        mapKeyToRotation("U");
                        piece = getPieceByColor(colorsOnPiece.toArray(new Color[2]));
                        rotationCount++;
                    }

                    if (rotationCount == 4) {
                        throw new UnsolvableCubeException();
                    }

                    try {
                        setOrientation(Color.YELLOW, sideColorOnMisplacedPiece);
                    } catch (InvalidOrientationException e) {
                        throw new UnsolvableCubeException();
                    }

                    // Put piece back in a correct way
                    mapKeysToRotation("U", "R", "U'", "R'", "U'", "F'", "U", "F");
                }
            }

            misplacedMiddlePieces = getMisplacedMiddlePieces();
        }
    }

    private ArrayList<Piece> getMisplacedMiddlePieces() {
        ArrayList<Piece> pieces = getSideMiddlePieces();
        ArrayList<Piece> piecesToReturn = new ArrayList<>();
        for (Piece piece : pieces) {
            boolean l = true;

            for (Position position : piece.getPositions()) {
                l = l && position.getFace().equals(position.getColor());
            }

            if (!l) {
                piecesToReturn.add(piece);
            }
        }

        return piecesToReturn;
    }

    public ArrayList<Piece> getSideMiddlePieces() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(getPieceByColor(Color.GREEN, Color.RED));
        pieces.add(getPieceByColor(Color.GREEN, Color.ORANGE));
        pieces.add(getPieceByColor(Color.BLUE, Color.ORANGE));
        pieces.add(getPieceByColor(Color.BLUE, Color.RED));

        return pieces;
    }

    private void solveWhiteCorners() throws UnsolvableCubeException {
        try {
            setOrientation(Color.WHITE, Color.RED);
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException(); // This will never occur
        }

        ArrayList<Piece> whiteCorners = getWhiteCorners();

        while (whiteCorners.stream().filter(this::isPieceInCorrectPosition).count() != 4) {
            int i = 0;
            while (isPieceInCorrectPosition(whiteCorners.get(i))) {
                i++;
            }
            Piece piece = whiteCorners.get(i);
            ArrayList<Color> colorsOnPiece = piece.getColors();
            // If the piece is on the white face, it should be moved to the bottom
            if (piece.hasFace(Color.WHITE)) {
                Piece finalPiece = piece;
                boolean hasFrontFacingPiece = colorsOnPiece.stream().filter(color -> finalPiece.getPosition(color).getFace().equals(orientation.getFaceFront())).count() == 1;
                boolean hasRightFacingPiece = colorsOnPiece.stream().filter(color -> finalPiece.getPosition(color).getFace().equals(orientation.getFaceRight())).count() == 1;
                while (!hasFrontFacingPiece || !hasRightFacingPiece) {
                    turnCubeClockwise();
                    Piece updatedPiece = getPieceByColor(colorsOnPiece.toArray(new Color[3]));

                    hasFrontFacingPiece = colorsOnPiece.stream().filter(color -> updatedPiece.getPosition(color).getFace().equals(orientation.getFaceFront())).count() == 1;
                    hasRightFacingPiece = colorsOnPiece.stream().filter(color -> updatedPiece.getPosition(color).getFace().equals(orientation.getFaceRight())).count() == 1;
                }
                piece = getPieceByColor(colorsOnPiece.toArray(new Color[3]));
                if (piece.getPosition(Color.WHITE).getFace().equals(Color.WHITE) || piece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceRight())) {
                    mapKeysToRotation("R'", "D'", "R");
                } else if (piece.getPosition(Color.WHITE).getFace().equals(orientation.getFaceFront())){
                    mapKeysToRotation("R'", "D", "R");
                }

            }
            piece = getPieceByColor(colorsOnPiece.toArray(new Color[3]));

            while(!isCornerInCorrectPositionOnTheBottom(piece)) {
                mapKeyToRotation("D");
                piece = mapPieceToColorInPlace(getPieceByColor(colorsOnPiece.toArray(new Color[3])));
            }

            // If the white tile faces down, it should be flipped to side
            if (piece.getPosition(Color.WHITE).getFace().equals(Color.YELLOW)) {
                while (!isPieceOnRightSide(piece)) {
                    turnCubeCounterClockwise();
                }
                mapKeysToRotation("F", "D'", "F'", "D", "D");
                piece = mapPieceToColorInPlace(getPieceByColor(colorsOnPiece.toArray(new Color[3])));
            }

            try {
                setOrientation(Color.WHITE, piece.getPosition(Color.WHITE).getFace());
            } catch (InvalidOrientationException e) {
                throw new UnsolvableCubeException(); // The white tile was on the yellow face
            }

            if (isPieceOnLeftSide(piece)) {
                mapKeysToRotation("D", "L", "D'", "L'");
            } else {
                mapKeysToRotation("D'", "R'", "D", "R");
            }

            whiteCorners = getWhiteCorners();
        }
    }

    private boolean isCornerInCorrectPositionOnTheBottom(Piece piece) {
        ArrayList<Color> colorsOnPiece = piece.getColors();
        colorsOnPiece.remove(Color.WHITE);
        ArrayList<Color> faceColorsOnPiece = piece.getFaceColors();
        faceColorsOnPiece.remove(Color.YELLOW);


        boolean isInCorrectPosition = true;
        for (Color color : colorsOnPiece) {
            isInCorrectPosition = isInCorrectPosition && faceColorsOnPiece.contains(color);
        }
        return isInCorrectPosition;
    }

    public boolean isPieceInCorrectPosition(Piece piece) {
        boolean isInCorrectPosition = true;
        int positionIndex = 0;
        while (positionIndex < piece.getPositions().size() && isInCorrectPosition) {
            Position position = piece.getPosition(piece.getColors().get(positionIndex));
            isInCorrectPosition = isInCorrectPosition && position.getFace().equals(position.getColor());
            positionIndex++;
        }
        return isInCorrectPosition;
    }

    private ArrayList<Piece> getWhiteCorners() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(getPieceByColor(Color.WHITE, Color.RED, Color.GREEN));
        pieces.add(getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN));
        pieces.add(getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE));
        pieces.add(getPieceByColor(Color.WHITE, Color.RED, Color.BLUE));

        return pieces;
    }

    private void createWhiteCrossOnWhiteFace() throws UnsolvableCubeException {
        ArrayList<Piece> whitePieces = getWhitePiecesInCross();
        int rotationCount = 0;

        while (rotationCount < 4 && whitePieces.size() != 0) {
            for (Piece piece : whitePieces) {
                Position sidePosition = piece.getOtherColor(Color.WHITE);
                if (sidePosition.getFace().equals(sidePosition.getColor())) {
                    try {
                        setOrientation(Color.YELLOW, sidePosition.getFace());
                    } catch (InvalidOrientationException e) {
                        e.printStackTrace();
                    }

                    mapKeysToRotation("F", "F");
                }
            }
            mapKeyToRotation("U");
            rotationCount++;
            whitePieces = getWhitePiecesInCross();

            if (whitePieces.size() == 0) {
                mapKeyToRotation("U'"); //It will counter the last turn, which is necessary for the loop condition, but unnecessary for the solution
                rotationCount--;
            }
        }

        if (rotationCount == 4) {
            throw new UnsolvableCubeException();
        }
    }

    private void createWhiteCross() throws UnsolvableCubeException {
        try {
            setOrientation(Color.YELLOW, Color.ORANGE);
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        ArrayList<Piece> whitePiecesInCross = getWhitePiecesInCross();
        int whiteTilesInCross = whitePiecesInCross.size();

        if (whiteTilesInCross != 4) {
            ArrayList<Piece> piecesInMiddleLayer = new ArrayList<>();
            piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 1, 0)));
            piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 1, 2)));
            piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceBack(), 1, 0)));
            piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceBack(), 1, 2)));
            ArrayList<Piece> whitePiecesInMiddleLayer = (ArrayList<Piece>) piecesInMiddleLayer.stream()
                    .map(this::mapPieceToColorInPlace)
                    .filter(piece -> piece.hasColor(Color.WHITE))
                    .collect(Collectors.toList());

            while (whitePiecesInMiddleLayer.size() != 0) {
                Piece piece = whitePiecesInMiddleLayer.get(0);

                try {
                    setOrientation(orientation.getFaceUp(), piece.getOtherColor(Color.WHITE).getFace());
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }

                Piece pieceOnTop = mapPieceToColorInPlace(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 2, 1)));
                int rotationCount = 0;
                while (rotationCount < 4 && whitePiecesInCross.contains(pieceOnTop)) {
                    mapKeyToRotation("U");
                    pieceOnTop = mapPieceToColorInPlace(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 2, 1)));
                    whitePiecesInCross = getWhitePiecesInCross();
                    rotationCount++;
                }

                if (rotationCount == 4) {
                    throw new UnsolvableCubeException();
                }

                if (isPieceOnLeftSide(piece)) {
                    mapKeyToRotation("F");
                } else {
                    mapKeyToRotation("F'");
                }
                whitePiecesInCross = getWhitePiecesInCross();

                piecesInMiddleLayer.clear();
                piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 1, 0)));
                piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 1, 2)));
                piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceBack(), 1, 0)));
                piecesInMiddleLayer.add(pieceMap.getPieceByPosition(new Position(orientation.getFaceBack(), 1, 2)));
                whitePiecesInMiddleLayer = (ArrayList<Piece>) piecesInMiddleLayer.stream()
                        .map(this::mapPieceToColorInPlace)
                        .filter(p -> p.hasColor(Color.WHITE))
                        .collect(Collectors.toList());
            }
        }

        // At this point, all 2 tiled pieces from the middle layer, which contains white tile, should be in the cross

        whiteTilesInCross = whitePiecesInCross.size();

        if (whiteTilesInCross != 4) {
            ArrayList<Piece> piecesInBottomLayer = new ArrayList<>();
            piecesInBottomLayer.add(pieceMap.getPieceByPosition(new Position(Color.RED, 0, 1)));
            piecesInBottomLayer.add(pieceMap.getPieceByPosition(new Position(Color.GREEN, 0, 1)));
            piecesInBottomLayer.add(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 0, 1)));
            piecesInBottomLayer.add(pieceMap.getPieceByPosition(new Position(Color.BLUE, 0, 1)));

            ArrayList<Piece> whitePiecesInBottomLayer = (ArrayList<Piece>) piecesInBottomLayer.stream()
                    .map(this::mapPieceToColorInPlace)
                    .filter(piece -> piece.hasColor(Color.WHITE))
                    .collect(Collectors.toList());

            for (Piece piece : whitePiecesInBottomLayer) {
                try {
                    setOrientation(orientation.getFaceUp(), piece.getOtherFace(Color.WHITE));
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }

                whitePiecesInCross = getWhitePiecesInCross();
                Piece pieceOnTop = mapPieceToColorInPlace(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 2, 1)));
                int rotationCount = 0;
                while (rotationCount < 4 && whitePiecesInCross.contains(pieceOnTop)) {
                    mapKeyToRotation("U");
                    pieceOnTop = mapPieceToColorInPlace(pieceMap.getPieceByPosition(new Position(orientation.getFaceFront(), 2, 1)));
                    whitePiecesInCross = getWhitePiecesInCross();
                    rotationCount++;
                }

                if (rotationCount == 4) {
                    throw new UnsolvableCubeException();
                }

                mapKeysToRotation("F", "F");
                whitePiecesInCross = getWhitePiecesInCross();
            }
        }

        whitePiecesInCross = getWhitePiecesInCross();

        if (whitePiecesInCross.size() != 4) {
            throw new UnsolvableCubeException();
        }

        for (Piece p : whitePiecesInCross) {
            if (!p.getColorOnFace(Color.YELLOW).equals(Color.WHITE)) {
                try {
                    setOrientation(Color.YELLOW, p.getPosition(Color.WHITE).getFace());
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }

                mapKeysToRotation("F'", "U", "L'", "U'"); //The last U' is needed to make positions in cross intact
            }
        }

        whitePiecesInCross = getWhitePiecesInCross();
        for (Piece p : whitePiecesInCross) {
            if (!p.getColorOnFace(Color.YELLOW).equals(Color.WHITE)) {
                throw new UnsolvableCubeException();
            }
        }
    }

    private ArrayList<Piece> getWhitePiecesInCross() {
        ArrayList<Piece> piecesInCross = new ArrayList<>();
        piecesInCross.add(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 0, 1)));
        piecesInCross.add(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 0)));
        piecesInCross.add(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 2)));
        piecesInCross.add(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 2, 1)));

        return (ArrayList<Piece>) piecesInCross.stream().map(this::mapPieceToColorInPlace).filter(piece -> piece.hasColor(Color.WHITE)).collect(Collectors.toList());
    }

    public Face getFaceWithCurrentOrientation(Color faceColor) {
        int rotationDegree = 0;
        switch (faceColor) {
            case WHITE:
                switch (orientation.getFaceFront()) {
                    case RED:
                        rotationDegree = 0;
                        break;
                    case BLUE:
                        rotationDegree = 90;
                        break;
                    case ORANGE:
                        rotationDegree = 180;
                        break;
                    case GREEN:
                        rotationDegree = 270;
                        break;
                }
                break;
            case RED:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        rotationDegree = 180;
                        break;
                    case BLUE:
                        rotationDegree = 90;
                        break;
                    case YELLOW:
                        rotationDegree = 0;
                        break;
                    case GREEN:
                        rotationDegree = 270;
                        break;
                }
                break;
            case GREEN:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        rotationDegree = 180;
                        break;
                    case RED:
                        rotationDegree = 90;
                        break;
                    case YELLOW:
                        rotationDegree = 0;
                        break;
                    case ORANGE:
                        rotationDegree = 270;
                        break;
                }
                break;
            case ORANGE:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        rotationDegree = 180;
                        break;
                    case GREEN:
                        rotationDegree = 90;
                        break;
                    case YELLOW:
                        rotationDegree = 0;
                        break;
                    case BLUE:
                        rotationDegree = 270;
                        break;
                }
                break;
            case BLUE:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        rotationDegree = 180;
                        break;
                    case ORANGE:
                        rotationDegree = 90;
                        break;
                    case YELLOW:
                        rotationDegree = 0;
                        break;
                    case RED:
                        rotationDegree = 270;
                        break;
                }
                break;
            case YELLOW:
                switch (orientation.getFaceFront()) {
                    case RED:
                        rotationDegree = 180;
                        break;
                    case BLUE:
                        rotationDegree = 90;
                        break;
                    case ORANGE:
                        rotationDegree = 0;
                        break;
                    case GREEN:
                        rotationDegree = 270;
                        break;
                }
                break;
        }

        Face face = null;
        switch (rotationDegree) {
            case 0:
                face = new Face(getFace(faceColor).getNthRow(0),
                        getFace(faceColor).getNthRow(1),
                        getFace(faceColor).getNthRow(2));
                break;
            case 90:
                face = new Face(getFace(faceColor).getNthColumn(0).reverse(),
                        getFace(faceColor).getNthColumn(1).reverse(),
                        getFace(faceColor).getNthColumn(2).reverse());
                break;
            case 180:
                face = new Face(getFace(faceColor).getNthRow(2).reverse(),
                        getFace(faceColor).getNthRow(1).reverse(),
                        getFace(faceColor).getNthRow(0).reverse());
                break;
            case 270:
                face = new Face(getFace(faceColor).getNthColumn(2),
                        getFace(faceColor).getNthColumn(1),
                        getFace(faceColor).getNthColumn(0));
                break;
        }

        return face;
    }

    @Override
    public boolean isValidCube() {
        ArrayList<Piece> allPieces = pieceMap.getAllPieces();

        Cube referenceCube = new CubeThree();
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
            whiteFace.setNthRow(0, new Layer(Color.RED, Color.WHITE, Color.WHITE));
        } else {
            whiteFace.setNthRow(0, new Layer(Color.WHITE, Color.WHITE, Color.WHITE));
        }
    }
}
