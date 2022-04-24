package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
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

    protected ArrayList<Move> solution;

    public Cube(Face whiteFace, Face redFace, Face greenFace, Face orangeFace, Face blueFace, Face yellowFace, int dimensions) {
        this.whiteFace = whiteFace;
        this.redFace = redFace;
        this.greenFace = greenFace;
        this.orangeFace = orangeFace;
        this.blueFace = blueFace;
        this.yellowFace = yellowFace;

        this.orientation = new Orientation();
        this.dimensions = dimensions;
        this.solution = new ArrayList<>();
    }

    public Cube(int dimensions) {
        this(Face.generateFace(Color.WHITE, dimensions),
                Face.generateFace(Color.RED, dimensions),
                Face.generateFace(Color.GREEN, dimensions),
                Face.generateFace(Color.ORANGE, dimensions),
                Face.generateFace(Color.BLUE, dimensions),
                Face.generateFace(Color.YELLOW, dimensions), dimensions);

        this.orientation = new Orientation();
        this.solution = new ArrayList<>();
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

        solution.add(new Rotation(rotationKey));
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

    public void setOrientation(Color faceUp, Color faceTowardsPlayer) throws InvalidOrientationException {
        orientation.setOrientation(faceUp, faceTowardsPlayer);
        solution.add(new Reorientation(orientation));
    }

    public void simplifySolution() {
        for (int i = 0; i < solution.size() - 2; i++) {
            // If two reorientations were made, the first one is unnecessary
            if (solution.get(i) instanceof Reorientation && solution.get(i+1) instanceof Reorientation) {
                solution.remove(i);
            }

            if (solution.get(i) instanceof Reorientation) {
                continue;
            }

            // If three rotation with the same key was made, they can be replaced with one counter rotation
            String key = ((Rotation) solution.get(i)).getKey();
            int j = i;
            while (j < i + 3 && ((Rotation) solution.get(j)).getKey().equals(key)) {
                j++;
                if (!(solution.get(j) instanceof Rotation)) {
                    break;
                }
            }

            if (!(solution.get(j) instanceof Rotation)) {
                continue;
            }

            if (j == i+3) {
                String newKey = (key.endsWith("'")) ? (key.substring(0, 1)) : key + "'";

                ((Rotation) solution.get(i)).setKey(newKey);
                solution.remove(i+1);
                solution.remove(i+1);
            }

            // If one rotation and its counter rotation were made, they both can be eliminated
            key = ((Rotation) solution.get(i)).getKey();
            String nextKey = ((Rotation) solution.get(i)).getKey();

            if (areCounterRotations(key, nextKey)) {
                solution.remove(i+1);
                solution.remove(i);
            }
        }
    }

    public ArrayList<Position> getTopLayerPositions() {
        ArrayList<Position> positions = new ArrayList<>();

        switch (orientation.getFaceUp()) {
            case WHITE:
                positions.addAll(redFace.getNthRowPositions(0));
                positions.addAll(greenFace.getNthRowPositions(0));
                positions.addAll(orangeFace.getNthRowPositions(0));
                positions.addAll(blueFace.getNthRowPositions(0));
                break;
            case RED:
                positions.addAll(yellowFace.getNthRowPositions(0));
                positions.addAll(greenFace.getNthColumnPositions(dimensions - 1));
                positions.addAll(whiteFace.getNthRowPositions(dimensions - 1));
                positions.addAll(blueFace.getNthColumnPositions(0));
                break;
            case GREEN:
                positions.addAll(yellowFace.getNthColumnPositions(0));
                positions.addAll(orangeFace.getNthColumnPositions(dimensions - 1));
                positions.addAll(whiteFace.getNthColumnPositions(0));
                positions.addAll(redFace.getNthColumnPositions(0));
                break;
            case ORANGE:
                positions.addAll(whiteFace.getNthRowPositions(0));
                positions.addAll(greenFace.getNthColumnPositions(0));
                positions.addAll(yellowFace.getNthRowPositions(dimensions - 1));
                positions.addAll(blueFace.getNthColumnPositions(dimensions - 1));
                break;
            case BLUE:
                positions.addAll(whiteFace.getNthColumnPositions(dimensions - 1));
                positions.addAll(redFace.getNthColumnPositions(dimensions - 1));
                positions.addAll(yellowFace.getNthColumnPositions(dimensions - 1));
                positions.addAll(orangeFace.getNthColumnPositions(0));
                break;
            case YELLOW:
                positions.addAll(redFace.getNthRowPositions(dimensions - 1));
                positions.addAll(greenFace.getNthRowPositions(dimensions - 1));
                positions.addAll(orangeFace.getNthRowPositions(dimensions - 1));
                positions.addAll(blueFace.getNthRowPositions(dimensions - 1));
                break;
        }

        return positions;
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

    public void randomScramble(int length) {
        String[] possibleRotations = new String[]{"U", "U'", "F", "F'", "L", "L'", "B", "B'", "R", "R'", "D", "D'"};
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            String nextRotation = possibleRotations[random.nextInt(possibleRotations.length)];
            if (i > 0 && areCounterRotations(((Rotation) solution.get(solution.size() - 1)).getKey(), nextRotation)) {
                i--;
                continue;
            }
            mapKeyToRotation(possibleRotations[random.nextInt(possibleRotations.length)]);
        }
    }

    public ArrayList<Move> getSolution() {
        return solution;
    }

    public String getSolutionString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solution:\n");
        for (Move m : solution) {
            if (m instanceof Reorientation) {
                Reorientation reorientation = (Reorientation) m;
                sb.append(reorientation + "\n");
            } else {
                Rotation rotation = (Rotation) m;
                sb.append(rotation + "\n");
            }
        }

        return sb.toString();
    }

    public void turnCubeClockwise() throws InvalidOrientationException {
        Color faceRight = orientation.getFaceRight();
        Color faceFront = orientation.getFaceFront();

        setOrientation(orientation.getFaceUp(), faceRight);//, faceFront);
    }

    public boolean isSolved() {
        boolean l = true;
        Iterator<Face> it = getFaceIterator();
        int i;

        while (it.hasNext() && l) {
            Face f = it.next();
            Color colorOnFace = f.getAllColors().get(0);
            i = 0;

            while (i < f.getAllColors().size() && l) {
                l = l && f.getAllColors().get(i).equals(colorOnFace);
                i++;
            }
        }

        return l;
    }

    public boolean areCounterRotations(String key, String nextKey) {
        return key.endsWith("'") && key.substring(0, 1).equals(nextKey)
                || nextKey.endsWith("'") && nextKey.substring(0, 1).equals(key);
    }
}
