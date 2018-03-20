/**
 * 
 */
package com.backbase.kalah.game;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Game DTO to pass data through layers
 * @author diegotoro
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDTO {
	
	private String id;
	private Score score;
	private Player turn;
	private Status status;
	private String error;
	
	/**
	 * Constructor
	 * @param game game
	 */
	private GameDTO(Game game) {
		this.id = game.getId();
		this.score = game.getScore();
		this.turn = game.getTurn();
		this.status = game.getStatus();
	}
	
	/**
	 * Constructor
	 * @param game game
	 */
	private GameDTO(String error) {
		this.error = error;
	}
	
	public static GameDTO getInstance(Game game) {
		return new GameDTO(game);
	}
	
	public static GameDTO getInstance(String error) {
		return new GameDTO(error);
	}
}
