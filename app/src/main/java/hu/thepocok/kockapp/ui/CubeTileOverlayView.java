package hu.thepocok.kockapp.ui;

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
    private final int TILESIZE = 150;

    private int width = 0;
    private int height = 0;
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
            paint.setColor(android.graphics.Color.rgb(tileColors.get(i).redValue,
                    tileColors.get(i).greenValue,
                    tileColors.get(i).blueValue));

            Point tileOffset = (isTwoTimesTwo) ? cubeTwoPieceOffset[i] : cubeThreePieceOffset[i];
//            canvas.drawRect((float) ((tileOffset.x * TILESIZE) + (centerPoint.x - TILESIZE / 2) + (tileOffset.x * BORDERWIDTH)),
//                    (float) ((tileOffset.y * TILESIZE) + (centerPoint.y - TILESIZE / 2) + (tileOffset.y * BORDERWIDTH)),
//                    (float) (((tileOffset.x + 1) * TILESIZE) + (centerPoint.x + TILESIZE / 2) + ((tileOffset.x + 1) * BORDERWIDTH)),
//                    (float) (((tileOffset.y + 1) * TILESIZE) + (centerPoint.y + TILESIZE / 2) + ((tileOffset.y + 1) * BORDERWIDTH)),
//                    paint);

            canvas.drawRect((float) (tileOffset.x * TILESIZE + centerPoint.x - TILESIZE / 2),
                    (float) (tileOffset.y * TILESIZE + centerPoint.y - TILESIZE / 2),
                    (float) (tileOffset.x * TILESIZE + centerPoint.x + TILESIZE / 2),
                    (float) (tileOffset.y * TILESIZE + centerPoint.y + TILESIZE / 2),
                    paint);
        }

    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        width = xNew;
        height = yNew;

        Log.d(TAG, "Width: " + width + " Height: " + height);

        centerPoint = new Point(xNew / 2, yNew / 2);
    }

    public Point[] getAnalyzableSquareCoordinates(int imageWidth, int imageHeight, Point imageCenterPoint, int index, int rotationDegree) {
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

        double normalizedTileWidth = (TILESIZE / (double) width) * imageWidth;
        double normalizedTileHeight = (TILESIZE / (double) height) * imageHeight;

        Point topLeft = new Point((tileOffset.x * normalizedTileWidth) + imageCenterPoint.x - (normalizedTileWidth / 2), (tileOffset.y * normalizedTileHeight) + imageCenterPoint.y - (normalizedTileHeight / 2));
        Point bottomRight = new Point((tileOffset.x * normalizedTileWidth) + imageCenterPoint.x + (normalizedTileWidth / 2), (tileOffset.y * normalizedTileHeight) + imageCenterPoint.y + (normalizedTileHeight / 2));

        return new Point[]{topLeft, bottomRight};
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
}
