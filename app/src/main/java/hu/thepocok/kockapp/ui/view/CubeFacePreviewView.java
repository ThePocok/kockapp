package hu.thepocok.kockapp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import hu.thepocok.kockapp.model.cube.component.Color;
import hu.thepocok.kockapp.model.cube.component.Face;

public class CubeFacePreviewView extends View {
    private final String TAG = "CubeFacePreviewView";
    private final int BORDER_WIDTH = 1;
    private final int PREVIEW_TEXT_SIZE = 36;
    private final int FACE_TEXT_SIZE = 24;

    private int fullWidth;
    private int fullHeight;
    private double sectionWidth;
    private double sectionHeight;

    private double textPositionX;
    private double textPositionY;
    private final String previewTextID = "title";
    private final String[] faceNames = new String[]{
            "white",
            "red",
            "green",
            "orange",
            "blue",
            "yellow"
    };

    private Face whiteFace = null;
    private Face redFace = null;
    private Face greenFace = null;
    private Face orangeFace = null;
    private Face blueFace = null;
    private Face yellowFace = null;

    private final ArrayList<FacePreviewPosition> sectionPositions = new ArrayList<>();

    private int cubeDimensions = 3;

    public CubeFacePreviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        fullWidth = xNew;
        fullHeight = yNew;

        Log.d(TAG, "Width: " + fullWidth + " Height: " + fullWidth);

        sectionWidth = fullWidth * 0.2;
        sectionHeight = fullHeight * 0.4;

        textPositionX = (fullWidth / 2.0f) - (resolveStringID(previewTextID).length() / 2.0f * PREVIEW_TEXT_SIZE / 2);
        textPositionY = fullHeight * 0.1;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw text
        Paint paint = new Paint();
        paint.setColor(android.graphics.Color.BLACK);
        paint.setTextSize(PREVIEW_TEXT_SIZE);
        canvas.drawText(resolveStringID(previewTextID), (float) textPositionX, (float) textPositionY, paint);

        // Draw faces
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDER_WIDTH);

        for (int i = 0; i < 2; i++) {
            double coordY = (fullHeight * 0.2) + (i * fullHeight * 0.4);

            for (int j = 0; j < 3; j++) {
                double coordX = (fullWidth * 0.2) + (j * sectionWidth);

                drawCubeFace(canvas, faceNames[(i * 3) + j], coordX, coordY);
            }
        }
    }

    public void drawCubeFace(Canvas canvas, String faceNameID, double coordX, double coordY) {
        double textPositionX = (sectionWidth / 2.0f) - (resolveStringID(faceNameID).length() / 2.0f * FACE_TEXT_SIZE / 2);
        double textPositionY = sectionHeight * 0.05;

        // Draw face title
        Paint paint = new Paint();
        paint.setColor(android.graphics.Color.BLACK);
        paint.setTextSize(FACE_TEXT_SIZE);
        canvas.drawText(resolveStringID(faceNameID), (float) (coordX + textPositionX), (float) (coordY + textPositionY), paint);

        double tileSize = (sectionWidth * 0.8) / cubeDimensions;
        double leftMargin = sectionWidth * 0.1;

        Face faceToDraw = null;
        switch (faceNameID) {
            case "white":
                faceToDraw = whiteFace;
                break;
            case "red":
                faceToDraw = redFace;
                break;
            case "green":
                faceToDraw = greenFace;
                break;
            case "orange":
                faceToDraw = orangeFace;
                break;
            case "blue":
                faceToDraw = blueFace;
                break;
            case "yellow":
                faceToDraw = yellowFace;
                break;
        }

        paint = new Paint();

        for (int y = 0; y < cubeDimensions; y++) {
            for (int x = 0; x < cubeDimensions; x++) {
                if (faceToDraw != null) {
                    paint.setColor(android.graphics.Color.rgb(faceToDraw.getNthRow(y).getNthPiece(x).redValue,
                            faceToDraw.getNthRow(y).getNthPiece(x).greenValue,
                            faceToDraw.getNthRow(y).getNthPiece(x).blueValue));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect((float) (leftMargin + coordX + (tileSize * x)),
                            (float) ((textPositionY * 2) + coordY + (tileSize * y)),
                            (float) (leftMargin + coordX + (tileSize * (x + 1))),
                            (float) ((textPositionY * 2) + coordY + (tileSize * (y + 1))),
                            paint);
                }

                paint.setColor(android.graphics.Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(BORDER_WIDTH);

                canvas.drawRect((float) (leftMargin + coordX + (tileSize * x)),
                        (float) ((textPositionY * 2) + coordY + (tileSize * y)),
                        (float) (leftMargin + coordX + (tileSize * (x + 1))),
                        (float) ((textPositionY * 2) + coordY + (tileSize * (y + 1))),
                        paint);
            }
        }
        if (sectionPositions.size() != 6) {
            sectionPositions.add(new FacePreviewPosition(faceNameID, coordX, coordY,
                    coordX + sectionWidth, coordY + sectionHeight));
        }
    }

    private String resolveStringID(String stringID) {
        int id = getContext().getResources().getIdentifier(stringID, "string", getContext().getPackageName());
        return getContext().getResources().getString(id);
    }

    public void setCubeDimensions(int cubeDimensions) {
        this.cubeDimensions = cubeDimensions;
        invalidate();
    }

    public Color getClickedFace(double coordX, double coordY) {
        String clickedFace = null;

        for (FacePreviewPosition section : sectionPositions) {
            if (section.getTopLeftX() <= coordX && section.getTopLeftY() <= coordY
            && section.getBottomRightX() >= coordX && section.getBottomRightY() >= coordY) {
                clickedFace = section.getFaceColorID();
                break;
            }
        }

        if (clickedFace == null) {
            return Color.EMPTY;
        }

        switch (clickedFace) {
            case "white":
                return Color.WHITE;
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
            default:
                return Color.EMPTY;
        }
    }

    public void setFace(Color color, Face face) {
        switch (color){
            case WHITE:
                whiteFace = face;
                break;
            case RED:
                redFace = face;
                break;
            case GREEN:
                greenFace = face;
                break;
            case ORANGE:
                orangeFace = face;
                break;
            case BLUE:
                blueFace = face;
                break;
            case YELLOW:
                yellowFace = face;
                break;
        }

        invalidate();
    }

    public void setAllFaces(Face whiteFace, Face redFace, Face greenFace,
                            Face orangeFace, Face blueFace, Face yellowFace) {
        this.whiteFace = whiteFace;
        this.redFace = redFace;
        this.greenFace = greenFace;
        this.orangeFace = orangeFace;
        this.blueFace = blueFace;
        this.yellowFace = yellowFace;

        invalidate();
    }

    public void clearFaces() {
        whiteFace = null;
        redFace = null;
        greenFace = null;
        orangeFace = null;
        blueFace = null;
        yellowFace = null;

        invalidate();
    }

    public static class FacePreviewPosition {
        private final String faceColorID;

        private final double topLeftX;
        private final double topLeftY;
        private final double bottomRightX;
        private final double bottomRightY;

        public FacePreviewPosition(String faceColorID, double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
            this.faceColorID = faceColorID;
            this.topLeftX = topLeftX;
            this.topLeftY = topLeftY;
            this.bottomRightX = bottomRightX;
            this.bottomRightY = bottomRightY;
        }

        public String getFaceColorID() {
            return faceColorID;
        }

        public double getTopLeftX() {
            return topLeftX;
        }

        public double getTopLeftY() {
            return topLeftY;
        }

        public double getBottomRightX() {
            return bottomRightX;
        }

        public double getBottomRightY() {
            return bottomRightY;
        }
    }
}
