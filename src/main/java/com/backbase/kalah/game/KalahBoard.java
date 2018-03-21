/**
 * 
 */
package com.backbase.kalah.game;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.math.IntRange;
import org.apache.commons.lang.math.Range;

import com.backbase.kalah.util.CircularList;

/**
 * Class to represent the Kalah game board
 * @author dtoro
 */
@Data
public class KalahBoard {
	
	public static final int NUM_OF_PITS = 12;
	public static final int NUM_OF_MACALA = 2;
	
	public static final int MACALA_INDEX_P1 = 6;
	public static final int MACALA_INDEX_P2 = 13;
	
	public static final Range PIT_RANGE_P1 = new IntRange(0, 5);
	public static final Range PIT_RANGE_P2 = new IntRange(7, 12);

	private final int seedNumber;
	
	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	private CircularList<Integer> pitList;

	
	/**
	 * Constructor
	 * @param seedNumber
	 */
	public KalahBoard(int seedNumber) {
		this.seedNumber = seedNumber;
		pitList = new CircularList<Integer>(NUM_OF_PITS + NUM_OF_MACALA);
		initalizePitList();
		
	}
	
	/**
	 * Initializes the small pits with the default number of seeds and macalas
	 * with no seeds
	 */
	private void initalizePitList() {
		for (int i = 0; i < NUM_OF_PITS + NUM_OF_MACALA; i++) {
			if  (i == MACALA_INDEX_P1 || i == KalahBoard.MACALA_INDEX_P2) {
				pitList.add(0);
			} else {
				pitList.add(this.seedNumber);	
			}
		}	
	}
	
	/**
	 * Set a number of seeds in the specified pit index
	 * @param index
	 * @return void
	 */
	public void setSeedsInPit(final int index, final int seeds) {
		pitList.set(index, seeds);
	}
	
	/**
	 * Removes all seeds in the specified pit index
	 * @param index
	 * @return
	 */
	public void removeSeedsInPit(final int index) {
		pitList.set(index, 0);
	}
	
	/**
	 * Get the number of seeds in the front pit
	 * @param index
	 * @return int
	 */
	public int getSeedsInFrontPit(final int index) {
		int frontIndex = NUM_OF_PITS - index;
		return pitList.get(frontIndex);
	}
	
	/**
	 * Removes all seeds in the front pit
	 * @param index
	 * @return
	 */
	public void removeSeedsInFrontPit(final int index) {
		int frontIndex = NUM_OF_PITS - index;
		pitList.set(frontIndex, 0);
	}
	
	/**
	 * Get's the number of seeds in the specified pit index
	 * @param index
	 * @return
	 */
	public int getSeedsInPit(final int index) {
		return pitList.get(index);
	}
	
	/**
	 * Resolves the equivalent index in the list from a relative index.
	 * See the {@link CircularList#getZeroBasedIndex(int)} method.
	 * @param relativeIndex
	 * @return
	 */
	public int resolvePitIndex(int relativeIndex) {
		return pitList.getZeroBasedIndex(relativeIndex);
	}
	
	
}
