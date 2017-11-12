package com.example.sheilalasahido.game_final;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class Gameview extends SurfaceView{

    private boolean tutorialISShown = true;
    volatile private boolean paused = true;

    public static final long UPDATE_INTERVAL = 50;

    private KarakterPemain pemain;
    private Background background;
    private Frontground frontground;
    private TombolPause mandek;
    private Tutorial tata_cara;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private List<PowerUP> powerUPS = new ArrayList<PowerUP>();

    private Game game;

    private SurfaceHolder holder;

    public Gameview(Context context){
        super(context);
        this.game = (Game) context;
        setFocusable(true);

        holder = getHolder();
        pemain = new Cow(this, game);
        background = new Background(this, game);
        frontground = new Frontground(this, game);
        mandek = new TombolPause(game, this);
        tata_cara = new Tutorial(this, game);
    }

    public void showPetunjuk(){
        pemain.move();
        mandek.move();

        while(!holder.getSurface().isValid()){
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas, true);
        tata_cara.move();
        tata_cara.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void drawSekali(){
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if(tutorialISShown){
                    showPetunjuk();
                }
                else{
                    draw();
                }
            }
        })).start();
    }

    private void draw(){
        while(!holder.getSurface().isValid()){
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas, true);
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawCanvas(Canvas canvas, boolean drawPlayer){
        background.draw(canvas);
        for(Obstacle r : obstacles){
            r.draw(canvas);
        }
        //for(PowerUP p : powerUPS){
           // p.draw(canvas);
        //}
        if(drawPlayer){
            pemain.draw(canvas);
        }
        frontground.draw(canvas);
        mandek.draw(canvas);

        Paint teks = new Paint();
        teks.setColor(Color.BLACK);
        teks.setTextSize(getScoreTextMetrics());
        String writ = game.getResources().getString(R.string.onscreen_score_text)+" "+game.accomplishmentBox.points+"/"+game.getResources().getString(R.string.onscreen_coin_text)
                +" "+game.kuota;
        canvas.drawText(writ, 0, getScoreTextMetrics(), teks);
    }

    public KarakterPemain getPlayer(){return this.pemain;}

    public void pause(){
        stopTimer();
        paused = true;
    }

    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
        if(timerTask != null){
            timerTask.cancel();
        }
    }

    public void resume(){
        paused = false;
        startTimer();
    }

    private void startTimer(){
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void setUpTimerTask(){
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Gameview.this.run();
            }
        };
    }

    public void run(){
        checkPasses();
        checkOutofRange();
        checkCollision();
        createObstacle();
        move();
        draw();
    }

    private void checkPasses(){
        for(Obstacle o : obstacles){
            if(o.isPassed()){
                if(o.isAlreadyPassed){
                    o.onPass();
                    //createPowerUP();
                }
            }
        }
    }

    private void createPowerUP(){
        if((powerUPS.size()<1)&&(Math.random()*100<20)){
            powerUPS.add(new Extra_Kuota(this, game));
        }
    }

    private void move(){
        for(Obstacle o : obstacles){
            o.setSpeedX(-getCepatX());
            o.move();
        }
        //for(PowerUP p : powerUPS){
          //  p.move();
        //}

        background.setSpeedX(-getCepatX()*2);
        background.move();

        frontground.setSpeedX(-getCepatX()*4/3);
        frontground.move();

        mandek.move();
        pemain.move();
    }

    private void checkOutofRange(){
        for(int i = 0; i<obstacles.size(); i++){
            if(this.obstacles.get(i).isOutOfRange()){
                this.obstacles.remove(i);
                i--;
            }
        }
        //for(int i=0;i<powerUPS.size(); i++){
          ///  if(this.powerUPS.get(i).isOutOfRange()){
             //   this.powerUPS.remove(i);
               // i--;
            //}
        //}
    }

    private void checkCollision(){
        for(Obstacle o : obstacles){
            o.onCollision();
            gameOver();
        }
        //for(int i=0;i<powerUPS.size(); i++){
          //  if(this.powerUPS.get(i).isColliding(pemain)){
            //    this.powerUPS.get(i).onCollision();
              //  this.powerUPS.remove(i);
                //i--;
            //}
        //}
        if(pemain.isTouchingEdge()){
            gameOver();
        }
    }

    public void gameOver(){
        pause();
        playerDeadFall();
        game.gameOver();
    }

    private void playerDeadFall(){
        pemain.mati();
        do{
            pemain.move();
            draw();
            try{Thread.sleep(UPDATE_INTERVAL/4); } catch (InterruptedException e) { e.printStackTrace();}
        }while(!pemain.isTouchingGround());

    }

    private void createObstacle(){
        if(obstacles.size()<1){
            obstacles.add(new Obstacle(this, game));
        }
    }

    public int getCepatX(){
        int speedDefault = this.getHeight()/45;
        int speedIncrease = (int)(this.getHeight()/600f*(game.accomplishmentBox.points/4));
        int speed=speedDefault+speedIncrease;
        return  Math.min(speed,2*speedDefault);
    }

    public void revive(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                setupHidup();
            }
        }).start();
    }

    private void setupHidup(){
        game.gameOverDialog.hide();
        pemain.setY(this.getHeight()/2-pemain.getLebar()/2);
        pemain.setX(this.getWidth()/6);
        obstacles.clear();
        //powerUPS.clear();
        pemain.hidup_lagi();
        for(int i = 0; i < 6; ++i){
            while(!holder.getSurface().isValid()){}
            Canvas canvas = holder.lockCanvas();
            drawCanvas(canvas, i%2 == 0);
            holder.unlockCanvasAndPost(canvas);
            try { Thread.sleep(UPDATE_INTERVAL*6); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        resume();
    }

    public int getScoreTextMetrics(){return (int) (this.getHeight()/21.0f);}

    public Game getGame(){
        return this.game;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_DOWN
                && !this.pemain.isDead()){
            if(tutorialISShown){
                tutorialISShown = false;
                resume();
                this.pemain.onTap();
            }else if(paused){
                resume();
            }else if(mandek.isTouching((int) event.getX(), (int) event.getY()) && !this.paused){
                pause();
            }else{
                this.pemain.onTap();
            }
        }
        return true;
    }
}

