package hu.thepocok.kockapp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.opencv.core.Point;

import java.util.ArrayList;

import hu.thepocok.kockapp.model.cube.component.Color;

public class CubeTileOverlayView extends View {
    private final String TAG = "CubeTileOverlayView";
    private final int BORDERWIDTH = 10;
    private final int TILESIZE2 = 250;
    private final int TILESIZE3 = 150;

    private int width = 0;
    private int height = 0;
    private int rotationDegree = 0;
    private boolean isRotationDegreeSet = false;
    private Point centerPoint;

    private ArrayList<Color> tileColors = new ArrayList<>();

    private boolean isTwoTimesTwo = false;

    private Point[] cubeThreePieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
            new Point(0, -1), new Point(0, 0), new Point(0, 1),
            new Point(1, -1), new Point(1, 0), new Point(1, 1)
    };

    private Point[] cubeTwoPieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 0),
            new Point(0, -1), new Point(0, 0)
    };

    public CubeTileOverlayView(Context context, AttributeSet attr) {
        super(context, attr);

        for (int i = 0; i < cubeThreePieceOffset.length; i++) {
            tileColors.add(Color.EMPTY);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDERWIDTH);
        int arrayLength = (isTwoTimesTwo) ? cubeTwoPieceOffset.length : cubeThreePieceOffset.length;

        for (int i = 0; i < arrayLength; i++) {
            int cubeDimensions = (isTwoTimesTwo) ? 2 : 3;
            int row = i / cubeDimensions;
            int col = i % cubeDimensions;
            int rotatedIndex;

            switch (rotationDegree) {
                case 90:
                    rotatedIndex = (cubeDimensions * col) + (cubeDimensions - row - 1);
                    break;
                case 180:
                    rotatedIndex = (isTwoTimesTwo) ? 3-i : 8-i;
                    break;
                case 270:
                    rotatedIndex = (cubeDimensions * (cubeDimensions - col - 1)) + row;
                    break;
                default:
                    rotatedIndex = i;
            }

            paint.setColor(android.graphics.Color.rgb(tileColors.get(rotatedIndex).redValue,
                    tileColors.get(rotatedIndex).greenValue,
                    tileColors.get(rotatedIndex).blueValue));

            if (isTwoTimesTwo) {
                Point tileOffset = cubeTwoPieceOffset[i];

                canvas.drawRect((float) (tileOffset.x * TILESIZE2 + centerPoint.x),
                        (float) (tileOffset.y * TILESIZE2 + centerPoint.y),
                        (float) ((tileOffset.x + 1) * TILESIZE2 + centerPoint.x),
                        (float) ((tileOffset.y + 1) * TILESIZE2 + centerPoint.y),
                        paint);
            } else {
                Point tileOffset = cubeThreePieceOffset[i];

                canvas.drawRect((float) (tileOffset.x * TILESIZE3 + centerPoint.x - TILESIZE3 / 2),
                        (float) (tileOffset.y * TILESIZE3 + centerPoint.y - TILESIZE3 / 2),
                        (float) (tileOffset.x * TILESIZE3 + centerPoint.x + TILESIZE3 / 2),
                        (float) (tileOffset.y * TILESIZE3 + centerPoint.y + TILESIZE3 / 2),
                        paint);
            }
        }

    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        width = xNew;
        height = yNew;

        Log.d(TAG, "Width: " + width + " Height: " + height);

        centerPoint = new Point(xNew / 2.0f, yNew / 2.0f);
    }

    public Point[] getAnalyzableSquareCoordinates(int imageWidth, int imageHeight, Point imageCenterPoint, int index) {
        int cubeDimensions = (isTwoTimesTwo) ? 2 : 3;
        int row = index / cubeDimensions;
        int col = index % cubeDimensions;
        int rotatedIndex = -1;

        switch (rotationDegree) {
            case 90:
                rotatedIndex = (cubeDimensions * col) + (cubeDimensions - row - 1);
                break;
            case 180:
                rotatedIndex = (isTwoTimesTwo) ? 3-index : 8-index;
                break;
            case 270:
                rotatedIndex = (cubeDimensions * (cubeDimensions - col - 1)) + row;
                break;
        }

        Point tileOffset = (isTwoTimesTwo) ? cubeTwoPieceOffset[rotatedIndex] : cubeThreePieceOffset[rotatedIndex];

        double normalizedTileWidth;
        double normalizedTileHeight;
        Point topLeft;
        Point bottomRight;

        if (isTwoTimesTwo) {
            normalizedTileWidth = (TILESIZE2 / (double) width) * imageWidth;
            normalizedTileHeight = (TILESIZE2 / (double) height) * imageHeight;

            topLeft = new Point((tileOffset.x * normalizedTileWidth) + imageCenterPoint.x, (tileOffset.y * normalizedTileHeight) + imageCenterPoint.y);
            bottomRight = new Point(((tileOffset.x + 1) * normalizedTileWidth) + imageCenterPoint.x, ((tileOffset.y + 1) * normalizedTileHeight) + imageCenterPoint.y);
        } else {
            normalizedTileWidth = (TILESIZE3 / (double) width) * imageWidth;
            normalizedTileHeight = (TILESIZE3 / (double) height) * imageHeight;

            topLeft = new Point((tileOffset.x * normalizedTileWidth) + imageCenterPoint.x - (normalizedTileWidth / 2), (tileOffset.y * normalizedTileHeight) + imageCenterPoint.y - (normalizedTileHeight / 2));
            bottomRight = new Point((tileOffset.x * normalizedTileWidth) + imageCenterPoint.x + (normalizedTileWidth / 2), (tileOffset.y * normalizedTileHeight) + imageCenterPoint.y + (normalizedTileHeight / 2));
        }

        return new Point[]{topLeft, bottomRight, new Point(index, rotatedIndex)};
    }

    public void setTileColors(ArrayList<Color> tileColors) {
        this.tileColors.clear();
        this.tileColors = (ArrayList<Color>) tileColors.clone();

        invalidate();
    }

    public void setTwoTimesTwo(boolean isTwoTimesTwo) {
        this.isTwoTimesTwo = isTwoTimesTwo;

        tileColors.clear();
        if (isTwoTimesTwo) {
            for (int i = 0; i < cubeTwoPieceOffset.length; i++) {
                tileColors.add(Color.EMPTY);
            }
        } else {
            for (int i = 0; i < cubeThreePieceOffset.length; i++) {
                tileColors.add(Color.EMPTY);
            }
        }
        invalidate();
    }

    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

    public boolean isRotationDegreeSet() {
        return isRotationDegreeSet;
    }
}
