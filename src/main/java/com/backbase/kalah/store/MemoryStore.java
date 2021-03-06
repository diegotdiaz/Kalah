/**
 * 
 */
package com.backbase.kalah.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.backbase.kalah.game.KalahGame;

/**
 * Implementation of GameStore which stores games in memory.
 * @author diegotoro
 */
@Component("MemoryStore")
public final class MemoryStore implements GameStore {
	
	private static Map<String, KalahGame> store = new HashMap<>();

	@Override
	public void putGame(String gameId, KalahGame game) {
		store.put(gameId, game);
	}

	@Override
	public KalahGame getGame(String gameId) {
		return store.get(gameId);
	}

	@Override
	public Optional<KalahGame> deleteGame(String gameId) {
		Optional<KalahGame> game = Optional.ofNullable(store.remove(gameId));
		return game;
	}

}
