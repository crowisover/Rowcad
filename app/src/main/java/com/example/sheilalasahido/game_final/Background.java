package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends Sprite{
    public static Bitmap globalBitmap;

    public Background(Gameview tampilan, Game game){
        super(tampilan, game);
        if(globalBitmap == null){
            globalBitmap = Util.getDownScaledBitmapAlpha8(game, R.drawable.bg);
        }
        this.bitmap = globalBitmap;
    }

    @Override
    public void draw(Canvas canvas){
        double factor = (1.0*canvas.getHeight())/bitmap.getHeight();
        if(-x>bitmap.getWidth()){
            x+=bitmap.getWidth();
        }

        int endBitmap = Math.min(-x+(int)(canvas.getWidth()/factor), bitmap.getHeight());
        int endCanvas = (int)((endBitmap+x)*factor)+1;
        src.set(-x, 0, endBitmap, bitmap.getHeight());
        dst.set(0,0, endCanvas, canvas.getHeight());
        canvas.drawBitmap(this.bitmap, src, dst, null);

        if(endBitmap == bitmap.getWidth()){
            src.set(-x, 0, endBitmap, bitmap.getHeight());
            dst.set(0,0, endCanvas, canvas.getHeight());
            canvas.drawBitmap(this.bitmap, src, dst, null);
        }
    }
}
