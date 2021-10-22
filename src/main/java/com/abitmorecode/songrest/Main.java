package com.abitmorecode.songrest;

import com.google.gson.Gson;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			SongController.instance.init("exercises/1/songs.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
