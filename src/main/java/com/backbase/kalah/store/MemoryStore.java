/**
 * 
 */
package com.backbase.kalah.store;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.backbase.kalah.game.Game;

/**
 * Stores games in memory
 * @author diegotoro
 */
@Component("MemoryStore")
public final class MemoryStore implements GameStore {
	
	private static Map<String, Game> store = new HashMap<>();

	@Override
	public void putGame(String gameId, Game game) {
		store.put(gameId, game);
	}

	@Override
	public Game getGame(String gameId) {
		return store.get(gameId);
	}

	@Override
	public void deleteGame(String gameId) {
		store.remove(gameId);
	}

}
