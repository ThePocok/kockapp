package hu.thepocok.kockapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

import hu.thepocok.kockapp.model.Color;
import hu.thepocok.kockapp.model.Cube;
import hu.thepocok.kockapp.model.CubeTwo;
import hu.thepocok.kockapp.model.CubeTwoPieceMap;
import hu.thepocok.kockapp.model.Face;
import hu.thepocok.kockapp.model.Layer;
import hu.thepocok.kockapp.model.Move;
import hu.thepocok.kockapp.model.Piece;
import hu.thepocok.kockapp.model.Position;
import hu.thepocok.kockapp.model.Reorientation;
import hu.thepocok.kockapp.model.Rotation;
import hu.thepocok.kockapp.model.exception.UnsolvableCubeException;

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
    public void rotatingUpCounterclockwise() {
        cube.rotateUpCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingUpClockwise() {
        cube.rotateUpClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();

        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingDownClockwise() {
        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingDownCounterClockwise() {
        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingRightCounterClockwise() {
        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.rotateRightCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingRightClockwise() {
        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.RED, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingLeftClockwise() {
        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingLeftCounterClockwise() {
        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingFrontClockwise() {
        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingFrontCounterClockwise() {
        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingBackClockwise() {
        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void rotatingBackCounterClockwise() {
        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 2), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 2), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));
    }

    @Test
    public void scrambledCubeRotationTest() {
        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 2), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 2), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateDownClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE), new Layer(Color.YELLOW, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateUpClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.GREEN, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED), new Layer(Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE), new Layer(Color.GREEN, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE), new Layer(Color.WHITE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE), new Layer(Color.WHITE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.GREEN)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE), new Layer(Color.RED, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.WHITE, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.YELLOW));
        Assert.assertTrue(cube.isValidCube());

        cube.rotateBackClockwise();
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

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.WHITE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.WHITE, 0, 1)));
        System.out.println(colors);
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.ORANGE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.WHITE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.GREEN, Color.RED)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.WHITE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.BLUE, Color.RED)));


        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.RED, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.RED, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.RED, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.RED, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE)));


        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.GREEN, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.GREEN, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.GREEN, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.GREEN, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.GREEN)));


        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.ORANGE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.ORANGE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.ORANGE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.ORANGE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.GREEN)));


        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.BLUE, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.BLUE, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.WHITE, Color.ORANGE, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.BLUE, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.BLUE, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.BLUE)));


        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.YELLOW, 0, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.YELLOW, 0, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.YELLOW, 1, 0)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.GREEN)));

        colors = cube.mapPieceToColor(pieceMap.getPieceColorPositions(new Position(Color.YELLOW, 1, 1)));
        Assert.assertTrue(colors.containsAll(Arrays.asList(Color.YELLOW, Color.ORANGE, Color.BLUE)));
    }

    @Test
    public void invalidCubeTest() {
        Assert.assertTrue(cube.isValidCube());
        cube.makeCubeInvalid();
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
    public void simplifySolutionTest() throws NoSuchFieldException {
        cube.mapKeysToRotation("D", "D", "D");
        cube.mapKeysToRotation("B'", "B'", "B'");
        cube.mapKeysToRotation("F'", "F", "F'");
        cube.mapKeysToRotation("U", "U'", "U");
        cube.mapKeysToRotation("R", "L", "R");
        cube.simplifySolution();

        Field field = Cube.class.getDeclaredField("solution");
        field.setAccessible(true);
        ArrayList<Move> solution = cube.getSolution(); //(ArrayList<Move>) field.get(cube);
        System.out.println(solution);
        Assert.assertTrue(((Rotation) solution.get(0)).getKey().equals("D'"));
        Assert.assertTrue(((Rotation) solution.get(1)).getKey().equals("B"));
        Assert.assertTrue(((Rotation) solution.get(2)).getKey().equals("F'"));
        Assert.assertTrue(((Rotation) solution.get(3)).getKey().equals("F"));
        Assert.assertTrue(((Rotation) solution.get(4)).getKey().equals("F'"));
        Assert.assertTrue(((Rotation) solution.get(5)).getKey().equals("U"));
        Assert.assertTrue(((Rotation) solution.get(6)).getKey().equals("U'"));
        Assert.assertTrue(((Rotation) solution.get(7)).getKey().equals("U"));
        Assert.assertTrue(((Rotation) solution.get(8)).getKey().equals("R"));
        Assert.assertTrue(((Rotation) solution.get(9)).getKey().equals("L"));
        Assert.assertTrue(((Rotation) solution.get(10)).getKey().equals("R"));
    }

    @Test
    public void randomScrambledSecondPieceSolutionTest() throws UnsolvableCubeException {
        //cube.mapKeysToRotation("U'", "U", "F", "D'", "L'", "B'"); //simplify error
        cube.randomScramble(6);
        ArrayList<Move> scramble = cube.getSolution();
        System.out.println("Scramble:");
        StringBuilder sb = new StringBuilder();
        sb.append("Solution:\n");
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

        cube.solve();

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
    public void randomScrambledThirdPieceSolutionTest() throws UnsolvableCubeException {
        //cube.mapKeysToRotation("B'", "B", "L'", "F'", "U", "L");
        cube.randomScramble(6);
        ArrayList<Move> scramble = cube.getSolution();
        System.out.println("Scramble:");
        StringBuilder sb = new StringBuilder();
        sb.append("Solution:\n");
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

        cube.solve();

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
    public void randomScrambledFourthPieceSolutionTest() throws UnsolvableCubeException {
        //cube.mapKeysToRotation("L", "B'", "D", "B'", "U'", "B");
        cube.randomScramble(6);
        ArrayList<Move> scramble = cube.getSolution();
        System.out.println("Scramble:");
        StringBuilder sb = new StringBuilder();
        sb.append("Solution:\n");
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

        cube.solve();

        Piece thirdPiece = cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
        Piece fourthPiece = cube.getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

        try {
            Assert.assertNotNull(thirdPiece);
            Assert.assertTrue(thirdPiece.isAdjacent(fourthPiece, Color.WHITE, Color.GREEN));
        } catch (AssertionError e) {
            System.out.println("Third piece is not solved!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
        System.out.println(cube);
    }

    @Test
    public void randomScrambledYellowFaceTest() throws UnsolvableCubeException {
        //cube.mapKeysToRotation("L", "B'", "D", "B'", "U'", "B");
        cube.randomScramble(6);
        ArrayList<Move> scramble = cube.getSolution();
        System.out.println("Scramble:");
        StringBuilder sb = new StringBuilder();
        sb.append("Solution:\n");
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

        cube.solve();

        try {
            Assert.assertEquals(4, cube.getFace(cube.getOrientation().getFaceUp()).getColorCount(Color.YELLOW));
            Assert.assertEquals(4, cube.getFace(cube.getOrientation().getFaceDown()).getColorCount(Color.WHITE));
        } catch (AssertionError e) {
            System.out.println("Yellow face is unsolved or white face did not remain intact!");

            System.out.println(cube.getSolutionString());
            throw e;
        }

        System.out.println(cube.getSolutionString());
        System.out.println(cube);
    }

    @Test
    public void hundredRandomTest() throws UnsolvableCubeException{
        for(int i = 0; i < 100; i++) {
            System.out.println("Teszt " + (i+1));
            cube = new CubeTwo();
            randomScrambledSecondPieceSolutionTest();

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

            thirdPiece = cube.getPieceByColor(Color.WHITE, Color.ORANGE, Color.GREEN);
            Piece fourthPiece = cube.getPieceByColor(Color.WHITE, Color.RED, Color.GREEN);

            try {
                Assert.assertNotNull(thirdPiece);
                Assert.assertTrue(thirdPiece.isAdjacent(fourthPiece, Color.WHITE, Color.GREEN));
            } catch (AssertionError e) {
                System.out.println("Third piece is not solved!");

                System.out.println(cube.getSolutionString());
                throw e;
            }

            try {
                Assert.assertEquals(4, cube.getFace(cube.getOrientation().getFaceUp()).getColorCount(Color.YELLOW));
                Assert.assertEquals(4, cube.getFace(cube.getOrientation().getFaceDown()).getColorCount(Color.WHITE));
            } catch (AssertionError e) {
                System.out.println("Yellow face is unsolved or white face did not remain intact!");

                System.out.println(cube.getSolutionString());
                throw e;
            }
        }
    }
}
