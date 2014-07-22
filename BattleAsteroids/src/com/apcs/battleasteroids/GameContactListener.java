package com.apcs.battleasteroids;

import com.apcs.battleasteroids.GameObject.ObjectStatus;
import com.apcs.battleasteroids.GameObject.ObjectType;
import com.apcs.battleasteroids.screen.GameScreen;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {

	public GameScreen _game;
	
	public GameContactListener(GameScreen gameScreen)
	{
		_game = gameScreen;
	}
	
	@Override
	public void beginContact(Contact contact) {
		GameObject go1 = (GameObject) contact.getFixtureA().getBody().getUserData();
		GameObject go2 = (GameObject) contact.getFixtureB().getBody().getUserData();
		
		if (go1._status != ObjectStatus.AVAILABLE || go2._status != ObjectStatus.AVAILABLE)
			return;
		
		// A bullet hit a boulder
		if ((go1._type == ObjectType.BULLET && go2._type == ObjectType.BOULDER) 
			|| (go1._type == ObjectType.BOULDER && go2._type == ObjectType.BULLET))
		{
			if (go1._type == ObjectType.BULLET)
			{
				Boulder boulder = (Boulder) go2;
				Ship ship = (Ship) go1._owner;
				if (boulder.GetSplitTimes() == 0)
					ship.SetPoint(ship.GetPoint() + 100);
				if (boulder.GetSplitTimes() == 1)
					ship.SetPoint(ship.GetPoint() + 150);
				if (boulder.GetSplitTimes() == 2)
					ship.SetPoint(ship.GetPoint() + 250);
				if (boulder.GetSplitTimes() == 3)
					ship.SetPoint(ship.GetPoint() + 400);		
			}
			go1.markDestruct();
			go2.markDestruct();
		}
		
		// A bullet hit a wall
		if (go1._type == ObjectType.BULLET && go2._type == ObjectType.WALL)
			go1.markDestruct();
		if (go1._type == ObjectType.WALL && go2._type == ObjectType.BULLET)
			go2.markDestruct();
		
		// A bullet hit a ship
		if (go1._type == ObjectType.SHIP && go2._type == ObjectType.BULLET && go2._owner != go1)
		{
			Ship ship1 = (Ship) go1;
			if (ship1._destructable == true)
			{
				Ship ship = (Ship) go2._owner;
				ship.SetPoint(ship.GetPoint() + 500);
				go1.markDestruct();
			}
			go2.markDestruct();
		}
		if (go1._type == ObjectType.BULLET && go2._type == ObjectType.SHIP && go1._owner != go2)
		{
			Ship ship2 = (Ship) go2;
			if (ship2._destructable == true)
			{
				Ship ship = (Ship) go1._owner;
				ship.SetPoint(ship.GetPoint() + 500);
				go2.markDestruct();
			}
			go1.markDestruct();
		}
		
		// 2 ships hit each other
		if (go1._type == ObjectType.SHIP && go2._type == ObjectType.SHIP)
		{
			Ship ship1 = (Ship) go1;
			ship1.SetPoint(ship1.GetPoint() + 500);
			Ship ship2 = (Ship) go2;
			ship2.SetPoint(ship2.GetPoint() + 500);
			go1.markDestruct();
			go2.markDestruct();
		}
		
		// Boulder hit a ship
		if (go1._type == ObjectType.SHIP && go2._type == ObjectType.BOULDER)
		{
			Ship ship = (Ship) go1;
			if (ship._destructable == true)
			{
				go1.markDestruct();
				go2.markDestruct();
			}
		}
		if (go1._type == ObjectType.BOULDER && go2._type == ObjectType.SHIP)
		{
			Ship ship = (Ship) go2;
			if (ship._destructable == true)
			{
				go1.markDestruct();
				go2.markDestruct();
			}
		}
			
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
