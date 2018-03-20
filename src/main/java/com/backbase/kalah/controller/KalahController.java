/**
 * 
 */
package com.backbase.kalah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import com.backbase.kalah.game.Game;
import com.backbase.kalah.game.GameDTO;
import com.backbase.kalah.game.Player;
import com.backbase.kalah.store.GameStore;
   
/**
 * 
 * @author diegotoro
 *
 */
@RestController
@ApplicationScope
@RequestMapping("/kalah")
public class KalahController {
		
	private GameStore store;
	
	@Value("${kalah.game.storeType}")
	private String storeType;
	
	@Autowired
	private ApplicationContext context;

	/**
	 * 
	 * @param seedNumber
	 * @return
	 */
	@RequestMapping(value = "/start/{seedNumber}", method =  RequestMethod.POST)
	public ResponseEntity<GameDTO> startGame(@ PathVariable(value = "seedNumber", required = true) Integer seedNumber) {
		Game game = new Game(seedNumber);
		getStore().putGame(game.getId(), game);
		return ResponseEntity.status(HttpStatus.CREATED).body(GameDTO.getInstance(game));
	}
	
	/**
	 * 
	 * @param gameId
	 * @param player
	 * @param pit
	 * @return
	 */
	@RequestMapping(value = "/move/id/{gameId}/player/{player}/pit/{pit}", method =  RequestMethod.PUT)
	public ResponseEntity<GameDTO> move(@PathVariable(value = "gameId", required = true) String gameId,
			@PathVariable(value = "player", required = true) Player player,
			@PathVariable(value = "pit", required = true) Integer pit) {
		Game game = getStore().getGame(gameId);
		if (game == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GameDTO.getInstance("Game not started"));
		}
		game.move(player, pit);
		getStore().putGame(gameId, game);
		return ResponseEntity.ok(GameDTO.getInstance(game));
	}
	
	/**
	 * 
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value = "/status/{gameId}", method =  RequestMethod.GET)
	public ResponseEntity<GameDTO> status(@PathVariable(value = "gameId", required = true) String gameId) {		
		return ResponseEntity.ok(GameDTO.getInstance(store.getGame(gameId)));
	}
	
	/**
	 * 
	 * @param gameId
	 */
	@RequestMapping(value = "/id", method =  RequestMethod.DELETE)
	public ResponseEntity<Void> deleteGame(@PathVariable(value = "gameId", required = true) String gameId) {		
		getStore().deleteGame(gameId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	/**
	 * Instantiates GameStore with the type specified in the app configuration
	 * @return GameStore store
	 */
	private GameStore getStore() {
		if (this.store == null) {
			this.store = (GameStore) context.getBean(this.storeType);
		}
		return this.store;
	}
}