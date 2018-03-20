package com.backbase.kalah.game;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @author dtoro
 *
 */
@Data
public class Game {

	@Setter(value = AccessLevel.PRIVATE)
	private String id;
	
	@Setter(value = AccessLevel.PRIVATE)
	private Board board;
	
	@Setter(value = AccessLevel.PRIVATE)
	private Player turn;
	
	@Setter(value = AccessLevel.PRIVATE)
	private Status status;
	
	/**
	 * Constructor.
	 * @param numOfSeeds number of seeds for pits.
	 */
	public Game(final int numOfSeeds) {
		checkValidSeedNum(numOfSeeds);
		this.id = UUID.randomUUID().toString();
		this.board = new Board(numOfSeeds);
		this.status = Status.STARTED;
		chooseStartingPlayer();
	}
	
	/**
	 * Checks whether number of seeds is grater than zero
	 * @param numOfSeeds
	 */
	private void checkValidSeedNum(final int numOfSeeds) {
		if (numOfSeeds <= 0) {
			throw new IllegalArgumentException("Number of seeds mmust be grater than zero");
		}
	}
	
	/**
	 * Makes a move for the specified player, from the specified pit in the board.
	 * @param player player.
	 * @param pitIndex house Index, 1-based index.
	 */
	public void move(final Player player, final int pitIndex) {
		validateMove(player, pitIndex - 1);
		
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
			
			if (seeds == 1 && !((index == Board.MACALA_INDEX_P1 && player.equals(Player.PLAYER_1)) || 
					(index == Board.MACALA_INDEX_P2 && player.equals(Player.PLAYER_2)))) {
				switchTurn();
			}
		}
		
		updateGameStatus();
		
	}
	
	
	/**
	 * Returns gae's score
	 * @return Score
	 */
	public Score getScore() {
		return Score.getInstance(board);
	}
	
	/**
	 * Updates game status according the number of seens in players pits
	 */
	private void updateGameStatus() {
		boolean p1Finished = IntStream.rangeClosed(Board.PIT_RANGE_P1.getMinimumInteger(), Board.PIT_RANGE_P1.getMaximumInteger())
				.allMatch(i -> board.getSeedsInPit(i) == 0);
			
		boolean p2Finished = IntStream.rangeClosed(Board.PIT_RANGE_P2.getMinimumInteger(),  Board.PIT_RANGE_P2.getMaximumInteger())
				.allMatch(i -> board.getSeedsInPit(i) == 0);
		
		Status status = p1Finished || p2Finished ? Status.FINISHED :  Status.STARTED;
		setStatus(status);
	}
	
	/**
	 * Determines whether the game is finished
	 * @return boolean
	 */
	private boolean isGameFinished() {
		return status.equals(Status.FINISHED);
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
	 * Validates whether a move is legal or not
	 * @param player
	 * @param pitIndex
	 */
	public void validateMove(final Player player, final int pitIndex) {	
		if (isGameFinished()) {
			throw new IllegalArgumentException("Game is finished");
		}
		
		boolean playerTurn = turn.equals(player);
		
		if (!playerTurn) {
			throw new IllegalArgumentException(String.format("It' not %s's turn", player));
		}
		
		boolean validHouseIndex = Board.PIT_RANGE_P1.containsInteger(pitIndex - 1);
		
		if (!validHouseIndex) {
			throw new IllegalArgumentException(String.format("Illegal move, %s may only take seeds from pit %d to %", 
					player, Board.PIT_RANGE_P1.getMinimumInteger(), Board.PIT_RANGE_P1.getMaximumInteger()));
		}
		
	}
}
