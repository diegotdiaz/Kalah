package com.backbase.kalah.game;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import static com.backbase.kalah.util.Messages.*;

/**
 * Class responsible to handle a Kalah game between 2 players
 * @author dtoro
 *
 */
@Data
public class KalahGame {

	@Setter(value = AccessLevel.PRIVATE)
	private String id;
	
	@Setter(value = AccessLevel.PRIVATE)
	private KalahBoard board;
	
	@Setter(value = AccessLevel.PRIVATE)
	private Player turn;
	
	@Setter(value = AccessLevel.PRIVATE)
	private Status status;
	
	/**
	 * Constructor.
	 * @param numOfSeeds number of seeds for pits.
	 */
	public KalahGame(final int numOfSeeds) {
		checkValidSeedNum(numOfSeeds);
		this.id = UUID.randomUUID().toString();
		this.board = new KalahBoard(numOfSeeds);
		this.status = Status.ON_GOING;
		chooseStartingPlayer();
	}
	
	/**
	 * Checks whether number of seeds is grater than zero
	 * @param numOfSeeds
	 */
	private void checkValidSeedNum(final int numOfSeeds) {
		if (numOfSeeds <= 0) {
			throw new KalahBrokenRuleException("Number of seeds must be grater than zero");
		}
	}
	
	/**
	 * Makes a move for the specified player, from the specified pit in the board.
	 * @param player player.
	 * @param pitIndex house Index, 1-based index.
	 */
	public void move(final Player player, final int pitIndex) {
		final int zeroBasedPitIndex = pitIndex - 1;
		validateMove(player, zeroBasedPitIndex);
		
		int startPitIndex = player.equals(Player.PLAYER_1) ? zeroBasedPitIndex :  pitIndex + 6;
		
		int seeds = board.getSeedsInPit(startPitIndex);
		
		if (seeds == 0) {
			throw new KalahBrokenRuleException(String.format(MESSAGE_NO_SEEDS, pitIndex));
		}
		
		board.removeSeedsInPit(startPitIndex);
		
		for (int i = startPitIndex + 1; seeds > 0; i++, seeds--) {
			int index =  board.resolvePitIndex(i);
			int seedCount = board.getSeedsInPit(index);
			
			if ((index == KalahBoard.MACALA_INDEX_P1 && player.equals(Player.PLAYER_2)) ||
					index == KalahBoard.MACALA_INDEX_P2 && player.equals(Player.PLAYER_1)) {
				seeds += 1;
				continue;
			}
			
			if (seeds == 0 && board.getSeedsInPit(index) == 0) {
				int seedsInFront = board.getSeedsInFrontPit(index);
				int macalaIndex = player.equals(Player.PLAYER_1) ? KalahBoard.MACALA_INDEX_P1 : KalahBoard.MACALA_INDEX_P2;
				board.setSeedsInPit(macalaIndex, seedsInFront + 1);
				board.removeSeedsInFrontPit(index);
			} else {
				board.setSeedsInPit(index, seedCount + 1);
			}
			
			if (seeds == 1 && !((index == KalahBoard.MACALA_INDEX_P1 && player.equals(Player.PLAYER_1)) || 
					(index == KalahBoard.MACALA_INDEX_P2 && player.equals(Player.PLAYER_2)))) {
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
		boolean p1Finished = IntStream.rangeClosed(KalahBoard.PIT_RANGE_P1.getMinimumInteger(), KalahBoard.PIT_RANGE_P1.getMaximumInteger())
				.allMatch(i -> board.getSeedsInPit(i) == 0);
			
		boolean p2Finished = IntStream.rangeClosed(KalahBoard.PIT_RANGE_P2.getMinimumInteger(),  KalahBoard.PIT_RANGE_P2.getMaximumInteger())
				.allMatch(i -> board.getSeedsInPit(i) == 0);
		
		Status status = p1Finished || p2Finished ? Status.FINISHED :  Status.ON_GOING;
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
			throw new KalahBrokenRuleException(MESSAGE_GAME_FINISHED);
		}
		
		boolean playerTurn = turn.equals(player);
		
		if (!playerTurn) {
			throw new KalahBrokenRuleException(String.format(MESSAGE_WRONG_TURN, player));
		}
		
		boolean validHouseIndex = KalahBoard.PIT_RANGE_P1.containsInteger(pitIndex);
		
		if (!validHouseIndex) {
			throw new KalahBrokenRuleException(String.format(MESSAGE_ILLEGAL_MOVE, 
					player, KalahBoard.PIT_RANGE_P1.getMinimumInteger(), KalahBoard.PIT_RANGE_P1.getMaximumInteger()));
		}
		
	}
}
