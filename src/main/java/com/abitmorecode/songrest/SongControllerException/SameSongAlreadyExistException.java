package com.abitmorecode.songrest.SongControllerException;

public class SameSongAlreadyExistException extends Exception {
	public SameSongAlreadyExistException() {
		super();
	}

	public SameSongAlreadyExistException(String message) {
		super(message);
	}
}
