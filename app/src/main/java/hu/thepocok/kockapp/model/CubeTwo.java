package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;

public class CubeTwo extends Cube{

    public CubeTwo() {
        super(2);
        pieceMap = new CubeTwoPieceMap();
    }

    @Override
    public void solve() throws UnsolvableCubeException {
        /* First task: find the reference piece, and orient the cuba in a way,
        that the reference piece's white color faces up */

        Piece referencePiece = findReferencePiece();
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
            orientation.setOrientation(faceUp, faceTowardsPlayer, orientation.getOppositeColor(faceRight));
        } catch (InvalidOrientationException e) {
            throw new UnsolvableCubeException();
        }

        return;
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
