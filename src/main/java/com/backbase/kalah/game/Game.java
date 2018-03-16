/**
 * 
 */
package com.backbase.kalah.game;

import java.util.Random;
import java.util.stream.IntStream;

import lombok.Data;

/**
 * @author dtoro
 *
 */
@Data
public class Game {

	private Board board;
	private Player turn;
	
	public Game(final int seedNumber) {
		this.board = new Board(seedNumber);
		chooseStartingPlayer();
	}
	
	/**
	 * Makes a move for the specified player, from the specified house in the board.
	 * @param player player.
	 * @param houseIndex house Index, 1-based index.
	 */
	private void move(final Player player, final int houseIndex) {
		validateMove(player, houseIndex);
		
		int startIndex = player.equals(Player.PLAYER_1) ? houseIndex - 1 :  houseIndex + 6;
		
		int seeds = board.getPitList().get(startIndex);
		
		for (int i = startIndex; seeds > 0; seeds--) {
			int seedCount = board.getPitList().get(i);
			
			//TODO Look at index resolving for macalas
			if ((i == Board.P1_MACALA_INDEX && player.equals(Player.PLAYER_2)) ||
					i == Board.P2_MACALA_INDEX && player.equals(Player.PLAYER_1)) {
				seeds += 1;
				continue;
			}
	
			board.getPitList().set(i, seedCount + 1);
		
			
			if (seeds == 0 && !((i == Board.P1_MACALA_INDEX && player.equals(Player.PLAYER_1)) || 
					(i == Board.P2_MACALA_INDEX && player.equals(Player.PLAYER_2)))) {
				switchTurn();
			}
		}
				
	}
	
	private void switchTurn() {
		this.turn = this.turn.equals(Player.PLAYER_1) ? Player.PLAYER_2: Player.PLAYER_1;
	}
	
	private void chooseStartingPlayer() {
		this.turn = Player.values()[new Random(Player.values().length).nextInt()];
	}
	
	
	public void validateMove(final Player player, final int houseIndex) {	
	
		boolean validHouseIndex = IntStream.rangeClosed(1, 6).anyMatch(i -> i == houseIndex);
		
		if (!validHouseIndex) {
			throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from small pits", player));
		}
		
		boolean validPlayer =  Player.valueOf(player.toString()) != null;
		
		if (!validPlayer) {
			throw new IllegalArgumentException(String.format("Unknown player: %s", player));
		}
		
		boolean playerTurn = turn.equals(player);
		
		if (!playerTurn) {
			throw new IllegalArgumentException(String.format("It' not %s turn", player));
		}
		
	}
}
