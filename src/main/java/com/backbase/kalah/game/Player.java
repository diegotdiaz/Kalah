/**
 * 
 */
package com.backbase.kalah.game;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author dtoro
 *
 */
public enum Player {
	PLAYER_1, PLAYER_2;
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	@JsonCreator
    public static Player parseWithValidation(String player) {
        final String upperCaseStatus = player.toUpperCase();
        if (exists(upperCaseStatus)) {
            return Player.valueOf(upperCaseStatus);
        } else {
            throw new IllegalArgumentException("Unkown player");
        }
    }
	
	private static boolean exists(final String player) {
        return Arrays.stream(values()).anyMatch(s -> s.name().equals(player));
    }
	
	
}
