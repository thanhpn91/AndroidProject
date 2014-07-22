package com.apcs.battleasteroids;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject {
	// Object types
	public enum ObjectType {
		SHIP(1), BOULDER(2), BULLET(4), WALL(8);
		
		private int _value;
		
		private ObjectType(int value)
		{
			_value = value;
		}
		
		public int getValue()
		{
			return _value;
		}
		
		public short getShort()
		{
			return (short)_value;
		}
	}
	
	// Object physical statuses
	public enum ObjectStatus {
		DESTROYED, DESTROYING, AVAILABLE, REMOVAL
	}
	BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("skin/air.json"));
	// Object internal information
	public GameObject _owner; // Object owning this object
	public ObjectType _type; // Object type 
	public ObjectStatus _status; // Object status
	public Body _body; // Object physical body
	
	public GameState _gamestate; // Object reference to the GameState
	
	// Base constructor
	public GameObject(GameState gamestate, GameObject owner, 
			ObjectType type, ObjectStatus status)
	{
		_gamestate = gamestate;
		_gamestate._gameObjects.add(this);
		
		_owner = owner;
		_type = type;
		_status = status;
		_body = null;
	}
	
	// Object actions
	
	// Spawn create and set up physical bodies for object and doing other related things
	public abstract void spawn();
	
	// Mark physical body for destruction
	public abstract void markDestruct();
	
	// Destruct destroy physical bodies for object and doing other related things
	public abstract void destruct();
	
	// Draw the object to the game screen
	public abstract void draw(SpriteBatch spritebatch);
	{
		
	}
	
	// Dispose object when not needed anymore
	public abstract void dispose();
	
	// Time-based update
	public abstract void update(float deltatime);
	
	// Mark an object for removal from game objects collection
	public void markRemoval()
	{
		if (_status == ObjectStatus.DESTROYED)
		{
			_status = ObjectStatus.REMOVAL;
		}
	}
}
