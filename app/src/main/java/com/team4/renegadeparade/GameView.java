package com.team4.renegadeparade;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

/*
    Class created by Nathan
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private float centerX;
    private float centerY;
    private Drawable mBackground;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    public GameView(Context context, AttributeSet attributes, int style)
    {
        super(context, attributes, style);
        getHolder().addCallback(this);
    }

    public GameView(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        getHolder().addCallback(this);
    }

    private void setupDimensions()
    {
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
    }

    public void drawGame(float xPos, float yPos)
    {
        Canvas canvas = this.getHolder().lockCanvas();
        Rect imageBounds = canvas.getClipBounds();

        mBackground.setBounds(imageBounds);
        mBackground.draw(canvas);
        canvas.translate(xPos, yPos);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder)
    {
        mBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.background, null);
        setupDimensions();
        drawGame(centerX, centerY);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}
}
