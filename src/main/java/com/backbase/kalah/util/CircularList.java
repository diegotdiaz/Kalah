/**
 * 
 */
package com.backbase.kalah.util;

import java.util.ArrayList;

/**
 * Extension of ArrayList class, it simulates a circular list which allows to be iterated endlessly.
 * @author dtoro
 */
public class CircularList<E> extends ArrayList<E> {

	
	private static final long serialVersionUID = 6656748151699843145L;


	public CircularList() {
		super();
	}
	
	public CircularList(int capacity) {
		super(capacity);
	}

	@Override
	public E get(int index) {
		int realIndex = getZeroBasedIndex(index);
		return super.get(realIndex);
	}
	
	/**
	 * Resolves the equivalent index in the list from a relative index.
	 * e.g: 
	 * CircularList = [3,1,9,7] and index = 5, it will return 1
	 * CircularList = [3,1,9,7] and index = 4, it will return 0
	 * @param index
	 * @return zero-based index
	 */
	public int getZeroBasedIndex(final int index) {
		if (isEmpty()) {
			return size();
		}
		
 		if (index >= size()) {
			return index % size();
		} else if (index < 0) {
			return (size() + index) % size();
		} 
		
		return index;
	}
	
}
