package com.apcs.battleasteroids;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.apcs.battleasteroids.GameObject.ObjectStatus;
import com.apcs.battleasteroids.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class BulletSpawner implements Runnable {
	public GameScreen _game;
	public Ship ship;
	public ScheduledExecutorService t;
	
	BulletSpawner(GameScreen g, Ship s)
	{
		_game = g;
		ship = s;
		t = Executors.newSingleThreadScheduledExecutor();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (t.isShutdown())
			t = Executors.newSingleThreadScheduledExecutor();
		
		t.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub		
				
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (ship._status == ObjectStatus.AVAILABLE)
								{
									Vector2 direction = new Vector2((float)Math.cos(ship._body.getAngle()),
											(float)Math.sin(ship._body.getAngle()));
									Bullet bullet = new Bullet(_game._gamestate, 
											ship._body.getPosition().mul(GameScreen.BOX_TO_WORLD).add(direction.mul(_game._gamestate._shipsize / 2)),
											direction.mul(_game._gamestate._bulletspeed), _game._gamestate._bulletsize, ship);
									bullet.spawn();
									_game._gamestate._gameObjects.add(bullet);
								}
							}
						});
			}
		},0,200,TimeUnit.MILLISECONDS);
	}
	
	public void stop()
	{
		if (!t.isShutdown())
			t.shutdown();
	}
} 
