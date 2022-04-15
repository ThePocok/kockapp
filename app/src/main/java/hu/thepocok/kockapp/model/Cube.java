package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Iterator;

import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;

/**
 * The Cube class models an abstract Rubik's cube.
 * By default, the white faces is on top, the yellow is on bottom,
 * the red is facing towards us, on it's right side is the green face,
 * on it's left side is the blue one, and on the back, there is the orange face.
 */
public abstract class Cube {
    protected final Face whiteFace;
    protected final Face redFace;
    protected final Face greenFace;
    protected final Face orangeFace;
    protected final Face blueFace;
    protected final Face yellowFace;

    protected Orientation orientation;

    protected int dimensions;
    protected PieceMap pieceMap;

    public Cube(Face whiteFace, Face redFace, Face greenFace, Face orangeFace, Face blueFace, Face yellowFace, int dimensions) {
        this.whiteFace = whiteFace;
        this.redFace = redFace;
        this.greenFace = greenFace;
        this.orangeFace = orangeFace;
        this.blueFace = blueFace;
        this.yellowFace = yellowFace;

        this.orientation = new Orientation();
        this.dimensions = dimensions;
    }

    public Cube(int dimensions) {
        this(Face.generateFace(Color.WHITE, dimensions),
                Face.generateFace(Color.RED, dimensions),
                Face.generateFace(Color.GREEN, dimensions),
                Face.generateFace(Color.ORANGE, dimensions),
                Face.generateFace(Color.BLUE, dimensions),
                Face.generateFace(Color.YELLOW, dimensions), dimensions);

        this.orientation = new Orientation();
    }

    /**
     * Move: U
     * Turning the white face clockwise.
     * Affected layers: Whole white face, first layer of red, orange, green and blue faces.
     */
    public void rotateUpClockwise() {
        whiteFace.rotateClockwise();

        Layer layerToRotate = greenFace.getNthRow(0);
        greenFace.setNthRow(0, redFace.getNthRow(0));

        Layer originalLayer = orangeFace.getNthRow(0);
        orangeFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthRow(0);
        blueFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(0, layerToRotate);
    }

    /**
     * Move: U'
     * Turning the white face counterclockwise.
     * Affected layers: Whole white face, first layer of red, orange, green and blue faces.
     */
    public void rotateUpCounterClockwise() {
        whiteFace.rotateCounterClockwise();

        Layer layerToRotate = blueFace.getNthRow(0);
        blueFace.setNthRow(0, redFace.getNthRow(0));

        Layer originalLayer = orangeFace.getNthRow(0);
        orangeFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthRow(0);
        greenFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(0, layerToRotate);
    }

