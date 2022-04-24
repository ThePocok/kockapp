package hu.thepocok.kockapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.CubeThree;
import hu.thepocok.kockapp.model.cube.component.Face;
import hu.thepocok.kockapp.model.cube.component.Layer;

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
    public void rotatingUpCounterclockwise() {
        cube.rotateUpCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingUpClockwise() {
        cube.rotateUpClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingDownClockwise() {
        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingDownCounterClockwise() {
        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 3), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 3), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingRightCounterClockwise() {
        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.rotateRightCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingRightClockwise() {
        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.RED, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE), new Layer(Color.WHITE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE), new Layer(Color.YELLOW, Color.YELLOW, Color.ORANGE)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE), new Layer(Color.RED, Color.RED, Color.ORANGE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.RED, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingLeftClockwise() {
        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingLeftCounterClockwise() {
        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED), new Layer(Color.YELLOW, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE), new Layer(Color.ORANGE, Color.ORANGE, Color.WHITE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED), new Layer(Color.ORANGE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED), new Layer(Color.ORANGE, Color.ORANGE, Color.RED)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingFrontClockwise() {
        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingFrontCounterClockwise() {
        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.WHITE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.GREEN, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingBackClockwise() {
        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void rotatingBackCounterClockwise() {
        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE), new Layer(Color.BLUE, Color.BLUE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN), new Layer(Color.BLUE, Color.BLUE, Color.GREEN)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(Face.generateFace(Color.RED, 3), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(Face.generateFace(Color.ORANGE, 3), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 3));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 3));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 3));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 3));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 3));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 3));
    }

    @Test
    public void scrambledCubeRotationTest() {
        cube.rotateRightCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(Face.generateFace(Color.GREEN, 3), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(Face.generateFace(Color.BLUE, 3), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateBackClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE), new Layer(Color.RED, Color.RED, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateFrontClockwise();
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.RED, Color.RED, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.YELLOW), new Layer(Color.WHITE, Color.GREEN, Color.YELLOW), new Layer(Color.WHITE, Color.GREEN, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE, Color.BLUE), new Layer(Color.YELLOW, Color.YELLOW, Color.RED), new Layer(Color.GREEN, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateLeftClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.WHITE, Color.WHITE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.YELLOW, Color.RED), new Layer(Color.WHITE, Color.GREEN, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.YELLOW, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.GREEN, Color.WHITE, Color.WHITE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.YELLOW, Color.BLUE), new Layer(Color.GREEN, Color.RED, Color.BLUE)), cube.getFace(Color.YELLOW));

        cube.rotateBackCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.WHITE), new Layer(Color.ORANGE, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.GREEN, Color.GREEN)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE, Color.ORANGE), new Layer(Color.RED, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.YELLOW, Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.ORANGE), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.YELLOW, Color.BLUE), new Layer(Color.WHITE, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateUpClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.ORANGE), new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.RED, Color.YELLOW, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.RED, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE, Color.ORANGE), new Layer(Color.YELLOW, Color.ORANGE, Color.BLUE), new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.BLUE), new Layer(Color.GREEN, Color.WHITE, Color.BLUE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.RED), new Layer(Color.GREEN, Color.YELLOW, Color.BLUE), new Layer(Color.WHITE, Color.YELLOW, Color.RED)), cube.getFace(Color.YELLOW));

        cube.rotateRightClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.BLUE, Color.RED), new Layer(Color.WHITE, Color.RED, Color.BLUE), new Layer(Color.RED, Color.YELLOW, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.RED), new Layer(Color.RED, Color.GREEN, Color.GREEN), new Layer(Color.BLUE, Color.ORANGE, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.WHITE, Color.GREEN), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW), new Layer(Color.BLUE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.RED, Color.YELLOW), new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateFrontCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.WHITE, Color.RED), new Layer(Color.GREEN, Color.WHITE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.BLUE, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.RED, Color.YELLOW), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.ORANGE, Color.GREEN)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.ORANGE), new Layer(Color.GREEN, Color.ORANGE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE, Color.ORANGE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.GREEN, Color.BLUE), new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.WHITE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateLeftCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.WHITE, Color.RED), new Layer(Color.WHITE, Color.WHITE, Color.BLUE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.GREEN, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.GREEN), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.RED, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.BLUE), new Layer(Color.BLUE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateUpCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.BLUE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.GREEN), new Layer(Color.GREEN, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.BLUE, Color.RED, Color.BLUE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.GREEN, Color.BLUE), new Layer(Color.BLUE, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.YELLOW, Color.GREEN)), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.RED, Color.BLUE), new Layer(Color.ORANGE, Color.WHITE, Color.WHITE), new Layer(Color.RED, Color.BLUE, Color.WHITE)), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.ORANGE, Color.GREEN), new Layer(Color.GREEN, Color.RED, Color.YELLOW), new Layer(Color.WHITE, Color.BLUE, Color.YELLOW)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.WHITE, Color.WHITE, Color.GREEN), new Layer(Color.RED, Color.GREEN, Color.ORANGE), new Layer(Color.WHITE, Color.WHITE, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.YELLOW, Color.WHITE, Color.GREEN), new Layer(Color.GREEN, Color.ORANGE, Color.GREEN), new Layer(Color.BLUE, Color.RED, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.BLUE, Color.RED), new Layer(Color.RED, Color.BLUE, Color.YELLOW), new Layer(Color.ORANGE, Color.ORANGE, Color.YELLOW)), cube.getFace(Color.BLUE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.YELLOW, Color.GREEN), new Layer(Color.GREEN, Color.YELLOW, Color.YELLOW), new Layer(Color.ORANGE, Color.BLUE, Color.ORANGE)), cube.getFace(Color.YELLOW));
    }
}
