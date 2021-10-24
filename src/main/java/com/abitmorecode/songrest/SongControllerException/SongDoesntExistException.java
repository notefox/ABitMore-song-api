package com.abitmorecode.songrest.SongControllerException;

public class SongDoesntExistException extends Exception{
	public SongDoesntExistException() {
		super();
	}

	public SongDoesntExistException(String message) {
		super(message);
	}
}
