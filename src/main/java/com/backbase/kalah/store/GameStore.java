/**
 * 
 */
package com.backbase.kalah.store;

import com.backbase.kalah.game.Game;

/**
 * Defines behavior for different store implementations
 * @author diegotoro
 */
public interface GameStore {
	
	public void putGame(String gameId, Game game);
	
	public Game getGame(String gameId);
	
	public void deleteGame(String gameId);

}
