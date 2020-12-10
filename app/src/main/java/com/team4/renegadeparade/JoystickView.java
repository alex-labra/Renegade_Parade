package com.team4.renegadeparade;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import org.jetbrains.annotations.NotNull;

/*
 * Created by Daniel on 7/25/2016.
 * https://github.com/efficientisoceles/JoystickView
 * Modified by: Nathan
 * Tested by: Rey, Nathan, Alex, and Zayn
 * Debugged by: Nathan
 */

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener
{
    private static JoystickView instance;
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private final int ratio = 5; //The smaller, the more shading will occur

    //Added deltaX and deltaY values and removed joystick listener callback by Nathan
    public float deltaX;
    public float deltaY;

    private void setupDimensions()
    {
        instance = this;
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
        baseRadius = Math.min(getWidth(), getHeight()) / 3f;
        hatRadius = Math.min(getWidth(), getHeight()) / 5f;
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    public JoystickView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoystickView(Context context, AttributeSet attributes, int style)
    {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoystickView (Context context, AttributeSet attributes)
    {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    private void drawJoystick(float newX, float newY)
    {
        if(getHolder().getSurface().isValid())
        {
            Canvas myCanvas = this.getHolder().lockCanvas(); //Stuff to draw
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Clear the BG

            //First determine the sin and cos of the angle that the touched point is at relative to the center of the joystick
            float hypotenuse = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
            float sin = (newY - centerY) / hypotenuse; //sin = o/h
            float cos = (newX - centerX) / hypotenuse; //cos = a/h

            //Draw the base first before shading
            colors.setARGB(100, 100, 100, 100);
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
            for(int i = 1; i <= (int) (baseRadius / ratio); i++)
            {
                colors.setARGB(150/i, 0, 0, 0); //Gradually decrease the shade of black drawn to create a nice shading effect
                myCanvas.drawCircle(newX - cos * hypotenuse * (ratio/baseRadius) * i,
                        newY - sin * hypotenuse * (ratio/baseRadius) * i, i * (hatRadius * ratio / baseRadius), colors); //Gradually increase the size of the shading effect
            }

            //Drawing the joystick hat
            for(int i = 0; i <= (int) (hatRadius / ratio); i++)
            {
                colors.setARGB(255, (int) (i * (255 * ratio / hatRadius)), (int) (i * (255 * ratio / hatRadius)), 255); //Change the joystick color for shading purposes
                myCanvas.drawCircle(newX, newY, hatRadius - (float) i * (ratio) / 2 , colors); //Draw the shading for the hat
            }

            getHolder().unlockCanvasAndPost(myCanvas); //Write the new drawing to the SurfaceView
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //Setting up joystick after the surface is created instead of when the class is initialized so conflicts don't occur.
        setupDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    @Override
    public boolean onTouch(View v, MotionEvent e)
    {
        if(v.equals(this) && GameView.getInstance().activePlay) //Added boolean check to make sure game isn't over by Nathan
        {
            if(e.getAction() != MotionEvent.ACTION_UP)
            {
                float displacement = (float) Math.sqrt((Math.pow(e.getX() - centerX, 2)) + Math.pow(e.getY() - centerY, 2));
                if(displacement < baseRadius)
                {
                    drawJoystick(e.getX(), e.getY());
                    //Set deltaX and deltaY values instead of joystick listener callback by Nathan
                    deltaX = (e.getX() - centerX)/baseRadius;
                    deltaY = (e.getY() - centerY)/baseRadius;
                }
                else
                {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);
                    //Set deltaX and deltaY values instead of joystick listener callback by Nathan
                    deltaX = (constrainedX-centerX)/baseRadius;
                    deltaY = (constrainedY-centerY)/baseRadius;
                }
            }
            else
            {
                drawJoystick(centerX, centerY);
                //Set deltaX and deltaY values instead of joystick listener callback by Nathan
                deltaX = 0;
                deltaY = 0;
            }
        }
        return true;
    }

    //Instance by Nathan
    public static JoystickView getInstance()
    {
        return instance;
    }
}