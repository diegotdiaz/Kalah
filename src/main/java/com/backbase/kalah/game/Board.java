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
	
	private static final int NUM_OF_PITS = 6;
	private static final int NUM_OF_MACALA = 2;

	private final int totalSeeds;
	private CircularList<Integer> pitList;

	
	public Board(final int seedNumber) {
		this.totalSeeds = NUM_OF_PITS * seedNumber;
		pitList = new CircularList<Integer>(NUM_OF_PITS +  NUM_OF_MACALA);
	}
	
}