    /**
     * Move: D
     * Turning the yellow face clockwise.
     * Affected faces: Whole yellow face, last layer of red, orange, green and blue faces.
     */
    public void rotateDownClockwise() {
        yellowFace.rotateClockwise();

        Layer layerToRotate = blueFace.getNthRow(blueFace.getDimensions() - 1);
        blueFace.setNthRow(blueFace.getDimensions() - 1, redFace.getNthRow(redFace.getDimensions() - 1));

        Layer originalLayer = orangeFace.getNthRow(orangeFace.getDimensions() - 1);
        orangeFace.setNthRow(orangeFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthRow(greenFace.getDimensions() - 1);
        greenFace.setNthRow(greenFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(redFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: D'
     * Turning the yellow face counterclockwise.
     * Affected faces: Whole yellow face, last layer of red, orange, green and blue faces.
     */
    public void rotateDownCounterClockwise() {
        yellowFace.rotateCounterClockwise();

        Layer layerToRotate = greenFace.getNthRow(greenFace.getDimensions() - 1);
        greenFace.setNthRow(greenFace.getDimensions() - 1, redFace.getNthRow(redFace.getDimensions() - 1));

        Layer originalLayer = orangeFace.getNthRow(orangeFace.getDimensions() - 1);
        orangeFace.setNthRow(orangeFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthRow(blueFace.getDimensions() - 1);
        blueFace.setNthRow(blueFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(redFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: L
     * Turning the green face clockwise.
     * Affected faces: Whole green face, right column of white, red, yellow and orange faces.
     */
    public void rotateLeftClockwise() {
        greenFace.rotateClockwise();

        Layer layerToRotate = redFace.getNthColumn(0);
        redFace.setNthColumn(0, whiteFace.getNthColumn(0));

        Layer originalLayer = yellowFace.getNthColumn(0);
        yellowFace.setNthColumn(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = orangeFace.getNthColumn(orangeFace.getDimensions() - 1);
        orangeFace.setNthColumn(orangeFace.getDimensions() - 1,  layerToRotate.reverse());
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(0, layerToRotate.reverse());
    }

    /**
     * Move: L'
     * Turning the green face counterclockwise.
     * Affected faces: Whole green face, right column of white, red, yellow and orange faces.
     */
    public void rotateLeftCounterClockwise() {
        greenFace.rotateCounterClockwise();

        Layer layerToRotate = orangeFace.getNthColumn(orangeFace.getDimensions() - 1);
        orangeFace.setNthColumn(orangeFace.getDimensions() - 1, whiteFace.getNthColumn(0).reverse());

        Layer originalLayer = yellowFace.getNthColumn(0);
        yellowFace.setNthColumn(0, layerToRotate.reverse());
        layerToRotate = originalLayer;

        originalLayer = redFace.getNthColumn(0);
        redFace.setNthColumn(0,  layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(0, layerToRotate);
    }

    /**
     * Move: R
     * Turning the blue face clockwise.
     * Affected faces: Whole blue face, right column of white, red, yellow and orange faces.
     */
    public void rotateRightClockwise() {
        blueFace.rotateClockwise();

        Layer layerToRotate = orangeFace.getNthColumn(0);
        orangeFace.setNthColumn(0,  whiteFace.getNthColumn(whiteFace.getDimensions() - 1).reverse());

        Layer originalLayer = yellowFace.getNthColumn(yellowFace.getDimensions() - 1);
        yellowFace.setNthColumn(yellowFace.getDimensions() - 1, layerToRotate.reverse());
        layerToRotate = originalLayer;

        originalLayer = redFace.getNthColumn(redFace.getDimensions() - 1);
        redFace.setNthColumn(redFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(whiteFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: R'
     * Turning the blue face counterclockwise.
     * Affected faces: Whole blue face, right column of white, red, yellow and orange faces.
     */
    public void rotateRightCounterClockwise() {
        blueFace.rotateCounterClockwise();

        Layer layerToRotate = redFace.getNthColumn(redFace.getDimensions() - 1);
        redFace.setNthColumn(redFace.getDimensions() - 1,  whiteFace.getNthColumn(whiteFace.getDimensions() - 1));

        Layer originalLayer = yellowFace.getNthColumn(yellowFace.getDimensions() - 1);
        yellowFace.setNthColumn(yellowFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = orangeFace.getNthColumn(0);
        orangeFace.setNthColumn(0, layerToRotate.reverse());
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(whiteFace.getDimensions() - 1, layerToRotate.reverse());
    }

    /**
     * Move: F
     * Turning the red face clockwise.
     * Affected faces: Whole red face, bottom row of white face, left column of blue face,
     * top row of yellow face, right column of green face
     */
    public void rotateFrontClockwise() {
        redFace.rotateClockwise();

        Layer layerToRotate = blueFace.getNthColumn(0);
        blueFace.setNthColumn(0, whiteFace.getNthRow(whiteFace.getDimensions() - 1));

        Layer originalLayer = yellowFace.getNthRow(0);
        yellowFace.setNthRow(0, layerToRotate.reverse());
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthColumn(greenFace.getDimensions() - 1);
        greenFace.setNthColumn(greenFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthRow(whiteFace.getDimensions() - 1, layerToRotate.reverse());
    }

    /**
     * Move: F'
     * Turning the red face counterclockwise.
     * Affected faces: Whole red face, bottom row of white face, left column of blue face,
     * top row of yellow face, right column of green face
     */
    public void rotateFrontCounterClockwise() {
        redFace.rotateCounterClockwise();

        Layer layerToRotate = greenFace.getNthColumn(greenFace.getDimensions() - 1);
        greenFace.setNthColumn(greenFace.getDimensions() - 1, whiteFace.getNthRow(whiteFace.getDimensions() - 1).reverse());

        Layer originalLayer = yellowFace.getNthRow(0);
        yellowFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthColumn(0);
        blueFace.setNthColumn(0, layerToRotate.reverse());
        layerToRotate = originalLayer;

        whiteFace.setNthRow(whiteFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: B
     * Turning the orange face clockwise.
     * Affected faces: Whole orange face, top row of white face, right column of blue face,
     * bottom row of yellow face, left column of green face
     */
    public void rotateBackClockwise() {
        orangeFace.rotateClockwise();

        Layer layerToRotate = greenFace.getNthColumn(0);
        greenFace.setNthColumn(0, whiteFace.getNthRow(0).reverse());

        Layer originalLayer = yellowFace.getNthRow(yellowFace.getDimensions() - 1);
        yellowFace.setNthRow(yellowFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthColumn(blueFace.getDimensions() - 1);
        blueFace.setNthColumn(blueFace.getDimensions() - 1, layerToRotate.reverse());
        layerToRotate = originalLayer;

        whiteFace.setNthRow(0, layerToRotate);
    }

    /**
     * Move: B'
     * Turning the orange face counterclockwise.
     * Affected faces: Whole orange face, top row of white face, right column of blue face,
     * bottom row of yellow face, left column of green face
     */
    public void rotateBackCounterClockwise() {
        orangeFace.rotateCounterClockwise();

        Layer layerToRotate = blueFace.getNthColumn(blueFace.getDimensions() - 1);
        blueFace.setNthColumn(blueFace.getDimensions() - 1, whiteFace.getNthRow(0));

        Layer originalLayer = yellowFace.getNthRow(yellowFace.getDimensions() - 1);
        yellowFace.setNthRow(yellowFace.getDimensions() - 1, layerToRotate.reverse());
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthColumn(0);
        greenFace.setNthColumn(0, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthRow(0, layerToRotate.reverse());
    }

    public void mapKeysToRotation(String... rotationKeys) {
        for (String key : rotationKeys) {
            mapKeyToRotation(key);
            System.out.println(this);
        }
    }

    public void mapKeyToRotation(String rotationKey) {
        switch (rotationKey) {
            case "U":
                mapFaceToRotation(orientation.getFaceUp(), false);
                break;
            case "U'":
                mapFaceToRotation(orientation.getFaceUp(), true);
                break;
            case "F":
                mapFaceToRotation(orientation.getFaceFront(), false);
                break;
            case "F'":
                mapFaceToRotation(orientation.getFaceFront(), true);
                break;
            case "L":
                mapFaceToRotation(orientation.getFaceLeft(), false);
                break;
            case "L'":
                mapFaceToRotation(orientation.getFaceLeft(), true);
                break;
            case "B":
                mapFaceToRotation(orientation.getFaceBack(), false);
                break;
            case "B'":
                mapFaceToRotation(orientation.getFaceBack(), true);
                break;
            case "R":
                mapFaceToRotation(orientation.getFaceRight(), false);
                break;
            case "R'":
                mapFaceToRotation(orientation.getFaceRight(), true);
                break;
            case "D":
                mapFaceToRotation(orientation.getFaceDown(), false);
                break;
            case "D'":
                mapFaceToRotation(orientation.getFaceDown(), true);
                break;
        }
    }

    private void mapFaceToRotation(Color color, boolean counterClockwise) {
        switch (color) {
            case WHITE:
                if (counterClockwise) {
                    rotateUpCounterClockwise();
                } else  {
                    rotateUpClockwise();
                }
                break;
            case RED:
                if (counterClockwise) {
                    rotateFrontCounterClockwise();
                } else  {
                    rotateFrontClockwise();
                }
                break;
            case GREEN:
                if (counterClockwise) {
                    rotateLeftCounterClockwise();
                } else  {
                    rotateLeftClockwise();
                }
                break;
            case ORANGE:
                if (counterClockwise) {
                    rotateBackCounterClockwise();
                } else  {
                    rotateBackClockwise();
                }
                break;
            case BLUE:
                if (counterClockwise) {
                    rotateRightCounterClockwise();
                } else  {
                    rotateRightClockwise();
                }
                break;
            case YELLOW:
                if (counterClockwise) {
                    rotateDownCounterClockwise();
                } else  {
                    rotateDownClockwise();
                }
                break;
        }
    }

    /**
     * Determines the faces next to the face of the given color
     * @param color
     * @return ArrayList of the side faces in the following order: <br>
     * Face on top, face on right, face on bottom, face on left
     */
    protected ArrayList<Face> determineSideFaces(Color color) {
        ArrayList<Face> faces = new ArrayList<>();
        switch (color) {
            case WHITE:
                faces.add(orangeFace);
                faces.add(blueFace);
                faces.add(redFace);
                faces.add(greenFace);
                break;
            case YELLOW:
                faces.add(redFace);
                faces.add(blueFace);
                faces.add(orangeFace);
                faces.add(greenFace);
                break;
            case RED:
                faces.add(whiteFace);
                faces.add(blueFace);
                faces.add(yellowFace);
                faces.add(greenFace);
                break;
            case GREEN:
                faces.add(whiteFace);
                faces.add(redFace);
                faces.add(yellowFace);
                faces.add(orangeFace);
                break;
            case ORANGE:
                faces.add(whiteFace);
                faces.add(greenFace);
                faces.add(yellowFace);
                faces.add(blueFace);
                break;
            case BLUE:
                faces.add(whiteFace);
                faces.add(orangeFace);
                faces.add(yellowFace);
                faces.add(redFace);
                break;
            default:
                break;
        }

        return faces;
    }

    public Color getColorFromPosition(Position position) {
        return getFace(position.getFace()).getNthColumn(position.getColumn()).getNthPiece(position.getRow());
    }

    public ArrayList<Color> mapPieceToColor(Piece piece) {
        ArrayList<Color> colors = new ArrayList<>();
        for(Position p : piece.getPositions()) {
            colors.add(getFace(p.getFace()).getNthColumn(p.getColumn()).getNthPiece(p.getRow()));
        }
        return colors;
    }

    public Piece mapPieceToColorInPlace(Piece piece) {
        for(Position p : piece.getPositions()) {
            p.setColor(getFace(p.getFace()).getNthColumn(p.getColumn()).getNthPiece(p.getRow()));
        }
        return piece;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Face getFace(Color color) {
        switch (color) {
            case WHITE: return whiteFace;
            case RED: return redFace;
            case GREEN: return greenFace;
            case ORANGE: return orangeFace;
            case BLUE: return blueFace;
            case YELLOW: return yellowFace;
            default: return null;
        }
    }

    public Iterator<Face> getFaceIterator() {
        ArrayList<Face> faces = new ArrayList<>();
        faces.add(whiteFace);
        faces.add(redFace);
        faces.add(greenFace);
        faces.add(orangeFace);
        faces.add(blueFace);
        faces.add(yellowFace);
        return faces.iterator();
    }

    public abstract boolean isValidCube();

    public abstract Position getPositionByColor(Color... colors);

    public abstract void solve() throws UnsolvableCubeException;

    @Override
    public String toString() {
        String cube = "Cube: \n" +
                whiteFace.toString() +
                redFace.toString() +
                greenFace.toString() +
                orangeFace.toString() +
                blueFace.toString() +
                yellowFace.toString();
        return cube;
    }

    protected PieceMap getPieceMap() {
        return pieceMap;
    }
}
