package com.apcs.battleasteroids;

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


public class Ship extends GameObject {
	// Internal Object information
	private Vector2 _startpoint;
	private float _startrotation;
	private float _size;	
	private float[] _coords = new float[] {-0.33f, -0.33f, 0.66f, 0, -0.33f, 0.33f};
	private String _shipName;
	private Texture _shipTexture;
	public boolean _destructable;
	private float _point;
	
	public float GetPoint()
	{
		return _point;
	}
	
	public void SetPoint(float point)
	{
		_point = point;
	}
	
	
	
	public Ship(GameState state, Vector2 startpoint, float startrotation, float size, String shipName)
	{
		// Setting variables
		
		super(state, null, ObjectType.SHIP, ObjectStatus.DESTROYED);
		_startpoint = startpoint.mul(GameScreen.WORLD_TO_BOX);
		_shipTexture = new Texture(Gdx.files.internal("images/" +shipName +".png"));
		_shipName = shipName;
		_startrotation = startrotation;
		_size = size * GameScreen.WORLD_TO_BOX;
		
		for (int i = 0; i < _coords.length; ++i)
		{
			_coords[i] *= _size;
		}
		
		_destructable = true;
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
			fd.restitution = 0f;
			fd.filter.categoryBits = _type.getShort();
			fd.filter.maskBits = (short)(ObjectType.BOULDER.getShort() | ObjectType.BULLET.getShort() 
					| ObjectType.WALL.getShort() | ObjectType.SHIP.getShort());
		
			_body = _gamestate._world.createBody(bd);
			loader.attachFixture(_body, _shipName, fd, _size);
			_body.setUserData(this);
			
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
		
	}

	@Override
	public void draw(SpriteBatch spritebatch) {
		if (_status == ObjectStatus.AVAILABLE)
		{
				
			Sprite sprite = new Sprite(_shipTexture);
			/*Vector2 position = _body.getWorldCenter().mul(GameScreen.BOX_TO_WORLD);
			Vector2 origin = _body.getLocalCenter().mul(GameScreen.BOX_TO_WORLD);
			float width = _size * GameScreen.BOX_TO_WORLD;
			float height = _size * GameScreen.BOX_TO_WORLD;
			sprite.setOrigin(origin.x + width / 2, origin.y + width / 2);
			sprite.rotate(_body.getAngle() / MathUtils.PI * 180);
			sprite.translate(position.x - width / 2, position.y - height / 2);
			sprite.setSize(_size*GameScreen.BOX_TO_WORLD, _size*GameScreen.BOX_TO_WORLD);*/
			Vector2 origin = loader.getOrigin(_shipName, _size).cpy().mul(GameScreen.BOX_TO_WORLD);
			Vector2 pos = _body.getPosition().mul(GameScreen.BOX_TO_WORLD).sub(origin);

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
