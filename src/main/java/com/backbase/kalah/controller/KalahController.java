/**
 * 
 */
package com.backbase.kalah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import com.backbase.kalah.game.KalahGame;
import com.backbase.kalah.game.KalahGameDTO;
import com.backbase.kalah.game.KalahBrokenRuleException;
import com.backbase.kalah.game.MoveRequestDTO;
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
	 * End point to create a new game starting with the specified number of seeds in each pit
	 * @param seedNumber seedNumber
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/start/{seedNumber}", method =  RequestMethod.POST)
	public ResponseEntity<KalahGameDTO> startGame(@PathVariable(value = "seedNumber", required = true) Integer seedNumber) {
		KalahGame game = new KalahGame(seedNumber);
		getStore().putGame(game.getId(), game);
		return ResponseEntity.status(HttpStatus.CREATED).body(KalahGameDTO.getInstance(game));
	}
	
	/**
	 * End point to make a move in the board for a player from teh specified pit
	 * @param gameId gameId
	 * @param player player 
	 * @param pit pit
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/move/id/{gameId}", method =  RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KalahGameDTO> move(@PathVariable(value = "gameId", required = true) String gameId,
			@RequestBody MoveRequestDTO moveRequest) {
		KalahGame game = getStore().getGame(gameId);
		if (game == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(KalahGameDTO.getInstance(String.format("Game with ID %s was not found", gameId)));
		}
		try {
			game.move(moveRequest.getPlayer(), moveRequest.getPit());
		} catch (KalahBrokenRuleException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(KalahGameDTO.getInstance(e.getMessage()));
		}
		getStore().putGame(gameId, game);
		return ResponseEntity.ok(KalahGameDTO.getInstance(game));
	}
	
	/**
	 * End point to get a specified game from the store.
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value = "/status/{gameId}", method =  RequestMethod.GET)
	public ResponseEntity<KalahGameDTO> status(@PathVariable(value = "gameId", required = true) String gameId) {		
		return ResponseEntity.ok(KalahGameDTO.getInstance(store.getGame(gameId)));
	}
	
	/**
	 * End point to delete specified game from the store.
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