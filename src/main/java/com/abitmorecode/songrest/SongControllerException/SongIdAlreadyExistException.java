package com.abitmorecode.songrest.SongControllerException;

public class SongIdAlreadyExistException extends Exception {
	public SongIdAlreadyExistException() {
		super();
	}

	public SongIdAlreadyExistException(String message) {
		super(message);
	}
}
