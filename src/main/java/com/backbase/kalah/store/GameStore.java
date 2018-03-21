/**
 * 
 */
package com.backbase.kalah.store;

import java.util.Optional;

import com.backbase.kalah.game.KalahGame;

/**
 * Defines behavior for different store implementations
 * @author diegotoro
 */
public interface GameStore {
	
	public void putGame(String gameId, KalahGame game);
	
	public KalahGame getGame(String gameId);
	
	public Optional<KalahGame> deleteGame(String gameId);

}
