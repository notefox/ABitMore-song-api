package com.abitmorecode.songrest.Services;

import com.abitmorecode.songrest.Models.Song;
import com.abitmorecode.songrest.SongControllerException.SameSongAlreadyExistException;
import com.abitmorecode.songrest.SongControllerException.SongDoesntExistException;
import com.abitmorecode.songrest.SongControllerException.SongIdAlreadyExistException;

import java.util.List;

public interface SongsManager {
	/**
	 * getter for a specific song in list
	 * @param id id of Song
	 * @return Song searched for
	 * @throws SongDoesntExistException thrown, if id isn't used by a Song
	 */
	Song getSpecificSong(int id) throws SongDoesntExistException;

	/**
	 * getter for all Songs
	 * @return List of all songs
	 */
	List<Song> getAllSongs();

	/**
	 * adds a Song with automatic id init
	 * @param title title for Song
	 * @param artist Artist of Song
	 * @param label Label of Song
	 * @param released Song Release Year
	 */
	//void addSong(String title, String artist, String label, int released);

	/**
	 * adds a Song Object
	 * @param song Song
	 * @throws SameSongAlreadyExistException thrown, if the same Song already exist locally and is saved in here
	 * @throws SongIdAlreadyExistException thrown, if song id, if given, already exists
	 */
	void addSong(Song song) throws SameSongAlreadyExistException, SongIdAlreadyExistException;

	/**
	 * adds a Song over Json
	 * @param json Json String
	 * @throws SameSongAlreadyExistException thrown, if the same Song already exist locally and is saved in here
	 * @throws SongIdAlreadyExistException thrown, if the song id, if given, already exists
	 */
	//void addSong(String json) throws SameSongAlreadyExistException, SongIdAlreadyExistException;

	/**
	 * deletes a Song over id
	 * @param id id int
	 * @throws SongDoesntExistException thrown, if Song with id doesn't exist
	 */
	void deleteSong(int id) throws SongDoesntExistException;

	/**
	 * reset method for deleting all requests-able Songs
	 */
	void reset();
}
