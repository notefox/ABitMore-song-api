package com.abitmorecode.songrest;

import com.abitmorecode.songrest.SongControllerException.SameSongAlreadyExistException;
import com.abitmorecode.songrest.SongControllerException.SongDoesntExistException;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class SongrestApplication {

	private static Gson gson = new Gson();

	public static void main(String[] args) {
		SpringApplication.run(SongrestApplication.class, args);
	}

	@GetMapping("/ABitMoreCode/songs/{id}")
	public String getSong(@PathVariable int songId){
		try {
			return gson.toJson(SongController.instance.getSpecificSong(songId));
		} catch (SongDoesntExistException | SongController.NotYetInitializedException e) {
			// TODO: use Logger added later
			e.printStackTrace();
			return e.toString();
		}
	}

	@GetMapping("/ABitMoreCode/songs")
	public String getSongs(){
		try {
			return gson.toJson(SongController.instance.getAllSongs());
		} catch (SongController.NotYetInitializedException e) {
			// TODO: use Logger added later
			e.printStackTrace();
			return e.toString();
		}
	}

	@PostMapping("/ABitMoreCode/songs")
	public Song postSong(@RequestBody Song song){
		try {
			SongController.instance.addSong(song);
			return song;
		} catch (SameSongAlreadyExistException e) {
			e.printStackTrace();
			return null;
		} catch (SongController.NotYetInitializedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping("/ABitMoreCode/songs/{id}")
	public int deleteSong(@PathVariable int songId){
		try {
			SongController.instance.deleteSong(songId);
			return songId;
		} catch (SongDoesntExistException e) {
			e.printStackTrace();
		} catch (SongController.NotYetInitializedException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}

		// TODO: HTML response with error code
		return -1;
	}
}
