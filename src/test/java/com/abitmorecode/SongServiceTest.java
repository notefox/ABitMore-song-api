package com.abitmorecode;

import com.abitmorecode.songrest.Models.Song;
import com.abitmorecode.songrest.Services.SongService;
import com.abitmorecode.songrest.Services.SongsManager;
import com.abitmorecode.songrest.SongControllerException.SameSongAlreadyExistException;
import com.abitmorecode.songrest.SongControllerException.SongIdAlreadyExistException;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongServiceTest {

	@SuppressWarnings("FieldCanBeLocal")
	private final String path = "src/test/java/com/abitmorecode/test_json/";

	private String correctpath;
	private String wrongpath;
	private String weirdpath;
	private String nonepath;

	@BeforeEach
	void setup() {
		correctpath = path + "correct_songs.json";
		wrongpath = path + "incorrect.json";
		weirdpath = path + "incorrect_songs.json";
		nonepath = path + "somepath";
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
		SongsManager songService = null;
		try {
			songService = new SongService(correctpath);
		} catch (IOException e) {
			fail();
		}

		List<Song> songs = songService.getAllSongs();

		assertEquals(new Song(1, "Das Test", "N bisschen Test", "TestTestTest", 2015), songs.stream().findFirst().get());
	}

	@Test
	void pathNotFoundTest() {
		final SongsManager[] songService = new SongService[1];
		assertThrows(NoSuchFileException.class, () -> songService[0] = new SongService(nonepath));
	}

	@Test
	void fileHasNoJsonInItTest() {
		final SongsManager[] songService = new SongService[1];
		assertThrows(JsonParseException.class, () -> songService[0] = new SongService(wrongpath));
	}

	@Test
	void fileHasWrongJsonInItTest() throws IOException {
		SongsManager  songService = new SongService(weirdpath);
		assert songService.getAllSongs().isEmpty();
	}

	@Test
	void addASimpleSong() {
		SongsManager songService = new SongService();
		songService.reset();
		try {
			songService.addSong(new Song(1, "Das Test", "N bisschen Test", "TestTestTest", 2015));
		} catch (SameSongAlreadyExistException | SongIdAlreadyExistException e) {
			fail();
		}
		assertEquals(new Song(1, "Das Test", "N bisschen Test", "TestTestTest", 2015), songService.getAllSongs().stream().findFirst().get());
	}

	@Test
	void addASongWithAutomaticIdAssigment() {
		SongsManager songService = new SongService();
		songService.reset();
	}
}