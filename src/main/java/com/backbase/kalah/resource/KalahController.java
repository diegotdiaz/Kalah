/**
 * 
 */
package com.backbase.kalah.resource;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backbase.kalah.game.Game;
import com.backbase.kalah.game.Player;

/**
 * @author diegotoro
 *
 */
@RestController
@RequestMapping("/kalah")
public class KalahController {
	
	private Game game;

	@RequestMapping(value = "/start")
	public ResponseEntity<String> startGame() {
		game = new Game(6);
		return ResponseEntity.ok(game.getScore().toString());
	}
	
	@RequestMapping(value = "/move/player/{player}/pit/{pit}")
	public ResponseEntity<String> move(@PathParam(value = "player") Player player,
			@PathParam(value = "pit") Integer pit) {
		game.move(player, pit);
		return ResponseEntity.ok(game.getScore().toString());
	}
}
