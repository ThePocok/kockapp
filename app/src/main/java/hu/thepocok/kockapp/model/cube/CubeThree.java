package hu.thepocok.kockapp.model.cube;

import java.util.ArrayList;
import java.util.Iterator;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;
import hu.thepocok.kockapp.model.piecemap.CubeThreePieceMap;

public class CubeThree extends Cube{

    public CubeThree() {
        super(3);
        pieceMap = new CubeThreePieceMap();
    }

    @Override
    public void solve() {
        return;
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
