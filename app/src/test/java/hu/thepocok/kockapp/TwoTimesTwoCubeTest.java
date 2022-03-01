package hu.thepocok.kockapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.thepocok.kockapp.model.Color;
import hu.thepocok.kockapp.model.CubeTwo;
import hu.thepocok.kockapp.model.Face;
import hu.thepocok.kockapp.model.Layer;

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

        System.out.println(cube.toString());
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

        System.out.println(cube.toString());
    }

    @Test
    public void rotatingDownCounterClockwise() {
        cube.rotateDownCounterClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.BLUE));
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
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownCounterClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));

        System.out.println(cube.toString());
    }

    @Test
    public void rotatingDownClockwise() {
        cube.rotateDownClockwise();
        Assert.assertEquals(Face.generateFace(Color.WHITE, 2), cube.getFace(Color.WHITE));
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.RED, Color.RED)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.BLUE));
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
        Assert.assertEquals(new Face(new Layer(Color.RED, Color.RED), new Layer(Color.GREEN, Color.GREEN)), cube.getFace(Color.RED));
        Assert.assertEquals(new Face(new Layer(Color.GREEN, Color.GREEN), new Layer(Color.ORANGE, Color.ORANGE)), cube.getFace(Color.GREEN));
        Assert.assertEquals(new Face(new Layer(Color.ORANGE, Color.ORANGE), new Layer(Color.BLUE, Color.BLUE)), cube.getFace(Color.ORANGE));
        Assert.assertEquals(new Face(new Layer(Color.BLUE, Color.BLUE), new Layer(Color.RED, Color.RED)), cube.getFace(Color.BLUE));
        Assert.assertEquals(Face.generateFace(Color.YELLOW, 2), cube.getFace(Color.YELLOW));

        cube.rotateDownClockwise();
        Assert.assertEquals(cube.getFace(Color.WHITE), Face.generateFace(Color.WHITE, 2));
        Assert.assertEquals(cube.getFace(Color.RED), Face.generateFace(Color.RED, 2));
        Assert.assertEquals(cube.getFace(Color.GREEN), Face.generateFace(Color.GREEN, 2));
        Assert.assertEquals(cube.getFace(Color.ORANGE), Face.generateFace(Color.ORANGE, 2));
        Assert.assertEquals(cube.getFace(Color.BLUE), Face.generateFace(Color.BLUE, 2));
        Assert.assertEquals(cube.getFace(Color.YELLOW), Face.generateFace(Color.YELLOW, 2));

        System.out.println(cube.toString());
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

        System.out.println(cube.toString());
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

        System.out.println(cube.toString());
    }
}
