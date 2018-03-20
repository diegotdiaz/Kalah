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
	public void gameStartsWith6Seeds() {
		Game game = createGame(DEFAULT_SEED_NUM);
		assertThat(game.getId()).isNotEqualTo(null);
		assertThat(game.getBoard().getSeedsInPit(0)).isEqualTo(DEFAULT_SEED_NUM);
		assertThat(game.getTurn()).isIn(Arrays.asList(Player.values()));
		assertThat(game.getStatus()).isEqualTo(Status.STARTED);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void gameDontStartWithNoSeeds() {
		Game game = createGame(0);
		assertThat(game.getId()).isEqualTo(null);
		assertThat(game.getStatus()).isNotIn(Arrays.asList(Status.values()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void playerCantMoveWioutTurn() {
		Game game = createGame(DEFAULT_SEED_NUM);
		Player turn = game.getTurn().equals(Player.PLAYER_1) ? Player.PLAYER_2: Player.PLAYER_1; 
		game.move(turn, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidPitInMove() {
		Game game = createGame(DEFAULT_SEED_NUM);
		int pit = Board.PIT_RANGE_P2.getMaximumInteger();
		game.move(game.getTurn(), pit);
	}
	
	@Test
	public void testTurnSwitch() {
		Game game = createGame(DEFAULT_SEED_NUM);
		Player firstTurn = game.getTurn();
		game.move(firstTurn, 2);
		assertThat(game.getTurn()).isNotEqualTo(firstTurn);		
	}
	
	private Game createGame(final int seedNumber) {
		Game game = new Game(seedNumber);
		return game;
	}
	
}
