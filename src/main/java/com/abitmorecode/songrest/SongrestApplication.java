package com.abitmorecode.songrest;

import com.abitmorecode.songrest.SongControllerException.SongDoesntExistException;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SongrestApplication {

	private static Gson gson = new Gson();

	public static void main(String[] args) {
		SpringApplication.run(SongrestApplication.class, args);
	}

	@GetMapping("/ABitMoreCode/songs/{id}")
	public String getSong(@PathVariable String id){
		int songId;
		try {
			songId = Integer.parseInt(id);
		}catch(NumberFormatException e){
			// TODO: HTMLresponse with error code
			return "";
		}
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
}
