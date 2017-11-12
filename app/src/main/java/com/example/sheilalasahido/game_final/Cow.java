package com.example.sheilalasahido.game_final;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cow extends KarakterPemain{
	
	private static final int POINTS_TO_SIR = 23;
	private static final int POINT_TO_COOL = 35;
	
	public static Bitmap globalBitmap;
	private static int sound = -1;
	//private Accessory accessory;
	
	public Cow (Gameview view, Game game){
		super(view, game);
		if(globalBitmap == null){
			globalBitmap = Util.getScaledBitmapAlpha8 (game, R.drawable.cow);
		}
		this.bitmap = globalBitmap;
		this.lebar = this.bitmap.getWidth()/(colNr = 8);
		this.tinggi = this.bitmap.getHeight()/4;
		this.frametime = 3;
		this.y = game.getResources().getDisplayMetrics().heightPixels / 2;
		
		if(sound == -1){
			sound = Game.soundPool.load(game, R.raw.cow, 1);
		}
		
		//this.accessory = new Accessory (view, game);
	}
	
	private void playSound(){
		Game.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
	}
	
	@Override
	public void onTap (){
		super.onTap();
		playSound();
	}
	
	@Override
	public void move(){
		changeToNextFrame();
		super.move();
		if(row != 3){
			if (speedY > getTapSpeed() / 3 && speedY < getMaxSpeed () * 1/3){
				row = 0;
			} else if (speedY > 0){
				row = 1;
			} else {
				row = 2;
			}
		}
		
		//if (this.accessory != null){
		//	this.accessory.moveTo (this.x, this.y);
		//}
	}
	
	@Override
	public void draw (Canvas canvas) {
		super.draw (canvas);
		//if (this.accessory != null && !isDead){
		//	this.accessory.draw(canvas);
		//}
	}
	
	@Override
	public void mati (){
		this.row = 3;
		this.frametime = 3;
		super.mati();
	}
	
	@Override
	public void hidup_lagi(){
		super.hidup_lagi();
		//this.accessory.setBitmap(Util.getScaledBitmapAlpha8 (game, R.drawable.accessory_scumbag));
	}
	
	@Override
	public void upgradeBitmap (int points){
		super.upgradeBitmap(points);
		//if(points == POINTS_TO_SIR){
		//	this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sir));
		//}else if (points == POINT_TO_COOL){
		//	this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sunglasses));
		//}
	}
}
	