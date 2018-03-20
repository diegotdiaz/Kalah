/**
 * 
 */
package com.backbase.kalah.store;

import com.backbase.kalah.game.KalahGame;

/**
 * Defines behavior for different store implementations
 * @author diegotoro
 */
public interface GameStore {
	
	public void putGame(String gameId, KalahGame game);
	
	public KalahGame getGame(String gameId);
	
	public void deleteGame(String gameId);

}
