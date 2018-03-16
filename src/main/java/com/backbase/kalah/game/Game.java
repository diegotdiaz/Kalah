/**
 * 
 */
package com.backbase.kalah.game;

import lombok.Data;

/**
 * @author dtoro
 *
 */
@Data
public class Game {

	private Board board;
	
	public Game(final int seedNumber) {
		this.board = new Board(seedNumber);
	}
	
	/**
	 * Makes a move for the specified player, from the specified house in the board.
	 * @param player player.
	 * @param houseIndex house Index.
	 */
	public void move(Player player, int houseIndex) {
		switch (player) {
		case PLAYER_1:
			if (houseIndex > 5) {
				throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from it's own side", player));
			}
			if (houseIndex == 5) {
				throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from small pits", player));
			}
			
			break;
		case PLAYER_2:
			if (houseIndex < 6) {
				throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from it's own side", player));
			}
			if (houseIndex == 13) {
				throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from small pits", player));
			}
			break;
		default:
			throw new IllegalArgumentException(String.format("Unknown player: %s", player));
		}
	}
	
}
