package com.abitmorecode.songrest;

import com.abitmorecode.songrest.SongControllerException.SameSongAlreadyExistException;
import com.abitmorecode.songrest.SongControllerException.SongIdAlreadyExistException;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SongControllerTest {

	@SuppressWarnings("FieldCanBeLocal")
	private final String path = "src/test/java/com/abitmorecode/songrest/test_json/";

	private String correctpath;
	private String wrongpath;
	private String weirdpath;
	private String nonepath;

	@BeforeEach
	void setup() {
		SongController.instance.reset();
		correctpath = path + "correct_songs.json";
		wrongpath = path + "incorrect.json";
		weirdpath = path + "incorrect_songs.json";
		nonepath = path + "somepath";
	}

	void init() {
		try {
			SongController.instance.init(correctpath);
		} catch (IOException | SongController.AlreadyInitializedException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testFilesExist() {
		assertNotNull(correctpath);
		assertNotNull(wrongpath);
		assertNotNull(weirdpath);
		assertNotNull(nonepath);
		assert(new File(correctpath).exists());
		assert(new File(wrongpath).exists());
		assert(new File(weirdpath).exists());
		assert(!new File(nonepath).exists());
	}

	@Test
	void basicInitializeTest() {
		try {
			SongController.instance.init(correctpath);
		} catch (IOException | SongController.AlreadyInitializedException e) {
			fail();
		}

		Song[] songs = new Song[0];
		try {
			songs = SongController.instance.getAllSongs();
		} catch (SongController.NotYetInitializedException e) {
			fail();
		}
		assertEquals(new Song(2, "Das Test", "N bisschen Test", "TestTestTest", 2015).toString(), songs[1].toString());
	}

	@Test
	void pathNotFoundTest() {
		assertThrows(NoSuchFileException.class, () -> SongController.instance.init(nonepath));
	}

	@Test
	void fileHasNoJsonInItTest() {
		assertThrows(JsonParseException.class, () -> SongController.instance.init(wrongpath));
	}

	@Test
	void fileHasWrongJsonInItTest() {
		assertThrows(JsonParseException.class, () -> SongController.instance.init(weirdpath));
	}

	@Test
	void addASimpleSong() {
		init();
		Song song = new Song(2, "tite", "artist", "label", 2020);
		try {
			SongController.instance.addSong(song);
		} catch (SameSongAlreadyExistException | SongController.NotYetInitializedException e) {
			fail();
		}
		try {
			assertEquals(Arrays.stream(SongController.instance.getAllSongs()).findFirst().get().toString(), song.toString());
		} catch (SongController.NotYetInitializedException e) {
			fail();
		}
	}

	@Test
	void addASongWithOriginalParamList() {
		init();
		Song song = new Song(2, "tite", "artist", "label", 2020);
		try {
			SongController.instance.addSong(2, "tite", "artist", "label", 2020);
		} catch (SongController.NotYetInitializedException | SongIdAlreadyExistException e) {
			fail();
		}
		try {
			assertEquals(Arrays.stream(SongController.instance.getAllSongs()).findFirst().get().toString(), song.toString());
		} catch (SongController.NotYetInitializedException e) {
			fail();
		}
	}

	@Test
	void addASongWithAutomaticIdAssigment() {
		init();
	}
}