package com.abitmorecode.songrest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

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
		try {
			SongController.instance.init(correctpath);
		} catch (IOException e) {
			fail();
		}

		Song[] songs = SongController.instance.getAllSongs();
		assertEquals(new Song(1, "Das Test", "N bisschen Test", "TestTestTest", 2015).toString(), songs[0].toString());
	}

	@Test
	void pathNotFoundTest() {
		assertThrows(NoSuchFileException.class, () -> SongController.instance.init(nonepath));
	}
}