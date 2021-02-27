package com.kindustry.system.constant;

public enum ValidFlag {

	VALID("VALID"), INVALID("INVALID");

	private String message;

	ValidFlag(String message) {
		this.message = message;
	}

	public String toString() {
		return this.message;
	}
}
