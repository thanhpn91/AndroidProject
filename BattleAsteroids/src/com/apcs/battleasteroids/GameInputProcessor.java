package com.apcs.battleasteroids;

import java.util.Timer;

import com.apcs.battleasteroids.GameObject.ObjectStatus;
import com.apcs.battleasteroids.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameInputProcessor implements InputProcessor {
	public GameScreen _game;
	
	public Vector2 _p1origin;
	public int _p1pointer;
	public Vector2 _p2origin;
	public int _p2pointer;
	public Vector2 _p1delta;
	public Vector2 _p2delta;
	public float _sensitivity;
	public long _shipRespawnTime;
	public BulletSpawner _bullet1;
	public BulletSpawner _bullet2;
	private Texture _controlTexture1 = new Texture(Gdx.files.internal("images/control_1.png"));
	private Texture _controlTexture2 = new Texture(Gdx.files.internal("images/control_2.png"));
	
	
	public GameInputProcessor(GameScreen game)
	{
		_game = game;
		_p1pointer = -1;
		_p2pointer = -1;
		_sensitivity = 0.05f;
		_p1delta = new Vector2(0, 1);
		_p2delta = new Vector2(0, 1);
		_p1origin = new Vector2(0, 0);
		_p2origin = new Vector2(0, 0);
		_shipRespawnTime = 3000;
		_bullet1 = new BulletSpawner(_game, _game._gamestate._ship1);
		_bullet2 = new BulletSpawner(_game, _game._gamestate._ship2);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX < _game._width / 2)
		{
			if (_game._gamestate._ship1._status == ObjectStatus.DESTROYED)// && System.currentTimeMillis() - _game._gamestate._ship1.GetDeathTimer() > 3000)
			{
				if (_game._gamestate._ship1SpawnTask.spawnable == true)
				{
					_game._gamestate._ship1.spawn();
					_game._gamestate._ship1SpawnTask = new ShipSpawnTask(false);
				}
				else if (_game._gamestate._ship1SpawnTask.spawning == false)
				{
					Timer timer = new Timer();
					timer.schedule(_game._gamestate._ship1SpawnTask, _shipRespawnTime);
					_game._gamestate._ship1SpawnTask.spawning = true;
//					_game._gamestate._invincibleTask = new InvincibleTask(_game._gamestate._ship1);
//					timer.schedule(_game._gamestate._invincibleTask, 3000);
				}
			}
			
			// P1 input zone
			if (screenY < _game._height / 2 && _game._gamestate._ship1._status == ObjectStatus.AVAILABLE)
			{
				// Spawn a new bullet
				_bullet1.run();
				return true;
			}
			else
			{
				// P1 move zone
				if (_p1pointer == -1)
				{
					_p1pointer = pointer;
					_p1origin = new Vector2(screenX, screenY);
				}
				
				return true;
			}
		}
		else if (screenX > _game._width / 2)
		{
			if (_game._gamestate._ship2._status == ObjectStatus.DESTROYED)// && System.currentTimeMillis() - _game._gamestate._ship2.GetDeathTimer() > 3000)
			{

				if (_game._gamestate._ship2SpawnTask.spawnable == true)
				{
					_game._gamestate._ship2.spawn();
					_game._gamestate._ship2SpawnTask = new ShipSpawnTask(false);
				}
				else if (_game._gamestate._ship2SpawnTask.spawning == false)
				{
					Timer timer = new Timer();
					timer.schedule(_game._gamestate._ship2SpawnTask, _shipRespawnTime);
					_game._gamestate._ship2SpawnTask.spawning = true;
//					_game._gamestate._invincibleTask = new InvincibleTask(_game._gamestate._ship2);
//					timer.schedule(_game._gamestate._invincibleTask, 3000);
				}
			}
			
			// P2 input zone
			if (screenY > _game._height / 2 && _game._gamestate._ship2._status == ObjectStatus.AVAILABLE)
			{
				// Spawn a new bullet
				_bullet2.run();
				return true;
			}
			else
			{
				// P2 move zone
				if (_p2pointer == -1)
				{
					_p2pointer = pointer;
					_p2origin = new Vector2(screenX, screenY);
				}
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (screenX < _game._width/2)
		{
			if (screenY < _game._height/2)
				_bullet1.stop();
		}
		else if (screenY > _game._height/2)
			_bullet2.stop();
		
		if (_p1pointer == pointer)
		{
			_p1pointer = -1;
			return true;
		}
		else if (_p2pointer == pointer)
		{
			_p2pointer = -1;
			return true;
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (_p1pointer == pointer)
		{
			_p1delta = new Vector2(screenX - _p1origin.x , _p1origin.y - screenY);
			return true;
		}
		else if (_p2pointer == pointer)
		{
			_p2delta = new Vector2(screenX - _p2origin.x,  _p2origin.y - screenY);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public void draw(SpriteBatch spriteBatch){
		if (_p1pointer != -1){ 
		Sprite sprite = new Sprite(_controlTexture1);
		
		sprite.setSize(_game._width/8,_game._width/8);
		sprite.setPosition(_p1origin.x - _game._width/16, _game._height - _p1origin.y - _game._width/16);
	       
		sprite.draw(spriteBatch);
		}
				
		if (_p2pointer != -1){ 
			Sprite sprite = new Sprite(_controlTexture2);
			
			sprite.setSize(_game._width/8,_game._width/8); 
			sprite.setPosition(_p2origin.x - _game._width/16, _game._height - _p2origin.y - _game._width/16);
		      
			sprite.draw(spriteBatch);
			}
	}
}
