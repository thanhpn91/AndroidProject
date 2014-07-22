package com.apcs.battleasteroids;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	@SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );

        // whether to use OpenGL ES 2.0
        boolean useOpenGLES2 = true;

        // create the game
        initialize( new BattleAsteroidsGame(), useOpenGLES2 );
        
       
    }
}