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
public class KalahGameDTO {
	
	private String id;
	private Score score;
	private Player turn;
	private Status status;
	private String error;
	
	/**
	 * Constructor
	 * @param game game
	 */
	private KalahGameDTO(KalahGame game) {
		this.id = game.getId();
		this.score = game.getScore();
		this.turn = game.getTurn();
		this.status = game.getStatus();
	}
	
	/**
	 * Constructor
	 * @param game game
	 */
	private KalahGameDTO(String error) {
		this.error = error;
	}
	
	public static KalahGameDTO getInstance(KalahGame game) {
		return new KalahGameDTO(game);
	}
	
	public static KalahGameDTO getInstance(String error) {
		return new KalahGameDTO(error);
	}
}
