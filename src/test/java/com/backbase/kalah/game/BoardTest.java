/**
 * 
 */
package com.backbase.kalah.game;

import static com.backbase.kalah.game.KalahBoard.PIT_RANGE_P1;
import static com.backbase.kalah.game.KalahBoard.PIT_RANGE_P2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

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
public class BoardTest {

	
	private static final int DEFAULT_SEED_NUM = 6;
	
	private KalahBoard board;
	
	@Before
	public void initialize() {
		board = new KalahBoard(DEFAULT_SEED_NUM);
	}

	@Test
	public void boardStartsWithSeedsInPlayersPits() {
		IntStream.rangeClosed(PIT_RANGE_P1.getMinimumInteger(), PIT_RANGE_P1.getMaximumInteger())
			.forEach(i -> assertThat(board.getSeedsInPit(i)).isEqualTo(DEFAULT_SEED_NUM));
	
		IntStream.rangeClosed(PIT_RANGE_P2.getMinimumInteger(), PIT_RANGE_P2.getMaximumInteger())
			.forEach(i -> assertThat(board.getSeedsInPit(i)).isEqualTo(DEFAULT_SEED_NUM));
		
		assertThat(board.getSeedsInPit(KalahBoard.MACALA_INDEX_P1)).isEqualTo(0);
		assertThat(board.getSeedsInPit(KalahBoard.MACALA_INDEX_P2)).isEqualTo(0);
	}
	
	@Test
	public void testSetSeedsInPit() {
		final int pitIndex = 0;
		final int seedNumber = 1;
		board.setSeedsInPit(pitIndex, seedNumber);
		assertThat(board.getSeedsInPit(pitIndex)).isEqualTo(seedNumber);
	}
	
	@Test
	public void testRemoveSeedsInPit() {
		final int pitIndex = 1;
		board.removeSeedsInPit(pitIndex);
		assertThat(board.getSeedsInPit(pitIndex)).isEqualTo(0);
	}
	
	@Test
	public void testGetSeedsInFrontPit() {
		final int pitIndex = PIT_RANGE_P1.getMinimumInteger();
		final int frontPitIndex = PIT_RANGE_P2.getMaximumInteger();
		
		board.setSeedsInPit(frontPitIndex, 2);
		assertThat(board.getSeedsInPit(pitIndex)).isEqualTo(DEFAULT_SEED_NUM);
		assertThat(board.getSeedsInFrontPit(pitIndex)).isEqualTo(2);
	}
	
	@Test
	public void testRemoveSeedsInFrontPit() {
		final int pitIndex = PIT_RANGE_P1.getMinimumInteger();
		
		board.removeSeedsInFrontPit(pitIndex);
		assertThat(board.getSeedsInPit(pitIndex)).isEqualTo(DEFAULT_SEED_NUM);
		assertThat(board.getSeedsInFrontPit(pitIndex)).isEqualTo(0);
	}
	

}
