package hu.aranyosipeter.kockapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import hu.aranyosipeter.kockapp.model.cube.component.Color;
import hu.aranyosipeter.kockapp.model.cube.CubeTwo;
import hu.aranyosipeter.kockapp.model.piecemap.CubeTwoPieceMap;
import hu.aranyosipeter.kockapp.model.cube.component.Face;
import hu.aranyosipeter.kockapp.model.cube.component.Layer;
import hu.aranyosipeter.kockapp.model.move.Move;
import hu.aranyosipeter.kockapp.model.cube.component.Piece;
import hu.aranyosipeter.kockapp.model.cube.component.Position;
import hu.aranyosipeter.kockapp.model.move.Reorientation;
import hu.aranyosipeter.kockapp.model.move.Rotation;
import hu.aranyosipeter.kockapp.model.exception.UnsolvableCubeException;

public class TwoTimesTwoCubeTest {
    private CubeTwo cube;

    @Before
    public void createCube() {
        cube = new CubeTwo();
    }

    @Test
    public void cubeCreationTest() {
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));
    }

    @Test
    public void rotatingUpClockwise() {
        cube.mapKeyToRotation("U");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingUpCounterclockwise() {
        cube.mapKeyToRotation("U'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingDownClockwise() {
        cube.mapKeyToRotation("D");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingDownCounterClockwise() {
        cube.mapKeyToRotation("D'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingRightClockwise() {
        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingRightCounterClockwise() {
        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("R'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingLeftClockwise() {
        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingLeftCounterClockwise() {
        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingFrontClockwise() {
        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingFrontCounterClockwise() {
        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingBackClockwise() {
        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingBackCounterClockwise() {
        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void scrambledCubeRotationTest() {
        cube.mapKeyToRotation("R'");
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("F");
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("L");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("D");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("B'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("U");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("R");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.GREEN, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("F'");
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("L'");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.WHITE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("U'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("D'");
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.mapKeyToRotation("B");
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());
    }

    @Test
    public void pieceMapTest() {
        CubeTwoPieceMap pieceMap = new CubeTwoPieceMap();
        ArrayList<Color> colors;

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.WHITE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.RED)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.RED, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.GREEN, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.GREEN)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.ORANGE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.GREEN)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.BLUE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.BLUE)));


        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceByPosition(new Position(Color.YELLOW, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.BLUE)));
    }

    @Test
    public void invalidCubeTest() {
        Assert.assertTrue(cube.isValidCube());
        cube.makeCubeInvalid();
        Assert.assertFalse(cube.isValidCube());

        cube = new CubeTwo(Face.generateFace(Color.YELLOW, 2),
                Face.generateFace(Color.RED, 2),
                Face.generateFace(Color.GREEN, 2),
                Face.generateFace(Color.ORANGE, 2),
                Face.generateFace(Color.BLUE, 2),
                Face.generateFace(Color.YELLOW, 2));
        Assert.assertFalse(cube.isValidCube());
    }

    @Test
    public void pieceAdjacencyTest() {
        Piece piece1 = cube.getPieceByColor(Color.BLUE, Color.WHITE, Color.RED);
        Piece piece2 = cube.getPieceByColor(Color.BLUE, Color.WHITE, Color.ORANGE);

        Assert.assertTrue(piece1.isAdjacent(piece2, Color.WHITE));
        Assert.assertTrue(piece1.isAdjacent(piece2, Color.BLUE));
        Assert.assertTrue(piece1.isAdjacent(piece2, Color.WHITE, Color.BLUE));

        piece2 = cube.getPieceByColor(Color.WHITE, Color.GREEN, Color.RED);
        Assert.assertTrue(piece1.isAdjacent(piece2, Color.WHITE));
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.BLUE));
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.WHITE, Color.BLUE));

        piece2 = cube.getPieceByColor(Color.WHITE, Color.GREEN, Color.ORANGE);
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.WHITE));
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.RED));
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.WHITE, Color.RED));

        piece2 = cube.getPieceByColor(Color.YELLOW, Color.RED, Color.BLUE);
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.WHITE));
        Assert.assertFalse(piece1.isAdjacent(piece2, Color.YELLOW));
        Assert.assertTrue(piece1.isAdjacent(piece2, Color.RED));
        Assert.assertTrue(piece1.isAdjacent(piece2, Color.BLUE));
        Assert.assertTrue(piece1.isAdjacent(piece2, Color.BLUE, Color.RED));
    }

    @Test
    public void simplifySolutionTest() throws UnsolvableCubeException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        cube.randomScramble(8);
        ArrayList<Move> scramble = cube.getSolution();

        cube.getSolution().clear();

        Method method = CubeTwo.class.getDeclaredMethod("setInitialOrientation");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveSecondPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveThirdPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveFourthPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("moveYellowTilesUp");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("swapNotMatchingPieces");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertTrue(cube.isSolved());

            cube.getSolution().clear();
            cube.solveBySolutionArray(scramble);
            cube.solve();
            Assert.assertTrue(cube.isSolved());
        } catch (AssertionError e) {
            System.out.println("The simplified solution cannot solve the cube!");

            System.out.println("Scramble:");
            System.out.println(scramble);

            System.out.println("Solution:");
            System.out.println(cube.getSolutionString());
            throw e;
        }
    }

    @Test
    public void initialOrientationTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }

        cube.getSolution().clear();
        Method method = CubeTwo.class.getDeclaredMethod("setInitialOrientation");
        method.setAccessible(true);

        method.invoke(cube);

        Piece referencePiece = cube.findReferencePiece();
        try {
            Assert.assertNotNull(referencePiece);
            Assert.assertEquals(cube.getOrientation().getFaceUp(), referencePiece.getPosition(Color.WHITE).getFace());
            Assert.assertEquals(cube.getOrientation().getFaceFront(), referencePiece.getPosition(Color.RED).getFace());
        } catch (AssertionError e) {
            System.out.println("Second piece is not solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void secondPieceSolutionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }

        cube.getSolution().clear();
        Method method = CubeTwo.class.getDeclaredMethod("setInitialOrientation");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveSecondPiece");
        method.setAccessible(true);

        method.invoke(cube);

        Piece referencePiece = cube.findReferencePiece();
        Piece secondPiece = cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
        try {
            Assert.assertNotNull(referencePiece);
            Assert.assertTrue(referencePiece.isAdjacent(secondPiece, Color.WHITE, Color.BLUE));
        } catch (AssertionError e) {
            System.out.println("Second piece is not solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void thirdPieceSolutionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }

        cube.getSolution().clear();
        Method method = CubeTwo.class.getDeclaredMethod("setInitialOrientation");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveSecondPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveThirdPiece");
        method.setAccessible(true);

        method.invoke(cube);

        Piece secondPiece = cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.BLUE);
        Piece thirdPiece = cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
        try {
            Assert.assertNotNull(secondPiece);
            Assert.assertTrue(secondPiece.isAdjacent(thirdPiece, Color.WHITE, Color.ORANGE));
        } catch (AssertionError e) {
            System.out.println("Third piece is not solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void fourthPieceSolutionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }

        cube.getSolution().clear();
        Method method = CubeTwo.class.getDeclaredMethod("setInitialOrientation");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveSecondPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveThirdPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveFourthPiece");
        method.setAccessible(true);

        method.invoke(cube);

        Piece thirdPiece = cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
        Piece fourthPiece = cube.getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

        try {
            Assert.assertNotNull(thirdPiece);
            Assert.assertTrue(thirdPiece.isAdjacent(fourthPiece, Color.WHITE, Color.GREEN));

            Color whiteFaceColor = thirdPiece.getPosition(Color.WHITE).getFace();
            cube.getFace(whiteFaceColor).matchPattern("WHITE", "WHITE", "WHITE", "WHITE");
        } catch (AssertionError e) {
            System.out.println("Third piece is not solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void yellowFaceTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }

        cube.getSolution().clear();
        Method method = CubeTwo.class.getDeclaredMethod("setInitialOrientation");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveSecondPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveThirdPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("solveFourthPiece");
        method.setAccessible(true);

        method.invoke(cube);

        method = CubeTwo.class.getDeclaredMethod("moveYellowTilesUp");
        method.setAccessible(true);

        method.invoke(cube);

        try {
            Assert.assertEquals(4, cube.getFace(cube.getOrientation().getFaceUp()).getColorCount(Color.YELLOW));
            Assert.assertEquals(4, cube.getFace(cube.getOrientation().getFaceDown()).getColorCount(Color.WHITE));
        } catch (AssertionError e) {
            System.out.println("Yellow face is unsolved or white face did not remain intact!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
    }

    @Test
    public void solveWholeCubeTest() throws UnsolvableCubeException {
        if (cube.isSolved()) {
            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);
        }

        cube.solve();

        try {
            Assert.assertTrue(cube.isSolved());
        } catch (AssertionError e) {
            System.out.println("Cube is unsolved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
        System.out.println(cube);
    }

    @Test
    public void hundredRandomTest() throws UnsolvableCubeException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for(int i = 0; i < 100; i++) {
            System.out.println("Teszt " + (i+1));
            cube = new CubeTwo();

            cube.randomScramble(8);
            ArrayList<Move> scramble = cube.getSolution();
            System.out.println("Scramble:");
            StringBuilder sb = new StringBuilder();
            for (Move m : scramble) {
                if (m instanceof Reorientation) {
                    Reorientation reorientation = (Reorientation) m;
                    sb.append(reorientation + "\n");
                } else if (m instanceof Rotation) {
                    Rotation rotation = (Rotation) m;
                    sb.append(rotation + "\n");
                }
            }
            System.out.println(sb);
            System.out.println(cube);

            initialOrientationTest();
            secondPieceSolutionTest();
            thirdPieceSolutionTest();
            fourthPieceSolutionTest();
            yellowFaceTest();
            solveWholeCubeTest();
        }
    }
}
