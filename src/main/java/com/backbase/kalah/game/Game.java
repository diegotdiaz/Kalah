/**
 * 
 */
package com.backbase.kalah.game;

import java.util.Random;

import lombok.Data;

/**
 * @author dtoro
 *
 */
@Data
public class Game {

	private Board board;
	private Player turn;
	
	/**
	 * Constructor.
	 * @param numOfSeeds number of seeds for pits.
	 */
	public Game(final int numOfSeeds) {
		this.board = new Board(numOfSeeds);
		chooseStartingPlayer();
	}
	
	/**
	 * Makes a move for the specified player, from the specified pit in the board.
	 * @param player player.
	 * @param pitIndex house Index, 1-based index.
	 */
	public void move(final Player player, final int pitIndex) {
		validateMove(player, pitIndex);
		
		int startIndex = player.equals(Player.PLAYER_1) ? pitIndex - 1 :  pitIndex + 6;
		
		int seeds = board.getSeedsInPit(startIndex);
		board.removeSeedsInPit(startIndex);
		
		for (int i = startIndex + 1; seeds > 0; i++, seeds--) {
			int index =  board.resolvePitIndex(i);
			int seedCount = board.getSeedsInPit(index);
			
			if ((index == Board.MACALA_INDEX_P1 && player.equals(Player.PLAYER_2)) ||
					index == Board.MACALA_INDEX_P2 && player.equals(Player.PLAYER_1)) {
				seeds += 1;
				continue;
			}
			
			if (seeds == 0 && board.getSeedsInPit(index) == 0) {
				int seedsInFront = board.getSeedsInFrontPit(index);
				int macalaIndex = player.equals(Player.PLAYER_1) ? Board.MACALA_INDEX_P1 : Board.MACALA_INDEX_P2;
				board.setSeedsInPit(macalaIndex, seedsInFront + 1);
				board.removeSeedsInFrontPit(index);
			} else {
				board.setSeedsInPit(index, seedCount + 1);
			}
			
			if (seeds == 0 && !((index == Board.MACALA_INDEX_P1 && player.equals(Player.PLAYER_1)) || 
					(index == Board.MACALA_INDEX_P2 && player.equals(Player.PLAYER_2)))) {
				switchTurn();
			}
		}		
		
	}
	
	public Score getScore() {
		return Score.getInstance(board);
	}
	
	/**
	 * Determines whether teh game is finished
	 * @return boolean
	 */
	private boolean isGameFinished() {
		return false;
	}
	
	/**
	 * Switch's turns between players.
	 */
	private void switchTurn() {
		this.turn = this.turn.equals(Player.PLAYER_1) ? Player.PLAYER_2: Player.PLAYER_1;
	}
	
	/**
	 * Chooses randomly the starting player.
	 */
	private void chooseStartingPlayer() {
		this.turn = Player.values()[new Random().nextInt(Player.values().length)];
	}
	
	/**
	 * 
	 * @param player
	 * @param pitIndex
	 */
	public void validateMove(final Player player, final int pitIndex) {	
		if (isGameFinished()) {
			throw new IllegalArgumentException("Game is finished");
		}
		
		boolean validHouseIndex = true;
		
		if (!validHouseIndex) {
			throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from small pits", player));
		}
		
		boolean validPlayer =  Player.valueOf(player.toString()) != null;
		
		if (!validPlayer) {
			throw new IllegalArgumentException(String.format("Unknown player: %s", player));
		}
		
		boolean playerTurn = turn.equals(player);
		
		if (!playerTurn) {
//			throw new IllegalArgumentException(String.format("It' not %s turn", player));
		}
		
	}
}
