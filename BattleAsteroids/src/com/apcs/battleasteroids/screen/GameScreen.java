package com.apcs.battleasteroids.screen;

import java.util.Random;
import java.util.logging.Level;

import com.apcs.battleasteroids.BattleAsteroidsGame;
import com.apcs.battleasteroids.Boulder;
//import com.apcs.battleasteroids.GameBorder;
import com.apcs.battleasteroids.GameContactListener;
import com.apcs.battleasteroids.GameInputProcessor;
import com.apcs.battleasteroids.GameObject;
import com.apcs.battleasteroids.GameObject.ObjectStatus;
import com.apcs.battleasteroids.GameState;
import com.apcs.battleasteroids.Ship;
import com.apcs.battleasteroids.ShipSpawnTask;
import com.apcs.battleasteroids.Wall;
import com.apcs.battleasteroids.services.MusicManager.BattleAsteroidsMusic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameScreen extends AbstractScreen {

	public float _width;
	public float _height;
	// whether we are in development mode
	public static final boolean DEV_MODE = false;

	public static final float BOX_TO_WORLD = 50f;
	public static final float WORLD_TO_BOX = 1 / 50f;

	public GameState _gamestate;
	public OrthographicCamera _camera;
	public Box2DDebugRenderer _renderer;
	public GameInputProcessor _input;
	public GameContactListener _contact;

	public Matrix4 _boxmatrix;
	
	
	
	public GameScreen(BattleAsteroidsGame game) {
		super(game);

		// create the listeners

		// Initialize the game state
		_gamestate = new GameState();

		// Calculate some core coordinates and sizs
		// Calculate some core coordinates and sizs
		_width = Gdx.graphics.getWidth();
		_height = Gdx.graphics.getHeight();
		_gamestate._wallstart = new Vector2(_width / 6, 0);
		_gamestate._wallend = new Vector2(5 * _width / 6, _height);
		_gamestate._wallthick = _height / 15;
		_gamestate._shipsize = _width / 35;
		_gamestate._bulletsize = _height / 500 ;
		_gamestate._bulletspeed = _height / 10;
		
		// Prepare the camera
		_camera = new OrthographicCamera(_width, _height);
		_boxmatrix = new Matrix4(_camera.combined);
		_boxmatrix.scale(BOX_TO_WORLD, BOX_TO_WORLD, 1);
		_boxmatrix.translate(-_width / 2 * WORLD_TO_BOX, -_height / 2 * WORLD_TO_BOX, 0);
		_camera.translate(_width / 2, _height / 2);
		
		_renderer = new Box2DDebugRenderer(true, true, true, true, true);
		
		// Setup the ships
		_gamestate._ship1 = new Ship(_gamestate, new Vector2(_gamestate._wallstart.x + _gamestate._wallthick + 2 * _gamestate._shipsize,
				_height - _gamestate._wallthick - 2 * _gamestate._shipsize),
				0, _gamestate._shipsize,"air1");
		_gamestate._ship1.spawn();
		_gamestate._gameObjects.add(_gamestate._ship1);
		_gamestate._ship2 = new Ship(_gamestate, new Vector2(_gamestate._wallend.x - _gamestate._wallthick - 2 * _gamestate._shipsize,
				_gamestate._wallthick + 2 * _gamestate._shipsize),
				0, _gamestate._shipsize,"air2");
		_gamestate._ship2.spawn();
		_gamestate._gameObjects.add(_gamestate._ship2);
		
		// Setup the ship spawn tasks
		_gamestate._ship1SpawnTask = new ShipSpawnTask(false);
		_gamestate._ship2SpawnTask = new ShipSpawnTask(false);
		
		// Setup the walls
		Wall walls = new Wall(_gamestate, new Vector2(_gamestate._wallstart.x, _gamestate._wallstart.y),
				new Vector2(_gamestate._wallend.x, _gamestate._wallthick));
		walls.spawn();
		_gamestate._gameObjects.add(walls);
		Wall walln = new Wall(_gamestate, 
				new Vector2(_gamestate._wallstart.x, _height - _gamestate._wallthick),
				new Vector2(_gamestate._wallend.x, _height));
		walln.spawn();
		_gamestate._gameObjects.add(walln);
		Wall wallw = new Wall(_gamestate, _gamestate._wallstart,
				new Vector2(_gamestate._wallstart.x + _gamestate._wallthick, _height));
		wallw.spawn();
		_gamestate._gameObjects.add(wallw);
		Wall walle = new Wall(_gamestate,
				new Vector2(_gamestate._wallend.x - _gamestate._wallthick, 0), _gamestate._wallend);
		walle.spawn();
		
//		_gamestate._gameBorder = new GameBorder(_gamestate, new Vector2(_gamestate._wallstart.x, _gamestate._wallstart.y), new Vector2(_gamestate._wallend.x, _gamestate._wallend.y), _gamestate._wallthick);
//		for (Wall wall : _gamestate._gameBorder._walls)
//		{
//			wall.spawn();
//			_gamestate._gameObjects.add(wall);
//		}
		SpriteBatch spritebatch = new SpriteBatch();
		spritebatch.begin();
		// TODO: Components draw code
		
		
		
		// Setup some boulders
		Random generator = new Random();
		int randNumber = generator.nextInt(1) + 1;
		Boulder boulder1 = new Boulder(_gamestate, new Vector2(_width / 2, _height / 2),
				MathUtils.PI / 4, new Vector2(200, 200), MathUtils.PI / 6, 100, 0, randNumber);
		boulder1.spawn();
		randNumber = generator.nextInt(1) + 1;
		_gamestate._gameObjects.add(boulder1);
		Boulder boulder2 = new Boulder(_gamestate, new Vector2(_width / 2, _height / 2),
				MathUtils.PI / 4, new Vector2(-200, -200), MathUtils.PI / 6, 100, 0, randNumber);
		
		boulder2.spawn();
		_gamestate._gameObjects.add(boulder2);
		
		randNumber = generator.nextInt(1) + 1;
		Boulder boulder3 = new Boulder(_gamestate, new Vector2(_width / 2, _height / 2),
				MathUtils.PI / 4, new Vector2(200, -200), MathUtils.PI / 6, 100, 0, randNumber);
		boulder3.spawn();
		randNumber = generator.nextInt(1) + 1;
		_gamestate._gameObjects.add(boulder3);
		Boulder boulder4 = new Boulder(_gamestate, new Vector2(_width / 2, _height / 2),
				MathUtils.PI / 4, new Vector2(-200, 200), MathUtils.PI / 6, 100, 0, randNumber);
		
		boulder4.spawn();
		_gamestate._gameObjects.add(boulder4);
		
		// Setup the input processor
		_input = new GameInputProcessor(this);
		Gdx.input.setInputProcessor(_input);
		
		// Setup the contact listener
		_contact = new GameContactListener(this);
		_gamestate._world.setContactListener(_contact);
		_input.draw(spritebatch);
		spritebatch.end();
	}

	public void dispose() {

	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		game.getMusicManager().play(BattleAsteroidsMusic.LEVEL);
		float timedelta = Gdx.app.getGraphics().getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		_camera.update();

		// Set ships velocity and ships orientation based on the user input
		if (_gamestate._ship1._status == ObjectStatus.AVAILABLE) {
			if (_input._p1pointer != -1)
				_gamestate._ship1._body.setLinearVelocity(new Vector2(
						_input._p1delta.x * _input._sensitivity,
						_input._p1delta.y * _input._sensitivity));
			else
				_gamestate._ship1._body.setLinearVelocity(new Vector2(0, 0));
			_gamestate._ship1._body.setTransform(
					_gamestate._ship1._body.getPosition(),
					MathUtils.atan2(_input._p1delta.y, _input._p1delta.x));
		}
		if (_gamestate._ship2._status == ObjectStatus.AVAILABLE) {
			if (_input._p2pointer != -1)
				_gamestate._ship2._body.setLinearVelocity(new Vector2(
						_input._p2delta.x * _input._sensitivity,
						_input._p2delta.y * _input._sensitivity));
			else
				_gamestate._ship2._body.setLinearVelocity(new Vector2(0, 0));
			_gamestate._ship2._body.setTransform(
					_gamestate._ship2._body.getPosition(),
					MathUtils.atan2(_input._p2delta.y, _input._p2delta.x));
		}

		SpriteBatch spritebatch = new SpriteBatch();
		spritebatch.begin();
		// TODO: Components draw code
		for (GameObject object : _gamestate._gameObjects)
		{
			object.draw(spritebatch);
		}
		_input.draw(spritebatch);
		spritebatch.end();
		//_renderer.render(_gamestate._world, _boxmatrix);
		
		_gamestate._world.step(timedelta, 10, 10);
		_gamestate.sweepDeadBodies();
		_gamestate.updateObject(timedelta);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
}
