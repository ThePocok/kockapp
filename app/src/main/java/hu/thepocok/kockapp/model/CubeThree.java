package hu.thepocok.kockapp.model;

public class CubeThree extends Cube{

    public CubeThree() {
        super(3);
        pieceMap = new PieceMap() {
            @Override
            public Piece getPieceByPosition(Position positionToFind) {
                return null;
            }
        };
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
