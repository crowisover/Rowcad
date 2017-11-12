package com.example.sheilalasahido.game_final;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class Game extends FragmentActivity{

    GameOverDialog gameOverDialog;
    private static final long DOUBLE_BACK_TIME = 1000;
    public int numberOfRevive = 1;
    private static int gameOverCounter = 1;
    public static final String coin_save = "coin_save";
    public static final String coin_key = "coin_key";
    public static SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    public static MediaPlayer musicPlayer = null;
    AccomplishmentBox accomplishmentBox;
    public boolean musicShouldPlay = false;
    private long backPressed;
    public MyHandler handler;
    Gameview tampilan;
    int kuota;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        accomplishmentBox = new AccomplishmentBox();
        tampilan = new Gameview(this);
        gameOverDialog = new GameOverDialog(this);
        handler = new MyHandler(this);
        setContentView(tampilan);
        initMusicPlayer();
        loadcoins();
    }

    private void loadcoins(){
        SharedPreferences saves = this.getSharedPreferences(coin_save, 0);
        this.kuota = saves.getInt(coin_key, 0);
    }

    @Override
    protected void onPause(){
        tampilan.pause();
        if(musicPlayer.isPlaying()){
            musicPlayer.pause();
        }
        super.onPause();

    }

    @Override
    protected void onResume(){
        tampilan.drawSekali();
        if(musicShouldPlay){
            musicPlayer.start();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-backPressed<DOUBLE_BACK_TIME){
            super.onBackPressed();
        }
        else{
            backPressed = System.currentTimeMillis();
        }
    }

    public void increaseKuota(){
        this.kuota++;
        if(kuota >= 50 && !accomplishmentBox.achievement_50_coins){
            accomplishmentBox.achievement_50_coins = true;
            //handler.sendMessage(Message.obtain(handler,1,R.string.toast_achievement_50_coins, MyHandler.SHOW_TOAST));
        }
    }

    public void gameOver(){
        handler.sendMessage(Message.obtain(handler, MyHandler.GAME_OVER_DIALOG));
    }

    public void increasePoints(){
        accomplishmentBox.points++;
        this.tampilan.getPlayer().upgradeBitmap(accomplishmentBox.points);

        if(accomplishmentBox.points >= AccomplishmentBox.BRONZE_POINTS){
            if(!accomplishmentBox.achievement_bronze){
                accomplishmentBox.achievement_bronze = true;
            }
        }

        if(accomplishmentBox.points >= AccomplishmentBox.SILVER_POINTS){
            if(!accomplishmentBox.achievement_silver){
                accomplishmentBox.achievement_silver = true;
            }
        }

        if(accomplishmentBox.points >= AccomplishmentBox.GOLD_POINTS){
            if(!accomplishmentBox.achievement_gold){
                accomplishmentBox.achievement_gold = true;
            }
        }
    }

    public void initMusicPlayer(){
        if(musicPlayer == null){
            musicPlayer = MediaPlayer.create(this, R.raw.nyan_cat_theme);
            musicPlayer.setLooping(true);
            musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        }
        musicPlayer.seekTo(0);
    }

    static class MyHandler extends Handler{
        public static final int GAME_OVER_DIALOG = 0;

        private Game game;

        public MyHandler(Game game){
            this.game = game;
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case GAME_OVER_DIALOG:
                    showGameOverDialog();
                    break;
            }
        }

        private void showGameOverDialog() {
            ++Game.gameOverCounter;
            game.gameOverDialog.init();
            game.gameOverDialog.show();
        }
    }
}
