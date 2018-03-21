/**
 * 
 */
package com.backbase.kalah.game;

import lombok.Data;

/**
 * Represents a request to do a move in the kalah game
 * @author dtoro
 */
@Data
public class MoveRequestDTO {
	
	private Player player;
	
	private int pit;

	/**
	 * Constructor
	 */
	public MoveRequestDTO(Player player, int pit) {
		this.player = player;
		this.pit = pit;
	}
	
	public MoveRequestDTO() {
		super();
	}

}
