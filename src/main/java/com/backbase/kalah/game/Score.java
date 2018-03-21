/**
 * 
 */
package com.backbase.kalah.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Class to represent a game score
 * @author dtoro
 */
@Data
public final class Score {
	
	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private KalahBoard board;
	
	@Setter(value = AccessLevel.NONE)
	private List<Integer> boardP1 = new ArrayList<>();;
	
	@Setter(value = AccessLevel.NONE)
	private List<Integer> boardP2 = new ArrayList<>();;
	

	/**
	 * Constructor.
	 * @param board board.
	 */
	private Score(@NonNull KalahBoard board) {
		this.board = board;
		initializeBoards();
	}
	
	public static Score getInstance(KalahBoard board) {
		return new Score(board);
	}
	
	/**
	 * Initializes each player's board with their corresponding score
	 */
	private void initializeBoards() {
		IntStream.rangeClosed(KalahBoard.PIT_RANGE_P1.getMinimumInteger(), KalahBoard.MACALA_INDEX_P1)
			.forEach(i -> boardP1.add(board.getSeedsInPit(i)));
		
		IntStream.rangeClosed(KalahBoard.PIT_RANGE_P2.getMinimumInteger(), KalahBoard.MACALA_INDEX_P2)
		.forEach(i -> boardP2.add(board.getSeedsInPit(i)));
	}

 }
