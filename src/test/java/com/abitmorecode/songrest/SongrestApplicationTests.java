package com.abitmorecode.songrest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SongrestApplicationTests {

	private static SongrestApplication songrestApplication;

	@BeforeAll
	static void beforeAll() {
		songrestApplication = new SongrestApplication();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getSong() {

	}

	@Test
	void getSongs() {

	}
}
