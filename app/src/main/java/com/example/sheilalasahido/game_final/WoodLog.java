package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;

public class WoodLog extends Sprite {

    public static Bitmap globalBitmap;

    public WoodLog(Gameview view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.log_full);
        }
        this.bitmap = globalBitmap;
        this.lebar = this.bitmap.getWidth();
        this.tinggi = this.bitmap.getHeight();
    }

    public void init(int x, int y){
        this.x = x;
        this.y = y;
    }
}
