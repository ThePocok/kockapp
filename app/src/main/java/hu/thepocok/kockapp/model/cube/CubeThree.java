package hu.thepocok.kockapp.model.cube;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Position;
import hu.thepocok.kockapp.model.piecemap.CubeThreePieceMap;

public class CubeThree extends Cube{

    public CubeThree() {
        super(3);
        pieceMap = new CubeThreePieceMap();
    }

    @Override
    public boolean isValidCube() {
        return false;
    }


    public Position getPositionByColor(Color... colors) {
        return null;
    }

    @Override
    public void solve() {
        return;
    }
}
