/**
 * 
 */
package com.backbase.kalah.game;

import lombok.Data;

import com.backbase.kalah.util.CircularList;

/**
 * @author dtoro
 *
 */
@Data
public class Board {
	
	private static final int NUM_OF_PITS = 12;
	private static final int NUM_OF_MACALA = 2;
	
	public static final int P1_MACALA_INDEX = 6;
	public static final int P2_MACALA_INDEX = 13;

	private final int seedNumber;
	private CircularList<Integer> pitList;

	
	public Board(final int seedNumber) {
		this.seedNumber = seedNumber;
		pitList = new CircularList<Integer>(NUM_OF_PITS + NUM_OF_MACALA);
		initalizePitList();
	}
	
	/**
	 * Initializes the small pits with the default number of seeds and macalas
	 * with no seeds
	 */
	private void initalizePitList() {
		for (int i = 0; i < pitList.size(); i++) {
			if  (!pitList.isEquivalentIndexes(P1_MACALA_INDEX, i) && 
					!pitList.isEquivalentIndexes(P2_MACALA_INDEX, i)) {
				pitList.set(i, this.seedNumber);
			} else {
				pitList.set(i, 0);
			}
		}	
	}
	
}
