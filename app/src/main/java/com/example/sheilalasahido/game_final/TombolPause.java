package com.example.sheilalasahido.game_final;

public class TombolPause extends Sprite{
    public TombolPause(Game game, Gameview T_ampilan){
        super(T_ampilan, game);
        this.bitmap = Util.getScaledBitmapAlpha8(game, R.drawable.pause_button);
        this.lebar = this.bitmap.getWidth();
        this.tinggi = this.bitmap.getHeight();
    }
    @Override
    public void move(){
        this.x = this.Tampilan.getWidth()-this.lebar;
        this.y = 0;
    }
}