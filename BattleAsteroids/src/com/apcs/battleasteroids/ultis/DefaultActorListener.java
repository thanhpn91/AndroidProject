package com.apcs.battleasteroids.ultis;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class DefaultActorListener extends InputListener{
	@Override
	public boolean touchDown(
			InputEvent event,
			float x,
			float y,
			int pointer,
			int button )
	{
		return true;
	}
}
