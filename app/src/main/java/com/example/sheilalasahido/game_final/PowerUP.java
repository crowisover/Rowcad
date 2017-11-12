package com.example.sheilalasahido.game_final;

public abstract class PowerUP extends Sprite{
    public PowerUP(Gameview tampilan, Game game){
        super(tampilan, game);
        init();
    }

    private void init(){
        this.x = Tampilan.getWidth()*4/5;
        this.y = 0 - this.tinggi;
        this.speedX = - Tampilan.getCepatX();
        this.speedY = (int)(Tampilan.getCepatX()*(Math.random()+0.5));
    }
}