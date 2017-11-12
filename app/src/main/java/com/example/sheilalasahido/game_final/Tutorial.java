package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;

public class Tutorial extends Sprite{
    public static Bitmap globalBitmap;

    public Tutorial(Gameview tampilan, Game game){
        super(tampilan, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.tutorial);
        }
        this.bitmap = globalBitmap;
        this.lebar = this.bitmap.getWidth();
        this.tinggi = this.bitmap.getHeight();
    }

    @Override
    public void move(){
        this.x = Tampilan.getWidth()/2-this.lebar/2;
        this.y = Tampilan.getHeight()/2-this.tinggi/2;
    }
}
