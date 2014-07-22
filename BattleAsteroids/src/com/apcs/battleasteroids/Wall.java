package com.apcs.battleasteroids;

import com.apcs.battleasteroids.screen.GameScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall extends GameObject {
	// Internal Object information
	private Vector2 _startpoint;
	private Vector2 _endpoint;
	
	public Wall(GameState state, Vector2 startpoint, Vector2 endpoint)
	{
		// Setting variables
		super(state, null, ObjectType.WALL, ObjectStatus.DESTROYED);
		_startpoint = startpoint.mul(GameScreen.WORLD_TO_BOX);
		_endpoint = endpoint.mul(GameScreen.WORLD_TO_BOX);
	}
	
	@Override
	public void spawn() {
		if (_status == ObjectStatus.DESTROYED)
		{
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			
			FixtureDef fd = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			float xmin, xmax, ymin, ymax;
			if (_startpoint.x < _endpoint.x)
			{
				xmin = _startpoint.x;
				xmax = _endpoint.x;
			}
			else
			{
				xmin = _endpoint.x;
				xmax = _startpoint.x;
			}
			if (_startpoint.y < _endpoint.y)
			{
				ymin = _startpoint.y;
				ymax = _endpoint.y;
			}
			else
			{
				ymin = _endpoint.y;
				ymax = _startpoint.y;
			}
			shape.set(new float[]{xmin, ymin, xmax, ymin, xmax, ymax, xmin, ymax});
			
			fd.shape = shape;
			fd.density = 1;
			fd.friction = 0.5f;
			fd.restitution = 0.3f;
			fd.filter.categoryBits = _type.getShort();
			fd.filter.maskBits = (short)(ObjectType.BOULDER.getShort() | ObjectType.BULLET.getShort()
					| ObjectType.WALL.getShort() | ObjectType.SHIP.getShort());
		
			_body = _gamestate._world.createBody(bd);
			_body.setUserData(this);
			_body.createFixture(fd);
			
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
		
	}

	@Override
	public void draw(SpriteBatch spritebatch) {
		if (_status == ObjectStatus.AVAILABLE)
		{
			
		}
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void update(float deltatime) {
		
	}
}
