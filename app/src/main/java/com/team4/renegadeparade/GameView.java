package com.team4.renegadeparade;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.team4.renegadeparade.SettingsActivity.musicPlaying;

/*
    Class created by Nathan, Alex
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable
{
    private static GameView instance;

    //thread by Alex
    private Thread thread;
    public boolean activePlay, gameOver = false;
    public static float ratioX, ratioY;
    private int screenX, screenY;
    private Paint paint;
    public GameCharacter gameCharacter;
    private Background background1, background2;
    private List<Pellet> pellets;
    private Enemy[] enemies;
    private Random random;
    private int score = 0;
    private SoundPool soundPool;
    private int sound1, sound2, sound3;

    //implemented by alex
    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;//grab screen and width from device
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        initialize();
    }
    public GameView(Context context, AttributeSet attributes, int style)
    {
        super(context, attributes, style);
        getHolder().addCallback(this);
        initialize();
    }
    public GameView(Context context, AttributeSet attributes)
    {
        super(context, attributes);
        getHolder().addCallback(this);
        initialize();
        
         //get soundpool to grab the .mp3 file by alex
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();

        }   else   {
            soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC, 0);
        }

        sound1 = soundPool.load(context, R.raw.shoot, 1);
        sound2 = soundPool.load(context, R.raw.death_sound,1);
        sound3 = soundPool.load(context, R.raw.enemy_die,1);
    }

    //block by alex
    void initialize()
    {

        instance = this;
        Point point = new Point();
        MainActivity.getInstance().getWindowManager().getDefaultDisplay().getSize(point);

        this.screenX = point.x;//find given poin on screen
        this.screenY = point.y;

        ratioX = screenWidth / screenX; //create screen ratio
        ratioY = screenHeight / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        gameCharacter = new GameCharacter(this, screenY, getResources());

        pellets = new ArrayList<>();

        //creates incoming enemies on screen
        enemies = new Enemy[3];
        for(int i = 0; i < 3; i++)  {

            Enemy enemy = new Enemy(getResources());
            enemies[i] = enemy;

        }

        random = new Random();

        background2.x = screenX;

        paint = new Paint();

        score = 0;
    }

    //block by alex
    @Override
    public void run() {

        while(activePlay)   {
            update();
            draw();
            sleep();
        }

    }

    //block by alex
    // NOTE: if not implemented by me please write you name next to your code
    private void update()   {

        background1.x -= 6 * ratioX; //moving on x-axis change to y to move on y-axis
        background2.x -= 6 * ratioX; //both regulate background speed

        if(background1.x + background1.background.getWidth() < 0) {

            background1.x = screenX;

        }

        if(background2.x + background2.background.getWidth() < 0) {

            background2.x = screenX;

        }

        //creates the bullets fired in game screen
        List<Pellet> offScreenPellet = new ArrayList<>();
        for(Pellet pellet : pellets) {

            if(pellet.x > screenX)
                offScreenPellet.add(pellet);

            pellet.x += 60 * ratioX; //manage speed of pellet

            for (Enemy enemy : enemies) {
                //play sound here for enemy death in if statement
                if(Rect.intersects(enemy.getCollisionShape(), pellet.getCollisionShape()))  {

                    enemy.x = - 2000;
                    pellet.x = screenX + 2000;
                    enemy.dead = true;
                    if(musicPlaying == true)    {
                        soundPool.play(sound3, 1, 1, 0, 0, 1);
                    }


                    score++;
                }
            }

        }

        //removes bullets when they are offscreen
        for(Pellet pellet : offScreenPellet) {

            pellets.remove(pellet);

        }

        for(Enemy enemy : enemies)  {

            enemy.x -= enemy.speed;

            if(enemy.x + enemy.width < 0)   {

                if(!enemy.dead) {
                    gameOver = true;//ends game
                    return;
                }

                //creates speed for bullets and increases speed over time
                int topRandomSpeed = (int) (7 * ratioX);
                enemy.speed = Math.toIntExact((long) (random.nextInt(topRandomSpeed) + (score*0.1))); //random speed by Nathan

                if(enemy.speed <= 1 *ratioX) {

                    enemy.speed = (int) (2 * ratioY);

                }

                enemy.x = screenX;
                enemy.y = random.nextInt(screenY - enemy.height);

                enemy.dead = false;

            }

            if(Rect.intersects(enemy.getCollisionShape(), gameCharacter.getCollisionShape()))   {

                gameOver = true;

                return;

            }
        }

    }


    private void draw() {

        //by alex
        if(getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            //Drawing the score. by Zayn
            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);
                canvas.drawText("Score: " + score, getWidth()/14,getHeight()/10, paint);

            for(Enemy enemy : enemies)  {

                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);

            }

            if(gameOver)
            {
                activePlay = false;
                canvas.drawBitmap(gameCharacter.getDeadCharacter(), gameCharacter.x, gameCharacter.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                MainActivity.getInstance().highscoreManager.addScoreToList(score);
                InGameScreen.getInstance().yourScore.setText(score + "");
                InGameScreen.getInstance().HighScore.setText(MainActivity.getInstance().highscoreManager.getHighScore() + "");
                InGameScreen.getInstance().showEnd();
                if(musicPlaying == true)    {
                    soundPool.play(sound2, 1, 1, 0, 0, 1);
                }

                return;
            }
            float movementX = JoystickView.getInstance().deltaX * gameCharacter.speed;
            float movementY = JoystickView.getInstance().deltaY * gameCharacter.speed;
            if (gameCharacter.getCollisionShape().centerX() + movementX < getWidth()
            && gameCharacter.getCollisionShape().centerX() + movementX > getWidth()*0.03)
                gameCharacter.x+=(movementX);
            if (gameCharacter.getCollisionShape().centerY() + movementY < getHeight()
            && gameCharacter.getCollisionShape().centerY() + movementY > getHeight()*0.03)
                gameCharacter.y+=(movementY);
            //code for color
            if(GameCharacter.character_color != 0) {
                paint.setColorFilter(new PorterDuffColorFilter(GameCharacter.character_color, PorterDuff.Mode.SRC_IN));
            }
            canvas.drawBitmap(gameCharacter.getGameCharacter(), gameCharacter.x, gameCharacter.y, paint);

            paint.setColorFilter(null);


            //draw pellet loop by alex
            for(Pellet pellet: pellets) {
                canvas.drawBitmap(pellet.pellet, pellet.x, pellet.y, paint);
            }

            //draw enemies by alex
            for(Enemy enemy : enemies)  {

                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);

            }
            getHolder().unlockCanvasAndPost(canvas); //show moving background
        }
    }
    //block by alex
    private void sleep()   {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //block by alex
    public void start()
    {
        if (thread == null || !thread.isAlive())
        {
            activePlay = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    //block by alex
    public void stop()
    {
        try {
            activePlay = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //block by alex
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gameCharacter.fire++;

        return true;
    }

    //block by alex
    public void newPellet()
    {
        //play sound on fire
        if(musicPlaying == true)    {
            soundPool.play(sound1, 1, 1, 0, 0, 1);
        }

        Pellet pellet = new Pellet(getResources());
        pellet.x = gameCharacter.x + gameCharacter.width;
        pellet.y = gameCharacter.y + (gameCharacter.height / 8); //set bullet size
        pellets.add(pellet);
    }
    public static GameView getInstance()
    {
        return instance;
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder)
    {
        start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder)
    {
        stop();
    }


}


