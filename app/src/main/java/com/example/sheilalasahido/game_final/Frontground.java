package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;

public class Frontground extends Background{

    public static final float GROUND_HEIGHT = (1f*/*45*/35)/720;

    public static Bitmap globalBitmap;

    public Frontground(Gameview tampilan, Game game){
        super(tampilan, game);
        if(globalBitmap == null){
            globalBitmap = Util.getDownScaledBitmapAlpha8(game, R.drawable.fg);
        }
        this.bitmap = globalBitmap;
    }
}
