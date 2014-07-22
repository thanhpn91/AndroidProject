package com.apcs.battleasteroids;

import java.util.Random;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.apcs.battleasteroids.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Boulder extends GameObject {
	// Internal Object information
	private Vector2 _startpoint;
	private float _startrotation;
	private Vector2 _startvelocity;
	private float _startangvelocity;
	private float _size;
	private int _splittimes;
	private int _boulderName;
	private Texture _boulderTexture;
	
	public int GetSplitTimes()
	{
		return _splittimes;
	}
	
	// Constants
	public static final int MAX_SPLIT = 4;
	public static final float REDUCTION_RATIO = 0.7f;
	
	public Boulder(GameState state, Vector2 startpoint, float startrotation, Vector2 startvelocity, float  startangvelocity, float size, int splittimes, int boulderName)
	{
		// Setting variables
		super(state, null, ObjectType.BOULDER, ObjectStatus.DESTROYED);
		_boulderName = boulderName;
		_boulderTexture = new Texture(Gdx.files.internal("images/" +"asteroid" +boulderName +".png"));
		_startpoint = startpoint.mul(GameScreen.WORLD_TO_BOX);
		_startrotation = startrotation;
		_startvelocity = startvelocity.mul(GameScreen.WORLD_TO_BOX);
		_startangvelocity = startangvelocity;
		_size = size * GameScreen.WORLD_TO_BOX;
		_splittimes = splittimes;
	}
	
	@Override
	public void spawn() {
		if (_status == ObjectStatus.DESTROYED)
		{
			BodyDef bd = new BodyDef();
			bd.position.set(_startpoint);
			bd.angle = _startrotation;
			bd.type = BodyType.DynamicBody;
			
			FixtureDef fd = new FixtureDef();			
			fd.density = 1f;
			fd.friction = 0f;
			fd.restitution = 1f;
			fd.filter.categoryBits = _type.getShort();
			fd.filter.maskBits = (short)(ObjectType.BULLET.getShort() 
					| ObjectType.WALL.getShort() | ObjectType.SHIP.getShort());
		
			_body = _gamestate._world.createBody(bd);
			loader.attachFixture(_body, "asteroid"+_boulderName, fd, _size);
			_body.setUserData(this);
			_body.createFixture(fd);
			_body.setLinearVelocity(_startvelocity);
			_body.setAngularVelocity(_startangvelocity);
			
			_status = ObjectStatus.AVAILABLE;
			
		}
	}
	
	@Override
	public void markDestruct() {
		if (_status == ObjectStatus.AVAILABLE)
			_status = ObjectStatus.DESTROYING;
	}

	@Override
	public void destruct() {
		if (_splittimes < MAX_SPLIT)
		{
			Vector2 spawnpos = _body.getPosition().mul(GameScreen.BOX_TO_WORLD);
			Vector2 velocity = _body.getLinearVelocity().mul(GameScreen.BOX_TO_WORLD);
			velocity = new Vector2(-velocity.y, velocity.x);
			float size = _size * GameScreen.BOX_TO_WORLD * REDUCTION_RATIO;
			
			// random asteroid
			Random generator = new Random();
			int randNumber = generator.nextInt( 1 ) + 1;
			Boulder boulder1 = new Boulder(_gamestate, new Vector2(spawnpos), _startrotation, 
					new Vector2(velocity), _startangvelocity, size, _splittimes + 1, randNumber);
			boulder1.spawn();
			
			_gamestate._gameObjects.add(boulder1);
			
			randNumber = generator.nextInt( 1 ) + 1;
			Boulder boulder2 = new Boulder(_gamestate, new Vector2(spawnpos), _startrotation, 
					new Vector2(velocity).mul(-1), _startangvelocity, size, _splittimes + 1,randNumber);
			
			boulder2.spawn();
			
			_gamestate._gameObjects.add(boulder2);
			
		}
		markRemoval();
	}

	@Override
	public void draw(SpriteBatch spritebatch) {
		if (_status == ObjectStatus.AVAILABLE)
		{
			Sprite sprite = new Sprite(_boulderTexture);
			Vector2 origin = loader.getOrigin("asteroid"+_boulderName, _size).cpy().mul(GameScreen.BOX_TO_WORLD);
			Vector2 pos = _body.getPosition().mul(GameScreen.BOX_TO_WORLD).sub(origin);
			
			sprite.setSize(_size*GameScreen.BOX_TO_WORLD, _size*GameScreen.BOX_TO_WORLD);
		    sprite.setPosition(pos.x, pos.y);
		    sprite.setOrigin(origin.x, origin.y);
		    sprite.setRotation(_body.getAngle() * MathUtils.radiansToDegrees);
			sprite.draw(spritebatch);
		}
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void update(float deltatime) {
		
	}
}
