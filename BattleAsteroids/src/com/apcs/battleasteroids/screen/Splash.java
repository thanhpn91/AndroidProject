package com.apcs.battleasteroids.screen;
import com.apcs.battleasteroids.BattleAsteroidsGame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Splash extends AbstractScreen
{
	private Image splashImage;
	
	public Splash(BattleAsteroidsGame game )
		{
	    	super( game );
		}

	@Override
	public void show()
	{
		super.show();
    
	    // retrieve the splash image's region from the atlas
	    AtlasRegion splashRegion = getAtlas().findRegion( "splash-screen/splash-image" );
	    Drawable splashDrawable = new TextureRegionDrawable( splashRegion );
	
	    // here we create the splash image actor; its size is set when the
	    // resize() method gets called
	    splashImage = new Image( splashDrawable, Scaling.stretch );
	    splashImage.setFillParent( true );
	
	    // this is needed for the fade-in effect to work correctly; we're just
	    // making the image completely transparent
	    splashImage.getColor().a = 0f;
	
	    // configure the fade-in/out effect on the splash image
	    splashImage.addAction( sequence( fadeIn( 0.75f ), delay( 1.75f ), fadeOut( 0.75f ),
	        new Action() {
	            @Override
	            public boolean act(
	                float delta )
	            {
	                // the last action will move to the next screen
	                game.setScreen( new MenuScreen( game ) );
	                return true;
	            }
	        } ) );
	
	    // and finally we add the actor to the stage
	    stage.addActor( splashImage );
	}
}
