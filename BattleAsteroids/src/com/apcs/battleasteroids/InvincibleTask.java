package com.apcs.battleasteroids;

import java.util.TimerTask;

import com.apcs.battleasteroids.GameObject.ObjectType;
import com.badlogic.gdx.physics.box2d.Fixture;

public class InvincibleTask extends TimerTask{
	Ship ship;
	int _invincibleTime;
	
	public InvincibleTask(Ship ship)
	{
		this.ship = ship;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ship._destructable = true;
		for (Fixture f : ship._body.getFixtureList())
		{
			f.getFilterData().maskBits = (short)(ObjectType.WALL.getShort());
		}
	}

}
