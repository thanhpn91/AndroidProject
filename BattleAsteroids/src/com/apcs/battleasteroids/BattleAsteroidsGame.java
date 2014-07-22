package com.apcs.battleasteroids;

import com.apcs.battleasteroids.screen.MenuScreen;
import com.apcs.battleasteroids.screen.Splash;
import com.apcs.battleasteroids.services.MusicManager;
import com.apcs.battleasteroids.services.PreferencesManager;
import com.apcs.battleasteroids.services.SoundManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;


public class BattleAsteroidsGame extends Game {
	public static final boolean DEV_MODE = false;
	// constant useful for logging
    public static final String LOG = BattleAsteroidsGame.class.getSimpleName();
    private PreferencesManager preferencesManager;
    private SoundManager soundManager;
    private MusicManager musicManager;
    
	@Override
	public void create() {
		Gdx.app.log( BattleAsteroidsGame.LOG, "Creating game on " + Gdx.app.getType() );
		// create the preferences manager
        preferencesManager = new PreferencesManager();

        // create the music manager
        musicManager = new MusicManager();
        musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( preferencesManager.isMusicEnabled() );

        // create the sound manager
        soundManager = new SoundManager();
        soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( preferencesManager.isSoundEnabled() );
	}
	
	@Override
    public void render()
    {
        super.render();
  
    }
	
	@Override
    public void setScreen(
        Screen screen )
    {
        super.setScreen( screen );
        Gdx.app.log( BattleAsteroidsGame.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }
	
	 @Override
	    public void resize(
	        int width,
	        int height )
	    {
	        super.resize( width, height );
	        Gdx.app.log( BattleAsteroidsGame.LOG, "Resizing game to: " + width + " x " + height );

	        // show the splash screen when the game is resized for the first time;
	        // this approach avoids calling the screen's resize method repeatedly
	        if( getScreen() == null ) {	           
	                setScreen( new MenuScreen( this ) );
	            
	        }
	    }
	 
	// Services' getters
	 public PreferencesManager  getPreferencesManager() {
		// TODO Auto-generated method stub
		return preferencesManager;
	 }

	 

	 public SoundManager getSoundManager()
	 {
		 return soundManager;
	 }

	public MusicManager getMusicManager() {
		// TODO Auto-generated method stub
		return musicManager;
	}


}
