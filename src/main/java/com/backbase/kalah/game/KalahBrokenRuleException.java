package com.backbase.kalah.game;

/**
 * Exception class thrown when a game rule is broken
 * @author dtoro
 *
 */
public class KalahBrokenRuleException extends RuntimeException {


	private static final long serialVersionUID = -6898569592635699464L;

	public KalahBrokenRuleException(String message) {
		super(message);
	}

	public KalahBrokenRuleException(Throwable cause) {
		super(cause);
	}

	public KalahBrokenRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public KalahBrokenRuleException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
