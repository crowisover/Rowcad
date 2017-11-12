package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Accessory extends Sprite {
    
    public Accessory(Gameview view, Game game) {
        super(view, game);
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        this.lebar = this.bitmap.getWidth();
        this.tinggi = this.bitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        if(this.bitmap != null){
            super.draw(canvas);
        }
    }
}
