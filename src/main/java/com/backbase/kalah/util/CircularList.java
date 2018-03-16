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
		if (index >= size()) {
			index =  index % size();
		} else if (index < 0) {
			index =  (size() + index) % size();
		} 
		return super.get(index);
	}
	
	
	public boolean isEquivalentIndexes(final int indexA, final int indexB) {
		if (indexB >= size()) {
			return indexA == indexB % size();
		} else if (indexA < 0) {
			return indexA ==  (size() + indexB) % size();
		}
		
		return indexA == indexB;
	}
	
}
