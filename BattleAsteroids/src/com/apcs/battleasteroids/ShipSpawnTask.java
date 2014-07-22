package com.apcs.battleasteroids;
import java.util.TimerTask;

import com.apcs.battleasteroids.GameObject.ObjectStatus;


public class ShipSpawnTask extends TimerTask {
	boolean spawnable;
	boolean spawning;
	
	public ShipSpawnTask(boolean spawnable)
	{
		this.spawnable = spawnable;
		this.spawning = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		spawnable = true;
		this.spawning = false;
	}
	
}