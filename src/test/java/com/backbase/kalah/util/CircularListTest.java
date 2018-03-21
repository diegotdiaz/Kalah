/**
 * 
 */
package com.backbase.kalah.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author dtoro
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CircularListTest {

	private static final int LIST_SIZE = 3;
	private CircularList<Integer> list = new CircularList<Integer>();
	
	@Before
	public void init() {
		for (int i = 0; i < LIST_SIZE; i++) {
			list.add(i);
		}
	}
	
	@Test
	public void testZeroBasedIndex() {
		assertThat(0).isEqualTo(list.getZeroBasedIndex(LIST_SIZE));
		assertThat(2).isEqualTo(list.getZeroBasedIndex(LIST_SIZE + 2));
		assertThat(0).isEqualTo(list.getZeroBasedIndex(LIST_SIZE + 3));
		assertThat(LIST_SIZE - 1).isEqualTo(list.getZeroBasedIndex(-1));
		assertThat(LIST_SIZE - 2).isEqualTo(list.getZeroBasedIndex(-2));
	}

}
