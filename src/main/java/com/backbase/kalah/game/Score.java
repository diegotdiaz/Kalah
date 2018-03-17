/**
 * 
 */
package com.backbase.kalah.game;

import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.lang.NonNull;

/**
 * 
 * Class to represent a game score
 * @author dtoro
 */
@Data
public final class Score {
	
	private static final String TAB = "\t";
	private static final String SPACE_TABS = "\t\t\t\t\t";
	private static final String LINE_JUMP = "\n";
	
	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private Board board;

	/**
	 * Constructor.
	 * @param board board.
	 */
	private Score(@NonNull Board board) {
		this.board = board;
	}
	
	public static Score getInstance(Board board) {
		return new Score(board);
	}
		
	@Override
	public String toString(){		
		return getScoreString();
	}

	private String getScoreString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(LINE_JUMP);
		buffer.append(Player.PLAYER_2);
		buffer.append(LINE_JUMP);
		buffer.append(getPlayer2Score());
		buffer.append(LINE_JUMP);
		buffer.append(board.getSeedsInPit(Board.MACALA_INDEX_P2));
		buffer.append(SPACE_TABS);
		buffer.append(board.getSeedsInPit(Board.MACALA_INDEX_P1));
		buffer.append(LINE_JUMP);
		buffer.append(getPlayer1Score());
		buffer.append(LINE_JUMP);
		buffer.append(Player.PLAYER_1);
		return buffer.toString();
	}
	
	private String getPlayer2Score() {
		StringBuffer buffer = new StringBuffer();
		int from = Board.PIT_RANGE_P2.getMinimumInteger();
		int to = Board.PIT_RANGE_P2.getMaximumInteger();
		
		IntStream.rangeClosed(from, to)
			.forEach(i -> buffer.append(board.getSeedsInPit(to - i + from)).append(TAB));
		return buffer.toString();
	}
	
	private String getPlayer1Score() {
		StringBuffer buffer = new StringBuffer();
		IntStream.rangeClosed(Board.PIT_RANGE_P1.getMinimumInteger(), Board.PIT_RANGE_P1.getMaximumInteger())
			.forEach(i -> buffer.append(board.getSeedsInPit(i)).append(TAB));
		return buffer.toString();
	}
 }
