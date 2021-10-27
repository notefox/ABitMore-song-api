package com.abitmorecode.songrest.Controller;

import com.abitmorecode.songrest.Models.Song;
import com.abitmorecode.songrest.Services.SongsManager;
import com.abitmorecode.songrest.SongControllerException.SameSongAlreadyExistException;
import com.abitmorecode.songrest.SongControllerException.SongDoesntExistException;
import com.abitmorecode.songrest.SongControllerException.SongIdAlreadyExistException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {
	private static final Logger log = LoggerFactory.getLogger(SongController.class);
	private static final Gson gson = new Gson();
	@Autowired
	private SongsManager songService;

	@GetMapping("/ABitMoreCode/songs/{id}")
	public String getSong(@PathVariable int songId) {
		try {
			return gson.toJson(songService.getSpecificSong(songId));
		} catch (SongDoesntExistException e) {
			// TODO: use Logger
			e.printStackTrace();
			return e.toString();
		}
	}

	@GetMapping("/ABitMoreCode/songs")
	public ResponseEntity<List<Song>> getSongs() {
		return new ResponseEntity<>(songService.getAllSongs(), HttpStatus.OK);
	}

	@PostMapping("/ABitMoreCode/songs")
	public Song postSong(@RequestBody Song song) {
		try {
			songService.addSong(song);
			return song;
		} catch (SameSongAlreadyExistException | SongIdAlreadyExistException e) {
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping("/ABitMoreCode/songs/{id}")
	public int deleteSong(@PathVariable int id) {
		try {
			songService.deleteSong(id);
			return id;
		} catch (SongDoesntExistException | NumberFormatException e) {
			e.printStackTrace();
		}

		// TODO: HTML response with error code
		// use ResponseEntity
		// return new ResponseEntity<>(stuffToReturn, HttpStatus.BAD_REQUEST);
		// https://www.baeldung.com/spring-response-entity
		return -1;
	}
}
