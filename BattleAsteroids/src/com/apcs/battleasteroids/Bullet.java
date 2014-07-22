package com.apcs.battleasteroids;

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

public class Bullet extends GameObject {
	// Internal Object information
	private Vector2 _startpoint;
	private Vector2 _startvelocity;
	private float _size;
	private Texture _bulletTexture;
	public Bullet(GameState state, Vector2 startpoint,  Vector2 startvelocity, float size, GameObject owner)
	{
		// Setting variables
		super(state, owner, ObjectType.BULLET, ObjectStatus.DESTROYED);
		_bulletTexture = new Texture(Gdx.files.internal("images/bullet.png"));
		_startpoint = startpoint.mul(GameScreen.WORLD_TO_BOX);
		_startvelocity = startvelocity.mul(GameScreen.WORLD_TO_BOX);
		_size = size * GameScreen.WORLD_TO_BOX;
	}
	
	@Override
	public void spawn() {
		if (_status == ObjectStatus.DESTROYED)
		{
			BodyDef bd = new BodyDef();
			bd.position.set(_startpoint);
			bd.type = BodyType.DynamicBody;
			
			FixtureDef fd = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(_size, _size);
			fd.shape = shape;
			fd.density = 1f;
			fd.friction = 0f;
			fd.restitution = 0f;
			fd.filter.categoryBits = _type.getShort();
			fd.filter.maskBits = (short)(ObjectType.BOULDER.getShort() | ObjectType.WALL.getShort() | ObjectType.SHIP.getShort());
		
			_body = _gamestate._world.createBody(bd);
			_body.setUserData(this);
			_body.createFixture(fd);
			_body.setLinearVelocity(_startvelocity);
			
			_status = ObjectStatus.AVAILABLE;
			shape.dispose();
		}
	}
	
	@Override
	public void markDestruct() {
		if (_status == ObjectStatus.AVAILABLE)
			_status = ObjectStatus.DESTROYING;
	}

	@Override
	public void destruct() {
		markRemoval();
	}

	@Override
	public void draw(SpriteBatch spritebatch) {
		if (_status == ObjectStatus.AVAILABLE)
		{
			Sprite sprite = new Sprite(_bulletTexture);
			Vector2 origin = _body.getLocalCenter().cpy().mul(GameScreen.BOX_TO_WORLD);
			Vector2 pos = _body.getWorldCenter().mul(GameScreen.BOX_TO_WORLD).sub(origin);
			sprite.setSize(_size*GameScreen.BOX_TO_WORLD*4, _size*GameScreen.BOX_TO_WORLD *4);
		    sprite.setPosition(pos.x - _size*GameScreen.BOX_TO_WORLD*2, pos.y - _size*GameScreen.BOX_TO_WORLD*2);		    
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
