/**
 * 
 */
package com.backbase.kalah.game;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author diegotoro
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTest {
	
	private static final int DEFAULT_SEED_NUM = 6;
	
	@Test
	public void gameStartsWithPlayerTurn() {
		KalahGame game = createGame(DEFAULT_SEED_NUM);
		assertThat(game.getId()).isNotEqualTo(null);
		assertThat(game.getTurn()).isIn(Arrays.asList(Player.values()));
		assertThat(game.getStatus()).isEqualTo(Status.ON_GOING);
	}
	
	@Test(expected = KalahBrokenRuleException.class)
	public void gameCantStartWithNoSeeds() {
		KalahGame game = createGame(0);
		assertThat(game.getId()).isEqualTo(null);
		assertThat(game.getStatus()).isNotIn(Arrays.asList(Status.values()));
	}
	
	@Test(expected = KalahBrokenRuleException.class)
	public void cantMoveInInvalidPit() {
		KalahGame game = createGame(DEFAULT_SEED_NUM);
		int pit = KalahBoard.PIT_RANGE_P2.getMaximumInteger();
		game.move(game.getTurn(), pit);
	}
	
	@Test(expected = KalahBrokenRuleException.class)
	public void playerCantMoveWithoutTurn() {
		KalahGame game = createGame(DEFAULT_SEED_NUM);
		Player turn = game.getTurn().equals(Player.PLAYER_1) ? Player.PLAYER_2: Player.PLAYER_1; 
		game.move(turn, 1);
	}
	
	@Test(expected = KalahBrokenRuleException.class)
	public void testInvalidPlayer() {
		KalahGame game = createGame(DEFAULT_SEED_NUM);
		game.move(null, 2);
	}
	
	@Test
	public void testTurnSwitch() {
		KalahGame game = createGame(DEFAULT_SEED_NUM);
		Player firstTurn = game.getTurn();
		game.move(firstTurn, 2);
		assertThat(game.getTurn()).isNotEqualTo(firstTurn);		
	}
	
	private KalahGame createGame(final int seedNumber) {
		KalahGame game = new KalahGame(seedNumber);
		return game;
	}
	
}
