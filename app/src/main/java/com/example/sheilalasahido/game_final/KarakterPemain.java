package com.example.sheilalasahido.game_final;

public abstract class KarakterPemain extends Sprite{

    protected boolean isDead = false;

    public KarakterPemain(Gameview tampilan, Game game){
        super(tampilan, game);
        move();
    }

    @Override
    public void move(){
        this.x = this.Tampilan.getWidth()/6;
        if(speedY<0){
            speedY = speedY*2/3+getSpeedTimeDecreased()/2;
        }
        else{
            this.speedY+=getSpeedTimeDecreased();
        }

        if(this.speedY >getMaxSpeed()){
            this.speedY =getMaxSpeed();
        }

        super.move();
    }

    public void mati(){
        this.isDead = true;
        this.speedY =getMaxSpeed()/2;
    }

    public void onTap(){
        this.speedY = getTapSpeed();
        this.y += getPosTapIncrease();
    }

    protected float getMaxSpeed(){
        return Tampilan.getHeight()/51.2f;
    }

    protected float getSpeedTimeDecreased(){
        return Tampilan.getHeight()/320;
    }

    protected float getTapSpeed(){
        return -Tampilan.getHeight()/16f;
    }

    protected float getPosTapIncrease(){
        return -Tampilan.getHeight()/16f;
    }

    public  void hidup_lagi(){
        this.isDead = false;
        this.row = 0;
    }

    public void upgradeBitmap(int Points){

    }

    public boolean isDead(){
        return this.isDead();
    }
}
