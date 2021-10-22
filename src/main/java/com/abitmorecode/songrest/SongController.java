package com.abitmorecode.songrest;

import java.util.ArrayList;
import java.util.List;

public class SongController {

	public static SongController instance = new SongController();
	private final List<Song> songs = new ArrayList<>();

	public void init(String filepath) {
		// do the stuff
	}

	public Song getSpecificSong(int id) {
		return (Song) songs.stream().filter(s -> s.getId() == id);
	}

	public Song[] getAllSongs() {
		return songs.toArray(new Song[]{});
	}

	public void addSong(Song song) {
		songs.add(song);
	}

	public void addSong(String json) {
		// TODO: do this
	}

	public void deleteSong(int id) {
		songs.remove((Song) songs.stream().filter(s -> s.getId() == id));
	}
}
