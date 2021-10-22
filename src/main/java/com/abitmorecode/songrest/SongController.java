package com.abitmorecode.songrest;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SongController {

	public static SongController instance = new SongController();
	private List<Song> songs = new ArrayList<>();

	private static Gson gson = new Gson();


	/**
	 * init a json via a filename
	 * @param filepath file path to json file
	 * @throws IOException thrown, if file doesn't exist or file can't be read
	 */
	public void init(String filepath) throws IOException {
		File file = new File(filepath);
		Stream<String> linesStream = Files.lines(Path.of(filepath));
		AtomicReference<String> allLines = new AtomicReference<>("");
		Arrays.stream(linesStream.toArray()).forEach((line) -> {
			allLines.updateAndGet(v -> v + line);
		});
		String lines = allLines.get().replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
		Song[] loadedIn = gson.fromJson(lines, Song[].class);
		songs = Arrays.stream(loadedIn).collect(Collectors.toList());
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
