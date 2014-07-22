package com.apcs.battleasteroids.services;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.apcs.battleasteroids.BattleAsteroidsGame;
import com.apcs.battleasteroids.services.SoundManager.BattleAsteroidsSound;
import com.apcs.battleasteroids.ultis.LRUCache.CacheEntryRemovedListener;
import com.apcs.battleasteroids.ultis.LRUCache;


public class SoundManager implements
    CacheEntryRemovedListener<BattleAsteroidsSound,Sound>,
    Disposable{
	/**
	 * The available sound files.
	 */
	public enum BattleAsteroidsSound
	{
	    CLICK( "sound/click.mp3" );
	
	    private final String fileName;
	
	    private BattleAsteroidsSound(
	        String fileName )
	    {
	        this.fileName = fileName;
	    }
	
	    public String getFileName()
	    {
	        return fileName;
	    }
	}
	
	/**
	 * The volume to be set on the sound.
	 */
	private float volume = 1f;
	
	/**
	 * Whether the sound is enabled.
	 */
	private boolean enabled = true;
	
	/**
	 * The sound cache.
	 */
	private final LRUCache<BattleAsteroidsSound,Sound> soundCache;
	
	/**
	 * Creates the sound manager.
	 */
	public SoundManager()
	{
	    soundCache = new LRUCache<SoundManager.BattleAsteroidsSound,Sound>( 10 );
	    soundCache.setEntryRemovedListener( this );
	}
	
	/**
	 * Plays the specified sound.
	 */
	public void play(
			BattleAsteroidsSound sound )
	{
	    // check if the sound is enabled
	    if( ! enabled ) return;
	
	    // try and get the sound from the cache
	    Sound soundToPlay = soundCache.get( sound );
	    if( soundToPlay == null ) {
	        FileHandle soundFile = Gdx.files.internal( sound.getFileName() );
	        soundToPlay = Gdx.audio.newSound( soundFile );
	        soundCache.add( sound, soundToPlay );
	    }
	
	    // play the sound
	    Gdx.app.log( BattleAsteroidsGame.LOG, "Playing sound: " + sound.name() );
	    soundToPlay.play( volume );
	}
	
	/**
	 * Sets the sound volume which must be inside the range [0,1].
	 */
	public void setVolume(
	    float volume )
	{
	    Gdx.app.log( BattleAsteroidsGame.LOG, "Adjusting sound volume to: " + volume );
	
	    // check and set the new volume
	    if( volume < 0 || volume > 1f ) {
	        throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
	    }
	    this.volume = volume;
	}
	
	/**
	 * Enables or disabled the sound.
	 */
	public void setEnabled(
	    boolean enabled )
	{
	    this.enabled = enabled;
	}
	
	// EntryRemovedListener implementation
	
	
	public void notifyEntryRemoved(
	    BattleAsteroidsSound key,
	    Sound value )
	{
	    Gdx.app.log( BattleAsteroidsGame.LOG, "Disposing sound: " + key.name() );
	    value.dispose();
	}
	
	/**
	 * Disposes the sound manager.
	 */
	public void dispose()
	{
	    Gdx.app.log( BattleAsteroidsGame.LOG, "Disposing sound manager" );
	    for( Sound sound : soundCache.retrieveAll() ) {
	        sound.stop();
	        sound.dispose();
	    }
	}
}

