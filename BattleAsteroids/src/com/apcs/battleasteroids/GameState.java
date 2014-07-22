package com.apcs.battleasteroids;

import java.util.ArrayList;
import java.util.Iterator;

import com.apcs.battleasteroids.GameObject.ObjectStatus;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class GameState {
	// Object internal information
	public World _world; // Game world
	public ArrayList<GameObject> _gameObjects; // Game objects
	
	public Vector2 _wallstart;
	public Vector2 _wallend;
	public float _wallthick;
	public float _shipsize;
	public float _bulletsize;
	public float _bulletspeed;
	
	public Ship _ship1;
	public Ship _ship2;
	//public GameBorder _gameBorder;
	public ShipSpawnTask _ship1SpawnTask;
	public ShipSpawnTask _ship2SpawnTask;
	public InvincibleTask _invincibleTask;
	
	// Constructors
	public GameState()
	{
		_world = new World(new Vector2(0, 0), true);
		_gameObjects = new ArrayList<GameObject>();
	}
	
	// Maintenace function
	public void sweepDeadBodies()
	{
		for (Iterator<Body> iter = _world.getBodies(); iter.hasNext();)
		{
			Body body = iter.next();
			if (body != null)
			{
				GameObject go = (GameObject) body.getUserData();
				if (go._status == ObjectStatus.DESTROYING)
				{
					go._status = ObjectStatus.DESTROYED;
					go.destruct();
					go._body = null;
					_world.destroyBody(body);
				}
			}
		}
	}
	
	public void updateObject(float deltatime)
	{
		for (Iterator<GameObject> iter = _gameObjects.iterator(); iter.hasNext();)
		{
			GameObject go = iter.next();
			if (go._status == ObjectStatus.REMOVAL)
				iter.remove();
			else
				go.update(deltatime);
		}
	}
}
