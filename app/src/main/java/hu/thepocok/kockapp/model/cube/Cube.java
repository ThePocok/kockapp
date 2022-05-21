package hu.thepocok.kockapp.model.cube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;
import hu.thepocok.kockapp.model.cube.util.Orientation;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;
import hu.thepocok.kockapp.model.move.Move;
import hu.thepocok.kockapp.model.move.Reorientation;
import hu.thepocok.kockapp.model.move.Rotation;
import hu.thepocok.kockapp.model.move.Separator;
import hu.thepocok.kockapp.model.piecemap.PieceMap;

/**
 * The Cube class models an abstract Rubik's cube.
 * By default, the white faces is on top, the yellow is on bottom,
 * the red is facing towards us, on it's right side is the green face,
 * on it's left side is the blue one, and on the back, there is the orange face.
 */
public abstract class Cube implements Serializable{
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
    protected ArrayList<Separator> sections;

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
        this.sections = new ArrayList<>();
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
        this.sections = new ArrayList<>();
    }

    /**
     * Move: U
     * Turning the white face clockwise.
     * Affected layers: Whole white face, first layer of red, orange, green and blue faces.
     */
    private void rotateUpClockwise() {
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
    private void rotateUpCounterClockwise() {
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
    private void rotateDownClockwise() {
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
    private void rotateDownCounterClockwise() {
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
    private void rotateLeftClockwise() {
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
    private void rotateLeftCounterClockwise() {
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
    private void rotateRightClockwise() {
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
    private void rotateRightCounterClockwise() {
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
    private void rotateFrontClockwise() {
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
    private void rotateFrontCounterClockwise() {
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
    private void rotateBackClockwise() {
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
    private void rotateBackCounterClockwise() {
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

    public void simplifySolution() {
        for (int i = 0; i < solution.size() - 3; i++) {
            if (solution.get(i) instanceof Separator) {
                continue;
            }

            // If two reorientations were made, the first one is unnecessary
            if (solution.get(i) instanceof Reorientation && solution.get(i+1) instanceof Reorientation) {
                ((Reorientation) solution.get(i+1)).setPreviousOrientation(((Reorientation) solution.get(i)).getPreviousOrientation());
                solution.remove(i);
                i--;
                continue;
            }

            if (solution.get(i) instanceof Reorientation) {
                continue;
            }

            // If three rotation with the same key was made, they can be replaced with one counter rotation
            String key = ((Rotation) solution.get(i)).getKey();
            int j = i+1; //i+1
            while (j < i + 3 && solution.get(j) instanceof Rotation &&((Rotation) solution.get(j)).getKey().equals(key)) {
                j++;
            }

            // Must subtract one, since the loop ends one step after it found the third same rotation
            if (j == i+3) {
                if (solution.get(j-1) instanceof Rotation) {
                    String newKey = (key.endsWith("'")) ? (key.substring(0, 1)) : key + "'";

                    ((Rotation) solution.get(i)).setKey(newKey);
                    solution.remove(i+1);
                    solution.remove(i+1);
                }
            }

            // If the reason for breaking out of the loop was not that it found a reorientation, we can simplify


            // If one rotation and its counter rotation were made, they both can be eliminated
            // First, we must check, that the next step in the solution is also a rotation
            if (solution.get(i+1) instanceof Rotation) {
                key = ((Rotation) solution.get(i)).getKey();
                String nextKey = ((Rotation) solution.get(i + 1)).getKey();

                if (areCounterRotations(key, nextKey)) {
                    solution.remove(i + 1);
                    solution.remove(i);
                }
            }
        }

        if (solution.get(solution.size() - 2) instanceof Reorientation) {
            solution.remove(solution.size() - 2);
        }
    }

    public void solveBySolutionArray(ArrayList<Move> solution) throws UnsolvableCubeException {
        for (Move move : solution) {
            if (move instanceof Rotation) {
                mapKeyToRotation(((Rotation) move).getKey());
            } else if (move instanceof Reorientation) {
                Reorientation reorientation = (Reorientation) move;
                try {
                    setOrientation(reorientation.getOrientation().getFaceUp(), reorientation.getOrientation().getFaceFront());
                } catch (InvalidOrientationException e) {
                    throw new UnsolvableCubeException();
                }
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
                sb.append(reorientation).append("\n");
            } else if (m instanceof Rotation) {
                Rotation rotation = (Rotation) m;
                sb.append(rotation).append("\n");
            } else if (m instanceof Separator) {
                sb.append("##### Separator #####\n");
            }
        }

        return sb.toString();
    }

    public void turnCubeClockwise() {
        Color faceRight = orientation.getFaceRight();

        try {
            setOrientation(orientation.getFaceUp(), faceRight);
        } catch (InvalidOrientationException e) {
            e.printStackTrace(); // This will never occur
        }
    }


    public void turnCubeCounterClockwise() {
        Color faceLeft = orientation.getFaceLeft();

        try {
            setOrientation(orientation.getFaceUp(), faceLeft);
        } catch (InvalidOrientationException e) {
            e.printStackTrace(); // This will never occur
        }
    }

    public boolean isPieceOnLeftSide(Piece piece) {
        return piece.hasFace(orientation.getFaceFront()) && piece.hasFace(orientation.getFaceLeft());
    }

    public boolean isPieceOnRightSide(Piece piece) {
        return piece.hasFace(orientation.getFaceFront()) && piece.hasFace(orientation.getFaceRight());
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

    public Position getPositionByColor(Color... colors) {
        if (dimensions == 2 && colors.length != 3) {
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
        if (dimensions == 2 && colors.length != 3) {
            return null;
        }

        for (Piece piece : pieceMap.getAllPieces()) {
            Piece positionsWithColor = mapPieceToColorInPlace(piece);
            List<Color> colorsInPiece = positionsWithColor.getPositions().stream().map(Position::getColor).collect(Collectors.toList());

            if (colorsInPiece.containsAll(Arrays.asList(colors)) && colorsInPiece.size() == colors.length) {
                return piece;
            }
        }

        return null;
    }

    public Piece getPieceByFaceColor(Color... colors) {
        if (dimensions == 2 && colors.length != 3) {
            return null;
        }

        for (Piece piece : pieceMap.getAllPieces()) {
            Piece positionsWithColor = mapPieceToColorInPlace(piece);
            List<Color> colorsInPiece = positionsWithColor.getPositions().stream().map(Position::getFace).collect(Collectors.toList());

            if (colorsInPiece.containsAll(Arrays.asList(colors)) && colorsInPiece.size() == colors.length) {
                return piece;
            }
        }

        return null;
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

    public Cube duplicate() {
        if (this instanceof CubeTwo) {
            return new CubeTwo(whiteFace.duplicate(),
                    redFace.duplicate(),
                    greenFace.duplicate(),
                    orangeFace.duplicate(),
                    blueFace.duplicate(),
                    yellowFace.duplicate());
        } else {
            return new CubeThree(whiteFace.duplicate(),
                    redFace.duplicate(),
                    greenFace.duplicate(),
                    orangeFace.duplicate(),
                    blueFace.duplicate(),
                    yellowFace.duplicate());
        }
    }

    public void setOrientation(Color faceUp, Color faceFront) throws InvalidOrientationException {
        Orientation previousOrientation = new Orientation(orientation.getFaceUp(), orientation.getFaceFront());
        if (orientation.getFaceUp().equals(faceUp) && orientation.getFaceFront().equals(faceFront)) {
            return;
        }

        orientation.setOrientation(faceUp, faceFront);
        solution.add(new Reorientation(previousOrientation, orientation));
    }

    public int getDimensions() {
        return dimensions;
    }

    /**
     *
     * @param section The index of the section, beginning from 0
     * @return The nth section of the solution
     */
    public ArrayList<Move> getSolutionSection(int section) {
        ArrayList<Move> solutionSection = new ArrayList<>();
        int skippedSections = 0;
        int i = 0;

        while (i < solution.size() && skippedSections < section) {
            if (solution.get(i) instanceof Separator) {
                skippedSections++;
            }

            i++;
        }

        while (i < solution.size() && skippedSections == section) {
            if (solution.get(i) instanceof Separator) {
                skippedSections++;
                i++;
                continue;
            }

            solutionSection.add(solution.get(i));
            i++;
        }

        return solutionSection;
    }

    /**
     *
     * @param section The index of the section, beginning from 0
     * @return The solution before the given section
     */
    public ArrayList<Move> getSolutionBeforeSection(int section) {
        ArrayList<Move> solutionSection = new ArrayList<>();
        int addedSections = 0;
        int i = 0;

        while (i < solution.size() && addedSections < section) {
            if (solution.get(i) instanceof Separator) {
                addedSections++;
                i++;
                continue;
            }

            solutionSection.add(solution.get(i));
            i++;
        }

        return solutionSection;
    }

    public void addSection(int sectionID) {
        Separator separator = new Separator(sectionID);
        solution.add(separator);
        sections.add(separator);
    }

    public int getSectionCount() {
        return sections.size();
    }

    public int getIDFromSection(int currentSection) {
        return sections.get(currentSection).getSectionID();
    }

    //TODO simplify cases
    public Face getFaceWithCurrentOrientation(Color faceColor) {
        int rotationDegree = 0;
        switch (orientation.getFaceUp()) {
            case WHITE:
                switch (orientation.getFaceFront()) {
                    case RED:
                        rotationDegree = 0;
                        break;
                    case BLUE:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 90;
                                break;
                            case RED:
                            case GREEN:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 0;
                                break;
                            case YELLOW:
                                rotationDegree = 270;
                                break;
                        }
                        break;
                    case ORANGE:
                        switch (faceColor) {
                            case WHITE:
                            case YELLOW:
                                rotationDegree = 180;
                                break;
                            case RED:
                            case GREEN:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 0;
                                break;
                        }
                        break;
                    case GREEN:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 270;
                                break;
                            case RED:
                            case BLUE:
                            case ORANGE:
                            case GREEN:
                                rotationDegree = 0;
                                break;
                            case YELLOW:
                                rotationDegree = 90;
                                break;
                        }
                        break;
                }
                break;
            case RED:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                                rotationDegree = 180;
                                break;
                            case GREEN:
                                rotationDegree = 270;
                                break;
                            case ORANGE:
                            case YELLOW:
                                rotationDegree = 0;
                                break;
                            case BLUE:
                                rotationDegree = 90;
                                break;
                        }
                        break;
                    case BLUE:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 180;
                                break;
                            case RED:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 90;
                                break;
                            case GREEN:
                                rotationDegree = 270;
                                break;
                            case YELLOW:
                                rotationDegree = 0;
                                break;
                        }
                        break;
                    case YELLOW:
                        switch (faceColor) {
                            case WHITE:
                            case ORANGE:
                                rotationDegree = 180;
                                break;
                            case RED:
                            case YELLOW:
                                rotationDegree = 0;
                                break;
                            case GREEN:
                                rotationDegree = 270;
                                break;
                            case BLUE:
                                rotationDegree = 90;
                                break;
                        }
                        break;
                    case GREEN:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 180;
                                break;
                            case RED:
                            case GREEN:
                            case ORANGE:
                                rotationDegree = 270;
                                break;
                            case BLUE:
                                rotationDegree = 90;
                                break;
                            case YELLOW:
                                rotationDegree = 0;
                                break;
                        }
                        break;
                }
                break;
            case GREEN:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                            case YELLOW:
                                rotationDegree = 90;
                                break;
                            case GREEN:
                                rotationDegree = 180;
                                break;
                            case ORANGE:
                                rotationDegree = 270;
                                break;
                            case BLUE:
                                rotationDegree = 0;
                                break;
                        }
                        break;
                    case RED:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                            case GREEN:
                            case BLUE:
                            case YELLOW:
                                rotationDegree = 90;
                                break;
                            case ORANGE:
                                rotationDegree = 270;
                                break;
                        }
                        break;
                    case YELLOW:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                            case YELLOW:
                                rotationDegree = 90;
                                break;
                            case GREEN:
                                rotationDegree = 0;
                                break;
                            case ORANGE:
                                rotationDegree = 270;
                                break;
                            case BLUE:
                                rotationDegree = 180;
                                break;
                        }
                        break;
                    case ORANGE:
                        switch (faceColor) {
                            case WHITE:
                            case YELLOW:
                            case RED:
                                rotationDegree = 90;
                                break;
                            case GREEN:
                            case BLUE:
                            case ORANGE:
                                rotationDegree = 270;
                                break;
                        }
                        break;
                }
                break;
            case ORANGE:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                                rotationDegree = 0;
                                break;
                            case GREEN:
                                rotationDegree = 90;
                                break;
                            case ORANGE:
                            case YELLOW:
                                rotationDegree = 180;
                                break;
                            case BLUE:
                                rotationDegree = 270;
                                break;
                        }
                        break;
                    case GREEN:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 0;
                                break;
                            case RED:
                            case ORANGE:
                            case GREEN:
                                rotationDegree = 90;
                                break;
                            case BLUE:
                                rotationDegree = 270;
                                break;
                            case YELLOW:
                                rotationDegree = 180;
                                break;
                        }
                        break;
                    case YELLOW:
                        switch (faceColor) {
                            case WHITE:
                            case ORANGE:
                                rotationDegree = 0;
                                break;
                            case RED:
                            case YELLOW:
                                rotationDegree = 180;
                                break;
                            case GREEN:
                                rotationDegree = 90;
                                break;
                            case BLUE:
                                rotationDegree = 270;
                                break;
                        }
                        break;
                    case BLUE:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 0;
                                break;
                            case RED:
                            case BLUE:
                            case ORANGE:
                                rotationDegree = 270;
                                break;
                            case GREEN:
                                rotationDegree = 90;
                                break;
                            case YELLOW:
                                rotationDegree = 180;
                                break;
                        }
                        break;
                }
                break;
            case BLUE:
                switch (orientation.getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case WHITE:
                            case YELLOW:
                            case RED:
                                rotationDegree = 270;
                                break;
                            case GREEN:
                                rotationDegree = 0;
                                break;
                            case ORANGE:
                                rotationDegree = 90;
                                break;
                            case BLUE:
                                rotationDegree = 180;
                                break;
                        }
                        break;
                    case ORANGE:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                            case YELLOW:
                                rotationDegree = 270;
                                break;
                            case GREEN:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 90;
                                break;
                        }
                        break;
                    case YELLOW:
                        switch (faceColor) {
                            case WHITE:
                            case RED:
                            case YELLOW:
                                rotationDegree = 270;
                                break;
                            case GREEN:
                                rotationDegree = 180;
                                break;
                            case ORANGE:
                                rotationDegree = 90;
                                break;
                            case BLUE:
                                rotationDegree = 0;
                                break;
                        }
                        break;
                    case RED:
                        switch (faceColor) {
                            case WHITE:
                            case GREEN:
                            case RED:
                            case BLUE:
                            case YELLOW:
                                rotationDegree = 270;
                                break;
                            case ORANGE:
                                rotationDegree = 90;
                                break;
                        }
                        break;
                }
                break;
            case YELLOW:
                switch (orientation.getFaceFront()) {
                    case RED:
                        rotationDegree = 180;
                        break;
                    case BLUE:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 270;
                                break;
                            case RED:
                            case GREEN:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 180;
                                break;
                            case YELLOW:
                                rotationDegree = 90;
                                break;
                        }
                        break;
                    case ORANGE:
                        switch (faceColor) {
                            case WHITE:
                            case YELLOW:
                                rotationDegree = 0;
                                break;
                            case RED:
                            case GREEN:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 180;
                                break;
                        }
                        break;
                    case GREEN:
                        switch (faceColor) {
                            case WHITE:
                                rotationDegree = 90;
                                break;
                            case RED:
                            case GREEN:
                            case ORANGE:
                            case BLUE:
                                rotationDegree = 180;
                                break;
                            case YELLOW:
                                rotationDegree = 270;
                                break;
                        }
                        break;
                }
                break;
        }

        Face face = null;
        switch (rotationDegree) {
            case 0:
                if (dimensions == 2) {
                    face = new Face(faceColor, getFace(faceColor).getNthRow(0),
                            getFace(faceColor).getNthRow(1));
                } else if (dimensions == 3){
                    face = new Face(faceColor, getFace(faceColor).getNthRow(0),
                            getFace(faceColor).getNthRow(1),
                            getFace(faceColor).getNthRow(2));
                }
                break;
            case 90:
                if (dimensions == 2) {
                    face = new Face(faceColor, getFace(faceColor).getNthColumn(0).reverse(),
                            getFace(faceColor).getNthColumn(1).reverse());
                } else if(dimensions == 3) {
                    face = new Face(faceColor, getFace(faceColor).getNthColumn(0).reverse(),
                            getFace(faceColor).getNthColumn(1).reverse(),
                            getFace(faceColor).getNthColumn(2).reverse());
                }
                break;
            case 180:
                if (dimensions == 2) {
                    face = new Face(faceColor, getFace(faceColor).getNthRow(1).reverse(),
                            getFace(faceColor).getNthRow(0).reverse());
                } else if (dimensions == 3) {
                    face = new Face(faceColor, getFace(faceColor).getNthRow(2).reverse(),
                            getFace(faceColor).getNthRow(1).reverse(),
                            getFace(faceColor).getNthRow(0).reverse());
                }
                break;
            case 270:
                if (dimensions == 2) {
                    face = new Face(faceColor, getFace(faceColor).getNthColumn(1),
                            getFace(faceColor).getNthColumn(0));
                } else if (dimensions == 3) {
                    face = new Face(faceColor, getFace(faceColor).getNthColumn(2),
                            getFace(faceColor).getNthColumn(1),
                            getFace(faceColor).getNthColumn(0));
                }
                break;
        }

        return face;
    }

    public abstract boolean isValidCube();

    public abstract void solve() throws UnsolvableCubeException;

    @Override
    public String toString() {
        return "Cube: \n" +
                whiteFace.toString() + "\n" +
                redFace.toString() + "\n" +
                greenFace.toString() + "\n" +
                orangeFace.toString() + "\n" +
                blueFace.toString() + "\n" +
                yellowFace.toString();
    }
}
