/**
 * 
 */
package com.backbase.kalah.game;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumerator to represent valid player names
 * @author dtoro
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Player {
	@JsonProperty("player_1")
	PLAYER_1,
	@JsonProperty("player_2")
	PLAYER_2;
	
	@JsonCreator
    public static Player parseWithValidation(String player) {
        if (exists(player)) {
            return Player.valueOf(player.toUpperCase());
        } else {
            throw new KalahBrokenRuleException(String.format("Unknown Player %s", player));
        }
    }
	
	private static boolean exists(final String player) {
        return Arrays.stream(values()).anyMatch(s -> s.name().equalsIgnoreCase(player));
    }
	
	
}
