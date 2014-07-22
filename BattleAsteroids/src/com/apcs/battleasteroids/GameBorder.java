//package com.apcs.battleasteroids;
//
//import com.apcs.battleasteroids.screen.GameScreen;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
//import com.badlogic.gdx.physics.box2d.PolygonShape;
//
//public class GameBorder extends GameObject {
//	// Internal Object information
//	private Vector2 _startpoint;
//	private Vector2 _endpoint;
//	private float[][] _coords;
//	private float _thickness;
//	public Wall[] _walls;
//	
//	public GameBorder(GameState state, Vector2 startpoint, Vector2 endpoint, float thickness)
//	{
//		// Setting variables
//		//super(state, null, ObjectType.WALL, ObjectStatus.DESTROYED);
//		_startpoint = startpoint.mul(GameScreen.WORLD_TO_BOX);
//		_endpoint = endpoint.mul(GameScreen.WORLD_TO_BOX);
//		_thickness = thickness;
//		
//		float width, height,x1, x2, x3, x4, y1, y2, y3, y4;
//		width = _endpoint.x - _startpoint.x;
//		height = _endpoint.y - _startpoint.y;
//		x1 = _startpoint.x;
//		x2 = _startpoint.x + width/3;
//		x3 = _startpoint.x + 2*width/3;
//		x4 = _startpoint.x + width;
//		
//		y1 = _startpoint.y;
//		y2 = _startpoint.y + height/3;
//		y3 = _startpoint.y + 2*height/3;
//		y4 = _startpoint.y + height;
//		
//		_coords = new float[][]{{x2, y1, x2, y1 + _thickness, x3, y1, x3, y1 + _thickness},
//				{x2,y4, x2, y4 - _thickness, x3, y4, x3, y4 - _thickness},
//				{x1, y2, x1 + _thickness, y2, x1, y3, x1 + _thickness, y3},
//				{x4, y2, x4 - _thickness, y2, x4, y3, x4 - _thickness, y3},
//				{x2, y1, x2, y1 + _thickness, x1 + _thickness, y2, x1, y2},
//				{x3, y1, x3, y1 + _thickness, x4 - _thickness, y2, x4, y2},
//				{x2, y4, x2, y4 - _thickness, x1 + _thickness, y3, x1, y3},
//				{x4, y3, x4 - _thickness, y3, x3, y4 - _thickness, x3, y4}};
////		_walls = new Wall[8];
//		for (int i = 0; i < 8; ++i)
//		{
//			_walls[i] = new Wall(state, new Vector2(_coords[0][i],_coords[1][i]), new Vector2(_coords[2][i],_coords[3][i]), new Vector2(_coords[4][i],_coords[5][i]), new Vector2(_coords[6][i],_coords[7][i]));
//		}
//		
////	}
//	
//	@Override
//	public void spawn()
//	{
//		if (_status == ObjectStatus.DESTROYED)
//		{
//			BodyDef bd = new BodyDef();
//			bd.type = BodyType.StaticBody;
//			
//			FixtureDef fd = new FixtureDef();
//			PolygonShape shape = new PolygonShape();
//			
//		
//			shape.set(new float[]{xmin, ymin, xmax, ymin, xmax, ymax, xmin, ymax});
//			
//			fd.shape = shape;
//			fd.density = 1;
//			fd.friction = 0.5f;
//			fd.restitution = 0.3f;
//			fd.filter.categoryBits = _type.getShort();
//			fd.filter.maskBits = (short)(ObjectType.BOULDER.getShort() | ObjectType.BULLET.getShort()
//					| ObjectType.WALL.getShort() | ObjectType.SHIP.getShort());
//		
//			_body = _gamestate._world.createBody(bd);
//			_body.setUserData(this);
//			_body.createFixture(fd);
//			
//			_status = ObjectStatus.AVAILABLE;
//			shape.dispose();
//		}
//	}
//	
//	@Override
//	public void markDestruct() {
//		if (_status == ObjectStatus.AVAILABLE)
//			_status = ObjectStatus.DESTROYING;
//	}
//
//	@Override
//	public void destruct() {
//		
//	}
//
//	@Override
//	public void draw() {
//		if (_status == ObjectStatus.AVAILABLE)
//		{
//			
//		}
//	}
//	
//	@Override
//	public void dispose() {
//		
//	}
//
//	@Override
//	public void update(float deltatime) {
//		
//	}
//
//	@Override
//	public void draw(SpriteBatch spritebatch) {
//		// TODO Auto-generated method stub
//		
//	}
//}
