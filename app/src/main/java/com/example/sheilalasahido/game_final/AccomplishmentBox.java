package com.example.sheilalasahido.game_final;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AccomplishmentBox {
    public static final int GOLD_POINTS = 100;

    public static final int SILVER_POINTS = 50;

    public static final int BRONZE_POINTS = 10;

    public static final String SAVE_NAME = "ACCOMBLISHMENTS";

    public static final String ONLINE_STATUS_KEY = "online_status";

    public static final String KEY_POINTS = "points";
    public static final String ACHIEVEMENT_KEY_50_COINS = "achievement_survive_5_minutes";
    public static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    public static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    public static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    public static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    int points;
    boolean achievement_50_coins;
    boolean achievement_toastification;
    boolean achievement_bronze;
    boolean achievement_silver;
    boolean achievement_gold;

    public void saveLocal(Activity activity){
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();

        if(points > saves.getInt(KEY_POINTS, 0)){
            editor.putInt(KEY_POINTS, points);
        }
        if(achievement_50_coins){
            editor.putBoolean(ACHIEVEMENT_KEY_50_COINS, true);
        }
        if(achievement_toastification){
            editor.putBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, true);
        }
        if(achievement_bronze){
            editor.putBoolean(ACHIEVEMENT_KEY_BRONZE, true);
        }
        if(achievement_silver){
            editor.putBoolean(ACHIEVEMENT_KEY_SILVER, true);
        }
        if(achievement_gold){
            editor.putBoolean(ACHIEVEMENT_KEY_GOLD, true);
        }

        editor.commit();
    }

    public static AccomplishmentBox getLocal(Activity activity){
        AccomplishmentBox box = new AccomplishmentBox();
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);

        box.points = saves.getInt(KEY_POINTS, 0);
        box.achievement_50_coins = saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false);
        box.achievement_toastification = saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
        box.achievement_bronze = saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
        box.achievement_silver = saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
        box.achievement_gold = saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);

        return box;
    }

    public static void savesAreOffline(Activity activity){
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, false);
        editor.commit();
    }

}
