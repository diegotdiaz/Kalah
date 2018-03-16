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
		if (index > size()) {
			index =  index % size();
		} else if (index < size()) {
			index =  (size() + index) % size();
		} 
		return get(index);
	}
	
}
