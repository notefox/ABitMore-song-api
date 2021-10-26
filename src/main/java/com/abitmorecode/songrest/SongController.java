package com.abitmorecode.songrest;

import com.abitmorecode.songrest.SongControllerException.SameSongAlreadyExistException;
import com.abitmorecode.songrest.SongControllerException.SongDoesntExistException;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Song Controller (Singleton)
 */
@RestController
public class SongController {

	public static SongController instance = new SongController();
	private List<Song> songs = new ArrayList<>();

	private static boolean INITIALIZED = false;
	private static final Gson gson = new Gson();

	/**
	 * private constructor for Singleton pattern
	 */
	private SongController() {}

	/**
	 * init a json via a file (filepath)
	 *
	 * on corrupted/malformed json, it will either ignore it/throw an error it if the file still
	 *
	 * inherits basic structure, or throws a RuntimeException
	 *
	 * @param filepath file path to json file
	 * @throws IOException thrown, if file doesn't exist or file can't be read
	 * @throws AlreadyInitializedException thrown, if Controller is already initialized
	 */
	public void init(String filepath) throws IOException, AlreadyInitializedException {
		// check if songs already initialized
		if (INITIALIZED) { throw new AlreadyInitializedException();}

		// load in string
		Stream<String> linesStream = Files.lines(Path.of(filepath));
		AtomicReference<String> allLines = new AtomicReference<>("");
		Arrays.stream(linesStream.toArray()).forEach((line) -> {
			allLines.updateAndGet(v -> v + line);
		});

		// remove all the spaces
		String lines = allLines.get().replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");

		// format Json into Array of Songs
		Song[] loadedIn;
		// init empty array, if no object was found / loaded in
		// TODO: use Logger to inform about that
		if((loadedIn = gson.fromJson(lines, Song[].class)) == null) {
			loadedIn = new Song[]{};
		}

		// remove any Song with null in parameters
		// TODO: use Logger to inform about that
		List<Song> nullList = Arrays.stream(loadedIn).filter(Song::anyNull).collect(Collectors.toList());
		List<Song> clonedList = Arrays.stream(loadedIn).collect(Collectors.toList());
		clonedList.removeAll(nullList);

		// set the cleaned List as the officially running Song List
		songs = clonedList;

		// set init tag to true
		INITIALIZED = true;
	}

	/**
	 * initialize with nothing in Song List
	 */
	void init() {
		// set init tag to true
		INITIALIZED = true;
	}

	/**
	 * resets the SongController instance
	 */
	public void reset() {
		instance = new SongController();
		INITIALIZED = false;
	}

	/**
	 * getter for a specific song in list
	 * @param id id of Song
	 * @return Song searched for
	 * @throws SongDoesntExistException thrown, if id isn't used by a Song
	 * @throws NotYetInitializedException thrown, if INITIALIZED tags hasn't been set yet
	 */
	public Song getSpecificSong(int id) throws SongDoesntExistException, NotYetInitializedException {
		checkIfAlreadyInitialized();

		if (!idAlreadyExist(id)) {
			return (Song) songs.stream().filter(s -> s.getId() == id);
		}
		throw new SongDoesntExistException("Song with id:" + id + "doesn't exist");
	}

	/**
	 * getter for all Songs
	 * @return Array of all songs
	 */
	public Song[] getAllSongs() throws NotYetInitializedException {
		checkIfAlreadyInitialized();
		return songs.toArray(new Song[]{});
	}


	/**
	 * adds a Song with default Song Parameter list
	 *
	 * @see Song
	 * @throws SongIdAlreadyExistException thrown, if id is already in use
	 */
	public void addSong(@NonNull int id, @NonNull String title, @NonNull String artist, @NonNull String label, @NonNull int released) throws SongIdAlreadyExistException, NotYetInitializedException {
		checkIfAlreadyInitialized();
		if (idAlreadyExist(id)) {
			throw new SongIdAlreadyExistException("id: " + id + " already exist");
		}
		Song newSong = new Song(id, title, artist, label, released);
		songs.add(newSong);
	}

	/**
	 * adds a Song with automatic id init
	 * @param title title for Song
	 * @param artist Artist of Song
	 * @param label Label of Song
	 * @param released Song Release Year
	 */
	public void addSong(@NonNull String title, @NonNull String artist, @NonNull String label, @NonNull int released) throws NotYetInitializedException {
		checkIfAlreadyInitialized();
		int id = getLastId() + 1;
		Song newSong = new Song(id, title, artist, label, released);
		songs.add(newSong);
	}

	/**
	 * adds a Song with id
	 * @param song Song
	 * @throws SameSongAlreadyExistException thrown, if the same Song already exist locally and is saved in here
	 */
	public void addSong(Song song) throws SameSongAlreadyExistException, NotYetInitializedException {
		checkIfAlreadyInitialized();
		// check if Song already exist
		if (songAlreadyExist(song)) {
			throw new SameSongAlreadyExistException("the song: " + song + " already exist");
		}
		songs.add(song);
	}

	/**
	 * adds a Song over Json
	 * @param json Json String
	 * @throws SameSongAlreadyExistException thrown, if the same Song already exist locally and is saved in here
	 * @throws NotYetInitializedException thrown, if SongController wasn't yet initialized
	 */
	public void addSong(String json) throws SameSongAlreadyExistException, NotYetInitializedException {
		checkIfAlreadyInitialized();
		Song song = gson.fromJson(json, Song.class);
		// check if Song already exist
		if (songAlreadyExist(song)) {
			throw new SameSongAlreadyExistException("the song: " + song + " already exist");
		}
		songs.add(song);
	}

	/**
	 * deletes a Song over id
	 * @param id id int
	 * @throws SongDoesntExistException thrown, if Song with id doesn't exist
	 * @throws NotYetInitializedException thrown, if SongController wasn't yet initialized
	 */
	public void deleteSong(int id) throws SongDoesntExistException, NotYetInitializedException {
		checkIfAlreadyInitialized();
		if (idAlreadyExist(id)) {
			songs.remove((Song) songs.stream().filter(s -> s.getId() == id));
		}
		throw new SongDoesntExistException("Song with id:" + id + "doesn't exist");
	}

	/**
	 * gets the last id of the local Song List
	 * @return int id
	 */
	private int getLastId() {
		return songs.stream().max((s1, s2) -> Math.max(s1.getId(), s2.getId())).get().getId();
	}

	/**
	 * gives back a boolean, if id already exist in Song List
	 * @param id int id
	 * @return boolean, exists or not?
	 */
	private boolean idAlreadyExist(int id) {
		return songs.stream().anyMatch(s -> s.getId() == id);
	}

	/**
	 * checks if song already exists in list
	 * @param song Song to check
	 * @return boolean, already exist or not
	 */
	private boolean songAlreadyExist(Song song) {
		return songs.stream().anyMatch(s -> (Objects.equals(s.toString(), song.toString())));
	}

	private void checkIfAlreadyInitialized() throws NotYetInitializedException {
		if (!INITIALIZED) {
			throw new NotYetInitializedException();
		}
	}

	static class AlreadyInitializedException extends Exception {
		public AlreadyInitializedException() {
			super("SongController already initialized");
		}
	}

	public static class NotYetInitializedException extends Exception {
		public NotYetInitializedException() {
			super("SongController was not yet initialized! call SongController.instance.init(filepath) for initialisation");
		}
	}
}
