/**
 * 
 */
package com.backbase.kalah.util;

import java.util.ArrayList;

/**
 * @author dtoro
 *
 */
public class CircularList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CircularList(int capacity) {
		super(capacity);
	}

	@Override
	public E get(int index) {
		int realIndex = getZeroBasedIndex(index);
		return super.get(realIndex);
	}
	
	
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
