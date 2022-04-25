package hu.thepocok.kockapp.model.cube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import hu.thepocok.kockapp.model.cube.component.Color;
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

        if (whiteTilesInCross == 4) {
            return; //The cross is complete
        }

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

            mapKeyToRotation("F'");
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

        // At this point, all 2 tiled pieces from the middle layer, which contains white tile, should be in the cross

        whiteTilesInCross = whitePiecesInCross.size();

        if (whiteTilesInCross == 4) {
            return; //The cross is complete
        }

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
