package com.apcs.battleasteroids.screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.apcs.battleasteroids.BattleAsteroidsGame;
import com.apcs.battleasteroids.services.SoundManager.BattleAsteroidsSound;
import com.apcs.battleasteroids.ultis.DefaultActorListener;

public class MenuScreen extends AbstractScreen {
	public MenuScreen (BattleAsteroidsGame game){
		super (game);
		
	}
	 
	 @Override
	 public void show()
	 {
		 super.show();

	     // retrieve the default table actor
	     Table table = super.getTable();
	     table.add( "Welcome to Battle Asterroid for Android!" ).spaceBottom( 50 );
	     table.row();

	     // register the button "start game"
	     TextButton startGameButton = new TextButton( "Start game", getSkin() );
	     startGameButton.addListener( new DefaultActorListener() {
	    	 public void touchUp(
	    		InputEvent event,
	            float x,
	            float y,
	            int pointer,
	            int button )
	          {
	    		 super.touchUp( event, x, y, pointer, button );	  
	    		 game.getSoundManager().play( BattleAsteroidsSound.CLICK );
	             game.setScreen( new GameScreen( game ) );
	          }
	        } );
	     table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
	     table.row();
	    
		 // register the button "options"
	     TextButton optionsButton = new TextButton( "Options", getSkin() );
	     optionsButton.addListener( new DefaultActorListener() {
	         @Override
	         public void touchUp(
	             InputEvent event,
	             float x,
	             float y,
	             int pointer,
	             int button )
	         {
	             super.touchUp( event, x, y, pointer, button );
	             game.getSoundManager().play( BattleAsteroidsSound.CLICK );
	             game.setScreen( new OptionsScreen( game ) );
	         }
	     } );
	     table.add( optionsButton ).uniform().fill().spaceBottom( 10 );
	     table.row();
	 }
}
