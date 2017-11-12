package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;

public class Extra_Kuota extends PowerUP{

    public static Bitmap globalBitmap;
    private static int sound = -1;

    public Extra_Kuota(Gameview tampilan, Game game){
        super(tampilan, game);
        if(globalBitmap==null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.kuota);
        }
        this.bitmap = globalBitmap;
        this.tinggi = this.bitmap.getHeight();
        this.lebar = this.bitmap.getWidth()/(colNr = 32);
        this.frametime = 1;
       // if(sound ==-1){
           // sound = Game.soundPool.load(game, R.raw.coin, 1);
        //}
    }

    @Override
    public void onCollision(){
        super.onCollision();
       // playsound();
        game.increaseKuota();
    }

    private void playsound(){
        Game.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }

    @Override
    public  void move(){
        changeToNextFrame();
        super.move();
    }
}