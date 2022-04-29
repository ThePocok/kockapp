package hu.thepocok.kockapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import hu.thepocok.kockapp.model.cube.CubeTwo;
import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.CubeThree;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;
import hu.thepocok.kockapp.model.cube.component.Piece;
import hu.thepocok.kockapp.model.cube.component.Position;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;
import hu.thepocok.kockapp.model.move.Move;
import hu.thepocok.kockapp.model.move.Reorientation;
import hu.thepocok.kockapp.model.move.Rotation;
import hu.thepocok.kockapp.model.piecemap.CubeThreePieceMap;

public class ThreeTimesThreeCubeTest {
    private CubeThree cube;

    @Before
    public void createCube() {
        cube = new CubeThree();
    }

    @Test
    public void cubeCreationTest() {
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));
    }

    @Test
    public void rotatingUpClockwise() {
        cube.mapKeyToRotation("U");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingUpCounterclockwise() {
        cube.mapKeyToRotation("U'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingDownClockwise() {
        cube.mapKeyToRotation("D");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingDownCounterClockwise() {
        cube.mapKeyToRotation("D'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingRightClockwise() {
        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingRightCounterClockwise() {
        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingLeftClockwise() {
        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingLeftCounterClockwise() {
        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingFrontClockwise() {
        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingFrontCounterClockwise() {
        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingBackClockwise() {
        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingBackCounterClockwise() {
        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void scrambledCubeRotationTest() {
        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.YELLOW), new Layer(Color.WHITE, Color.GREEN, Color.YELLOW), new Layer(Color.WHITE, Color.GREEN, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.WHITE, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.YELLOW, Color.RED), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.GREEN, Color.WHITE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.YELLOW, Color.BLUE), new Layer(Color.GREEN, Color.RED, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE, Color.ORANGE), new Layer(Color.RED, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.ORANGE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.YELLOW, Color.BLUE), new Layer(Color.WHITE, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.ORANGE), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.RED, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.YELLOW, Color.BLUE), new Layer(Color.WHITE, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.RED, Color.BLUE), new Layer(Color.RED, Color.YELLOW, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.RED, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.YELLOW), new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.WHITE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.ORANGE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.GREEN, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.GREEN), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.RED, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.BLUE), new Layer(Color.BLUE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.BLUE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.GREEN), new Layer(Color.GREEN, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.RED, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.BLUE), new Layer(Color.BLUE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.BLUE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.GREEN), new Layer(Color.GREEN, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.GREEN), new Layer(Color.BLUE, Color.RED, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW, Color.GREEN), new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.ORANGE)), cube.getFace(Color.YELLOW));
    }

    @Test
    public void pieceMapTest() {
        CubeThreePieceMap pieceMap = new CubeThreePieceMap();
        ArrayList<Color> colors;

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 0, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 1, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 2, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 2, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 2, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.RED)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 0, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 1, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 2, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 2, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 2, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE, Color.RED)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 0, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 1, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 2, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 2, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 2, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN, Color.RED)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 0, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 1, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 2, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 2, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 2, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN, Color.ORANGE)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 0, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 1, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 2, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 2, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 2, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE, Color.ORANGE)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 0, 2)));
            Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 2, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 2, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 2, 2)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.BLUE, Color.ORANGE)));
    }

    @Test
    public void invalidCubeTest() {
        Assert.assertTrue(cube.isValidCube());
        cube.makeCubeInvalid();
        Assert.assertFalse(cube.isValidCube());
    }

    @Test
    public void whiteCrossTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertEquals(Color.WHITE, cube.getColorFromPosition(new Position(Color.YELLOW, 0, 1)));
            Assert.assertEquals(Color.WHITE, cube.getColorFromPosition(new Position(Color.YELLOW, 1, 0)));
            Assert.assertEquals(Color.WHITE, cube.getColorFromPosition(new Position(Color.YELLOW, 1, 2)));
            Assert.assertEquals(Color.WHITE, cube.getColorFromPosition(new Position(Color.YELLOW, 2, 1)));
        } catch (AssertionError e) {
            System.out.println("White cross has not been created!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void whiteCrossOnWhiteFaceTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            //cube.mapKeysToRotation("U", "F'", "L", "R", "F'", "U'", "R'", "U");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createWhiteCrossOnWhiteFace");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertEquals(Color.RED, cube.getPieceByColor(Color.WHITE, Color.RED).getPosition(Color.RED).getFace());
            Assert.assertEquals(Color.GREEN, cube.getPieceByColor(Color.WHITE, Color.GREEN).getPosition(Color.GREEN).getFace());
            Assert.assertEquals(Color.ORANGE, cube.getPieceByColor(Color.WHITE, Color.ORANGE).getPosition(Color.ORANGE).getFace());
            Assert.assertEquals(Color.BLUE, cube.getPieceByColor(Color.WHITE, Color.BLUE).getPosition(Color.BLUE).getFace());
        } catch (AssertionError e) {
            System.out.println("White cross has not been created on white face!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void solveWhiteCornersTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            //cube.mapKeysToRotation("F", "L", "F", "B'", "U'", "F", "L", "B");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createWhiteCrossOnWhiteFace");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveWhiteCorners");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertEquals(Color.RED, cube.getPieceByColor(Color.WHITE, Color.RED).getPosition(Color.RED).getFace());
            Assert.assertEquals(Color.GREEN, cube.getPieceByColor(Color.WHITE, Color.GREEN).getPosition(Color.GREEN).getFace());
            Assert.assertEquals(Color.ORANGE, cube.getPieceByColor(Color.WHITE, Color.ORANGE).getPosition(Color.ORANGE).getFace());
            Assert.assertEquals(Color.BLUE, cube.getPieceByColor(Color.WHITE, Color.BLUE).getPosition(Color.BLUE).getFace());

            Assert.assertTrue(cube.isPieceInCorrectPosition(cube.mapPieceToColorInPlace(cube.getPieceByColor(Color.WHITE, Color.RED, Color.GREEN))));
            Assert.assertTrue(cube.isPieceInCorrectPosition(cube.mapPieceToColorInPlace(cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN))));
            Assert.assertTrue(cube.isPieceInCorrectPosition(cube.mapPieceToColorInPlace(cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE))));
            Assert.assertTrue(cube.isPieceInCorrectPosition(cube.mapPieceToColorInPlace(cube.getPieceByColor(Color.WHITE, Color.RED, Color.BLUE))));
        } catch (AssertionError e) {
            System.out.println("White corners has not been solved, or white cross did not remain intact!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void solveMiddleLayerTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
//            cube.mapKeysToRotation("B", "L", "B", "B'", "L", "F'", "B'", "D");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createWhiteCrossOnWhiteFace");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveWhiteCorners");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveMiddleLayer");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            ArrayList<Piece> sidePieces = cube.getSideMiddlePieces();

            for (Piece piece : sidePieces) {
                for (Position position : piece.getPositions()) {
                    Assert.assertEquals(position.getFace(), position.getColor());
                }
            }
        } catch (AssertionError e) {
            System.out.println("Middle layer has not been solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void solveYellowCrossTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
//            cube.mapKeysToRotation("F'", "R", "D'", "D'", "L", "B'", "L'", "F");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createWhiteCrossOnWhiteFace");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveWhiteCorners");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveMiddleLayer");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createYellowCross");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertTrue(cube.getFaceWithCurrentOrientation(Color.YELLOW).matchPattern("_", "YELLOW", "_", "YELLOW", "YELLOW", "YELLOW", "_", "YELLOW", "_"));
        } catch (AssertionError e) {
            System.out.println("Yellow cross has not been created!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void solveYellowCornersTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
//            cube.mapKeysToRotation("F'", "R", "D'", "D'", "L", "B'", "L'", "F");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createWhiteCrossOnWhiteFace");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveWhiteCorners");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveMiddleLayer");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createYellowCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("completeYellowFace");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertEquals(9, cube.getFace(Color.YELLOW).getColorCount(Color.YELLOW));
        } catch (AssertionError e) {
            System.out.println("Yellow corners has not been turned up!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void repositionYellowCornersTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
//            cube.mapKeysToRotation("D", "R", "R", "R", "D'", "F'", "L", "L");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        Method method = CubeThree.class.getDeclaredMethod("createWhiteCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createWhiteCrossOnWhiteFace");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveWhiteCorners");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("solveMiddleLayer");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("createYellowCross");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("completeYellowFace");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeThree.class.getDeclaredMethod("repositionYellowCorners");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertEquals(9, cube.getFace(Color.YELLOW).getColorCount(Color.YELLOW));
            Assert.assertTrue(cube.getFace(cube.getOrientation().getFaceFront()).getColorCount(cube.getOrientation().getFaceFront()) >= 8);
            Assert.assertTrue(cube.getFace(cube.getOrientation().getFaceLeft()).getColorCount(cube.getOrientation().getFaceLeft()) >= 8);
            Assert.assertTrue(cube.getFace(cube.getOrientation().getFaceBack()).getColorCount(cube.getOrientation().getFaceBack()) >= 8);
            Assert.assertTrue(cube.getFace(cube.getOrientation().getFaceRight()).getColorCount(cube.getOrientation().getFaceRight()) >= 8);
        } catch (AssertionError e) {
            System.out.println("Yellow corners has not been solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void solveWholeCubeTest() throws UnsolvableCubeException {
        if (cube.isSolved()) {
//            cube.mapKeysToRotation("F", "U'", "B", "U'", "U", "D'", "D", "R");
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }
        cube.getSolution().clear();

        cube.solve();

        try {
            Assert.assertTrue(cube.isSolved());
        } catch (AssertionError e) {
            System.out.println("Cube has not been solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void hundredRandomTest() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, UnsolvableCubeException {
        for(int i = 0; i < 100; i++) {
            System.out.println("Teszt " + (i+1));
            cube = new CubeThree();
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);

            whiteCrossTest();
            whiteCrossOnWhiteFaceTest();
            solveWhiteCornersTest();
            solveMiddleLayerTest();
            solveYellowCrossTest();
            solveYellowCornersTest();
            repositionYellowCornersTest();
            solveWholeCubeTest();
        }
    }
}
